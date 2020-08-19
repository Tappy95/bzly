package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MUserApprentice;

@Mapper
public interface MUserApprenticeDao {
	
	void insert(MUserApprentice mUserApprentice);
	
	void update(MUserApprentice mUserApprentice);
	
	void delete(Integer apprenticeId);
	
	MUserApprentice selectOne(Integer apprenticeId);
	
	List<MUserApprentice> selectList(MUserApprentice mUserApprentice);
	/**
	 * 后台获取用户徒弟数据
	 * @param mUserApprentice
	 * @return
	 */
	List<MUserApprentice> selectPage(MUserApprentice mUserApprentice);
	/**
	 * 根据userId查询师徒信息
	 * @param userId
	 * @return
	 */
	MUserApprentice selectUserId(@Param("userId")String userId);
	/**
	 * 根据userId查询师徒信息
	 * @param userId
	 * @return
	 */
	MUserApprentice selectUserIdNew(@Param("userId")String userId);

	List<MUserApprentice> selectListByUser(@Param("referrerId")String userId,@Param("apprenticeType") Integer apprenticeType);
	
	Map<String,Object> selectWishCount(String referrerId);
	
	List<Map<String,Object>> selectWishRewardList(@Param("referrerId")String referrerId,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
	
	int selectWishRewardCount(@Param("referrerId")String referrerId);
	
    List<Map<String,Object>> selectWishNumberList(@Param("referrerId")String referrerId,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
	
	int selectWishNumberCount(@Param("referrerId")String referrerId);
}
