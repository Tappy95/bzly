package com.mc.bzly.service.thirdparty;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.MChannelConfig;

public interface MChannelConfigService {
	/**
	 * 添加渠道配置
	 * @param mChannelConfig
	 * @param mChannelConfigUser
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
	PageInfo<MChannelConfig> selectList(MChannelConfig mChannelConfig);
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
	/**
	 * 验证是否有该游戏的权限
	 * @param userId
	 * @param id
	 * @return
	 */
	int checkGameRight(String userId, Integer id);
	
	/**
	 * 验证是否有该游戏的权限
	 * @param userId
	 * @param name28
	 * @return
	 */
	int check28Right(String userId, String name28);
	/**
	 * 验证是否有28游戏的权限
	 * @param userId
	 * @return
	 */
	Map<String,Object> check28RightNews(String userId);
	
	Map<String,Object> zjdSwitch(String userId);
	
}
