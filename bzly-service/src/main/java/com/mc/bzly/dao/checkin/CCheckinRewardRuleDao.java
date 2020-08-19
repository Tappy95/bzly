package com.mc.bzly.dao.checkin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.checkin.CCheckinRewardRule;

@Mapper
public interface CCheckinRewardRuleDao {
	
	int add(CCheckinRewardRule cCheckinRewardRule);
	
	List<CCheckinRewardRule> selectList();
	
	CCheckinRewardRule selectOne(Integer id);
	
	int update(CCheckinRewardRule cCheckinRewardRule);
	
	int delete(Integer id);
	
	CCheckinRewardRule selectNumber(int number);

}
