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
import com.mc.bzly.dao.news.AppNewsInformDao;
import com.mc.bzly.dao.news.AppNewsNoticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.news.AppNewsInformService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass=AppNewsInformService.class,version=WebConfig.dubboServiceVersion)
public class AppNewsInformServiceImpl implements AppNewsInformService {
    @Autowired
	private AppNewsInformDao appNewsInformDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
    @Autowired
    private AppNewsNoticeDao appNewsNoticeDao;
	
	@Override
	public PageInfo<AppNewsInform> queryList(AppNewsInform appNewsInform) {
		appNewsInform.setIsRelease(1);
		MUserInfo user=mUserInfoDao.selectOne(appNewsInform.getUserId());
		String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		if("zhongqing".equals(channelCode)) {
			appNewsInform.setAppType(2);
	    }else {
	    	appNewsInform.setAppType(1);
	    }
		PageHelper.startPage(appNewsInform.getPageNum(), appNewsInform.getPageSize());
		List<AppNewsInform> appNewsInformList =appNewsInformDao.selectList(appNewsInform);
		return new PageInfo<>(appNewsInformList);
	}

	@Override
	public Integer add(AppNewsInform appNewsInform) {
		if(appNewsInform.getPushObject()==1) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("mobile", appNewsInform.getMobile());
			MUserInfo mUserInfo=mUserInfoDao.selectUserBaseInfo(map);
			if(StringUtil.isNullOrEmpty(mUserInfo)) {
				return 3;
			}
			appNewsInform.setUserId(mUserInfo.getUserId());
		}
		appNewsInform.setIsPush(1);
		appNewsInform.setAddMode(1);
		appNewsInform.setCreaterTime(new Date().getTime());
		return appNewsInformDao.saveInform(appNewsInform);
	}

	@Override
	public AppNewsInform info(Integer id) {
		return appNewsInformDao.selectOne(id);
	}

	@Override
	public Integer update(AppNewsInform appNewsInform) {
		if(appNewsInform.getPushObject()==1) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("mobile", appNewsInform.getMobile());
			MUserInfo mUserInfo=mUserInfoDao.selectUserBaseInfo(map);
			if(StringUtil.isNullOrEmpty(mUserInfo)) {
				return 3;
			}
			appNewsInform.setUserId(mUserInfo.getUserId());
		}
		return appNewsInformDao.update(appNewsInform);
	}

	@Override
	public Integer delete(Integer id) {
		return appNewsInformDao.delete(id);
	}

	@Override
	public PageInfo<AppNewsInform> pageList(AppNewsInform appNewsInform) {
		appNewsInform.setAddMode(1);
		PageHelper.startPage(appNewsInform.getPageNum(), appNewsInform.getPageSize());
		List<AppNewsInform> appNewsInformList =appNewsInformDao.selectList(appNewsInform);
		return new PageInfo<>(appNewsInformList);
	}

	@Override
	public void addPush(AppNewsInform appNewsInform) {
		MUserInfo mUserInfo=mUserInfoDao.selectOne(appNewsInform.getUserId());
	    appNewsInform.setMobile(mUserInfo.getMobile());
		appNewsInform.setPushTime(new Date().getTime());
		appNewsInform.setCreaterTime(new Date().getTime());
		appNewsInform.setPushObject(1);
		appNewsInform.setIsRelease(1);
		appNewsInform.setInformType(1);
		appNewsInform.setIsPush(1);
		appNewsInform.setAddMode(2);
		appNewsInform.setInformUrl("1");
		appNewsInformDao.saveInform(appNewsInform);
	}

	@Override
	public void saveInformList(List<AppNewsInform> appNewsInforms) {
		appNewsInformDao.saveInformList(appNewsInforms);
	}

	@Override
	public Map<String,Object> selectIsRead(String userId) {
		Map<String,Object> data=new HashMap<>();
		int informCount=appNewsInformDao.selectIsRead(userId);
		int newsNotice=appNewsNoticeDao.selectIsReadCount(userId);
		data.put("count", informCount+newsNotice);
		return data;
	}

	@Override
	public int updateRead(Integer id, String userId) {
		return appNewsInformDao.updateRead(id, userId);
	}

	@Override
	public int updateWholeRead(String userId) {
		return appNewsInformDao.updateWholeRead(userId);
	}

	
}
