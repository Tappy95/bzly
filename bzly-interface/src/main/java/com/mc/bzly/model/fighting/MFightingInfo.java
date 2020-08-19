package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MFightingInfo")
public class MFightingInfo extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer fightId;
	private Integer fightingType; 
	private String initiator; 
	private String defense; 
	private Integer initiatorCoin;
	private Integer defenseCoin; 
	private Long fightingTime; 
	private String winner; 
	private Integer victoryScore; 
	private Integer failureScore; 
	private Integer useTime; 
	private String entryCode; 
	private Integer state; 
	
	private Integer isReceive;

	private String initiatorName; 
	private String defenseName; 
	private String winnerName; 
	private Integer quesNum; 
	public Integer getFightId() {
		return fightId;
	}
	public void setFightId(Integer fightId) {
		this.fightId = fightId;
	}
	public Integer getFightingType() {
		return fightingType;
	}
	public void setFightingType(Integer fightingType) {
		this.fightingType = fightingType;
	}
	
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getDefense() {
		return defense;
	}
	public void setDefense(String defense) {
		this.defense = defense;
	}
	public Long getFightingTime() {
		return fightingTime;
	}
	public void setFightingTime(Long fightingTime) {
		this.fightingTime = fightingTime;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public Integer getVictoryScore() {
		return victoryScore;
	}
	public void setVictoryScore(Integer victoryScore) {
		this.victoryScore = victoryScore;
	}
	public Integer getFailureScore() {
		return failureScore;
	}
	public void setFailureScore(Integer failureScore) {
		this.failureScore = failureScore;
	}
	public Integer getUseTime() {
		return useTime;
	}
	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}
	
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public String getDefenseName() {
		return defenseName;
	}
	public void setDefenseName(String defenseName) {
		this.defenseName = defenseName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getInitiatorCoin() {
		return initiatorCoin;
	}
	public void setInitiatorCoin(Integer initiatorCoin) {
		this.initiatorCoin = initiatorCoin;
	}
	public Integer getDefenseCoin() {
		return defenseCoin;
	}
	public void setDefenseCoin(Integer defenseCoin) {
		this.defenseCoin = defenseCoin;
	}
	public String getWinnerName() {
		return winnerName;
	}
	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
	public Integer getQuesNum() {
		return quesNum;
	}
	public void setQuesNum(Integer quesNum) {
		this.quesNum = quesNum;
	}
	public Integer getIsReceive() {
		return isReceive;
	}
	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}
	
}
