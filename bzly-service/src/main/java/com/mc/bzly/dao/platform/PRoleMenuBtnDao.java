package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.platform.PRoleMenuBtn;

@Mapper
public interface PRoleMenuBtnDao {
	
	void batchInsert(List<PRoleMenuBtn> PRoleMenuBtns);
	
	void deleteByRole(Integer roleId);
	
	List<PRoleMenuBtn> selectByRole(Integer roleId);
}
