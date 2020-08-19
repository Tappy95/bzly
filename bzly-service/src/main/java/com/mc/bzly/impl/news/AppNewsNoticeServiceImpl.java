package com.mc.bzly.impl.news;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.news.AppNewsNoticeDao;
import com.mc.bzly.dao.news.AppNoticeUserDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.news.AppNewsNotice;
import com.mc.bzly.model.news.AppNoticeUser;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.news.AppNewsNoticeService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=AppNewsNoticeService.class,version=WebConfig.dubboServiceVersion)
public class AppNewsNoticeServiceImpl implements AppNewsNoticeService {
	
    @Autowired
	private AppNewsNoticeDao appNewsNoticeDao;
    @Autowired
    private AppNoticeUserDao appNoticeUserDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
	
	@Override
	public int add(AppNewsNotice appNewsNotice) {
		Long time=new Date().getTime();
		if(appNewsNotice.getCancelTime()>time && time>appNewsNotice.getReleaserTime()) {
			appNewsNotice.setIsRelease(1);
		}else {
			appNewsNotice.setIsRelease(2);
		}
		appNewsNotice.setRanges(1);
		appNewsNotice.setCreaterTime(new Date().getTime());
		return appNewsNoticeDao.saveNotice(appNewsNotice);
	}

	@Override
	public PageInfo<AppNewsNotice> queryList(AppNewsNotice appNewsNotice) {
		PageHelper.startPage(appNewsNotice.getPageNum(), appNewsNotice.getPageSize());
		List<AppNewsNotice> appNewsNoticeList =appNewsNoticeDao.selectList(appNewsNotice);
		return new PageInfo<>(appNewsNoticeList);
	}

	@Override
	public AppNewsNotice selectId(Integer id) {
		return appNewsNoticeDao.selectId(id);
	}

	@Override
	public int updateNotice(AppNewsNotice appNewsNotice) {
		if(appNewsNotice.getIsPublish().intValue()==2) {
			appNewsNotice.setIsRelease(2);
		}
		return appNewsNoticeDao.updateNotice(appNewsNotice);
	}

	@Override
	public int deleteNotice(Integer id) {
		return appNewsNoticeDao.deleteNotice(id);
	}

	@Override
	public Map<String,Object> selectOne(String userId) {
		Map<String,Object> data=new HashMap<>();
		data.put("isNotice", 1);//是否有公告1没有2有
		MUserInfo user=mUserInfoDao.selectOne(userId);
		String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		AppNewsNotice appNewsNotice=null;
	    if("zhongqing".equals(channelCode)) {
	    	appNewsNotice=appNewsNoticeDao.selectOneNews(new Date().getTime(),2);
	    }else {
	    	appNewsNotice=appNewsNoticeDao.selectOneNews(new Date().getTime(),1);
	    }
		if(StringUtil.isNullOrEmpty(appNewsNotice)) {
			return data;
		}
		if(userId=="") {
			data.put("isNotice", 2);
			data.put("appNewsNotice", appNewsNotice);
			return data;
		}
		AppNoticeUser appNoticeUser=appNoticeUserDao.selectOne(appNewsNotice.getId(), userId);
		if(StringUtil.isNullOrEmpty(appNoticeUser)) {
			data.put("isNotice", 2);
			data.put("appNewsNotice", appNewsNotice);
			return data;
		}
		return data;
	}

	@Override
	public PageInfo<AppNewsNotice> queryAppList(AppNewsNotice appNewsNotice) {
		MUserInfo user=mUserInfoDao.selectOne(appNewsNotice.getUserId());
		String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		if("zhongqing".equals(channelCode)) {
			appNewsNotice.setAppType(2);
	    }else {
	    	appNewsNotice.setAppType(1);
	    }
		PageHelper.startPage(appNewsNotice.getPageNum(), appNewsNotice.getPageSize());
		List<AppNewsNotice> appNewsNoticeList =appNewsNoticeDao.selectAppList(appNewsNotice);
		return new PageInfo<>(appNewsNoticeList);
	}

}
