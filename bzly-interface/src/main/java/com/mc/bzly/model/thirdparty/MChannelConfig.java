package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 渠道配置表
 * @author admin
 *
 */
@Alias("MChannelConfig")
public class MChannelConfig extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String channelCode;//渠道标识
	private Integer fissionId;//方案id
	private Integer chargeMode;//收费方式1线上2线下
	private Integer effectiveObject;//生效对象1仅对渠道生效2对渠道用户和渠道次级用户生效
	private String openGame;//第三方游戏id,逗号分隔
	private Long createTime;//创建时间
	private Integer applyTask;//是否需要完成高额赚任务1需要2不需要
	private Integer game28;//是否打开游戏28,1关闭2打开
	private Integer pcdd28;//是否打开蛋蛋28,1关闭2打开
	private Integer jnd28;//是否打开加拿大28,1关闭2打开
	
	private String fissionName;
	
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
	public Integer getFissionId() {
		return fissionId;
	}
	public void setFissionId(Integer fissionId) {
		this.fissionId = fissionId;
	}
	public Integer getChargeMode() {
		return chargeMode;
	}
	public void setChargeMode(Integer chargeMode) {
		this.chargeMode = chargeMode;
	}
	public Integer getEffectiveObject() {
		return effectiveObject;
	}
	public void setEffectiveObject(Integer effectiveObject) {
		this.effectiveObject = effectiveObject;
	}
	public String getOpenGame() {
		return openGame;
	}
	public void setOpenGame(String openGame) {
		this.openGame = openGame;
	}
	public Integer getGame28() {
		return game28;
	}
	public void setGame28(Integer game28) {
		this.game28 = game28;
	}
	public Integer getPcdd28() {
		return pcdd28;
	}
	public void setPcdd28(Integer pcdd28) {
		this.pcdd28 = pcdd28;
	}
	public Integer getJnd28() {
		return jnd28;
	}
	public void setJnd28(Integer jnd28) {
		this.jnd28 = jnd28;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getFissionName() {
		return fissionName;
	}
	public void setFissionName(String fissionName) {
		this.fissionName = fissionName;
	}
	public Integer getApplyTask() {
		return applyTask;
	}
	public void setApplyTask(Integer applyTask) {
		this.applyTask = applyTask;
	}
	
}
