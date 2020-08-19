package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LNoticeReady;

@Mapper
public interface LNoticeReadyDao {
	
	void insert(LNoticeReady lNoticeReady);

	void delete(LNoticeReady lNoticeReady);

	List<LNoticeReady> selectList(LNoticeReady lNoticeReady);
	
	long selectCount(LNoticeReady lNoticeReady);
	
	int selectSignCount(String userId);
}
