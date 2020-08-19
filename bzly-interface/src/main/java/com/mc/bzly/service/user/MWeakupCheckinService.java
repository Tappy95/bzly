package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.MWeakupCheckin;

public interface MWeakupCheckinService {

	Result add(MWeakupCheckin mWeakupCheckin) throws Exception;
	
	int modify(MWeakupCheckin mWeakupCheckin) throws Exception;
	
	MWeakupCheckin queryOne(Integer chkId);
	
	PageInfo<MWeakupCheckin> queryList(MWeakupCheckin mWeakupCheckin);

	Result checkin(MWeakupCheckin mWeakupCheckin) throws Exception;
}
