package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserSearchLog;

@Mapper
public interface LUserSearchLogDao {

	List<String> selectList(@Param("userId") String userId,@Param("searchType") Integer searchType);
	
	void insert(LUserSearchLog lUserSearchLog);
	
	void deleteAll(@Param("userId") String userId,@Param("searchType") Integer searchType);

	List<String> selectExist(@Param("userId") String userId,@Param("searchName") String searchName,@Param("searchType") Integer searchType);
}
