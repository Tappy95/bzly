package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LUserReward;

public interface LUserRewardService {

	int add(LUserReward lUserReward) throws Exception;
	
	LUserReward queryOne(Integer addressId);
	
	PageInfo<LUserReward> queryList(LUserReward lUserReward);

	double queryTotalReward(LUserReward lUserReward);
}
