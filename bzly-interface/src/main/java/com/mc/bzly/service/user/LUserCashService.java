package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.LUserCash;

public interface LUserCashService {

	String insert(LUserCash lUserCash);
	
	Map<String,Object> taskInfo(String userId);
	
	Map<String,Object> recommendGameCash(int ptype,String userId,Integer cashId);
	
	Result cashLaunch(String userId,Integer cashId,String ip);
	
	Map<String,Object> selectList(LUserCash lUserCash);
	
	Result updateState(Integer id);
}
