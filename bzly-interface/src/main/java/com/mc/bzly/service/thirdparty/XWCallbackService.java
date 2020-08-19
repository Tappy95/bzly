package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.model.thirdparty.XWCallback;

public interface XWCallbackService {
	
	Map<String, Object> page(XWCallback xwCallback);

	XWCallback queryByOrdernum(String ordernum);
	
	Map<String, Object> xyzPage(XWCallback xwCallback);

}
