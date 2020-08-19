package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LCashGameTask;
@Mapper
public interface LCashGameTaskDao {
	
	int add(LCashGameTask lCashGameTask);
	
	LCashGameTask selectGameId(@Param("userId")String userId,@Param("gameId")Integer gameId,@Param("cashId")Integer cashId);
	
	int selectCount(Integer cashId);

}
