package com.mc.bzly.service.lottery;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.lottery.MLotteryGoods;

public interface MLotteryGoodsService {
	
	int add(MLotteryGoods mLotteryGoods) throws Exception;
	
	int modify(MLotteryGoods mLotteryGoods) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	MLotteryGoods queryOne(Integer id);
	
	PageInfo<MLotteryGoods> queryList(MLotteryGoods mLotteryGoods);

	List<MLotteryGoods> queryByType(Integer typeId);
	
	PageInfo<MLotteryGoods> queryExchangeList(MLotteryGoods mLotteryGoods);
	
}
