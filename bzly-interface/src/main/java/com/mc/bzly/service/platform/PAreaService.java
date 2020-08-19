package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PArea;

public interface PAreaService {

	int add(PArea pArea) throws Exception;
	
	int modify(PArea pArea) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PArea queryOne(Integer id);
	
	PageInfo<PArea> queryList(PArea pArea);
	/**
	 * app获取区域
	 * @param pArea
	 * @return
	 */
	List<PArea> appList(PArea pArea);
}
