package com.mc.bzly.dao.lottery;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.lottery.MLotteryType;

@Mapper
public interface MLotteryTypeDao {
	/**
	 * 添加抽奖类型
	 * @param mLotteryType
	 * @return
	 */
	Integer insert(MLotteryType mLotteryType);
	/**
	 * 获取详情
	 * @param id
	 * @return
	 */
	MLotteryType selectOne(Integer id);
	/**
	 * 分页获取列表
	 * @param mLotteryType
	 * @return
	 */
	List<MLotteryType> selectPageList(MLotteryType mLotteryType);
	/**
	 * 下拉框获取列表
	 * @return
	 */
	List<MLotteryType> selectList();
	/**
	 * 修改
	 * @param mLotteryType
	 * @return
	 */
	Integer update(MLotteryType mLotteryType);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);

	/**
	 * 查询正在使用的积分抽奖类型信息
	 * @return
	 */
	MLotteryType selectEnableType();
	
	MLotteryType selectTypeOne();
}
