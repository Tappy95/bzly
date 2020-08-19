package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LRankDarenWeek;

@Mapper
public interface LRankDarenWeekDao {
	
	List<LRankDarenWeek> selectFlist(LRankDarenWeek lRankDarenWeek);

	List<LRankDarenWeek> selectNowlist(@Param("createTime") Long createTime);
	
	List<LRankDarenWeek> selectList(LRankDarenWeek lRankDarenWeek);
	
	int selectCount(LRankDarenWeek lRankDarenWeek);
	
	List<Map<String,Object>> selectCycle();

	String selectLaster5();

	List<LRankDarenWeek> selectByCycel(@Param("rankCycle")String rankCycle);

}
