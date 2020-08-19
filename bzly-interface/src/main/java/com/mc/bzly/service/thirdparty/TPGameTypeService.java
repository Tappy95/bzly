package com.mc.bzly.service.thirdparty;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPGameType;

public interface TPGameTypeService {

	int add(TPGameType tpGameType) throws Exception;
	
	int modify(TPGameType tpGameType) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	TPGameType queryOne(Integer id);
	
	PageInfo<TPGameType> queryList(TPGameType tpGameType);

	List<TPGameType> queryOpt();
	
	List<TPGameType> selectOption();
}
