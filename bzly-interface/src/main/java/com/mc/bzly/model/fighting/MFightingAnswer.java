package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MFightingAnswer")
public class MFightingAnswer extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer ansId;
	private Integer questionId; 
	private String answer; 
	private Integer isCorrect; 
	public Integer getAnsId() {
		return ansId;
	}
	public void setAnsId(Integer ansId) {
		this.ansId = ansId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(Integer isCorrect) {
		this.isCorrect = isCorrect;
	}
}
