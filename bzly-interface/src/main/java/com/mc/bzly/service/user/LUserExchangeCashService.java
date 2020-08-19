package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.LuserCashGameStatistic;

public interface LUserExchangeCashService {
	
	int update(LUserExchangeCash lUserExchangeCash);
	
	Map<String, Object> selectList(LUserExchangeCash lUserExchangeCash);
	
	int updateLocking(LUserExchangeCash lUserExchangeCash);
	
	int updateLockingList(String ids,String admin);
	
	int relieveLocking(Integer id,String admin);
	
	LUserExchangeCash selectInfo(Integer id);
	
	List<LUserExchangeCash> selectExcl(LUserExchangeCash lUserExchangeCash);
	
	Map<String, Object> selectH5List(LUserExchangeCash lUserExchangeCash);
	
	Map<String,Object> selectCashGame(LuserCashGameStatistic luserCashGameStatistic);
	
	List<Map<String,Object>> channelStatisticExcl(LuserCashGameStatistic luserCashGameStatistic);
}
