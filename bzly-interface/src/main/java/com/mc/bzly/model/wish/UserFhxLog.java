package com.mc.bzly.model.wish;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("UserFhxLog")
public class UserFhxLog implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userId; // 用户id
	private Integer fhxNum; // 分红型数量
	private Integer changedType; // 变更类型（1-抽奖 2-邀请好友 3-试玩任务 4-系统任务）
	private Integer status; // 状态（1-有效 2-失效）
	private Long changedTime; // 变更时间
	
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
	public Integer getFhxNum() {
		return fhxNum;
	}
	public void setFhxNum(Integer fhxNum) {
		this.fhxNum = fhxNum;
	}
	public Integer getChangedType() {
		return changedType;
	}
	public void setChangedType(Integer changedType) {
		this.changedType = changedType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getChangedTime() {
		return changedTime;
	}
	public void setChangedTime(Long changedTime) {
		this.changedTime = changedTime;
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
	public UserFhxLog() {
		// TODO Auto-generated constructor stub
	}
	public UserFhxLog(String userId, Integer fhxNum, Integer changedType, Integer status, Long changedTime) {
		super();
		this.userId = userId;
		this.fhxNum = fhxNum;
		this.changedType = changedType;
		this.status = status;
		this.changedTime = changedTime;
	}
	
	
}
