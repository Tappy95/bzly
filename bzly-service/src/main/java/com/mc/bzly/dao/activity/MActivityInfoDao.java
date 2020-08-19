package com.mc.bzly.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.activity.MActivityInfo;

@Mapper
public interface MActivityInfoDao {

	void insert(MActivityInfo mActivityInfo);
	
	void update(MActivityInfo mActivityInfo);
	
	void delete(Integer actId);
	
	MActivityInfo selectOne(Integer actId);
	
	List<MActivityInfo> selectList(MActivityInfo mActivityInfo);
	
	MActivityInfo selectByActivityType(Integer activityType);
}
