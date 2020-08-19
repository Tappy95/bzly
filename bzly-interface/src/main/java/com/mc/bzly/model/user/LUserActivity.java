package com.mc.bzly.model.user;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 达人活跃度
 */
@Alias("LUserActivity")
public class LUserActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 达人id
	private Double activityScore; // 活跃度
	private Integer qualityScore; // 质量分
	private String createDate; // 创建日期（格式：yyyy-MM-dd）
	private Long createTime; // 创建时间（格式：时间戳）
	
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
	public Double getActivityScore() {
		return activityScore;
	}
	public void setActivityScore(Double activityScore) {
		this.activityScore = activityScore;
	}
	public Integer getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public LUserActivity(Integer id, String userId, Double activityScore, Integer qualityScore, String createDate,
			Long createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.activityScore = activityScore;
		this.qualityScore = qualityScore;
		this.createDate = createDate;
		this.createTime = createTime;
	}
	public LUserActivity() {
	}
	
}
