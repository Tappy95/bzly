package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LDarenReward;

public interface LDarenRewardService {
	
	PageInfo<LDarenReward> list(LDarenReward lDarenReward);
}
