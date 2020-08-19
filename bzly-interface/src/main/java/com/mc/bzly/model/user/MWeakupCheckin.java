package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MWeakupCheckin")
public class MWeakupCheckin extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer chkId;           
	private Integer logId;           
	private String cycleNum;         
	private String checkinUser;      
	private String checkinUserMobile;
	private Long checkinTime;        
	private Double investment;       
	private Integer checkinState;    
	private Double rewardAmount;     

	public Integer getChkId() {
		return chkId;
	}
	public void setChkId(Integer chkId) {
		this.chkId = chkId;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getCheckinUser() {
		return checkinUser;
	}
	public void setCheckinUser(String checkinUser) {
		this.checkinUser = checkinUser;
	}
	public String getCheckinUserMobile() {
		return checkinUserMobile;
	}
	public void setCheckinUserMobile(String checkinUserMobile) {
		this.checkinUserMobile = checkinUserMobile;
	}
	public Long getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(Long checkinTime) {
		this.checkinTime = checkinTime;
	}
	public Double getInvestment() {
		return investment;
	}
	public void setInvestment(Double investment) {
		this.investment = investment;
	}
	public Integer getCheckinState() {
		return checkinState;
	}
	public void setCheckinState(Integer checkinState) {
		this.checkinState = checkinState;
	}
	public Double getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Double rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public String getCycleNum() {
		return cycleNum;
	}
	public void setCycleNum(String cycleNum) {
		this.cycleNum = cycleNum;
	}
	
}
