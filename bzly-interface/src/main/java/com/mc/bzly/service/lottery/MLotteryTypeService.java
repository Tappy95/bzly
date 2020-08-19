package com.mc.bzly.service.lottery;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.lottery.MLotteryType;

public interface MLotteryTypeService {
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
	PageInfo<MLotteryType> selectPageList(MLotteryType mLotteryType);
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
	 * 抽奖首页
	 * 1.奖品列表
	 * 2.类型信息
	 * 3.用户今日抽奖次数
	 * @return
	 */
	Map<String, Object> home();
	
	Map<String, Object> selectTypeOne();
}
