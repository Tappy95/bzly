package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 排行榜奖励规则配置
 */
@Alias("MRankConfigReward")
public class MRankConfigReward extends BaseModel implements Serializable{
	
 	private static final long serialVersionUID = 1L;
 	
 	private Integer id;
 	private Integer configId; // 排行配置id
	private Integer rewardType; // 奖励类型（1-金币 2-金猪）
	private Long rewardAmount; // 奖励金额
	private Integer rewardOrder; // 奖励排行
	private String rewardRemark; // 奖励排行描述
	private Long createTime; // 创建时间
	private Integer status; // 状态（1-启用 2-停用）
	
	private String rankName; // 所属排行描述
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Long getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(Long rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public Integer getRewardOrder() {
		return rewardOrder;
	}
	public void setRewardOrder(Integer rewardOrder) {
		this.rewardOrder = rewardOrder;
	}
	public String getRewardRemark() {
		return rewardRemark;
	}
	public void setRewardRemark(String rewardRemark) {
		this.rewardRemark = rewardRemark;
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
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
}
