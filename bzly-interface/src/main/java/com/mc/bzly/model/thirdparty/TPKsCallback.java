package com.mc.bzly.model.thirdparty;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TPKsCallback")
public class TPKsCallback extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer equipmentType; // 设备类型（1-iOS 2-安卓）
	private String imeiMD5; // 设备号MD5加密字段
	private String ip; // IP地址
	private String callback; // 回调地址
	private Long createTime; // 创建时间
	private Integer status; // 状态（1-待回调 2-已回调）
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getImeiMD5() {
		return imeiMD5;
	}
	public void setImeiMD5(String imeiMD5) {
		this.imeiMD5 = imeiMD5;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
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
