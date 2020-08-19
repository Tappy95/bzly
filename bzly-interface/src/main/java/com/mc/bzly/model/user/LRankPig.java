package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LRankPig")
public class LRankPig extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer rankType; // 排行榜类型（1-天榜 2-周榜 3-月榜 4-年榜 5-总榜）
	private Integer rankOrder; // 排名
	private String imageUrl; // 头像
	private String aliasName; // 用户别名
	private String mobile; // 电话号码
	private Long pigBalance; // 累计金猪数
	private String rankDate; // 排名时间
	private Long createTime; // 创建时间
	private String userId; // 用户id
	private String realData; // 是否真实用户（1-是 2-不是）
	private Long rewardAmount;// 金猪奖励
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRankType() {
		return rankType;
	}
	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
	public Integer getRankOrder() {
		return rankOrder;
	}
	public void setRankOrder(Integer rankOrder) {
		this.rankOrder = rankOrder;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public Long getPigBalance() {
		return pigBalance;
	}
	public void setPigBalance(Long pigBalance) {
		this.pigBalance = pigBalance;
	}
	public String getRankDate() {
		return rankDate;
	}
	public void setRankDate(String rankDate) {
		this.rankDate = rankDate;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRealData() {
		return realData;
	}
	public void setRealData(String realData) {
		this.realData = realData;
	}
	public Long getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Long rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	
}
