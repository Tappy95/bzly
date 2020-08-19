package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LRankPigSimple")
public class LRankPigSimple extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer rankOrder; // 排名
	private String imageUrl; // 头像
	private String mobile; // 电话号码
	private Long pigBalance; // 累计金猪数
	private Long rewardAmount;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getPigBalance() {
		return pigBalance;
	}
	public void setPigBalance(Long pigBalance) {
		this.pigBalance = pigBalance;
	}
	public Long getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Long rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	
}
