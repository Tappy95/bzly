package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.model.user.LUserActivity;

public interface LUserActivityService {
	
	Map<String, Object> myActivity(String userId);

	/**
	 * 修改质量分数和活跃度
	 * @param lUserActivity
	 */
	void modifyScore(LUserActivity lUserActivity);
	/**
	 * 打开活跃展示
	 * @param userId
	 * @param openActivity
	 */
	void openActivity(String userId, Integer openActivity);
}
