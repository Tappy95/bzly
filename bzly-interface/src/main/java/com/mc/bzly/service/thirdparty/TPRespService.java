package com.mc.bzly.service.thirdparty;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPResp;

public interface TPRespService {

	int add(TPResp tpResp) throws Exception;
	
	int modify(TPResp tpResp) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	TPResp queryOne(Integer id);
	
	PageInfo<TPResp> queryList(TPResp tpResp);
}
