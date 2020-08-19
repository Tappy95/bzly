package com.mc.bzly.service.thirdparty;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;

public interface MChannelConfigUserService {

	int add(MChannelConfigUser mChannelConfigUser) throws Exception;
	
	int modify(MChannelConfigUser mChannelConfigUser) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	MChannelConfigUser queryOne(Integer id);
	
	PageInfo<MChannelConfigUser> queryList(MChannelConfigUser mChannelConfigUser);

	List<MChannelConfigUser> queryListNoPage(MChannelConfigUser mChannelConfigUser);
}
