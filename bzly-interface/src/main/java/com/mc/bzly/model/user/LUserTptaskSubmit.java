package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 用户提交材料
 */
@Alias("LUserTptaskSubmit")
public class LUserTptaskSubmit implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 用户id
	private Integer lTpTaskId; // 领取记录id
	private Integer tpTaskId; // 任务id
	private Integer submitId; // 提交材料id
	private String submitName; // 提交材料名称
	private String submitValue; // 提交材料值
	private Long createTime; // 提交时间
	private Integer status; // 状态（1-正常 2-失效）
	
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
	public Integer getlTpTaskId() {
		return lTpTaskId;
	}
	public void setlTpTaskId(Integer lTpTaskId) {
		this.lTpTaskId = lTpTaskId;
	}
	public Integer getTpTaskId() {
		return tpTaskId;
	}
	public void setTpTaskId(Integer tpTaskId) {
		this.tpTaskId = tpTaskId;
	}
	public Integer getSubmitId() {
		return submitId;
	}
	public void setSubmitId(Integer submitId) {
		this.submitId = submitId;
	}
	public String getSubmitName() {
		return submitName;
	}
	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}
	public String getSubmitValue() {
		return submitValue;
	}
	public void setSubmitValue(String submitValue) {
		this.submitValue = submitValue;
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
}
