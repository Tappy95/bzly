package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.mc.bzly.model.user.LRankDarenWeek;

public interface LRankDarenWeekService {
	
	Map<String, Object> queryFlist();
	
	Map<String, Object> page(LRankDarenWeek lRankDarenWeek);
	
	List<Map<String,Object>> selectCycle();

	List<LRankDarenWeek> queryByCycel(String rankCycle);
}
