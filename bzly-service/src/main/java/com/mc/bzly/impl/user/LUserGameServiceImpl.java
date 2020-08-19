package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPCallbackDao;
import com.mc.bzly.dao.user.LUserGameDao;
import com.mc.bzly.dao.user.LUserSignGameDao;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LUserGame;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.service.user.LUserGameService;

@Service(interfaceClass = LUserGameService.class,version = WebConfig.dubboServiceVersion)
public class LUserGameServiceImpl implements LUserGameService {

	@Autowired
	private LUserGameDao lUserGameDao;
	@Autowired
	private LUserSignGameDao lUserSignGameDao;
	@Autowired
	private TPCallbackDao tpCallbackDao;
	
	@Override
	public PageInfo<TPGame> queryMyTry(LUserGame lUserGame) {
		PageHelper.startPage(lUserGame.getPageNum(), lUserGame.getPageSize());
		List<TPGame> lUserGameList = lUserGameDao.selectMyTry(lUserGame.getUserId());
		return new PageInfo<>(lUserGameList);
	}

	@Override
	public void remove(Integer id,String userId) {
		lUserGameDao.delete(id,userId);
	}

	@Override
	public void removeQDZ(Integer lid, String userId) {
		LUserSignGame lUserSignGame = new LUserSignGame();
		lUserSignGame.setId(lid);
		lUserSignGame.setIsHide(2);
		lUserSignGame.setUserId(userId);
		lUserSignGameDao.hideUpdate(lUserSignGame);
	}

	@Override
	public String queryTodayFinish(String userId) {
		long now = new Date().getTime();
		now = now - now % 86400000;
		long count = tpCallbackDao.selectTodayFinish(userId,now);
		return count+"";
	}

}
