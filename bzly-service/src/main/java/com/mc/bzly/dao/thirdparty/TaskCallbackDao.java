package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TaskCallback;

@Mapper
public interface TaskCallbackDao {
	
	void insert(TaskCallback taskCallback);
	
	TaskCallback selectByOrderNum(@Param("orderNum")String orderNum);
	
	List<TaskCallback> selectList(TaskCallback taskCallback);
	
	int selectCount(TaskCallback taskCallback);
}
