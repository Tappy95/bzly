package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LRankCoin;

@Mapper
public interface LRankCoinDao {

	List<LRankCoin> selectList(LRankCoin LRankCoin);

	String selectLatest7Date();

	LRankCoin selectOne(LRankCoin lRankCoin);
}
