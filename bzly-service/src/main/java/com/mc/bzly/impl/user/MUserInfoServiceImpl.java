package com.mc.bzly.impl.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.thirdparty.WxSupport;
import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.passbook.MVipInfoDao;
import com.mc.bzly.dao.pay.PPayLogDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.thirdparty.MFissionSchemeDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LDarenRewardDao;
import com.mc.bzly.dao.user.LDarenRewardDetailDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserActivityDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserGyroDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.wish.UserWishValueDao;
import com.mc.bzly.dao.wish.WishGainDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.passbook.MVipInfo;
import com.mc.bzly.model.pay.PPayLog;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.thirdparty.MFissionScheme;
import com.mc.bzly.model.user.DarenUserVo;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserActivity;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MChannelUser;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.wish.UserWishValue;
import com.mc.bzly.model.wish.WishGain;
import com.mc.bzly.redis.CodisConfig;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.passbook.MPassbookService;
import com.mc.bzly.service.platform.PDictionaryService;
import com.mc.bzly.service.platform.PLevelService;
import com.mc.bzly.service.redis.RedisService;
import com.mc.bzly.service.user.MUserInfoService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass=MUserInfoService.class,version=WebConfig.dubboServiceVersion)
public class MUserInfoServiceImpl implements MUserInfoService {

	@Autowired 
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private PDictionaryService pDictionaryService;
	@Autowired
	private UserWishValueDao userWishValueDao;
	@Autowired
	private WishGainDao wishGainDao;
	@Autowired
	private PLevelService pLevelService;
	@Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
    private LPigChangeDao lPigChangeDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private MPassbookService mPassbookService;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private MVipInfoDao mVipInfoDao;
	@Autowired
	private MFissionSchemeDao mFissionSchemeDao;
	@Autowired
	private PPayLogDao pPayLogDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	private final String tokenPrefix = "bz_user_token_";
	@Autowired
	private LDarenRewardDao lDarenRewardDao;
	@Autowired
	private LDarenRewardDetailDao lDarenRewardDetailDao;
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	@Autowired
	private MChannelInfoDao mChannelInfoDao;
	@Autowired
	private LUserActivityDao lUserActivityDao;
	@Autowired
	private MUserGyroDao mUserGyroDao;
	
	@Transactional
	@Override
	public Result addMUserInfo(MUserInfo mUserInfo) throws Exception {
		Result result = new Result();
		Long now = new Date().getTime();
		MUserInfo referrer = null;
		// 判断手机号是否存在
		Map<String , Object> param = new HashMap<>();
		param.put("mobile", mUserInfo.getMobile());
		MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
		if(oldUserInfo != null){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		PDictionary regCoin = pDictionaryService.queryByName("reg_coin");
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(regCoin == null?0l:new Long(regCoin.getDicValue())); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setRegImei(mUserInfo.getImei());
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		if(StringUtils.isEmpty(mUserInfo.getProfile())){
			// 七牛云默认头像
			mUserInfo.setProfile("https://image.bzlyplay.com/default_img.png");
		}
		// 生成token
		String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
		String token = MD5Util.getMd5(v);
		mUserInfo.setToken(token);
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
				if(oldUserInfo != null){
					return "1";
				}
				mUserInfoDao.insert(mUserInfo);
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		if("1".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		Map<String,Object> redisMap=new HashMap<>();
		redisMap.put("userId", mUserInfo.getUserId());
		redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
		
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			map.put("regType", "app");
			jmsProducer.sendMessage(Type.ADD_APPRENTICE, map);
		}
		// 发放卡券
		if(!"xiaqiu".equals(mUserInfo.getChannelCode())){
			jmsProducer.sendMessage(Type.REG_SEND_PASSBOOK, mUserInfo.getUserId());
		}
		
		if(mUserInfo.getCoin().longValue() > 0){
			// 插入用户金币变更记录
			LCoinChange lCoinChange = new LCoinChange(mUserInfo.getUserId(), mUserInfo.getCoin(), ConstantUtil.FLOWTYPE_IN,
					ConstantUtil.COIN_CHANGED_TYPE_9, now, 1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange);
		}
		//新手注册任务
	    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
	    if(StringUtil.isNullOrEmpty(lUserTask)) {
	    	LUserTask userTask=new LUserTask();
			userTask.setUserId(mUserInfo.getUserId());
			userTask.setTaskId(11);
			userTask.setCreateTime(new Date().getTime());
			userTask.setIsReceive(2);
			lUserTaskDao.insert(userTask);
	    }
		mUserInfo.setPassword(null);
		mUserInfo.setUserId(null);
		result.setToken(token);
		result.setData(mUserInfo);
		result.setSuccess(true);
		result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		// 发送快手回调
		/*String imei = mUserInfo.getImei();
		if(StringUtils.isNotBlank(imei)){
			String imeiMD5 = MD5Util.getMd5(imei);
			jmsProducer.sendMessage(Type.SEND_KS_CALLBACK, imeiMD5);
		}*/
		return result;
	}

	@Transactional
	@Override
	public void modifyMUserInfo(MUserInfo mUserInfo) throws Exception {
		mUserInfoDao.update(mUserInfo);
	}

	@Transactional
	@Override
	public void modifyBaseMUserInfo(MUserInfo mUserInfo) throws Exception {
		mUserInfoDao.updateBaseInfo(mUserInfo);
		MUserInfo user=mUserInfoDao.selectOne(mUserInfo.getUserId());
		if(!StringUtil.isNullOrEmpty(user.getQqNum()) && !StringUtil.isNullOrEmpty(user.getAliasName()) && !StringUtil.isNullOrEmpty(user.getSex()) && !StringUtil.isNullOrEmpty(user.getBirthday())) {
			Map<String,Object> data=new HashMap<>();
			data.put("userId", mUserInfo.getUserId());
			data.put("task", 20);
			jmsProducer.userTask(Type.USER_TASK, data);
		}
	}

	@Override
	public MUserInfo queryUserBaseInfo(Map<String, Object> param) {
		MUserInfo info = mUserInfoDao.selectUserBaseInfo(param);
		if(StringUtils.isNotBlank(info.getReferrer())){
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("userId", info.getReferrer());
			MUserInfo reffer = mUserInfoDao.selectUserBaseInfo(para);
			info.setReferrerCode(reffer.getQrCode());
		}
		if(info != null){
			if(("zhongqing".equals(info.getChannelCode())) || ("zhongqing".equals(info.getParentChannelCode()))){
				PDictionary recommendUrl = pDictionaryService.queryByName("recommend_url_zq");
				info.setQrCode(recommendUrl.getDicValue()+info.getQrCode());
			}else if(("shendumeng".equals(info.getChannelCode())) || ("shendumeng".equals(info.getParentChannelCode()))){
				PDictionary recommendUrl = pDictionaryService.queryByName("recommend_url_sdm");
				info.setQrCode(recommendUrl.getDicValue()+info.getQrCode());
			}else{
				PDictionary recommendUrl = pDictionaryService.queryByName("recommend_url");
				info.setQrCode(recommendUrl.getDicValue()+info.getQrCode());
			}
		}
		return info;
	}

	@Override
	public MUserInfo queryOne(String userId) {
		return mUserInfoDao.selectHtOne(userId);
	}

	@Override
	public Result loginByPassword(Map<String, Object> paramMap) {
		Result result = new Result();
		MUserInfo mUserInfo = mUserInfoDao.selectUserForLogin(paramMap);
		if(mUserInfo == null){
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FAILURE.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FAILURE.getStatusCode());
		}else if(mUserInfo.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
		}else{
			String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			if(channelInfo.getWebType().intValue()==3) {
				result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
				result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
				return result;
			}
			String userId = mUserInfo.getUserId();
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(mUserInfo.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				mUserInfo.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", mUserInfo.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			mUserInfo.setEquipmentType(new Integer(paramMap.get("equipmentType").toString()));
			mUserInfo.setRegistrationId(paramMap.get("registrationId").toString());
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(mUserInfo.getImei())){
				//新手注册任务
			    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
			    if(StringUtil.isNullOrEmpty(lUserTask)) {
			    	LUserTask userTask=new LUserTask();
					userTask.setUserId(mUserInfo.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
			    }
			}
			mUserInfo.setImei(paramMap.get("imei").toString());
			mUserInfoDao.updateBaseInfo(mUserInfo);
			
			mUserInfo.setUserId(null);
			mUserInfo.setPassword(null);
			mUserInfo.setPayPassword(null);
			result.setToken(mUserInfo.getToken());
			result.setData(mUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}
		return result;
	}

	@Override
	public Result loginBySms(Map<String, Object> paramMap) {
		Result result = new Result();
		MUserInfo mUserInfo = mUserInfoDao.selectUserForLogin(paramMap);
//		MUserInfo mUserInfo = null;
		if(mUserInfo == null){
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FAILURE.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FAILURE.getStatusCode());
		}else if(mUserInfo.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
		}else{
//			String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
//			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
//			if(channelInfo.getWebType().intValue()==3) {
//				result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
//				result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
//				return result;
			System.out.println("第三选项");

			String userId = mUserInfo.getUserId();
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(mUserInfo.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				mUserInfo.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", mUserInfo.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			mUserInfo.setEquipmentType(new Integer(paramMap.get("equipmentType").toString()));
			mUserInfo.setRegistrationId(paramMap.get("registrationId").toString());
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(mUserInfo.getImei())){
				//新手注册任务
			    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
			    if(StringUtil.isNullOrEmpty(lUserTask)) {
			    	LUserTask userTask=new LUserTask();
					userTask.setUserId(mUserInfo.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
			    }
			}
			mUserInfo.setImei(paramMap.get("imei").toString());
			mUserInfoDao.updateBaseInfo(mUserInfo);
			
			mUserInfo.setUserId(null);
			mUserInfo.setPassword(null);
			mUserInfo.setPayPassword(null);
			result.setToken(mUserInfo.getToken());
			result.setData(mUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}
		return result;
	}

	@Override
	public PageInfo<MUserInfo> queryList(MUserInfo mUserInfo) {
		PageHelper.startPage(mUserInfo.getPageNum(), mUserInfo.getPageSize());
		List<MUserInfo> mUserInfoList = mUserInfoDao.selectList(mUserInfo);
		return new PageInfo<>(mUserInfoList);
	}

	@Override
	public Map<String, Object> queryByQrCode(String qrCode) {
		return mUserInfoDao.selectByQrCode(qrCode);
	}
	
	@Override
	public MUserInfo queryByMobile(String mobile) {
		return mUserInfoDao.selectByMobile(mobile);
	}

	@Override
	public Result resetPassword(MUserInfo mUserInfo) {
		Result result=new Result();
		String channelCode =StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
		if(channelInfo.getWebType().intValue()==3) {
			result.setMessage(RespCodeState.BZLY_UPDE_PASSWORD.getMessage());
			result.setStatusCode(RespCodeState.BZLY_UPDE_PASSWORD.getStatusCode());
			return result;
		}	
		mUserInfoDao.updatePassword(mUserInfo);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

	@Override
	public MUserInfo queryUserAuth(Map<String, Object> param) {
		return mUserInfoDao.selectUserAuth(param);
	}

	@Transactional
	@Override
	public Result bindQrCode(String userId, String qrCode) throws Exception {
		long now = new Date().getTime();
		
		Result result = new Result();
		String refferrerId = "";
		// 1.校验邀请码是否存在
		Map<String, Object> refferrer = mUserInfoDao.selectByQrCode(qrCode);
		if(refferrer == null){
			result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());
			result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
			return result;
		}else{
			if(!StringUtil.isNullOrEmpty(refferrer.get("referrerId"))) {
				refferrerId = refferrer.get("referrerId").toString();	
			}
		}
		// 2.判断是否是自己
		if(userId.equals(refferrer.get("refferrer"))){
			result.setMessage(RespCodeState.REFFERRER_NOT_YOURSELF.getMessage());
			result.setStatusCode(RespCodeState.REFFERRER_NOT_YOURSELF.getStatusCode());
			return result;
		}
		// 3.判断邀请人的师傅是不是自己
		if(StringUtils.isNotBlank(refferrerId)){
			if(userId.equals(refferrerId)){
				result.setMessage(RespCodeState.REFFERRER_NOT_USE.getMessage());
				result.setStatusCode(RespCodeState.REFFERRER_NOT_USE.getStatusCode());
				return result;
			}
		}
		MUserInfo userInfoOld = mUserInfoDao.selectOne(userId);
		if(StringUtils.isNotEmpty(userInfoOld.getReferrer())){
			result.setMessage("不能重复绑定邀请人");
			result.setStatusCode("3022");
			return result;
		}
		userInfoOld.setReferrer(refferrer.get("refferrer").toString());
		mUserInfoDao.update(userInfoOld);
		int roleType = (int) refferrer.get("roleType");
		// 3.修改邀请码主人的徒弟数量
		MUserInfo userInfo = new MUserInfo();
		userInfo.setUserId(refferrer.get("refferrer").toString());
		userInfo.setUpdateTime(now);
		userInfo.setApprentice((int)refferrer.get("apprentice")+ 1);
		mUserInfoDao.update(userInfo);
		// 4.新增一条师徒关系记录
		MUserApprentice mUserApprentice = new MUserApprentice();
		mUserApprentice.setReferrerId(userInfo.getUserId());
		mUserApprentice.setUserId(userId);
		mUserApprentice.setCreateTime(now);
		mUserApprentice.setUpdateTime(now);
		mUserApprentice.setContribution(0l);
		mUserApprentice.setRewardStatus(1);
		if(roleType == 4){ 
			// 如果用户是当天注册的，则给奖励
			long dayTime = 24 * 60 * 60 * 1000;
			long regTimeMin = now - dayTime;
			if(userInfoOld.getCreateTime() < regTimeMin){
				mUserApprentice.setRewardStatus(2);
			}
			mUserApprentice.setApprenticeType(2);
		}else{
			mUserApprentice.setApprenticeType(1);
		}
		mUserApprenticeDao.insert(mUserApprentice);
		
		result.setData(null);
		result.setSuccess(true);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

	@Override
	public Result bindingNumber(MUserInfo mUserInfo,Integer type,String appId,String appSecret) {
		MUserInfo user=mUserInfoDao.selectOne(mUserInfo.getUserId());
		Result result = new Result();
		if(type==1) {//支付宝
			int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
			int aliCount=mUserInfoDao.selectAliNum(mUserInfo.getAliNum());
			if(aliCount>=count) {
				result.setStatusCode(RespCodeState.ACCOUNT_ERROR.getStatusCode());
				result.setMessage(RespCodeState.ACCOUNT_ERROR.getMessage());
				return result;
			}
			if(!StringUtil.isPhoneNo(mUserInfo.getAliNum()) && !StringUtil.isEmailNo(mUserInfo.getAliNum())) {
				result.setStatusCode(RespCodeState.NUM_ERROR.getStatusCode());
				result.setMessage(RespCodeState.NUM_ERROR.getMessage());
				return result;//账号格式不正确
			}
			if(!StringUtil.isNullOrEmpty(user.getAliNum())) {
				result.setStatusCode(RespCodeState.ALI_NUM_EXIST.getStatusCode());
				result.setMessage(RespCodeState.ALI_NUM_EXIST.getMessage());
				return result;//已绑定支付宝账号
			}else {
				mUserInfoDao.update(mUserInfo);
			}
		}else {//微信
			if(StringUtil.isNullOrEmpty(user.getOpenId())) {
				Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
				String openId = String.valueOf(data.get("openid"));
				if(StringUtil.isNullOrEmpty(openId)) {
					result.setStatusCode(RespCodeState.OPENID_ERROR.getStatusCode());
					result.setMessage(RespCodeState.OPENID_ERROR.getMessage());
					 return result;//openId获取失败
				}
		        int wxCount=mUserInfoDao.selectOpenId(openId);
		        if(wxCount>=1) {
		        	result.setStatusCode(RespCodeState.WX_ALREADY_BINDING.getStatusCode());
					result.setMessage(RespCodeState.WX_ALREADY_BINDING.getMessage());
					return result;
				}
		        if(!StringUtil.isNullOrEmpty(data.get("access_token"))) {
		        	Map<String,Object> wxUserInfo=WxSupport.getUserInfo(data.get("access_token").toString(),data.get("openid").toString());
					if(!StringUtil.isNullOrEmpty(wxUserInfo)) {
						if(!StringUtil.isNullOrEmpty(wxUserInfo.get("headimgurl").toString())) {
							mUserInfo.setProfile(wxUserInfo.get("headimgurl").toString());
						}
						if(!StringUtil.isNullOrEmpty(wxUserInfo.get("nickname").toString())) {
							mUserInfo.setAliasName(wxUserInfo.get("nickname").toString());
						}
					}
		        }
		        mUserInfo.setOpenId(openId);
				mUserInfoDao.update(mUserInfo);
			}else {
				mUserInfoDao.update(mUserInfo);
			}
			
		}
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}
	@Transactional
	public void taskPush(String userId,Integer task) {
		//完成任务获得奖励
		LUserTask lUserTask=lUserTaskDao.selectOne(userId, task);
		Map<String,Object> date=new HashMap<>();
		if(StringUtil.isNullOrEmpty(lUserTask)) {
			MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(task);
			date=mPassbookService.taskUsePassbook(userId, mTaskInfo.getTaskType());;
			long multiple=(long)date.get("res");
			LUserTask userTask=new LUserTask();
			userTask.setUserId(userId);
			userTask.setTaskId(task);
			userTask.setCreateTime(new Date().getTime());
			lUserTaskDao.insert(userTask);
			MUserInfo user=mUserInfoDao.selectOne(userId);
			if(mTaskInfo.getRewardUnit()==1) {//金币奖励
				LCoinChange change=new LCoinChange();
				long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
				change.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_10);
				change.setFlowType(1);
				change.setChangedTime(new Date().getTime());
				change.setUserId(userId);
				change.setAmount(amount);
				change.setCoinBalance(user.getCoin()+amount);
				mUserInfoDao.updatecCoin(amount, userId);
				lCoinChangeDao.insert(change);
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(mTaskInfo.getRemark()+"+"+amount+"金币");	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}else if(mTaskInfo.getRewardUnit()==2) {//金猪奖励
				long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
				LPigChange pig=new LPigChange();
			    pig.setUserId(userId);
			    pig.setAmount(amount);
			    pig.setFlowType(1);
			    pig.setChangedType(ConstantUtil.PIG_CHANGED_TYPE_3);
			    pig.setChangedTime(new Date().getTime());
			    pig.setPigBalance(user.getPigCoin()+amount);
			    lPigChangeDao.insert(pig);
			    mUserInfoDao.updatecPigAdd(amount, userId);
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(mTaskInfo.getRemark()+"+"+amount+"金猪");	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}
			mPassbookService.passbookOverdue((int)date.get("fbrspassbookId"));
	   }
	}

	@Override
	public Integer modifyBindingNumber(MUserInfo mUserInfo, Integer type,String appId,String appSecret) {
		//查看用户姓名是否受限
		String[] nameAt=pDictionaryDao.selectByName(DictionaryUtil.CASH_LIMIT_NAME).getDicValue().split(",");
		for(String name:nameAt) {
			if(name.equals(mUserInfo.getUserName())) {
				return 2;
			}
		}
		MUserInfo user=mUserInfoDao.selectOne(mUserInfo.getUserId());
		int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
		if(type==1) {//支付宝
			int oldAli=mUserInfoDao.selectUserAli(mUserInfo.getUserId());
			if(!mUserInfo.getAliNum().equals(user.getAliNum())) {
				if(oldAli>count) {
					return 6;
				}
				
				int aliCount=mUserInfoDao.selectAliNum(mUserInfo.getAliNum());
				if(aliCount>=count) {
					return 6;
				}
				
				if(!StringUtil.isPhoneNo(mUserInfo.getAliNum()) && !StringUtil.isEmailNo(mUserInfo.getAliNum())) {
					return 2;//账号格式不正确
				}
			}
			mUserInfoDao.update(mUserInfo);
		}else {//微信
			int oldWx=mUserInfoDao.selectUserWx(mUserInfo.getUserId());
			if(oldWx>count) {
				return 6;
			}
			
			Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
			String openId = String.valueOf(data.get("openid"));
			
			int wxCount=mUserInfoDao.selectOpenId(openId);
			if(wxCount>=count) {
				return 6;
			}
			
			mUserInfo.setOpenId(openId);
			mUserInfoDao.update(mUserInfo);
		}
		return 1;
	}

	@Override
	public long getUserCoin(String userId) {
		MUserInfo user=mUserInfoDao.selectOne(userId);
		return user.getCoin();
	}

	@Override
	public Map<String, Object> getUserPigCoin(String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		resultMap.put("pigCoin", user.getPigCoin());
		List<MVipInfo> vips = mVipInfoDao.selectByUser(userId);
		// 查询用户最大VIP
		if(vips!= null && vips.size() > 0 ){
			resultMap.put("maxLimit", vips.get(0).getOnetimeLimit());
		}else{
			resultMap.put("maxLimit", 50000000);
		}
		return resultMap;
	}

	@Override
	public Result regH5(MUserInfo mUserInfo) {
		Result result = new Result();
		Long now = new Date().getTime();
		MUserInfo referrer = null;
		// 判断手机号是否存在
		Map<String , Object> param = new HashMap<>();
		param.put("mobile", mUserInfo.getMobile());
		MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
		if(oldUserInfo != null){
			String channelCode=StringUtil.isNullOrEmpty(oldUserInfo.getChannelCode())?oldUserInfo.getParentChannelCode():oldUserInfo.getChannelCode();
			if(StringUtil.isNullOrEmpty(channelCode)) {
				channelCode="baozhu";
			}
			result.setData(channelCode);
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		PDictionary regCoin = pDictionaryService.queryByName("reg_coin");
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(regCoin == null?0l:new Long(regCoin.getDicValue())); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
				if(oldUserInfo != null){
					return "1";
				}
				mUserInfoDao.insert(mUserInfo);
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		String channelCode=StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="baozhu";
		}
		if("1".equals(regStr)){
			result.setData(channelCode);
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			map.put("regType", "H5");
			jmsProducer.sendMessage(Type.ADD_APPRENTICE, map);
		}
		// 发放卡券
		jmsProducer.sendMessage(Type.REG_SEND_PASSBOOK, mUserInfo.getUserId());
		if(mUserInfo.getCoin().longValue() > 0){
			// 插入用户金币变更记录
			LCoinChange lCoinChange = new LCoinChange(mUserInfo.getUserId(), mUserInfo.getCoin(), ConstantUtil.FLOWTYPE_IN,
					ConstantUtil.COIN_CHANGED_TYPE_9, now, 1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange);
		}
		//新手注册任务
	    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
	    if(StringUtil.isNullOrEmpty(lUserTask)) {
	    	LUserTask userTask=new LUserTask();
			userTask.setUserId(mUserInfo.getUserId());
			userTask.setTaskId(11);
			userTask.setCreateTime(new Date().getTime());
			userTask.setIsReceive(2);
			lUserTaskDao.insert(userTask);
	    }
	    result.setData(channelCode);
		result.setSuccess(true);
		result.setMessage(RespCodeState.USER_REGISTERED_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.USER_REGISTERED_SUCCESS.getStatusCode());
		return result;
	}
	
	@Override
	public MUserInfo queryUserBaseInfoSimple(Map<String, Object> param){
		return mUserInfoDao.selectUserBaseInfo(param);
	}

	@Override
	public Integer updatePayPassword(MUserInfo mUserInfo) {
		return mUserInfoDao.updatePayPassword(mUserInfo);
	}

	@Override
	public Map<String, Object> selectChannelUser(MChannelUser mChannelUser) {
		Map<String, Object> result = new HashMap<>();
		mChannelUser.setPageIndex(mChannelUser.getPageSize() * (mChannelUser.getPageNum() - 1));
		List<MChannelUser> mChannelUserList = mUserInfoDao.selectChannelUserNew(mChannelUser);
		List<String> userIds=new ArrayList<String>();
		for(MChannelUser m:mChannelUserList) {
			userIds.add(m.getUserId());
		}
		if(userIds.size()>0) {
			List<Map<String, Object>> txAmounts = mUserInfoDao.selectChannelTxAmount(userIds);//累计提现
			List<Map<String, Object>> czAmounts = mUserInfoDao.selectCzAmount(userIds);//累计充值
			List<Map<String, Object>> cqCoins = mUserInfoDao.selectCqCoin(userIds);//累计赚取金币
			for(int i=0;i<mChannelUserList.size();i++) {
				for (Map<String, Object> txAmount : txAmounts) {
					if(txAmount.get("userId").toString().equals(mChannelUserList.get(i).getUserId())){
						String str=txAmount.get("txAmount")+"";
						mChannelUserList.get(i).setSumCash(Long.parseLong(str));
						txAmounts.remove(txAmount);
						break;
					}
				}
				for (Map<String, Object> czAmount : czAmounts) {
					if(czAmount.get("userId").toString().equals(mChannelUserList.get(i).getUserId())){
						String str = czAmount.get("czAmount")+"";
						mChannelUserList.get(i).setSumRecharge(Double.parseDouble(str));
						czAmounts.remove(czAmount);
						break;
					}
				}
				for (Map<String, Object> cqCoin : cqCoins) {
					if(cqCoin.get("userId").toString().equals(mChannelUserList.get(i).getUserId())){
						String str = cqCoin.get("zqCoin")+"";
						mChannelUserList.get(i).setSumCoin(Long.parseLong(str));
						cqCoins.remove(cqCoin);
						break;
					}
				}
			}
		}
		long count = mUserInfoDao.selectChannelUserCount(mChannelUser);
		result.put("list", mChannelUserList);
		result.put("total", count);
		return result;
	}

	@Override
	public List<Map<String, Object>> selectChannelExclUser(MChannelUser mChannelUser) {
		return mUserInfoDao.selectChannelExclUser(mChannelUser);
	}

	@Override
	public int setSuperPartner(String userId,Integer isSuper,String remark) {
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		if(mUserInfo.getRoleType() == 1){
			return 2;
		}
		if(mUserInfo.getStatus() != 1){
			return 3;
		}
		if(isSuper==3) {
			mUserInfo.setRoleType(3);	
		}else {
			mUserInfo.setRoleType(2);	
		}
		mUserInfo.setRemark(remark);
		mUserInfoDao.updateRoleType(mUserInfo);
		return 1;
	}

	@Override
	public void freezeUser(String userId,Integer isFrozen,String remark) {
		MUserInfo mUserInfo = new MUserInfo();
		mUserInfo.setUserId(userId);
		if(isFrozen==3) {
			mUserInfo.setStatus(2);
			MUserInfo user=mUserInfoDao.selectOne(userId);
			redisService.delete(user.getToken());
		}else {
			mUserInfo.setStatus(1);	
		}
		mUserInfo.setRemark(remark);
		mUserInfo.setUpdateTime(new Date().getTime());
		mUserInfoDao.updateBaseInfo(mUserInfo);
		mUserGyroDao.delete(userId);
	}

	@Override
	public Map<String, Object> queryListNew(MUserInfo mUserInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		mUserInfo.setPageIndex(mUserInfo.getPageSize() * (mUserInfo.getPageNum() - 1));
		List<MUserInfo> mUserList = mUserInfoDao.selectNew(mUserInfo);
		List<String> userIds = new ArrayList<>();
		List<String> refferrers = new ArrayList<>();
		
		for (int i = 0; i < mUserList.size(); i++) {
			userIds.add(mUserList.get(i).getUserId());
			if(mUserList.get(i).getReferrer() != null && !"".equals(mUserList.get(i).getReferrer())){
				refferrers.add(mUserList.get(i).getReferrer());
			}
		}
		List<Map<String, Object>> reffers = new ArrayList<>();
		if(refferrers.size() > 0){
			reffers = mUserInfoDao.selectReffer(refferrers);
		}
		if(userIds.size()>0) {
			//List<Map<String, Object>> vipAmounts = mUserInfoDao.selectVipAmount(userIds);
			List<Map<String, Object>> vipCounts = mUserInfoDao.selectVipCount(userIds);
			List<Map<String, Object>> txAmounts = mUserInfoDao.selectTxAmount(userIds);
			List<Map<String, Object>> txCounts = mUserInfoDao.selectTxCount(userIds);
			List<Map<String, Object>> djCounts = mUserInfoDao.selectDjCount(userIds);
			/*List<Map<String, Object>> yxCounts = mUserInfoDao.selectGameCount(userIds);
			List<Map<String, Object>> zcYxCounts = mUserInfoDao.selectZcGameCount(userIds);*/
			
			for (int i = 0; i < mUserList.size(); i++) {
				for (Map<String, Object> reffer : reffers) {
					if(reffer.get("userId").toString().equals(mUserList.get(i).getReferrer())){
						mUserList.get(i).setReferrer(reffer.get("accountId").toString());
						break;
					}
				}
				/*for (Map<String, Object> vipAmount : vipAmounts) {
					if(vipAmount.get("userId").toString().equals(mUserList.get(i).getReferrer())){
						mUserList.get(i).setVipAmount((double)vipAmount.get("vipAmount"));
						vipAmounts.remove(vipAmount);
						break;
					}
				}*/
				for (Map<String, Object> vipCount : vipCounts) {
					if(vipCount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = vipCount.get("vipCount")+"";
						mUserList.get(i).setVipCount(new Integer(str));
						vipCounts.remove(vipCount);
						break;
					}
				}
				for (Map<String, Object> txAmount : txAmounts) {
					if(txAmount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = txAmount.get("txAmount")+"";
						mUserList.get(i).setTxAmount(Long.valueOf(str));
						txAmounts.remove(txAmount);
						break;
					}
				}
				for (Map<String, Object> txCount : txCounts) {
					if(txCount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = txCount.get("txCount") +"";
						mUserList.get(i).setTxCount(new Integer(str));
						txCounts.remove(txCount);
						break;
					}
				}
				for (Map<String, Object> djCount : djCounts) {
					if(djCount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = djCount.get("djCount") +"";
						mUserList.get(i).setDjCount(new Integer(str));
						djCounts.remove(djCount);
						break;
					}
				}
				/*for (Map<String, Object> yxCount : yxCounts) {
					if(yxCount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = yxCount.get("yxCount")+"";
						mUserList.get(i).setYxCount(Long.valueOf(str));
						txAmounts.remove(yxCount);
						break;
					}
				}
				
				for (Map<String, Object> zcYxCount : zcYxCounts) {
					if(zcYxCount.get("userId").toString().equals(mUserList.get(i).getUserId())){
						String str = zcYxCount.get("zcYxCount")+"";
						mUserList.get(i).setZcYxCount(Long.valueOf(str));
						txAmounts.remove(zcYxCount);
						break;
					}
				}*/
			}
		}
		
		long count  = mUserInfoDao.selectNewCount(mUserInfo);
		result.put("list", mUserList); 
		result.put("total", count);
		return result;
	}

	@Override
	public Map<String, Object> selectFrozen(String userId) {
		return mUserInfoDao.selectFrozen(userId);
	}
	
	@Override
	public Map<String, Object> extensionStatistic(String userId) {
		Map<String, Object> data=new HashMap<String, Object>();
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		long teamBenefit=Long.parseLong(lCoinChangeDao.selectGroupContribution(mUserInfo.getUserId()));//团队总收益 4,5,8,15,17,18,22,24,25
		long directProfit=lCoinChangeDao.selectTeamSum(15, userId);//直属总收益 15
		int directPeopleNum=mUserInfoDao.teamPeopleNum(userId, 2);//直属人数
		long ordinaryProfit=lCoinChangeDao.selectTeamSum(5, userId);//普通用户总收益 4,5,8
		int ordinaryPeopleNum=mUserInfoDao.teamPeopleNum(userId, 1);//普通用户人数
		long additionalProfit=lCoinChangeDao.selectTeamSumNew(userId);//额外收益 18,22
	    int indirectPeopleNum=mUserInfoDao.teamIndirectNum(userId);//间接推荐人数
	    long selectMyReward=lCoinChangeDao.selectMyReward(userId);//间接推荐人数 4,5,8
	    List<MUserApprentice> drPeopleSize =mUserApprenticeDao.selectListByUser(userId, 2);
	    int drPeopleNum = drPeopleSize.size();
	    long drReward = 0l;
	    if(drPeopleNum > 0){
	    	drReward = lCoinChangeDao.selectDRReward(userId); // 24,25，28
	    }
	    long indirectProfit=teamBenefit-directProfit-ordinaryProfit-additionalProfit - drReward;//间接收益
	    long highVipAmount = lCoinChangeDao.selecthHighVipAmount(userId);
	    data.put("highVipAmount", highVipAmount);
	    if(highVipAmount == 0L){
	    	data.put("highVipCount", 0);
	    }else{
	    	data.put("highVipCount",lCoinChangeDao.selecthHighVipCount(userId));
	    }
	    data.put("teamBenefit", teamBenefit);
	    data.put("directPeopleNum", directPeopleNum);
	    data.put("directProfit", directProfit);
	    data.put("ordinaryPeopleNum", ordinaryPeopleNum);
	    data.put("ordinaryProfit", ordinaryProfit);
	    data.put("indirectPeopleNum", indirectPeopleNum);
	    data.put("indirectProfit", indirectProfit);
	    data.put("additionalProfit", additionalProfit);
	    data.put("reward", selectMyReward); 
	    data.put("isiIndirectProfit", mUserInfo.getRoleType()>1?1:2);
	    data.put("drPeopleNum", drPeopleNum);
	    data.put("drReward", drReward);
		return data;
	}
	
	@Override
	public void changeRoleType(String outTradeNo,long now){
		PPayLog pPayLog = pPayLogDao.selectOne(outTradeNo);
		if(StringUtil.isNullOrEmpty(pPayLog)) {
			return;
		}
		/*if(pPayLog.getState().intValue()==2) {
			return;
		}
		pPayLog.setPayTime(new Date().getTime());
		pPayLog.setState(2);
		pPayLogDao.update(pPayLog);*/
		
		String res=new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				if(pPayLog.getState().intValue()==2) {
					return "2";
				}
				pPayLog.setPayTime(new Date().getTime());
				pPayLog.setState(2);
				pPayLogDao.update(pPayLog);
				return "1";
			}
		}.execute(pPayLog.getUserId(),"redis_lock_td_callback");
		if("2".equals(res)) {
			return;
		}
		
		
		MUserInfo mUserInfo = mUserInfoDao.selectOne(pPayLog.getUserId());
		String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		if(StringUtils.isBlank(channelCode)){
			channelCode = "baozhu";
		}
		MFissionScheme mFissionScheme = mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
		int effectiveDay = mFissionScheme.getEffectiveDay();
		
		mUserInfo.setSurplusTime(mUserInfo.getSurplusTime() + effectiveDay);
		mUserInfo.setUpdateTime(now);
		if(mUserInfo.getReferrer() != null){ // 只有有上级，才能给上级分佣
			Map<String, Object> map = new HashMap<>();
			map.put("userId", mUserInfo.getReferrer());
			// 只有购买团队长，才给其他的分佣
			if(mUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_1 && mUserInfo.getXqEndTime() < now){ // 说明是购买的
				map.put("changeType", 1);
			}else{
				map.put("changeType", 2);
			}
			// 发mq处理
			jmsProducer.sendMessage(Type.SEND_COIN_TO_TEAM_LEADER, map);
		}
		mUserInfo.setXqEndTime(0l);
		mUserInfo.setRoleType(ConstantUtil.ROLE_TYPE_2);
		mUserInfoDao.updateBaseInfo(mUserInfo);
	}

	@Override
	public List<MUserInfo> selectExcl(MUserInfo mUserInfo) {
		return mUserInfoDao.selectExcl(mUserInfo);
	}

	@Override
	public Integer bindingWxNumber(MUserInfo mUserInfo, Integer type, String appId, String appSecret) {
		MUserInfo user=mUserInfoDao.selectOne(mUserInfo.getUserId());
		Map<String,Object> date=new HashMap<>();
		int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
		if(!StringUtil.isNullOrEmpty(user.getOpenId())) {
			return 5;//已绑定微信账号
		}else {
			Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
			String openId = String.valueOf(data.get("openid"));
	        if(openId==null || openId.trim().equals("")){
	            return 4;//openId获取失败
	        }
	        int wxCount=mUserInfoDao.selectOpenId(openId);
	        if(wxCount>=count) {
				return 6;
			}
	        Map<String,Object> wxUserInfo=WxSupport.getUserInfo(data.get("access_token").toString(),data.get("openid").toString());
	        mUserInfo.setOpenId(openId);
			mUserInfo.setProfile(wxUserInfo.get("headimgurl").toString());
			mUserInfo.setAliasName(wxUserInfo.get("nickname").toString());
			mUserInfoDao.update(mUserInfo);
			date.put("userId", mUserInfo.getUserId());
			date.put("task", 2);
			jmsProducer.userTask(Type.USER_TASK, date);
		}
		return 1;
	}

	@Override
	public Integer modifyBindingWxNumber(MUserInfo mUserInfo, Integer type, String appId, String appSecret) {
		MUserInfo user=mUserInfoDao.selectOne(mUserInfo.getUserId());
		int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
		int oldWx=mUserInfoDao.selectUserWx(mUserInfo.getUserId());
		if(oldWx>count) {
			return 6;
		}
		Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
		if(StringUtil.isNullOrEmpty(data.get("openid"))) {
			 return 4;//openId获取失败
		}
		String openId = String.valueOf(data.get("openid"));
		if(!openId.equals(user.getOpenId())) {
			int wxCount=mUserInfoDao.selectOpenId(openId);
			if(wxCount>=count) {
				return 6;
			}	
		}
		if(!StringUtil.isNullOrEmpty(data.get("access_token"))) {
        	Map<String,Object> wxUserInfo=WxSupport.getUserInfo(data.get("access_token").toString(),data.get("openid").toString());
        	if(!StringUtil.isNullOrEmpty(wxUserInfo)) {
				if(!StringUtil.isNullOrEmpty(wxUserInfo.get("headimgurl").toString())) {
					mUserInfo.setProfile(wxUserInfo.get("headimgurl").toString());
				}
				if(!StringUtil.isNullOrEmpty(wxUserInfo.get("nickname").toString())) {
					mUserInfo.setAliasName(wxUserInfo.get("nickname").toString());
				}
			}
	    }
	    mUserInfo.setOpenId(openId);
		mUserInfoDao.update(mUserInfo);
		return 1;
	}

	@Override
	public Map<String, Object> selectDrList(DarenUserVo darenUserVo) {
		String date = DateUtil.getCurrentDate5();
		Map<String, Object> result = new HashMap<String, Object>();
		darenUserVo.setPageIndex(darenUserVo.getPageSize() * (darenUserVo.getPageNum() - 1));
		List<DarenUserVo> mUserList = mUserInfoDao.selectDrList(darenUserVo);
		for (int i = 0; i < mUserList.size(); i++) {
			DarenUserVo DarenUserVo = mUserList.get(i);
			LUserActivity lUserActivity = lUserActivityDao.selectByDate(date, DarenUserVo.getUserId());
			if(lUserActivity == null){
				DarenUserVo.setQualityScore(100);
				DarenUserVo.setActivityScore(1d);
			}else{
				DarenUserVo.setQualityScore(lUserActivity.getQualityScore());
				DarenUserVo.setActivityScore(lUserActivity.getActivityScore());
			}
			// 获取收益
			Map<String, Object> map = lDarenRewardDao.selectByUserTotal(DarenUserVo.getUserId());
			if(map == null){
				DarenUserVo.setFirstReward(0l);
				DarenUserVo.setSecondReward(0l);
				DarenUserVo.setReward(0l);
				DarenUserVo.setTaskCount(0);
				DarenUserVo.setApprenticeCount(0);
				DarenUserVo.setApprenticeReward(0l);
			}else{
				long firstReward = map.get("first_reward") == null?0l:new Long(map.get("first_reward").toString());
				long secondReward = map.get("second_reward") == null?0l:new Long(map.get("second_reward").toString());
				int taskCount = map.get("task_count") == null?0:new Integer(map.get("task_count").toString());
				int apprenticeCount = map.get("apprentice_count") == null?0:new Integer(map.get("apprentice_count").toString());
				long reward = firstReward + secondReward;
				long apprenticeReward = lDarenRewardDetailDao.selectSumApprenticeReward(DarenUserVo.getUserId());
				DarenUserVo.setFirstReward(firstReward);
				DarenUserVo.setSecondReward(secondReward);
				DarenUserVo.setReward(reward);
				DarenUserVo.setTaskCount(taskCount);
				DarenUserVo.setApprenticeCount(apprenticeCount);
				DarenUserVo.setApprenticeReward(apprenticeReward);
			}
		}
		long count  = mUserInfoDao.selectDrCount(darenUserVo);
		result.put("list", mUserList); 
		result.put("total", count);
		return result;
	}

	@Override
	public int setDR(Integer accountId) {
		MUserInfo user=mUserInfoDao.selectByAccountId(accountId);
		if(StringUtil.isNullOrEmpty(user)){
			return 0;//用户不存在
		}
		if(user.getRoleType().intValue()==4) {
			return 3;//用户不是小猪猪无法，不能设置为达人
		}
		if(user.getRoleType().intValue()!=1) {
			return 2;//用户不是小猪猪无法，不能设置为达人
		}
		int i=mUserInfoDao.updateUserDR(accountId,4,new Date().getTime());
		return i;
	}
	@Override
	public void cancleDR(Integer accountId) {
		
		mUserInfoDao.updateUserDR(accountId,1,new Date().getTime());
	}

	@Override
	public List<DarenUserVo> drExcel(DarenUserVo darenUserVo) {
		String date = DateUtil.getCurrentDate5();
		darenUserVo.setPageIndex(darenUserVo.getPageSize() * (darenUserVo.getPageNum() - 1));
		List<DarenUserVo> mUserList = mUserInfoDao.selectDrList(darenUserVo);
		for (int i = 0; i < mUserList.size(); i++) {
			DarenUserVo DarenUserVo = mUserList.get(i);
			LUserActivity lUserActivity = lUserActivityDao.selectByDate(date, DarenUserVo.getUserId());
			if(lUserActivity == null){
				DarenUserVo.setQualityScore(100);
				DarenUserVo.setActivityScore(1d);
			}else{
				DarenUserVo.setQualityScore(lUserActivity.getQualityScore());
				DarenUserVo.setActivityScore(lUserActivity.getActivityScore());
			}
			
			// 获取收益
			Map<String, Object> map = lDarenRewardDao.selectByUserTotal(DarenUserVo.getUserId());
			if(map == null){
				DarenUserVo.setFirstReward(0l);
				DarenUserVo.setSecondReward(0l);
				DarenUserVo.setReward(0l);
				DarenUserVo.setTaskCount(0);
				DarenUserVo.setApprenticeCount(0);
				DarenUserVo.setApprenticeReward(0l);
			}else{
				long firstReward = map.get("first_reward") == null?0l:new Long(map.get("first_reward").toString());
				long secondReward = map.get("second_reward") == null?0l:new Long(map.get("second_reward").toString());
				int taskCount = map.get("task_count") == null?0:new Integer(map.get("task_count").toString());
				int apprenticeCount = map.get("apprentice_count") == null?0:new Integer(map.get("apprentice_count").toString());
				long reward = firstReward + secondReward;
				long apprenticeReward = lDarenRewardDetailDao.selectSumApprenticeReward(DarenUserVo.getUserId());
				DarenUserVo.setFirstReward(firstReward);
				DarenUserVo.setSecondReward(secondReward);
				DarenUserVo.setReward(reward);
				DarenUserVo.setTaskCount(taskCount);
				DarenUserVo.setApprenticeCount(apprenticeCount);
				DarenUserVo.setApprenticeReward(apprenticeReward);
			}
		}
		return mUserList;
	}
	
	@Override
	public Result regChannelH5(MUserInfo mUserInfo) {
		Result result = new Result();
		Long now = new Date().getTime();
		// 判断手机号是否存在
		Map<String , Object> param = new HashMap<>();
		param.put("mobile", mUserInfo.getMobile());
		MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
		if(oldUserInfo != null){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		if(StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())) {
			mUserInfo.setChannelCode("baozhu");
		}else {
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(mUserInfo.getChannelCode());
			if(StringUtil.isNullOrEmpty(channelInfo)) {
				mUserInfo.setChannelCode("baozhu");
			}
		}
		
		PDictionary regCoin = pDictionaryService.queryByName("reg_coin");
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(regCoin == null?0l:new Long(regCoin.getDicValue())); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
				if(oldUserInfo != null){
					return "1";
				}
				mUserInfoDao.insert(mUserInfo);
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		if("1".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		// 发放卡券
		jmsProducer.sendMessage(Type.REG_SEND_PASSBOOK, mUserInfo.getUserId());
		if(mUserInfo.getCoin().longValue() > 0){
			// 插入用户金币变更记录
			LCoinChange lCoinChange = new LCoinChange(mUserInfo.getUserId(), mUserInfo.getCoin(), ConstantUtil.FLOWTYPE_IN,
					ConstantUtil.COIN_CHANGED_TYPE_9, now, 1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange);
		}
		//新手注册任务
	    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
	    if(StringUtil.isNullOrEmpty(lUserTask)) {
	    	LUserTask userTask=new LUserTask();
			userTask.setUserId(mUserInfo.getUserId());
			userTask.setTaskId(11);
			userTask.setCreateTime(new Date().getTime());
			userTask.setIsReceive(2);
			lUserTaskDao.insert(userTask);
	    }
		result.setSuccess(true);
		result.setMessage(RespCodeState.USER_REGISTERED_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.USER_REGISTERED_SUCCESS.getStatusCode());
		return result;
	}

	@Override
	public MUserInfo selectAccountId(Integer accountId) {
		return mUserInfoDao.selectByAccountId(accountId);
	}

	@Override
	public Map<String, Object> isUser(Integer accountId) {
		Map<String,Object> data=mUserInfoDao.selectAccountId(accountId);
		if(StringUtil.isNullOrEmpty(data)) {
			data=new HashMap<>();
			data.put("res", 2);//用户不存在
		}else {
			data.put("res", 1);	
		}
		return data;
	}

	@Override
	public Map<String, Object> zjdUser(String userId) {
		Map<String,Object> data=new HashMap<>();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		String mobile=user.getMobile();
		data.put("accountId", user.getAccountId());
		data.put("mobile", mobile.subSequence(0, 3)+"****"+mobile.substring(7, 11));
		return data;
	}

	@Override
	public Map<String,Object> mailList(int addressBook, int callRecord) {
		Map<String,Object> data=new HashMap<>();
		data.put("res", 1);
		int address=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ADDRESS_BOOK_NUMBER).getDicValue());
		int call=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CALL_RECORD_NUMBER).getDicValue());
		if(addressBook<=address && callRecord<=call) {
			data.put("res", 2);
			return data;//拒绝登录
		}
		return data;
	}

	@Override
	public Result wechatLogin(MUserInfo mUserInfo,String appId,String appSecret) {
		Result result = new Result();
		Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
		if(StringUtil.isNullOrEmpty(data.get("openid"))) {
			  //openId获取失败
			result.setMessage(RespCodeState.AUTH_ERROR.getMessage());
			result.setStatusCode(RespCodeState.AUTH_ERROR.getStatusCode());
			return result;
		}
		String openId = data.get("openid").toString();
		MUserInfo old = mUserInfoDao.selectInfoOpenId(openId);
		if(old != null){ // 说明已经绑定了微信
			if(old.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
			}else{
				String channelCode = old.getChannelCode() == null?old.getParentChannelCode():old.getChannelCode();
				MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
				if(channelInfo.getWebType().intValue()==3) {
					result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
					result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
					return result;
				}	
			}
			String userId = old.getUserId();
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(old.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+old.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				old.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", old.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			old.setRegistrationId(mUserInfo.getRegistrationId());
			old.setEquipmentType(mUserInfo.getEquipmentType());
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(old.getImei())){
				//新手注册任务
				LUserTask lUserTask=lUserTaskDao.selectOne(old.getUserId(), 11);
				if(StringUtil.isNullOrEmpty(lUserTask)) {
					LUserTask userTask=new LUserTask();
					userTask.setUserId(old.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
				}
			}
			
			old.setImei(mUserInfo.getImei());
			mUserInfoDao.updateBaseInfo(old);
			
			old.setUserId(null);
			old.setPassword(null);
			old.setPayPassword(null);
			result.setToken(old.getToken());
			result.setData(old);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}else{
			old = new MUserInfo();
	        if(!StringUtil.isNullOrEmpty(data.get("access_token"))) {
	        	Map<String,Object> wxUserInfo=WxSupport.getUserInfo(data.get("access_token").toString(),data.get("openid").toString());
				if(!StringUtil.isNullOrEmpty(wxUserInfo)) {
					if(!StringUtil.isNullOrEmpty(wxUserInfo.get("headimgurl").toString())) {
						old.setProfile(wxUserInfo.get("headimgurl").toString());
					}
					if(!StringUtil.isNullOrEmpty(wxUserInfo.get("nickname").toString())) {
						old.setAliasName(wxUserInfo.get("nickname").toString());
					}
				}
	        }
	        old.setOpenId(openId);
	        // 需要先去绑定手机号
			result.setMessage(RespCodeState.NEED_BIND_MOBILE.getMessage());
			result.setStatusCode(RespCodeState.NEED_BIND_MOBILE.getStatusCode());
			result.setData(old);
			result.setToken("");
		}
		return result;
	}

	@Override
	public Result bindMobile(MUserInfo mUserInfo) {
		Result result = new Result();
		long now = new Date().getTime();
		MUserInfo referrer = null;
		
		MUserInfo oldUserInfo = mUserInfoDao.selectByMobile(mUserInfo.getMobile());
		if(oldUserInfo != null){
			String channelCode =StringUtil.isNullOrEmpty(oldUserInfo.getChannelCode())?oldUserInfo.getParentChannelCode():oldUserInfo.getChannelCode();
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			if(channelInfo.getWebType().intValue()==3) {
				result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
				result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
				return result;
			}
			if(StringUtils.isNotBlank(oldUserInfo.getOpenId())){
				if(!oldUserInfo.getOpenId().equals(oldUserInfo.getOpenId())){
					result.setSuccess(true);
					result.setMessage(RespCodeState.XW_BIND_OTHER_MOBILE.getMessage());
					result.setStatusCode(RespCodeState.XW_BIND_OTHER_MOBILE.getStatusCode());
					return result;
				}
			}
			String userId = oldUserInfo.getUserId();
			String v = tokenPrefix+oldUserInfo.getUserId()+"_"+mUserInfo.getImei();
			String token = MD5Util.getMd5(v);
			oldUserInfo.setToken(token);
			oldUserInfo.setOpenId(mUserInfo.getOpenId());
			oldUserInfo.setProfile(mUserInfo.getProfile());
			oldUserInfo.setAliasName(mUserInfo.getAliasName());
			oldUserInfo.setImei(mUserInfo.getImei());
			oldUserInfo.setRegistrationId(mUserInfo.getRegistrationId());
			oldUserInfo.setEquipmentType(mUserInfo.getEquipmentType());
			int res=0;
			try {
				res=mUserInfoDao.updateBaseInfo(oldUserInfo);
			}catch (Exception e) {
				oldUserInfo.setAliasName("小猪猪");
				res=mUserInfoDao.updateBaseInfo(oldUserInfo);
			}
			if(res==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
				return result;
			}
			
			Map<String,Object> redisMap=new HashMap<>();
			redisMap.put("userId", oldUserInfo.getUserId());
			redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
			
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(mUserInfo.getImei())){
				//新手注册任务
				LUserTask lUserTask=lUserTaskDao.selectOne(oldUserInfo.getUserId(), 11);
				if(StringUtil.isNullOrEmpty(lUserTask)) {
					LUserTask userTask=new LUserTask();
					userTask.setUserId(oldUserInfo.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
				}
			}
			mUserInfo.setPassword(null);
			mUserInfo.setUserId(null);
			result.setToken(token);
			result.setData(oldUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
			return result;
			/*result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;*/
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		PDictionary regCoin = pDictionaryService.queryByName("reg_coin");
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(regCoin == null?0l:new Long(regCoin.getDicValue())); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setRegImei(mUserInfo.getImei());
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		if(StringUtils.isEmpty(mUserInfo.getProfile())){
			mUserInfo.setProfile("https://image.bzlyplay.com/default_img.png");
		}
		// 生成token
		String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
		String token = MD5Util.getMd5(v);
		mUserInfo.setToken(token);
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectByMobile(mUserInfo.getMobile());
				if(oldUserInfo != null){
					return "1";
				}
				int res=0;
				try {
					res=mUserInfoDao.insert(mUserInfo);	
				}catch (Exception e) {
					mUserInfo.setAliasName("小猪猪");
					res=mUserInfoDao.insert(mUserInfo);
				}
				if(res==0) {
					return "3";	
				}
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		if("3".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		if("1".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		Map<String,Object> redisMap=new HashMap<>();
		redisMap.put("userId", mUserInfo.getUserId());
		redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
		
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			map.put("regType", "app");
			jmsProducer.sendMessage(Type.ADD_APPRENTICE, map);
		}
		// 发放卡券
		jmsProducer.sendMessage(Type.REG_SEND_PASSBOOK, mUserInfo.getUserId());
		
		if(mUserInfo.getCoin().longValue() > 0){
			// 插入用户金币变更记录
			LCoinChange lCoinChange = new LCoinChange(mUserInfo.getUserId(), mUserInfo.getCoin(), ConstantUtil.FLOWTYPE_IN,
					ConstantUtil.COIN_CHANGED_TYPE_9, now, 1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange);
		}
		//新手注册任务
	    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
	    if(StringUtil.isNullOrEmpty(lUserTask)) {
	    	LUserTask userTask=new LUserTask();
			userTask.setUserId(mUserInfo.getUserId());
			userTask.setTaskId(11);
			userTask.setCreateTime(new Date().getTime());
			userTask.setIsReceive(2);
			lUserTaskDao.insert(userTask);
	    }
		mUserInfo.setPassword(null);
		mUserInfo.setUserId(null);
		result.setToken(token);
		result.setData(mUserInfo);
		result.setSuccess(true);
		result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		// 发送快手回调
		/*String imei = mUserInfo.getImei();
		if(StringUtils.isNotBlank(imei)){
			String imeiMD5 = MD5Util.getMd5(imei);
			jmsProducer.sendMessage(Type.SEND_KS_CALLBACK, imeiMD5);
		}*/
		return result;
	}

	@Override
	public Result addWishMUserInfo(MUserInfo mUserInfo) throws Exception {
		Result result = new Result();
		
		String channelCode =StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
		if(channelInfo.getWebType().intValue()!=3) {
			result.setMessage(RespCodeState.QR_CODE_ERROR.getMessage());
			result.setStatusCode(RespCodeState.QR_CODE_ERROR.getStatusCode());
			return result;
		}
		
		Long now = new Date().getTime();
		MUserInfo referrer = null;
		// 判断手机号是否存在
		Map<String , Object> param = new HashMap<>();
		param.put("mobile", mUserInfo.getMobile());
		MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
		if(oldUserInfo != null){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(0l); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setRegImei(mUserInfo.getImei());
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		if(StringUtils.isEmpty(mUserInfo.getProfile())){
			mUserInfo.setProfile("https://image.bzlyplay.com/default_img.png");
		}
		// 生成token
		String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
		String token = MD5Util.getMd5(v);
		mUserInfo.setToken(token);
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
				if(oldUserInfo != null){
					return "1";
				}
				mUserInfoDao.insert(mUserInfo);
				
				int wishNum=Integer.parseInt(pDictionaryDao.selectByName("reg_wish_num").getDicValue());
				long time=new Date().getTime();
				UserWishValue userWishValue=new UserWishValue();
				userWishValue.setUserId(mUserInfo.getUserId());
			    userWishValue.setWishValue(wishNum);
			    userWishValue.setUpdateTime(time);
			    userWishValueDao.insert(userWishValue);
			    WishGain wishGain=new WishGain();
			    wishGain.setUserId(mUserInfo.getUserId());
			    wishGain.setFlowType(1);
			    wishGain.setMode(5);
			    wishGain.setNumber(wishNum);
			    wishGain.setCreateTime(time);
			    wishGain.setSign(mUserInfo.getUserId()+time);
			    wishGainDao.insert(wishGain);
				
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		if("1".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		Map<String,Object> redisMap=new HashMap<>();
		redisMap.put("userId", mUserInfo.getUserId());
		redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
		
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			jmsProducer.wishReceiveQueue(Type.WISH_ADD_APPRENTICE, map);
		}
		
		mUserInfo.setPassword(null);
		mUserInfo.setUserId(null);
		result.setToken(token);
		result.setData(mUserInfo);
		result.setSuccess(true);
		result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		// 发送趣头条上报
		String imei = mUserInfo.getImei();
		if(StringUtils.isNotBlank(imei)){
			String imeiMD5 = MD5Util.getMd5(imei);
			jmsProducer.sendMessage(Type.SEND_QTT_CALLBACK, imeiMD5);
		}
		return result;
	}

	@Override
	public Result regWishH5(MUserInfo mUserInfo) {
		Result result = new Result();
		
		String channelCodeReg =StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCodeReg);
		if(channelInfo.getWebType().intValue()!=3) {
			result.setMessage(RespCodeState.QR_CODE_ERROR.getMessage());
			result.setStatusCode(RespCodeState.QR_CODE_ERROR.getStatusCode());
			return result;
		}
		
		Long now = new Date().getTime();
		MUserInfo referrer = null;
		// 判断手机号是否存在
		Map<String , Object> param = new HashMap<>();
		param.put("mobile", mUserInfo.getMobile());
		MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
		if(oldUserInfo != null){
			String channelCode=StringUtil.isNullOrEmpty(oldUserInfo.getChannelCode())?oldUserInfo.getParentChannelCode():oldUserInfo.getChannelCode();
			if(StringUtil.isNullOrEmpty(channelCode)) {
				channelCode="wishbz";
			}
			result.setData(channelCode);
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(0l); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectUserBaseInfo(param);
				if(oldUserInfo != null){
					return "1";
				}
				mUserInfoDao.insert(mUserInfo);
				
				int wishNum=Integer.parseInt(pDictionaryDao.selectByName("reg_wish_num").getDicValue());
				long time=new Date().getTime();
				UserWishValue userWishValue=new UserWishValue();
				userWishValue.setUserId(mUserInfo.getUserId());
			    userWishValue.setWishValue(wishNum);
			    userWishValue.setUpdateTime(time);
			    userWishValueDao.insert(userWishValue);
			    WishGain wishGain=new WishGain();
			    wishGain.setUserId(mUserInfo.getUserId());
			    wishGain.setFlowType(1);
			    wishGain.setMode(5);
			    wishGain.setNumber(wishNum);
			    wishGain.setCreateTime(time);
			    wishGain.setSign(mUserInfo.getUserId()+time);
			    wishGainDao.insert(wishGain);
				
				return "2";
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		String channelCode=StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		if(StringUtil.isNullOrEmpty(channelCode)) {
			channelCode="wishbz";
		}
		if("1".equals(regStr)){
			result.setData(channelCode);
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			jmsProducer.wishReceiveQueue(Type.WISH_ADD_APPRENTICE, map);
		}
		
	    result.setData(channelCode);
		result.setSuccess(true);
		result.setMessage(RespCodeState.USER_REGISTERED_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.USER_REGISTERED_SUCCESS.getStatusCode());
		return result;
	}

	@Override
	public Result bindWishMobile(MUserInfo mUserInfo) {
		Result result = new Result();
		long now = new Date().getTime();
		MUserInfo referrer = null;
		
		MUserInfo oldUserInfo = mUserInfoDao.selectByMobile(mUserInfo.getMobile());
		if(oldUserInfo != null){
			String channelCode =StringUtil.isNullOrEmpty(oldUserInfo.getChannelCode())?oldUserInfo.getParentChannelCode():oldUserInfo.getChannelCode();
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			if(channelInfo.getWebType().intValue()!=3) {
				result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
				result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
				return result;
			}
			if(StringUtils.isNotBlank(oldUserInfo.getOpenId())){
				if(!oldUserInfo.getOpenId().equals(oldUserInfo.getOpenId())){
					result.setSuccess(true);
					result.setMessage(RespCodeState.XW_BIND_OTHER_MOBILE.getMessage());
					result.setStatusCode(RespCodeState.XW_BIND_OTHER_MOBILE.getStatusCode());
					return result;
				}
			}
			String v = tokenPrefix+oldUserInfo.getUserId()+"_"+mUserInfo.getImei();
			String token = MD5Util.getMd5(v);
			oldUserInfo.setToken(token);
			oldUserInfo.setOpenId(mUserInfo.getOpenId());
			oldUserInfo.setProfile(mUserInfo.getProfile());
			oldUserInfo.setAliasName(mUserInfo.getAliasName());
			oldUserInfo.setImei(mUserInfo.getImei());
			oldUserInfo.setRegistrationId(mUserInfo.getRegistrationId());
			oldUserInfo.setEquipmentType(mUserInfo.getEquipmentType());
			int res=0;
			try {
				res=mUserInfoDao.updateBaseInfo(oldUserInfo);	
			}catch (Exception e) {
				oldUserInfo.setAliasName("小猪猪");
				res=mUserInfoDao.updateBaseInfo(oldUserInfo);	
			}
			if(res==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());	
				return result;
			}
			
			Map<String,Object> redisMap=new HashMap<>();
			redisMap.put("userId", oldUserInfo.getUserId());
			redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	
			mUserInfo.setPassword(null);
			mUserInfo.setUserId(null);
			result.setToken(token);
			result.setData(oldUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
			return result;
		}
		// 判断邀请人是否存在
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){ // 有邀请人
			mUserInfo.setRecommendedTime(now); // 邀请时间
			referrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
			if(referrer == null){
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.REFERRER_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.REFERRER_NOT_EXIST.getMessage());	
				return result;
			}
		}
		mUserInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		mUserInfo.setLevel(pLevelService.queryLowerLevel().getLevel()); // 设置注册时等等级为最低等级
		mUserInfo.setLevelValue(0l); // 经验值为0
		mUserInfo.setCreateTime(now);
		mUserInfo.setUpdateTime(now);
		mUserInfo.setCoin(0l); // 金币
		mUserInfo.setApprentice(0); // 学徒数量
		mUserInfo.setJadeCabbage(BigDecimal.ZERO); // 代币
		mUserInfo.setReward(BigDecimal.ZERO); // 获取的总奖励金额
		mUserInfo.setBalance(BigDecimal.ZERO); // 余额
		mUserInfo.setPigCoin(0l);
		mUserInfo.setRegImei(mUserInfo.getImei());
		mUserInfo.setQrCode(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10));
		if(StringUtils.isEmpty(mUserInfo.getProfile())){
			mUserInfo.setProfile("https://image.bzlyplay.com/default_img.png");
		}
		// 生成token
		String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
		String token = MD5Util.getMd5(v);
		mUserInfo.setToken(token);
		String regStr = new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserInfo oldUserInfo = mUserInfoDao.selectByMobile(mUserInfo.getMobile());
				if(oldUserInfo != null){
					return "1";
				}
				int res=0;
				try {
					res=mUserInfoDao.insert(mUserInfo);	
				}catch (Exception e) {
					mUserInfo.setAliasName("小猪猪");
					res=mUserInfoDao.insert(mUserInfo);
				}
				if(res==0) {
					return "3";	
				}
				int wishNum=Integer.parseInt(pDictionaryDao.selectByName("reg_wish_num").getDicValue());
				long time=new Date().getTime();
				UserWishValue userWishValue=new UserWishValue();
				userWishValue.setUserId(mUserInfo.getUserId());
			    userWishValue.setWishValue(wishNum);
			    userWishValue.setUpdateTime(time);
			    userWishValueDao.insert(userWishValue);
			    WishGain wishGain=new WishGain();
			    wishGain.setUserId(mUserInfo.getUserId());
			    wishGain.setFlowType(1);
			    wishGain.setMode(5);
			    wishGain.setNumber(wishNum);
			    wishGain.setCreateTime(time);
			    wishGain.setSign(mUserInfo.getUserId()+time);
			    wishGainDao.insert(wishGain);
				return "2";	
			}
		}.execute(mUserInfo.getMobile(),"redis_user_reg");
		if("3".equals(regStr)) {
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());	
			return result;
		}
		if("1".equals(regStr)){
			result.setSuccess(false);
			result.setStatusCode(RespCodeState.MOBILE_ALREADY_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_ALREADY_EXIST.getMessage());	
			return result;
		}
		Map<String,Object> redisMap=new HashMap<>();
		redisMap.put("userId", mUserInfo.getUserId());
		redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
		
		if(!StringUtils.isEmpty(mUserInfo.getReferrer())){
			// 添加师徒关系
			Map<String, String> map = new HashMap<String, String>();
			map.put("referrerId", mUserInfo.getReferrer());
			map.put("userId", mUserInfo.getUserId());
			jmsProducer.wishReceiveQueue(Type.WISH_ADD_APPRENTICE, map);
		}
		
		mUserInfo.setPassword(null);
		mUserInfo.setUserId(null);
		result.setToken(token);
		result.setData(mUserInfo);
		result.setSuccess(true);
		result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		// 发送趣头条上报
		String imei = mUserInfo.getImei();
		if(StringUtils.isNotBlank(imei)){
			String imeiMD5 = MD5Util.getMd5(imei);
			jmsProducer.sendMessage(Type.SEND_QTT_CALLBACK, imeiMD5);
		}
		return result;
	}

	@Override
	public Result loginWishByPassword(Map<String, Object> paramMap) {
		Result result = new Result();
		MUserInfo mUserInfo = mUserInfoDao.selectUserForLogin(paramMap);
		if(mUserInfo == null){
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FAILURE.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FAILURE.getStatusCode());
		}else if(mUserInfo.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
		}else{
			String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			if(channelInfo.getWebType().intValue()!=3) {
				result.setMessage(RespCodeState.NO_LOGIN_BZ_WISH.getMessage());
				result.setStatusCode(RespCodeState.NO_LOGIN_BZ_WISH.getStatusCode());
				return result;
			}
			String userId = mUserInfo.getUserId();
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(mUserInfo.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				mUserInfo.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", mUserInfo.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			mUserInfo.setEquipmentType(new Integer(paramMap.get("equipmentType").toString()));
			mUserInfo.setRegistrationId(paramMap.get("registrationId").toString());
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(mUserInfo.getImei())){
				//新手注册任务
			    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
			    if(StringUtil.isNullOrEmpty(lUserTask)) {
			    	LUserTask userTask=new LUserTask();
					userTask.setUserId(mUserInfo.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
			    }
			}
			mUserInfo.setImei(paramMap.get("imei").toString());
			mUserInfoDao.updateBaseInfo(mUserInfo);
			
			mUserInfo.setUserId(null);
			mUserInfo.setPassword(null);
			mUserInfo.setPayPassword(null);
			result.setToken(mUserInfo.getToken());
			result.setData(mUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}
		return result;
	}

	@Override
	public Result loginWishBySms(Map<String, Object> paramMap) {
		Result result = new Result();
		MUserInfo mUserInfo = mUserInfoDao.selectUserForLogin(paramMap);
		if(mUserInfo == null){
			result.setData(null);
			result.setMessage(RespCodeState.ADMIN_LOGIN_FAILURE.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_FAILURE.getStatusCode());
		}else if(mUserInfo.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
		}else{
			String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
			MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
			if(channelInfo.getWebType().intValue()!=3) {
				result.setMessage(RespCodeState.NO_LOGIN_BZ_WISH.getMessage());
				result.setStatusCode(RespCodeState.NO_LOGIN_BZ_WISH.getStatusCode());
				return result;
			}
			String userId = mUserInfo.getUserId();
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(mUserInfo.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+mUserInfo.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				mUserInfo.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", mUserInfo.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			mUserInfo.setEquipmentType(new Integer(paramMap.get("equipmentType").toString()));
			mUserInfo.setRegistrationId(paramMap.get("registrationId").toString());
			//jmsProducer.darenRecommend(Type.DAREN_RECOMMEND_REWARD,userId);
			if(StringUtil.isNullOrEmpty(mUserInfo.getImei())){
				//新手注册任务
			    LUserTask lUserTask=lUserTaskDao.selectOne(mUserInfo.getUserId(), 11);
			    if(StringUtil.isNullOrEmpty(lUserTask)) {
			    	LUserTask userTask=new LUserTask();
					userTask.setUserId(mUserInfo.getUserId());
					userTask.setTaskId(11);
					userTask.setCreateTime(new Date().getTime());
					userTask.setIsReceive(2);
					lUserTaskDao.insert(userTask);
			    }
			}
			mUserInfo.setImei(paramMap.get("imei").toString());
			mUserInfoDao.updateBaseInfo(mUserInfo);
			
			mUserInfo.setUserId(null);
			mUserInfo.setPassword(null);
			mUserInfo.setPayPassword(null);
			result.setToken(mUserInfo.getToken());
			result.setData(mUserInfo);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}
		return result;
	}

	@Override
	public Result wechatWishLogin(MUserInfo mUserInfo, String appId, String appSecret) {
		Result result = new Result();
		Map<String,Object> data = WxSupport.getAccessToken(mUserInfo.getWxCode(),appId,appSecret);
		if(StringUtil.isNullOrEmpty(data.get("openid"))) {
			  //openId获取失败
			result.setMessage(RespCodeState.AUTH_ERROR.getMessage());
			result.setStatusCode(RespCodeState.AUTH_ERROR.getStatusCode());
			return result;
		}
		String openId = data.get("openid").toString();
		MUserInfo old = mUserInfoDao.selectInfoOpenId(openId);
		if(old != null){ // 说明已经绑定了微信
			if(old.getStatus().intValue() == 2){
				result.setMessage(RespCodeState.USER_LOGIN_FORBIDDEN.getMessage());
				result.setStatusCode(RespCodeState.USER_LOGIN_FORBIDDEN.getStatusCode());
				return result;
			}else{
				String channelCode =StringUtil.isNullOrEmpty(old.getChannelCode())?old.getParentChannelCode():old.getChannelCode();
				MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
				if(channelInfo.getWebType().intValue()!=3) {
					result.setMessage(RespCodeState.NO_LOGIN_BZLY.getMessage());
					result.setStatusCode(RespCodeState.NO_LOGIN_BZLY.getStatusCode());
					return result;
				}	
			}
			// 生成token
			String redisUser=redisService.getString(mUserInfo.getToken());
			if(StringUtil.isNullOrEmpty(old.getToken()) || StringUtil.isNullOrEmpty(redisUser)) {
				String v = tokenPrefix+old.getUserId()+"_"+mUserInfo.getImei();
				String token = MD5Util.getMd5(v);
				old.setToken(token);
				
				Map<String,Object> redisMap=new HashMap<>();
				redisMap.put("userId", old.getUserId());
				redisService.put(token, redisMap,CodisConfig.DEFAULT_EXPIRE);
	    	}
			old.setRegistrationId(mUserInfo.getRegistrationId());
			old.setEquipmentType(mUserInfo.getEquipmentType());
			old.setImei(mUserInfo.getImei());
			mUserInfoDao.updateBaseInfo(old);
			
			old.setUserId(null);
			old.setPassword(null);
			old.setPayPassword(null);
			result.setToken(old.getToken());
			result.setData(old);
			result.setSuccess(true);
			result.setMessage(RespCodeState.ADMIN_LOGIN_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.ADMIN_LOGIN_SUCCESS.getStatusCode());
		}else{
			old = new MUserInfo();
	        if(!StringUtil.isNullOrEmpty(data.get("access_token"))) {
	        	Map<String,Object> wxUserInfo=WxSupport.getUserInfo(data.get("access_token").toString(),data.get("openid").toString());
				if(!StringUtil.isNullOrEmpty(wxUserInfo)) {
					if(!StringUtil.isNullOrEmpty(wxUserInfo.get("headimgurl").toString())) {
						old.setProfile(wxUserInfo.get("headimgurl").toString());
					}
					if(!StringUtil.isNullOrEmpty(wxUserInfo.get("nickname").toString())) {
						old.setAliasName(wxUserInfo.get("nickname").toString());
					}
				}
	        }
	        old.setOpenId(openId);
	        // 需要先去绑定手机号
			result.setMessage(RespCodeState.NEED_BIND_MOBILE.getMessage());
			result.setStatusCode(RespCodeState.NEED_BIND_MOBILE.getStatusCode());
			result.setData(old);
			result.setToken("");
		}
		return result;
	}

	@Override
	public Result resetWishPassword(MUserInfo mUserInfo) {
		Result result=new Result();
		String channelCode =StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		MChannelInfo channelInfo=mChannelInfoDao.selectDownloadUrl(channelCode);
		if(channelInfo.getWebType().intValue()!=3) {
			result.setMessage(RespCodeState.WISHBZ_UPDE_PASSWORD.getMessage());
			result.setStatusCode(RespCodeState.WISHBZ_UPDE_PASSWORD.getStatusCode());
			return result;
		}	
		mUserInfoDao.updatePassword(mUserInfo);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}
}