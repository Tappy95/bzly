package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LDarenReward;

@Mapper
public interface LDarenRewardDao {

	List<LDarenReward> selectList(LDarenReward lDarenReward);
	
	void insert(LDarenReward lDarenReward);
	
	void update(LDarenReward lDarenReward);
	
	LDarenReward selectByUser(@Param("userId") String userId,@Param("rewardDate") String rewardDate);

	Map<String, Object> selectByUserTotal(@Param("userId")String userId);
}
