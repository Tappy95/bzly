package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.BZCallback;

public interface BZCallbackService {
	
	Map<String, Object> page(BZCallback bzCallback);

	BZCallback queryByOrdernum(String ordernum);

}
