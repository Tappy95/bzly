package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MUserAddress;

@Mapper
public interface MUserAddressDao {
	
	void insert(MUserAddress mUserAddress);
	
	void update(MUserAddress mUserAddress);
	
	void delete(Integer addressId);
	
	Map<String,Object> selectOne(Integer addressId);
	
	List<MUserAddress> selectList(MUserAddress mUserAddress);
	/**
	 * 获取用户地址列表
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> selectUserList(String userId);
	/**
	 * 修改用户地址为非默认地址
	 * @param userId
	 * @return
	 */
	Integer updateIsDefault(String userId);
	
	MUserAddress selectUserInfo(@Param("userId")String userId,@Param("addressId")Integer addressId);
}
