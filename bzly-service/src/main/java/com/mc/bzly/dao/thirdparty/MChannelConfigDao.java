package com.mc.bzly.dao.thirdparty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.MChannelConfig;

@Mapper
public interface MChannelConfigDao {
	/**
	 * 添加渠道配置
	 * @param mFissionScheme
	 * @return
	 */
	int insert(MChannelConfig mChannelConfig);
	/**
	 * 获取渠道配置详情
	 * @param id
	 * @return
	 */
	MChannelConfig selectOne(Integer id);
	/**
	 * 渠道配置列表
	 * @param mFissionScheme
	 * @return
	 */
	List<MChannelConfig> selectList(MChannelConfig mChannelConfig);
	/**
	 * 修改渠道配置
	 * @param mFissionScheme
	 * @return
	 */
	int update(MChannelConfig mChannelConfig);
	/**
	 * 删除渠道配置
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	
	MChannelConfig selectByChannelCode(String channelCode);
	
	int selectFissionId(int fissionId);
	
}
