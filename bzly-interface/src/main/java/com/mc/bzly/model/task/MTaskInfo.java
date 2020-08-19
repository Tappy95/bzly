package com.mc.bzly.model.task;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MTaskInfo")
public class MTaskInfo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String taskName; 
	private Integer taskType; 
	private String typeName; 
	private Long reward; 
	private Integer rewardUnit; 
	private String remark; 
	private Long createTime; 
	private Integer status;
	private String taskImg;//任务图片
	private Integer iconType;//小图标类型1无2金猪3金币
	private String remarks;//备注
	private Integer sort;//排序
	private String fulfilTaskImg;//任务完成图标
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public Integer getRewardUnit() {
		return rewardUnit;
	}
	public void setRewardUnit(Integer rewardUnit) {
		this.rewardUnit = rewardUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTaskImg() {
		return taskImg;
	}
	public void setTaskImg(String taskImg) {
		this.taskImg = taskImg;
	}
	public Integer getIconType() {
		return iconType;
	}
	public void setIconType(Integer iconType) {
		this.iconType = iconType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getFulfilTaskImg() {
		return fulfilTaskImg;
	}
	public void setFulfilTaskImg(String fulfilTaskImg) {
		this.fulfilTaskImg = fulfilTaskImg;
	}
	
}
