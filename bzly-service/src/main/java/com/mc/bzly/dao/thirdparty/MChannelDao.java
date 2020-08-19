package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.MChannel;

@Mapper
public interface MChannelDao {

	void insert(MChannel mChannel);
	
	List<MChannel> selectList();
	
	MChannel selectNmae(String channelName);
}
