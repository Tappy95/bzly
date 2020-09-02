package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.DateUtils;
import com.bzly.common.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.thirdparty.TpVideoCallbackDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LNoticeReadyDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.dao.user.LUserSignDao;
import com.mc.bzly.dao.user.MSignRuleDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.thirdparty.TpVideoCallback;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserGameTask;
import com.mc.bzly.model.user.LUserSign;
import com.mc.bzly.model.user.MSignRule;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.user.LUserSignService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LUserSignService.class,version = WebConfig.dubboServiceVersion)
public class LUserSignServiceImpl implements LUserSignService {

	@Autowired
	private LUserSignDao lUserSignDao;
	@Autowired
	private MSignRuleDao mSignRuleDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private MChannelConfigUserDao mChannelConfigUserDao;
	@Autowired
	private TPGameDao tPGameDao;
	@Autowired 
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserGameTaskDao lUserGameTaskDao;
	@Autowired
	private LNoticeReadyDao lNoticeReadyDao;
	@Autowired
	private TpVideoCallbackDao tpVideoDao;
	
	@Override
	public Map<String,Object> add(String userId,String ip) {
		Map<String,Object> data=new HashMap<String,Object>();
		/*int count=lUserSignDao.selectIsSign(userId);
		if(count>0) {
			data.put("res",3);//3今天已签到
			return data;
		}*/
		
		LUserSign lUserSign=lUserSignDao.selectUserId(userId);
		data.put("sumCoin", lCoinChangeDao.selectSignSum(userId));//累计获取金币数
		//用户第一次签到
		if(StringUtil.isNullOrEmpty(lUserSign)) {
			MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
			LUserSign sign=new LUserSign();
			sign.setUserId(userId);
			sign.setSignIp(ip);
			sign.setScore(mSignRule.getScore());
			sign.setSignTime(new Date().getTime());
			sign.setStickTimes(1);
			sign.setRuleId(mSignRule.getRuleId());
			
			jmsProducer.daySign(Type.DAY_SIGN, sign);
			
			data.put("stickTimes", sign.getStickTimes());
			data.put("score", sign.getScore());
			data.put("res",1);
			return data;
		}else {
			String today=DateUtils.getMillisecondTime(new Date().getTime());//当前日期
			String signTime=DateUtils.getMillisecondTime(lUserSign.getSignTime());//最近一次签到记录的日期
			String lastDay=DateUtils.getMillisecondTime(new Date().getTime()-24*60*60*1000);//当前时间减一天
			//今天已签到
			if(today.equals(signTime)) {
				data.put("stickTimes", lUserSign.getStickTimes());
				data.put("score", lUserSign.getScore());
				data.put("res",3);
				return data;
			}else if(signTime.equals(lastDay)) {//连续签到
				LUserSign sign=new LUserSign();
				MSignRule mSignRule=null;
				sign.setStickTimes(lUserSign.getStickTimes()+1);
				if(sign.getStickTimes()>7) {
					mSignRule=mSignRuleDao.selectStickTime(7);
					
				}else {
					mSignRule=mSignRuleDao.selectStickTime(sign.getStickTimes());
				}
				sign.setUserId(userId);
				sign.setSignIp(ip);
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			    
				data.put("stickTimes", sign.getStickTimes());
				data.put("score", sign.getScore());
				data.put("res",1);
				return data;
				
			}else {//非连续签到，从第一天开始签
				MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
				LUserSign sign=new LUserSign();
				sign.setUserId(userId);
				sign.setSignIp(ip);
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setStickTimes(1);
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			    
				data.put("stickTimes", sign.getStickTimes());
				data.put("score", sign.getScore());
				data.put("res",1);
				return data;
			}
		}
	}

	@Override
	public PageInfo<LUserSign> queryList(LUserSign lUserSign) {
		PageHelper.startPage(lUserSign.getPageNum(), lUserSign.getPageSize());
		List<LUserSign> lUserSignList = lUserSignDao.selectList(lUserSign);
		return new PageInfo<>(lUserSignList);
	}
	
	public void coinChangeLog(LUserSign sign) {
        MUserInfo user=mUserInfoDao.selectOne(sign.getUserId());
		LCoinChange coin=new LCoinChange();
		coin.setUserId(sign.getUserId());
		coin.setAmount(sign.getScore());
		coin.setFlowType(1);
		coin.setChangedType(2);
		coin.setChangedTime(new Date().getTime());
		coin.setCoinBalance(user.getCoin());
		lCoinChangeDao.insert(coin);
	}

	@Override
	public PageInfo<LUserSign> pageList(LUserSign lUserSign) {
		PageHelper.startPage(lUserSign.getPageNum(), lUserSign.getPageSize());
		List<LUserSign> lUserSignList = lUserSignDao.selectPage(lUserSign);
		return new PageInfo<>(lUserSignList);
	}

	@Override
	public void masterWorkerCoin(String userId) {
		MUserApprentice mUserApprentice=mUserApprenticeDao.selectUserId(userId);
		if(StringUtil.isNullOrEmpty(mUserApprentice)) {
			return;
		}
		int count=lUserSignDao.selectCount(userId);
		if(count==7) {
			/*PDictionary pDictionary=pDictionaryDao.selectByName(ConstantUtil.APPRENTICE_SIGN_7);*/
			MUserInfo user=mUserInfoDao.selectOne(mUserApprentice.getReferrerId());
			String channelCode = user.getChannelCode() == null?user.getParentChannelCode():user.getChannelCode();
			MChannelConfigUser mChannelConfigUser=mChannelConfigUserDao.selectUserChannelCode(user.getRoleType() > 1?2:1,channelCode == null?"baozhu":channelCode);
			
			LCoinChange coin=new LCoinChange();
			coin.setUserId(mUserApprentice.getReferrerId());
			coin.setAmount(mChannelConfigUser.getSign7());
			coin.setFlowType(1);
			coin.setChangedType(5);     
			coin.setChangedTime(new Date().getTime());
			coin.setCoinBalance(user.getCoin()+mChannelConfigUser.getSign7());
			lCoinChangeDao.insert(coin);
			mUserInfoDao.updatecCoin(mChannelConfigUser.getSign7(), mUserApprentice.getReferrerId());
			
			//修改徒弟贡献金币数
			mUserApprentice.setContribution(mUserApprentice.getContribution()+mChannelConfigUser.getSign7());
			mUserApprenticeDao.update(mUserApprentice);
			
			//推送奖励消息
			AppNewsInform appNewsInform=new AppNewsInform();
			appNewsInform.setUserId(mUserApprentice.getReferrerId());
			appNewsInform.setInformTitle(InformConstant.INVITE_REWARD_TITLE);
			appNewsInform.setInformContent(InformConstant.INVITE_SIGN_CONTENT_7+"+"+mChannelConfigUser.getSign7()+"金币");
			appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
			appNewsInformService.addPush(appNewsInform);
			
		}else if(count==15) {
			/*PDictionary pDictionary=pDictionaryDao.selectByName(ConstantUtil.APPRENTICE_SIGN_15);*/
			
			MUserInfo user=mUserInfoDao.selectOne(mUserApprentice.getReferrerId());
	    	if(StringUtil.isNullOrEmpty(user.getChannelCode())) {
	    		user.setChannelCode("baozhu");
	    	}
			if(user.getRoleType()!=1) {
				user.setRoleType(2);
			}
			MChannelConfigUser mChannelConfigUser=mChannelConfigUserDao.selectUserChannelCode(user.getRoleType(), user.getChannelCode());
			
			LCoinChange coin=new LCoinChange();
			coin.setUserId(mUserApprentice.getReferrerId());
			coin.setAmount(mChannelConfigUser.getSign15());
			coin.setFlowType(1);
			coin.setChangedType(5);
			coin.setChangedTime(new Date().getTime());
			coin.setCoinBalance(user.getCoin()+mChannelConfigUser.getSign15());
			lCoinChangeDao.insert(coin);
			mUserInfoDao.updatecCoin(mChannelConfigUser.getSign15(), mUserApprentice.getReferrerId());
			//修改徒弟贡献金币数
			mUserApprentice.setContribution(mUserApprentice.getContribution()+mChannelConfigUser.getSign15());
			mUserApprenticeDao.update(mUserApprentice);
			//推送奖励消息
			AppNewsInform appNewsInform=new AppNewsInform();
			appNewsInform.setUserId(mUserApprentice.getReferrerId());
			appNewsInform.setInformTitle(InformConstant.INVITE_REWARD_TITLE);
			appNewsInform.setInformContent(InformConstant.INVITE_SIGN_CONTENT_15+"+"+mChannelConfigUser.getSign15()+"金币");
			appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
			appNewsInformService.addPush(appNewsInform);
		}
		
	}

	@Override
	public Map<String, Object> getDaySign(String userId) {
		Map<String,Object> data=new HashMap<String,Object>();
		LUserSign lUserSign=lUserSignDao.selectUserId(userId);
		data.put("res",1);//1没有匹配游戏2匹配了游戏
		data.put("isSign", 1);//1今天未签到2今天已签到
		data.put("sumCoin", lCoinChangeDao.selectSignSum(userId));//累计获取金币数
		data.put("isRemind", lNoticeReadyDao.selectSignCount(userId));
		//data.put("isVideo", tpVideoDao.selectCount(userId, 1));
		PDictionary lotteryDictionary=pDictionaryDao.selectByName(DictionaryUtil.SIGN_EXTRA_COIN);
		data.put("taskCoin", Long.parseLong(lotteryDictionary.getDicValue())/10000.0);
		if(StringUtil.isNullOrEmpty(lUserSign)) {
			data.put("stickTimes", 0);//连续签到天数
			return data;
		}else {
			String today=DateUtils.getMillisecondTime(new Date().getTime());//当前日期
			String signTime=DateUtils.getMillisecondTime(lUserSign.getSignTime());//最近一次签到记录的日期
			String lastDay=DateUtils.getMillisecondTime(new Date().getTime()-24*60*60*1000);//当前时间减一天
			if(today.equals(signTime)) {//今天已签到
				data.put("stickTimes", lUserSign.getStickTimes());
				data.put("isSign", 2);
				data.put("isTask", lUserSign.getIsTask());//是否领取了任务奖励1未领取2领取
				if(lUserSign.getIsTask().intValue()!=1) {
				   data.put("taskCoin", lUserSign.getTaskCoin()/10000.0);
				}
				if(lUserSign.getStickTimes().intValue()>=7) {
					TPGame tpGame=tPGameDao.selectSignTask(userId);
					if(!StringUtil.isNullOrEmpty(tpGame)) {
						data.put("res",2);
						data.put("tpGame",tpGame);
					}
				}
				return data;
			}else if(signTime.equals(lastDay)) {//连续签到
				data.put("stickTimes", lUserSign.getStickTimes());
				return data;
			}else {
				data.put("stickTimes", 0);//连续签到天数
				return data;
			}
		}
	}

	@Override
	public Map<String, Object> recommendGameSign(int ptype, String userId) {
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("res", 1);//成功
		LUserSign lUserSign=lUserSignDao.selectUserId(userId);
		if(lUserSign.getStickTimes().intValue()<7) {
			data.put("res", 2);//签到天数不足
			return data;
		}
		if(lUserSign.getIsTask().intValue()==2) {
			data.put("res", 3);//任务奖励已领取
			return data;
		}
		LUserGameTask lUserGameTask=lUserGameTaskDao.selectSginOne(userId, 3);
		if(!StringUtil.isNullOrEmpty(lUserGameTask) && lUserGameTask.getState().intValue()==2) {
			data.put("res", 4);//任务已完成
			data.put("tpGame", null);
			return data;//任务已完成
		}
		TPGame tpGame = new TPGame();
		List<TPGame> tpGames = tPGameDao.recommendGameList(ptype,userId);
		
		Random r=new Random();
		int index=r.nextInt(tpGames.size());
        tpGame=tpGames.get(index);
        
		data.put("tpGame", tpGame);
		return data;
	}

	@Override
	public Result receiveCoin(String userId) {
		Result result = new Result();
		LUserSign lUserSign=lUserSignDao.selectUserId(userId);
		if(lUserSign.getIsTask().intValue()==2) {
			result.setStatusCode(RespCodeState.REPEAT_RECEIVE.getStatusCode());
			result.setMessage(RespCodeState.REPEAT_RECEIVE.getMessage());
			return result;
		}
		LUserGameTask lUserGameTask=lUserGameTaskDao.selectSginOne(userId, 3);
		if(StringUtil.isNullOrEmpty(lUserGameTask) || lUserGameTask.getState()==1) {
			result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
			result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
			return result;
		}
		jmsProducer.signTask(Type.SIGN_TASK, userId);
		
		result.setData(pDictionaryDao.selectByName(DictionaryUtil.SIGN_EXTRA_COIN).getDicValue());
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}
	@Override
	public Map<String, Object> addNew(String userId,String ip) throws Exception {
		Map<String,Object> data=new HashMap<String,Object>();
		int count=tpVideoDao.selectCount(userId,1);
		data.put("video", 1);//签到成功
		if(count<=0) {
			data.put("video", 2);//未观看视频
			return data;
		}
		LUserSign lUserSign=lUserSignDao.selectUserId(userId);
		data.put("sumCoin", lCoinChangeDao.selectSignSum(userId));//累计获取金币数
		//用户第一次签到
		if(StringUtil.isNullOrEmpty(lUserSign)) {
			MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
			LUserSign sign=new LUserSign();
			sign.setUserId(userId);
			sign.setSignIp(ip);
			sign.setScore(mSignRule.getScore());
			sign.setSignTime(new Date().getTime());
			sign.setStickTimes(1);
			sign.setRuleId(mSignRule.getRuleId());
			
			jmsProducer.daySign(Type.DAY_SIGN, sign);
			
			data.put("stickTimes", sign.getStickTimes());
			data.put("score", sign.getScore());
			data.put("res",1);
			return data;
		}else {
			String today=DateUtils.getMillisecondTime(new Date().getTime());//当前日期
			String signTime=DateUtils.getMillisecondTime(lUserSign.getSignTime());//最近一次签到记录的日期
			String lastDay=DateUtils.getMillisecondTime(new Date().getTime()-24*60*60*1000);//当前时间减一天
			//今天已签到
			if(today.equals(signTime)) {
				data.put("stickTimes", lUserSign.getStickTimes());
				data.put("score", lUserSign.getScore());
				data.put("res",3);
				return data;
			}else if(signTime.equals(lastDay)) {//连续签到
				LUserSign sign=new LUserSign();
				MSignRule mSignRule=null;
				sign.setStickTimes(lUserSign.getStickTimes()+1);
				if(sign.getStickTimes()>7) {
					mSignRule=mSignRuleDao.selectStickTime(7);
					
				}else {
					mSignRule=mSignRuleDao.selectStickTime(sign.getStickTimes());
				}
				sign.setUserId(userId);
				sign.setSignIp(ip);
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			    
				data.put("stickTimes", sign.getStickTimes());
				data.put("score", sign.getScore());
				data.put("res",1);
				return data;
				
			}else {//非连续签到，从第一天开始签
				MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
				LUserSign sign=new LUserSign();
				sign.setUserId(userId);
				sign.setSignIp(ip);
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setStickTimes(1);
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			    
				data.put("stickTimes", sign.getStickTimes());
				data.put("score", sign.getScore());
				data.put("res",1);
				return data;
			}
		}
	}

	
}
