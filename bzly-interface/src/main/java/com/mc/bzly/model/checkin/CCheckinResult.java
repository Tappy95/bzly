package com.mc.bzly.model.checkin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("CCheckinResult")
public class CCheckinResult extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Long bonusPool;//瓜分金额
	private Integer successNumber;//成功人数
	private Integer failNumber;//失败人数
	private int successRealNumber;//真实成功人数
	private int failRealNumber;//真实失败人数
	private Long actualBonus;//实际奖金
	private Long createTime;//创建时间
	
	private String createDate;//创建时间
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getBonusPool() {
		return bonusPool;
	}
	public void setBonusPool(Long bonusPool) {
		this.bonusPool = bonusPool;
	}
	public Integer getSuccessNumber() {
		return successNumber;
	}
	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}
	public Integer getFailNumber() {
		return failNumber;
	}
	public void setFailNumber(Integer failNumber) {
		this.failNumber = failNumber;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getActualBonus() {
		return actualBonus;
	}
	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}
	public int getSuccessRealNumber() {
		return successRealNumber;
	}
	public void setSuccessRealNumber(int successRealNumber) {
		this.successRealNumber = successRealNumber;
	}
	public int getFailRealNumber() {
		return failRealNumber;
	}
	public void setFailRealNumber(int failRealNumber) {
		this.failRealNumber = failRealNumber;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
