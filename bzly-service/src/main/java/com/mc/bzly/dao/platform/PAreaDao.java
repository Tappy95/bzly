package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PArea;

@Mapper
public interface PAreaDao {

	void insert(PArea pArea);
	
	void update(PArea pArea);
	
	void delete(Integer id);
	
	PArea selectOne(Integer id);

	List<PArea> selectList(PArea pArea);
	
	String selectFullName(Integer id);
}
