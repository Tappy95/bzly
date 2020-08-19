package com.mc.bzly.service.activity;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.activity.MActivityInfo;

public interface MActivityInfoService {

	int add(MActivityInfo mActivityInfo) throws Exception;
	
	int modify(MActivityInfo mActivityInfo) throws Exception;
	
	int remove(Integer actId) throws Exception;
	
	MActivityInfo queryOne(Integer actId);
	
	PageInfo<MActivityInfo> queryList(MActivityInfo mActivityInfo);
	
	Map<String, Object> queryCheckInRule(String userId);
}
