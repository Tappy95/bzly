package com.mc.bzly.service.lottery;

import java.util.List;
import java.util.Map;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.lottery.MLotteryOrder;

public interface MLotteryOrderService {
	
	Result clickLottery(MLotteryOrder mLotteryOrder,String password) throws Exception;
	
	int modify(MLotteryOrder mLotteryOrder) throws Exception;
	
	Map<String,Object> queryOne(Integer id);
	
	Map<String, Object> queryList(MLotteryOrder mLotteryOrder);
	
	List<Map<String,Object>> orderNews();
	/**
	 * app获取奖品详情
	 * @param id
	 * @return
	 */
	Map<String,Object> appQueryOne(Integer id);
    /**
     * 获取卡密
     * @return
     */
	Map<String,Object> getCardPassword(String payPassword,String userId,Integer id);

	Long userOrderCount(Integer typeId,String userId);
	
    int updateLocking(MLotteryOrder mLotteryOrder);
	
	int updateLockingList(String ids,String admin);
	
	int relieveLocking(Integer id,String admin);
	
	List<MLotteryOrder> selectExcl(MLotteryOrder mLotteryOrder);
	
	Result userExchangeGoods(String userId,Integer id,String password);
	
	Result addressBinding(String userId,Integer orderId,Integer addressId);
	
	Map<String, Object> appQueryList(MLotteryOrder mLotteryOrder);
}
