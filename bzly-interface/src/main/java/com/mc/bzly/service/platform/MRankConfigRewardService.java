package com.mc.bzly.service.platform;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.MRankConfigReward;

public interface MRankConfigRewardService {
	
	void add(MRankConfigReward mRankConfigReward);
	
	void modify(MRankConfigReward mRankConfigReward);
	
	void remove(Integer id);
	
	MRankConfigReward queryOne(Integer id);
	
	PageInfo<MRankConfigReward> queryList(MRankConfigReward mRankConfigReward);
}
