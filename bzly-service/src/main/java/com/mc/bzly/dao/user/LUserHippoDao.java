package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LUserAdsReward;
import com.mc.bzly.model.user.LUserHippo;

@Mapper
public interface LUserHippoDao {
	
	void insert(LUserHippo lUserHippo);

	long selectCount(LUserAdsReward lUserAdsReward);
	
	void insertAdsReward(LUserAdsReward lUserAdsReward);
}
