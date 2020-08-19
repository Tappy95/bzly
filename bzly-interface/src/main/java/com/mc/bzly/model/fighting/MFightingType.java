package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
/**
 * 
 * @author admin
 *
 */
@Alias("MFightingType")
public class MFightingType extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer typeId;
	private String typeName;
	private Long createTime; 
	private Long updateTime; 
	private Integer isDisable; 
	private Integer rewardType; 
	private Integer rewardFrom; 
	private Integer rewardTo; 
	private String fightingRule; 
	private Integer questionNum; 
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Integer getRewardFrom() {
		return rewardFrom;
	}
	public void setRewardFrom(Integer rewardFrom) {
		this.rewardFrom = rewardFrom;
	}
	public Integer getRewardTo() {
		return rewardTo;
	}
	public void setRewardTo(Integer rewardTo) {
		this.rewardTo = rewardTo;
	}
	public String getFightingRule() {
		return fightingRule;
	}
	public void setFightingRule(String fightingRule) {
		this.fightingRule = fightingRule;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	
	
	
	

}
