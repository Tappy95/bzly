package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.utils.DateUtils;
import com.bzly.common.utils.ShaUtil;
import com.mc.bzly.base.CsjResult;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.thirdparty.TpVideoCallbackDao;
import com.mc.bzly.dao.user.LUserBQDao;
import com.mc.bzly.dao.user.LUserSignDao;
import com.mc.bzly.dao.user.LUserWelfareDao;
import com.mc.bzly.dao.user.MSignRuleDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.thirdparty.TPYmCallback;
import com.mc.bzly.model.thirdparty.TpVideoCallback;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.model.user.LUserSign;
import com.mc.bzly.model.user.LUserWelfare;
import com.mc.bzly.model.user.MSignRule;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.thirdparty.TpVideoCallbackService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = TpVideoCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TpVideoCallbackServiceImpl implements TpVideoCallbackService{
	@Autowired
	private TpVideoCallbackDao tpVideoDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired 
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserWelfareDao lUserWelfareDao;
	@Autowired
	private JMSProducer jmsProducer;

	@Override
	public CsjResult hbVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey) {
		CsjResult result=new CsjResult();
		result.setIsValid(true);
		TpVideoCallback video=tpVideoDao.selectOne(tpVideoCallback.getUser_id());
		if(!StringUtil.isNullOrEmpty(video)) {
			result.setIsValid(false);
			return result;
		}
		
		String sha256=ShaUtil.SHA256(appSecurityKey+":"+tpVideoCallback.getTrans_id());
		if(!sha256.equals(tpVideoCallback.getSign())) {
			tpVideoCallback.setState(2);
			tpVideoCallback.setRemarks("验签失败");
		}
		tpVideoCallback.setCreatorTime(new Date().getTime());
		tpVideoDao.save(tpVideoCallback);
		
		if(tpVideoCallback.getState().intValue()==2) {
			result.setIsValid(false);
			return result;
		}
		return result;
		
		/*LUserSign lUserSign=lUserSignDao.selectUserId(tpVideoCallback.getUser_id());

		//用户第一次签到
		if(StringUtil.isNullOrEmpty(lUserSign)) {
			MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
			LUserSign sign=new LUserSign();
			sign.setUserId(tpVideoCallback.getUser_id());
			sign.setScore(mSignRule.getScore());
			sign.setSignTime(new Date().getTime());
			sign.setStickTimes(1);
			sign.setRuleId(mSignRule.getRuleId());
			
			jmsProducer.daySign(Type.DAY_SIGN, sign);
			
		}else {
			String today=DateUtils.getMillisecondTime(new Date().getTime());//当前日期
			String signTime=DateUtils.getMillisecondTime(lUserSign.getSignTime());//最近一次签到记录的日期
			String lastDay=DateUtils.getMillisecondTime(new Date().getTime()-24*60*60*1000);//当前时间减一天
			//今天已签到
			if(today.equals(signTime)) {
				result.setIsValid(true);
				return result;
			}else if(signTime.equals(lastDay)) {//连续签到
				LUserSign sign=new LUserSign();
				MSignRule mSignRule=null;
				sign.setStickTimes(lUserSign.getStickTimes()+1);
				if(sign.getStickTimes()>7) {
					mSignRule=mSignRuleDao.selectStickTime(7);
					
				}else {
					mSignRule=mSignRuleDao.selectStickTime(sign.getStickTimes());
				}
				sign.setUserId(tpVideoCallback.getUser_id());
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			    
			}else {//非连续签到，从第一天开始签
				MSignRule mSignRule=mSignRuleDao.selectStickTime(1);
				LUserSign sign=new LUserSign();
				sign.setUserId(tpVideoCallback.getUser_id());
				sign.setScore(mSignRule.getScore());
				sign.setSignTime(new Date().getTime());
				sign.setStickTimes(1);
				sign.setRuleId(mSignRule.getRuleId());
				sign.setLastDay(lUserSign.getSignTime());
				
			    jmsProducer.daySign(Type.DAY_SIGN, sign);
			}
		}
	
		return result;*/
	}

	@Override
	public CsjResult syVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey) {
		CsjResult result=new CsjResult();
		TpVideoCallback video=tpVideoDao.selectOne(tpVideoCallback.getTrans_id());
		if(!StringUtil.isNullOrEmpty(video)) {
			result.setIsValid(false);
			return result;
		}
		String sha256=ShaUtil.SHA256(appSecurityKey+":"+tpVideoCallback.getTrans_id());
		if(!sha256.equals(tpVideoCallback.getSign())) {
			tpVideoCallback.setState(2);
			tpVideoCallback.setRemarks("验签失败");
		}
		TpVideoCallback videoTime=tpVideoDao.selectTime(tpVideoCallback.getUser_id());
		if(!StringUtil.isNullOrEmpty(videoTime)) {
			long day=new Date().getTime()-videoTime.getCreatorTime();
			long time=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_INTERVAL_TIME).getDicValue())*1000;
			if(day<=time) {
				tpVideoCallback.setState(3);
				tpVideoCallback.setRemarks("短时间内不能再次获取奖励");
			}
		}
		
		int count=tpVideoDao.selectCount(tpVideoCallback.getUser_id(), 2);
		int dic=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_NUMBER).getDicValue());
		if(count>=dic) {
			tpVideoCallback.setState(3);
			tpVideoCallback.setRemarks("超过当天奖励次数");
		}
		MUserInfo user=mUserInfoDao.selectOne(tpVideoCallback.getUser_id());
	    if(StringUtil.isNullOrEmpty(user)) {
	    	tpVideoCallback.setState(3);
			tpVideoCallback.setRemarks("用户不存在");
	    }
	    String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
	    /*if("lee".equals(channelCode)) {
	    	tpVideoCallback.setReward_amount(600);
	    }*/
	    
		jmsProducer.videoTask(Type.VIDEO_TASK, tpVideoCallback);
		
		if(tpVideoCallback.getState().intValue()==2) {
			result.setIsValid(false);
			return result;
		}else {
			result.setIsValid(true);
			return result;
		}
	}
	
	@Override
	public CsjResult bqVideoCallback(TpVideoCallback tpVideoCallback, String appSecurityKey) {
		CsjResult result=new CsjResult();
		result.setIsValid(true);
		TpVideoCallback video=tpVideoDao.selectOne(tpVideoCallback.getTrans_id());
		if(!StringUtil.isNullOrEmpty(video)) {
			result.setIsValid(false);
			return result;
		}
		
		String sha256=ShaUtil.SHA256(appSecurityKey+":"+tpVideoCallback.getTrans_id());
		if(!sha256.equals(tpVideoCallback.getSign())) {
			tpVideoCallback.setState(2);
			tpVideoCallback.setRemarks("验签失败");
		}
		long now = new Date().getTime();
		tpVideoCallback.setCreatorTime(now);
		tpVideoDao.save(tpVideoCallback);
		
		if(tpVideoCallback.getState().intValue()==2) {
			result.setIsValid(false);
			return result;
		}
		
		result.setIsValid(true);
		return result;
	}


	@Override
	public Map<String, Object> videoUser(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		int count=tpVideoDao.selectCount(userId, 2);
		int dic=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_NUMBER).getDicValue());
		long time=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_INTERVAL_TIME).getDicValue())*1000;
		TpVideoCallback video=tpVideoDao.selectTime(userId);
		
		/*if(count>=dic) {
			data.put("res", 1);//超过当天观看次数
			data.put("coin", video.getReward_amount());//奖励金币数
			data.put("frequency", dic);//观看视频限制次数
			data.put("count", count);//用户观看次数
			return data;
		}
		
		if(StringUtil.isNullOrEmpty(video)) {
			data.put("res", 2);//可以观看下一个视频
			return data;
		}*/
		long day=new Date().getTime()-video.getCreatorTime();
		if(day<=time) {
			data.put("res", 3);//限制时间内
			data.put("time", time-day);//毫秒值
			data.put("coin", video.getReward_amount());//奖励金币数
			data.put("frequency", dic);//观看视频限制次数
			data.put("count", count);//用户观看次数
			return data;
		}else {
			data.put("res", 2);//可以观看下一个视频
			return data;
		}
	}

	@Override
	public Map<String, Object> videoCount(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("res", 1);
		int count=tpVideoDao.selectCount(userId, 2);
		int dic=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_NUMBER).getDicValue());
		if(count>=dic) {
			data.put("res", 2);//超过当天观看次数
		}
		return data;
	}

	@Override
	public Map<String, Object> list(TpVideoCallback tpVideoCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		tpVideoCallback.setPageIndex(tpVideoCallback.getPageSize() * (tpVideoCallback.getPageNum() - 1));
		List<TpVideoCallback> tpVideoCallbackList = tpVideoDao.selectList(tpVideoCallback);
		int total=tpVideoDao.selectListCount(tpVideoCallback);
		Map<String, Object> countMap=tpVideoDao.selectCountSum(tpVideoCallback);
		Map<String, Object> smallMap=tpVideoDao.selectSmallSum(tpVideoCallback);
		result.put("list", tpVideoCallbackList);
		result.put("total", total);
		result.put("successCount", countMap.get("successCount"));
		result.put("moneySum", countMap.get("coinSum")==null ? 0:countMap.get("coinSum"));

		result.put("smallSuccessCount", smallMap.get("smallSuccessCount"));
		result.put("smallMoneySum", smallMap.get("smallCoinSum")==null ? 0:smallMap.get("smallCoinSum"));
		return result;
	}

	@Override
	public Map<String, Object> videoTimeUser(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		int count=tpVideoDao.selectCount(userId, 2);
		int dic=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_NUMBER).getDicValue());
		
		if(count>=dic) {
			data.put("res", 1);//超过当天观看次数
			
			return data;
		}
		
		TpVideoCallback video=tpVideoDao.selectTime(userId);
		if(StringUtil.isNullOrEmpty(video)) {
			data.put("res", 2);//可以观看下一个视频
			return data;
		}
		long day=new Date().getTime()-video.getCreatorTime();
		long time=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIDEO_INTERVAL_TIME).getDicValue())*1000;
		
		if(day<=time) {
			data.put("res", 3);//限制时间内
			return data;
		}else {
			data.put("res", 2);//可以观看下一个视频
			return data;
		}
	}

	@Override
	public Map<String, Object> newUserVideo(String userId) {
		Map<String,Object> data=new HashMap<>();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(user.getCreateTime().longValue()>1572598800000l) {
			int video=tpVideoDao.selectNewUserVideo(userId);
			if(video>=1) {
				int welfare=lUserWelfareDao.selectCount(userId, 1);
				if(welfare>=1) {
					data.put("cou", -1);	
				}else {
					LUserWelfare lUserWelfare=new LUserWelfare();
					lUserWelfare.setUserId(userId);
					lUserWelfare.setTypes(1);
					lUserWelfare.setCreateTime(new Date().getTime());
					lUserWelfareDao.add(lUserWelfare);
					data.put("cou", video);	
				}
			}else {
				data.put("cou", video);	
			}
		}else {
			data.put("cou", -1);
		}
		return data;
	}

	@Override
	public Map<String, Object> newUserVideoCoin(String userId) {
		Map<String,Object> data=new HashMap<>();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(user.getCreateTime().longValue()>1572598800000l) {
			data.put("rewardAmount", tpVideoDao.selectNewUserVideoCoin(userId));
		}else {
			data.put("rewardAmount", 0);
		}
		return data;
	}

	@Override
	public CsjResult tkVideoCallback(TpVideoCallback tpVideoCallback, String appSecurityKey) {
		CsjResult result=new CsjResult();
		TpVideoCallback video=tpVideoDao.selectOne(tpVideoCallback.getTrans_id());
		if(!StringUtil.isNullOrEmpty(video)) {
			result.setIsValid(false);
			return result;
		}
		String sha256=ShaUtil.SHA256(appSecurityKey+":"+tpVideoCallback.getTrans_id());
		if(!sha256.equals(tpVideoCallback.getSign())) {
			tpVideoCallback.setState(2);
			tpVideoCallback.setRemarks("验签失败");
		}
		
		int count=tpVideoDao.selectCount(tpVideoCallback.getUser_id(), 4);
		if(count>=2) {
			tpVideoCallback.setState(3);
			tpVideoCallback.setRemarks("超过奖励次数");
		}
		MUserInfo user=mUserInfoDao.selectOne(tpVideoCallback.getUser_id());
	    if(StringUtil.isNullOrEmpty(user)) {
	    	tpVideoCallback.setState(3);
			tpVideoCallback.setRemarks("用户不存在");
	    }
		
		jmsProducer.videoTask(Type.VIDEO_TASK, tpVideoCallback);
		
		if(tpVideoCallback.getState().intValue()==2) {
			result.setIsValid(false);
			return result;
		}else {
			result.setIsValid(true);
			return result;
		}
	}
}
