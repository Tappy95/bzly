package com.mc.bzly.service.platform;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PApplicationMarket;

public interface PApplicationMarketService {
	 
	Integer save(PApplicationMarket pApplicationMarket);
	 
	PageInfo<PApplicationMarket> selectList(PApplicationMarket pApplicationMarket);
	 
	PApplicationMarket selectOne(Integer id);
	 
	Integer update(PApplicationMarket pApplicationMarket);
	 
	Integer delete(Integer id);
}
