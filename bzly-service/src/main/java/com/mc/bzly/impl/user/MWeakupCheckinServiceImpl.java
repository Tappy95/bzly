package com.mc.bzly.impl.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.activity.MActivityInfoDao;
import com.mc.bzly.dao.activity.MActivityLogDao;
import com.mc.bzly.dao.user.LBalanceChangeDao;
import com.mc.bzly.dao.user.LUserCheckinDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.MWeakupCheckinDao;
import com.mc.bzly.model.activity.MActivityInfo;
import com.mc.bzly.model.activity.MActivityLog;
import com.mc.bzly.model.user.LBalanceChange;
import com.mc.bzly.model.user.LUserCheckin;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.MWeakupCheckin;
import com.mc.bzly.service.user.MWeakupCheckinService;

@Service(interfaceClass = MWeakupCheckinService.class,version =WebConfig.dubboServiceVersion)
public class MWeakupCheckinServiceImpl implements MWeakupCheckinService {

	@Autowired
	private MWeakupCheckinDao mWeakupCheckinDao;
	
	@Autowired
	private MActivityInfoDao mActivityInfoDao;
	
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private MActivityLogDao mActivityLogDao;

	@Autowired
	private LBalanceChangeDao lBalanceChangeDao;
	
	@Autowired
	private LUserCheckinDao lUserCheckinDao;
	
	@Transactional
	@Override
	public Result add(MWeakupCheckin mWeakupCheckin) throws Exception {
		Result result = new Result();
		String cycNum = DateUtil.getShortCurrentDate();
		long dateTime = new Date().getTime();
		/**
		 * 1.判断账户余额
		 * 2.判断是否已经参与过该打卡
		 * 3.判断参加时间是不是在活动范围内
		 * 4.添加参与打卡记录
		 * 5.修改打卡活动记录的参与人数和参与金额
		 * 6.修改账户余额
		 * 7.添加用户余额变动记录
		 * 8.添加/修改用户打卡统计表
		 */
		MUserInfo userInfo = mUserInfoDao.selectOne(mWeakupCheckin.getCheckinUser());
		double balance = userInfo.getBalance().doubleValue();
		double investment = mWeakupCheckin.getInvestment().doubleValue();
		if(balance < investment){
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			return result;
		}
		List<MWeakupCheckin> lists = mWeakupCheckinDao.selectList(mWeakupCheckin);
		if(lists != null && lists.size() > 0){
			result.setStatusCode(RespCodeState.HAS_JOIN_ACTIVITY.getStatusCode());
			result.setMessage(RespCodeState.HAS_JOIN_ACTIVITY.getMessage());
			return result;
		}
		MActivityLog mActivityLog = mActivityLogDao.selectOne(mWeakupCheckin.getLogId());
		if(cycNum.equals(mActivityLog.getCycleNum())){
			result.setStatusCode(RespCodeState.NOT_IN_ACTIVITY_TIMING.getStatusCode());
			result.setMessage(RespCodeState.NOT_IN_ACTIVITY_TIMING.getMessage());
			return result;
		}
		// 添加参与打卡记录
		mWeakupCheckin.setCheckinState(0);
		mWeakupCheckin.setRewardAmount(0d);
		mWeakupCheckinDao.insert(mWeakupCheckin);
		
		// 修改打卡活动记录的参与人数和参与金额
		mActivityLog.setAllocationAmount(mActivityLog.getAllocationAmount() + mWeakupCheckin.getInvestment());
		mActivityLog.setParticipantsNum(mActivityLog.getParticipantsNum() +1);
		mActivityLog.setParticipantsAmount(mActivityLog.getParticipantsAmount() + mWeakupCheckin.getInvestment());
		mActivityLogDao.update(mActivityLog);
		
		// 修改账户余额
		userInfo.setBalance(userInfo.getBalance().subtract(new BigDecimal(mWeakupCheckin.getInvestment())));
		userInfo.setUpdateTime(dateTime);
		mUserInfoDao.update(userInfo);
		
		// 添加用户余额变动记录
		LBalanceChange lBalanceChange = new LBalanceChange();
		lBalanceChange.setUserId(mWeakupCheckin.getCheckinUser());
		lBalanceChange.setAmount(mWeakupCheckin.getInvestment());
		lBalanceChange.setFlowType(2);
		lBalanceChange.setIsAuditing(0);
		lBalanceChange.setChangedType(4);
		lBalanceChange.setChangedTime(dateTime);
		lBalanceChangeDao.insert(lBalanceChange);
		
		// 添加/修改用户打卡统计表
		LUserCheckin lUserCheckin = lUserCheckinDao.selectOne(mWeakupCheckin.getCheckinUser());
		if(lUserCheckin != null){ // 修改参与金额
			lUserCheckin.setTotalInvestment(lUserCheckin.getTotalInvestment() + mWeakupCheckin.getInvestment());
			lUserCheckinDao.update(lUserCheckin);
		}else{ // 插入新的记录
			lUserCheckin = new LUserCheckin();
			lUserCheckin.setUserId(mWeakupCheckin.getCheckinUser());
			lUserCheckin.setTotalInvestment(mWeakupCheckin.getInvestment());
			lUserCheckin.setTotalFailure(0);
			lUserCheckin.setTotalSuccess(0);
			lUserCheckin.setTotalReward(0.00);
			lUserCheckinDao.insert(lUserCheckin);
		}
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.JOIN_ACTIVITY_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.JOIN_ACTIVITY_SUCCESS.getMessage());
		return result;
	}

	@Transactional
	@Override
	public int modify(MWeakupCheckin mWeakupCheckin) throws Exception {
		mWeakupCheckinDao.update(mWeakupCheckin);
		return 1;
	}

	@Override
	public MWeakupCheckin queryOne(Integer chkId) {
		return mWeakupCheckinDao.selectOne(chkId);
	}

	@Override
	public PageInfo<MWeakupCheckin> queryList(MWeakupCheckin mWeakupCheckin) {
		PageHelper.startPage(mWeakupCheckin.getPageNum(), mWeakupCheckin.getPageSize());
		List<MWeakupCheckin> mWeakupCheckinList = mWeakupCheckinDao.selectList(mWeakupCheckin);
		return new PageInfo<>(mWeakupCheckinList);
	}

	/**
	 * 用户打卡
	 */
	@Transactional
	@Override
	public Result checkin(MWeakupCheckin mWeakupCheckin) throws Exception {
		Result result = new Result();
		String cycNum = DateUtil.getShortCurrentDate();
		List<MWeakupCheckin> lists = mWeakupCheckinDao.selectList(mWeakupCheckin);
		if(lists != null && lists.size() > 0){
			MActivityInfo info = mActivityInfoDao.selectByActivityType(1);
			MActivityLog mActivityLog = mActivityLogDao.selectOne(mWeakupCheckin.getLogId());
			String startTime = DateUtil.getCurrentDate().substring(0,10) + " " + info.getStartTime();
			String stopTime = DateUtil.getCurrentDate().substring(0,10) + " " + info.getStopTime();
			Date date = new Date();
			Date startDate = DateUtil.praseStringDate(startTime);
			Date stopDate = DateUtil.praseStringDate(stopTime);
			mWeakupCheckin = lists.get(0);
			if(!cycNum.equals(mActivityLog.getCycleNum())){ // 前一天打卡
				result.setStatusCode(RespCodeState.NOT_AT_CHECKIN_TIME.getStatusCode());
				result.setMessage(RespCodeState.NOT_AT_CHECKIN_TIME.getMessage());
				return result;
			}
			if((date.getTime() < startDate.getTime())){ // 提前打卡
				result.setStatusCode(RespCodeState.NOT_AT_CHECKIN_TIME.getStatusCode());
				result.setMessage(RespCodeState.NOT_AT_CHECKIN_TIME.getMessage());
				return result;
			}
			if(mWeakupCheckin.getCheckinState() != null){
				if(mWeakupCheckin.getCheckinState().intValue() > 0){
					result.setStatusCode(RespCodeState.USER_HAS_CHECKIN.getStatusCode());
					result.setMessage(RespCodeState.USER_HAS_CHECKIN.getMessage());
					return result;
				}
			}
			if(date.getTime() > stopDate.getTime()){ // 超过打卡时间
				// 修改打卡记录未失败
				mWeakupCheckin.setCheckinTime(date.getTime());
				mWeakupCheckin.setCheckinState(2);
				mWeakupCheckin.setRewardAmount(0.00d);
				mWeakupCheckinDao.update(mWeakupCheckin);
				// 统计表中增加一次失败的次数
				LUserCheckin lUserCheckin = lUserCheckinDao.selectOne(mWeakupCheckin.getCheckinUser());
				lUserCheckin.setTotalFailure(lUserCheckin.getTotalFailure() + 1);
				lUserCheckinDao.update(lUserCheckin);
				
				result.setStatusCode(RespCodeState.USER_CHECKIN_FAILURE.getStatusCode());
				result.setMessage(RespCodeState.USER_CHECKIN_FAILURE.getMessage());
				return result;
			}
			mWeakupCheckin.setCheckinTime(date.getTime());
			mWeakupCheckin.setCheckinState(1);
			mWeakupCheckinDao.update(mWeakupCheckin);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.USER_CHECKIN_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.USER_CHECKIN_SUCCESS.getMessage());
			return result;
		}else{ // 未参加活动
			result.setStatusCode(RespCodeState.NO_JOIN_ACTIVITY.getStatusCode());
			result.setMessage(RespCodeState.NO_JOIN_ACTIVITY.getMessage());
			return result;
		}
	}

}
