package com.mc.bzly.service.thirdparty;

import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;

public interface TPTaskStatusCallbackService {
	
	void add(TPTaskStatusCallback tpTaskStatusCallback);

	TPTaskStatusCallback queryByNum(String flewNum,Integer status);
}
