package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("BZCallback")
public class BZCallback extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer adid; // 广告ID
	private String adname; // 广告名称
	private String appid; // 开发者ID
	private String ordernum; // 订单编号
	private Integer dlevel; // 奖励级别
	private String pagename; // 用户体验游戏的包名
	private String deviceid; // 手机设备号 imei 或 idfa
	private String simid; // 手机sim卡id
	private String appsign; // 开发者用户编号（用户id）
	private String merid; // 用户体验游戏注册的账号id
	private String event; // 奖励说明—在开发者自己的APP中需显示给用户看，以便用户了解自己获得的奖励
	private String price; // 于开发者结算单价、保留2位小数 【人民币单位】
	private String money; // 开发者需奖励给用户金额 【人民币单位】
	private String itime; // 用户获得奖励时间 时间字符串 如：2018/01/24 12:13:24
	private String keycode; // 订单校验参数 加密规则MD5(adid+appid+ordernum+dlevel+deviceid+appsign+price+money+key) MD5加密结果需转大写
	private Integer status; // 处理结果 1-成功 2-失败
	
    private String accountId;
	
	private Integer pageIndex;
	private String createTime;
	
	private Long startTime;
	private Long endTime;
	
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
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
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
	public String getAppsign() {
		return appsign;
	}
	public void setAppsign(String appsign) {
		this.appsign = appsign;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getItime() {
		return itime;
	}
	public void setItime(String itime) {
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
