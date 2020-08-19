package com.mc.bzly.service.egg;

import java.util.Map;

import com.mc.bzly.model.egg.EGoldEggType;

public interface EGoldEggTypeService {

    int add(EGoldEggType eGoldEggType);
	
	Map<String,Object> selectList();
	
	EGoldEggType selectOne(Integer id);
	
	int update(EGoldEggType eGoldEggType);
	
	int delete(Integer id);
	
	Map<String,Object> smashEggInfo(String userId);
}
