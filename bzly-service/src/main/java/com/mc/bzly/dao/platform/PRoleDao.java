package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PRole;

@Mapper
public interface PRoleDao {

	void insert(PRole pRole);
	
	void update(PRole pRole);
	
	void delete(Integer id);
	
	PRole selectOne(Integer id);
	
	List<PRole> selectList(PRole pRole);

	List<PRole> selectOption();
}
