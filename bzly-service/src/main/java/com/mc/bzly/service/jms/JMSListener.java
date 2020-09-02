package com.mc.bzly.service.jms;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.Base64;
import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.HttpUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.dao.checkin.CCheckinLogDao;
import com.mc.bzly.dao.egg.EGoleEggOrderDao;
import com.mc.bzly.dao.egg.EUserGoldEggDao;
import com.mc.bzly.dao.jms.JmsNewsLogDao;
import com.mc.bzly.dao.lottery.MLotteryGoodsDao;
import com.mc.bzly.dao.lottery.MLotteryOrderDao;
import com.mc.bzly.dao.passbook.MPassbookDao;
import com.mc.bzly.dao.passbook.RSPassbookTaskDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.thirdparty.HippoClkTrackingDao;
import com.mc.bzly.dao.thirdparty.HippoImpTrackingDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.dao.thirdparty.MFissionSchemeDao;
import com.mc.bzly.dao.thirdparty.TPKsCallbackDao;
import com.mc.bzly.dao.thirdparty.TPQttCallbackDao;
import com.mc.bzly.dao.thirdparty.TPYmCallbackDao;
import com.mc.bzly.dao.thirdparty.TpVideoCallbackDao;
import com.mc.bzly.dao.user.LActiveGoldLogDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserBQDao;
import com.mc.bzly.dao.user.LUserCashDao;
import com.mc.bzly.dao.user.LUserExchangeCashDao;
import com.mc.bzly.dao.user.LUserSignDao;
import com.mc.bzly.dao.user.LUserSignGameDao;
import com.mc.bzly.dao.user.LUserSigninDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.dao.wish.UserWishValueDao;
import com.mc.bzly.dao.wish.WishGainDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.checkin.CCheckinLog;
import com.mc.bzly.model.egg.EGoldEggType;
import com.mc.bzly.model.egg.EGoleEggOrder;
import com.mc.bzly.model.egg.EUserGoldEgg;
import com.mc.bzly.model.jms.JmsNewsLog;
import com.mc.bzly.model.jms.JmsWrapper;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.lottery.MLotteryGoods;
import com.mc.bzly.model.lottery.MLotteryOrder;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.passbook.MPassbook;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.model.thirdparty.HippoClkTracking;
import com.mc.bzly.model.thirdparty.HippoImpTracking;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.model.thirdparty.MFissionScheme;
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.model.thirdparty.TPKsCallback;
import com.mc.bzly.model.thirdparty.TPQttCallback;
import com.mc.bzly.model.thirdparty.TPYmCallback;
import com.mc.bzly.model.thirdparty.TpVideoCallback;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.model.thirdparty.YoleCallback;
import com.mc.bzly.model.user.LActiveGoldLog;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.model.user.LUserCash;
import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.LUserSign;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.model.user.LUserSignin;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.model.wish.UserWishValue;
import com.mc.bzly.model.wish.WishGain;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.passbook.MPassbookService;
import com.mc.bzly.service.thirdparty.TPGameService;
import com.mc.bzly.service.user.LActiveGoldLogService;
import com.mc.bzly.service.user.LUserSignService;
import com.bzly.common.utils.StringUtil;

@Service
public class JMSListener {
	private static Logger logger = LoggerFactory.getLogger(JMSListener.class);
	
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private MPassbookDao mPassbookDao;
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	@Autowired
	private LUserSignDao lUserSignDao;
	@Autowired
	private JmsNewsLogDao jmsNewsLogDao;
	@Autowired
	private HippoImpTrackingDao hippoImpTrackingDao;
	@Autowired
	private HippoClkTrackingDao hippoClkTrackingDao;
	@Autowired
	private MChannelConfigUserDao mChannelConfigUserDao;
	@Autowired
	private MChannelConfigDao mChannelConfigDao;
	@Autowired
	private MFissionSchemeDao mFissionSchemeDao;
	@Autowired
	private LUserExchangeCashDao lUserExchangeCashDao;
	@Autowired
	private LUserSigninDao lUserSigninDao;
	@Autowired
	private LUserSignGameDao lUserSignGameDao;
	@Autowired
	private TPGameService tpGameService;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private RSPassbookTaskDao rsPassbookTaskDao;
	@Autowired
	private LUserSignService lUserSignService;
	@Autowired
    private LActiveGoldLogDao lActiveGoldLogDao;
	@Autowired
    private LPigChangeDao lPigChangeDao;
	@Autowired
	private LActiveGoldLogService lActiveGoldLogService;
	@Autowired
	private MPassbookService mPassbookService;
	@Autowired
	private LUserVipDao lUserVipDao;
	@Autowired
	private MLotteryGoodsDao mLotteryGoodsDao;
	@Autowired 
	private MLotteryOrderDao mLotteryOrderDao;
	@Autowired
	private TPYmCallbackDao tPYmCallbackDao;
	@Autowired
	private LUserCashDao lUserCashDao;
	@Autowired
	private TpVideoCallbackDao tpVideoDao;
	@Autowired
	private EGoleEggOrderDao eGoleEggOrderDao;
	@Autowired
    private EUserGoldEggDao eUserGoldEggDao;
	@Autowired
	private CCheckinLogDao cCheckinLogDao;
	@Autowired
	private LUserBQDao lUserBQDao;
	@Autowired
	private TPKsCallbackDao tpKsCallbackDao;
	@Autowired
	private TPQttCallbackDao tpQttCallbackDao;
	@Autowired
	private UserWishValueDao userWishValueDao;
	@Autowired
	private WishGainDao wishGainDao;
	
	@JmsListener(destination = "bz.queue")  
	public void receiveQueue(String text) {  
		logger.info("监听到bz.queue队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			
		    if(Type.USER_FIRST_WITHDRAWALS.equals(jmsWrapper.getType())){
		    	/* 徒弟首次提现 */
		    	JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
		    	String referrerId= obj.getString("referrerId");
		    	String userId= obj.getString("userId");
		    	MUserInfo referrer = mUserInfoDao.selectOne(referrerId); // 师父信息
		    	String channelCode = referrer.getChannelCode();
		    	if(StringUtil.isNullOrEmpty(channelCode)) {
		    		channelCode=referrer.getParentChannelCode();
		    	}
		    	if(StringUtils.isBlank(channelCode)){
		    		channelCode = "baozhu";
		    	}
				MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
				MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), referrer.getRoleType() > 1?2:1); // 获取奖励方案
		    	MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
		    	mUserApprentice.setRewardStatus(2);
		    	mUserApprentice.setUpdateTime(jmsWrapper.getCreatetime());
		    	// 修改师徒关系为有效推荐
		    	if(referrer.getRoleType().intValue() !=  ConstantUtil.ROLE_TYPE_4){
		    		long recommendCoin = mChannelConfigUser.getRecommendCoin();
		    		if(recommendCoin > 0L){
						mUserApprentice.setContribution(mUserApprentice.getContribution()+recommendCoin);
						mUserApprenticeDao.update(mUserApprentice);
						// 修改师父账户金币
						referrer.setCoin(referrer.getCoin() + recommendCoin);
						referrer.setUpdateTime(jmsWrapper.getCreatetime());
						mUserInfoDao.update(referrer);
						// 新增师父金币变动记录
						LCoinChange lCoinChange1 = new LCoinChange(referrerId, recommendCoin, ConstantUtil.FLOWTYPE_IN,
										ConstantUtil.COIN_CHANGED_TYPE_4, jmsWrapper.getCreatetime(),1,"推荐用户",referrer.getCoin());
						lCoinChangeDao.insert(lCoinChange1);
		    		}
		    	}else{
					long darenCoin = mChannelConfigUser.getDarenCoin();
					if(darenCoin > 0L){
						mUserApprentice.setContribution(mUserApprentice.getContribution() + darenCoin);
						mUserApprenticeDao.update(mUserApprentice);	
						// 修改师父账户金币
						referrer.setCoin(referrer.getCoin() + darenCoin);
						referrer.setUpdateTime(jmsWrapper.getCreatetime());
						mUserInfoDao.update(referrer);
						// 新增师父金币变动记录
						LCoinChange lCoinChange1 = new LCoinChange(referrerId, darenCoin, ConstantUtil.FLOWTYPE_IN,
										ConstantUtil.COIN_CHANGED_TYPE_4, jmsWrapper.getCreatetime(),1,"推荐用户",referrer.getCoin());
						lCoinChangeDao.insert(lCoinChange1);
					}
		    	}
		    }else if(Type.ADD_APPRENTICE.equals(jmsWrapper.getType())){
		    	// 添加师徒关系
		    	JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
		    	String referrerId= obj.getString("referrerId");
		    	String userId= obj.getString("userId");
		    	String regType= obj.getString("regType");
		    	
		    	MUserInfo referrer = mUserInfoDao.selectOne(referrerId);
				MUserApprentice mUserApprentice = new MUserApprentice();
				mUserApprentice.setReferrerId(referrerId);
				mUserApprentice.setUserId(userId);
				mUserApprentice.setCreateTime(jmsWrapper.getCreatetime());
				mUserApprentice.setUpdateTime(jmsWrapper.getCreatetime());
				mUserApprentice.setContribution(0l);
				mUserApprentice.setRewardStatus(1);
				
				// 修改师傅的徒弟数量
				referrer.setApprentice(referrer.getApprentice()+1);
				referrer.setUpdateTime(jmsWrapper.getCreatetime());
				if(referrer.getRoleType().intValue() == ConstantUtil.ROLE_TYPE_4){
					mUserApprentice.setApprenticeType(2);
					if("app".equals(regType)){ // APP注册即登陆
						// 给推荐奖励 达人邀请奖励修改至首次提现处奖励
						/*String channelCode = referrer.getChannelCode() == null?referrer.getParentChannelCode():referrer.getChannelCode();
						if(StringUtils.isBlank(channelCode)){
				    		channelCode = "baozhu";
				    	}
						MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
						MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), 1); // 获取奖励方案
						long darenCoin = mChannelConfigUser.getDarenCoin();
						referrer.setCoin(referrer.getCoin() + darenCoin);
						LCoinChange lCoinChange1 = new LCoinChange(referrerId, darenCoin, ConstantUtil.FLOWTYPE_IN,
								ConstantUtil.COIN_CHANGED_TYPE_4, jmsWrapper.getCreatetime(),1,"推荐用户",referrer.getCoin());
						lCoinChangeDao.insert(lCoinChange1);
						mUserApprentice.setContribution(mUserApprentice.getContribution() + darenCoin);*/
					}
					int daren_openActivity_limit = new Integer(pDictionaryDao.selectByName("daren_openActivity_limit").getDicValue());
					if(referrer.getApprentice().intValue() >= daren_openActivity_limit){
						referrer.setOpenActivity(2);
					}
				}else{
					mUserApprentice.setApprenticeType(1);
				}
				mUserApprenticeDao.insert(mUserApprentice);
				
				mUserInfoDao.update(referrer);
				
				LUserBQ userBq=lUserBQDao.selectByUserId(referrerId, 2);
				if(StringUtil.isNullOrEmpty(userBq)) {
					userBq=new LUserBQ();
					userBq.setUserId(referrerId);
					userBq.setCardCount(1);
					userBq.setUpdateTime(new Date().getTime());
					userBq.setBqType(2);
					lUserBQDao.insert(userBq);
				}else {
					userBq.setCardCount(userBq.getCardCount().intValue()+1);
					userBq.setUpdateTime(new Date().getTime());
					lUserBQDao.update(userBq);
				}
		    }else if(Type.REG_SEND_PASSBOOK.equals(jmsWrapper.getType())){
		    	String userId = jmsWrapper.getData().toString();
		    	List<MPassbook> mPassbooks = mPassbookDao.selectRegSend();
				List<RSUserPassbook> rss = new ArrayList<RSUserPassbook>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (MPassbook mPassbook : mPassbooks) {
					int userDay = mPassbook.getUseDay();
					Calendar cal =Calendar.getInstance();
					cal.add(Calendar.DAY_OF_YEAR, userDay);
					RSUserPassbook rs = new RSUserPassbook();
					rs.setPassbookId(mPassbook.getId());
					rs.setStatus(1);
					rs.setUserId(userId);
					rs.setExpirationDay(userDay - 1);
					rs.setExpirationTime(sdf.format(cal.getTime()));
					rss.add(rs);
				}
				if(rss.size() > 0 ){
					rsUserPassbookDao.batchInsert(rss);
				}
		    }else if(Type.HIPPO_SEND_IMP_TRACKING.equals(jmsWrapper.getType())){
		    	logger.info("Type.HIPPO_SEND_IMP_TRACKING 接收到请求：{}",jmsWrapper.getData().toString());
		    	JSONArray arrays = JSON.parseArray(jmsWrapper.getData().toString());
		    	List<HippoImpTracking> hippoImpTrackings = new ArrayList<>();
		    	for (int i = 0; i < arrays.size(); i++) {
					//HttpUtil.sendGetUrl(arrays.get(i).toString());
					hippoImpTrackings.add(new HippoImpTracking(arrays.get(i).toString(), jmsWrapper.getCreatetime()));
				}
		    	if(hippoImpTrackings.size() > 0){
		    		hippoImpTrackingDao.batchInsert(hippoImpTrackings);
		    	}
		    	logger.info("Type.HIPPO_SEND_IMP_TRACKING 执行结束，共执行：{}条",hippoImpTrackings.size());
		    }else if(Type.HIPPO_SEND_CLK_TRACKING.equals(jmsWrapper.getType())){
		    	logger.info("Type.HIPPO_SEND_CLK_TRACKING 接收到请求：{}",jmsWrapper.getData().toString());
		    	JSONArray arrays = JSON.parseArray(jmsWrapper.getData().toString());
		    	List<HippoClkTracking> hippoClkTrackings = new ArrayList<>();
		    	for (int i = 0; i < arrays.size(); i++) {
					//HttpUtil.sendGetUrl(arrays.get(i).toString());
					hippoClkTrackings.add(new HippoClkTracking(arrays.get(i).toString(), jmsWrapper.getCreatetime()));
				}
		    	if(hippoClkTrackings.size() >0){
		    		hippoClkTrackingDao.batchInsert(hippoClkTrackings);
		    	}
		    	logger.info("Type.HIPPO_SEND_CLK_TRACKING 执行结束，共执行：{}条",hippoClkTrackings.size());
		    }else if(Type.SEND_COIN_TO_TEAM_LEADER.equals(jmsWrapper.getType())){
		    	JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
		    	int changeType= obj.getInteger("changeType");
		    	String referrer= obj.getString("userId");
		    	
		    	MUserInfo mUserInfo = mUserInfoDao.selectOne(referrer);
		    	String channelCode = StringUtils.isBlank(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
		    	if(StringUtils.isBlank(channelCode)){
		    		channelCode = "baozhu";
		    	}
		    	logger.info("channelCode = {}",channelCode);
		    	MFissionScheme mFissionScheme = mFissionSchemeDao.selectSchemeByChannelCode(channelCode);
		    	double oneCommission = mFissionScheme.getOneCommission(); // 一级分佣比例
	    		double twoCommission = mFissionScheme.getTwoCommission(); // 二级分佣比例
		    	double partnerCommission = mFissionScheme.getPartnerCommission(); // 合伙人分佣比例
		    	double team_price = mFissionScheme.getTeamPrice().doubleValue(); // 团队长出售金额
		    	if(changeType == 2){
		    		team_price = mFissionScheme.getRenewPrice().doubleValue(); // 团队长续费金额
		    	}
		    	List<LCoinChange> lCoinChanges = new ArrayList<>();
		    	long oneCoin = (long)(team_price *oneCommission * 10000) ;
		    	long twoCoin = (long)(team_price *twoCommission * 10000) ;
	    		long partnerCoin = 0l;
	    		if("jwqd".equals(channelCode)){ // 只有姜维渠道才给超级合伙人分佣
	    			partnerCoin = (long)(team_price *partnerCommission * 10000);
	    		}
		    	if(mUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_3){
		    		// 如果上级是超级合伙人，则给一级分佣+合伙人分佣
		    		// 直属返利
		    		LCoinChange commissionLCoinChange = new LCoinChange(mUserInfo.getUserId(),oneCoin , ConstantUtil.FLOWTYPE_IN, 
		    				ConstantUtil.COIN_CHANGED_TYPE_15, jmsWrapper.getCreatetime(), 1,"",mUserInfo.getCoin()+oneCoin);
		    		lCoinChanges.add(commissionLCoinChange);
		    		// 超级合伙人返利
		    		if("jwqd".equals(channelCode)){
			    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(mUserInfo.getUserId(), partnerCoin, ConstantUtil.FLOWTYPE_IN,
			    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",mUserInfo.getCoin()+oneCoin+partnerCoin);
			    		lCoinChanges.add(partnerCommissioLCoinChange);
		    		}
		    		// 修改用户账户数据
		    		mUserInfo.setCoin(mUserInfo.getCoin() + oneCoin + partnerCoin);
		    		mUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
		    		mUserInfoDao.update(mUserInfo);
		    		// 给二级分佣
		    		if(StringUtils.isNotBlank(mUserInfo.getReferrer())){
		    			MUserInfo secUser = mUserInfoDao.selectOne(mUserInfo.getUserId());
		    			if(secUser.getRoleType() > 1){
		    				LCoinChange twocommissionLCoinChange = new LCoinChange(secUser.getUserId(),twoCoin , ConstantUtil.FLOWTYPE_IN,
		    						ConstantUtil.COIN_CHANGED_TYPE_17, jmsWrapper.getCreatetime(), 1,"",secUser.getCoin() + twoCoin);
				    		lCoinChanges.add(twocommissionLCoinChange);
				    		// 修改用户账户数据
				    		secUser.setCoin(secUser.getCoin() + twoCoin);
				    		secUser.setUpdateTime(jmsWrapper.getCreatetime());
				    		mUserInfoDao.update(secUser);
		    			}
		    		}
		    		
		    	}else if(mUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_2){
		    		// 如果上级是团队长，则给一级分佣 直属返利
		    		LCoinChange oneLCoinChange = new LCoinChange(mUserInfo.getUserId(),oneCoin, ConstantUtil.FLOWTYPE_IN, 
		    				ConstantUtil.COIN_CHANGED_TYPE_15, jmsWrapper.getCreatetime(), 1,"",mUserInfo.getCoin()+oneCoin);
		    		lCoinChanges.add(oneLCoinChange);
		    		
		    		mUserInfo.setCoin(mUserInfo.getCoin() + oneCoin);
		    		mUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
		    		mUserInfoDao.update(mUserInfo);
		    		if(StringUtils.isNotBlank(mUserInfo.getReferrer())){
		    			MUserInfo towmUserInfo = mUserInfoDao.selectOne(mUserInfo.getReferrer());
		    			if(towmUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_2){
		    				// 如果上上级是团队长给二级分佣
		    				LCoinChange twoCommissionLCoinChange = new LCoinChange(towmUserInfo.getUserId(), twoCoin , ConstantUtil.FLOWTYPE_IN,
		    						ConstantUtil.COIN_CHANGED_TYPE_17, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin()+twoCoin);
		    				lCoinChanges.add(twoCommissionLCoinChange);
		    				towmUserInfo.setCoin(towmUserInfo.getCoin() + twoCoin);
		    				towmUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
				    		mUserInfoDao.update(towmUserInfo);
				    		if("jwqd".equals(channelCode)){
					    		if(StringUtils.isNotBlank(towmUserInfo.getReferrer())){
					    			// 再往上查最近一级的超级合伙人给合伙人分佣
					    			String partnerUserId = getPartnerUserId(towmUserInfo.getUserId());
					    			if(StringUtils.isNotBlank(partnerUserId)){
						    			MUserInfo partnerUserInfo = mUserInfoDao.selectOne(partnerUserId);
						    			// 超级合伙人返利
							    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(partnerUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN,
							    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",partnerUserInfo.getCoin() + partnerCoin);
							    		lCoinChanges.add(partnerCommissioLCoinChange);
							    		partnerUserInfo.setCoin(partnerUserInfo.getCoin() + partnerCoin);
							    		partnerUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
							    		mUserInfoDao.update(partnerUserInfo);
					    			}
					    		}
				    		}
		    			}else if(towmUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_3){
		    				// 二级分佣
				    		LCoinChange twoCommissioLCoinChange = new LCoinChange(towmUserInfo.getUserId(), twoCoin, ConstantUtil.FLOWTYPE_IN, 
				    				ConstantUtil.COIN_CHANGED_TYPE_17, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin() + twoCoin);
				    		lCoinChanges.add(twoCommissioLCoinChange);
				    		// 超级合伙人返利
				    		if("jwqd".equals(channelCode)){
					    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(towmUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN, 
					    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin() + twoCoin + partnerCoin);
					    		lCoinChanges.add(partnerCommissioLCoinChange);
				    		}
				    		// 修改用户账户数据
				    		towmUserInfo.setCoin(towmUserInfo.getCoin() + twoCoin + partnerCoin);
				    		towmUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
				    		mUserInfoDao.update(towmUserInfo);
		    			}else {
		    				if("jwqd".equals(channelCode)){
			    				if(StringUtils.isNotBlank(towmUserInfo.getReferrer())){
					    			// 再往上查最近一级的超级合伙人给合伙人分佣
					    			String partnerUserId = getPartnerUserId(towmUserInfo.getUserId());
					    			if(StringUtils.isNotBlank(partnerUserId)){
						    			MUserInfo partnerUserInfo = mUserInfoDao.selectOne(partnerUserId);
						    			// 超级合伙人返利
							    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(partnerUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN,
							    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",partnerUserInfo.getCoin() + partnerCoin );
							    		lCoinChanges.add(partnerCommissioLCoinChange);
							    		partnerUserInfo.setCoin(partnerUserInfo.getCoin() + partnerCoin);
							    		partnerUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
							    		mUserInfoDao.update(partnerUserInfo);
					    			}
					    		}
			    			}
		    			}
		    		}else{
		    			if("jwqd".equals(channelCode)){
			    			// 再往上查最近一级的超级合伙人给合伙人分佣
			    			String partnerUserId = getPartnerUserId(mUserInfo.getUserId());
			    			if(StringUtils.isNotBlank(partnerUserId)){
				    			MUserInfo partnerUserInfo = mUserInfoDao.selectOne(partnerUserId);
				    			// 超级合伙人返利
					    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(partnerUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN,
					    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",partnerUserInfo.getCoin() + partnerCoin);
					    		lCoinChanges.add(partnerCommissioLCoinChange);
					    		partnerUserInfo.setCoin(partnerUserInfo.getCoin() + partnerCoin);
					    		partnerUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
					    		mUserInfoDao.update(partnerUserInfo);
			    			}
			    		}
		    		}
		    	}else {
		    		// 如果是小猪猪，跳过一级分佣，查上上级的角色给二级分佣，再网上查最近一级的超级合伙人给合伙人分佣
		    		if(StringUtils.isNotBlank(mUserInfo.getReferrer())){
		    			MUserInfo towmUserInfo = mUserInfoDao.selectOne(mUserInfo.getReferrer());
		    			if(towmUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_2){
		    				LCoinChange twoCommissionLCoinChange = new LCoinChange(towmUserInfo.getUserId(),twoCoin , ConstantUtil.FLOWTYPE_IN,
		    						ConstantUtil.COIN_CHANGED_TYPE_17, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin() + twoCoin);
		    				lCoinChanges.add(twoCommissionLCoinChange);
		    				// 修改用户账户数据
				    		towmUserInfo.setCoin(towmUserInfo.getCoin() + twoCoin);
				    		towmUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
				    		mUserInfoDao.update(towmUserInfo);
				    		if("jwqd".equals(channelCode)){
					    		if(towmUserInfo.getReferrer() != null){
					    			// 再往上查最近一级的超级合伙人给合伙人分佣
					    			String partnerUserId = getPartnerUserId(towmUserInfo.getUserId());
					    			if(StringUtils.isNotBlank(partnerUserId)){
						    			MUserInfo partnerUserInfo = mUserInfoDao.selectOne(partnerUserId);
						    			// 超级合伙人返利
							    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(partnerUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN,
							    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",partnerUserInfo.getCoin() + partnerCoin);
							    		lCoinChanges.add(partnerCommissioLCoinChange);
							    		partnerUserInfo.setCoin(partnerUserInfo.getCoin() + partnerCoin);
							    		partnerUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
							    		mUserInfoDao.update(partnerUserInfo);
					    			}
					    		}
				    		}
		    			}else if(towmUserInfo.getRoleType() == ConstantUtil.ROLE_TYPE_3){
		    				// 二级返利
				    		LCoinChange twoCommissioLCoinChange = new LCoinChange(towmUserInfo.getUserId(), twoCoin, ConstantUtil.FLOWTYPE_IN,
				    				ConstantUtil.COIN_CHANGED_TYPE_17, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin() + twoCoin);
				    		lCoinChanges.add(twoCommissioLCoinChange);
				    		// 超级合伙人返利
				    		if("jwqd".equals(channelCode)){
					    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(towmUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN, 
					    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",towmUserInfo.getCoin() + twoCoin + partnerCoin);
					    		lCoinChanges.add(partnerCommissioLCoinChange);
				    		}
				    		// 修改用户账户数据
				    		towmUserInfo.setCoin(towmUserInfo.getCoin() + twoCoin + partnerCoin);
				    		towmUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
				    		mUserInfoDao.update(towmUserInfo);
		    			}else{
		    				if("jwqd".equals(channelCode)){
			    				// 再往上查最近一级的超级合伙人给合伙人分佣
				    			String partnerUserId = getPartnerUserId(towmUserInfo.getUserId());
				    			if(StringUtils.isNotBlank(partnerUserId)){
					    			MUserInfo partnerUserInfo = mUserInfoDao.selectOne(partnerUserId);
					    			// 超级合伙人返利
						    		LCoinChange partnerCommissioLCoinChange = new LCoinChange(partnerUserInfo.getUserId(), partnerCoin , ConstantUtil.FLOWTYPE_IN,
						    				ConstantUtil.COIN_CHANGED_TYPE_18, jmsWrapper.getCreatetime(), 1,"",partnerUserInfo.getCoin() + partnerCoin);
						    		lCoinChanges.add(partnerCommissioLCoinChange);
						    		partnerUserInfo.setCoin(partnerUserInfo.getCoin() + partnerCoin);
						    		partnerUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
						    		mUserInfoDao.update(partnerUserInfo);
				    			}
			    			}
		    			}
		    		}
		    	}
		    	if(lCoinChanges.size()>0) {
		    		lCoinChangeDao.batchInsert(lCoinChanges);	
		    	}
		    }else if(Type.EXCHANGE_COIN_TASK_QDZ.equals(jmsWrapper.getType())){
		    	logger.info("消息类型：{}-----开始",Type.EXCHANGE_COIN_TASK_QDZ);
		    	String userId = jmsWrapper.getData().toString();
			    List<LUserSignin> lUserSignins = lUserSigninDao.selectSignin(userId);
			    if(lUserSignins.size() > 0){
			    	long hasReward = 0l;
			    	LUserSignin lUserSignin = lUserSignins.get(0);
			    	// 查询游戏任务
			    	List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserSigin(userId, lUserSignin.getId());
			    	if(lUserSignGames.size() == lUserSignin.getGameCount().intValue()){
			    		// 查询今日兑换金猪的金币数
			    		long now = new Date().getTime();
			    		long dayT = 24 * 60 * 60 * 1000l;
			    		long time = now - (now % dayT);
			    		long sumTime = time;
			    		if(lUserSignGames.get(lUserSignGames.size() - 1).getFinishTime().longValue() > time){
			    			sumTime = lUserSignGames.get(lUserSignGames.size() - 1).getFinishTime();
			    		}
			    		long coinTotal = lCoinChangeDao.selectSumByUser(userId, sumTime, ConstantUtil.COIN_CHANGED_TYPE_12);
			    		if(lUserSignins.size() == 3){
			    			if(coinTotal >= 20000){
			    				lUserSignin.setStatus(3);
			    				lUserSignin.setUpdateTime(now);
			    				lUserSigninDao.update(lUserSignin);
			    			}
			    		}else{
			    			long needCoin = 0l;
			    			// 判断今天是否领了奖励
			    			List<LUserSignin> hasReces = lUserSigninDao.selectSigninFinish(userId, time);
			    			for (LUserSignin hasRece : hasReces) {
								if(hasRece.getSignDay().intValue() == 5 || hasRece.getSignDay().intValue() == 10){
									hasReward = hasReward + 20000;
								}else{
									hasReward = hasReward + 50000;
								}
							}
			    			// 判断待修改的记录
			    			if(lUserSignin.getSignDay().intValue() == 5 || lUserSignin.getSignDay().intValue() == 10){
			    				needCoin = 20000;
			    			}else {
			    				needCoin = 50000;
			    			}
			    			if(coinTotal >= (hasReward + needCoin)){
			    				lUserSignin.setStatus(3);
			    				lUserSignin.setUpdateTime(now);
			    				lUserSigninDao.update(lUserSignin);
			    			}
			    		}
			    	}
			    }
			    logger.info("消息类型：{}-----结束",Type.EXCHANGE_COIN_TASK_QDZ);
		    }else if(Type.SEND_KS_CALLBACK.equals(jmsWrapper.getType())){
		    	String imeiMD5 = jmsWrapper.getData().toString();
		    	logger.info("------开始发送激活，注册数据给快手---imeiMD5:{}-------------------------",imeiMD5);
		    	TPKsCallback tpKsCallback = tpKsCallbackDao.selectByImei(imeiMD5);
		    	if(tpKsCallback != null && tpKsCallback.getStatus().intValue() == 1){
		    		// 发送激活回调
		    		String jhCallback = tpKsCallback.getCallback() + "&event_type=1&event_time="+jmsWrapper.getCreatetime();
		    		String respjhText = HttpUtil.sendGetUrlNew(jhCallback);
		    		logger.info("------------激活数据：{}------",respjhText);
		    		// 发送注册回调
		    		String zcCallback = tpKsCallback.getCallback() + "&event_type=2&event_time="+jmsWrapper.getCreatetime();
		    		String respzcText = HttpUtil.sendGetUrlNew(zcCallback);
		    		logger.info("------------注册数据：{}------",respzcText);
		    		tpKsCallback.setStatus(2);
		    		tpKsCallbackDao.update(tpKsCallback);
		    	}
		    	logger.info("------发送激活，注册数据给快手结束---imeiMD5:{}-------------------------",imeiMD5);
		    	
		    }else if(Type.SEND_QTT_CALLBACK.equals(jmsWrapper.getType())){
		    	String imeiMD5 = jmsWrapper.getData().toString();
		    	logger.info("------开始发送激活，注册数据给趣头条---imeiMD5:{}-------------------------",imeiMD5);
		    	TPQttCallback tpQttCallback = tpQttCallbackDao.selectByImei(imeiMD5);
		    	if(tpQttCallback != null && tpQttCallback.getStatus().intValue() == 1){
		    		// 发送激活回调
		    		String callback = URLDecoder.decode(tpQttCallback.getCallback(),"utf8");
		    		String jhCallback = callback + "&op2=0&op2_active_time="+jmsWrapper.getCreatetime();
		    		String respjhText = HttpUtil.sendGetUrlNew(jhCallback);
		    		logger.info("------------激活数据：{}------",respjhText);
		    		// 发送注册回调
		    		String zcCallback = callback + "&op2=1&op2_active_time="+jmsWrapper.getCreatetime();
		    		String respzcText = HttpUtil.sendGetUrlNew(zcCallback);
		    		logger.info("------------注册数据：{}------",respzcText);
		    		tpQttCallback.setStatus(2);
		    		tpQttCallbackDao.update(tpQttCallback);
		    	}
		    	logger.info("------发送激活，注册数据给趣头条结束---imeiMD5:{}-------------------------",imeiMD5);
		    	
		    }
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.cash")  
	public void receiveCash(String text) {  
		logger.info("监听到bz.queue.cash队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.cash",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			LCoinChange lCoinChange=JSON.parseObject(jmsWrapper.getData().toString(),LCoinChange.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					String res="1";
					MUserInfo mUserInfo=mUserInfoDao.selectOne(lCoinChange.getUserId());
					long coin_to_money = new Long(pDictionaryDao.selectByName("coin_to_money").getDicValue());
					if(mUserInfo.getCoin()<lCoinChange.getAmount()) {
						return "2";//金币不足
					}
					long now = new Date().getTime();
					List<LCoinChange> lCoinChanges = lCoinChangeDao.selectByChangeType(lCoinChange.getUserId(), lCoinChange.getChangedType());
					if(lCoinChanges.size() == 0){
						res="3"; // 是首次提现
					}
					// 开始抵扣，插入金币申请表，发送消息
					mUserInfoDao.updatecCoinReduce(lCoinChange.getAmount(), lCoinChange.getUserId());
					lCoinChange.setChangedTime(now);
					lCoinChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
					lCoinChange.setAmount(lCoinChange.getAmount());
					lCoinChange.setStatus(2);
					lCoinChange.setCoinBalance(mUserInfo.getCoin()-lCoinChange.getAmount());
					lCoinChangeDao.insertCoin(lCoinChange);
					String outTradeNo = Base64.getOutTradeNo();
					LUserExchangeCash lUserExchangeCash=new LUserExchangeCash();
					//int actualAmount=(int)(lCoinChange.getAmount()/coin_to_money);
					lUserExchangeCash.setCoinChangeId(lCoinChange.getId());
					lUserExchangeCash.setUserId(lCoinChange.getUserId());
					lUserExchangeCash.setOutTradeNo(outTradeNo+"1");
					lUserExchangeCash.setCoin(lCoinChange.getAmount());
					lUserExchangeCash.setActualAmount(new BigDecimal(lCoinChange.getActualAmount()));
					lUserExchangeCash.setServiceCharge(new BigDecimal(lCoinChange.getAmount()/10000.0-lCoinChange.getActualAmount()));
					lUserExchangeCash.setCoinToMoney((int)coin_to_money);
					lUserExchangeCash.setCreatorTime(new Date().getTime());
					lUserExchangeCash.setState(1);
					lUserExchangeCash.setCoinBalance(mUserInfo.getCoin());
					lUserExchangeCash.setUserIp(lCoinChange.getUserIp());
					lUserExchangeCash.setCashType(1);

					lUserExchangeCash.setRealName(mUserInfo.getUserName());	
					
					if(lCoinChange.getAccountType().intValue()==2) {
						lUserExchangeCash.setBankAccount(mUserInfo.getAliNum());
					}else if(lCoinChange.getAccountType().intValue()==1) {
						lUserExchangeCash.setBankAccount(mUserInfo.getOpenId());
					}
					/*result=cashTask(actualAmount,flag,mUserInfo,now,lCoinChange,lUserExchangeCash.getId());*/
					int money=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CASH_QUOTA).getDicValue());
					if(lCoinChange.getActualAmount()<=money) {
						/*jmsProducer.userCash(Type.CASH_PAY, lUserExchangeCash.getId());	*/
						lUserExchangeCash.setIsLocking(1);
						lUserExchangeCash.setLockingMobile("1");
					}else {
						lUserExchangeCash.setIsLocking(2);
					}
					lUserExchangeCashDao.insert(lUserExchangeCash);
					return res;
				}
				
			}.execute(lCoinChange.getUserId(),"redis_lock_cash");
			if(!"2".equals(res)) {
				if(lCoinChange.getAccountType().intValue()==1) {
					completeTask(lCoinChange.getUserId(),22);
				}else {
					completeTask(lCoinChange.getUserId(),21);
				}
			}
			if("3".equals(res)) {
				MUserInfo mUserInfo=mUserInfoDao.selectOne(lCoinChange.getUserId());
				if(StringUtils.isNotEmpty(mUserInfo.getReferrer())){
					userFirstWithdrawals(mUserInfo.getReferrer(),mUserInfo.getUserId());
	    		}
		    }
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	} 
	
	public String getPartnerUserId(String userId){
		MUserInfo muserInfo = mUserInfoDao.selectOne(userId);
		if(StringUtils.isNotBlank(muserInfo.getReferrer())){
			MUserInfo referrrer = mUserInfoDao.selectOne(muserInfo.getReferrer());
			if(referrrer.getRoleType() == 3){
				return referrrer.getUserId();
			}else{
				return getPartnerUserId(referrrer.getUserId());
			}
		}
		return null;
	}
	
	@JmsListener(destination = "bz.queue.game")  
	public void gameCallback(String text) {  
		logger.info("监听到bz.queue.game队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.game",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info("---------------"+jmsWrapper.getType().toString()+"---------------");
			if(JmsWrapper.Type.CALL_BACK_YOLE.equals(jmsWrapper.getType())){
				YoleCallback yoleCallback = JSON.parseObject(jmsWrapper.getData().toString(),YoleCallback.class);
				if(yoleCallback != null && yoleCallback.getAppid() != null){
					yoleCallback.setStatus(2);
					long reward = 0l;
					// 验证是否sign是否正确
					String appid = yoleCallback.getAppid();
					String gmgold = yoleCallback.getGmgold();
					try{
						String str = new AWorker<String>() {
							@Override
							public String call(String request) throws Exception {
								long reward = 0l;
								reward = tpGameService.settlementGame(appid, new Long(gmgold),yoleCallback.getGmname());
								yoleCallback.setStatus(1);
								return reward+"";
							}
						}.execute(appid, "redis_lock_CALL_BACK_YOLE");
						reward = new Long(str);
					}catch (Exception e) {
						e.printStackTrace();
						jmsNewsLog.setState(2);
						jmsNewsLog.setJmsException(e.toString());
						logger.info("JmsWrapper.Type.CALL_BACK_YOLE 异常,Yoleid:{}",yoleCallback.getYoleid());
					}
					yoleCallback.setCreateTime(DateUtil.getCurrentDate());
					tpGameService.yoleCallbackInsert(yoleCallback,reward);
				}
			}else if(JmsWrapper.Type.CALL_BACK_PCDD.equals(jmsWrapper.getType())){
				PCDDCallback pcDDCallback = JSON.parseObject(jmsWrapper.getData().toString(),PCDDCallback.class);
				if(pcDDCallback != null && pcDDCallback.getAdid() != null){
					long baseReward = (long) (pcDDCallback.getMoney() * 10000);
					long reward = 0l;
					pcDDCallback.setStatus(1);
					try{
						String str = new AWorker<String>() {
							@Override
							public String call(String request) throws Exception {
								long reward = 0l;
								reward = tpGameService.settlementGame(pcDDCallback.getUserid(), baseReward,pcDDCallback.getAdname());
								pcDDCallback.setStatus(2);
								return reward+"";
							}
						}.execute(pcDDCallback.getUserid(), "redis_lock_CALL_BACK_PCDD");
						reward = new Long(str);
					}catch (Exception e) {
						e.printStackTrace();
						jmsNewsLog.setState(2);
						jmsNewsLog.setJmsException(e.toString());
						logger.info("JmsWrapper.Type.CALL_BACK_PCDD 异常,Ordernum:{}",pcDDCallback.getOrdernum());
					}
					pcDDCallback.setCreateTime(DateUtil.getCurrentDate());
					tpGameService.pcddCallbackInsert(pcDDCallback,reward);
				}
			}else if(JmsWrapper.Type.CALL_BACK_XW.equals(jmsWrapper.getType())){
				XWCallback xwCallback = JSON.parseObject(jmsWrapper.getData().toString(),XWCallback.class);
				if(xwCallback != null && xwCallback.getAdid() != null){
					double moneyd = Double.valueOf(xwCallback.getMoney());
					long baseReward = (long) (moneyd * 10000);
					long reward = 0l;
					xwCallback.setStatus(2);
					try{
						String str = new AWorker<String>() {
							@Override
							public String call(String request) throws Exception {
								long reward = 0l;
								reward = tpGameService.settlementGame(xwCallback.getAppsign(), baseReward,xwCallback.getAdname());
								xwCallback.setStatus(1);
								return reward+"";
						}
					}.execute(xwCallback.getAppsign(), "redis_lock_CALL_BACK_XW");
					reward = new Long(str);
					}catch (Exception e) {
						jmsNewsLog.setState(2);
						jmsNewsLog.setJmsException(e.toString());
						logger.info("JmsWrapper.Type.CALL_BACK_XW 异常,Ordernum:{}",xwCallback.getOrdernum());
					}
					xwCallback.setCreateTime(DateUtil.getCurrentDate());
					tpGameService.xwCallbackInsert(xwCallback,reward);
				}
			}else if(JmsWrapper.Type.CALL_BACK_BZ.equals(jmsWrapper.getType())){
				BZCallback bzCallback = JSON.parseObject(jmsWrapper.getData().toString(),BZCallback.class);
				if(bzCallback != null && bzCallback.getAdid() != null){
					double moneyd = Double.valueOf(bzCallback.getMoney());
					long baseReward = (long) (moneyd * 10000);
					long reward = 0l;
					bzCallback.setStatus(2);
					try{
						String str = new AWorker<String>() {
							@Override
							public String call(String request) throws Exception {
								long reward = 0l;
								reward = tpGameService.settlementGame(bzCallback.getAppsign(), baseReward,bzCallback.getAdname());
								bzCallback.setStatus(1);
								return reward+"";
						}
					}.execute(bzCallback.getAppsign(), "redis_lock_CALL_BACK_BZ");
					reward = new Long(str);
					}catch (Exception e) {
						jmsNewsLog.setState(2);
						jmsNewsLog.setJmsException(e.toString());
						logger.info("JmsWrapper.Type.CALL_BACK_BZ 异常,Ordernum:{}",bzCallback.getOrdernum());
					}
					bzCallback.setCreateTime(DateUtil.getCurrentDate());
					tpGameService.bzCallbackInsert(bzCallback, reward);
				}
			}else if(JmsWrapper.Type.CALL_BACK_XYZ.equals(jmsWrapper.getType())){
				XWCallback xwCallback = JSON.parseObject(jmsWrapper.getData().toString(),XWCallback.class);
				if(xwCallback != null && xwCallback.getAdid() != null){
					double moneyd = Double.valueOf(xwCallback.getMoney());
					moneyd = moneyd / 0.4;
					long baseReward = (long) Math.ceil(moneyd);
					long reward = 0l;
					xwCallback.setStatus(2);
					try{
						String str = new AWorker<String>() {
							@Override
							public String call(String request) throws Exception {
								long reward = 0l;
								reward = tpGameService.settlementXYZ(xwCallback.getAppsign(), baseReward,xwCallback.getAdname());
								xwCallback.setStatus(1);
								return reward+"";
						}
					}.execute(xwCallback.getAppsign(), "redis_lock_CALL_BACK_XYZ");
					reward = new Long(str);
					}catch (Exception e) {
						jmsNewsLog.setState(2);
						jmsNewsLog.setJmsException(e.toString());
						logger.info("JmsWrapper.Type.redis_lock_CALL_BACK_XYZ 异常,Ordernum:{}",xwCallback.getOrdernum());
					}
					xwCallback.setCreateTime(DateUtil.getCurrentDate());
					tpGameService.xwCallbackInsertXYZ(xwCallback,reward);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.sign")  
	public void daySign(String text) {  
		logger.info("监听到bz.queue.sign队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.sign",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			LUserSign lUserSign = JSON.parseObject(jmsWrapper.getData().toString(),LUserSign.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					int count=lUserSignDao.selectIsSign(lUserSign.getUserId());
					if(count>0) {
						return "2";//今日已签到
					}
					int i=lUserSignDao.insert(lUserSign);
					if(i>0) {
						int res=coinAdd(lUserSign.getScore(),lUserSign.getUserId(),ConstantUtil.COIN_CHANGED_TYPE_2);
						if(res>0) {
							return "1";
						}
					}
					return "2";
				}
			}.execute(lUserSign.getUserId(),"redis_lock_sign");
			if("1".equals(res)) {
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(lUserSign.getUserId());
				appNewsInform.setInformTitle(InformConstant.SIGN_REWARD_TITLE);
				appNewsInform.setInformContent(InformConstant.SIGN_REWARD_CONTENT+"+"+lUserSign.getScore()+"金币");
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);	
				lUserSignService.masterWorkerCoin(lUserSign.getUserId());
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.active")  
	public void dayActive(String text) {  
		logger.info("监听到bz.queue.active队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.active",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			long coin=Long.parseLong(obj.getString("coin"));
			long pig=Long.parseLong(obj.getString("pig"));
			String vipName=obj.getString("vipName");
			//List<LActiveGoldLog> list=JSON.parseArray(obj.getString("list").toString(),LActiveGoldLog.class); 
			LActiveGoldLog lActiveGoldLog=JSON.parseObject(obj.getString("activeLog").toString(),LActiveGoldLog.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					int count=lActiveGoldLogDao.selectDayNews(userId, lActiveGoldLog.getVipId());
					if(count!=0) {
						return "2";//用户已领取过活跃金
					}
					lActiveGoldLogDao.save(lActiveGoldLog);
					coinAddRemark(coin, userId, ConstantUtil.COIN_CHANGED_TYPE_6,vipName);
					pigAddRemark(pig,userId,ConstantUtil.PIG_CHANGED_TYPE_1,vipName);
					return "1";
				}
			}.execute(userId,"redis_lock_action");
			if("1".equals(res)) {
				lActiveGoldLogService.rechargeRewardPush(userId,String.valueOf(coin),"金币");
				lActiveGoldLogService.rechargeRewardPush(userId,String.valueOf(pig),"金猪");	
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.task")  
	public void userTask(String text) {  
		logger.info("监听到bz.queue.sign队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.task",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			Integer task=Integer.parseInt(obj.getString("task"));
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					LUserTask lUserTask=lUserTaskDao.selectOne(userId, task);
					Map<String,Object> date=new HashMap<>();
					String res="-1";
					if(StringUtil.isNullOrEmpty(lUserTask)) {
						MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(task);
						if(StringUtil.isNullOrEmpty(mTaskInfo)) {
							return "1";
						}
						date=mPassbookService.taskUsePassbook(userId, mTaskInfo.getTaskType());;
						long multiple=(long)date.get("res");
						LUserTask userTask=new LUserTask();
						userTask.setUserId(userId);
						userTask.setTaskId(task);
						userTask.setCreateTime(new Date().getTime());
						lUserTaskDao.insert(userTask);
						if(mTaskInfo.getRewardUnit()==1) {//金币奖励
							long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
							coinAdd(amount,userId,ConstantUtil.COIN_CHANGED_TYPE_10);
							res=mTaskInfo.getRemark()+"+"+amount+"金币";
						}else if(mTaskInfo.getRewardUnit()==2) {//金猪奖励
							long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
						    pigAdd(amount,userId,ConstantUtil.PIG_CHANGED_TYPE_3);
						    res=mTaskInfo.getRemark()+"+"+amount+"金猪";
						}
						mPassbookService.passbookOverdue((int)date.get("fbrspassbookId"));
				   }
				   return res;
				}
			}.execute(userId,"redis_lock_task");
			if(!"-1".equals(res)) {
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(res);	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.coinExchangePig")  
	public void coinExchangePig(String text) {  
		logger.info("监听到bz.queue.coinExchangePig队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.coinExchangePig",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			long coin=Long.parseLong(obj.getString("coin"));
			long pig=coin*10;
			Double add=lUserVipDao.pigAddition(userId);
			if(add!=null && add!=0) {
				long addNum=(long) (pig*add/100);
				pig=pig+addNum;
			}
			long piga=pig;
			
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
					if(mUserInfo.getCoin()<coin) {
						return "2";//账户余额不足
					}
					int i=coinReduce(coin,userId,ConstantUtil.COIN_CHANGED_TYPE_12);
					if(i>0) {
						pigAdd(piga,userId,ConstantUtil.PIG_CHANGED_TYPE_5);	
					}
					return "1";
				}
			}.execute(userId,"redis_lock_coinExchangePig");	
			
			if("1".equals(res)) {
				//兑换金猪任务
			    LUserTask task=lUserTaskDao.selectOne(userId, 11);
			    if(!StringUtil.isNullOrEmpty(task)) {
			    	LUserTask lUserTask=lUserTaskDao.selectOne(userId, 15);
				    if(StringUtil.isNullOrEmpty(lUserTask)) {
				    	Long pigNum=lPigChangeDao.selectCountPig(5, userId);
				    	long max =Long.parseLong(pDictionaryDao.selectByName(DictionaryUtil.PIG_EXCHANGE_TASK).getDicValue());
				    	if(pigNum>=max) {
				    		LUserTask userTask=new LUserTask();
							userTask.setUserId(userId);
							userTask.setTaskId(15);
							userTask.setCreateTime(new Date().getTime());
							userTask.setIsReceive(1);
							lUserTaskDao.insert(userTask);	
				    	}
				    }
			    }
			}
			
		    List<LUserSignin> lUserSignins = lUserSigninDao.selectSignin(userId);
		    if(lUserSignins.size() > 0){
		    	long hasReward = 0l;
		    	LUserSignin lUserSignin = lUserSignins.get(0);
		    	// 查询游戏任务
		    	List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserSigin(userId, lUserSignin.getId());
		    	if(lUserSignGames.size() == lUserSignin.getGameCount().intValue()){
		    		// 查询今日兑换金猪的金币数
		    		long now = new Date().getTime();
		    		long dayT = 24 * 60 * 60 * 1000l;
		    		long time = now - (now % dayT);
		    		long sumTime = time;
		    		if(lUserSignGames.get(lUserSignGames.size() - 1).getFinishTime().longValue() > time){
		    			sumTime = lUserSignGames.get(lUserSignGames.size() - 1).getFinishTime();
		    		}
		    		long coinTotal = lCoinChangeDao.selectSumByUser(userId, sumTime, ConstantUtil.COIN_CHANGED_TYPE_12);
		    		logger.info("coinTotal ------------------------"+coinTotal);
		    		if(lUserSignins.size() == 3){
		    			logger.info("lUserSignins.size() ------------------------"+lUserSignins.size());		    			
		    			if(coinTotal >= 20000){
		    				lUserSignin.setStatus(3);
		    				lUserSignin.setUpdateTime(now);
		    				lUserSigninDao.update(lUserSignin);
		    			}
		    		}else{
		    			long needCoin = 0l;
		    			// 判断今天是否领了奖励
		    			List<LUserSignin> hasReces = lUserSigninDao.selectSigninFinish(userId, sumTime);
		    			for (LUserSignin hasRece : hasReces) {
							if(hasRece.getSignDay().intValue() == 5 || hasRece.getSignDay().intValue() == 10){
								hasReward = hasReward + 20000;
							}else{
								hasReward = hasReward + 50000;
							}
						}
		    			// 判断待修改的记录
		    			if(lUserSignin.getSignDay().intValue() == 5 || lUserSignin.getSignDay().intValue() == 10){
		    				needCoin = 20000;
		    			}else {
		    				needCoin = 50000;
		    			}
		    			if(coinTotal >= (hasReward + needCoin)){
		    				lUserSignin.setStatus(3);
		    				lUserSignin.setUpdateTime(now);
		    				lUserSigninDao.update(lUserSignin);
		    			}
		    		}
		    	}
		    }
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.cashExamine")  
	public void cashExamine(String text) {  
		logger.info("监听到bz.queue.cashExamine队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.cashExamine",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			LUserExchangeCash lUserExchangeCash = JSON.parseObject(jmsWrapper.getData().toString(),LUserExchangeCash.class);
			LCoinChange lCoinChangeOld = lCoinChangeDao.selectOne(lUserExchangeCash.getCoinChangeId());
			
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					long now=new Date().getTime();
					if(lCoinChangeOld.getStatus().intValue() != 2){
						return "3";//记录已审核
					}
					lCoinChangeOld.setReason(lUserExchangeCash.getRemarks());
					if(lUserExchangeCash.getState().intValue()==5) {
						lCoinChangeOld.setAuditTime(now);
						lUserExchangeCash.setExamineTime(now);
						lCoinChangeOld.setStatus(1);
						lCoinChangeDao.update(lCoinChangeOld);
						lUserExchangeCashDao.update(lUserExchangeCash);
						return "1";//提现成功
					}else if(lUserExchangeCash.getState().intValue()==2){
						lCoinChangeOld.setStatus(1);
						lCoinChangeDao.update(lCoinChangeOld);
						lUserExchangeCashDao.update(lUserExchangeCash);
						return "1";//提现成功
					}else {
						lCoinChangeOld.setAuditTime(now);
						lUserExchangeCash.setExamineTime(now);
						lCoinChangeOld.setStatus(3);
						int i=coinAdd(lCoinChangeOld.getAmount(),lCoinChangeOld.getUserId(),ConstantUtil.COIN_CHANGED_TYPE_14);
						if(i>0) {
							lCoinChangeDao.update(lCoinChangeOld);
							lUserExchangeCashDao.update(lUserExchangeCash);
							return "2";
						}
						return "0";//提现失败
					}
				}
			}.execute(lUserExchangeCash.getUserId(),"redis_lock_cashExamine");
			/*if("1".equals(res)) {
				// 发送提现到账消息
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(lCoinChangeOld.getUserId());
				appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_WITHDRAWALS);
				appNewsInform.setInformContent(InformConstant.PUSH_CONTENT_SUCCESS);
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}else if("2".equals(res)){
				// 发送提现不通过消息
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(lCoinChangeOld.getUserId());
				appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_WITHDRAWALS);
				appNewsInform.setInformContent(InformConstant.PUSH_CONTENT_REFUSE);
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.userExchangeGoods")  
	public void userExchangeGoods(String text) {  
		logger.info("监听到bz.queue.userExchangeGoods队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.userExchangeGoods",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			Integer id=Integer.parseInt(obj.getString("id"));
			MLotteryGoods mLotteryGoods=mLotteryGoodsDao.selectOne(id);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					MUserInfo user=mUserInfoDao.selectOne(userId);
					if(mLotteryGoods.getPigCoin()>user.getPigCoin()) {
						return "0";//金猪不足
					}
					int i=pigReduce(mLotteryGoods.getPigCoin(),userId,ConstantUtil.PIG_CHANGED_TYPE_8);
					if(i>0) {
						return user.getAccountId().toString();
					}
					return "0";
				}
			}.execute(userId,"redis_lock_userExchangeGoods");
			if(!"0".equals(res)) {
				MLotteryOrder mLotteryOrder=new MLotteryOrder();
				mLotteryOrder.setUserId(userId);
				mLotteryOrder.setAccountId(Integer.parseInt(res));
				mLotteryOrder.setTypeId(mLotteryGoods.getTypeId());
				mLotteryOrder.setGoodsId(id);
				mLotteryOrder.setPrice(mLotteryGoods.getPrice());
				mLotteryOrder.setExpendPigCoin(mLotteryGoods.getPigCoin());
				mLotteryOrder.setStatus(1);
				mLotteryOrder.setCreateTime(new Date().getTime());
				mLotteryOrderDao.insert(mLotteryOrder);
				mLotteryGoodsDao.updateNumberAdd(id);
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}

	@JmsListener(destination = "bz.queue.userReceiveTask")  
	public void userReceiveTask(String text) {  
		logger.info("监听到bz.queue.userReceiveTask队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.userReceiveTask",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			Integer id=Integer.parseInt(obj.getString("id"));
			
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					LUserTask userTask=lUserTaskDao.selectOne(userId, id);
					if(StringUtil.isNullOrEmpty(userTask)) {
						return "3";//任务未完成
					}
					if(userTask.getIsReceive()==2) {
						return "2";//已领取过奖励
					}
					MTaskInfo task=mTaskInfoDao.selectOne(id);
					int i=coinAdd(task.getReward(),userId,ConstantUtil.COIN_CHANGED_TYPE_10);
					if(i>0) {
						lUserTaskDao.update(2,userTask.getId());
					}
					return "1";
				}
			}.execute(userId,"redis_lock_userReceiveTask");
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.reliefPig")  
	public void reliefPig(String text) {  
		logger.info("监听到bz.queue.reliefPig队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.reliefPig",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String userId=obj.getString("userId");
			long pig=Long.parseLong(obj.getString("pig"));
			int num=Integer.parseInt(obj.getString("num"));
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					int count=lPigChangeDao.getReliefPig(userId);
					if(count>=num) {
						return "3";//领取次数已用完
					}
					pigAdd(pig,userId,ConstantUtil.PIG_CHANGED_TYPE_7);
					return "1";
				}
			}.execute(userId,"redis_lock_reliefPig");
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.ymCallback")  
	public void ymCallback(String text) {  
		logger.info("监听到bz.queue.ymCallback队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.ymCallback",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			TPYmCallback tPYmCallback=JSON.parseObject(jmsWrapper.getData().toString(),TPYmCallback.class);
			tPYmCallback.setCreatorTime(new Date().getTime());
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					TPYmCallback old=tPYmCallbackDao.selectCallbackId(tPYmCallback.getCoinCallbackId());
					if(StringUtil.isNullOrEmpty(old)) {
					   int i=coinAddRemark(tPYmCallback.getCoinCount(),tPYmCallback.getUserId(),ConstantUtil.COIN_CHANGED_TYPE_26,"阅盟小说");
					   if(i>0) {
						   tPYmCallback.setState(1);
						   tPYmCallbackDao.insert(tPYmCallback);
					   }else {
						   tPYmCallback.setState(2);
						   tPYmCallbackDao.insert(tPYmCallback);
					   }
					}else {
						if(old.getState().intValue()==2) {
							int i=coinAddRemark(tPYmCallback.getCoinCount(),tPYmCallback.getUserId(),ConstantUtil.COIN_CHANGED_TYPE_26,"阅盟小说");
							if(i>0) {
							   tPYmCallbackDao.update(tPYmCallback.getId(), 1);
						    }else {
							   tPYmCallbackDao.update(tPYmCallback.getId(), 2);
						    }
						}
					}
					return "1";
				}
			}.execute(tPYmCallback.getUserId(),"redis_lock_ymCallback");
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.cashTask")  
	public void cashTask(String text) {  
		logger.info("监听到bz.queue.taskCash队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.cashTask",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			String outTradeNo=obj.getString("outTradeNo");
			Integer cashId=Integer.parseInt(obj.getString("cashId"));
			LCoinChange lCoinChange=JSON.parseObject(obj.getString("lCoinChange").toString(),LCoinChange.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					String res="1";
					int tradeCount=lUserExchangeCashDao.selectTradeCount(outTradeNo);
					if(tradeCount>0) {
						return "2";//订单已存在
					}
					LUserCash lUserCash=lUserCashDao.selectId(cashId, lCoinChange.getUserId());
					if(lUserCash.getState()!=2) {
						return "4";//任务未完成
					}
					MUserInfo mUserInfo=mUserInfoDao.selectOne(lCoinChange.getUserId());
					long coin_to_money = new Long(pDictionaryDao.selectByName("coin_to_money").getDicValue());
					if(mUserInfo.getCoin()<lCoinChange.getAmount()) {
						return "2";//金币不足
					}
					long now = new Date().getTime();
					List<LCoinChange> lCoinChanges = lCoinChangeDao.selectByChangeType(lCoinChange.getUserId(), lCoinChange.getChangedType());
					if(lCoinChanges.size() == 0){
						res="3"; // 是首次提现
					}
					// 开始抵扣，插入金币申请表，发送消息
					mUserInfoDao.updatecCoinReduce(lCoinChange.getAmount(), lCoinChange.getUserId());
					lCoinChange.setChangedTime(now);
					lCoinChange.setCoinBalance(mUserInfo.getCoin()-lCoinChange.getAmount());
					lCoinChangeDao.insertCoin(lCoinChange);
					LUserExchangeCash lUserExchangeCash=new LUserExchangeCash();
					int actualAmount=(int)(lCoinChange.getAmount()/coin_to_money);
					lUserExchangeCash.setCoinChangeId(lCoinChange.getId());
					lUserExchangeCash.setUserId(lCoinChange.getUserId());
					lUserExchangeCash.setOutTradeNo(outTradeNo);
					lUserExchangeCash.setCoin(lCoinChange.getAmount());
					lUserExchangeCash.setActualAmount(new BigDecimal(actualAmount));
					lUserExchangeCash.setServiceCharge(new BigDecimal(lCoinChange.getAmount()/10000.0-lCoinChange.getAmount()/coin_to_money));
					lUserExchangeCash.setCoinToMoney((int)coin_to_money);
					lUserExchangeCash.setCreatorTime(new Date().getTime());
					lUserExchangeCash.setState(1);
					lUserExchangeCash.setCoinBalance(mUserInfo.getCoin());
					lUserExchangeCash.setUserIp(lCoinChange.getUserIp());
					lUserExchangeCash.setRealName(mUserInfo.getUserName());	
					lUserExchangeCash.setCashType(2);
					if(lCoinChange.getAccountType().intValue()==2) {
						lUserExchangeCash.setBankAccount(mUserInfo.getAliNum());
					}else if(lCoinChange.getAccountType().intValue()==1) {
						lUserExchangeCash.setBankAccount(mUserInfo.getOpenId());
					}
					/*result=cashTask(actualAmount,flag,mUserInfo,now,lCoinChange,lUserExchangeCash.getId());*/
					int money=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CASH_QUOTA).getDicValue());
					if(actualAmount<=money) {
						/*jmsProducer.userCash(Type.CASH_PAY, lUserExchangeCash.getId());	*/
						lUserExchangeCash.setIsLocking(1);
						lUserExchangeCash.setLockingMobile("1");
					}else {
						lUserExchangeCash.setIsLocking(2);
					}
					lUserExchangeCashDao.insert(lUserExchangeCash);
					lUserCash.setState(3);
					lUserCashDao.update(lUserCash);
					return res;
				}
				
			}.execute(lCoinChange.getUserId(),"redis_lock_taskCash");
			if(!"2".equals(res)) {
				if(lCoinChange.getAccountType().intValue()==1) {
					completeTask(lCoinChange.getUserId(),22);
				}else {
					completeTask(lCoinChange.getUserId(),21);
				}
			}
			if("3".equals(res)) {
				MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(lCoinChange.getUserId());
				if(!StringUtil.isNullOrEmpty(mUserApprentice) && mUserApprentice.getApprenticeType().intValue()==1) {
					userFirstWithdrawals(mUserApprentice.getReferrerId(),mUserApprentice.getUserId());
				}
				/*MUserInfo mUserInfo=mUserInfoDao.selectOne(lCoinChange.getUserId());
				if(StringUtils.isNotEmpty(mUserInfo.getReferrer())){
					userFirstWithdrawals(mUserInfo.getReferrer(),mUserInfo.getUserId());
	    		}*/
		    }
			
			
		    	
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	} 
	
	@JmsListener(destination = "bz.queue.signTask")  
	public void signTask(String text) {  
		logger.info("监听到bz.queue.signTask队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.signTask",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			String userId=jmsWrapper.getData().toString();
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					LUserSign lUserSign=lUserSignDao.selectUserId(userId);
					if(lUserSign.getIsTask().intValue()==2) {
						return "3";//奖励已领取
					}
					PDictionary lotteryDictionary=pDictionaryDao.selectByName(DictionaryUtil.SIGN_EXTRA_COIN);
					int res=coinAdd(Long.parseLong(lotteryDictionary.getDicValue()),userId,ConstantUtil.COIN_SIGN_TASK_TYPE_29);
					if(res>0) {
						lUserSign.setIsTask(2);
						lUserSign.setTaskCoin(Long.parseLong(lotteryDictionary.getDicValue()));
						lUserSignDao.update(lUserSign);
					}
					return "2";
				}
			}.execute(userId,"redis_lock_signTask");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.videoTask")  
	public void videoTask(String text) { 
		logger.info("监听到bz.queue.videoTask队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.videoTask",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			TpVideoCallback tpVideo=JSON.parseObject(jmsWrapper.getData().toString(),TpVideoCallback.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					TpVideoCallback video=tpVideoDao.selectOne(tpVideo.getTrans_id());
					if(!StringUtil.isNullOrEmpty(video)) {
						return "1";//奖励已领取
					}
					tpVideo.setCreatorTime(new Date().getTime());
					int i=tpVideoDao.save(tpVideo);
					if(tpVideo.getState().intValue()==1 && i>0) {
						coinAdd(tpVideo.getReward_amount(),userId,ConstantUtil.COIN_SIGN_TASK_TYPE_30);
					}
					return "2";
				}
			}.execute(tpVideo.getUser_id(),"redis_lock_videoTask");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	@Transactional
	@JmsListener(destination = "bz.queue.smashEggs")  
	public void smashEggs(String text) { 
		logger.info("监听到bz.queue.smashEggs队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.smashEggs",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			EGoleEggOrder eGoleEggOrder=JSON.parseObject(jmsWrapper.getData().toString(),EGoleEggOrder.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					MUserInfo user=mUserInfoDao.selectOne(userId);
					if(eGoleEggOrder.getPigCoin()>user.getPigCoin()) {
						return "1";//金猪不足
					}
					EUserGoldEgg eUserGoldEgg=eUserGoldEggDao.selectOne(userId);
					int count=eGoleEggOrderDao.selectUserCount(userId);
					if(count>=eUserGoldEgg.getFrequency()) {
						return "2";//次数不足
					}
					long maxId=eGoleEggOrderDao.selectMaxId()+1;
					if(maxId==1) {
						eGoleEggOrder.setCardNumber(eGoleEggOrder.getCardNumber()+"100000012001"+""+StringUtil.getRandomString(2));
					}else {
						eGoleEggOrder.setCardNumber(eGoleEggOrder.getCardNumber()+maxId+""+StringUtil.getRandomString(2));
					}
					long time=new Date().getTime();
					eGoleEggOrder.setCreatorTime(time);
					int i=pigReduce(eGoleEggOrder.getPigCoin(),userId,ConstantUtil.PIG_CHANGED_TYPE_13);
					if(i>0) {
						eGoleEggOrderDao.add(eGoleEggOrder);	
					}
					return "3";
				}
			}.execute(eGoleEggOrder.getUserId(),"redis_lock_smashEggs");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	@Transactional
	@JmsListener(destination = "bz.queue.activationCard")  
	public void activationCard(String text) { 
		logger.info("监听到bz.queue.activationCard队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.activationCard",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
			long id=Long.parseLong(obj.getString("id"));
			String userId=obj.getString("userId");
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					EGoleEggOrder order=eGoleEggOrderDao.selectOne(id);
					if(order.getState().intValue()==2 || order.getIsProhibit()==2) {
						return "1";//激活卡无效
					}
					order.setExchangeUserId(userId);
					order.setExchangeTime(new Date().getTime());
					order.setState(2);
                    
					int i=eGoleEggOrderDao.update(order);
					if(i>0) {
						pigAdd(order.getObtainPigCoin(),userId,ConstantUtil.PIG_CHANGED_TYPE_14);
					}
					return "3";
				}
			}.execute(userId,"redis_lock_activationCard");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.enroll")  
	public void enroll(String text) {  
		logger.info("监听到bz.queue.enroll队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.enroll",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			CCheckinLog cCheckinLog=JSON.parseObject(jmsWrapper.getData().toString(),CCheckinLog.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					CCheckinLog checkinLog=cCheckinLogDao.selectCreate(userId);//今日报名打卡记录
					if(!StringUtil.isNullOrEmpty(checkinLog)) {
						return "1";//今日已报名
					}
					MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
					if(mUserInfo.getCoin().intValue()<cCheckinLog.getPayCoin()) {
						return "2";//金币不足
					}
					int res=coinReduce(cCheckinLog.getPayCoin(),userId,ConstantUtil.COIN_CHANGED_TYPE_32);
					if(res>0) {
						cCheckinLogDao.add(cCheckinLog);
					}
					return "3";
				}
			}.execute(cCheckinLog.getUserId(),"redis_lock_enroll");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.modifyCardPassword")  
	public void modifyCardPassword(String text) { 
		logger.info("监听到bz.queue.modifyCardPassword队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.modifyCardPassword",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			EGoleEggOrder eGoleEggOrder=JSON.parseObject(jmsWrapper.getData().toString(),EGoleEggOrder.class);
			long time=new Date().getTime();
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					EGoleEggOrder order=eGoleEggOrderDao.selectCard(eGoleEggOrder.getCardNumber());
					if(!StringUtil.isNullOrEmpty(order)) {
						return "2";
					}
					eGoleEggOrder.setCreatorTime(time);
					eGoleEggOrderDao.add(eGoleEggOrder);
					return "3";
				}
			}.execute(eGoleEggOrder.getUserId(),"redis_lock_modifyCardPassword");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	public void userFirstWithdrawals(String referrerId,String userId) {
		/* 徒弟首次提现 */
		long time=new Date().getTime();
    	MUserInfo referrer = mUserInfoDao.selectOne(referrerId); // 师父信息
    	String channelCode = referrer.getChannelCode();
    	if(StringUtil.isNullOrEmpty(channelCode)) {
    		channelCode=referrer.getParentChannelCode();
    	}
    	//String channelCode = referrer.getChannelCode() == null?referrer.getParentChannelCode():referrer.getChannelCode();
    	if(StringUtils.isBlank(channelCode)){
    		channelCode = "baozhu";
    	}
		MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
		MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), referrer.getRoleType() > 1?2:1); // 获取奖励方案
    	long recommendCoin = mChannelConfigUser.getRecommendCoin();
    	// 修改师徒关系为有效推荐
		MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
		mUserApprentice.setRewardStatus(2);
		mUserApprentice.setUpdateTime(time);
		if(referrer.getRoleType().intValue() != ConstantUtil.ROLE_TYPE_4){ // 如果是邀请达人，则不享受该奖励
			mUserApprentice.setContribution(mUserApprentice.getContribution()+recommendCoin);
			mUserApprenticeDao.update(mUserApprentice);
			// 修改师父账户金币
			referrer.setCoin(referrer.getCoin() + recommendCoin);
			referrer.setUpdateTime(time);
			mUserInfoDao.update(referrer);
			// 新增师父金币变动记录
			LCoinChange lCoinChange1 = new LCoinChange(referrerId, recommendCoin, ConstantUtil.FLOWTYPE_IN,
							ConstantUtil.COIN_CHANGED_TYPE_4, time,1,"推荐用户",referrer.getCoin());
			lCoinChangeDao.insert(lCoinChange1);
    	}else {
    		mUserApprenticeDao.update(mUserApprentice);
    	}
	}
	//增加金币
	public int coinAdd(long coin,String userId,Integer tyep) {
		int res=mUserInfoDao.updatecCoin(coin, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
			LCoinChange coinChange=new LCoinChange();
			coinChange.setUserId(userId);
			coinChange.setAmount(coin);
			coinChange.setFlowType(ConstantUtil.FLOWTYPE_IN);
			coinChange.setChangedType(tyep);
			coinChange.setChangedTime(new Date().getTime());
			coinChange.setCoinBalance(user.getCoin());
			lCoinChangeDao.insert(coinChange);
		}
		return res;
	}
	//增加金币
	public int coinAddRemark(long coin,String userId,Integer tyep,String remark) {
		int res=mUserInfoDao.updatecCoin(coin, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
			LCoinChange coinChange=new LCoinChange();
			coinChange.setUserId(userId);
			coinChange.setAmount(coin);
			coinChange.setFlowType(ConstantUtil.FLOWTYPE_IN);
			coinChange.setChangedType(tyep);
			coinChange.setChangedTime(new Date().getTime());
			coinChange.setCoinBalance(user.getCoin());
			coinChange.setRemarks(remark);
			lCoinChangeDao.insert(coinChange);
		}
		return res;
	}
	//减少金币
	public int coinReduce(long coin,String userId,Integer tyep) {
		int res=mUserInfoDao.updatecCoinReduce(coin, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
			LCoinChange coinChange=new LCoinChange();
			coinChange.setUserId(userId);
			coinChange.setChangedTime(new Date().getTime());
			coinChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
			coinChange.setAmount(coin);
			coinChange.setChangedType(tyep);
			coinChange.setCoinBalance(user.getCoin());
			lCoinChangeDao.insertCoin(coinChange);	
		}
		return res;
	}
	
	//增加金猪
	public int pigAdd(long pig,String userId,Integer tyep) {
		int res=mUserInfoDao.updatecPigAdd(pig, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
		    LPigChange pigChange=new LPigChange();
		    pigChange.setUserId(userId);
		    pigChange.setAmount(pig);
		    pigChange.setFlowType(1);
		    pigChange.setChangedType(tyep);
		    pigChange.setChangedTime(new Date().getTime());
		    pigChange.setPigBalance(user.getPigCoin());
		    lPigChangeDao.insert(pigChange);
			return res;
		}
		return 0;
	}
	
	//增加金猪
	public int pigAddRemark(long pig,String userId,Integer tyep,String remark) {
		int res=mUserInfoDao.updatecPigAdd(pig, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
		    LPigChange pigChange=new LPigChange();
		    pigChange.setUserId(userId);
		    pigChange.setAmount(pig);
		    pigChange.setFlowType(1);
		    pigChange.setChangedType(tyep);
		    pigChange.setChangedTime(new Date().getTime());
		    pigChange.setPigBalance(user.getPigCoin());
		    pigChange.setRemarks(remark);
		    lPigChangeDao.insert(pigChange);
			return res;
		}
		return 0;
	}
	
	//减少金猪
	public int pigReduce(long pig,String userId,Integer tyep) {
		int res=mUserInfoDao.updatecPigReduce(pig, userId);
		if(res>0) {
			MUserInfo user=mUserInfoDao.selectOne(userId);
			LPigChange pigChange=new LPigChange();
		    pigChange.setUserId(userId);
		    pigChange.setAmount(pig);
		    pigChange.setFlowType(2);
		    pigChange.setChangedType(tyep);
		    pigChange.setChangedTime(new Date().getTime());
		    pigChange.setPigBalance(user.getPigCoin());
		    lPigChangeDao.insert(pigChange);	
		}
		return res;
	}
	 
	public void completeTask(String userId,Integer task) {
		try {
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					LUserTask lUserTask=lUserTaskDao.selectOne(userId, task);
					Map<String,Object> date=new HashMap<>();
					String res="-1";
					if(StringUtil.isNullOrEmpty(lUserTask)) {
						MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(task);
						if(StringUtil.isNullOrEmpty(mTaskInfo)) {
							return "1";
						}
						date=mPassbookService.taskUsePassbook(userId, mTaskInfo.getTaskType());;
						long multiple=(long)date.get("res");
						LUserTask userTask=new LUserTask();
						userTask.setUserId(userId);
						userTask.setTaskId(task);
						userTask.setCreateTime(new Date().getTime());
						lUserTaskDao.insert(userTask);
						if(mTaskInfo.getRewardUnit()==1) {//金币奖励
							long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
							coinAdd(amount,userId,ConstantUtil.COIN_CHANGED_TYPE_10);
							res=mTaskInfo.getRemark()+"+"+amount+"金币";
						}else if(mTaskInfo.getRewardUnit()==2) {//金猪奖励
							long amount=mTaskInfo.getReward()+mTaskInfo.getReward()*multiple;
						    pigAdd(amount,userId,ConstantUtil.PIG_CHANGED_TYPE_3);
						    res=mTaskInfo.getRemark()+"+"+amount+"金猪";
						}
						mPassbookService.passbookOverdue((int)date.get("fbrspassbookId"));
				   }
				   return res;
				}
			}.execute(userId,"redis_lock_task");
			if(!"-1".equals(res)) {
				AppNewsInform appNewsInform=new AppNewsInform();
				appNewsInform.setUserId(userId);
				appNewsInform.setInformTitle(InformConstant.COMPLETE_TASK_TITLE);
				appNewsInform.setInformContent(res);	
				appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
				appNewsInformService.addPush(appNewsInform);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@JmsListener(destination = "bz.queue.darenRecommend")  
	public void darenRecommend(String text) { 
		logger.info("监听到bz.queue.darenRecommend队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.darenRecommend",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			String userId = jmsWrapper.getData().toString();
			MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
			if(mUserApprentice != null){
				if(mUserApprentice.getRewardStatus().intValue() == 1 && mUserApprentice.getApprenticeType().intValue() == 2){ // 未给奖励
					mUserApprentice.setRewardStatus(2);
					MUserInfo mUserInfo = mUserInfoDao.selectOne(mUserApprentice.getReferrerId());
					if(mUserInfo.getRoleType().intValue() == ConstantUtil.ROLE_TYPE_4){
						String channelCode = StringUtil.isNullOrEmpty(mUserInfo.getChannelCode())?mUserInfo.getParentChannelCode():mUserInfo.getChannelCode();
						if(StringUtils.isBlank(channelCode)){
				    		channelCode = "baozhu";
				    	}
						MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
						MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), 1); // 获取奖励方案
						long darenCoin = mChannelConfigUser.getDarenCoin();
						mUserInfo.setCoin(mUserInfo.getCoin() + darenCoin);
						LCoinChange lCoinChange1 = new LCoinChange(mUserInfo.getUserId(), darenCoin, ConstantUtil.FLOWTYPE_IN,
								ConstantUtil.COIN_CHANGED_TYPE_4, jmsWrapper.getCreatetime(),1,"推荐用户",mUserInfo.getCoin());
						// 插入金币金额
						lCoinChangeDao.insert(lCoinChange1);
						// 修改账户余额
						mUserInfoDao.update(mUserInfo);
						// 修改邀请状态
						mUserApprentice.setContribution(mUserApprentice.getContribution() + darenCoin);
						mUserApprenticeDao.update(mUserApprentice);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}
	
	@JmsListener(destination = "bz.queue.wish.cash")  
	public void wishReceiveCash(String text) {  
		logger.info("监听到bz.queue.wish.cash队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.wish.cash",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			LCoinChange lCoinChange=JSON.parseObject(jmsWrapper.getData().toString(),LCoinChange.class);
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					String res="1";
					MUserInfo mUserInfo=mUserInfoDao.selectOne(lCoinChange.getUserId());
					long coin_to_money = new Long(pDictionaryDao.selectByName("coin_to_money").getDicValue());
					if(mUserInfo.getCoin()<lCoinChange.getAmount()) {
						return "2";//金币不足
					}
					long now = new Date().getTime();
					List<LCoinChange> lCoinChanges = lCoinChangeDao.selectByChangeType(lCoinChange.getUserId(), lCoinChange.getChangedType());
					if(lCoinChanges.size() == 0){
						res="3"; // 是首次提现
					}
					// 开始抵扣，插入金币申请表，发送消息
					mUserInfoDao.updatecCoinReduce(lCoinChange.getAmount(), lCoinChange.getUserId());
					lCoinChange.setChangedTime(now);
					lCoinChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
					lCoinChange.setAmount(lCoinChange.getAmount());
					lCoinChange.setStatus(2);
					lCoinChange.setCoinBalance(mUserInfo.getCoin()-lCoinChange.getAmount());
					lCoinChangeDao.insertCoin(lCoinChange);
					String outTradeNo = Base64.getOutTradeNo();
					LUserExchangeCash lUserExchangeCash=new LUserExchangeCash();
					//int actualAmount=(int)(lCoinChange.getAmount()/coin_to_money);
					lUserExchangeCash.setCoinChangeId(lCoinChange.getId());
					lUserExchangeCash.setUserId(lCoinChange.getUserId());
					lUserExchangeCash.setOutTradeNo(outTradeNo+"1");
					lUserExchangeCash.setCoin(lCoinChange.getAmount());
					lUserExchangeCash.setActualAmount(new BigDecimal(lCoinChange.getActualAmountM()));
					lUserExchangeCash.setServiceCharge(new BigDecimal(lCoinChange.getAmount()/10000.0-lCoinChange.getActualAmountM()));
					lUserExchangeCash.setCoinToMoney((int)coin_to_money);
					lUserExchangeCash.setCreatorTime(new Date().getTime());
					lUserExchangeCash.setState(1);
					lUserExchangeCash.setCoinBalance(mUserInfo.getCoin());
					lUserExchangeCash.setUserIp(lCoinChange.getUserIp());
					lUserExchangeCash.setCashType(1);

					lUserExchangeCash.setRealName(mUserInfo.getUserName());	
					
					if(lCoinChange.getAccountType().intValue()==2) {
						lUserExchangeCash.setBankAccount(mUserInfo.getAliNum());
					}else if(lCoinChange.getAccountType().intValue()==1) {
						lUserExchangeCash.setBankAccount(mUserInfo.getOpenId());
					}
					/*result=cashTask(actualAmount,flag,mUserInfo,now,lCoinChange,lUserExchangeCash.getId());*/
					int money=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CASH_QUOTA).getDicValue());
					if(lCoinChange.getActualAmountM()<=money) {
						/*jmsProducer.userCash(Type.CASH_PAY, lUserExchangeCash.getId());	*/
						lUserExchangeCash.setIsLocking(1);
						lUserExchangeCash.setLockingMobile("1");
					}else {
						lUserExchangeCash.setIsLocking(2);
					}
					lUserExchangeCashDao.insert(lUserExchangeCash);
					return res;
				}
				
			}.execute(lCoinChange.getUserId(),"redis_lock_wish_cash");
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	} 
	
	
	@JmsListener(destination = "bz.queue.wishReceiveQueue")  
	public void wishReceiveQueue(String text) { 
		logger.info("监听到bz.queue.wishReceiveQueue队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.wishReceiveQueue",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			JSONObject obj=(JSONObject) JSON.parse(jmsWrapper.getData().toString());
	    	String referrerId= obj.getString("referrerId");
	    	String userId= obj.getString("userId");
	    	
			String res=new AWorker<String>() {
				@Override
				public String call(String referrerId) throws Exception {
					MUserApprentice userApprentice=mUserApprenticeDao.selectUserId(userId);
					if(!StringUtil.isNullOrEmpty(userApprentice)) {
						return "2";
					}
					MUserInfo referrer = mUserInfoDao.selectOne(referrerId);
					MUserApprentice mUserApprentice = new MUserApprentice();
					mUserApprentice.setReferrerId(referrerId);
					mUserApprentice.setUserId(userId);
					mUserApprentice.setCreateTime(jmsWrapper.getCreatetime());
					mUserApprentice.setUpdateTime(jmsWrapper.getCreatetime());
					mUserApprentice.setContribution(0l);
					mUserApprentice.setRewardStatus(1);
					mUserApprentice.setApprenticeType(1);
					
					// 修改师傅的徒弟数量
					referrer.setApprentice(referrer.getApprentice()+1);
					referrer.setUpdateTime(jmsWrapper.getCreatetime());
					
					mUserApprenticeDao.insert(mUserApprentice);
					
					mUserInfoDao.update(referrer);
					
					long time=new Date().getTime();
					UserWishValue userWishValue=new UserWishValue();
					userWishValue.setUserId(referrerId);
				    userWishValue.setWishValue(1);
				    userWishValue.setUpdateTime(time);
				    userWishValueDao.update(userWishValue);
				    WishGain wishGain=new WishGain();
				    wishGain.setUserId(referrerId);
				    wishGain.setFlowType(1);
				    wishGain.setMode(11);
				    wishGain.setNumber(1);
				    wishGain.setCreateTime(time);
				    wishGain.setSign(referrerId+time);
				    wishGainDao.insert(wishGain);
					
					return "1";
				}
			}.execute(referrerId,"redis_lock_wishReceiveQueue");
			
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
	    logger.info("消息处理结束，等待下一次消息");
	}

	@JmsListener(destination = "bz.queue.high_vip_reward")  
	public void highVipReward(String text) { 
		logger.info("监听到bz.queue.high_vip_reward队列消息，并开始处理。。。。。");
		JmsNewsLog jmsNewsLog=new JmsNewsLog(text,"bz.queue.high_vip_reward",1,new Date().getTime());
		try {
			JmsWrapper jmsWrapper = JSON.parseObject(text, JmsWrapper.class);
			logger.info(jmsWrapper.getType().toString());
			String userId = jmsWrapper.getData().toString();
			String res=new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					/**
					 *  1、普通用户奖励模式：邀请1个用户开通高额赚VIP，奖励现金300元，直推30个用户开通高额赚VIP 即可自动升级小客服；
					 *	2、小客服奖励模式：直推1个用户开通高额赚VIP，奖励现金350元，直推5个小客服即可自动升级为合伙人；
					 *	3、合伙人奖励模式：直推1个用户开通高额赚VIP，奖励现金380元。
					 */
					MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
					// 金币变更类型 41-直接好友开通VIP奖励 42-间接好友开通VIP奖励
					if(mUserInfo.getHighRole().intValue() == 1){ // 奖励模式：邀请1个用户开通高额赚VIP，奖励现金300元；
						// 给300奖励
						long rewardFirst = 300 * 11000l;
						mUserInfo.setCoin(mUserInfo.getCoin() + rewardFirst);
						mUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
						LCoinChange lCoinChange = new LCoinChange(userId, rewardFirst, 1, 41, jmsWrapper.getCreatetime(), 1, "好友开通高额赚VIP奖励", mUserInfo.getCoin());
						lCoinChangeDao.insert(lCoinChange);
						// 查询名下有多少个人购买了高额赚VIP
						long highVipCount = lUserVipDao.selectOpenHighVipCount(userId);
						if(highVipCount >= 30){// 是否升级为小客服
							mUserInfo.setHighRole(2);
						}
						mUserInfoDao.update(mUserInfo);
					}else if(mUserInfo.getHighRole().intValue() == 2){
						// 奖励现金350元
						long rewardFirst = 350 * 11000l;
						mUserInfo.setCoin(mUserInfo.getCoin() + rewardFirst);
						mUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
						LCoinChange lCoinChange = new LCoinChange(userId, rewardFirst, 1, 41, jmsWrapper.getCreatetime(), 1, "好友开通高额赚VIP奖励", mUserInfo.getCoin());
						lCoinChangeDao.insert(lCoinChange);
						// 查询名下有多少个小客服
						long kfCount = mUserInfoDao.selectHighVipXKFCount(userId);
						if(kfCount >= 5){// 是否升级为合伙人
							mUserInfo.setHighRole(3);
						}
						mUserInfoDao.update(mUserInfo);
					}else if(mUserInfo.getHighRole().intValue() == 3){
						// 奖励现金380元
						long rewardFirst = 380 * 11000l;
						mUserInfo.setCoin(mUserInfo.getCoin() + rewardFirst);
						mUserInfo.setUpdateTime(jmsWrapper.getCreatetime());
						LCoinChange lCoinChange = new LCoinChange(userId, rewardFirst, 1, 41, jmsWrapper.getCreatetime(), 1, "好友开通高额赚VIP奖励", mUserInfo.getCoin());
						lCoinChangeDao.insert(lCoinChange);
						mUserInfoDao.update(mUserInfo);
					}
					/**
					 * 2、小客服奖励模式：间接推1个用户开通高额赚VIP ，奖励50元，直推5个小客服即可自动升级为合伙人；
					 * 3、合伙人奖励模式：间接推1个用户开通高额赚VIP ，奖励80元。
					 */
					if(!StringUtil.isNullOrEmpty(mUserInfo.getReferrer())){
						// 给上级奖励
						MUserInfo refferrer = mUserInfoDao.selectOne(mUserInfo.getReferrer());
						if(refferrer.getHighRole().intValue() == 1){
							// 查询名下有多少个小客服
							long kfCount = mUserInfoDao.selectHighVipXKFCount(refferrer.getUserId());
							if(kfCount >= 5){// 是否升级为合伙人
								refferrer.setHighRole(3);
							}
							mUserInfoDao.update(refferrer);
						}else if(refferrer.getHighRole().intValue() == 2){
							// 奖励现金50元
							long rewardFirst = 50 * 11000l;
							refferrer.setCoin(refferrer.getCoin() + rewardFirst);
							refferrer.setUpdateTime(jmsWrapper.getCreatetime());
							LCoinChange lCoinChange = new LCoinChange(refferrer.getUserId(), rewardFirst, 1, 42, jmsWrapper.getCreatetime(), 1, "间接好友开通高额赚VIP奖励", refferrer.getCoin());
							lCoinChangeDao.insert(lCoinChange);
							// 查询名下有多少个小客服
							long kfCount = mUserInfoDao.selectHighVipXKFCount(refferrer.getUserId());
							if(kfCount >= 5){// 是否升级为合伙人
								refferrer.setHighRole(3);
							}
							mUserInfoDao.update(refferrer);
						}else if(refferrer.getHighRole().intValue() == 3){
							// 奖励现金80元
							long rewardFirst = 80 * 11000l;
							refferrer.setCoin(refferrer.getCoin() + rewardFirst);
							refferrer.setUpdateTime(jmsWrapper.getCreatetime());
							LCoinChange lCoinChange = new LCoinChange(refferrer.getUserId(), rewardFirst, 1, 42, jmsWrapper.getCreatetime(), 1, "间接好友开通高额赚VIP奖励", refferrer.getCoin());
							lCoinChangeDao.insert(lCoinChange);
							mUserInfoDao.update(refferrer);
						}
					}
					return "1";
				}
			}.execute(userId,"bz.queue.high_vip_reward_"+userId);
			if("1".equals(res)){
				logger.info("消息处理结束，处理成功！！！");
			}
		}catch (Exception e) {
			e.printStackTrace();
			jmsNewsLog.setState(2);
			jmsNewsLog.setJmsException(e.toString());
		}finally {
			jmsNewsLogDao.insert(jmsNewsLog);
		}
		logger.info("消息处理结束，等待下一次消息");
	}
	
}
