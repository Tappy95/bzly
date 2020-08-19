package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.thirdparty.TPTaskInfo;
import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;
import com.mc.bzly.model.thirdparty.TaskCallback;

public interface TPTaskInfoService {
	 
	int getTasks();
	
	void modify(TPTaskInfo tpTaskInfo);
	
	PageInfo<TPTaskInfo> queryList(TPTaskInfo tpTaskInfo);

	PageInfo<TPTaskInfo> queryFList(TPTaskInfo tpTaskInfo);

	Result info(Integer id,String userId);

	TPTaskInfo queryOne(Integer id);

	TaskCallback queryCallBack(String orderNum);

	long settle(TaskCallback taskCallback);

	void addCallBack(TaskCallback taskCallback);
	
	Map<String,Object> getConductTask(String userId);
	/**
	 * 创建任务地址 url
	 * @param userId 用户id
	 * @param id 任务id
	 * @param bzlyuserChannel  
	 * @param bzlyAppkey  
	 * @return
	 */
	String buildUrl(String userId, Integer id,String bzlyuserChannel,String bzlyAppkey);
	/**
	 * 获取用户任务状态变化
	 * @param tpTaskStatusCallback
	 */
	void taskUserChangedCallback(TPTaskStatusCallback tpTaskStatusCallback);
	
	int delete(Integer id);
}
