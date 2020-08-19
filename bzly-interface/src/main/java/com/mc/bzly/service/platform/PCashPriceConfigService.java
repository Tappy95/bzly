package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PCashPriceConfig;

public interface PCashPriceConfigService {
	
    int save(PCashPriceConfig pCashPriceConfig);
	
    PageInfo<PCashPriceConfig> selectList(PCashPriceConfig pCashPriceConfig);
	
	PCashPriceConfig selectOne(Integer id);
	
	int update(PCashPriceConfig pCashPriceConfig);
	
	int delete(Integer id);
	
	List<PCashPriceConfig> appList(PCashPriceConfig pCashPriceConfig);
	
	List<PCashPriceConfig> appWishList(PCashPriceConfig pCashPriceConfig,String userId);
}
