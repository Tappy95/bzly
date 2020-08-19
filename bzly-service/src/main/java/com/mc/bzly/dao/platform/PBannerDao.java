package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PBanner;

@Mapper
public interface PBannerDao {

	void insert(PBanner pBanner);
	
	void update(PBanner pBanner);
	
	void delete(Integer bannerId);
	
	PBanner selectOne(Integer bannerId);
	
	List<PBanner> selectList(PBanner pBanner);
}
