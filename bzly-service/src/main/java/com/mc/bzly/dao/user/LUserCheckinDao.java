package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserCheckin;

@Mapper
public interface LUserCheckinDao {
	
	void insert(LUserCheckin lUserCheckin);
	
	void update(LUserCheckin lUserCheckin);
	
	LUserCheckin selectOne(String userId);
	
	List<LUserCheckin> selectList(LUserCheckin lUserCheckin);
	
	void batchUpdate(List<LUserCheckin> lUserCheckin);

	void batchUpdateFailure(@Param("logId")Integer logId);
}

