package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.RSUserPassbook;

@Mapper
public interface RSUserPassbookDao {

	void batchInsert(List<RSUserPassbook> rsUserPassbook);
	
	/**
	 * 修改用户优惠券
	 * @param rsUserPassbook
	 */
	void update(RSUserPassbook rsUserPassbook);
	
	/**
	 * 添加优惠券
	 * @param rsUserPassbook
	 */
	void insert(RSUserPassbook rsUserPassbook);
	
	/**
	 * 根据用户+卡券id查询
	 * @param rsUserPassbook
	 * @return
	 */
	RSUserPassbook selectByExample(RSUserPassbook rsUserPassbook);
	
	/**
	 * 查询未过期的卡券
	 */
	List<RSUserPassbook> selectListByExample(RSUserPassbook rsUserPassbook);
	
	/**
	 * 批量修改卡券状态
	 * @param rsUserPassbook
	 */
	void batchUpdate(List<RSUserPassbook> rsUserPassbook);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	RSUserPassbook selectOne(@Param("id")Integer id);
	/**
	 * app获取卡卷列表
	 * @param userId
	 * @return
	 */
	List<RSUserPassbook> selectAppList(RSUserPassbook rsUserPassbook);
	
	/**
	 * 根据
	 * @param userId
	 * @param passbookType
	 * @return
	 */
	List<Map<String, Object>> selectByUser(@Param("userId")String userId,@Param("passbookType") Integer passbookType);
	/**
	 * 获取用户所有的折扣券
	 * @param userId
	 * @return
	 */
	List<RSUserPassbook> selectDiscount(@Param("userId")String userId);
	/**
	 * 获取加成券
	 * @param userId
	 * @return
	 */
	RSUserPassbook selectAddDiscount(@Param("userId")String userId);
	
	int selectAddDiscountCount(@Param("userId")String userId);
	
}
