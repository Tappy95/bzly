package com.mc.bzly.service.user;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LUserVip;

public interface LUserVipService {

	int add(LUserVip lUserVip) throws Exception;
	
	int modify(LUserVip lUserVip) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	LUserVip queryOne(Integer id);
	
	PageInfo<LUserVip> queryList(LUserVip lUserVip);
	
	void checkOverdue();

	List<Map<String,String>> selectVipNews(String channelCode);

	List<Map<String,Object>> queryMyVips(String userId);

	List<Map<String,Object>> queryMyVipsRecord(String userId);

	Map<String,Object> readyWithdrawals(String userId);
	
	Map<String,Object> selectReliefPig(String userId);

	Map<String,Object> receiveReliefPig(String userId) throws ParseException;
}
