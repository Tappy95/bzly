package com.mc.bzly.dao.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.task.MTaskInfo;

@Mapper
public interface MTaskInfoDao {

	void insert(MTaskInfo mTaskInfo);
	
	void update(MTaskInfo mTaskInfo);
	
	void delete(@Param("id")Integer id);
	
	MTaskInfo selectOne(@Param("id")Integer id);
	
	List<MTaskInfo> selectList(MTaskInfo mTaskInfo);
	/**
	 * app用户获取任务列表
	 * @param userId
	 * @param taskType
	 * @return
	 */
	List<Map<String,Object>> selectUserTask(@Param("userId")String userId,@Param("taskType")Integer taskType);
	/**
	 * 任务数量
	 * @param taskType
	 * @return
	 */
	int selectCount(@Param("taskType")Integer taskType);
	
	List<Map<String,Object>> selectAppUserTask(String userId);
}
