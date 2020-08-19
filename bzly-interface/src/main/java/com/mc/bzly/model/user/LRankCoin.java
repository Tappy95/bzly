package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LRankCoin")
public class LRankCoin extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer rankType; // 排行榜类型（1-天榜 2-周榜 3-月榜 4-年榜 5-总榜）
	private Integer rankOrder; // 排名
	private String imageUrl; // 头像
	private String aliasName; // 用户别名
	private String mobile; // 电话号码
	private String userId; // 用户id
	private Long coinBalance; // 累计金币数
	private String rankDate; // 排名时间
	private Long createTime; // 创建时间
	private Integer realData; // 是否真实用户（1-是 2-不是）
	private Long rewardAmount; // 奖励金币数
	
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
	public Integer getRealData() {
		return realData;
	}
	public void setRealData(Integer realData) {
		this.realData = realData;
	}
	public Long getCoinBalance() {
		return coinBalance;
	}
	public void setCoinBalance(Long coinBalance) {
		this.coinBalance = coinBalance;
	}
	public Long getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Long rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public LRankCoin(Integer rankType, Integer rankOrder, String imageUrl, String aliasName, String mobile,
			Long coinBalance, String rankDate, Long createTime, String userId, Integer realData) {
		this.rankType = rankType;
		this.rankOrder = rankOrder;
		this.imageUrl = imageUrl;
		this.aliasName = aliasName;
		this.mobile = mobile;
		this.coinBalance = coinBalance;
		this.rankDate = rankDate;
		this.createTime = createTime;
		this.userId = userId;
		this.realData = realData;
	}
	public LRankCoin() {
	}
}
