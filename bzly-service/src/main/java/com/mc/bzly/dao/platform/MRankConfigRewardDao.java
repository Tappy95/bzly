package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.MRankConfigReward;

@Mapper
public interface MRankConfigRewardDao {

	void insert(MRankConfigReward mRankConfigReward);
	
	void update(MRankConfigReward mRankConfigReward);
	
	void delete(@Param("id") Integer id);
	
	MRankConfigReward selectOne(@Param("id") Integer id);
	
	List<MRankConfigReward> selectList(MRankConfigReward mRankConfigReward);
}
