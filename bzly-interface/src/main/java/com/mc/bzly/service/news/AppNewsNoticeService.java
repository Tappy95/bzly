package com.mc.bzly.service.news;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.news.AppNewsNotice;

public interface AppNewsNoticeService {
	 
    int add(AppNewsNotice appNewsNotice);
     
    PageInfo<AppNewsNotice> queryList(AppNewsNotice appNewsNotice);
     
    AppNewsNotice selectId(Integer id);
     
    int updateNotice(AppNewsNotice appNewsNotice);
     
    int deleteNotice(Integer id);
    
    Map<String,Object> selectOne(String userId);
    
    PageInfo<AppNewsNotice> queryAppList(AppNewsNotice appNewsNotice);
}
