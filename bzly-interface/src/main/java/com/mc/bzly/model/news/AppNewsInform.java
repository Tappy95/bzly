package com.mc.bzly.model.news;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("AppNewsInform")
public class AppNewsInform extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String userId;
    private String mobile;
    private String informTitle;
    private String informContent; 
    private Integer pushObject; 
    private Long pushTime; 
    private Long createrTime; 
    private Integer informType; 
    private Integer isRelease; 
    private String informUrl; 
    private Integer isPush; 
    private Integer pushMode; 
    private Integer addMode;
    private Integer isRead;
    private Integer appType;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInformTitle() {
		return informTitle;
	}
	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}
	public String getInformContent() {
		return informContent;
	}
	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}
	public Long getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Long createrTime) {
		this.createrTime = createrTime;
	}
	public Integer getInformType() {
		return informType;
	}
	public void setInformType(Integer informType) {
		this.informType = informType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	
	public String getInformUrl() {
		return informUrl;
	}
	public void setInformUrl(String informUrl) {
		this.informUrl = informUrl;
	}
	public Integer getIsPush() {
		return isPush;
	}
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	public Integer getPushMode() {
		return pushMode;
	}
	public void setPushMode(Integer pushMode) {
		this.pushMode = pushMode;
	}
	
	public Integer getPushObject() {
		return pushObject;
	}
	public void setPushObject(Integer pushObject) {
		this.pushObject = pushObject;
	}
	public Long getPushTime() {
		return pushTime;
	}
	public void setPushTime(Long pushTime) {
		this.pushTime = pushTime;
	}
	public Integer getAddMode() {
		return addMode;
	}
	public void setAddMode(Integer addMode) {
		this.addMode = addMode;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	
}
