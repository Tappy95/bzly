package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserBQ;

@Mapper
public interface LUserBQDao {

	void insert(LUserBQ lUserBQ);
	
	void update(LUserBQ lUserBQ);
	
	LUserBQ selectByUserId(@Param("userId") String userId,@Param("bqType")Integer bqType);
}
