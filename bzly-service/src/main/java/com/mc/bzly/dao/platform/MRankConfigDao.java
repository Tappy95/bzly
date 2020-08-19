package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.MRankConfig;

@Mapper
public interface MRankConfigDao {

	void insert(MRankConfig mRankConfig);
	
	void update(MRankConfig mRankConfig);
	
	void delete(Integer id);
	
	MRankConfig selectOne(Integer id);
	
	List<MRankConfig> selectList(MRankConfig mRankConfig);
}
