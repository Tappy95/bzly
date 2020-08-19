package com.mc.bzly.service.news;

import com.mc.bzly.model.news.AppNoticeUser;

public interface AppNoticeUserService {
	
	Integer add(AppNoticeUser appNoticeUser);
	
	Integer addList(String userId);

}
