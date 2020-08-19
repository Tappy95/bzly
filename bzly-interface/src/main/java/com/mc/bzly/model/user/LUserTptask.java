package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 用户任务记录
 */
@Alias("LUserTptask")
public class LUserTptask extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 用户id
	private Integer accountId; // 用户账户id
	private Integer tpTaskId; // 任务id
	private Long updateTime; // 修改时间
	private Long createTime; // 领取时间
	private Long expireTime; // 过期时间
	private String remark; // 描述
	private Integer status; // 状态（-1-已过期 1-待提交 2-已提交，待审核 3-审核通过 4-审核失败 5-预约）
	private String flewNum; // 任务订单号
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
	public Integer getTpTaskId() {
		return tpTaskId;
	}
	public void setTpTaskId(Integer tpTaskId) {
		this.tpTaskId = tpTaskId;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getFlewNum() {
		return flewNum;
	}
	public void setFlewNum(String flewNum) {
		this.flewNum = flewNum;
	}
	
}
