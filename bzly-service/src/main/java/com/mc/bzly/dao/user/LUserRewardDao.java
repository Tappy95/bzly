package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LUserReward;

@Mapper
public interface LUserRewardDao {
	
	void insert(LUserReward lUserReward);
	
	LUserReward selectOne(Integer addressId);
	
	List<LUserReward> selectList(LUserReward lUserReward);

	double selectTotalReward(Integer rewardType);
}
