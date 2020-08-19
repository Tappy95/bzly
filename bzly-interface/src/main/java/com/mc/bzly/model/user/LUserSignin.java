package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserSignin")
public class LUserSignin extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 签到用户
	private Integer signDay; // 签到日期，第几天
	private Long createTime; // 创建时间
	private Long updateTime; // 修改时间
	private Integer gameCount; // 游戏任务数量 第一天1一个游戏 第2，5，10，15天-2个游戏，其他3个游戏
	private Integer reward; // 奖励金额
	private Integer status; // 状态（1-待补签 2-可进行 3-完成带领取 4-已领取 ）
	
	private Integer accountId;
	
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getSignDay() {
		return signDay;
	}
	public void setSignDay(Integer signDay) {
		this.signDay = signDay;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getGameCount() {
		return gameCount;
	}
	public void setGameCount(Integer gameCount) {
		this.gameCount = gameCount;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
	public LUserSignin() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public LUserSignin(String userId, Integer signDay, Integer gameCount,Long createTime, Integer status,Integer reward) {
		super();
		this.userId = userId;
		this.signDay = signDay;
		this.gameCount = gameCount;
		this.createTime = createTime;
		this.status = status;
		this.reward = reward;
	}
	
	
}
