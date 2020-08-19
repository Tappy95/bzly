package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MUserVipReferrerLog")
public class MUserVipReferrerLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private String referrerId;//师傅id
	private Integer rank;//累计充值vip奖励等级
	private Long creatorTime;//创建时间
	
	
	
	public MUserVipReferrerLog() {
		super();
	}
	
	public MUserVipReferrerLog(String userId, String referrerId, Integer rank, Long creatorTime) {
		super();
		this.userId = userId;
		this.referrerId = referrerId;
		this.rank = rank;
		this.creatorTime = creatorTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReferrerId() {
		return referrerId;
	}
	public void setReferrerId(String referrerId) {
		this.referrerId = referrerId;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	
	
	
	

}
