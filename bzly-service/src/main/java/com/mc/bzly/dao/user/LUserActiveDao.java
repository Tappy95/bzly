package com.mc.bzly.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserActive;
@Mapper
public interface LUserActiveDao {
	/**
	 * 添加活跃记录
	 * @param lUserActive
	 * @return
	 */
	Integer insert(LUserActive lUserActive);
	/**
	 * 查询活跃记录
	 * @param userId
	 * @param activeTime
	 * @return
	 */
	LUserActive selectOne(@Param("userId")String userId,@Param("activeTime")String activeTime);

}
