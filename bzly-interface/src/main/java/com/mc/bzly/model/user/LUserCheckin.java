package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 用户打卡统计
 */
@Alias("LUserCheckin")
public class LUserCheckin extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId; 
	
	private Double totalInvestment; 
	
	private Integer totalSuccess; 
	
	private Integer totalFailure; 
	
	private Double totalReward; 

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public Integer getTotalSuccess() {
		return totalSuccess;
	}

	public void setTotalSuccess(Integer totalSuccess) {
		this.totalSuccess = totalSuccess;
	}

	public Integer getTotalFailure() {
		return totalFailure;
	}

	public void setTotalFailure(Integer totalFailure) {
		this.totalFailure = totalFailure;
	}

	public Double getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(Double totalReward) {
		this.totalReward = totalReward;
	}
	
}
