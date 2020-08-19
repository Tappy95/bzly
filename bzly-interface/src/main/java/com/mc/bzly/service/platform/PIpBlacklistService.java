package com.mc.bzly.service.platform;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PIpBlacklist;

public interface PIpBlacklistService {

	int add(PIpBlacklist pIpBlacklist) throws Exception;
	
	int modify(PIpBlacklist pIpBlacklist) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PIpBlacklist queryOne(Integer id);
	
	PIpBlacklist queryByIp(String ip);
	
	PageInfo<PIpBlacklist> queryList(PIpBlacklist pIpBlacklist);
}
