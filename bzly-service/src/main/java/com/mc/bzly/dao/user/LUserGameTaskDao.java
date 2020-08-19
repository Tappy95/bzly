package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserGameTask;

@Mapper
public interface LUserGameTaskDao {
	
	int insert(LUserGameTask lUserGameTask);
	
	LUserGameTask selectOne(@Param("userId")String userId,@Param("vipId")int vipId,@Param("taskType")Integer taskType);
	
	int update(LUserGameTask lUserGameTask);
	
	LUserGameTask selectGameOne(@Param("userId")String userId,@Param("gameId")int gameId,@Param("taskType")int taskType);
	
	List<Map<String,Object>> selectlist(@Param("createTime")String createTime,@Param("accountId")Integer accountId);

	int updateState(@Param("id")Integer id,@Param("state")Integer state);
	
	int updateHide(LUserGameTask lUserGameTask);
	
	LUserGameTask selectInfo(@Param("userId")String userId,@Param("gameId")int gameId);
	
	int selectCashCount(Integer cashId);
	
	LUserGameTask selectGameOne2(@Param("userId")String userId,@Param("gameId")int gameId);
	
	LUserGameTask selectSginOne(@Param("userId")String userId,@Param("taskType")Integer taskType);
	
	LUserGameTask selectCashGameOne(@Param("userId")String userId,@Param("taskType")int taskType,@Param("state")int state);
	
	LUserGameTask selectId(Integer id);
}
