package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.MUserAddress;

public interface MUserAddressService {

	int add(MUserAddress mUserAddress) throws Exception;
	
	int modify(MUserAddress mUserAddress) throws Exception;
	
	int remove(Integer addressId) throws Exception;
	
	Map<String,Object> queryOne(Integer addressId);
	
	PageInfo<MUserAddress> queryList(MUserAddress mUserAddress);
	/**
	 * 获取用户地址列表
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> selectUserList(String userId);
}
