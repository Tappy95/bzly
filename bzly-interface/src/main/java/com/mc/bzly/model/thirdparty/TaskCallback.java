package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 任务平台回调数据
 */
@Alias("TaskCallback")
public class TaskCallback extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String orderNum;
	private Integer taskId;
	private String name;
	private String userId;
	private Double chReward;
	private Double userReward;
	private long totalCoin;
	private Integer resultCode; // 审核结果(1-通过 2-拒绝)
	private String remark;
	private Long date;
	private String sign;
	private Long createTime;
	private Integer status; // 处理结果（1-成功 2-失败）
	
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getChReward() {
		return chReward;
	}
	public void setChReward(Double chReward) {
		this.chReward = chReward;
	}
	public Double getUserReward() {
		return userReward;
	}
	public void setUserReward(Double userReward) {
		this.userReward = userReward;
	}
	public Integer getResultCode() {
		return resultCode;
	}
	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public long getTotalCoin() {
		return totalCoin;
	}
	public void setTotalCoin(long totalCoin) {
		this.totalCoin = totalCoin;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
