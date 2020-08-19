package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.TPYmCallback;

public interface TPYmCallbackService {
	
	TPYmCallback selectCallbackId(String coinCallbackId);
	
	void callback(TPYmCallback tPYmCallback);
	
	Map<String, Object> page(TPYmCallback tPYmCallback);
}
