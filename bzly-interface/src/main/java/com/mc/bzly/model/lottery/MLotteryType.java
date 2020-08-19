package com.mc.bzly.model.lottery;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 抽奖类型
 * @author admin
 *
 */
@Alias("MLotteryType")
public class MLotteryType extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String typeName;//类型名称
	private Integer dayNum;//每天发放数量
	private Integer timesOneday;//用户每天次数限制
	private Long expendPigCoin;//每次所需金猪
	private String remark;//类型描述
	private Integer applyCrowd;//适用人群 1全部
	private Integer lotterySort;//抽奖分类 1抽奖2兑奖
	private Long createTime;//创建时间
	private Integer status;//类型状态（1-启用 2-停用)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getTimesOneday() {
		return timesOneday;
	}
	public void setTimesOneday(Integer timesOneday) {
		this.timesOneday = timesOneday;
	}
	public Long getExpendPigCoin() {
		return expendPigCoin;
	}
	public void setExpendPigCoin(Long expendPigCoin) {
		this.expendPigCoin = expendPigCoin;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Integer getApplyCrowd() {
		return applyCrowd;
	}
	public void setApplyCrowd(Integer applyCrowd) {
		this.applyCrowd = applyCrowd;
	}
	public Integer getLotterySort() {
		return lotterySort;
	}
	public void setLotterySort(Integer lotterySort) {
		this.lotterySort = lotterySort;
	}
	public Integer getDayNum() {
		return dayNum;
	}
	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	
	
}
