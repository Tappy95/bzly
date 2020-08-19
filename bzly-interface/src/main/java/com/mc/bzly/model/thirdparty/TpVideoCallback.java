package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TpVideoCallback")
public class TpVideoCallback extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String user_id;//用户id
	private Integer operateType;//操作类型：1.每日红包 2.首页视频
	private String trans_id;//唯一交易ID
	private Integer reward_amount;//奖励数量
	private String reward_name;//奖励名称
	private Long creatorTime;//创建时间
	private String sign;//加密结果
	private Integer state;//状态：1成功2失败
	private String remarks;//备注
	
	private Integer accountId;
	private Long startCreatorTime;
	private Long endCreatorTime;
	
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public Integer getReward_amount() {
		return reward_amount;
	}
	public void setReward_amount(Integer reward_amount) {
		this.reward_amount = reward_amount;
	}
	public String getReward_name() {
		return reward_name;
	}
	public void setReward_name(String reward_name) {
		this.reward_name = reward_name;
	}
	public Long getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(Long creatorTime) {
		this.creatorTime = creatorTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Long getStartCreatorTime() {
		return startCreatorTime;
	}
	public void setStartCreatorTime(Long startCreatorTime) {
		this.startCreatorTime = startCreatorTime;
	}
	public Long getEndCreatorTime() {
		return endCreatorTime;
	}
	public void setEndCreatorTime(Long endCreatorTime) {
		this.endCreatorTime = endCreatorTime;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
