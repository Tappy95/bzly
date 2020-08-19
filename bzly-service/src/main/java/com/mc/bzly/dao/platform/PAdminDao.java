package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PAdmin;

@Mapper
public interface PAdminDao {

	void insert(PAdmin pAdmin);
	
	void update(PAdmin pAdmin);
	
	void delete(String adminId);
	
	PAdmin selectOne(String adminId);

	PAdmin selectForLogin(PAdmin pAdmin);
	
	List<PAdmin> selectList(PAdmin pAdmin);
}
