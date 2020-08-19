package com.mc.bzly.dao.fighting;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.fighting.LFightingInfo;

@Mapper
public interface LFightingInfoDao {
	/**
	 * 添加答题记录
	 * @param lFightingInfo
	 * @return
	 */
	Integer save(LFightingInfo lFightingInfo);
	/**
	 * 根据userId查询答题记录
	 * @param userId
	 * @return
	 */
	LFightingInfo selectUserId(@Param("userId")String userId);

}
