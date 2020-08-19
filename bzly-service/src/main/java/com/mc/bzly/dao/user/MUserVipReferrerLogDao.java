package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MUserVipReferrerLog;

@Mapper
public interface MUserVipReferrerLogDao {
	/**
	 * 添加累充vip师傅奖励记录
	 * @param mUserVipReferrerLog
	 * @return
	 */
	int insert(MUserVipReferrerLog mUserVipReferrerLog);
	/**
	 * 用户id和推荐者id查询领取等级最大的记录
	 * @param userId
	 * @param referrerId
	 * @return
	 */
	MUserVipReferrerLog selectOne(@Param("userId")String userId,@Param("referrerId")String referrerId);

}
