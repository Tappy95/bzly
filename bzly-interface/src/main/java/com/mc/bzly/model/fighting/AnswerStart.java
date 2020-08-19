package com.mc.bzly.model.fighting;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("AnswerStart")
public class AnswerStart  extends BaseModel implements Serializable{
	
	 private static final long serialVersionUID = 1L;
     private Integer fightId; 
     private Integer num; 
     private Integer role; 
     private String entryCode; 
	public Integer getFightId() {
		return fightId;
	}
	public void setFightId(Integer fightId) {
		this.fightId = fightId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getEntryCode() {
		return entryCode;
	}
	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}
}
