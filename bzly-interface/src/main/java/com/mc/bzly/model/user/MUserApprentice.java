package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MUserApprentice")
public class MUserApprentice extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int apprenticeId;        
	
	private String referrerId; // 师傅id   

	private String userId; // 徒弟id

	private Long contribution;       

	private Long updateTime;         

	private Long createTime;         

	private String apprenticeMobile; 

	private String referrerName;     
	private String apprenticeName;   

	private Integer rewardStatus;   
	
	private Integer apprenticeType; // 徒弟类型（1-正常徒弟 2-达人徒弟）


	public int getApprenticeId() {
		return apprenticeId;
	}

	public void setApprenticeId(int apprenticeId) {
		this.apprenticeId = apprenticeId;
	}

	public String getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getContribution() {
		return contribution;
	}

	public void setContribution(Long contribution) {
		this.contribution = contribution;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getApprenticeMobile() {
		return apprenticeMobile;
	}

	public void setApprenticeMobile(String apprenticeMobile) {
		this.apprenticeMobile = apprenticeMobile;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getApprenticeName() {
		return apprenticeName;
	}

	public void setApprenticeName(String apprenticeName) {
		this.apprenticeName = apprenticeName;
	}

	public Integer getRewardStatus() {
		return rewardStatus;
	}

	public void setRewardStatus(Integer rewardStatus) {
		this.rewardStatus = rewardStatus;
	}

	public Integer getApprenticeType() {
		return apprenticeType;
	}

	public void setApprenticeType(Integer apprenticeType) {
		this.apprenticeType = apprenticeType;
	}
	
	
}
