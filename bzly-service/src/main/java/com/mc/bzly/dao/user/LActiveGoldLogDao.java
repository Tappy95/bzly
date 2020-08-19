package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LActiveGoldLog;

@Mapper
public interface LActiveGoldLogDao {
	/**
	 * 添加或活跃金领取记录
	 * @param lActiveGoldLog
	 * @return
	 */
    Integer save(LActiveGoldLog lActiveGoldLog);
    /**
     * 批量添加活跃金领取记录
     * @param list
     */
    void saveList(List<LActiveGoldLog> list);
    /**
     * 获取用户最近一次领取鼓励奖记录
     * @param userId
     * @param vipId
     * @return
     */
    LActiveGoldLog selectTimeMax(@Param("userId")String userId,@Param("vipId")Integer vipId);
    /**
     * 查看用户当天有没有领取过活跃金
     * @param userId
     * @return
     */
    int selectDay(@Param("userId")String userId);
    
    int selectDayNews(@Param("userId")String userId,@Param("vipId")int vipId);
}
