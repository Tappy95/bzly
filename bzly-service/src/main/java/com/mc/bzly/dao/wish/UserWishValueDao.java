package com.mc.bzly.dao.wish;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.wish.UserWishValue;

@Mapper
public interface UserWishValueDao {
	
	int insert(UserWishValue userWishValue);
	
	int updateAdd(UserWishValue userWishValue);
	
	UserWishValue selectOne(String userId);

	int update(UserWishValue userWishValue);
}
