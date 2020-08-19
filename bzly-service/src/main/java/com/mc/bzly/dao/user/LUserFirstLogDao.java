package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserFirstLog;

@Mapper
public interface LUserFirstLogDao {
	
	Integer insert(LUserFirstLog lUserFirstLog);
	
	LUserFirstLog selectOne(@Param("userId")String userId);

}
