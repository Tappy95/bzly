package com.mc.bzly.impl.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserSignGameDao;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.service.user.LUserSignGameService;

@Service(interfaceClass = LUserSignGameService.class,version = WebConfig.dubboServiceVersion)
public class LUserSignGameServiceImpl implements LUserSignGameService {

	@Autowired
	private LUserSignGameDao lUserSignGameDao;
	@Override
	public void add(LUserSignGame lUserSignGame) {
		lUserSignGame.setCreateTime(new Date().getTime());
		lUserSignGame.setStatus(1);
		lUserSignGameDao.insert(lUserSignGame);
	}

	@Override
	public void modify(LUserSignGame lUserSignGame) {

	}

}
