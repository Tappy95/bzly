package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.YoleCallback;

public interface YoleCallbackService {
	
	Map<String, Object> page(YoleCallback yoleCallback);

	YoleCallback queryByYoleid(String yoleid);
}
