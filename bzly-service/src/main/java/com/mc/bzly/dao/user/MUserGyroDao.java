package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MUserGyro;

@Mapper
public interface MUserGyroDao {
	
	int add(MUserGyro mUserGyro);
	
	MUserGyro selectOne(@Param("userId")String userId,@Param("pageType")Integer pageType,@Param("number")Integer number);
	
	int update(MUserGyro mUserGyro);
	
	List<MUserGyro> selectList(String userId);
	
	int delete(String userId);

}
