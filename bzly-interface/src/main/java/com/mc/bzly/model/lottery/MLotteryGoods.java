package com.mc.bzly.model.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MLotteryGoods")
public class MLotteryGoods extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private Integer typeId; // 抽奖类型id
	
	private String abbreviation;//商品简称
	
	private String goodsName; // 奖品名称
	
	private Integer rate; // 中奖概率
	
	private String imageUrl; // 奖品图片地址
	
	private String remark; // 奖品描述
	
	private Long createTime; // 创建时间
	
	private Integer status; // 状态（1-启用 2-停用）
	
	private BigDecimal price;//奖品真实价格
	
	private Long pigCoin;//兑换所需金猪
	
	private int goodsNumber;//奖品数量
	
	private int goodsConsumeNumber;//奖品消耗数量
	
	private Integer orders;//排序
	
	private Integer goodsType;//商品类型
	
	private String carouselImg;//奖品轮播图
	
	private String infoImg;//奖品详情图
	
	private Integer lotterySort;//抽奖分类 1兑换2抽奖
	
	private String typeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getPigCoin() {
		return pigCoin;
	}

	public void setPigCoin(Long pigCoin) {
		this.pigCoin = pigCoin;
	}

	public int getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(int goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public int getGoodsConsumeNumber() {
		return goodsConsumeNumber;
	}

	public void setGoodsConsumeNumber(int goodsConsumeNumber) {
		this.goodsConsumeNumber = goodsConsumeNumber;
	}
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getCarouselImg() {
		return carouselImg;
	}

	public void setCarouselImg(String carouselImg) {
		this.carouselImg = carouselImg;
	}

	public String getInfoImg() {
		return infoImg;
	}

	public void setInfoImg(String infoImg) {
		this.infoImg = infoImg;
	}

	public Integer getLotterySort() {
		return lotterySort;
	}

	public void setLotterySort(Integer lotterySort) {
		this.lotterySort = lotterySort;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
