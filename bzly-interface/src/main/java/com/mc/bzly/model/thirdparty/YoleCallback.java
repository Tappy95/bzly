package com.mc.bzly.model.thirdparty; // 

import java.io.Serializable; // 

import org.apache.ibatis.type.Alias; // 

import com.mc.bzly.model.BaseModel; // 

@Alias("YoleCallback")
public class YoleCallback extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String yoleid; 
	private String userid; 
	private String appid; 
	private String imei; 
	private String chid;
	private String gmid; 
	private String gmname; 
	private String rewardid; 
	private String rewarddesc; 
	private String gmgold; 
	private String chgold; 
	private String rewardtype; 
	private String menuorder; 
	private String sign;  
	private Integer status; 
	
    private String accountId;
	
	private Integer pageIndex;
	private String createTime;
	
	private Long startTime;
	private Long endTime;
	
	public String getYoleid() {
		return yoleid;
	}
	public void setYoleid(String yoleid) {
		this.yoleid = yoleid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getChid() {
		return chid;
	}
	public void setChid(String chid) {
		this.chid = chid;
	}
	public String getGmid() {
		return gmid;
	}
	public void setGmid(String gmid) {
		this.gmid = gmid;
	}
	public String getGmname() {
		return gmname;
	}
	public void setGmname(String gmname) {
		this.gmname = gmname;
	}
	public String getRewardid() {
		return rewardid;
	}
	public void setRewardid(String rewardid) {
		this.rewardid = rewardid;
	}
	public String getRewarddesc() {
		return rewarddesc;
	}
	public void setRewarddesc(String rewarddesc) {
		this.rewarddesc = rewarddesc;
	}
	public String getGmgold() {
		return gmgold;
	}
	public void setGmgold(String gmgold) {
		this.gmgold = gmgold;
	}
	public String getChgold() {
		return chgold;
	}
	public void setChgold(String chgold) {
		this.chgold = chgold;
	}
	public String getRewardtype() {
		return rewardtype;
	}
	public void setRewardtype(String rewardtype) {
		this.rewardtype = rewardtype;
	}
	public String getMenuorder() {
		return menuorder;
	}
	public void setMenuorder(String menuorder) {
		this.menuorder = menuorder;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	
	
}
