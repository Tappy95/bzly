package com.mc.bzly.service.user;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.thirdparty.TpVideoCallback;
import com.mc.bzly.model.user.LUserSign;

public interface LUserSignService {

	Map<String,Object> add(String userId,String ip) throws Exception;
	
	Map<String,Object> addNew(String userId,String ip) throws Exception;
	
	PageInfo<LUserSign> queryList(LUserSign lUserSign);
	 
	PageInfo<LUserSign> pageList(LUserSign lUserSign);
	 
	void masterWorkerCoin(String userId);
	
	Map<String,Object> getDaySign(String userId);
	
	Map<String,Object> recommendGameSign(int ptype,String userId);
	
	Result receiveCoin(String userId);
}
