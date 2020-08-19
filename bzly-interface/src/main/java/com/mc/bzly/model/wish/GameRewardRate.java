package com.mc.bzly.model.wish;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("GameRewardRate")
public class GameRewardRate extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer rewardType; // 奖品类型（1-现金 2-分红心）
	private Long rewardNum; // 奖励数量
	private Integer rate; // 概率
	private Long createTime; // 创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Long getRewardNum() {
		return rewardNum;
	}
	public void setRewardNum(Long rewardNum) {
		this.rewardNum = rewardNum;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
