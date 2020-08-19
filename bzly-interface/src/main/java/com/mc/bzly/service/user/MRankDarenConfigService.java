package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.MRankDarenConfig;

public interface MRankDarenConfigService {
	
    int insert(MRankDarenConfig mRankDarenConfig);
	
    PageInfo<MRankDarenConfig> selectList(MRankDarenConfig mRankDarenConfig);
    
    MRankDarenConfig selectOne(Integer id);
	
	int update(MRankDarenConfig mRankDarenConfig);
	
	int delete(Integer id);
}
