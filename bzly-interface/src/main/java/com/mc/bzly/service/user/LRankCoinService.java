package com.mc.bzly.service.user;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LRankCoin;

public interface LRankCoinService {
	
	PageInfo<LRankCoin> list(LRankCoin lRankCoin) throws Exception;

	PageInfo<LRankCoin> listF(LRankCoin lRankCoin);

	Map<String, Object> queryHisMy(LRankCoin lRankCoin, String userId) throws Exception ;
}
