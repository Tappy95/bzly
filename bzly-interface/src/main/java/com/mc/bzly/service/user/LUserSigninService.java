package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.news.AppNewsNotice;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.model.user.LUserSignin;

public interface LUserSigninService {

	void add(LUserSignin lUserSignin);

	void modify(LUserSignin lUserSignin);

	Result init(LUserSignin lUserSignin);

	Result receiveReward(LUserSignin lUserSignin);

	Map<String, Object> todayFinish(String userId);

	List<TPGame> signinGames(int ptype,String userId, Integer signinId);
	
	Map<String, Object> pageList(LUserSignin lUserSignin);
	
	PageInfo<LUserSignGame> signinGameList(LUserSignGame lUserSignGame);
	
	LUserSignin selectOne(Integer id);
	
	int deleteGame(Integer id);
}
