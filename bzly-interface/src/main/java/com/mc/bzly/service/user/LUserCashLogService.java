package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.mc.bzly.model.user.LUserCashLog;

public interface LUserCashLogService {

	Map<String, Object> selectList(LUserCashLog lUserCashLog);
	
	List<LUserCashLog> excl(LUserCashLog lUserCashLog);
}
