package com.mc.bzly.dao.task;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.task.MTaskType;

@Mapper
public interface MTaskTypeDao {
	
	List<MTaskType> selectList();
	
	List<MTaskType> selectDownList();
}
