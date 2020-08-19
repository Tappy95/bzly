package com.mc.bzly.service.passbook;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.passbook.MVipInfo;

public interface MVipInfoService {
	
	void add(MVipInfo mVipInfo) throws Exception;
	
	void modify(MVipInfo mVipInfo) throws Exception;
	
	void remove(Integer id) throws Exception;
	
	MVipInfo queryOne(Integer id);
	
	PageInfo<MVipInfo> queryList(MVipInfo mVipInfo);

	List<Map<String,Object>> queryListNoPage(String userId);
	
	Map<String,Object> selectOneUser(Integer id,String userId);
	
	List<Map<String,Object>> dowZqVip();
}
