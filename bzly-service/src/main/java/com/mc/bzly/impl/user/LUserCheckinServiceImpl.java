package com.mc.bzly.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserCheckinDao;
import com.mc.bzly.model.user.LUserCheckin;
import com.mc.bzly.service.user.LUserCheckinService;

@Service(interfaceClass = LUserCheckinService.class,version = WebConfig.dubboServiceVersion)
public class LUserCheckinServiceImpl implements LUserCheckinService {

	@Autowired
	private LUserCheckinDao lUserCheckinDao;
	
	@Transactional
	@Override
	public int add(LUserCheckin lUserCheckin) {
		lUserCheckinDao.insert(lUserCheckin);
		return 1;
	}

	@Transactional
	@Override
	public int modify(LUserCheckin lUserCheckin) throws Exception {
		lUserCheckinDao.update(lUserCheckin);
		return 1;
	}

	@Override
	public LUserCheckin queryOne(String userId) {
		return lUserCheckinDao.selectOne(userId);
	}

	@Override
	public PageInfo<LUserCheckin> queryList(LUserCheckin lUserCheckin) {
		PageHelper.startPage(lUserCheckin.getPageNum(), lUserCheckin.getPageSize());
		List<LUserCheckin> lUserCheckinList = lUserCheckinDao.selectList(lUserCheckin);
		return new PageInfo<>(lUserCheckinList);
	}

}
