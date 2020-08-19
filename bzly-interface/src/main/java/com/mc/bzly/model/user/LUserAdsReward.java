package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("LUserAdsReward")
public class LUserAdsReward implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;
	private String content;
	private String rewardDate;
	private Integer status;
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
	public String getRewardDate() {
		return rewardDate;
	}
	public void setRewardDate(String rewardDate) {
		this.rewardDate = rewardDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LUserAdsReward() {
		// TODO Auto-generated constructor stub
	}
	public LUserAdsReward(String userId, String content, String rewardDate,Integer status) {
		super();
		this.userId = userId;
		this.content = content;
		this.rewardDate = rewardDate;
		this.status = status;
	}
	
}
