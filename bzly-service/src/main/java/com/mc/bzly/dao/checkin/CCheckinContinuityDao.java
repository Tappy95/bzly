package com.mc.bzly.dao.checkin;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.checkin.CCheckinContinuity;
@Mapper
public interface CCheckinContinuityDao {
	
	int add(CCheckinContinuity cCheckinContinuity);
	
	CCheckinContinuity  selectOne(String userId);
	
	int update(CCheckinContinuity cCheckinContinuity);

}
