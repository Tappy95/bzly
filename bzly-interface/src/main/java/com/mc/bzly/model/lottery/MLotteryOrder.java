package com.mc.bzly.model.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MLotteryOrder")
public class MLotteryOrder extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String userId; // 中奖用户id
	private Integer typeId; // 中奖类型id
	private Integer goodsId; // 中奖奖品id
	private Integer addressId; // 寄送地址id
	private Integer accountId;//账户id
	private String expressCompany;//快递公司/卡号
	private String expressNumbers;//快递单号/卡密
	private String remarks;//备注
	private Long createTime; // 中奖时间
	private Long updateTime; // 修改时间
	private Integer status; // 中奖状态1.审核中2.拒绝3.待发货4.已发货
	private String operatorMobile;//操作人电话
	private String lockingMobile;//锁定人电话
	private Integer isLocking;//是否锁定
	private Long lotteryPig;
	
	private String admin;//管理员
	private String channelCode;//渠道标识
	private BigDecimal price;//奖品价格
	private String receiver;//收件人姓名
	private String operatorName;//管理员姓名
	private Integer locking;
	
	private Integer goodsType;
	
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private Long startTime;
    private Long endTime;
	
	private String goodsName;//商品名称
	private String mobile;//电话
	private String detailAddress;//地址
	private Integer lotterySort;//抽奖分类 1实物2虚拟
	private Long expendPigCoin;//抽奖消耗金猪
	private String imageUrl;//商品图片
	
	private String statusName;
	private String creatorDate;
	private String updateDate;
	
	private Integer pageIndex;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
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
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNumbers() {
		return expressNumbers;
	}
	public void setExpressNumbers(String expressNumbers) {
		this.expressNumbers = expressNumbers;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public Integer getLotterySort() {
		return lotterySort;
	}
	public void setLotterySort(Integer lotterySort) {
		this.lotterySort = lotterySort;
	}
	public Long getExpendPigCoin() {
		return expendPigCoin;
	}
	public void setExpendPigCoin(Long expendPigCoin) {
		this.expendPigCoin = expendPigCoin;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getOperatorMobile() {
		return operatorMobile;
	}
	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}
	public String getLockingMobile() {
		return lockingMobile;
	}
	public void setLockingMobile(String lockingMobile) {
		this.lockingMobile = lockingMobile;
	}
	public Integer getIsLocking() {
		return isLocking;
	}
	public void setIsLocking(Integer isLocking) {
		this.isLocking = isLocking;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Integer getLocking() {
		return locking;
	}
	public void setLocking(Integer locking) {
		this.locking = locking;
	}
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
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
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCreatorDate() {
		return creatorDate;
	}
	public void setCreatorDate(String creatorDate) {
		this.creatorDate = creatorDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public Long getLotteryPig() {
		return lotteryPig;
	}
	public void setLotteryPig(Long lotteryPig) {
		this.lotteryPig = lotteryPig;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	
}
