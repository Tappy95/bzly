package com.mc.bzly.impl.checkin;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.checkin.CCheckinContinuityDao;
import com.mc.bzly.dao.checkin.CCheckinLogDao;
import com.mc.bzly.dao.checkin.CCheckinResultDao;
import com.mc.bzly.dao.user.LUserBQDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.checkin.CCheckinContinuity;
import com.mc.bzly.model.checkin.CCheckinLog;
import com.mc.bzly.model.egg.EGoleEggOrder;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.checkin.CCheckinLogService;
import com.mc.bzly.service.jms.JMSProducer;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass=CCheckinLogService.class,version=WebConfig.dubboServiceVersion)
public class CCheckinLogServiceImpl implements CCheckinLogService{
	
	@Autowired
	private CCheckinLogDao cCheckinLogDao;
	@Autowired
	private CCheckinResultDao cCheckinResultDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LUserBQDao lUserBQDao;
	@Autowired
	private CCheckinContinuityDao cCheckinContinuityDao;
	@Autowired
	private JMSProducer jmsProducer;

	@Override
	public Map<String,Object> checkin(String userId) throws ParseException {
		Map<String,Object> data=new HashMap<String, Object>();
		long time=new Date().getTime();//当前时间
		long zeroH0M=DateUtil.timeStamp(0, 0);//获取00点00分的时间戳
		long sixH30M=DateUtil.timeStamp(6, 30);//获取6点30分的时间戳
		long eightH30M=DateUtil.timeStamp(8, 30);//获取8点30分的时间戳
		long elevenH0M=DateUtil.timeStamp(11, 0);//获取11点00分的时间戳
		//long twelveH0M=DateUtil.timeStamp(12, 0);//获取12点00分的时间戳
		CCheckinLog cCheckinLog1=cCheckinLogDao.selectCreate(userId);//今日报名打卡记录
		if(!StringUtil.isNullOrEmpty(cCheckinLog1)) {//计算明天6点半打卡倒计时
			Map<String,Object> count=cCheckinLogDao.selectCreateCount();
			data.put("checkinType", 1);//已报名
			data.put("countdown", cCheckinLog1.getCheckinTime()-time);//打卡倒计时
			data.put("dates", "明日");
			data.put("number", count.get("number"));//人数
			data.put("coin", count.get("coin"));//奖金数
		}else {//今日报名记录为空
			CCheckinLog cCheckinLog2=cCheckinLogDao.selectCheckin(userId);//今日打卡记录
			if(!StringUtil.isNullOrEmpty(cCheckinLog2)) {
				if(cCheckinLog2.getState()==2) {//今日打卡成功，可以报名下一次打卡
					data.put("checkinType", 2);//报名明天打卡
					
					Map<String,Object> count=cCheckinLogDao.selectCreateCount();
					data.put("dates", "明日");
					data.put("number", count.get("number"));//人数
					data.put("coin", count.get("coin"));//奖金数
				}else if(cCheckinLog2.getState()==1){
					
					Map<String,Object> count=cCheckinLogDao.selectCheckinCount();
					data.put("dates", "今日");
					data.put("number", count.get("number"));//人数
					data.put("coin", count.get("coin"));//奖金数
					
					if(time>=zeroH0M && time<sixH30M) {
						data.put("checkinType", 3);//已报名，当前时间不能打卡
						data.put("countdown", cCheckinLog2.getCheckinTime()-time);//打卡倒计时
					}else if(time>=sixH30M && time<=eightH30M) {
						data.put("checkinType", 4);//已报名，当前时间可以打卡
					}else if(time>eightH30M && time<=elevenH0M) {
						data.put("checkinType", 5);//已报名，当前时间可以补卡
					}
				}else {
					if(cCheckinLog2.getIsTips().intValue()==1) {
						data.put("checkinType", 6);//已报名，今日打卡失败,有弹框,可报名下次打卡
						cCheckinLogDao.updateTips(cCheckinLog2.getId());
					}else {
						data.put("checkinType", 7);//已报名，今日打卡失败,没弹框,可报名下次打卡		
					}
					Map<String,Object> count=cCheckinLogDao.selectCreateCount();
					data.put("dates", "明日");
					data.put("number", count.get("number"));//人数
					data.put("coin", count.get("coin"));//奖金数
				}
			}else {
				if(time>=zeroH0M && time<sixH30M) {
					
					Map<String,Object> count=cCheckinLogDao.selectCheckinCount();
					data.put("dates", "今日");
					data.put("number", count.get("number"));//人数
					data.put("coin", count.get("coin"));//奖金数

					data.put("checkinType", 8);//当前时间不能报名
				}else {
					
					Map<String,Object> count=cCheckinLogDao.selectCreateCount();
					data.put("dates", "明日");
					data.put("number", count.get("number"));//人数
					data.put("coin", count.get("coin"));//奖金数
					
					data.put("checkinType", 2);//报名明天打卡
				}
			}
		}
		data.put("checkinResult", cCheckinResultDao.selectOne());
		return data;
	}

	@Override
	public Result enroll(String userId) throws ParseException {
		Result result = new Result();
		long time=new Date().getTime();//当前时间
		long zeroH0M=DateUtil.timeStamp(0, 0);//获取00点00分的时间戳
		long sixH30M=DateUtil.timeStamp(6, 30);//获取6点30分的时间戳
		if(time>=zeroH0M && time<=sixH30M) {
			result.setStatusCode(RespCodeState.NOW_NO_CHECKIN.getStatusCode());
			result.setMessage(RespCodeState.NOW_NO_CHECKIN.getMessage());
			return result;
		}
		CCheckinLog cCheckinLog1=cCheckinLogDao.selectCreate(userId);//今日报名打卡记录
		if(!StringUtil.isNullOrEmpty(cCheckinLog1)) {
			result.setStatusCode(RespCodeState.HAS_JOIN_ACTIVITY.getStatusCode());
			result.setMessage(RespCodeState.HAS_JOIN_ACTIVITY.getMessage());
			return result;
		}
		CCheckinLog cCheckinLog2=cCheckinLogDao.selectCheckin(userId);//今日打卡记录
		if(!StringUtil.isNullOrEmpty(cCheckinLog2) && cCheckinLog2.getState()==1) {
			result.setStatusCode(RespCodeState.TODAY_NO_CHECKIN.getStatusCode());
			result.setMessage(RespCodeState.TODAY_NO_CHECKIN.getMessage());
			return result;
		}
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		if(mUserInfo.getCoin().intValue()<10000) {
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			return result;
		}
		CCheckinLog cCheckinLog=new CCheckinLog();
		cCheckinLog.setUserId(userId);
		cCheckinLog.setUserType(1);
		cCheckinLog.setPayCoin(10000l);
		cCheckinLog.setState(1);
		cCheckinLog.setCreateTime(new Date().getTime());
		cCheckinLog.setCheckinTime(sixH30M+86400000);
		cCheckinLog.setIsTips(1);
		cCheckinLog.setIsCoupon(1);
		
		jmsProducer.enroll(Type.ENROLL, cCheckinLog);
		
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}
    @Transactional
	@Override
	public Map<String, Object> clockin(String userId) throws ParseException {
		Map<String,Object> data=new HashMap<>();
		long time=new Date().getTime();//当前时间
		long sixH30M=DateUtil.timeStamp(6, 30);//获取6点30分的时间戳
		long eightH30M=DateUtil.timeStamp(8, 30);//获取8点30分的时间戳
		long elevenH0M=DateUtil.timeStamp(11, 0);//获取11点00分的时间戳
		if(time<sixH30M || time>elevenH0M) {
			data.put("res", 1);//未到打卡时间
			return data;
		}
		CCheckinLog cCheckinLog=cCheckinLogDao.selectCheckin(userId);//今日打卡记录
		if(StringUtil.isNullOrEmpty(cCheckinLog)) {
			data.put("res", 2);//你未参与今日的打卡活动
			return data;
		}
		if(cCheckinLog.getState().intValue()!=1) {
			data.put("res", 3);//请勿重复参与打卡
			return data;
		}
		CCheckinLog checkinLogLast=cCheckinLogDao.selectLast(userId);
		if(time>=sixH30M && time<=eightH30M) {//正常打卡
	        if(StringUtil.isNullOrEmpty(checkinLogLast)) {
	        	cCheckinLog.setContinuityDays(1);
	        }else {
	        	cCheckinLog.setLastTime(checkinLogLast.getUserTime());
	        	if(StringUtil.isNullOrEmpty(checkinLogLast.getContinuityDays())) {
	        		cCheckinLog.setContinuityDays(1);
	        	}else {
	        		cCheckinLog.setContinuityDays(checkinLogLast.getContinuityDays()+1);	
	        	}
	        }
	        cCheckinLog.setUserTime(time);
	        cCheckinLog.setState(2);
	        cCheckinLog.setIsCoupon(1);
	        cCheckinLogDao.update(cCheckinLog);
	        
	        CCheckinContinuity cCheckinContinuity=cCheckinContinuityDao.selectOne(userId);
	        if(StringUtil.isNullOrEmpty(cCheckinContinuity)) {
	        	cCheckinContinuity=new CCheckinContinuity();
	        	cCheckinContinuity.setUserId(userId);
	        	cCheckinContinuity.setContinuityDays(cCheckinLog.getContinuityDays());
	        	cCheckinContinuity.setUpdateTime(time);
	        	cCheckinContinuityDao.add(cCheckinContinuity);
	        }else {
	        	cCheckinContinuity.setContinuityDays(cCheckinLog.getContinuityDays());
	        	cCheckinContinuity.setUpdateTime(time);
	        	cCheckinContinuityDao.update(cCheckinContinuity);
	        }
	        
	        Map<String,Object> count=cCheckinLogDao.selectCheckinCount();
			data.put("coin", count.get("coin"));//奖金数
	        data.put("res", 4);//打卡成功
	        return data;
		}else {
			LUserBQ bq=lUserBQDao.selectByUserId(userId, 2);
			if(StringUtil.isNullOrEmpty(bq) || bq.getCardCount().intValue()<=0) {
				data.put("res", 5);//没有补签卡，无法补签
			    return data;
		    }else {
		    	if(StringUtil.isNullOrEmpty(checkinLogLast)) {
		        	cCheckinLog.setContinuityDays(1);
		        }else {
		        	cCheckinLog.setLastTime(checkinLogLast.getUserTime());
		        	cCheckinLog.setContinuityDays(checkinLogLast.getContinuityDays()+1);
		        }
		    	cCheckinLog.setUserTime(time);
		        cCheckinLog.setState(2);
		        cCheckinLog.setIsCoupon(2);
		        cCheckinLogDao.update(cCheckinLog);
		        
		        bq.setCardCount(bq.getCardCount()-1);
		        bq.setUpdateTime(time);
		        lUserBQDao.update(bq);
		        
		        CCheckinContinuity cCheckinContinuity=cCheckinContinuityDao.selectOne(userId);
		        if(StringUtil.isNullOrEmpty(cCheckinContinuity)) {
		        	cCheckinContinuity=new CCheckinContinuity();
		        	cCheckinContinuity.setUserId(userId);
		        	cCheckinContinuity.setContinuityDays(cCheckinLog.getContinuityDays());
		        	cCheckinContinuity.setUpdateTime(time);
		        	cCheckinContinuityDao.add(cCheckinContinuity);
		        }else {
		        	cCheckinContinuity.setContinuityDays(cCheckinLog.getContinuityDays());
		        	cCheckinContinuity.setUpdateTime(time);
		        	cCheckinContinuityDao.update(cCheckinContinuity);
		        }
		        
		        Map<String,Object> count=cCheckinLogDao.selectCheckinCount();
				data.put("coin", count.get("coin"));//奖金数
		        data.put("res", 6);//补签成功
		        return data;
		    }
		}
	}

	@Override
	public Map<String, Object> userList(CCheckinLog cCheckinLog) {
		Map<String,Object> data=new HashMap<>();
		cCheckinLog.setPageIndex(cCheckinLog.getPageSize() * (cCheckinLog.getPageNum() - 1));
		List<Map<String,Object>> list = cCheckinLogDao.selectUserList(cCheckinLog);
		int total=cCheckinLogDao.selectUserCount(cCheckinLog);
		data.put("list", list);
		data.put("total", total);
		return data;
	}

	@Override
	public Map<String, Object> clockinCount(String userId) {
		Map<String, Object> data=cCheckinLogDao.selectUserSum(userId);
		CCheckinContinuity cCheckinContinuity=cCheckinContinuityDao.selectOne(userId);
        if(StringUtil.isNullOrEmpty(cCheckinContinuity)) {
        	data.put("days", 0);
        }else {
        	data.put("days", cCheckinContinuity.getContinuityDays());
        }
		return data;
	}

	@Override
	public Map<String, Object> page(CCheckinLog cCheckinLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		cCheckinLog.setPageIndex(cCheckinLog.getPageSize() * (cCheckinLog.getPageNum() - 1));
		List<CCheckinLog> cCheckinLogList = cCheckinLogDao.selectList(cCheckinLog);
		int count=cCheckinLogDao.selectCount(cCheckinLog);
		Map<String,Object> subTotal=cCheckinLogDao.selectSubtotal(cCheckinLog);
		Map<String,Object> total=cCheckinLogDao.selectTotal(cCheckinLog);
		result.put("list", cCheckinLogList);
		result.put("total", count);
		result.put("subPayCoin", subTotal.get("payCoin"));
		result.put("subRewardCoin", subTotal.get("rewardCoin"));
		result.put("payCoin", total.get("payCoin"));
		result.put("rewardCoin", total.get("rewardCoin"));
		return result;
	}

}
