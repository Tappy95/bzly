package com.mc.bzly.service.thirdparty;

import java.util.List;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.thirdparty.MChannel;

public interface MChannelService {

	Result add(MChannel mChannel) throws Exception;
	
	List<MChannel> queryList();

}
