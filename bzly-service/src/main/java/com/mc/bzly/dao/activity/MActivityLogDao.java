package com.mc.bzly.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.activity.MActivityLog;

@Mapper
public interface MActivityLogDao {

	void insert(MActivityLog mActivityLog);
	
	void update(MActivityLog mActivityLog);
	
	void delete(Integer lId);
	
	MActivityLog selectOne(Integer lId);
	
	List<MActivityLog> selectList(MActivityLog mActivityLog);

	/**
	 * 查询最近的两条打卡活动记录
	 * @param integer 
	 * @return
	 */
	List<MActivityLog> selectLatestTwo(Integer actId);
}
