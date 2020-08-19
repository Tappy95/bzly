package com.mc.bzly.service.news;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.news.AppNewsInform;

public interface AppNewsInformService {
	
    
	PageInfo<AppNewsInform> queryList(AppNewsInform appNewsInform);
	 
	Integer add(AppNewsInform appNewsInform);
	 
	AppNewsInform info(Integer id);
	 
	Integer update(AppNewsInform appNewsInform);
	 
	Integer delete(Integer id);
	 
	PageInfo<AppNewsInform> pageList(AppNewsInform appNewsInform);
	 
	void addPush(AppNewsInform appNewsInform);
	 
	void saveInformList(List<AppNewsInform> appNewsInforms);
	
	Map<String,Object> selectIsRead(String userId);
	
	int updateRead(Integer id,String userId);
	
	int updateWholeRead(String userId);
	
	
}
