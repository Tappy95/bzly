package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MDarenReward")
public class MDarenReward extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer rewardType;//奖励类型1首个任务2后续任务 3-高额赚任务提成
	private String rewardName;//奖励名称
	private Long coin;//奖励金币数
	private Integer orders;//排序
	private Integer dayLimit;//天数限制
	private Integer peopleLimit;//人数限制
	private Integer state;//状态1启用2禁用3已删除
	private Long createTime;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public String getRewardName() {
		return rewardName;
	}
	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}
	public Integer getPeopleLimit() {
		return peopleLimit;
	}
	public void setPeopleLimit(Integer peopleLimit) {
		this.peopleLimit = peopleLimit;
	}
	
}
