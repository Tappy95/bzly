package com.mc.bzly.model.platform;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MRankConfig")
public class MRankConfig extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer rankType; // 排行榜类型（1-金猪排行榜 2-金币排行榜 3-好友排行榜）
	private Integer dataLogic; // 数据筛选逻辑（1-月榜 2-总榜）
	private Double rangeMin; // 随机范围-最小值
	private Double rangeMax; // 随机范围-最大值
	private Integer upNum; // 最小值随机值小于
	private Long createTime; // 创建时间
	private Long updateTime; // 修改时间
	private Integer status; // 状态（1-启用 2-停用）
	private String rankName; // 排行名称
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRankType() {
		return rankType;
	}
	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
	
	public Integer getDataLogic() {
		return dataLogic;
	}
	public void setDataLogic(Integer dataLogic) {
		this.dataLogic = dataLogic;
	}
	public Double getRangeMin() {
		return rangeMin;
	}
	public void setRangeMin(Double rangeMin) {
		this.rangeMin = rangeMin;
	}
	public Double getRangeMax() {
		return rangeMax;
	}
	public void setRangeMax(Double rangeMax) {
		this.rangeMax = rangeMax;
	}
	public Integer getUpNum() {
		return upNum;
	}
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	
}
