package com.mc.bzly.dao.checkin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.checkin.CCheckinLog;

@Mapper
public interface CCheckinLogDao {
	
	int add(CCheckinLog cCheckinLog);
	
	CCheckinLog selectCreate(String userId);
	
	int update(CCheckinLog cCheckinLog);
	
	CCheckinLog selectCheckin(String userId);
	
	Map<String,Object> selectCreateCount();
	
	Map<String,Object> selectCheckinCount();
	
	int updateTips(int id);
	
	CCheckinLog selectLast(String userId);
	
	List<Map<String,Object>> selectUserList(CCheckinLog cCheckinLog);
	
	int selectUserCount(CCheckinLog cCheckinLog);
	
	Map<String,Object> selectUserSum(String userId);
	
	List<CCheckinLog> selectList(CCheckinLog cCheckinLog);
	
	int selectCount(CCheckinLog cCheckinLog);
	
	Map<String,Object> selectSubtotal(CCheckinLog cCheckinLog);
	
	Map<String,Object> selectTotal(CCheckinLog cCheckinLog);

}
