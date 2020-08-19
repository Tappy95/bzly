package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("LFightingInfo")
public class LFightingInfo extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer logId;
	private Integer fightId; 
	private String userId; 
	private Integer questionId; 
	private Integer answerId; 
	private Integer isCorrect; 
	private Integer score; 
	private Long answerTime; 
	
	private Integer num; 
	private String entryCode; 
	private Integer enemyScore; 
	private Integer role; 
	private Integer quesScore; 
	private Integer setTime; 
	private Integer getScoreTime;
	
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getFightId() {
		return fightId;
	}
	public void setFightId(Integer fightId) {
		this.fightId = fightId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public Integer getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(Integer isCorrect) {
		this.isCorrect = isCorrect;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Long answerTime) {
		this.answerTime = answerTime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public Integer getEnemyScore() {
		return enemyScore;
	}
	public void setEnemyScore(Integer enemyScore) {
		this.enemyScore = enemyScore;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(Integer quesScore) {
		this.quesScore = quesScore;
	}
	public Integer getSetTime() {
		return setTime;
	}
	public void setSetTime(Integer setTime) {
		this.setTime = setTime;
	}
	public Integer getGetScoreTime() {
		return getScoreTime;
	}
	public void setGetScoreTime(Integer getScoreTime) {
		this.getScoreTime = getScoreTime;
	}
	
}
