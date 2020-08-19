package com.mc.bzly.impl.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserActiveDao;
import com.mc.bzly.model.user.LUserActive;
import com.mc.bzly.service.user.LUserActiveService;

@Service(interfaceClass = LUserActiveService.class,version = WebConfig.dubboServiceVersion)
public class LUserActiveServiceImpl implements LUserActiveService{
	
    @Autowired
	private LUserActiveDao lUserActiveDao;
    
	@Override
	public Integer insert(LUserActive lUserActive) {
		return lUserActiveDao.insert(lUserActive);
	}

	@Override
	public LUserActive selectOne(String userId, String activeTime) {
		return lUserActiveDao.selectOne(userId, activeTime);
	}

}
