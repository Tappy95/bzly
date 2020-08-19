package com.mc.bzly.model.user;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

@Alias("MUserInfo")
public class MUserInfo extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;            
	
	private Integer accountId;        

	private String userName;          

	private Integer sex;              

	private String birthday;          

	private String mobile;            

	private String aliasName;         

	private String identity;          

	private String socialDigitalNum;  

	private Integer digitalNumType;   

	private String profile;           

	private BigDecimal balance;       

	private BigDecimal jadeCabbage;   

	private Long coin;                

	private BigDecimal reward;        

	private Integer apprentice;       

	private String qrCode;            

	private String level;             

	private Long levelValue;          

	private String password;          

	private Long createTime;          

	private Long updateTime;          

	private String referrer;          

	private String referrerName;      

	private String referrerMobile;    

	private Long recommendedTime;     

	private String imei;

	private Integer equipmentType;    

	private Long pigCoin;             

	private String registrationId;    

	private String token;             

	private String aliNum;            

	private String openId;            

	private String referrerCode;

	private Integer sourceType;       

	private Integer status;   
	
	private String regImei;
	
	private String payPassword;

	private String channelCode;
	
	private String parentChannelCode; // 师傅的渠道标识
	
	private Integer roleType; // 角色类型（1-小猪猪 2-团队长 3-超级合伙人）
	
	private Integer surplusTime; // 角色剩余时间
	private Long xqEndTime; // 角色续期结束时间
	
	private String remark;
	private String wxNum;
	private String wxCode;
	
	private String qqNum;//用户qq号

	private String vipName; // VIP名称
	private Double vipAmount; // VIP累计充值
	private Integer vipCount; // VIP充值次数
	private Integer txCount; // 提现次数
	private Integer djCount; // 兑奖次数
	private Long txAmount; // 提现金额
	private Long yxCount;//完成游戏任务次数
	private Long zcYxCount;//注册当天完成游戏任务次数
	private String channelRelation;
	private Integer isRechargeVip;//是否购买vip 1是2否
	
	private String roleName;
	private String createDate;
	
	private Long startCreateTime;//起始时间
	private Long endCreateTime;//结束时间
	
	private Integer pageIndex;
	private Integer openActivity; // 是否打开活跃度展示（1-不展示 2-展示）
	private Integer highRole; // 高额赚角色（1-普通用户 2-小客服 3-合伙人）
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getSocialDigitalNum() {
		return socialDigitalNum;
	}

	public void setSocialDigitalNum(String socialDigitalNum) {
		this.socialDigitalNum = socialDigitalNum;
	}

	public Integer getDigitalNumType() {
		return digitalNumType;
	}

	public void setDigitalNumType(Integer digitalNumType) {
		this.digitalNumType = digitalNumType;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getJadeCabbage() {
		return jadeCabbage;
	}

	public void setJadeCabbage(BigDecimal jadeCabbage) {
		this.jadeCabbage = jadeCabbage;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

	public Integer getApprentice() {
		return apprentice;
	}

	public void setApprentice(Integer apprentice) {
		this.apprentice = apprentice;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(Long levelValue) {
		this.levelValue = levelValue;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public Long getRecommendedTime() {
		return recommendedTime;
	}

	public void setRecommendedTime(Long recommendedTime) {
		this.recommendedTime = recommendedTime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerMobile() {
		return referrerMobile;
	}

	public void setReferrerMobile(String referrerMobile) {
		this.referrerMobile = referrerMobile;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAliNum() {
		return aliNum;
	}

	public void setAliNum(String aliNum) {
		this.aliNum = aliNum;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getReferrerCode() {
		return referrerCode;
	}

	public void setReferrerCode(String referrerCode) {
		this.referrerCode = referrerCode;
	}

	public Long getCoin() {
		return coin;
	}

	public void setCoin(Long coin) {
		this.coin = coin;
	}

	public Long getPigCoin() {
		return pigCoin;
	}

	public void setPigCoin(Long pigCoin) {
		this.pigCoin = pigCoin;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRegImei() {
		return regImei;
	}

	public void setRegImei(String regImei) {
		this.regImei = regImei;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getParentChannelCode() {
		return parentChannelCode;
	}

	public void setParentChannelCode(String parentChannelCode) {
		this.parentChannelCode = parentChannelCode;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getSurplusTime() {
		return surplusTime;
	}

	public void setSurplusTime(Integer surplusTime) {
		this.surplusTime = surplusTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public Double getVipAmount() {
		return vipAmount;
	}

	public void setVipAmount(Double vipAmount) {
		this.vipAmount = vipAmount;
	}

	public Integer getVipCount() {
		return vipCount;
	}

	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}

	public Integer getTxCount() {
		return txCount;
	}

	public void setTxCount(Integer txCount) {
		this.txCount = txCount;
	}

	public Integer getDjCount() {
		return djCount;
	}

	public void setDjCount(Integer djCount) {
		this.djCount = djCount;
	}

	public Long getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(Long txAmount) {
		this.txAmount = txAmount;
	}

	public String getChannelRelation() {
		return channelRelation;
	}

	public void setChannelRelation(String channelRelation) {
		this.channelRelation = channelRelation;
	}

	public Long getXqEndTime() {
		return xqEndTime;
	}

	public void setXqEndTime(Long xqEndTime) {
		this.xqEndTime = xqEndTime;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getWxNum() {
		return wxNum;
	}

	public void setWxNum(String wxNum) {
		this.wxNum = wxNum;
	}

	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getIsRechargeVip() {
		return isRechargeVip;
	}

	public void setIsRechargeVip(Integer isRechargeVip) {
		this.isRechargeVip = isRechargeVip;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public Long getYxCount() {
		return yxCount;
	}

	public void setYxCount(Long yxCount) {
		this.yxCount = yxCount;
	}

	public Long getZcYxCount() {
		return zcYxCount;
	}

	public void setZcYxCount(Long zcYxCount) {
		this.zcYxCount = zcYxCount;
	}

	public Long getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Long getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Integer getOpenActivity() {
		return openActivity;
	}

	public void setOpenActivity(Integer openActivity) {
		this.openActivity = openActivity;
	}

	public Integer getHighRole() {
		return highRole;
	}

	public void setHighRole(Integer highRole) {
		this.highRole = highRole;
	}
}
