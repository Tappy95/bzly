package com.mc.bzly.service.user;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.MUserApprentice;

public interface MUserApprenticeService {

	int add(MUserApprentice mUserApprentice) throws Exception;
	
	int modify(MUserApprentice mUserApprentice) throws Exception;
	
	int remove(Integer addressId) throws Exception;
	
	MUserApprentice queryOne(Integer addressId);
	
	PageInfo<MUserApprentice> queryList(MUserApprentice mUserApprentice);
	 
	PageInfo<MUserApprentice> selectPage(MUserApprentice mUserApprentice);
	
	Map<String,Object> wishMentorCount(String userId);
	
	Map<String,Object> wishRewardPage(String userId,int pageNum,int pageSize);
	
	Map<String,Object> wishNumberPage(String userId,int pageNum,int pageSize);
}
