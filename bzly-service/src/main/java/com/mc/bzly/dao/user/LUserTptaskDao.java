package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.model.user.LUserTptaskSimple;

@Mapper
public interface LUserTptaskDao {
	
	void insert(LUserTptask lUserTptask);

	/**
	 * 查询列表
	 * @param userId
	 * @return
	 */
	List<LUserTptask> selectList(LUserTptask lUserTptask);
	
	/**
	 * 修改用户任务记录
	 * @param lUserTptask
	 */
	void update(LUserTptask lUserTptask);
	/**
	 * 查询单个记录
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @return
	 */
	LUserTptask selectByTaskId(@Param("tpTaskId")Integer taskId,@Param("userId")String userId,@Param("status")Integer status);
	
	/**
	 * 查询用户任务数 1-待完成 5-已预约
	 * @param userId
	 * @return
	 */
	long selectTaskCount(@Param("userId")String userId,@Param("status")Integer status);

	/**
	 * 查询单个记录
	 * @param id
	 */
	LUserTptask selectOne(Integer id);
	
	/**
	 * 查询单个记录
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @return
	 */
	LUserTptask selectByTaskIdAccount(@Param("tpTaskId")Integer taskId,@Param("accountId")Integer accountId,@Param("status")Integer status);

	/**
	 * 查询预约的记录
	 * @param id
	 * @return
	 */
	List<LUserTptask> selectListByTaskId(@Param("tpTaskId")Integer taskId);

	/**
	 * 修改预约记录为待提交
	 * @param expireTime
	 * @param id
	 */
	void batchUpdateStatus( @Param("expireTime")Long expireTime, @Param("tpTaskId")Integer tpTaskId);

	/**
	 * 前端查询列表
	 * @param lUserTptask
	 * @return
	 */
	List<LUserTptaskSimple> selectFList(LUserTptask lUserTptask);

	/**
	 * 查看用户是否已经完成了该任务
	 * @param tpTaskId
	 * @param userId
	 * @return
	 */
	List<LUserTptask> selectByTaskIdHasFinish(@Param("tpTaskId")Integer tpTaskId, @Param("userId")String userId);
	
	/**
	 * 查询用户已完成的任务
	 * @param userId
	 * @return
	 */
	List<LUserTptask> selectByUserHasFinish(@Param("userId")String userId);
	
	int selectUserCount(String userId);

	LUserTptask selectByFlewNum(@Param("flewNum")String flewNum);

	void updateStatus(LUserTptask lUserTptask);
	
}
