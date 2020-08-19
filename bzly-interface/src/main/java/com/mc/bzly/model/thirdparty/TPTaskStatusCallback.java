package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("TPTaskStatusCallback")
public class TPTaskStatusCallback implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 用户表的accountId
	private Integer taskId;
	private Integer status;
	private String sign;
	private String flewNum;
	private Integer dealStatus;// 处理结果 1-成功 2-失败
	private Long createTime;// 处理结果 1-成功 2-失败
	private Long expireTime; // 过期时间
	private String remark; // 描述
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
	public String getFlewNum() {
		return flewNum;
	}
	public void setFlewNum(String flewNum) {
		this.flewNum = flewNum;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
