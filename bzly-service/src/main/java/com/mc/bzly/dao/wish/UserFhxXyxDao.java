package com.mc.bzly.dao.wish;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.wish.UserFhxXyx;

@Mapper
public interface UserFhxXyxDao {

	void insert(UserFhxXyx userFhxXyx);
	
	void update(UserFhxXyx userFhxXyx);
	
	UserFhxXyx selectOne(@Param("userId") String userId);
	
	List<UserFhxXyx> selectList(UserFhxXyx userFhxXyx);

	long selectListCount(UserFhxXyx userFhxXyx);
}
