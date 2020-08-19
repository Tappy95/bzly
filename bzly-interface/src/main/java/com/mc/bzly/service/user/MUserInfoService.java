package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.DarenUserVo;
import com.mc.bzly.model.user.MChannelUser;
import com.mc.bzly.model.user.MUserInfo;

public interface MUserInfoService {
	
	Result addMUserInfo(MUserInfo mUserInfo) throws Exception;
	
	void modifyMUserInfo(MUserInfo mUserInfo) throws Exception;

	void modifyBaseMUserInfo(MUserInfo mUserInfo) throws Exception;
	
	MUserInfo queryUserBaseInfo(Map<String, Object> param);
	
	MUserInfo queryOne(String userId);
	
	Result loginByPassword(Map<String, Object> paramMap);

	Result loginBySms(Map<String, Object> paramMap);

	PageInfo<MUserInfo> queryList(MUserInfo mUserInfo);

	Map<String, Object> queryByQrCode(String qrCode);

	Result resetPassword(MUserInfo mUserInfo);
	
	MUserInfo queryUserAuth(Map<String, Object> param);
	 
	Result bindQrCode(String userId, String qrCode) throws Exception;
	 
	Result bindingNumber(MUserInfo mUserInfo,Integer type,String appId,String appSecret);

	MUserInfo queryByMobile(String mobile);
	 
	Integer modifyBindingNumber(MUserInfo mUserInfo,Integer type,String appId,String appSecret);
	 
	long getUserCoin(String userId);
	 
	Map<String, Object> getUserPigCoin(String userId);

	Result regH5(MUserInfo mUserInfo);

	MUserInfo queryUserBaseInfoSimple(Map<String, Object> param);
	
	Integer updatePayPassword(MUserInfo mUserInfo);
	/**
	 * 渠道用户列表
	 * @param mChannelUser
	 * @return
	 */
	//PageInfo<MChannelUser> selectChannelUser(MChannelUser mChannelUser);
	Map<String, Object> selectChannelUser(MChannelUser mChannelUser);
	/**
	 * 渠道用户导出
	 * @param mChannelUser
	 * @return
	 */
	List<Map<String,Object>> selectChannelExclUser(MChannelUser mChannelUser);

	/**
	 * 设置用户为超级合伙人
	 * @param userId
	 * @param remark
	 * @return
	 */
	int setSuperPartner(String userId,Integer isSuper,String remark);

	/**
	 * 冻结用户
	 * @param userId
	 * @param remark
	 * @return
	 */
	void freezeUser(String userId,Integer isFrozen,String remark);

	/**
	 * 查询用户（平台使用）
	 * @param mUserInfo
	 * @return
	 */
	Map<String, Object> queryListNew(MUserInfo mUserInfo);
	/**
	 * 查看用户是否冻结
	 * @param userId
	 * @return
	 */
	Map<String,Object> selectFrozen(String userId);
	
	Map<String,Object> extensionStatistic(String userId);
	
	/**
	 * @param outTradeNo 订单号
	 * @param now
	 */
	public void changeRoleType(String outTradeNo,long now);
	
	List<MUserInfo> selectExcl(MUserInfo mUserInfo);
	
	Integer bindingWxNumber(MUserInfo mUserInfo,Integer type,String appId,String appSecret);
	
	Integer modifyBindingWxNumber(MUserInfo mUserInfo, Integer type,String appId,String appSecret);

	/**
	 * 查询达人用户
	 * @param darenUserVo
	 * @return
	 */
	Map<String, Object> selectDrList(DarenUserVo darenUserVo);
	/**
	 * 设置用户为邀请达人
	 * @param accountId
	 */
	int setDR(Integer accountId);

	void cancleDR(Integer accountId);
	
	List<DarenUserVo> drExcel(DarenUserVo darenUserVo);
	
	Result regChannelH5(MUserInfo mUserInfo);
	
	MUserInfo selectAccountId(Integer accountId);
	
	Map<String,Object> isUser(Integer accountId);
	
	Map<String,Object> zjdUser(String userId);
	
	Map<String,Object> mailList(int addressBook,int callRecord);

	Result wechatLogin(MUserInfo mUserInfo,String appId,String appSecret);

	Result bindMobile(MUserInfo mUserInfo);
	
	/**
	 * 心愿猪app注册
	 * @param mUserInfo
	 * @return
	 * @throws Exception
	 */
	Result addWishMUserInfo(MUserInfo mUserInfo) throws Exception;
	/**
	 * 心愿猪h5注册
	 * @param mUserInfo
	 * @return
	 */
	Result regWishH5(MUserInfo mUserInfo);
	/**
	 * 心愿猪绑定微信
	 * @param mUserInfo
	 * @return
	 */
	Result bindWishMobile(MUserInfo mUserInfo);
	/**
	 * 心愿猪密码登录
	 * @param paramMap
	 * @return
	 */
	Result loginWishByPassword(Map<String, Object> paramMap);
	/**
	 * 心愿猪验证码登录
	 * @param paramMap
	 * @return
	 */
	Result loginWishBySms(Map<String, Object> paramMap);
	/**
	 * 心愿猪微信登录
	 * @param mUserInfo
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	Result wechatWishLogin(MUserInfo mUserInfo,String appId,String appSecret);
	/**
	 * 心愿猪修改密码
	 * @param mUserInfo
	 * @return
	 */
	Result resetWishPassword(MUserInfo mUserInfo);
}

