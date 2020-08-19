package com.mc.bzly.model.wish;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("UserFhxXyx")
public class UserFhxXyx implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userId; // 用户id
	
	private Integer fhxTotal; // 分红心总数
	private Integer fhxActive; // 分红心-有效
	private Integer fhxOverdue; // 分红心-过期
	private Integer xyxTotal; // 幸运星总数
	private Long updateTime; // 变更时间
	
	private Integer pageSize;
	private Integer pageNum;
	private Integer pageIndex;
	
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
	public Integer getFhxTotal() {
		return fhxTotal;
	}
	public void setFhxTotal(Integer fhxTotal) {
		this.fhxTotal = fhxTotal;
	}
	public Integer getFhxActive() {
		return fhxActive;
	}
	public void setFhxActive(Integer fhxActive) {
		this.fhxActive = fhxActive;
	}
	public Integer getFhxOverdue() {
		return fhxOverdue;
	}
	public void setFhxOverdue(Integer fhxOverdue) {
		this.fhxOverdue = fhxOverdue;
	}
	public Integer getXyxTotal() {
		return xyxTotal;
	}
	public void setXyxTotal(Integer xyxTotal) {
		this.xyxTotal = xyxTotal;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public UserFhxXyx() {
	}
	public UserFhxXyx(String userId, Integer fhxTotal, Integer fhxActive, Integer fhxOverdue, Integer xyxTotal,
			Long updateTime) {
		super();
		this.userId = userId;
		this.fhxTotal = fhxTotal;
		this.fhxActive = fhxActive;
		this.fhxOverdue = fhxOverdue;
		this.xyxTotal = xyxTotal;
		this.updateTime = updateTime;
	}
	
}
