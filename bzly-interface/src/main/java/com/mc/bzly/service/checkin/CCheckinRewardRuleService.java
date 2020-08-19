package com.mc.bzly.service.checkin;

import java.util.Map;

import com.mc.bzly.model.checkin.CCheckinRewardRule;

public interface CCheckinRewardRuleService {
	
    int add(CCheckinRewardRule cCheckinRewardRule);
	
	Map<String,Object> selectList();
	
	CCheckinRewardRule selectOne(Integer id);
	
	int update(CCheckinRewardRule cCheckinRewardRule);
	
	int delete(Integer id);
}
