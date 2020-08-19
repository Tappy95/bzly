package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.MRankDarenConfig;

@Mapper
public interface MRankDarenConfigDao {

	MRankDarenConfig selectByNum(Integer num);
	
	int insert(MRankDarenConfig mRankDarenConfig);
	
	List<MRankDarenConfig> selectList(MRankDarenConfig mRankDarenConfig);
	
	MRankDarenConfig selectOne(Integer id);
	
	int update(MRankDarenConfig mRankDarenConfig);
	
	int delete(Integer id);
}
