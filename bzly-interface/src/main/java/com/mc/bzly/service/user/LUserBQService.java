package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.model.user.LUserBQ;

public interface LUserBQService {
	
	void add(LUserBQ lUserBQ);
	
	void modify(LUserBQ lUserBQ);
	
	int queryCount(String userId);

	int useCard(String userId, int signinId);
	
	Map<String,Object> checkinCount(String userId);
}
