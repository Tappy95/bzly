package com.mc.bzly.dao.egg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.egg.EGoldEggType;

@Mapper
public interface EGoldEggTypeDao {
	
	int add(EGoldEggType eGoldEggType);
	
	List<EGoldEggType> selectList();
	
	EGoldEggType selectOne(Integer id);
	
	int update(EGoldEggType eGoldEggType);
	
	int delete(Integer id);

}
