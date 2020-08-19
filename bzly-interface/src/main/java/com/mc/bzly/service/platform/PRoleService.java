package com.mc.bzly.service.platform;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PRole;

public interface PRoleService {

	int add(PRole pRole,String menuIds,String btnIds) throws Exception;
	
	int modify(PRole pRole,String menuIds,String btnIds) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	Map<String, Object> queryOne(Integer id);
	
	PageInfo<PRole> queryList(PRole pRole);

	List<PRole> queryOption();
}
