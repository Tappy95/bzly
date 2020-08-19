package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.TPGameType;

@Mapper
public interface TPGameTypeDao {

	void insert(TPGameType tpGameType);
	
	void update(TPGameType tpGameType);
	
	int delete(Integer id);
	
	TPGameType selectOne(Integer id);
	
	List<TPGameType> selectList(TPGameType tpGameType);
	
	List<TPGameType> selectOption();
}
