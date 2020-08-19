package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.thirdparty.MFissionScheme;

@Mapper
public interface MFissionSchemeDao {
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
	List<MFissionScheme> selectList(MFissionScheme mFissionScheme);
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
	
	List<MFissionScheme> selectDownList();
	/**
	 * 前端获取方案图
	 * @param channelCode
	 * @return
	 */
	Map<String,Object> selectChannelCodeOne(String channelCode);
	
	MFissionScheme selectSchemeByChannelCode(String channelCode);

}
