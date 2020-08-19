package com.mc.bzly.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LNoticeReadyDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LNoticeReady;
import com.mc.bzly.service.user.LNoticeReadyService;

@Service(interfaceClass = LNoticeReadyService.class,version = WebConfig.dubboServiceVersion)
public class LNoticeReadyServiceImpl implements LNoticeReadyService {

	@Autowired
	private LNoticeReadyDao lNoticeReadyDao;
	
	@Override
	public void add(LNoticeReady lNoticeReady) {
		lNoticeReadyDao.insert(lNoticeReady);
	}

	@Override
	public void remove(LNoticeReady lNoticeReady) {
		lNoticeReadyDao.delete(lNoticeReady);
	}

	@Override
	public PageInfo<LNoticeReady> list(LNoticeReady lNoticeReady) {
		PageHelper.startPage(lNoticeReady.getPageNum(), lNoticeReady.getPageSize());
		List<LNoticeReady> lists = lNoticeReadyDao.selectList(lNoticeReady);
		return new PageInfo<>(lists);
	}

	@Override
	public long queryExist(LNoticeReady lNoticeReady) {
		return lNoticeReadyDao.selectCount(lNoticeReady);
	}
}
