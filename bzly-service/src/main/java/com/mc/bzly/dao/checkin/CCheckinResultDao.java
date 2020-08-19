package com.mc.bzly.dao.checkin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.checkin.CCheckinResult;

@Mapper
public interface CCheckinResultDao {
	
	CCheckinResult selectOne();
	
	List<CCheckinResult> selectList(CCheckinResult cCheckinResult);
	
	int selectCount(CCheckinResult cCheckinResult);

}
