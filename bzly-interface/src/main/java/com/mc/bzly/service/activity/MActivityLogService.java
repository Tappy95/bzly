package com.mc.bzly.service.activity;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.activity.MActivityLog;

public interface MActivityLogService {

	int add(MActivityLog mActivityLog) throws Exception;
	
	int modify(MActivityLog mActivityLog) throws Exception;
	
	int remove(Integer lId) throws Exception;
	
	MActivityLog queryOne(Integer lId);
	
	PageInfo<MActivityLog> queryList(MActivityLog mActivityLog);

	Map<String, Object> queryCheckinHome(String userId);
}
