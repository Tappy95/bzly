package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LBalanceChange;

public interface LBalanceChangeService {

	int add(LBalanceChange lBalanceChange) throws Exception;
	
	int modify(LBalanceChange lBalanceChange) throws Exception;
	
	LBalanceChange queryOne(Integer logId);
	
	PageInfo<LBalanceChange> queryList(LBalanceChange lBalanceChange);

}
