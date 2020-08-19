package com.mc.bzly.dao.news;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.news.AppNoticeUser;

@Mapper
public interface AppNoticeUserDao {
	
	Integer add(AppNoticeUser appNoticeUser);
	
	Integer addList(List<AppNoticeUser> appNoticeUsers);
	
	AppNoticeUser selectOne(@Param("noticeId")Integer noticeId,@Param("userId")String userId);

}
