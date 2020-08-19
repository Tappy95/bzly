package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("DarenUserVo")
public class DarenUserVo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userId; // 用户id        
	private Integer accountId; // 账户id   
	private String userName; // 用户名
	private String mobile; // 电话号码            
	private String aliasName; // 达人姓名       
	private String channelCode; // 渠道
	private Integer roleType; // 角色类型（1-小猪猪 2-团队长 3-超级合伙人 4-邀请达人）
	private Integer apprenticeCount; // 下级人数
	private Long reward; // 总收益
	private Long firstReward; // 首个任务奖励
	private Long secondReward; // 后续任务奖励
	private Integer taskCount; // 徒弟完成任务数
	private Long apprenticeReward; // 徒弟任务奖励
	private Long coin; // 余额
	private Long darenTime; // 达人设置时间
	private Integer pageIndex;
	private Double activityScore; // 活跃度
	private Integer qualityScore; // 质量分
	
	private String darenDate;
	
	private Long darenTime_start;
	private Long darenTime_end;
	private Integer openActivity;// 是否展示活跃度（1-不展示 2-展示）
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public Integer getApprenticeCount() {
		return apprenticeCount;
	}
	public void setApprenticeCount(Integer apprenticeCount) {
		this.apprenticeCount = apprenticeCount;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
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
	public Integer getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}
	public Long getApprenticeReward() {
		return apprenticeReward;
	}
	public void setApprenticeReward(Long apprenticeReward) {
		this.apprenticeReward = apprenticeReward;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	public Long getDarenTime() {
		return darenTime;
	}
	public void setDarenTime(Long darenTime) {
		this.darenTime = darenTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getDarenDate() {
		return darenDate;
	}
	public void setDarenDate(String darenDate) {
		this.darenDate = darenDate;
	}
	public Long getDarenTime_start() {
		return darenTime_start;
	}
	public void setDarenTime_start(Long darenTime_start) {
		this.darenTime_start = darenTime_start;
	}
	public Long getDarenTime_end() {
		return darenTime_end;
	}
	public void setDarenTime_end(Long darenTime_end) {
		this.darenTime_end = darenTime_end;
	}
	public Double getActivityScore() {
		return activityScore;
	}
	public void setActivityScore(Double activityScore) {
		this.activityScore = activityScore;
	}
	public Integer getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
	}
	public Integer getOpenActivity() {
		return openActivity;
	}
	public void setOpenActivity(Integer openActivity) {
		this.openActivity = openActivity;
	}
	
}
