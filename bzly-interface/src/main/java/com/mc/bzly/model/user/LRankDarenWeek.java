package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LRankDarenWeek")
public class LRankDarenWeek extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 达人id
	private Integer apprenticeCount; // 徒弟数量
	private Integer rank; // 排名
	private String rankCycle; // 排行周期
	private Long reward; // 奖励金币数
	private String createDate; // 排名时间
	private Integer status;//是否结算（1-未结算 2-已结算）
	
	private Integer pageIndex;
	private String aliasName;
	private String profile;
	
	private Integer accountId;
	
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
	public Integer getApprenticeCount() {
		return apprenticeCount;
	}
	public void setApprenticeCount(Integer apprenticeCount) {
		this.apprenticeCount = apprenticeCount;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getRankCycle() {
		return rankCycle;
	}
	public void setRankCycle(String rankCycle) {
		this.rankCycle = rankCycle;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
