package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LUserReconciliation;
import com.mc.bzly.model.user.LUserStatistic;
@Mapper
public interface LUserStatisticDao {
	
	int insert(LUserStatistic lUserStatistic);
	
	int update(LUserStatistic lUserStatistic);
	
	LUserStatistic selectOne(String userId);
	
	List<Map<String,Object>> selectUserList(LUserReconciliation lUserReconciliation);
	
	int selectUserCount(LUserReconciliation lUserReconciliation);

}
