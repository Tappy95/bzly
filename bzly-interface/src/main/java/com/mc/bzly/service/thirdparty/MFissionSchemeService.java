package com.mc.bzly.service.thirdparty;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.MFissionScheme;

public interface MFissionSchemeService {
	/**
	 * 添加裂变方案
	 * @param mFissionScheme
	 * @return
	 */
	int insert(MFissionScheme mFissionScheme);
	/**
	 * 获取裂变方案详情
	 * @param id
	 * @return
	 */
	MFissionScheme selectOne(Integer id);
	/**
	 * 裂变方案列表
	 * @param mFissionScheme
	 * @return
	 */
	PageInfo<MFissionScheme> selectList(MFissionScheme mFissionScheme);
	/**
	 * 修改裂变方案
	 * @param mFissionScheme
	 * @return
	 */
	int update(MFissionScheme mFissionScheme);
	/**
	 * 删除裂变方案
	 * @param id
	 * @return
	 */
	int delete(Integer id);
	/**
	 * 后台获取裂变方案下拉列表
	 * @return
	 */
	List<MFissionScheme> selectDownList();
	/**
	 * 前端获取方案图
	 * @param userId
	 * @return
	 */
	Map<String,Object> selectChannelCodeOne(String userId);
}
