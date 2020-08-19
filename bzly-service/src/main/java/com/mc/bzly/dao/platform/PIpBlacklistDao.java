package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PIpBlacklist;

@Mapper
public interface PIpBlacklistDao {

	void insert(PIpBlacklist pIpBlacklist);
	
	void update(PIpBlacklist pIpBlacklist);
	
	void delete(Integer id);
	
	PIpBlacklist selectOne(Integer id);

	PIpBlacklist selectByIp(String ip);
	
	List<PIpBlacklist> selectList(PIpBlacklist pIpBlacklist);
}
