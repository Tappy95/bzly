package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PVersion;

@Mapper
public interface PVersionDao {

	void insert(PVersion pVersion);
	
	void update(PVersion pVersion);
	
	void delete(Integer id);
	
	PVersion selectOne(Integer id);
	
	List<PVersion> selectList(PVersion pVersion);
	
	PVersion selectByVersionAndChannelCode(@Param("versionNo") String versionNo,@Param("channelCode") String channelCode);

	PVersion selectByChannelCode(@Param("channelCode") String channelCode);
	
	PVersion selectVersionChannnel(String channelCode);
	
	PVersion selectByIosVersionAndChannelCode(@Param("iosVersionNo") String iosVersionNo,@Param("channelCode") String channelCode);
}
