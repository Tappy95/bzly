package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("AnswerResult")
public class AnswerResult extends BaseModel implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	private String userId;
	private Integer score; 
	private Integer answerNum; 
	private int isEnd; 
	private int isSee;
	private Long time;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getAnswerNum() {
		return answerNum;
	}
	public void setAnswerNum(Integer answerNum) {
		this.answerNum = answerNum;
	}
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public int getIsSee() {
		return isSee;
	}
	public void setIsSee(int isSee) {
		this.isSee = isSee;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	

}
