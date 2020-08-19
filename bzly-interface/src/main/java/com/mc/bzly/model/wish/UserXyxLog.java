package com.mc.bzly.model.wish;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("UserXyxLog")
public class UserXyxLog implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String userId; // 用户id
	private Integer xyxNum; // 幸运星数量
	private Integer changedType; // 变更类型（1-获得10000分红心 2-系统任务）
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
	public Integer getXyxNum() {
		return xyxNum;
	}
	public void setXyxNum(Integer xyxNum) {
		this.xyxNum = xyxNum;
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
	public UserXyxLog() {
	}
	public UserXyxLog(String userId, Integer xyxNum, Integer changedType, Integer status,
			Long changedTime) {
		super();
		this.userId = userId;
		this.xyxNum = xyxNum;
		this.changedType = changedType;
		this.status = status;
		this.changedTime = changedTime;
	}
}
