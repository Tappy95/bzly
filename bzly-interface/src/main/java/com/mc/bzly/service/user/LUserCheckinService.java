package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LUserCheckin;

public interface LUserCheckinService {

	int add(LUserCheckin lUserCheckin) throws Exception;
	
	int modify(LUserCheckin lUserCheckin) throws Exception;
	
	LUserCheckin queryOne(String userId);
	
	PageInfo<LUserCheckin> queryList(LUserCheckin lUserCheckin);
}
