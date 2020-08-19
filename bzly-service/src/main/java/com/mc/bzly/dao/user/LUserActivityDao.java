package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserActivity;

@Mapper
public interface LUserActivityDao {
	
	LUserActivity selectByDate(@Param("createDate")String createDate,@Param("userId")String userId);
	
	void updateScore(LUserActivity lUserActivity);
}
