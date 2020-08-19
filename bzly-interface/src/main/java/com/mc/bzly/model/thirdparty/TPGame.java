package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("TPGame")
public class TPGame extends BaseModel implements Serializable {

	public static final long serialVersionUID = 1L;
	
	public Integer id;
	public Integer interfaceId; 
	public Integer gameId; 
	public String gameTitle; 
	public String icon; 
	public String url; 
	public String enddate; 
	public BigDecimal gameGold; 
	public String introduce; 
	public String typeName; 
	public Integer gameType; 
	public String packageName; 
	public Long coins; 
	public Integer status; 
	private Integer gameTag; // 游戏标签 （1-正常有游戏 2-快速任务）
	private Integer lid;
	private String interfaceName;
	private Integer taskStatus; // 签到赚任务状态（1-未完成 2-已完成）
	private Integer tryTag;					
	private Integer signinId;
	private Integer orderId;
	private Integer ptype; // 适用设备类型（1-iOS 2-安卓）
	
	private Integer typeId;
	
	private Integer cashStatus;//提现任务状态（1-未完成 2-已完成）
	private String labelStr; // 小标签，多个用逗号分隔
	private String shortIntro; // 充值返利
	
	private Integer fulfilTime;
	private Integer timeUnit;
	private String gameLabelName;
	private Integer isSetType;//是否设置类型1是2否
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public String getGameTitle() {
		return gameTitle;
	}
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public BigDecimal getGameGold() {
		return gameGold;
	}
	public void setGameGold(BigDecimal gameGold) {
		this.gameGold = gameGold;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getCoins() {
		return coins;
	}
	public void setCoins(Long coins) {
		this.coins = coins;
	}
	public Integer getGameType() {
		return gameType;
	}
	public void setGameType(Integer gameType) {
		this.gameType = gameType;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGameTag() {
		return gameTag;
	}
	public void setGameTag(Integer gameTag) {
		this.gameTag = gameTag;
	}
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Integer getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getTryTag() {
		return tryTag;
	}
	public void setTryTag(Integer tryTag) {
		this.tryTag = tryTag;
	}
	public Integer getSigninId() {
		return signinId;
	}
	public void setSigninId(Integer signinId) {
		this.signinId = signinId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}
	public String getLabelStr() {
		return labelStr;
	}
	public void setLabelStr(String labelStr) {
		this.labelStr = labelStr;
	}
	public String getShortIntro() {
		return shortIntro;
	}
	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}
	public String getGameLabelName() {
		return gameLabelName;
	}
	public void setGameLabelName(String gameLabelName) {
		this.gameLabelName = gameLabelName;
	}
	public Integer getIsSetType() {
		return isSetType;
	}
	public void setIsSetType(Integer isSetType) {
		this.isSetType = isSetType;
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
	
	
	
}
