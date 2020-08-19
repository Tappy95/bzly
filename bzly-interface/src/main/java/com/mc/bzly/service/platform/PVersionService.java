package com.mc.bzly.service.platform;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PVersion;

public interface PVersionService {

	int add(PVersion pVersion) throws Exception;
	
	int modify(PVersion pVersion) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PVersion queryOne(Integer id);
	
	PageInfo<PVersion> queryList(PVersion pVersion);
}
