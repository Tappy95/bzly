package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LDarenRewardDetail")
public class LDarenRewardDetail extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 达人id
	private Long reward; // 贡献奖励
	private String apprenticeId; // 徒弟id
	private Long apprenticeReward; // 徒弟奖励
	private Integer taskType; // 任务类型（1-首个任务 2-后续任务）
	private String taskName; // 任务名称
	private Long createTime; // 创建时间
	private Integer taccountId; // 用户id
	private Integer daccountId; // 所属达人id
	private String qrCode; // 达人邀请码
	private String userName; // 姓名
	
	private Long startTime;//起始时间
	private Long endTime;//结束时间
	
	private Integer pageIndex;
	
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
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public String getApprenticeId() {
		return apprenticeId;
	}
	public void setApprenticeId(String apprenticeId) {
		this.apprenticeId = apprenticeId;
	}
	public Long getApprenticeReward() {
		return apprenticeReward;
	}
	public void setApprenticeReward(Long apprenticeReward) {
		this.apprenticeReward = apprenticeReward;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getTaccountId() {
		return taccountId;
	}
	public void setTaccountId(Integer taccountId) {
		this.taccountId = taccountId;
	}
	public Integer getDaccountId() {
		return daccountId;
	}
	public void setDaccountId(Integer daccountId) {
		this.daccountId = daccountId;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}