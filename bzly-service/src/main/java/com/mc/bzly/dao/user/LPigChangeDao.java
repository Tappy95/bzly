package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.MessageList;

@Mapper
public interface LPigChangeDao {
	
	void insert(LPigChange lPigChange);
	
	LPigChange selectOne(Integer id);
	/**
	 * app获取金猪变更列表
	 * @param lCoinChange
	 * @return
	 */
	List<LPigChange> selectList(LPigChange lPigChange);
	/**
	 * 后台获取金猪变更列表
	 * @return
	 */
	List<LPigChange> selectPageList(LPigChange lPigChange);
	
	/**
	 * 查询轮播消息列表
	 * @return
	 */
	List<MessageList> selectMessageList();
	/**
	 * 查询救济金猪领取次数
	 * @param userId
	 * @return
	 */
	int getReliefPig(String userId);
	/**
	 * 获取某个变更方式所有的金猪
	 * @param changedType
	 * @param userId
	 * @return
	 */
	Long selectCountPig(@Param("changedType")Integer changedType,@Param("userId")String userId);
	/**
	 * 渠道获取金猪记录
	 * @param lPigChange
	 * @return
	 */
	List<LPigChange> selectChannelList(LPigChange lPigChange);
	
	List<Map<String,Object>> exclPigChange(LPigChange lPigChange);
	/**
	 * 当前页统计
	 * @param lPigChange
	 * @return
	 */
	List<Map<String,Object>> selectSum(LPigChange lPigChange);
	/**
	 * 全部统计
	 * @param lPigChange
	 * @return
	 */
	List<Map<String,Object>> selectCount(LPigChange lPigChange);

	long selectLastMonthPigWin(@Param("userId")String userId,@Param("startTime") long startTime, @Param("endTime")long endTime);
	
	/**
	 * @param changedType
	 * @param userId
	 * @param changedTime
	 * @return
	 */
	Long selectCountJJ(@Param("changedType")Integer changedType,@Param("userId")String userId,@Param("changedTime") Long changedTime);

	Double selectByChangeTypeRank(@Param("userId")String userId, @Param("startTime") long todayTime);
	
	List<LPigChange> selectPageListNew(LPigChange lPigChange);
	
	List<Map<String,Object>> selectSumNew(LPigChange lPigChange);
	
	List<Map<String,Object>> selectCountNew(LPigChange lPigChange);
	
	List<Map<String,Object>> exclPigChangeNew(LPigChange lPigChange);
}
