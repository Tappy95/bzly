package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.model.user.LDarenRewardDetail;

public interface LDarenRewardDetailService {
	
	Map<String, Object> list(LDarenRewardDetail lDarenRewardDetail);

	Map<String, Object> listF(String userId, String accountId,Integer pageSize,Integer pageNum);
}
