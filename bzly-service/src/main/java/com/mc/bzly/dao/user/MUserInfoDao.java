package com.mc.bzly.dao.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.DarenUserVo;
import com.mc.bzly.model.user.MChannelUser;
import com.mc.bzly.model.user.MUserInfo;

@Mapper
public interface MUserInfoDao {
	
	int insert(MUserInfo mUserInfo);
	
	void update(MUserInfo mUserInfo);
	
	int updateBaseInfo(MUserInfo mUserInfo);
	
	/**
	 * 查询用户基本信息（不包括账号金额信息）
	 * 1.手机号
	 * 2.账号密码
	 * 3.用户id
	 * @param paramMap
	 * @return
	 */
	MUserInfo selectUserBaseInfo(Map<String, Object> paramMap);
	/**
	 * 查询用户基本信息（不包括账号金额信息）
	 * 1.手机号
	 * 2.账号密码
	 * 3.用户id
	 * @param paramMap
	 * @return
	 */
	MUserInfo selectUserForLogin(Map<String, Object> paramMap);
	
	/**
	 * 查询用户全部信息
	 * @param userId
	 * @return
	 */
	MUserInfo selectOne(String userId);
	/**
	 * 签到修改金币
	 * @param coin
	 * @param usreId
	 * @return
	 */
	Integer updatecCoin(@Param("coin")Long coin,@Param("userId")String userId);

	/**
	 * @param mUserInfo
	 * @return
	 */
	List<MUserInfo> selectList(MUserInfo mUserInfo);

	Map<String, Object> selectByQrCode(String qrCode);

	void batchUpdate(List<MUserInfo> mUserInfos);
	/**
	 * 减少金币
	 * @param coin
	 * @param userId
	 * @return
	 */
	Integer updatecCoinReduce(@Param("coin")Long coin,@Param("userId")String userId);

	/**
	 * 重置密码
	 * @param mUserInfo
	 */
	void updatePassword(MUserInfo mUserInfo);

	MUserInfo selectUserAuth(Map<String, Object> param);
	/**
	 * 增加金猪
	 * @param pigCoin
	 * @param userId
	 * @return
	 */
	Integer updatecPigAdd(@Param("pigCoin")Long pigCoin,@Param("userId")String userId);
	/**
	 * 用户余额减少
	 * @param balance
	 * @param userId
	 * @return
	 */
	Integer updatecBalanceReduce(@Param("balance")BigDecimal balance,@Param("userId")String userId);

	MUserInfo selectByMobile(String mobile);
	/**
	 * 修改支付密码
	 * @param mUserInfo
	 * @return
	 */
	Integer updatePayPassword(MUserInfo mUserInfo);
	/**
	 * 渠道用户列表
	 * @param mChannelUser
	 * @return
	 */
	List<MChannelUser> selectChannelUser(MChannelUser mChannelUser);
	/**
	 * 渠道用户导出
	 * @param mChannelUser
	 * @return
	 */
	List<Map<String,Object>> selectChannelExclUser(MChannelUser mChannelUser);
	
	void updateRoleType(MUserInfo mUserInfo);
	
	/**
	 * @param mUserInfo
	 * @return
	 */
	List<MUserInfo> selectNew(MUserInfo mUserInfo);
	/**
	 * 查看用户是否冻结
	 * @param userId
	 * @return
	 */
	Map<String,Object> selectFrozen(String userId);
	/**
	 * 团队人数
	 * @param referrer
	 * @param roleType
	 * @return
	 */
	int teamPeopleNum(@Param("referrer")String referrer,@Param("roleType")Integer roleType);
	/**
	 * 间接推荐人数
	 * @param referrer
	 * @return
	 */
	int teamIndirectNum(@Param("referrer")String referrer);

	long selectNewCount(MUserInfo mUserInfo);

	List<MChannelUser> selectChannelUserNew(MChannelUser mChannelUser);

	long selectChannelUserCount(MChannelUser mChannelUser);
	
	List<MUserInfo> selectExcl(MUserInfo mUserInfo);
	
	MUserInfo selectHtOne(String userId);
	
	MUserInfo selectByAccountId(Integer accountId);
	
	int updatecPigReduce(@Param("pigCoin")Long pigCoin,@Param("userId")String userId);
	
	int selectAliNum(String aliNum);

	List<Map<String, Object>> selectReffer(List<String> referrer);

	List<Map<String, Object>> selectVipAmount(List<String> userId);

	List<Map<String, Object>> selectVipCount(List<String> userId);

	List<Map<String, Object>> selectTxAmount(List<String> userId);

	List<Map<String, Object>> selectTxCount(List<String> userId);

	List<Map<String, Object>> selectDjCount(List<String> userId);
	
	List<Map<String, Object>> selectZcGameCount(List<String> userId);

	List<String> selectChannelUsers(@Param("channelCode")String channelCode);
	
	int selectUserAli(String userId);
	
	int selectOpenId(String openId);
	
	int selectUserWx(String userId);
	
	List<Map<String, Object>> selectCzAmount(List<String> userId);
	
	List<Map<String, Object>> selectCqCoin(List<String> userId);
	
	List<Map<String, Object>> selectChannelTxAmount(List<String> userId);
	
	List<Map<String, Object>> selectGameCount(List<String> userId);

	List<DarenUserVo> selectDrList(DarenUserVo darenUserVo);

	long selectDrCount(DarenUserVo darenUserVo);

	int updateUserDR(@Param("accountId")Integer accountId,@Param("roleType")Integer roleType, @Param("darenTime") Long time);

	void updateOpenActivity(@Param("userId")String userId, @Param("openActivity")Integer openActivity);
	
	Map<String,Object> selectAccountId(Integer accountId);
	
	long selectBZ28INGCount(@Param("userId")String userId);

	long selectXY28INGCount(@Param("userId")String userId);
	
	MUserInfo selectBackstage(MUserInfo mUserInfo);

	MUserInfo selectInfoOpenId(@Param("openId")String openId);
	
	List<String> selectUserRelation(@Param("userRelation") Integer userRelation,@Param("channelCode")String channelCode);
	
	/**
	 * 查询名下有多少个小客服
	 */
	long selectHighVipXKFCount(@Param("userId")String userId);
}
