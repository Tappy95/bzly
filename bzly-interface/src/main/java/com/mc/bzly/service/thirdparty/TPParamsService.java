package com.mc.bzly.service.thirdparty;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPParams;

public interface TPParamsService {

	int add(TPParams tpParams) throws Exception;
	
	int modify(TPParams tpParams) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	TPParams queryOne(Integer id);
	
	PageInfo<TPParams> queryList(TPParams tpParams);
}
