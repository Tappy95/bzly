package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.mc.bzly.base.CsjResult;
import com.mc.bzly.model.thirdparty.TpVideoCallback;

public interface TpVideoCallbackService {

	CsjResult hbVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey);
	
	CsjResult syVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey);
	
	CsjResult bqVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey);
	
	Map<String,Object> videoUser(String userId);
	
	Map<String,Object> videoTimeUser(String userId);
	
	Map<String,Object> videoCount(String userId);
	
	Map<String, Object> list(TpVideoCallback tpVideoCallback);
	
	Map<String,Object> newUserVideo(String userId);
	
	Map<String,Object> newUserVideoCoin(String userId);
	
	CsjResult tkVideoCallback(TpVideoCallback tpVideoCallback,String appSecurityKey);
	
	
}
