package com.mc.bzly.dao.lottery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.lottery.MLotteryOrder;

@Mapper
public interface MLotteryOrderDao {
	
	void insert(MLotteryOrder mLotteryOrder);
	
	void update(MLotteryOrder mLotteryOrder);
	
	Map<String, Object> selectOne(Integer id);

	List<MLotteryOrder> selectList(MLotteryOrder mLotteryOrder);
	
	List<Map<String,Object>> selectNews();

	Long userTodayCount(@Param("userId") String userId,@Param("createTime") long createTime,@Param("typeId") Integer typeId);

	Long typeTodayCount(@Param("typeId") Integer typeId,@Param("createTime") long createTime);
	
	MLotteryOrder selectIdOne(Integer id);
	/**
	 * 锁定单个
	 * @param mLotteryOrder
	 * @return
	 */
	int updateLocking(MLotteryOrder mLotteryOrder);
	/**
	 * 锁定全部
	 * @param mLotteryOrders
	 * @return
	 */
	int updateLockingList(List<MLotteryOrder> mLotteryOrders);
	
	MLotteryOrder selectOrder(Integer id);
	/**
	 * 总记录数
	 * @param mLotteryOrder
	 * @return
	 */
	String selectCount(MLotteryOrder mLotteryOrder);
	/**
	 * 当前页统计
	 * @param mLotteryOrder
	 * @return
	 */
	Map<String,String> selectSum(MLotteryOrder mLotteryOrder);
	/**
	 * 统计所有
	 * @param mLotteryOrder
	 * @return
	 */
	Map<String,String> selectCountSum(MLotteryOrder mLotteryOrder);
	
	List<MLotteryOrder> selectExcl(MLotteryOrder mLotteryOrder);
	
	MLotteryOrder selectUserGoods(@Param("id")Integer id,@Param("userId")String userId);
	
	int selectOrderTime(String userId);
	
	int selectUserCount(String userId);
}
