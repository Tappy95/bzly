package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("PDataStatistics")
public class PDataStatistics extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String channelCode;//渠道标识
	private Integer total;//总用户数  
	private Integer sevenDayAdd;//7天内新增用户
	private Integer sameDayAdd;//当天新增用户数
	private Integer sign;//签到用户数
	private Integer bindAli;//绑定支付宝用户数
	private Integer cash;//提现用户数
	private Integer game28;//参与28游戏用户数
	private String creatorTime;//统计时间
	
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
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getSevenDayAdd() {
		return sevenDayAdd;
	}
	public void setSevenDayAdd(Integer sevenDayAdd) {
		this.sevenDayAdd = sevenDayAdd;
	}
	public Integer getSameDayAdd() {
		return sameDayAdd;
	}
	public void setSameDayAdd(Integer sameDayAdd) {
		this.sameDayAdd = sameDayAdd;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public Integer getBindAli() {
		return bindAli;
	}
	public void setBindAli(Integer bindAli) {
		this.bindAli = bindAli;
	}
	public Integer getCash() {
		return cash;
	}
	public void setCash(Integer cash) {
		this.cash = cash;
	}
	public Integer getGame28() {
		return game28;
	}
	public void setGame28(Integer game28) {
		this.game28 = game28;
	}
	public String getCreatorTime() {
		return creatorTime;
	}
	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

    
	
}
