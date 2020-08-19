package com.mc.bzly.impl.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LBalanceChangeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LBalanceChange;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.LBalanceChangeService;

@Service(interfaceClass = LBalanceChangeService.class,version = WebConfig.dubboServiceVersion)
public class LBalanceChangeServiceImpl implements LBalanceChangeService {

	@Autowired
	private LBalanceChangeDao lBalanceChangeDao;
	
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Transactional
	@Override
	public int add(LBalanceChange lBalanceChange) {
		lBalanceChangeDao.insert(lBalanceChange);
		return 1;
	} 

	@Transactional
	@Override
	public int modify(LBalanceChange lBalanceChange) throws Exception {
		LBalanceChange old = lBalanceChangeDao.selectOne(lBalanceChange.getLogId());
		long now = new Date().getTime();
		lBalanceChange.setReviewTime(now);
		lBalanceChangeDao.update(lBalanceChange);
		if(lBalanceChange.getIsAuditing().intValue() == 3){ // 标识拒绝，则需要把金额加上去
			// 返回用户的账户余额
			MUserInfo mUserInfo = mUserInfoDao.selectOne(old.getUserId());
			mUserInfo.setBalance(mUserInfo.getBalance().add(new BigDecimal(old.getAmount())));
			mUserInfo.setUpdateTime(now);
			mUserInfoDao.update(mUserInfo);
		}
		return 1;
	}

	@Override
	public LBalanceChange queryOne(Integer logId) {
		return lBalanceChangeDao.selectOne(logId);
	}

	@Override
	public PageInfo<LBalanceChange> queryList(LBalanceChange lBalanceChange) {
		PageHelper.startPage(lBalanceChange.getPageNum(), lBalanceChange.getPageSize());
		List<LBalanceChange> lBalanceChangeList = lBalanceChangeDao.selectList(lBalanceChange);
		return new PageInfo<>(lBalanceChangeList);
	}

	/*@Transactional
	@Override
	public Result withdrawalsApply(LBalanceChange lBalanceChange) {
		Result result = new Result();
		long now = new Date().getTime();
		MUserInfo mUserInfo = mUserInfoDao.selectOne(lBalanceChange.getUserId());
		double amount = lBalanceChange.getAmount();
		boolean flag = true; // 是否是首次提现
		// 判断是否首次提现
		List<LBalanceChange> lBalanceChanges = lBalanceChangeDao.selectRecord(lBalanceChange.getUserId(),ConstantUtil.BALANCE_CHANGED_TYPE_2);
		if(lBalanceChanges.size() > 0){
			flag = false;
			// 设置提现金额为字典配置
			BigDecimal withdrawals_limit = new BigDecimal(pDictionaryDao.selectByName("withdrawals_limit").getDicValue());
			if(withdrawals_limit.compareTo(new BigDecimal(amount)) > 0){
				result.setStatusCode(RespCodeState.WITHDRAWALS_LIMIT.getStatusCode());
				result.setMessage(RespCodeState.WITHDRAWALS_LIMIT.getMessage());
				return result;
			}
		}else{
			// 设置首次提现金额为字典配置
			BigDecimal first_withdrawals_limit = new BigDecimal(pDictionaryDao.selectByName("first_withdrawals_limit").getDicValue());
			if(first_withdrawals_limit.compareTo(new BigDecimal(amount)) > 0){
				result.setStatusCode(RespCodeState.WITHDRAWALS_LIMIT.getStatusCode());
				result.setMessage(RespCodeState.WITHDRAWALS_LIMIT.getMessage());
				return result;
			}
		}
		if(mUserInfo.getBalance().compareTo(new BigDecimal(amount)) >= 0){
			lBalanceChange.setChangedType(ConstantUtil.BALANCE_CHANGED_TYPE_2);
			lBalanceChange.setIsAuditing(1);
			lBalanceChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
			lBalanceChange.setChangedTime(now);
			lBalanceChangeDao.insert(lBalanceChange);
			// 修改账户余额
			mUserInfo.setBalance(mUserInfo.getBalance().subtract(new BigDecimal(amount)));
			mUserInfo.setUpdateTime(now);
			mUserInfoDao.update(mUserInfo);
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			// 首次提现后续步骤让mq去完成
			if(flag){
				jmsProducer.sendMessage(Type.USER_FIRST_WITHDRAWALS, lBalanceChange.getUserId());
			}
			return result;
		}else{
			// 使用金币进行提现 
			// 余额不足
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			return result;
		}
	}*/

}
