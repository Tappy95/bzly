package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PLevel;

public interface PLevelService {

	int add(PLevel pLevel) throws Exception;
	
	int modify(PLevel pLevel) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PLevel queryOne(Integer id);
	
	PageInfo<PLevel> queryList(PLevel pLevel);
	
	PLevel queryLowerLevel();
	/**
	 * 后台下拉列表获取成长等级
	 * @return
	 */
	List<PLevel> downLevelList();
}
