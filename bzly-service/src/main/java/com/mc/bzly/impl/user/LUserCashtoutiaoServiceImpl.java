package com.mc.bzly.impl.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserCashtoutiaoDao;
import com.mc.bzly.model.user.LUserCashtoutiao;
import com.mc.bzly.service.user.LUserCashtoutiaoService;

@Service(interfaceClass = LUserCashtoutiaoService.class,version = WebConfig.dubboServiceVersion)
public class LUserCashtoutiaoServiceImpl implements LUserCashtoutiaoService {

	@Autowired
	private LUserCashtoutiaoDao lUserCashtoutiaoDao;
	@Override
	public void add(LUserCashtoutiao lUserCashtoutiao) {
		lUserCashtoutiaoDao.insert(lUserCashtoutiao);
	}

}
