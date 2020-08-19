package com.mc.bzly.service.thirdparty;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.MDarenReward;

public interface MDarenRewardService {
	
	int save(MDarenReward mDarenReward);
	
	PageInfo<MDarenReward> selectList(MDarenReward mDarenReward);

	MDarenReward selectOne(int id);
	
	int update(MDarenReward mDarenReward);
	
	int delete(int id);

}
