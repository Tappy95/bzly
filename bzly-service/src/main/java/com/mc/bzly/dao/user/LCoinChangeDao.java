package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.MessageList;
import com.mc.bzly.model.user.UserWithdrawals;

@Mapper
public interface LCoinChangeDao {
	
	void insert(LCoinChange lCoinChange);
	
	LCoinChange selectOne(Integer id);
	/**
	 * app获取金币变更列表
	 * @param lCoinChange
	 * @return
	 */
	List<LCoinChange> selectList(LCoinChange lCoinChange);
	/**
	 * 后台获取金币变更列表
	 * @return
	 */
	List<LCoinChange> selectPageList(LCoinChange lCoinChange);
	
	/**
	 * 查询轮播消息列表
	 * @return
	 */
	List<MessageList> selectMessageList();
	
	/**
	 * 批量增加金币记录
	 * @param lCoinChanges
	 */
	void batchInsert(List<LCoinChange> lCoinChanges);
	
	/**
	 * 根据类型查看金币变更记录
	 * @param userId
	 * @param changeType
	 * @return
	 */
	List<LCoinChange> selectByChangeType(@Param("userId")String userId,@Param("changedType") Integer changedTime);
	
	void update(LCoinChange lCoinChange);

	List<UserWithdrawals> selectWithdrawals(UserWithdrawals userWithdrawals);

	UserWithdrawals selectWithdrawalsInfo(Integer id);

	/**
	 * 查询今日用户阅读资讯获得金币数
	 * @param userId
	 * @param totayMillisecond
	 * @param coinChangedType13
	 * @return
	 */
	double selectUserReadZXRewardSum(@Param("userId")String userId,@Param("changedTime") long changedTime);

	/**
	 * 查询今日用户阅读资讯获得金币数
	 * @param userId
	 * @param totayMillisecond
	 * @param coinChangedType13
	 * @return
	 */
	double selectUserReadRewardSum(@Param("userId")String userId,@Param("changedTime") long changedTime,@Param("changedType")Integer type);
	
	/**
	 * 获取从徒弟那边得到的奖励
	 * @param userId
	 * @return
	 */
	double getApprenticeReward(String userId);

	List<LCoinChange> getApprenticeRewardDetail(LCoinChange lCoinChange);
	/**
	 * 根据userId变更类型变更时间查询用户金币变动记录
	 * @param userId
	 * @param changedType
	 * @param changedTime
	 * @return
	 */
	LCoinChange selectByChangeTypeTime(@Param("userId")String userId,@Param("changedType")Integer changedType,@Param("changedTime") long changedTime);
	/**
	 * 渠道回去用户金币变动信息
	 * @param lCoinChange
	 * @return
	 */
	List<LCoinChange> selectChannelList(LCoinChange lCoinChange);
	
	List<Map<String,Object>> exclCoinChange(LCoinChange lCoinChange);
	/**
	 * 查询团队贡献
	 * @param userId
	 * @return
	 */
	String selectGroupContribution(String userId);
	/**
	 * 查看某类型总收益
	 * @param changedType
	 * @param userId
	 * @return
	 */
	long selectTeamSum(@Param("changedType")Integer changedType,@Param("userId")String userId);
	/**
	 * 查看某类型总收益
	 * @param changedType
	 * @param userId
	 * @return
	 */
	long selectMyReward(@Param("userId")String userId);

	List<MessageList> selectqdzList();

	long selectSumByUser(@Param("userId")String userId, @Param("time")long time, @Param("changedType")Integer changedType);
	/**
	 * 总收入支出
	 * @param lCoinChange
	 * @return
	 */
	List<Map<String,Object>> selectCount(LCoinChange lCoinChange);
	/**
	 * 当前页收入支出
	 * @param lCoinChange
	 * @return
	 */
	List<Map<String,Object>> selectSum(LCoinChange lCoinChange);
	
	void insertCoin(LCoinChange lCoinChange);
	
	/**
	 * 查看某类型总收益
	 * @param changedType
	 * @param userId
	 * @return
	 */
	long selectTeamSumNew(@Param("userId")String userId);

	List<MessageList> selectkszList();
	
	int selectWXcount(String userId);

	long selectDRReward(String userId);
	
	long selectSignSum(String userId);

	long selectUserXYXRewardSum(@Param("userId")String userId,@Param("changedTime") long totayMillisecond);

	Double selectByChangeTypeRank(@Param("userId")String userId,@Param("changedTime") long todayTime);
	
	List<LCoinChange> selectPageListNew(LCoinChange lCoinChange);
	
	List<Map<String,Object>> selectCountNew(LCoinChange lCoinChange);

	List<Map<String,Object>> selectSumNew(LCoinChange lCoinChange);
	
	List<Map<String,Object>> exclCoinChangeNew(LCoinChange lCoinChange);
	
	Map<String,Object> selectCountNewRevenue(LCoinChange lCoinChange);
	
	Map<String,Object> selectCountNewExpend(LCoinChange lCoinChange);

	List<LCoinChange> selectListXYZ(LCoinChange lCoinChange);

	/**
	 * 高额赚VIP购买奖励
	 * @param userId
	 * @return
	 */
	long selecthHighVipAmount(String userId);

    long selecthHighVipCount(String userId);

	List<Map<String, Object>> listHighVip(@Param("userId")String userId,@Param("pageIndex") Integer pageIndex,@Param("pageSize") Integer pageSize);

	long countHighVip(@Param("userId")String userId);
}
