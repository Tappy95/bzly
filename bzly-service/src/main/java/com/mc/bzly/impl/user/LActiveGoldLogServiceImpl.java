package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.DateUtils;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.passbook.MVipInfoDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.user.LActiveGoldLogDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.passbook.MVipInfo;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LActiveGoldLog;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserGameTask;
import com.mc.bzly.model.user.LUserVip;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.user.LActiveGoldLogService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LActiveGoldLogService.class,version = WebConfig.dubboServiceVersion)
public class LActiveGoldLogServiceImpl implements LActiveGoldLogService{
    @Autowired
	private LUserVipDao lUserVipDao;
    @Autowired
    private MVipInfoDao mVipInfoDao;
    @Autowired
    private LActiveGoldLogDao lActiveGoldLogDao;
    @Autowired
   	private LCoinChangeDao lCoinChangeDao;
    @Autowired
    private LPigChangeDao lPigChangeDao;
    @Autowired
   	private MUserInfoDao mUserInfoDao;
    @Autowired
	private AppNewsInformService appNewsInformService;
    @Autowired
	private JMSProducer jmsProducer;
    @Autowired
    private LUserGameTaskDao lUserGameTaskDao;
    @Autowired 
	private TPGameDao tpGameDao;
    
	@Override
	public Map<String,Object> receiveActive(String userId) {
		Map<String,Object> date=new HashMap<String,Object>();
		List<LUserVip> userVips=lUserVipDao.selectIdList(userId);
		if(userVips.size()==0) {
			date.put("res", 3);
			return date;//用户没有开通任何vip
		}
		if(lActiveGoldLogDao.selectDay(userId)!=0) {
			date.put("res", 2);
			return date;//用户已领取过活跃金
		}
		MVipInfo mVipInfo=null;
		LActiveGoldLog active=null;
		LUserVip overdueVip=null;
		List<LActiveGoldLog> list=new ArrayList<LActiveGoldLog>();
		Map<String,Object> map=null;
		long coin=0;
		long pig=0;
		String currentTime=DateUtils.getMillisecondTime(new Date().getTime());//当前时间年月日
		String vipCreatorTime=null;//vip创建时间年月日
		String lastTime=null;//最后一次领取奖励时间年月日
		long maxDay=0;
		for(LUserVip userVip:userVips) {
			mVipInfo=mVipInfoDao.selectOne(userVip.getVipId());//获取vip信息
			if(userVip.getVipId().intValue()==10) {
				map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),3);
				coin=coin+Long.parseLong(map.get("coin").toString());
				pig=pig+Long.parseLong(map.get("pig").toString());
				maxDay=Long.parseLong(map.get("maxDay").toString());
				list.add((LActiveGoldLog)map.get("activeLog"));
				continue;
			}
			active=lActiveGoldLogDao.selectTimeMax(userId, userVip.getVipId());//获取最后一次领取记录
			overdueVip=lUserVipDao.selectOverdue(userId, userVip.getVipId());//获取最后一次过期的vip
			if(StringUtil.isNullOrEmpty(overdueVip)) {//第一次购买vip
				if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
					vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
					map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
					coin=coin+Long.parseLong(map.get("coin").toString());
					pig=pig+Long.parseLong(map.get("pig").toString());
					maxDay=Long.parseLong(map.get("maxDay").toString());
					list.add((LActiveGoldLog)map.get("activeLog"));
					/*long day=DateUtils.getDaySub(vipCreatorTime,currentTime)+1;//获取多少天未领取
					if(day>5) {//最多只能领取5天的
						day=5;
					}
					if(maxDay<day) {
						maxDay=day;
					}
					coin=coin+mVipInfo.getEverydayActiveCoin()*day;
					pig=pig+mVipInfo.getEverydayActivePig()*day;
					activeLog.setUserId(userId);
					activeLog.setVipId(userVip.getVipId());
                    activeLog.setActiveCoin(mVipInfo.getEverydayActiveCoin()*day);
                    activeLog.setActivePig(mVipInfo.getEverydayActivePig()*day);
                    activeLog.setCreatorTime(new Date().getTime());
                    activeLog.setDays(day);
                    list.add(activeLog);*/
				}else {
					lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
					map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
					coin=coin+Long.parseLong(map.get("coin").toString());
					pig=pig+Long.parseLong(map.get("pig").toString());
					maxDay=Long.parseLong(map.get("maxDay").toString());
					list.add((LActiveGoldLog)map.get("activeLog"));
					/*long day=DateUtils.getDaySub(lastTime,currentTime)+1;//获取多少天未领取
					if(day>5) {//最多只能领取5天的
						day=5;
					}
					if(maxDay<day) {
						maxDay=day;
					}
					coin=coin+mVipInfo.getEverydayActiveCoin()*day;
					pig=pig+mVipInfo.getEverydayActivePig()*day;
					activeLog.setUserId(userId);
					activeLog.setVipId(userVip.getVipId());
                    activeLog.setActiveCoin(mVipInfo.getEverydayActiveCoin()*day);
                    activeLog.setActivePig(mVipInfo.getEverydayActivePig()*day);
                    activeLog.setCreatorTime(new Date().getTime());
                    activeLog.setDays(day);
                    list.add(activeLog);*/
				}
			}else {
				if(overdueVip.getExpireTime().equals(vipCreatorTime)) {//连续购买
					if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
						vipCreatorTime=DateUtils.getMillisecondTime(overdueVip.getCreateTime());
						map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						list.add((LActiveGoldLog)map.get("activeLog"));
					}else {
						lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
						map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						list.add((LActiveGoldLog)map.get("activeLog"));
					}
				}else {//非连续购买
					if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
						vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
						map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						list.add((LActiveGoldLog)map.get("activeLog"));
					}else {
						if(active.getCreatorTime()>userVip.getCreateTime()) {//最后一领取时间在最新vip开通之后
							lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
							map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
							coin=coin+Long.parseLong(map.get("coin").toString());
							pig=pig+Long.parseLong(map.get("pig").toString());
							maxDay=Long.parseLong(map.get("maxDay").toString());
							list.add((LActiveGoldLog)map.get("activeLog"));	
						}else {//最后一领取时间在最新vip开通之前
							vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
							map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
							coin=coin+Long.parseLong(map.get("coin").toString());
							pig=pig+Long.parseLong(map.get("pig").toString());
							maxDay=Long.parseLong(map.get("maxDay").toString());
							list.add((LActiveGoldLog)map.get("activeLog"));
						}
					}
				}
			}
		}
		
		Map<String,Object> jmsData=new HashMap<>();
		jmsData.put("coin", coin);
		jmsData.put("pig", pig);
		jmsData.put("list", list);
		jmsData.put("userId", userId);
		jmsProducer.dayActive(Type.DAY_ACTIVE, jmsData);
		
		date.put("coin", coin);
		date.put("pig", pig);
		date.put("maxDay", maxDay);
		date.put("res", 1);
		return date;
	}
	//计算活跃金领取数量
	public Map<String,Object> calculationActive(String userId,String startTime,String endTime,long maxDay,MVipInfo mVipInfo,Integer vipId,Integer type){
		Map<String,Object> map=new HashMap<String,Object>();
		LActiveGoldLog activeLog=new LActiveGoldLog();
		long day=0;
		if(type==1) {//1没有最后次领取时间2有最后一次领取时间3只能领取当天的活跃金
			day=DateUtils.getDaySub(startTime,endTime)+1;
		}else if(type==2){
			day=DateUtils.getDaySub(startTime,endTime);//获取多少天未领取	
		}else {
			day=1;
		}
		if(maxDay<day) {
			maxDay=day;
		}
		if(day>5) {//最多只能领取5天的
			day=5;
		}
		long coin=mVipInfo.getEverydayActiveCoin()*day;
		long pig=mVipInfo.getEverydayActivePig()*day;
		activeLog.setUserId(userId);
		activeLog.setVipId(vipId);
        activeLog.setActiveCoin(mVipInfo.getEverydayActiveCoin()*day);
        activeLog.setActivePig(mVipInfo.getEverydayActivePig()*day);
        activeLog.setCreatorTime(new Date().getTime());
        activeLog.setDays(day);
        map.put("coin", coin);
        map.put("pig", pig);
        map.put("maxDay", maxDay);
        map.put("activeLog", activeLog);
        return map;
	}
	//修改金币金猪记录
	public void rechargeRewardUpdate(String userId,Long coinNum,Long pigNum){
		    MUserInfo user=mUserInfoDao.selectOne(userId);
			LCoinChange coin=new LCoinChange();
			coin.setUserId(userId);
			coin.setAmount(coinNum);
			coin.setFlowType(1);
			coin.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_6);     
			coin.setChangedTime(new Date().getTime());
			coin.setCoinBalance(user.getCoin()+coinNum);
			lCoinChangeDao.insert(coin);
			mUserInfoDao.updatecCoin(coinNum, userId);
		    LPigChange pig=new LPigChange();
		    pig.setUserId(userId);
		    pig.setAmount(pigNum);
		    pig.setFlowType(1);
		    pig.setChangedType(ConstantUtil.PIG_CHANGED_TYPE_1);
		    pig.setChangedTime(new Date().getTime());
		    pig.setPigBalance(user.getPigCoin()+pigNum);
		    lPigChangeDao.insert(pig);
		    mUserInfoDao.updatecPigAdd(pigNum, userId);
	}
	
	//活跃奖励消息推送
	@Override
	public void rechargeRewardPush(String userId,String num,String unit){
		AppNewsInform appNewsInform=new AppNewsInform();
		appNewsInform.setUserId(userId);
		appNewsInform.setInformTitle(InformConstant.RECHARGE_VIP_TITLE);
		appNewsInform.setInformContent(String.format(InformConstant.CONTINUED_VIP_ACTIVE_CONTENT,num,unit));
		appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
		appNewsInformService.addPush(appNewsInform);
	}
	@Override
	public Result receiveActiveNews(String userId, Integer vipId) {
		Result result = new Result();
		if(lActiveGoldLogDao.selectDayNews(userId,vipId)!=0) {
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.TODAY_RECEIVE.getStatusCode());
			result.setMessage(RespCodeState.TODAY_RECEIVE.getMessage());
			return result;//用户已领取过活跃金
		}
		MVipInfo mVipInfo=mVipInfoDao.selectUserVip(userId, vipId);
		if(StringUtil.isNullOrEmpty(mVipInfo)) {
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.USER_NO_VIP.getStatusCode());
			result.setMessage(RespCodeState.USER_NO_VIP.getMessage());
			return result;//用户未开通该vip
		}
		if(mVipInfo.getRewardDay().intValue()!=-1) {
			LUserVip userVip=lUserVipDao.selectUserVip(userId, vipId);
			int days=userVip.getSurplusDay()+mVipInfo.getRewardDay();
			if(days<mVipInfo.getUseDay()) {
				result.setStatusCode(RespCodeState.VIP_REWARD_EXPIRE.getStatusCode());
				result.setMessage(RespCodeState.VIP_REWARD_EXPIRE.getMessage());
				return result;//奖励已过期
			}
		}
		Map<String,Object> date=new HashMap<String,Object>();
		if(mVipInfo.getIsTask().intValue()==1) {
			LUserGameTask lUserGameTask=lUserGameTaskDao.selectOne(userId, mVipInfo.getId(),1);
			if(StringUtil.isNullOrEmpty(lUserGameTask) || lUserGameTask.getState().intValue()==1) {//未完成任务
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
				result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
			}else {
				LActiveGoldLog activeLog=new LActiveGoldLog();
				activeLog.setUserId(userId);
				activeLog.setVipId(vipId);
		        activeLog.setActiveCoin(mVipInfo.getEverydayActiveCoin());
		        activeLog.setActivePig(mVipInfo.getEverydayActivePig());
		        activeLog.setCreatorTime(new Date().getTime());
		        activeLog.setDays(1l);
		        
		        Map<String,Object> jmsData=new HashMap<>();
				jmsData.put("coin", mVipInfo.getEverydayActiveCoin());
				jmsData.put("pig", mVipInfo.getEverydayActivePig());
				jmsData.put("activeLog", activeLog);
				jmsData.put("userId", userId);
				jmsData.put("vipName", mVipInfo.getName());
				jmsProducer.dayActive(Type.DAY_ACTIVE, jmsData);
				
				date.put("coin", mVipInfo.getEverydayActiveCoin());
				date.put("pig", mVipInfo.getEverydayActivePig());
				date.put("maxDay", 1);
				result.setData(date);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
			
		}else {
			LUserVip overdueVip=null;
			LActiveGoldLog activeLog=null;
			Map<String,Object> map=null;
			long coin=0;
			long pig=0;
			String currentTime=DateUtils.getMillisecondTime(new Date().getTime());//当前时间年月日
			String vipCreatorTime=null;//vip创建时间年月日
			String lastTime=null;//最后一次领取奖励时间年月日
			long maxDay=0;
			LUserVip userVip=lUserVipDao.selectUserVip(userId, vipId);
			LActiveGoldLog active=lActiveGoldLogDao.selectTimeMax(userId, userVip.getVipId());//获取最后一次领取记录
			overdueVip=lUserVipDao.selectOverdue(userId, userVip.getVipId());//获取最后一次过期的vip
			if(StringUtil.isNullOrEmpty(overdueVip)) {//第一次购买vip
				if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
					vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
					map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
					coin=coin+Long.parseLong(map.get("coin").toString());
					pig=pig+Long.parseLong(map.get("pig").toString());
					maxDay=Long.parseLong(map.get("maxDay").toString());
					activeLog=(LActiveGoldLog)map.get("activeLog");
					
				}else {
					lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
					map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
					coin=coin+Long.parseLong(map.get("coin").toString());
					pig=pig+Long.parseLong(map.get("pig").toString());
					maxDay=Long.parseLong(map.get("maxDay").toString());
					activeLog=(LActiveGoldLog)map.get("activeLog");
					
				}
			}else {
				if(overdueVip.getExpireTime().equals(vipCreatorTime)) {//连续购买
					if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
						vipCreatorTime=DateUtils.getMillisecondTime(overdueVip.getCreateTime());
						map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						activeLog=(LActiveGoldLog)map.get("activeLog");
					}else {
						lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
						map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						activeLog=(LActiveGoldLog)map.get("activeLog");
					}
				}else {//非连续购买
					if(StringUtil.isNullOrEmpty(active)) {//第一次领取活跃金
						vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
						map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
						coin=coin+Long.parseLong(map.get("coin").toString());
						pig=pig+Long.parseLong(map.get("pig").toString());
						maxDay=Long.parseLong(map.get("maxDay").toString());
						activeLog=(LActiveGoldLog)map.get("activeLog");
					}else {
						if(active.getCreatorTime()>userVip.getCreateTime()) {//最后一领取时间在最新vip开通之后
							lastTime=DateUtils.getMillisecondTime(active.getCreatorTime());
							map=calculationActive(userId,lastTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),2);
							coin=coin+Long.parseLong(map.get("coin").toString());
							pig=pig+Long.parseLong(map.get("pig").toString());
							maxDay=Long.parseLong(map.get("maxDay").toString());
							activeLog=(LActiveGoldLog)map.get("activeLog");
						}else {//最后一领取时间在最新vip开通之前
							vipCreatorTime=DateUtils.getMillisecondTime(userVip.getCreateTime());
							map=calculationActive(userId,vipCreatorTime,currentTime,maxDay,mVipInfo,userVip.getVipId(),1);
							coin=coin+Long.parseLong(map.get("coin").toString());
							pig=pig+Long.parseLong(map.get("pig").toString());
							maxDay=Long.parseLong(map.get("maxDay").toString());
							activeLog=(LActiveGoldLog)map.get("activeLog");
						}
					}
				}
			}
			Map<String,Object> jmsData=new HashMap<>();
			jmsData.put("coin", coin);
			jmsData.put("pig", pig);
			jmsData.put("activeLog", activeLog);
			jmsData.put("userId", userId);
			jmsData.put("vipName", mVipInfo.getName());
			jmsProducer.dayActive(Type.DAY_ACTIVE, jmsData);
			
			date.put("coin", coin);
			date.put("pig", pig);
			date.put("maxDay", maxDay);
			result.setData(date);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}
		return result;
	}
	@Override
	public Map<String,Object> recommendGameVip(int ptype,String userId, int vipId) {
		Map<String,Object> date=new HashMap<String,Object>();
		MVipInfo mVipInfo=mVipInfoDao.selectUserVip(userId, vipId);
		if(StringUtil.isNullOrEmpty(mVipInfo)) {
			date.put("res", 2);
			date.put("tpGame", null);
			return date;//用户未开通该vip
		}
		LUserGameTask lUserGameTask=lUserGameTaskDao.selectOne(userId, mVipInfo.getId(),1);
		if(!StringUtil.isNullOrEmpty(lUserGameTask) && lUserGameTask.getState().intValue()==2) {
			date.put("res", 3);//
			date.put("tpGame", null);
			return date;//任务已完成
		}
		TPGame tpGame = new TPGame();
		List<TPGame> tpGames = tpGameDao.recommendGameList(ptype,userId);
		
		Random r=new Random();
		int index=r.nextInt(tpGames.size());
        tpGame=tpGames.get(index);
        
		date.put("res", 1);
		date.put("tpGame", tpGame);
		return date;
	}
}
