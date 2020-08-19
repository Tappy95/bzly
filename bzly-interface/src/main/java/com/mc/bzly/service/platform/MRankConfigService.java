package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.MRankConfig;

public interface MRankConfigService {

	int add(MRankConfig mRankConfig) throws Exception;
	
	int modify(MRankConfig mRankConfig) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	MRankConfig queryOne(Integer id);
	
	PageInfo<MRankConfig> queryList(MRankConfig mRankConfig);

	List<MRankConfig> querySelect();

}
