package com.mc.bzly.service.task;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.task.MTaskType;

public interface MTaskInfoService {
	
	void add(MTaskInfo mTaskInfo) throws Exception;
	
	void modify(MTaskInfo mTaskInfo) throws Exception;
	
	void remove(Integer id) throws Exception;
	
	MTaskInfo queryOne(Integer id);
	
	PageInfo<MTaskInfo> queryList(MTaskInfo mTaskInfo);
	 
	List<MTaskType> selectUserTask(String userId);
	/**
	 * 新手任务列表
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> selectNoviceTask(String userId);
	
	List<Map<String,Object>> selectAppUserTask(String userId);
	/**
	 * 新手任务列表
	 * @param userId
	 * @return
	 */
	Map<String,Object> selectNoviceTaskNew(String userId);

}
