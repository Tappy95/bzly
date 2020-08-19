package com.mc.bzly.dao.wish;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.wish.UserFhxLog;

@Mapper
public interface UserFhxLogDao {

	void insert(UserFhxLog userFhxLog);
	
	List<UserFhxLog> selectListF(UserFhxLog userFhxLog);
	
	long selectListFCount(UserFhxLog userFhxLog);

}
