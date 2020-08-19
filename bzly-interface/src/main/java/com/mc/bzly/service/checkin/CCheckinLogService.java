package com.mc.bzly.service.checkin;

import java.text.ParseException;
import java.util.Map;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.checkin.CCheckinLog;

public interface CCheckinLogService {

	Map<String,Object> checkin(String userId) throws ParseException;
	
	Result enroll(String userId) throws ParseException;
	
	Map<String,Object> clockin(String userId) throws ParseException;
	
	Map<String,Object> userList(CCheckinLog cCheckinLog);
	
	Map<String,Object> clockinCount(String userId);
	
	Map<String,Object> page(CCheckinLog cCheckinLog);
}
