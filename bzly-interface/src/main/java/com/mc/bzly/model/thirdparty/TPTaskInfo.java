package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TPTaskInfo")
public class TPTaskInfo extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;//任务名称
	private String logo;//任务logo
	private Integer typeId;//任务类型
	private String label;//任务标签逗号分隔
	private Double reward;//奖励(元)
	private Integer channelTaskNumber;
	private Integer surplusChannelTaskNumber;
	private Integer isUpper;//是否上架1上架2下架3-待上架
	private Integer isSignin;//是否是签到赚任务1是2否
	private String taskChannel;//任务渠道
	private Long createTime;//创建时间
	private Integer fulfilTime;//完成时间
	private Integer timeUnit;//时间单位1天2小时3分钟
	private Integer isOrder; // 是否可预约1-是 2-否
	private Long orderTime; // 预上架时间
	private Integer orders;//排序
	
	private String userId;
	private Integer status;
	private Double drReward; // 邀请达人奖励
	private String taskInfoUrl; // 详情地址
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getReward() {
		return reward;
	}
	public void setReward(Double reward) {
		this.reward = reward;
	}
	public Integer getIsUpper() {
		return isUpper;
	}
	public void setIsUpper(Integer isUpper) {
		this.isUpper = isUpper;
	}
	public Integer getIsSignin() {
		return isSignin;
	}
	public void setIsSignin(Integer isSignin) {
		this.isSignin = isSignin;
	}
	public String getTaskChannel() {
		return taskChannel;
	}
	public void setTaskChannel(String taskChannel) {
		this.taskChannel = taskChannel;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getSurplusChannelTaskNumber() {
		return surplusChannelTaskNumber;
	}
	public void setSurplusChannelTaskNumber(Integer surplusChannelTaskNumber) {
		this.surplusChannelTaskNumber = surplusChannelTaskNumber;
	}
	public Integer getChannelTaskNumber() {
		return channelTaskNumber;
	}
	public void setChannelTaskNumber(Integer channelTaskNumber) {
		this.channelTaskNumber = channelTaskNumber;
	}
	public Integer getFulfilTime() {
		return fulfilTime;
	}
	public void setFulfilTime(Integer fulfilTime) {
		this.fulfilTime = fulfilTime;
	}
	public Integer getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}
	public Integer getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}
	public Long getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getDrReward() {
		return drReward;
	}
	public void setDrReward(Double drReward) {
		this.drReward = drReward;
	}
	public String getTaskInfoUrl() {
		return taskInfoUrl;
	}
	public void setTaskInfoUrl(String taskInfoUrl) {
		this.taskInfoUrl = taskInfoUrl;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
}
