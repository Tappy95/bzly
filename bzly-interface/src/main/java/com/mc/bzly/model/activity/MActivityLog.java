package com.mc.bzly.model.activity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MActivityLog")
public class MActivityLog extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer lId;
	private Integer activityId; 
	private String cycleNum; 
	private Integer participantsNum; 
	private Double participantsAmount; 
	private Double allocationAmount; 
	private Integer successNum; 
	private Integer failureNum; 
	
	public Integer getlId() {
		return lId;
	}
	public void setlId(Integer lId) {
		this.lId = lId;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getCycleNum() {
		return cycleNum;
	}
	public void setCycleNum(String cycleNum) {
		this.cycleNum = cycleNum;
	}
	public Integer getParticipantsNum() {
		return participantsNum;
	}
	public void setParticipantsNum(Integer participantsNum) {
		this.participantsNum = participantsNum;
	}
	public Double getParticipantsAmount() {
		return participantsAmount;
	}
	public void setParticipantsAmount(Double participantsAmount) {
		this.participantsAmount = participantsAmount;
	}
	public Double getAllocationAmount() {
		return allocationAmount;
	}
	public void setAllocationAmount(Double allocationAmount) {
		this.allocationAmount = allocationAmount;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getFailureNum() {
		return failureNum;
	}
	public void setFailureNum(Integer failureNum) {
		this.failureNum = failureNum;
	}
	
}
