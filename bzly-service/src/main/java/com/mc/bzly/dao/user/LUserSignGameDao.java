package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserSignGame;

@Mapper
public interface LUserSignGameDao {

	void insert(LUserSignGame lUserSignGame);

	void update(LUserSignGame lUserSignGame);
	
	List<LUserSignGame> selectByUserSignin(@Param("userId") String userId,@Param("signinId") Integer signinId,@Param("status") Integer status);
	
	void deleteByUserGame(@Param("userId") String userId,@Param("signinId") Integer signinId);
	
	List<LUserSignGame> selectByUserGame(@Param("userId") String userId,@Param("gameId") Integer gameId);

	long selectCountByUserSigin(@Param("userId") String userId,@Param("signinId") Integer signinId);

	long selectCountByUser(@Param("userId") String userId,@Param("time") long time);

	long selectSumByUser(@Param("userId") String userId,@Param("time") long time);

	List<LUserSignGame> selectByUserSigin(@Param("userId") String userId,@Param("signinId") Integer signinId);
	
	void hideUpdate(LUserSignGame lUserSignGame);

	void updateGame(LUserSignGame lUserSignGame);
	
	List<LUserSignGame> selectSignList(LUserSignGame lUserSignGame);
	
	int delete(Integer id);
}
