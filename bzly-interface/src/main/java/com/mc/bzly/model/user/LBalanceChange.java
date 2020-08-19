package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LBalanceChange")
public class LBalanceChange extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer logId;
	private String userId; 
	private String mobile; 
	private String userName; 
	private Double amount; 
	private String account; 
	private Integer accountType; 
	private Integer flowType; 
	private Integer changedType; 
	private Long changedTime; 
	private Integer isAuditing;  
	private Long reviewTime;
	private String reason;
	
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getChangedType() {
		return changedType;
	}
	/**
	 *  变更类型：（1-充值 2-提现 3-推荐好友,4-参与打卡,5-打卡成功）
	 */
	public void setChangedType(Integer changedType) {
		this.changedType = changedType;
	}
	public Long getChangedTime() {
		return changedTime;
	}
	public void setChangedTime(Long changedTime) {
		this.changedTime = changedTime;
	}
	public Integer getIsAuditing() {
		return isAuditing;
	}
	/**
	 *  是需要审核的吗？如果不，则设置为0，如果设置大于零，则表示审查的进度。1:待审核 2审核中 3拒绝 4成功
	 */
	public void setIsAuditing(Integer isAuditing) {
		this.isAuditing = isAuditing;
	}
	public Long getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Long reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Integer getFlowType() {
		return flowType;
	}
	public void setFlowType(Integer flowType) {
		this.flowType = flowType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
