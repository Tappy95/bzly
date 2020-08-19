package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PLevel;

@Mapper
public interface PLevelDao {

	void insert(PLevel pLevel);
	
	void update(PLevel pLevel);
	
	void delete(Integer id);
	
	PLevel selectOne(Integer id);
	
	List<PLevel> selectList(PLevel pLevel);
	
	/**
	 * 根据当前等级，查询下级等级信息
	 * @param level 当前等级
	 * @return
	 */
	PLevel selectNextLevel(String level);

	/**
	 * 当前等级
	 * @param level 当前等级
	 * @return
	 */
	PLevel selectByLevel(String level);
}

