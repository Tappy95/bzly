package com.mc.bzly.dao.wish;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.wish.GameRewardRate;

@Mapper
public interface GameRewardRateDao {

	void insert(GameRewardRate GameRewardRate);

	void update(GameRewardRate GameRewardRate);
	
	void delete(Integer id);
	
	GameRewardRate selectOne(Integer id);
	
	List<GameRewardRate> selectList(GameRewardRate GameRewardRate);
}
