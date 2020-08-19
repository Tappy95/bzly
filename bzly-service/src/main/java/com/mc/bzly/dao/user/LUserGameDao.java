package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LUserGame;

@Mapper
public interface LUserGameDao {

	void insert(LUserGame lUserGame);
	
	void update(LUserGame lUserGame);
	
	List<TPGame> selectMyTry(String userId);

	LUserGame selectOne(@Param("userId") String userId,@Param("gameId") Integer gameId);

	void delete(@Param("id") Integer id,@Param("userId") String userId);
	
}
