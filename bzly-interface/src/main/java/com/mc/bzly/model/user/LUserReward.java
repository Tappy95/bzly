package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserReward")
public class LUserReward extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer rewardId;       

	private String userId;          

	private Integer rewardType;     

	private Double money;           

	private Long rewardTime;        

	private String provide;         

	private String apprenticeMobile;

	public Integer getRewardId() {
		return rewardId;
	}

	public void setRewardId(Integer rewardId) {
		this.rewardId = rewardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getRewardTime() {
		return rewardTime;
	}

	public void setRewardTime(Long rewardTime) {
		this.rewardTime = rewardTime;
	}

	public String getProvide() {
		return provide;
	}

	public void setProvide(String provide) {
		this.provide = provide;
	}

	public String getApprenticeMobile() {
		return apprenticeMobile;
	}

	public void setApprenticeMobile(String apprenticeMobile) {
		this.apprenticeMobile = apprenticeMobile;
	}
	
}
