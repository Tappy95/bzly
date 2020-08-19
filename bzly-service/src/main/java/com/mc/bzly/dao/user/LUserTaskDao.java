package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserTask;

@Mapper
public interface LUserTaskDao {
	/**
	 * 添加完成的任务
	 * @param lUserTask
	 * @return
	 */
	Integer insert(LUserTask lUserTask);
	/**
	 * 查询用户是否完成了任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	LUserTask selectOne(@Param("userId")String userId,@Param("taskId")Integer taskId);
	/**
	 * 查询用户是否完成了任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	List<LUserTask> selectListUser(@Param("userId")String userId,@Param("taskId")Integer taskId);
	/**
	 * 用户完成的任务数
	 * @param userId
	 * @param taskType
	 * @return
	 */
	int selectCompleteCount(@Param("userId")String userId,@Param("taskType")Integer taskType);
	/**
	 * 修改任务
	 * @param id
	 * @return
	 */
	Integer update(@Param("isReceive")Integer isReceive,@Param("id")Integer id);
	
	/**
	 * 查询用户完成某个任务的个数
	 * @param userId
	 * @param taskId
	 * @return
	 */
	long selectUserTaskCount(@Param("userId")String userId,@Param("taskId")Integer taskId);
	
	List<Map<String,Object>> selectList(LUserTask lUserTask);
	
	int selectCount(LUserTask lUserTask);

}
