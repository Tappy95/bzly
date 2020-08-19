package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LNoticeReady;

public interface LNoticeReadyService {
	
	void add(LNoticeReady lNoticeReady);
	
	void remove(LNoticeReady lNoticeReady);
	
	PageInfo<LNoticeReady> list(LNoticeReady lNoticeReady);
	
	long queryExist(LNoticeReady lNoticeReady);
}
