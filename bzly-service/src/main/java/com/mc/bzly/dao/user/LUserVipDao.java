package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserVip;

@Mapper
public interface LUserVipDao {
	
	int insert(LUserVip lUserVip);
	
	int update(LUserVip lUserVip);
	
	int delete(Integer id);
	
	LUserVip selectOne(Integer id);
	
	List<LUserVip> selectList(LUserVip lUserVip);
	
	/**
	 * 批量修改VIP状态
	 * @param lUserVip
	 */
	void batchUpdate(List<LUserVip> lUserVip);
	/**
	 * 查询用户vip是否过期
	 * @param userId
	 * @param vipId
	 * @return
	 */
	int selectVip(@Param("userId")String userId,@Param("vipId")Integer vipId);
	/**
	 * 获取vip购买滚动消息
	 * @return
	 */
	List<Map<String,String>> selectVipNews(@Param("channelCode")String channelCode);
	/**
	 * 根据用户查询他的VIP列表，加成倒叙排列
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectByUser(@Param("userId")String userId);
	/**
	 * 获取所有用户开通的vip
	 * @param userId
	 * @return
	 */
	List<LUserVip> selectIdList(@Param("userId")String userId);
	/**
	 * 查看用户最后一次过期的vip
	 * @param userId
	 * @param vipId
	 * @return
	 */
	LUserVip selectOverdue(@Param("userId")String userId,@Param("vipId")Integer vipId);

	/**
	 * 根据用户查询他的VIP列表，加成倒叙排列
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> selectByUserOrder(@Param("userId")String userId);
	/**
	 * 获取用户购买了几种vip
	 * @param userId
	 * @return
	 */
	List<Integer> selectVipUserNum(@Param("userId")String userId);

	List<Map<String, Object>> selectByUserAll(String userId);
	/**
	 * 查询vip充值次数
	 * @param userId
	 * @return
	 */
	int selectCountVip(@Param("userId")String userId);
	/**
	 * 查询最高的救济金猪
	 * @param userId
	 * @return
	 */
	Map<String,Object> selectReliefPig(String userId);
	/**
	 * 查询金币换金猪的加成
	 * @param userId
	 * @return
	 */
	Double pigAddition(String userId);
	/**
	 * 查询用户未到期的最大vip
	 * @param userId
	 * @return
	 */
	String selectMaxVip(@Param("userId")String userId);
	
	int selectUserCount(String userId);
	
	LUserVip selectUserVip(@Param("userId")String userId,@Param("vipId")int vipId);
	
	/**
	 * 查看下级购买高额赚VIP人数
	 * @param userId
	 * @return
	 */
	long selectOpenHighVipCount(@Param("userId")String userId);
	
	/**
	 * 查看是否购买了高额赚VIP
	 * @param userId
	 * @return
	 */
	long selectHighVipHasBuy(String userId);
}