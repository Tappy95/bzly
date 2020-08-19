package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MUserGyro")
public class MUserGyro extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId;//用户id
	private double gyroX;//陀螺仪x轴
	private double gyroY;//陀螺仪y轴
	private double gyroZ;//陀螺仪z轴
	private Integer pageType;//页面类型：1主页，2游戏列表，3游戏详情
	private Long updateTime;//修改时间
	private Integer number;
	
	private Integer accountId;
	
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
	public double getGyroX() {
		return gyroX;
	}
	public void setGyroX(double gyroX) {
		this.gyroX = gyroX;
	}
	public double getGyroY() {
		return gyroY;
	}
	public void setGyroY(double gyroY) {
		this.gyroY = gyroY;
	}
	public double getGyroZ() {
		return gyroZ;
	}
	public void setGyroZ(double gyroZ) {
		this.gyroZ = gyroZ;
	}
	public Integer getPageType() {
		return pageType;
	}
	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	
}
