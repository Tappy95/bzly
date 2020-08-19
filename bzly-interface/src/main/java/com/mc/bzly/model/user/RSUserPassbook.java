package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("RSUserPassbook")
public class RSUserPassbook extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userId;        
	private Integer passbookId;   
	private String expirationTime;
	private Integer expirationDay;
	private Integer status;       

	private String passbookName;  
	private Integer passbookType; 
	private Integer passbookValue;
	private String typeName;      

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
	public Integer getPassbookId() {
		return passbookId;
	}
	public void setPassbookId(Integer passbookId) {
		this.passbookId = passbookId;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getExpirationDay() {
		return expirationDay;
	}
	public void setExpirationDay(Integer expirationDay) {
		this.expirationDay = expirationDay;
	}
	public String getPassbookName() {
		return passbookName;
	}
	public void setPassbookName(String passbookName) {
		this.passbookName = passbookName;
	}
	public Integer getPassbookType() {
		return passbookType;
	}
	public void setPassbookType(Integer passbookType) {
		this.passbookType = passbookType;
	}
	public Integer getPassbookValue() {
		return passbookValue;
	}
	public void setPassbookValue(Integer passbookValue) {
		this.passbookValue = passbookValue;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
