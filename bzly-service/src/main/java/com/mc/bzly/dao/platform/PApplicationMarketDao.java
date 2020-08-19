package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PApplicationMarket;

@Mapper
public interface PApplicationMarketDao {
	/**
	 * 添加应用市场
	 * @param pApplicationMarket
	 * @return
	 */
	Integer save(PApplicationMarket pApplicationMarket);
	/**
	 * 应用市场列表
	 * @param pApplicationMarket
	 * @return
	 */
	List<PApplicationMarket> selectList(PApplicationMarket pApplicationMarket);
	/**
	 * 市场详情
	 * @param id
	 * @return
	 */
	PApplicationMarket selectOne(@Param("id")Integer id);
	/**
	 * 修改市场
	 * @param pApplicationMarket
	 * @return
	 */
	Integer update(PApplicationMarket pApplicationMarket);
	/**
	 * 删除市场
	 * @param id
	 * @return
	 */
	Integer delete(@Param("id")Integer id);

}
