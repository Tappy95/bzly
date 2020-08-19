package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PWhitelist;

public interface PWhitelistService {

	int add(PWhitelist pWhitelist) throws Exception;
	
	int modify(PWhitelist pWhitelist) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PWhitelist queryOne(Integer id);
	
	PageInfo<PWhitelist> queryList(PWhitelist pWhitelist);
	
	List<PWhitelist> queryByContent(String content);
}
