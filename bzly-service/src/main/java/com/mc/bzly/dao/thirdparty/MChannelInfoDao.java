package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.MChannelInfo;

@Mapper
public interface MChannelInfoDao {

	void insert(MChannelInfo mChannelInfo);
	
	void update(MChannelInfo mChannelInfo);
	
	void delete(Integer id);
	
	MChannelInfo selectOne(Integer id);
	
	List<MChannelInfo> selectList(MChannelInfo mChannelInfo);

	MChannelInfo selectDownloadUrl(@Param("channelCode")String channelCode);
	/**
	 * 后台获取渠道下拉列
	 * @return
	 */
	List<Map<String,Object>> selectDownList();

	MChannelInfo selectByChannelCode(@Param("channelCode")String channelCode);
	/**
	 * 根据订单号获取渠道详情
	 * @param outTradeNo
	 * @return
	 */
	MChannelInfo selectCode(String outTradeNo);
	/**
	 * 后台渠道设置页面获取渠道下拉列表
	 * @return
	 */
	List<Map<String,Object>> selectChannelDownList();
	/**
	 * 后台版本管理页面获取渠道下拉列表
	 * @return
	 */
	List<Map<String,Object>> selectVersionDownList();

	
}