package com.mc.bzly.model.thirdparty;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.mc.bzly.model.BaseModel;

/**
 * 渠道配置用户分成奖励
 */
@Alias("MChannelConfigUser")
public class MChannelConfigUser extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer configId; // 渠道配置id
	private Integer userType; // 用户类型（1-普通用户 2-团队长）
	private Long sign7;
	private Long sign15;
	private Integer vip18;
	private Integer vip48;
	private Integer vip228;
	private Integer vip1188;
	private Integer vip1688;
	private Integer vip1888;
	private Integer vip3188;
	private Long level4;
	private Long level6;
	private Long level8;
	private Long level12;
	private Long createTime; // 创建时间
	private Integer status;  // 状态（1-启用 2-停用）
	private Integer darenCoin;// 达人推荐好友奖励金币数
	private Integer referrerAddition;//徒弟游戏试玩加成
	private Long recommendCoin;// 推荐有效好友奖励金币
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Long getSign7() {
		return sign7;
	}
	public void setSign7(Long sign7) {
		this.sign7 = sign7;
	}
	public Long getSign15() {
		return sign15;
	}
	public void setSign15(Long sign15) {
		this.sign15 = sign15;
	}
	public Integer getVip18() {
		return vip18;
	}
	public void setVip18(Integer vip18) {
		this.vip18 = vip18;
	}
	public Integer getVip48() {
		return vip48;
	}
	public void setVip48(Integer vip48) {
		this.vip48 = vip48;
	}
	public Integer getVip228() {
		return vip228;
	}
	public void setVip228(Integer vip228) {
		this.vip228 = vip228;
	}
	public Integer getVip1188() {
		return vip1188;
	}
	public void setVip1188(Integer vip1188) {
		this.vip1188 = vip1188;
	}
	public Integer getVip1688() {
		return vip1688;
	}
	public void setVip1688(Integer vip1688) {
		this.vip1688 = vip1688;
	}
	public Integer getVip1888() {
		return vip1888;
	}
	public void setVip1888(Integer vip1888) {
		this.vip1888 = vip1888;
	}
	public Integer getVip3188() {
		return vip3188;
	}
	public void setVip3188(Integer vip3188) {
		this.vip3188 = vip3188;
	}
	public Long getLevel4() {
		return level4;
	}
	public void setLevel4(Long level4) {
		this.level4 = level4;
	}
	public Long getLevel6() {
		return level6;
	}
	public void setLevel6(Long level6) {
		this.level6 = level6;
	}
	public Long getLevel8() {
		return level8;
	}
	public void setLevel8(Long level8) {
		this.level8 = level8;
	}
	public Long getLevel12() {
		return level12;
	}
	public void setLevel12(Long level12) {
		this.level12 = level12;
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
	public Integer getReferrerAddition() {
		return referrerAddition;
	}
	public void setReferrerAddition(Integer referrerAddition) {
		this.referrerAddition = referrerAddition;
	}
	public Long getRecommendCoin() {
		return recommendCoin;
	}
	public void setRecommendCoin(Long recommendCoin) {
		this.recommendCoin = recommendCoin;
	}
	public Integer getDarenCoin() {
		return darenCoin;
	}
	public void setDarenCoin(Integer darenCoin) {
		this.darenCoin = darenCoin;
	}
	
	
}
