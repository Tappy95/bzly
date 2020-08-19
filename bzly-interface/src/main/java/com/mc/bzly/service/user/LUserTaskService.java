package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.model.user.LUserTask;

public interface LUserTaskService {
	/**
	 * 完成新手任务
	 * @param userId
	 * @param id
	 * @return
	 */
    Integer userReceiveTask(String userId,Integer id);
    /**
     * 福利会任务完成
     * @param userId
     * @return
     */
    Integer welfareTask(String userId);
    
    Map<String,Object> list(LUserTask lUserTask);
}
