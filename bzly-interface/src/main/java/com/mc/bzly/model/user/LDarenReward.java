package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LDarenReward")
public class LDarenReward extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;
	private String rewardDate;
	private Integer apprenticeCount;
	private Long firstReward;
	private Long secondReward;
	private int taskCount;
	private Long updateTime;
	
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
	public Integer getApprenticeCount() {
		return apprenticeCount;
	}
	public void setApprenticeCount(Integer apprenticeCount) {
		this.apprenticeCount = apprenticeCount;
	}
	public Long getFirstReward() {
		return firstReward;
	}
	public void setFirstReward(Long firstReward) {
		this.firstReward = firstReward;
	}
	public Long getSecondReward() {
		return secondReward;
	}
	public void setSecondReward(Long secondReward) {
		this.secondReward = secondReward;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public LDarenReward() {
		// TODO Auto-generated constructor stub
	}
	public LDarenReward(String userId, String rewardDate, Integer apprenticeCount, Long firstReward, Long secondReward,
			int taskCount,Long updateTime) {
		super();
		this.userId = userId;
		this.rewardDate = rewardDate;
		this.apprenticeCount = apprenticeCount;
		this.firstReward = firstReward;
		this.secondReward = secondReward;
		this.taskCount = taskCount;
		this.updateTime = updateTime;
	}
}
