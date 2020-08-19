package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.MUserOpinion;

public interface MUserOpinionService {
	/**
	 * 添加意见反馈
	 * @param mUserOpinion
	 * @return
	 */
	Integer saveOpinion(MUserOpinion mUserOpinion,String userId);
	/**
	 * 意见反馈列表
	 * @return
	 */
	PageInfo<MUserOpinion> selectList(MUserOpinion mUserOpinion);
	/**
	 * 意见详情
	 * @param id
	 * @return
	 */
	MUserOpinion selectOne(Integer id);
	/**
	 * 修改意见反馈
	 * @return
	 */
	Integer update(MUserOpinion mUserOpinion);
}
