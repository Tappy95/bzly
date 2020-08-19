package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
/**
 * 问题表
 * @author admin
 *
 */
@Alias("MFightingQuestion")
public class MFightingQuestion extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer qId;
	private Integer questionType; 
	private String question; 
	private String creator; 
	private Long createTime; 
	private Integer score; 
	private Integer questionState; 
	private String rejectReason; 
	private Integer countTime; 
	private Integer coin; 
	private String answers;
	
	private String questionTypeName; 
	private String creatorName; 
	
	public Integer getqId() {
		return qId;
	}
	public void setqId(Integer qId) {
		this.qId = qId;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getQuestionState() {
		return questionState;
	}
	public void setQuestionState(Integer questionState) {
		this.questionState = questionState;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public Integer getCountTime() {
		return countTime;
	}
	public void setCountTime(Integer countTime) {
		this.countTime = countTime;
	}
	public Integer getCoin() {
		return coin;
	}
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getQuestionTypeName() {
		return questionTypeName;
	}
	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}
