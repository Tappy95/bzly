package com.mc.bzly.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.lottery.MLotteryGoods;

@Mapper
public interface MLotteryGoodsDao {
	
	void insert(MLotteryGoods mLotteryGoods);
	
	void update(MLotteryGoods mLotteryGoods);
	
	void delete(Integer id);
	
	MLotteryGoods selectOne(Integer id);

	List<MLotteryGoods> selectList(MLotteryGoods mLotteryGoods);
	
	/**
	 * 查询供抽奖的商品
	 * @param typeId
	 * @return
	 */
	List<MLotteryGoods> selectGoodsList(Integer typeId);
	
	List<MLotteryGoods> selectExchangeList(MLotteryGoods mLotteryGoods);
	
	int updateNumberAdd(Integer id);
	
	int updateNumberReduce(Integer id);
}
