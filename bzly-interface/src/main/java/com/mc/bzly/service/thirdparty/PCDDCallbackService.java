package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.PCDDCallback;

public interface PCDDCallbackService {
	
	Map<String, Object> page(PCDDCallback pcDDCallback);
	
	PCDDCallback queryByOrdernum(String ordernum);
}
