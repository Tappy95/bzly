package com.mc.bzly.service.passbook;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.passbook.MPassbook;

public interface MPassbookService {
	
	void add(MPassbook mPassbook,String taskTypes) throws Exception;

	void modify(MPassbook mPassbook,String taskTypes) throws Exception;

	void remove(Integer id) throws Exception;
	
	Map<String, Object> queryOne(Integer id);
	
	PageInfo<MPassbook> queryList(MPassbook mPassbook);
	
	void batchSendPassbook(String mobiles,Integer id) throws Exception;
	
	Map<String,Object> taskUsePassbook(String userId,Integer taskType);
	
	void passbookOverdue(Integer passbookId);
}
