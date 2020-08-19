package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserSign")
public class LUserSign extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer signId;//记录id 
	
	private String userId;//签到用户

	private Long signTime;//签到时间

	private Long score;//积分数  

	private Integer stickTimes;//连续签到次数

	private Long lastDay;//上次签到日期 

	private String signIp;//签到IP   

	private Integer ruleId;//签到规则 

	private String userName;//用户姓名
	
	private Integer isTask;//是否领取任务奖励1未领取2领取
	
	private Long taskCoin;//任务奖励金币数

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSignTime() {
		return signTime;
	}

	public void setSignTime(Long signTime) {
		this.signTime = signTime;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Integer getStickTimes() {
		return stickTimes;
	}

	public void setStickTimes(Integer stickTimes) {
		this.stickTimes = stickTimes;
	}

	public Long getLastDay() {
		return lastDay;
	}

	public void setLastDay(Long lastDay) {
		this.lastDay = lastDay;
	}

	public String getSignIp() {
		return signIp;
	}

	public void setSignIp(String signIp) {
		this.signIp = signIp;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsTask() {
		return isTask;
	}

	public void setIsTask(Integer isTask) {
		this.isTask = isTask;
	}

	public Long getTaskCoin() {
		return taskCoin;
	}

	public void setTaskCoin(Long taskCoin) {
		this.taskCoin = taskCoin;
	}
	
}
