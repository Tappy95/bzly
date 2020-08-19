package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LUserCashtoutiao;

@Mapper
public interface LUserCashtoutiaoDao {
	
	void insert(LUserCashtoutiao lUserCashtoutiao);
}
