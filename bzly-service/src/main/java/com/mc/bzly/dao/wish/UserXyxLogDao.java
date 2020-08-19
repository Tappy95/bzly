package com.mc.bzly.dao.wish;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.wish.UserXyxLog;

@Mapper
public interface UserXyxLogDao {

	void insert(UserXyxLog userXyxLog);
	
	List<UserXyxLog> selectListF(UserXyxLog userXyxLog);
	
	long selectListFCount(UserXyxLog userXyxLog);

	List<UserXyxLog> selectListByUserType(@Param("userId")String userId, @Param("changedType")int i);
}
