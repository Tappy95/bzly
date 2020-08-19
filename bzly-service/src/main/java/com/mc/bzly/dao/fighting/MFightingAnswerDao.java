package com.mc.bzly.dao.fighting;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.fighting.MFightingAnswer;

@Mapper
public interface MFightingAnswerDao {
	/**
	 * 添加答案
	 * @param mFightingAnswer
	 * @return
	 */
	Integer save(MFightingAnswer mFightingAnswer);
	/**
	 * 根据题目id查询答案
	 * @param questionId
	 * @return
	 */
	List<MFightingAnswer> selectQuestionId(@Param("questionId")Integer questionId);
	/**
	 * 修改答案
	 * @param mFightingAnswer
	 * @return
	 */
	Integer update(MFightingAnswer mFightingAnswer);
	/**
	 * 删除答案
	 * @param ansId
	 * @return
	 */
	Integer delete(@Param("ansId")Integer ansId);
	/**
	 * 根据题目id删除答案
	 * @param questionId
	 * @return
	 */
	Integer deleteQuestionId(@Param("questionId")Integer questionId);
	
	MFightingAnswer selectOne(@Param("ansId")Integer ansId);

}
