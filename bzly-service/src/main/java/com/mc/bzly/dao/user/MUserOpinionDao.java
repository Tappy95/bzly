package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MUserOpinion;

@Mapper
public interface MUserOpinionDao {
	/**
	 * 添加意见反馈
	 * @param mUserOpinion
	 * @return
	 */
	Integer saveOpinion(MUserOpinion mUserOpinion);
	/**
	 * 意见反馈列表
	 * @return
	 */
	List<MUserOpinion> selectList(MUserOpinion mUserOpinion);
	/**
	 * 意见详情
	 * @param id
	 * @return
	 */
	MUserOpinion selectOne(@Param("id")Integer id);
	/**
	 * 修改意见反馈
	 * @param mUserOpinion
	 * @return
	 */
	Integer update(MUserOpinion mUserOpinion);

}
