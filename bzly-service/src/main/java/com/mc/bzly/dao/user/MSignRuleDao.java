package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.MSignRule;

@Mapper
public interface MSignRuleDao {
	/**
	 * 添加签到规则
	 * @param mSignRule
	 * @return
	 */
	Integer insert(MSignRule mSignRule);
	/**
	 * 根据连续签到天数，查询签到规则
	 * @return
	 */
	MSignRule selectStickTime(@Param("stickTime")Integer stickTime);
	/**
	 * 后台获取签到规则列表
	 * @return
	 */
	List<MSignRule> selectList(MSignRule mSignRule);
	/**
	 * 查看签到规则详情
	 * @param ruleId
	 * @return
	 */
	MSignRule selectId(@Param("ruleId")Integer ruleId);
	/**
	 * 修改签到规则
	 * @param mSignRule
	 * @return
	 */
	Integer updateRule(MSignRule mSignRule);
	/**
	 * 删除签到规则
	 * @param ruleId
	 * @return
	 */
	Integer deleteRule(@Param("ruleId")Integer ruleId);
	
	List<MSignRule> selectAppList();

}
