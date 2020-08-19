package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;
 
@Alias("MSignRule")
public class MSignRule extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer ruleId;        
	private String ruleName;       
	private Integer stickTime;     
	private Long score;            
	private Integer hasOtherReward;
	private Integer rewardType;    
	private String otherReward;    
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Integer getStickTime() {
		return stickTime;
	}
	public void setStickTime(Integer stickTime) {
		this.stickTime = stickTime;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Integer getHasOtherReward() {
		return hasOtherReward;
	}
	public void setHasOtherReward(Integer hasOtherReward) {
		this.hasOtherReward = hasOtherReward;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public String getOtherReward() {
		return otherReward;
	}
	public void setOtherReward(String otherReward) {
		this.otherReward = otherReward;
	}
}
