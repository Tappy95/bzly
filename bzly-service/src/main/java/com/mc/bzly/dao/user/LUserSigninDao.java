package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserSignin;

@Mapper
public interface LUserSigninDao {
	
	void insert(LUserSignin lUserSignin);

	void update(LUserSignin lUserSignin);

	List<LUserSignin> selectList(LUserSignin lUserSignin);

	void batchInsert(List<LUserSignin> insertList);

	LUserSignin selectOne(Integer id);

	/**
	 * 查询待完成的签到记录 5，10，15天的
	 * @param userId
	 * @return
	 */
	List<LUserSignin> selectSignin(String userId);
	
	/**
	 * 查询待完成的签到记录 5，10，15天的
	 * @param userId
	 * @return
	 */
	List<LUserSignin> selectSigninFinish(@Param("userId") String userId,@Param("time")long time);

	/**
	 * 查询待补签的记录
	 * @param userId
	 * @param signDay
	 * @return
	 */
	List<LUserSignin> selectDBQ(@Param("userId")String userId, @Param("signDay")Integer signDay);
	
	void updateStatus(@Param("userId")String userId, @Param("signDay")Integer signDay, @Param("status")Integer status);
	
	List<LUserSignin> selectPageList(LUserSignin lUserSignin);
	
	int selectPageCount(LUserSignin lUserSignin);

}