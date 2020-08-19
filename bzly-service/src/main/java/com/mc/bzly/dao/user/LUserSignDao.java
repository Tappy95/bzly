package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserSign;

@Mapper
public interface LUserSignDao {
	
	Integer insert(LUserSign lUserSign);
	
	List<LUserSign> selectList(LUserSign lUserSign);
	/**
	 * 使用userId用户最近一次的签到记录
	 * @param userId
	 * @return
	 */
	LUserSign selectUserId(@Param("userId")String userId);
	/**
	 * 后台获取签到记录
	 * @param lUserSign
	 * @return
	 */
	List<LUserSign> selectPage(LUserSign lUserSign);
	/**
	 * 用户累计签到数
	 * @param userId
	 * @return
	 */
	int selectCount(@Param("userId")String userId);
	
	int selectIsSign(String userId);
	
	int update(LUserSign lUserSign);
}
