package com.mc.bzly.model.activity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MActivityInfo")
public class MActivityInfo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer actId;
	private String activityName; 
	private Integer activityType; 
	private String startTime; 
	private String stopTime; 
	private Integer isCycle; 
	private Long periodic; 
	private Integer periodicUnit; 
	private Integer isDisable; 
	private String memo; 
	private Long begainTime; 
	private Long endTime; 
	private String settlementTime; 
	private Double baseAllocationTmount; 
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStopTime() {
		return stopTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public Integer getIsCycle() {
		return isCycle;
	}
	public void setIsCycle(Integer isCycle) {
		this.isCycle = isCycle;
	}
	public Long getPeriodic() {
		return periodic;
	}
	public void setPeriodic(Long periodic) {
		this.periodic = periodic;
	}
	public Integer getPeriodicUnit() {
		return periodicUnit;
	}
	public void setPeriodicUnit(Integer periodicUnit) {
		this.periodicUnit = periodicUnit;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getBegainTime() {
		return begainTime;
	}
	public void setBegainTime(Long begainTime) {
		this.begainTime = begainTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}
	public Double getBaseAllocationTmount() {
		return baseAllocationTmount;
	}
	public void setBaseAllocationTmount(Double baseAllocationTmount) {
		this.baseAllocationTmount = baseAllocationTmount;
	}
	
}