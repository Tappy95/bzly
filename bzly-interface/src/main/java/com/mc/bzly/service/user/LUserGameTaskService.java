package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.mc.bzly.model.user.LUserGameTask;

public interface LUserGameTaskService {
	
	public int add(String userId,int vipId,int gameId);
	
	Map<String,Object> selectlist(String createTime,Integer accountId);
	
	int updateState(Integer id,Integer state);
	
	int updateHide(LUserGameTask lUserGameTask);

}
