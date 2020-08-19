package com.mc.bzly.model.thirdparty;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PCDDCallback")
public class PCDDCallback extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer adid; 
	private String adname; 
	private String pid;  
	private String ordernum; 
	private Integer dlevel; 
	private String pagename; 
	private String deviceid; 
	private String simid; 
	private String userid; 
	private String merid;
	private String event; 
	private Double price; 
	private Double money; 
	private Date itime; 
	private String keycode; 
	private Integer status; // 1-失败 2-成功
	private String createTime; 
	
	private String accountId;
	private String itimeTime;
	
	private Long startTime;
	private Long endTime;
	
	private Integer pageIndex;
	private Integer ptype;// 设备类型（1-iOS 2-安卓）
	private String channelCode;
	public Integer getAdid() {
		return adid;
	}
	public void setAdid(Integer adid) {
		this.adid = adid;
	}
	public String getAdname() {
		return adname;
	}
	public void setAdname(String adname) {
		this.adname = adname;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public Integer getDlevel() {
		return dlevel;
	}
	public void setDlevel(Integer dlevel) {
		this.dlevel = dlevel;
	}
	public String getPagename() {
		return pagename;
	}
	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getSimid() {
		return simid;
	}
	public void setSimid(String simid) {
		this.simid = simid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMerid() {
		return merid;
	}
	public void setMerid(String merid) {
		this.merid = merid;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getItime() {
		return itime;
	}
	public void setItime(Date itime) {
		this.itime = itime;
	}
	public String getKeycode() {
		return keycode;
	}
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getItimeTime() {
		return itimeTime;
	}
	public void setItimeTime(String itimeTime) {
		this.itimeTime = itimeTime;
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
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
}
