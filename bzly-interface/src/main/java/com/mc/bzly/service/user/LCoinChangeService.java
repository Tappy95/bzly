package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.MessageList;
import com.mc.bzly.model.user.UserWithdrawals;

public interface LCoinChangeService {
	 
	PageInfo<LCoinChange> queryList(LCoinChange lCoinChange);
	 
	PageInfo<LCoinChange> pageList(LCoinChange lCoinChange);
	
	LCoinChange info(Integer id);
	
	List<MessageList> queryMessageList();
	
	Result withdrawalsApply(LCoinChange lCoinChange,int ptype);
	
	PageInfo<UserWithdrawals> queryWithdrawals(UserWithdrawals userWithdrawals);
	
	UserWithdrawals queryWithdrawalsInfo(Integer id);
	
	int audit(LCoinChange lCoinChange);
	 
	Integer coinExchangePig(long coin,String userId);

	int readZXReward(String userId);

	Map<String, Object> getZXConifg(String userId);
	
	/**
	 * 获取从徒弟那边得到的奖励
	 * @param userId
	 * @return
	 */
	Map<String, Object> getApprenticeReward(String userId);
	
	/**
	 * 获取从徒弟那边得到的奖励明细
	 * @param userId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	PageInfo<LCoinChange> getApprenticeRewardDetail(LCoinChange lCoinChange);
	
	int readNewZXReward(String userId,Long time);
	
	List<Map<String,Object>> exclCoinChange(LCoinChange lCoinChange);

	Map<String, Object> getRewardConifg(String userId, Integer type,String content);

	int readReward(String userId, Long time, Integer type,String content);

	List<MessageList> queryqdzList();
	
	Map<String, Object> selectList(LCoinChange lCoinChange);

	List<MessageList> querykszList();
	
	int cashNum(String userId);
	
	Map<String, Object> xyxRewardConifg(String userId);

	Result getXYXReward(String userId, Long seconds);
	
	Result wishCash(LCoinChange lCoinChange);

	Map<String, Object> listHighVip(String userId, Integer pageSize, Integer pageNum);
}
