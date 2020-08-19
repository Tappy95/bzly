package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.TaskCallback;

public interface TaskCallbackService {
	
	Map<String, Object> selectList(TaskCallback taskCallback);
	
}
