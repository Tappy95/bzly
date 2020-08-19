package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LUserStatistic")
public class LUserStatistic extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;
	private Integer oneGame;//注册当天完成游戏任务次数
	private Integer twoGame;//注册前两天完成游戏任务次数
	private Integer totalGame;//累计完成游戏任务次数
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOneGame() {
		return oneGame;
	}
	public void setOneGame(Integer oneGame) {
		this.oneGame = oneGame;
	}
	public Integer getTwoGame() {
		return twoGame;
	}
	public void setTwoGame(Integer twoGame) {
		this.twoGame = twoGame;
	}
	public Integer getTotalGame() {
		return totalGame;
	}
	public void setTotalGame(Integer totalGame) {
		this.totalGame = totalGame;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
