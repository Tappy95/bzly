package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PBanner;

public interface PBannerService {

	int add(PBanner pBanner) throws Exception;
	
	int modify(PBanner pBanner) throws Exception;
	
	int remove(Integer bannerId) throws Exception;
	
	PBanner queryOne(Integer bannerId);
	
	PageInfo<PBanner> queryList(PBanner pBanner);

	List<PBanner> queryBanner(Integer position);
}
