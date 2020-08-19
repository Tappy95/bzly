package com.mc.bzly.dao.egg;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.egg.EUserGoldEgg;

@Mapper
public interface EUserGoldEggDao {
	
	int add(EUserGoldEgg eUserGoldEgg);
	
	EUserGoldEgg selectOne(String userId);
	
	int update(EUserGoldEgg eUserGoldEgg);

}
