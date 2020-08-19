package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LUserGame;

public interface LUserGameService {
	
	PageInfo<TPGame> queryMyTry(LUserGame lUserGame);

	void remove(Integer id,String userId);

	void removeQDZ(Integer lid, String userId);

	String queryTodayFinish(String userId);
}
