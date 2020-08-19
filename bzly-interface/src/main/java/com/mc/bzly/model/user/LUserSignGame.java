package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 用户签到游戏记录
 */
@Alias("LUserSignGame")
public class LUserSignGame extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userId; // userId
	private Integer gameId; // 游戏id tp_game
	private Integer signinId; // 所属签到天数id
	private Long createTime; // 领取任务时间
	private Long finishTime; // 完成时间
	private Long reward; // 游戏中获得的金币数
	private Integer status; // 状态（1-未完成 2-已完成）
	private Integer isHide; // 是否隐藏（1-不隐藏 2-隐藏）
	
	private String gameName;
	
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
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getSigninId() {
		return signinId;
	}
	public void setSigninId(Integer signinId) {
		this.signinId = signinId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public Integer getIsHide() {
		return isHide;
	}
	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
}
