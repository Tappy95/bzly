package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PVersion")
public class PVersion extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String channelCode; // 渠道标识
	private String versionNo; // 安卓版本号
	private String iosVersionNo;//ios版本号
	private Integer open28; // 是否开启28 1-开启 2-关闭
	private Integer needUpdate; // 是否需要更新（1-需要 2-不需要）
	private String updateUrl; // 安卓更新地址
	private Long createTime; // 创建时间
	private Integer status; // 状态（1-启用 2-停用）
	private Integer openNoviceTask;//是否开启新手引导1开启2不开启
	private String updateRemark;//更新描述
	private String iosUpdateUrl;//ios下载地址
	private Integer channelUpdate;//是否独立更新1是2否
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public Integer getOpen28() {
		return open28;
	}
	public void setOpen28(Integer open28) {
		this.open28 = open28;
	}
	public Integer getNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(Integer needUpdate) {
		this.needUpdate = needUpdate;
	}
	public String getUpdateUrl() {
		return updateUrl;
	}
	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
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
	public Integer getOpenNoviceTask() {
		return openNoviceTask;
	}
	public void setOpenNoviceTask(Integer openNoviceTask) {
		this.openNoviceTask = openNoviceTask;
	}
	public String getUpdateRemark() {
		return updateRemark;
	}
	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}
	public String getIosUpdateUrl() {
		return iosUpdateUrl;
	}
	public void setIosUpdateUrl(String iosUpdateUrl) {
		this.iosUpdateUrl = iosUpdateUrl;
	}
	public Integer getChannelUpdate() {
		return channelUpdate;
	}
	public void setChannelUpdate(Integer channelUpdate) {
		this.channelUpdate = channelUpdate;
	}
	public String getIosVersionNo() {
		return iosVersionNo;
	}
	public void setIosVersionNo(String iosVersionNo) {
		this.iosVersionNo = iosVersionNo;
	}
	
}
