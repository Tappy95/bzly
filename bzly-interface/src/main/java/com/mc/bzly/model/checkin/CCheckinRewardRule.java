package com.mc.bzly.model.checkin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
@Alias("CCheckinRewardRule")
public class CCheckinRewardRule extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer minNumber;//参与打卡最小人数
	private Integer maxNumber;//参与打卡最大人数
	private Integer rewardRatio;//奖励比例（%）
	private Long extraReward;//额外奖励（单位：金币）
	private Long createTime;//创建时间
	private Long minReward;//最小奖励
	private Long maxReward;//最大奖励
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMinNumber() {
		return minNumber;
	}
	public void setMinNumber(Integer minNumber) {
		this.minNumber = minNumber;
	}
	public Integer getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}
	public Integer getRewardRatio() {
		return rewardRatio;
	}
	public void setRewardRatio(Integer rewardRatio) {
		this.rewardRatio = rewardRatio;
	}
	public Long getExtraReward() {
		return extraReward;
	}
	public void setExtraReward(Long extraReward) {
		this.extraReward = extraReward;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getMinReward() {
		return minReward;
	}
	public void setMinReward(Long minReward) {
		this.minReward = minReward;
	}
	public Long getMaxReward() {
		return maxReward;
	}
	public void setMaxReward(Long maxReward) {
		this.maxReward = maxReward;
	}
}
