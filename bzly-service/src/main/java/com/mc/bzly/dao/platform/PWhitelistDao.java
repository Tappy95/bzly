package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PWhitelist;

@Mapper
public interface PWhitelistDao {

	void insert(PWhitelist PWhitelist);
	
	void update(PWhitelist PWhitelist);
	
	void delete(Integer id);
	
	PWhitelist selectOne(Integer id);

	PWhitelist selectByIp(String ip);
	
	List<PWhitelist> selectList(PWhitelist PWhitelist);

	List<PWhitelist> selectByContent(String content);
}
