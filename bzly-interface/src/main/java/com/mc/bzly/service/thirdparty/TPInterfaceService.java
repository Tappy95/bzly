package com.mc.bzly.service.thirdparty;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPInterface;

public interface TPInterfaceService {

	int add(TPInterface tpInterface) throws Exception;
	
	int modify(TPInterface tpInterface) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	TPInterface queryOne(Integer id);
	
	PageInfo<TPInterface> queryList(TPInterface tpInterface);

	List<TPInterface> queryOpt();

	int getGames(Integer id);
	
	List<Map<String,Object>> selectDown();
}
