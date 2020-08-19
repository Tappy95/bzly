package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LRankPig;
import com.mc.bzly.model.user.LRankPigSimple;

@Mapper
public interface LRankPigDao {

	List<LRankPig> selectList(LRankPig lRankPig);

	List<LRankPigSimple> selectFlist(LRankPig lRankPig);

	LRankPigSimple selectByUser(@Param("userId")String userId,@Param("rankDate") String rankDate,@Param("rankType") Integer rankType);

	String selectLatest7Date();

	LRankPig selectByUserOne(LRankPig lRankpig);
}
