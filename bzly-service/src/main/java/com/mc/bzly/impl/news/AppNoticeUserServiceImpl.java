package com.mc.bzly.impl.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.news.AppNewsNoticeDao;
import com.mc.bzly.dao.news.AppNoticeUserDao;
import com.mc.bzly.model.news.AppNewsNotice;
import com.mc.bzly.model.news.AppNoticeUser;
import com.mc.bzly.service.news.AppNoticeUserService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=AppNoticeUserService.class,version=WebConfig.dubboServiceVersion)
public class AppNoticeUserServiceImpl implements AppNoticeUserService{
	
    @Autowired
	private AppNoticeUserDao appNoticeUserDao;
    @Autowired
    private AppNewsNoticeDao appNewsNoticeDao;
	
	@Override
	public Integer add(AppNoticeUser appNoticeUser) {
		AppNoticeUser app=appNoticeUserDao.selectOne(appNoticeUser.getNoticeId(), appNoticeUser.getUserId());
		if(!StringUtil.isNullOrEmpty(app)) {
			return 2;
		}
		appNoticeUser.setReadNum(1);
		appNoticeUser.setCreaterTime(new Date().getTime());
		return appNoticeUserDao.add(appNoticeUser);
	}

	@Override
	public Integer addList(String userId) {
		List<AppNewsNotice> appNewsNotices=appNewsNoticeDao.selectReadList(userId);
		long time=new Date().getTime();
		List<AppNoticeUser> list=new ArrayList<>();
		AppNoticeUser appNoticeUser=null;
		for(AppNewsNotice a:appNewsNotices) {
			appNoticeUser=new AppNoticeUser();
			appNoticeUser.setCreaterTime(time);
			appNoticeUser.setNoticeId(a.getId());
			appNoticeUser.setUserId(userId);
			appNoticeUser.setReadNum(1);
			list.add(appNoticeUser);
		}
		appNoticeUserDao.addList(list);
		return 1;
	}

}
