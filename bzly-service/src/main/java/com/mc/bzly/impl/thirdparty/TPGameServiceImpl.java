package com.mc.bzly.impl.thirdparty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.Base64;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PApplicationMarketDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.platform.PLevelDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.thirdparty.BZCallbackDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigDao;
import com.mc.bzly.dao.thirdparty.MChannelConfigUserDao;
import com.mc.bzly.dao.thirdparty.MChannelInfoDao;
import com.mc.bzly.dao.thirdparty.MDarenRewardDao;
import com.mc.bzly.dao.thirdparty.PCDDCallbackDao;
import com.mc.bzly.dao.thirdparty.TPCallbackDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.thirdparty.TPInterfaceDao;
import com.mc.bzly.dao.thirdparty.TPParamsDao;
import com.mc.bzly.dao.thirdparty.XWCallbackDao;
import com.mc.bzly.dao.thirdparty.YoleCallbackDao;
import com.mc.bzly.dao.user.LCashGameTaskDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LDarenRewardDao;
import com.mc.bzly.dao.user.LDarenRewardDetailDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LUserCashDao;
import com.mc.bzly.dao.user.LUserGameDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.dao.user.LUserSearchLogDao;
import com.mc.bzly.dao.user.LUserSignGameDao;
import com.mc.bzly.dao.user.LUserSigninDao;
import com.mc.bzly.dao.user.LUserStatisticDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.LUserTptaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.dao.wish.GameRewardRateDao;
import com.mc.bzly.dao.wish.UserFhxLogDao;
import com.mc.bzly.dao.wish.UserFhxXyxDao;
import com.mc.bzly.dao.wish.UserWishValueDao;
import com.mc.bzly.dao.wish.UserXyxLogDao;
import com.mc.bzly.dao.wish.WishGainDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.jms.JmsWrapper;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.platform.PApplicationMarket;
import com.mc.bzly.model.platform.PDictionary;
import com.mc.bzly.model.platform.PLevel;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.model.thirdparty.MChannelConfig;
import com.mc.bzly.model.thirdparty.MChannelConfigUser;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.model.thirdparty.MDarenReward;
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.model.thirdparty.TPCallback;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.thirdparty.TPInterface;
import com.mc.bzly.model.thirdparty.TPParams;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.model.thirdparty.YoleCallback;
import com.mc.bzly.model.user.LCashGameTask;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LDarenReward;
import com.mc.bzly.model.user.LDarenRewardDetail;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.LUserCash;
import com.mc.bzly.model.user.LUserGame;
import com.mc.bzly.model.user.LUserGameTask;
import com.mc.bzly.model.user.LUserSearchLog;
import com.mc.bzly.model.user.LUserSignGame;
import com.mc.bzly.model.user.LUserSignin;
import com.mc.bzly.model.user.LUserStatistic;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.model.wish.GameRewardRate;
import com.mc.bzly.model.wish.UserFhxLog;
import com.mc.bzly.model.wish.UserFhxXyx;
import com.mc.bzly.model.wish.UserWishValue;
import com.mc.bzly.model.wish.UserXyxLog;
import com.mc.bzly.model.wish.WishGain;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.thirdparty.TPGameService;
import com.mc.bzly.util.SignUtil;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = TPGameService.class,version = WebConfig.dubboServiceVersion)
public class TPGameServiceImpl implements TPGameService {

	@Autowired 
	private TPGameDao tpGameDao;

	@Autowired 
	private TPParamsDao tpParamsDao;
	
	@Autowired 
	private LUserGameDao lUserGameDao;
	
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	
	@Autowired
	private LUserVipDao lUserVipDao;
	
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;

	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	
	@Autowired
	private YoleCallbackDao yoleCallbackDao;
	
	@Autowired
	private PLevelDao pLevelDao;
	
	@Autowired
	private LUserTaskDao lUserTaskDao;
	
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	
	@Autowired
	private LUserStatisticDao lUserStatisticDao;
	
	@Autowired
	private AppNewsInformService appNewsInformService;
	
	@Autowired
	private LPigChangeDao lPigChangeDao;
	
	@Autowired
	private PCDDCallbackDao pcddCallbackDao;
	
	@Autowired
	private PApplicationMarketDao pApplicationMarketDao;
	
	@Autowired
	private XWCallbackDao xwCallbackDao;
	
	@Autowired
	private TPCallbackDao tpCallbackDao;
	
	@Autowired
	private MChannelInfoDao mChannelInfoDao;
	
	@Autowired
	private MChannelConfigDao mChannelConfigDao;
	
	@Autowired
	private MChannelConfigUserDao mChannelConfigUserDao;
	
	@Autowired
	private LUserSignGameDao lUserSignGameDao;
	
	@Autowired
	private LUserSigninDao lUserSigninDao;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	TPInterfaceDao tpInterfaceDao;
	@Autowired
	private BZCallbackDao bzCallbackDao;
	@Autowired
	private MDarenRewardDao mDarenRewardDao;
	@Autowired
	private LDarenRewardDao lDarenRewardDao;
	@Autowired
	private LDarenRewardDetailDao lDarenRewardDetailDao;
	@Autowired
	private LUserGameTaskDao lUserGameTaskDao;
	@Autowired
	private LUserCashDao lUserCashDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserTptaskDao lUserTptaskDao;
	@Autowired
    private TPCallbackDao tPCallbackDao;
	@Autowired
	private LCashGameTaskDao lCashGameTaskDao;
	@Autowired
	private LUserSearchLogDao lUserSearchLogDao;
	@Autowired
	private UserWishValueDao userWishValueDao;
	@Autowired
	private WishGainDao wishGainDao;
	@Autowired
	private GameRewardRateDao gameRewardRateDao;
	@Autowired
	private UserFhxLogDao userFhxLogDao;
	@Autowired
	private UserFhxXyxDao userFhxXyxDao;
	@Autowired
	private UserXyxLogDao userXyxLogDao;
	
	@Transactional
	@Override
	public int add(TPGame tpGame) throws Exception {
		tpGameDao.insert(tpGame);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPGame tpGame) throws Exception {
		int i=tpGameDao.selectOrderId(tpGame);
		if(i>0) {
			return 2;//序号已存在
		}
		tpGameDao.update(tpGame);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		tpGameDao.delete(id);
		return 1;
	}

	@Override
	public Map<String,Object> queryOne(Integer id) {
		return tpGameDao.selectHtOne(id);
	}

	@Override
	public PageInfo<TPGame> queryList(TPGame tpGame,Integer marketId) {
		List<TPGame> tpGameList = new ArrayList<TPGame>();
		PageHelper.startPage(tpGame.getPageNum(), tpGame.getPageSize());
		if(StringUtil.isNullOrEmpty(marketId)) {
			tpGameList = tpGameDao.selectList(tpGame);
		}else {
			PApplicationMarket pApplicationMarket=pApplicationMarketDao.selectOne(marketId);
			if(pApplicationMarket.getState()==3) {
				tpGameList = tpGameDao.selectList(tpGame);
			}
		}
		return new PageInfo<>(tpGameList);	
	}
	
	@Override
	public String buildUrl(Integer id,Map<String, String> respMap) {
		TPGame tpGame = tpGameDao.selectOne(id);
		TPInterface tpInterface = tpInterfaceDao.selectOne(tpGame.getInterfaceId());
		if("channelOnLineAdids".equals(tpInterface.getInterfaceCode())
				|| "flowGameList".equals(tpInterface.getInterfaceCode())){
			respMap.put("adid", tpGame.getGameId()+"");
			respMap.put("ptype", "1");
			if("1".equals(respMap.get("equipmentType"))){
				respMap.put("ptype", "2");
			}
		}
		String url = tpGame.getUrl();
		List<TPParams> tpParamList = tpParamsDao.selectListByInterface(tpGame.getInterfaceId());
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		for (TPParams tpPara : tpParamList) {
			if(tpPara.getType().intValue() == 1){
				if(tpPara.getIsReplace().intValue() == 1){
					url = url.replace(tpPara.getValue().toString(), respMap.get(tpPara.getReplaceCode()));
					tpPara.setValue(respMap.get(tpPara.getReplaceCode()));
				}
			}
			if(tpPara.getInfoEncryptNeed().intValue() == 1){ // 是否加密所需字段
				encrpty.add(tpPara.getValue());
			}
			if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
				if(tpPara.getEncryptType().intValue() == 1){
					if(encrpty.size() > 0){
						String signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							url = url.replace(tpPara.getValue(), signStr);
						}
					}
				}
			}
		}
		// 插入我的试玩，如果已存在，则更新我的试玩
		LUserGame lUserGame = lUserGameDao.selectOne(respMap.get("userId"),id);
		if(lUserGame != null){
			lUserGame.setModifyTime(new Date().getTime());
			lUserGameDao.update(lUserGame);
		}else{
			lUserGame = new LUserGame();
			lUserGame.setGameId(id);
			lUserGame.setUserId(respMap.get("userId"));
			lUserGame.setModifyTime(new Date().getTime());
			lUserGameDao.insert(lUserGame);
		}
		return url;
	}

	@Override
	public void yoleCallbackInsert(YoleCallback yoleCallback,long totalReward){
		yoleCallbackDao.insert(yoleCallback);
		if(yoleCallback.getStatus() == 1){
			try{
				TPCallback tpCallback = new TPCallback();
				tpCallback.setCreateTime(new Date().getTime());
				tpCallback.setStatus(yoleCallback.getStatus());
				tpCallback.setTpName("麦游游戏");
				tpCallback.setGameId(yoleCallback.getGmid());
				tpCallback.setGameName(yoleCallback.getGmname());
				tpCallback.setOrderNum(yoleCallback.getYoleid());
				tpCallback.setGameReward(yoleCallback.getGmgold());
				tpCallback.setReward(totalReward+"");
				tpCallback.setTpGameId(yoleCallback.getUserid());
				tpCallback.setUserId(yoleCallback.getAppid());
				MUserInfo mUserInfo = mUserInfoDao.selectOne(yoleCallback.getAppid());
				if(mUserInfo.getChannelCode() != null  && (!"".equals(mUserInfo.getChannelCode()))){
					MChannelInfo mChannelInfo = mChannelInfoDao.selectByChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelName(mChannelInfo.getChannelName());
				}else{
					tpCallback.setChannelCode("baozhu");
					tpCallback.setChannelName("宝猪乐园");
				}
				tpCallbackDao.insert(tpCallback);
				gameStatistic(mUserInfo);//用户完成游戏统计
				TPGame tpGame = tpGameDao.selectByGameInterface(Integer.valueOf(yoleCallback.getGmid()), 1);
				if(tpGame != null){
					updateSignGame(yoleCallback.getAppid(), tpGame.getId(),totalReward);
				}
	
				//完成提现或者领取活跃金任务
				LUserGameTask gameTask=lUserGameTaskDao.selectGameOne2(yoleCallback.getUserid(), tpGame.getId());
				if(!StringUtil.isNullOrEmpty(gameTask) && gameTask.getState().intValue()==1) {
					/*int count=0;
					if(gameTask.getTaskType().intValue()==2){
						count=lUserGameTaskDao.selectCashCount(gameTask.getCashId());
					}*/
					gameTask.setState(2);
					gameTask.setMoney(Long.parseLong(yoleCallback.getGmgold())/10000.0);
					lUserGameTaskDao.update(gameTask);
					/*if(gameTask.getTaskType().intValue()==2){
					   if(count+1>=3) {
						   LUserCash lUserCash=lUserCashDao.selectId(gameTask.getCashId(), yoleCallback.getUserid());
						   if(lUserCash.getState().intValue()==1) {
							   lUserCash.setState(2);
							   lUserCashDao.update(lUserCash);
						   }
					   }
					}*/
				}
				long time=new Date().getTime();
				if(totalReward>=4000) {
					LUserCash cashLog=lUserCashDao.selectStateOne(yoleCallback.getUserid());
					if(StringUtil.isNullOrEmpty(cashLog)) {
						LUserCash lUserCash=new LUserCash();
						String outTradeNo = Base64.getOutTradeNo();
						lUserCash.setOutTradeNo(outTradeNo+"2");
						lUserCash.setState(1);
						lUserCash.setCreatorTime(time);
						lUserCash.setUserId(yoleCallback.getUserid());
						
						String str = new AWorker<String>() {
							@Override
							public String call(String userId) throws Exception {
								LUserCash userCash=lUserCashDao.selectStateOne(lUserCash.getUserId());
								if(StringUtil.isNullOrEmpty(userCash)) {
									lUserCashDao.insert(lUserCash);
								}
								return "1";
							}
						}.execute(lUserCash.getUserId(), "redis_lock_cash_task");
						
						LCashGameTask cashGameTask=new LCashGameTask();
						cashGameTask.setCashId(lUserCash.getId());
						cashGameTask.setUserId(yoleCallback.getUserid());
						cashGameTask.setGameId(tpGame.getId());
						cashGameTask.setCreateTime(time);
						lCashGameTaskDao.add(cashGameTask);
					}else {
						if(cashLog.getState().intValue()==1) {
							LCashGameTask cashGameTask=lCashGameTaskDao.selectGameId(cashLog.getUserId(), tpGame.getId(),cashLog.getId());	
							if(StringUtil.isNullOrEmpty(cashGameTask)) {
								int count=lCashGameTaskDao.selectCount(cashLog.getId());
								if(count<3) {
									cashGameTask=new LCashGameTask();
									cashGameTask.setCashId(cashLog.getId());
									cashGameTask.setUserId(cashLog.getUserId());
									cashGameTask.setGameId(tpGame.getId());
									cashGameTask.setCreateTime(time);
									lCashGameTaskDao.add(cashGameTask);	
								}
								if(count+1>=3) {
									cashLog.setState(2);
									lUserCashDao.update(cashLog);
								}
							}
						}
					}
				}
				
				//获得心愿值
				String strWish = new AWorker<String>() {
					@Override
					public String call(String userId) throws Exception {
						WishGain wishGain=wishGainDao.selectSign(yoleCallback.getYoleid());
						if(!StringUtil.isNullOrEmpty(wishGain)) {
							return "2";
						}
						UserWishValue userWishValue=userWishValueDao.selectOne(yoleCallback.getUserid());
						if(StringUtil.isNullOrEmpty(userWishValue)) {
							userWishValue=new UserWishValue();
							userWishValue.setUserId(yoleCallback.getUserid());
							userWishValue.setWishValue(4);
							userWishValue.setUpdateTime(time);
						    userWishValueDao.insert(userWishValue);
						}else {
							userWishValue.setUpdateTime(time);
							userWishValueDao.updateAdd(userWishValue);
						}
						wishGain=new WishGain();
					    wishGain.setUserId(yoleCallback.getUserid());
					    wishGain.setMode(3);
					    wishGain.setNumber(1);
					    wishGain.setSign(yoleCallback.getYoleid());
					    wishGain.setCreateTime(time);
					    wishGainDao.insert(wishGain);
						return "1";
					}
				}.execute(yoleCallback.getUserid(), "redis_lock_add_wish"); 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public PageInfo<TPGame> queryBList(TPGame tpGame) {
		PageHelper.startPage(tpGame.getPageNum(), tpGame.getPageSize());
		List<TPGame> tpGameList = tpGameDao.selectBList(tpGame);
		return new PageInfo<>(tpGameList);
	}

	@Override
	public void pcddCallbackInsert(PCDDCallback pcDDCallback,long totalReward) {
		pcddCallbackDao.insert(pcDDCallback);
		if(pcDDCallback.getStatus() == 2){
			try{
				long baseReward = (long) (pcDDCallback.getMoney() * 10000);
				TPCallback tpCallback = new TPCallback();
				tpCallback.setCreateTime(new Date().getTime());
				tpCallback.setStatus(1);
				tpCallback.setTpName("PC蛋蛋");
				tpCallback.setGameId(pcDDCallback.getAdid()+"");
				tpCallback.setGameName(pcDDCallback.getAdname());
				tpCallback.setOrderNum(pcDDCallback.getOrdernum());
				tpCallback.setGameReward(baseReward+"");
				tpCallback.setReward(totalReward+"");
				tpCallback.setTpGameId(pcDDCallback.getMerid());
				tpCallback.setUserId(pcDDCallback.getUserid());
				MUserInfo mUserInfo = mUserInfoDao.selectOne(pcDDCallback.getUserid());
				if(mUserInfo.getChannelCode() != null  && (!"".equals(mUserInfo.getChannelCode()))){
					MChannelInfo mChannelInfo = mChannelInfoDao.selectByChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelName(mChannelInfo.getChannelName());
				}else{
					tpCallback.setChannelCode("baozhu");
					tpCallback.setChannelName("宝猪乐园");
				}
				tpCallbackDao.insert(tpCallback);
				gameStatistic(mUserInfo);//用户完成游戏统计
				TPGame tpGame = null;
				if(pcDDCallback.getPtype().intValue() == 2){
					tpGame = tpGameDao.selectByGameInterface(Integer.valueOf(pcDDCallback.getAdid()), 2);
				}else {
					tpGame = tpGameDao.selectByGameInterface(Integer.valueOf(pcDDCallback.getAdid()), 5);
				}
				if(tpGame != null){
					updateSignGame(pcDDCallback.getUserid(), tpGame.getId(),totalReward);
				}
				
				//完成提现或者领取活跃金任务
				LUserGameTask gameTask=lUserGameTaskDao.selectGameOne2(pcDDCallback.getUserid(), tpGame.getId());
				if(!StringUtil.isNullOrEmpty(gameTask) && gameTask.getState().intValue()==1) {
					/*int count=0;
					if(gameTask.getTaskType().intValue()==2){
						count=lUserGameTaskDao.selectCashCount(gameTask.getCashId());
					}*/
					gameTask.setState(2);
					gameTask.setMoney(pcDDCallback.getMoney());
					lUserGameTaskDao.update(gameTask);
					/*if(gameTask.getTaskType().intValue()==2){
						   if(count+1>=3) {
							   LUserCash lUserCash=lUserCashDao.selectId(gameTask.getCashId(), pcDDCallback.getUserid());
							   if(lUserCash.getState().intValue()==1) {
								   lUserCash.setState(2);
								   lUserCashDao.update(lUserCash);
							   }
						   }
					}*/
				}
				long time=new Date().getTime();
				if(totalReward>=4000) {
					LUserCash cashLog=lUserCashDao.selectStateOne(pcDDCallback.getUserid());
					if(StringUtil.isNullOrEmpty(cashLog)) {
						LUserCash lUserCash=new LUserCash();
						String outTradeNo = Base64.getOutTradeNo();
						lUserCash.setOutTradeNo(outTradeNo+"2");
						lUserCash.setState(1);
						lUserCash.setCreatorTime(time);
						lUserCash.setUserId(pcDDCallback.getUserid());
						
						String str = new AWorker<String>() {
							@Override
							public String call(String userId) throws Exception {
								LUserCash userCash=lUserCashDao.selectStateOne(lUserCash.getUserId());
								if(StringUtil.isNullOrEmpty(userCash)) {
									lUserCashDao.insert(lUserCash);
								}
								return "1";
							}
						}.execute(lUserCash.getUserId(), "redis_lock_cash_task");
						
						LCashGameTask cashGameTask=new LCashGameTask();
						cashGameTask.setCashId(lUserCash.getId());
						cashGameTask.setUserId(pcDDCallback.getUserid());
						cashGameTask.setGameId(tpGame.getId());
						cashGameTask.setCreateTime(time);
						lCashGameTaskDao.add(cashGameTask);
					}else {
						if(cashLog.getState().intValue()==1) {
							LCashGameTask cashGameTask=lCashGameTaskDao.selectGameId(cashLog.getUserId(), tpGame.getId(),cashLog.getId());	
							if(StringUtil.isNullOrEmpty(cashGameTask)) {
								int count=lCashGameTaskDao.selectCount(cashLog.getId());
								if(count<3) {
									cashGameTask=new LCashGameTask();
									cashGameTask.setCashId(cashLog.getId());
									cashGameTask.setUserId(cashLog.getUserId());
									cashGameTask.setGameId(tpGame.getId());
									cashGameTask.setCreateTime(time);
									lCashGameTaskDao.add(cashGameTask);	
								}
								if(count+1>=3) {
									cashLog.setState(2);
									lUserCashDao.update(cashLog);
								}
							}
						}
					}
				}
				
				//获得心愿值
				String strWish = new AWorker<String>() {
					@Override
					public String call(String userId) throws Exception {
						WishGain wishGain=wishGainDao.selectSign(pcDDCallback.getOrdernum());
						if(!StringUtil.isNullOrEmpty(wishGain)) {
							return "2";
						}
						UserWishValue userWishValue=userWishValueDao.selectOne(pcDDCallback.getUserid());
						if(StringUtil.isNullOrEmpty(userWishValue)) {
							userWishValue=new UserWishValue();
							userWishValue.setUserId(pcDDCallback.getUserid());
							userWishValue.setWishValue(4);
							userWishValue.setUpdateTime(time);
						    userWishValueDao.insert(userWishValue);
						}else {
							userWishValue.setUpdateTime(time);
							userWishValueDao.updateAdd(userWishValue);
						}
						wishGain=new WishGain();
					    wishGain.setUserId(pcDDCallback.getUserid());
					    wishGain.setMode(3);
					    wishGain.setNumber(1);
					    wishGain.setSign(pcDDCallback.getOrdernum());
					    wishGain.setCreateTime(time);
					    wishGainDao.insert(wishGain);
						return "1";
					}
				}.execute(pcDDCallback.getUserid(), "redis_lock_add_wish"); 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional
	@Override
	public Long settlementGame(String userId, long baseReward,String gameName) throws Exception {
		long now = new Date().getTime();
		long passbookReward = 0l; // 加成券加成数
		long vipReward = 0l; // vip加成数
		long taskReward = 0l; // 任务奖励 
		List<Map<String, Object>> passbooks = new ArrayList<>();
		// 1.查看用户首张可用的加成券,必须小于等于一万才可以用加成券
		if(baseReward <= 10000){
			passbooks = rsUserPassbookDao.selectByUser(userId, ConstantUtil.PASSBOOK_TYPE_3);
			if(passbooks != null && passbooks.size() > 0){
				Map<String, Object> passbook = passbooks.get(0);
				int passbookAddition = new Integer(passbook.get("passbook_value").toString());
				passbookReward = baseReward * passbookAddition / 100;
			}
		}
		// 2.查看用户的未过期VIP，并给与相应报酬 取最高的
		List<Map<String, Object>> lUserVips = lUserVipDao.selectByUser(userId);
		if(lUserVips != null && lUserVips.size() > 0){
			Map<String, Object> lUserVip = lUserVips.get(0);
			int vipAddition = new Integer(lUserVip.get("game_addition").toString());
			vipReward = baseReward * vipAddition / 100;
		}
		
		// 3.用户是否首次完成任意棋牌任务增加百分比金币
		long count = lUserTaskDao.selectUserTaskCount(userId, 6);
		if(count == 0){
			MTaskInfo taskInfo = mTaskInfoDao.selectOne(6);
			taskReward = baseReward * taskInfo.getReward() / 100;
		}
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId); // 获取用户数据
		
		long reward = baseReward + passbookReward + vipReward +taskReward; // 总奖励金币数
		long pigReward = 0l;
		long levelValue = reward;
		if(reward > 10000){
			levelValue = 10000;
		}
		String level = mUserInfo.getLevel();
		long levelValueOld = mUserInfo.getLevelValue() == null?0l:mUserInfo.getLevelValue();
		//mUserInfo.setLevelValue( levelValueOld + levelValue); // 设置用户经验值
		// 4.用户当前等级
		PLevel thislevelInfo = pLevelDao.selectByLevel(level);
		if(thislevelInfo.getAddition().intValue() > 0){
			pigReward = thislevelInfo.getAddition().intValue() * baseReward / 100;
		}
		// 根据用户当前等级查询下一级等级信息
		PLevel levelInfo = pLevelDao.selectNextLevel(mUserInfo.getLevel());
		if(levelInfo == null){ // 说明是最高级了
			// 给师傅相应报酬
			MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
			if(mUserApprentice != null){
				MUserInfo referrer = mUserInfoDao.selectOne(mUserApprentice.getReferrerId()); // 获取用户数据
				int roleType = referrer.getRoleType().intValue();
				int channelSchame = 1;
				if(roleType == 2 || roleType == 3){
					channelSchame = 2;
				}
				String channelCode = referrer.getChannelCode() == null?referrer.getParentChannelCode():referrer.getChannelCode();
				if(StringUtils.isBlank(channelCode)){
		    		channelCode = "baozhu";
		    	}
				MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
				MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), channelSchame); // 获取奖励方案
				//  修改师傅账户金币数
				int referrerAddition = mChannelConfigUser.getReferrerAddition();
				long referrerReward = baseReward * referrerAddition / 100;
				referrer.setUpdateTime(now);
				referrer.setCoin(referrer.getCoin() + referrerReward);
				mUserInfoDao.update(referrer);
				//  新增师傅金币变更记录 
				LCoinChange lCoinChange = new LCoinChange(referrer.getUserId(), referrerReward, 
						ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_5, now, 1,"",referrer.getCoin());
				lCoinChangeDao.insert(lCoinChange);
				// 修改师徒贡献值
				mUserApprentice.setContribution(mUserApprentice.getContribution() + referrerReward);
				mUserApprenticeDao.update(mUserApprentice);
			}
		}else{
			if(levelInfo.getExperience() <= levelValueOld){ // 大于等于等级经验值，进行升级
				level = levelInfo.getLevel(); // 修改用户等级
				// 给师傅相应报酬
				MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
				if(mUserApprentice != null){
					// 如果是达人，则享受另外的奖励
					MUserInfo referrer = mUserInfoDao.selectOne(mUserApprentice.getReferrerId()); // 获取用户数据
					int roleType = referrer.getRoleType().intValue();
					int channelSchame = 1;
					if(roleType == 2 || roleType == 3){
						channelSchame = 2;
					}
					//if(mUserApprentice.getApprenticeType().intValue() == 2){
						if(referrer.getRoleType().intValue() == ConstantUtil.ROLE_TYPE_4){
							long dayT = 24 * 60 * 60 * 1000l;
							// 查询已完成多少个任务
							long taskCount = lDarenRewardDetailDao.selectCountTask(userId);
							taskCount = taskCount +1;
							// 根据完成任务数，获取奖励方案
							MDarenReward mDarenReward = mDarenRewardDao.selectByOrders(taskCount);
							if(mDarenReward != null){
								int dayLimit = mDarenReward.getDayLimit();//天数限制
								int peopleLimit = mDarenReward.getPeopleLimit();//人数限制
								int apprentice = referrer.getApprentice(); // 用户邀请数
								
								// 计算用户注册天数
								long regTime = mUserInfo.getCreateTime();
								long cz = now - regTime;
								long timeLimit = dayLimit * dayT;
								if(cz <= timeLimit && apprentice > peopleLimit && baseReward > 3999){
									// 修改今日奖励记录
									long level_reward = mDarenReward.getCoin();
									// 新增奖励明细
									LDarenReward lDarenReward = lDarenRewardDao.selectByUser(referrer.getUserId(), DateUtil.getCurrentDate5());
									if(lDarenReward == null){
										if(mDarenReward.getRewardType().intValue() == 1){
											lDarenReward = new LDarenReward(referrer.getUserId(), DateUtil.getCurrentDate5(), 1, level_reward, 0l, 1, now);
										}else{
											lDarenReward = new LDarenReward(referrer.getUserId(), DateUtil.getCurrentDate5(), 1, 0l,level_reward, 1, now);
										}
										lDarenRewardDao.insert(lDarenReward);
									}else{
							    		long time = now - (now % dayT);
							    		if(mDarenReward.getRewardType().intValue() == 1){
											lDarenReward.setFirstReward(lDarenReward.getFirstReward() + level_reward);
										}else{
											lDarenReward.setSecondReward(lDarenReward.getSecondReward() + level_reward);
										}
							    		List<LDarenRewardDetail> lDarenRewardDetails = lDarenRewardDetailDao.selectByUserToday(userId,time);
							    		if(lDarenRewardDetails.size() == 0){
							    			lDarenReward.setApprenticeCount(lDarenReward.getApprenticeCount() + 1);
							    		}
										lDarenReward.setTaskCount(lDarenReward.getTaskCount() + 1);
							    		lDarenReward.setUpdateTime(now);
										lDarenRewardDao.update(lDarenReward);
									}
									// 新增达人变帐明细
									LDarenRewardDetail lDarenRewardDetail = new LDarenRewardDetail();
									lDarenRewardDetail.setUserId(referrer.getUserId());
									lDarenRewardDetail.setReward(mDarenReward.getCoin());
									lDarenRewardDetail.setApprenticeId(userId);
									lDarenRewardDetail.setApprenticeReward(baseReward);
									lDarenRewardDetail.setTaskType(mDarenReward.getRewardType());
									lDarenRewardDetail.setTaskName(gameName);
									lDarenRewardDetail.setCreateTime(now);
									lDarenRewardDetailDao.insert(lDarenRewardDetail);
									// 贡献值
									mUserApprentice.setContribution(mUserApprentice.getContribution() + level_reward);
									mUserApprenticeDao.update(mUserApprentice);
									// 新增金币变帐明细
									long referrerCoin = referrer.getCoin() + level_reward;
									if(mDarenReward.getRewardType().intValue() == 1){
										LCoinChange lCoinChange2 = new LCoinChange(referrer.getUserId(), level_reward, 
												ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_24, now, 1,"邀请达人首个任务",referrerCoin);
										lCoinChangeDao.insert(lCoinChange2);
									}else{
										LCoinChange lCoinChange2 = new LCoinChange(referrer.getUserId(), level_reward, 
												ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_25, now, 1,"邀请达人后续任务",referrerCoin);
										lCoinChangeDao.insert(lCoinChange2);
									}
									// 修改金币数
									referrer.setCoin(referrerCoin);
									referrer.setUpdateTime(now);
									mUserInfoDao.update(referrer);
								}
							}
						}
					//}else {
						long level_reward = 0l;
						// 修改师傅账户金币数
						String channelCode = referrer.getChannelCode() == null?referrer.getParentChannelCode():referrer.getChannelCode();
						if(StringUtils.isBlank(channelCode)){
							channelCode = "baozhu";
						}
						MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
						MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), channelSchame); // 获取奖励方案
						int referrerAddition = mChannelConfigUser.getReferrerAddition();
						long referrerReward = baseReward * referrerAddition / 100;
						// 根据徒弟等级，获得奖励
						if(ConstantUtil.L4.equals(levelInfo.getLevel())){
							level_reward = mChannelConfigUser.getLevel4();
						}else if(ConstantUtil.L6.equals(levelInfo.getLevel())){
							level_reward = mChannelConfigUser.getLevel6();
						}else if(ConstantUtil.L8.equals(levelInfo.getLevel())){
							level_reward = mChannelConfigUser.getLevel8();
						}else if(ConstantUtil.L12.equals(levelInfo.getLevel())){
							level_reward = mChannelConfigUser.getLevel12();
						}else{
							level_reward = 0;
						}
						referrer.setCoin(referrer.getCoin() + referrerReward + level_reward);
						referrer.setUpdateTime(now);
						mUserInfoDao.update(referrer);
						//  新增师傅金币变更记录 
						LCoinChange lCoinChange = new LCoinChange(referrer.getUserId(), referrerReward, 
								ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_5, now, 1,"",referrer.getCoin() - level_reward);
						lCoinChangeDao.insert(lCoinChange);
						if(level_reward > 0){ // 师傅徒弟升级奖励
							LCoinChange lCoinChange2 = new LCoinChange(referrer.getUserId(), level_reward, 
									ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_8, now, 1,"徒弟升至"+levelInfo.getLevel()+"级奖励",referrer.getCoin());
							lCoinChangeDao.insert(lCoinChange2);
						}
						// 修改师徒贡献值
						mUserApprentice.setContribution(mUserApprentice.getContribution() + level_reward + referrerReward);
						mUserApprenticeDao.update(mUserApprentice);
					}
				//}
			} else { // 未升级，不给奖励
				// 给师傅相应报酬
				MUserApprentice mUserApprentice = mUserApprenticeDao.selectUserIdNew(userId);
				if(mUserApprentice != null){
					// 修改师傅账户金币数
					MUserInfo referrer = mUserInfoDao.selectOne(mUserApprentice.getReferrerId()); // 获取用户数据
					int roleType = referrer.getRoleType().intValue();
					int channelSchame = 1;
					if(roleType == 2 || roleType == 3){
						channelSchame = 2;
					}
					//if(mUserApprentice.getApprenticeType().intValue() == 2){
						if(referrer.getRoleType().intValue() == ConstantUtil.ROLE_TYPE_4){
							long dayT = 24 * 60 * 60 * 1000l;
							// 查询已完成多少个任务
							long taskCount = lDarenRewardDetailDao.selectCountTask(userId);
							taskCount = taskCount +1;
							// 根据完成任务数，获取奖励方案
							MDarenReward mDarenReward = mDarenRewardDao.selectByOrders(taskCount);
							if(mDarenReward != null){
								int dayLimit = mDarenReward.getDayLimit();//天数限制
								int peopleLimit = mDarenReward.getPeopleLimit();//人数限制
								int apprentice = referrer.getApprentice(); // 用户邀请数
								
								// 计算用户注册天数
								long regTime = mUserInfo.getCreateTime();
								long cz = now - regTime;
								long timeLimit = dayLimit * dayT;
								if(cz <= timeLimit && apprentice > peopleLimit && baseReward > 3999){
									// 修改今日奖励记录
									long level_reward = mDarenReward.getCoin();
									// 新增奖励明细
									LDarenReward lDarenReward = lDarenRewardDao.selectByUser(referrer.getUserId(), DateUtil.getCurrentDate5());
									if(lDarenReward == null){
										if(mDarenReward.getRewardType().intValue() == 1){
											lDarenReward = new LDarenReward(referrer.getUserId(), DateUtil.getCurrentDate5(), 1, level_reward, 0l, 1, now);
										}else{
											lDarenReward = new LDarenReward(referrer.getUserId(), DateUtil.getCurrentDate5(), 1, 0l,level_reward, 1, now);
										}
										lDarenRewardDao.insert(lDarenReward);
									}else{
							    		long time = now - (now % dayT);
							    		if(mDarenReward.getRewardType().intValue() == 1){
											lDarenReward.setFirstReward(lDarenReward.getFirstReward() + level_reward);
										}else{
											lDarenReward.setSecondReward(lDarenReward.getSecondReward() + level_reward);
										}
							    		List<LDarenRewardDetail> lDarenRewardDetails = lDarenRewardDetailDao.selectByUserToday(userId,time);
							    		if(lDarenRewardDetails.size() == 0){
							    			lDarenReward.setApprenticeCount(lDarenReward.getApprenticeCount() + 1);
							    		}
										lDarenReward.setTaskCount(lDarenReward.getTaskCount() + 1);
										lDarenReward.setUpdateTime(now);
										lDarenRewardDao.update(lDarenReward);
									}
									// 新增达人变帐明细
									LDarenRewardDetail lDarenRewardDetail = new LDarenRewardDetail();
									lDarenRewardDetail.setUserId(referrer.getUserId());
									lDarenRewardDetail.setReward(mDarenReward.getCoin());
									lDarenRewardDetail.setApprenticeId(userId);
									lDarenRewardDetail.setApprenticeReward(baseReward);
									lDarenRewardDetail.setTaskType(mDarenReward.getRewardType());
									lDarenRewardDetail.setTaskName(gameName);
									lDarenRewardDetail.setCreateTime(now);
									lDarenRewardDetailDao.insert(lDarenRewardDetail);
									// 贡献值
									mUserApprentice.setContribution(mUserApprentice.getContribution() + level_reward);
									mUserApprenticeDao.update(mUserApprentice);
									// 新增金币变帐明细
									long referrerCoin = referrer.getCoin() + level_reward;
									if(mDarenReward.getRewardType().intValue() == 1){
										LCoinChange lCoinChange2 = new LCoinChange(referrer.getUserId(), level_reward, 
												ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_24, now, 1,"邀请达人首个任务",referrerCoin);
										lCoinChangeDao.insert(lCoinChange2);
									}else{
										LCoinChange lCoinChange2 = new LCoinChange(referrer.getUserId(), level_reward, 
												ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_25, now, 1,"邀请达人后续任务",referrerCoin);
										lCoinChangeDao.insert(lCoinChange2);
									}
									// 修改金币数
									referrer.setCoin(referrerCoin);
									referrer.setUpdateTime(now);
									mUserInfoDao.update(referrer);
								}
							}
						}
					//}else {
						String channelCode = referrer.getChannelCode() == null?referrer.getParentChannelCode():referrer.getChannelCode();
						if(StringUtils.isBlank(channelCode)){
							channelCode = "baozhu";
						}
						MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
						MChannelConfigUser mChannelConfigUser = mChannelConfigUserDao.selectByUserTypeAndConfigId(mChannelConfig.getId(), channelSchame); // 获取奖励方案
						int referrerAddition = mChannelConfigUser.getReferrerAddition();
						long referrerReward = baseReward * referrerAddition / 100;
						referrer.setCoin(referrer.getCoin() + referrerReward);
						referrer.setUpdateTime(now);
						mUserInfoDao.update(referrer);
						//  新增师傅金币变更记录 
						LCoinChange lCoinChange = new LCoinChange(referrer.getUserId(), referrerReward, 
								ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_5, now, 1,"",referrer.getCoin());
						lCoinChangeDao.insert(lCoinChange);
						// 修改师徒贡献值
						mUserApprentice.setContribution(mUserApprentice.getContribution() + referrerReward);
						mUserApprenticeDao.update(mUserApprentice);
					}
				//}
			}
		}
		MUserInfo mUserInfoOld = mUserInfoDao.selectOne(userId); // 获取用户数据
		mUserInfoOld.setCoin(mUserInfoOld.getCoin() + reward); // 修改金币
		mUserInfoOld.setPigCoin(mUserInfoOld.getPigCoin() + pigReward); // 修改金猪
		mUserInfoOld.setLevelValue(levelValueOld + levelValue); // 修改经验值
		mUserInfoOld.setLevel(level); // 修改等级
		mUserInfoOld.setUpdateTime(now);
		mUserInfoDao.update(mUserInfoOld);
		// 金币变更记录
		LCoinChange lCoinChange = new LCoinChange(userId, reward, 
				ConstantUtil.FLOWTYPE_IN, ConstantUtil.COIN_CHANGED_TYPE_7, now, 1,gameName,mUserInfoOld.getCoin());
		lCoinChangeDao.insert(lCoinChange);
		// 金猪变更记录
		if(pigReward > 0){
			LPigChange lPigChange = new LPigChange(userId, pigReward, ConstantUtil.FLOWTYPE_IN,
					ConstantUtil.PIG_CHANGED_TYPE_4, now,"游戏试玩等级奖励",mUserInfoOld.getPigCoin());
			lPigChangeDao.insert(lPigChange);
		}
		// 修改卡券已使用
		if(passbookReward > 0){ // 说明有可用的加成券，这里要设置为已使用
			RSUserPassbook rsUserPassbook = new RSUserPassbook();
			rsUserPassbook.setId(new Integer(passbooks.get(0).get("rsId").toString()));
			rsUserPassbook.setStatus(2);
			rsUserPassbookDao.update(rsUserPassbook);
		}
		// 首次完成棋牌任务，插入任务表
		if(taskReward > 0){ // 说明有可用的加成券，这里要设置为已使用
			LUserTask lUserTask = new LUserTask(mUserInfo.getUserId(), 6, now);
			lUserTaskDao.insert(lUserTask);
		}
		// 发送任务返利到账提醒
		AppNewsInform appNewsInform=new AppNewsInform();
		appNewsInform.setUserId(mUserInfo.getUserId());
		appNewsInform.setInformTitle("试玩游戏奖励");
		appNewsInform.setInformContent("您有一笔游戏试玩奖励到账");
		appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
		appNewsInformService.addPush(appNewsInform);
		//完成一次游戏新手任务
		LUserTask task=lUserTaskDao.selectOne(userId, 11);
	    if(!StringUtil.isNullOrEmpty(task)) {
	    	List<LUserTask> uTasks=lUserTaskDao.selectListUser(userId, 14);
	    	if(uTasks.size() == 0){
		    	LUserTask userTask=new LUserTask();
				userTask.setUserId(userId);
				userTask.setTaskId(14);
				userTask.setCreateTime(new Date().getTime());
				userTask.setIsReceive(1);
				lUserTaskDao.insert(userTask);
	    	}
	    }
	    return reward;
	}

	@Override
	public void xwCallbackInsert(XWCallback xwCallback,long totalReward) {
		xwCallbackDao.insert(xwCallback);
		if(xwCallback.getStatus() == 1){
			try{
				double moneyd = Double.valueOf(xwCallback.getMoney());
				long baseReward = (long) (moneyd * 10000);
				TPCallback tpCallback = new TPCallback();
				tpCallback.setCreateTime(new Date().getTime());
				tpCallback.setStatus(xwCallback.getStatus());
				tpCallback.setTpName("闲玩游戏");
				tpCallback.setGameId(xwCallback.getAdid()+"");
				tpCallback.setGameName(xwCallback.getAdname());
				tpCallback.setOrderNum(xwCallback.getOrdernum());
				tpCallback.setGameReward(baseReward+"");
				tpCallback.setReward(totalReward+"");
				tpCallback.setTpGameId(xwCallback.getMerid());
				tpCallback.setUserId(xwCallback.getAppsign());
				MUserInfo mUserInfo = mUserInfoDao.selectOne(xwCallback.getAppsign());
				if(mUserInfo.getChannelCode() != null  &&  (!"".equals(mUserInfo.getChannelCode()))){
					MChannelInfo mChannelInfo = mChannelInfoDao.selectByChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelName(mChannelInfo.getChannelName());
				}else{
					tpCallback.setChannelCode("baozhu");
					tpCallback.setChannelName("宝猪乐园");
				}
				tpCallbackDao.insert(tpCallback);
				gameStatistic(mUserInfo);//用户完成游戏统计
				TPGame tpGame = tpGameDao.selectByGameInterface(Integer.valueOf(xwCallback.getAdid()), 3);
				if(tpGame != null){
					updateSignGame(xwCallback.getAppsign(), tpGame.getId(),totalReward);
				}
				
				//完成提现或者领取活跃金任务
				LUserGameTask gameTask=lUserGameTaskDao.selectGameOne2(xwCallback.getAppsign(), tpGame.getId());
				if(!StringUtil.isNullOrEmpty(gameTask) && gameTask.getState().intValue()==1) {
					/*int count=0;
					if(gameTask.getTaskType().intValue()==2){
						count=lUserGameTaskDao.selectCashCount(gameTask.getCashId());
					}*/
					gameTask.setState(2);
					gameTask.setMoney(Double.parseDouble(xwCallback.getMoney()));
					lUserGameTaskDao.update(gameTask);
					/*if(gameTask.getTaskType().intValue()==2){
						   if(count+1>=3) {
							   LUserCash lUserCash=lUserCashDao.selectId(gameTask.getCashId(), xwCallback.getAppsign());
							   if(lUserCash.getState().intValue()==1) {
								   lUserCash.setState(2);
								   lUserCashDao.update(lUserCash);
							   }
						   }
					}*/
				}
				long time=new Date().getTime();
				if(totalReward>=4000) {
					LUserCash cashLog=lUserCashDao.selectStateOne(xwCallback.getAppsign());
					if(StringUtil.isNullOrEmpty(cashLog)) {
						LUserCash lUserCash=new LUserCash();
						String outTradeNo = Base64.getOutTradeNo();
						lUserCash.setOutTradeNo(outTradeNo+"2");
						lUserCash.setState(1);
						lUserCash.setCreatorTime(time);
						lUserCash.setUserId(xwCallback.getAppsign());
						
						String str = new AWorker<String>() {
							@Override
							public String call(String userId) throws Exception {
								LUserCash userCash=lUserCashDao.selectStateOne(lUserCash.getUserId());
								if(StringUtil.isNullOrEmpty(userCash)) {
									lUserCashDao.insert(lUserCash);
								}
								return "1";
							}
						}.execute(lUserCash.getUserId(), "redis_lock_cash_task");
						
						LCashGameTask cashGameTask=new LCashGameTask();
						cashGameTask.setCashId(lUserCash.getId());
						cashGameTask.setUserId(xwCallback.getAppsign());
						cashGameTask.setGameId(tpGame.getId());
						cashGameTask.setCreateTime(time);
						lCashGameTaskDao.add(cashGameTask);
					}else {
						if(cashLog.getState().intValue()==1) {
							LCashGameTask cashGameTask=lCashGameTaskDao.selectGameId(cashLog.getUserId(), tpGame.getId(),cashLog.getId());	
							if(StringUtil.isNullOrEmpty(cashGameTask)) {
								int count=lCashGameTaskDao.selectCount(cashLog.getId());
								if(count<3) {
									cashGameTask=new LCashGameTask();
									cashGameTask.setCashId(cashLog.getId());
									cashGameTask.setUserId(cashLog.getUserId());
									cashGameTask.setGameId(tpGame.getId());
									cashGameTask.setCreateTime(time);
									lCashGameTaskDao.add(cashGameTask);	
								}
								if(count+1>=3) {
									cashLog.setState(2);
									lUserCashDao.update(cashLog);
								}
							}
						}
					}
				}
		
				//获得心愿值
				String strWish = new AWorker<String>() {
					@Override
					public String call(String userId) throws Exception {
						WishGain wishGain=wishGainDao.selectSign(xwCallback.getOrdernum());
						if(!StringUtil.isNullOrEmpty(wishGain)) {
							return "2";
						}
						UserWishValue userWishValue=userWishValueDao.selectOne(xwCallback.getAppsign());
						if(StringUtil.isNullOrEmpty(userWishValue)) {
							userWishValue=new UserWishValue();
							userWishValue.setUserId(xwCallback.getAppsign());
							userWishValue.setWishValue(4);
							userWishValue.setUpdateTime(time);
						    userWishValueDao.insert(userWishValue);
						}else {
							userWishValue.setUpdateTime(time);
							userWishValueDao.updateAdd(userWishValue);
						}
						wishGain=new WishGain();
					    wishGain.setUserId(xwCallback.getAppsign());
					    wishGain.setMode(3);
					    wishGain.setNumber(1);
					    wishGain.setSign(xwCallback.getOrdernum());
					    wishGain.setCreateTime(time);
					    wishGainDao.insert(wishGain);
						return "1";
					}
				}.execute(xwCallback.getAppsign(), "redis_lock_add_wish"); 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public XWCallback queryByXWOrderNum(String ordernum) {
		return xwCallbackDao.selectByOrderNum(ordernum);
	}

	@Override
	public PCDDCallback queryByPCDDOrderNum(String ordernum) {
		return pcddCallbackDao.selectByOrderNum(ordernum);
	}

	@Override
	public YoleCallback queryByYoLeId(String yoleid) {
		return yoleCallbackDao.selectByYoLeId(yoleid);
	}

	@Override
	public TPGame recommendGame(int ptype,String userId) {
		TPGame tpGame = new TPGame();
		tpGame.setGameTag(1);
		tpGame.setStatus(1);
		tpGame.setPtype(ptype);
		Random r=new Random();
		if(ptype == 2){
			List<TPGame> tpGames = tpGameDao.recommendGameList(ptype,userId);
			int index=r.nextInt(tpGames.size());
			tpGame=tpGames.get(index);
		}else{
			List<TPGame> tpGames = tpGameDao.recommendGameListIOS(ptype,userId);
			int index=r.nextInt(tpGames.size());
			tpGame=tpGames.get(index);
		}
		return tpGame;
	}

	@Override
	public TPGame recommendGameNew(int ptype,String userId) {
		TPGame tpGame = new TPGame();
		tpGame.setGameTag(1);
		tpGame.setStatus(1);
		tpGame.setPtype(ptype);
		Random r=new Random();
		List<TPGame> tpGames = tpGameDao.recommendGameList(ptype,userId);
		int index=r.nextInt(tpGames.size());
        tpGame=tpGames.get(index);
		return tpGame;
	}

	@Override
	public String buildQDGameUrl(Integer id, Map<String, String> respMap, Integer signinId) {
		/*if(signinId.intValue()==0) {
			LUserGameTask lUserGameTask=lUserGameTaskDao.selectInfo(respMap.get("userId"),id);
			return buildHyjGameUrl(id,respMap,lUserGameTask.getVipId());
		}*/
		TPGame tpGame = tpGameDao.selectOne(id);
		TPInterface tpInterface = tpInterfaceDao.selectOne(tpGame.getInterfaceId());
		String url = tpGame.getUrl();
		if("channelOnLineAdids".equals(tpInterface.getInterfaceCode()) 
				|| "flowGameList".equals(tpInterface.getInterfaceCode())){
			respMap.put("adid", tpGame.getGameId()+"");
			respMap.put("ptype", "1");
			if("1".equals(respMap.get("equipmentType"))){
				respMap.put("ptype", "2");
			}
		}
		List<TPParams> tpParamList = tpParamsDao.selectListByInterface(tpGame.getInterfaceId());
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		for (TPParams tpPara : tpParamList) {
			if(tpPara.getType().intValue() == 1){
				if(tpPara.getIsReplace().intValue() == 1){
					url = url.replace(tpPara.getValue().toString(), respMap.get(tpPara.getReplaceCode()));
					tpPara.setValue(respMap.get(tpPara.getReplaceCode()));
				}
			}
			if(tpPara.getInfoEncryptNeed().intValue() == 1){ // 是否加密所需字段
				encrpty.add(tpPara.getValue());
			}
			if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
				if(tpPara.getEncryptType().intValue() == 1){
					if(encrpty.size() > 0){
						String signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							url = url.replace(tpPara.getValue(), signStr);
						}
					}
				}
			}
		}
		// 插入我的试玩，如果已存在待完成的游戏，直接删除，当前签到记录，只能有一个待完成的游戏
		List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserSignin(respMap.get("userId"),signinId,1);
		List<LUserSignGame> lUserSignGameOld = lUserSignGameDao.selectByUserGame(respMap.get("userId"), id);
		if(lUserSignGameOld.size() > 0){
			LUserSignGame old = lUserSignGameOld.get(0);
			if(old.getStatus().intValue() == 2){
				return url;
			}
		}
		LUserSignGame lUserSignGame = new LUserSignGame();
		if(lUserSignGames != null && lUserSignGames.size() > 0){
			lUserSignGame = lUserSignGames.get(0);
			lUserSignGame.setGameId(id);
			lUserSignGame.setCreateTime(new Date().getTime());
			lUserSignGameDao.updateGame(lUserSignGame);
		}else {
			lUserSignGame.setSigninId(signinId);
			lUserSignGame.setGameId(id);
			lUserSignGame.setUserId(respMap.get("userId"));
			lUserSignGame.setCreateTime(new Date().getTime());
			lUserSignGame.setStatus(1);
			lUserSignGame.setIsHide(1);
			lUserSignGameDao.insert(lUserSignGame);
		}
		return url;
	}
	
	/**
	 * 回调后续修改用户签到赚状态
	 * @param userId
	 * @param gameId
	 */
	public void updateSignGame(String userId,Integer gameId,long totalReward){
		List<LUserSignGame> lUserSignGames = lUserSignGameDao.selectByUserGame(userId, gameId);
		if(lUserSignGames != null && lUserSignGames.size() > 0){
			LUserSignGame lUserSignGame = lUserSignGames.get(0);
			if(lUserSignGame.getStatus().intValue() == 1){
				long now = new Date().getTime();
				lUserSignGame.setStatus(2);
				lUserSignGame.setFinishTime(now);
				lUserSignGame.setReward(totalReward);
				lUserSignGameDao.update(lUserSignGame);
				long count = lUserSignGameDao.selectCountByUserSigin(userId,lUserSignGame.getSigninId());
				LUserSignin lUserSignin = lUserSigninDao.selectOne(lUserSignGame.getSigninId());
				if(count == lUserSignin.getGameCount().longValue()){
					if((lUserSignin.getSignDay() % 5) > 0){
						lUserSignin.setStatus(3);
						lUserSignin.setUpdateTime(now);
						lUserSigninDao.update(lUserSignin);
					}
				}
			}
		}
	}

	@Override
	public void sendYOLE(YoleCallback yoleCallback) {
		jmsProducer.gameCallback(JmsWrapper.Type.CALL_BACK_YOLE, yoleCallback);
	}

	@Override
	public void sendPCDD(PCDDCallback pcDDCallback) {
		jmsProducer.gameCallback(JmsWrapper.Type.CALL_BACK_PCDD, pcDDCallback);
	}

	@Override
	public void sendXW(XWCallback xwCallback) {
		jmsProducer.gameCallback(JmsWrapper.Type.CALL_BACK_XW, xwCallback);
	}

	@Override
	public void sendBZ(BZCallback bzCallback) {
		jmsProducer.gameCallback(JmsWrapper.Type.CALL_BACK_BZ, bzCallback);
	}

	@Override
	public void bzCallbackInsert(BZCallback bzCallback, long reward) {
		bzCallbackDao.insert(bzCallback);
		if(bzCallback.getStatus() == 1){
			try{
				double moneyd = Double.valueOf(bzCallback.getMoney());
				long baseReward = (long) (moneyd * 10000);
				TPCallback tpCallback = new TPCallback();
				tpCallback.setCreateTime(new Date().getTime());
				tpCallback.setStatus(bzCallback.getStatus());
				tpCallback.setTpName("宝猪游戏");
				tpCallback.setGameId(bzCallback.getAdid()+"");
				tpCallback.setGameName(bzCallback.getAdname());
				tpCallback.setOrderNum(bzCallback.getOrdernum());
				tpCallback.setGameReward(baseReward+"");
				tpCallback.setReward(reward+"");
				tpCallback.setTpGameId(bzCallback.getMerid());
				tpCallback.setUserId(bzCallback.getAppsign());
				MUserInfo mUserInfo = mUserInfoDao.selectOne(bzCallback.getAppsign());
				if(mUserInfo.getChannelCode() != null  &&  (!"".equals(mUserInfo.getChannelCode()))){
					MChannelInfo mChannelInfo = mChannelInfoDao.selectByChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelCode(mUserInfo.getChannelCode());
					tpCallback.setChannelName(mChannelInfo.getChannelName());
				}else{
					tpCallback.setChannelCode("baozhu");
					tpCallback.setChannelName("宝猪乐园");
				}
				tpCallbackDao.insert(tpCallback);
				gameStatistic(mUserInfo);//用户完成游戏统计
				TPGame tpGame = tpGameDao.selectByGameInterface(Integer.valueOf(bzCallback.getAdid()), 4);
				if(tpGame != null){
					updateSignGame(bzCallback.getAppsign(), tpGame.getId(),reward);
				}
				
				//完成提现或者领取活跃金任务
				LUserGameTask gameTask=lUserGameTaskDao.selectGameOne2(bzCallback.getAppsign(), tpGame.getId());
				if(!StringUtil.isNullOrEmpty(gameTask) && gameTask.getState().intValue()==1) {
					/*int count=0;
					if(gameTask.getTaskType().intValue()==2){
						count=lUserGameTaskDao.selectCashCount(gameTask.getCashId());
					}*/
					gameTask.setState(2);
					gameTask.setMoney(Double.parseDouble(bzCallback.getMoney()));
					lUserGameTaskDao.update(gameTask);
					/*if(gameTask.getTaskType().intValue()==2){
						   if(count+1>=3) {
							   LUserCash lUserCash=lUserCashDao.selectId(gameTask.getCashId(), bzCallback.getAppsign());
							   if(lUserCash.getState().intValue()==1) {
								   lUserCash.setState(2);
								   lUserCashDao.update(lUserCash);
							   }
						   }
					}*/
				}
				long time=new Date().getTime();
				if(reward>=4000) {
					LUserCash cashLog=lUserCashDao.selectStateOne(bzCallback.getAppsign());
					if(StringUtil.isNullOrEmpty(cashLog)) {
						LUserCash lUserCash=new LUserCash();
						String outTradeNo = Base64.getOutTradeNo();
						lUserCash.setOutTradeNo(outTradeNo+"2");
						lUserCash.setState(1);
						lUserCash.setCreatorTime(time);
						lUserCash.setUserId(bzCallback.getAppsign());
						
						String str = new AWorker<String>() {
							@Override
							public String call(String userId) throws Exception {
								LUserCash userCash=lUserCashDao.selectStateOne(lUserCash.getUserId());
								if(StringUtil.isNullOrEmpty(userCash)) {
									lUserCashDao.insert(lUserCash);
								}
								return "1";
							}
						}.execute(lUserCash.getUserId(), "redis_lock_cash_task");
						
						LCashGameTask cashGameTask=new LCashGameTask();
						cashGameTask.setCashId(lUserCash.getId());
						cashGameTask.setUserId(bzCallback.getAppsign());
						cashGameTask.setGameId(tpGame.getId());
						cashGameTask.setCreateTime(time);
						lCashGameTaskDao.add(cashGameTask);
					}else {
						if(cashLog.getState().intValue()==1) {
							LCashGameTask cashGameTask=lCashGameTaskDao.selectGameId(cashLog.getUserId(), tpGame.getId(),cashLog.getId());	
							if(StringUtil.isNullOrEmpty(cashGameTask)) {
								int count=lCashGameTaskDao.selectCount(cashLog.getId());
								if(count<3) {
									cashGameTask=new LCashGameTask();
									cashGameTask.setCashId(cashLog.getId());
									cashGameTask.setUserId(cashLog.getUserId());
									cashGameTask.setGameId(tpGame.getId());
									cashGameTask.setCreateTime(time);
									lCashGameTaskDao.add(cashGameTask);	
								}
								if(count+1>=3) {
									cashLog.setState(2);
									lUserCashDao.update(cashLog);
								}
							}
						}
					}
				}
				
				//获得心愿值
				String strWish = new AWorker<String>() {
					@Override
					public String call(String userId) throws Exception {
						WishGain wishGain=wishGainDao.selectSign(bzCallback.getOrdernum());
						if(!StringUtil.isNullOrEmpty(wishGain)) {
							return "2";
						}
						UserWishValue userWishValue=userWishValueDao.selectOne(bzCallback.getAppsign());
						if(StringUtil.isNullOrEmpty(userWishValue)) {
							userWishValue=new UserWishValue();
							userWishValue.setUserId(bzCallback.getAppsign());
							userWishValue.setWishValue(4);
							userWishValue.setUpdateTime(time);
						    userWishValueDao.insert(userWishValue);
						}else {
							userWishValue.setUpdateTime(time);
							userWishValueDao.updateAdd(userWishValue);
						}
						wishGain=new WishGain();
					    wishGain.setUserId(bzCallback.getAppsign());
					    wishGain.setMode(3);
					    wishGain.setNumber(1);
					    wishGain.setSign(bzCallback.getOrdernum());
					    wishGain.setCreateTime(time);
					    wishGainDao.insert(wishGain);
						return "1";
					}
				}.execute(bzCallback.getAppsign(), "redis_lock_add_wish"); 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BZCallback queryByBZOrderNum(String ordernum) {
		return bzCallbackDao.selectByOrderNum(ordernum);
	}

	@Override
	public String buildHyjGameUrl(Integer id, Map<String, String> respMap, Integer vipId) {
		TPGame tpGame = tpGameDao.selectOne(id);
		TPInterface tpInterface = tpInterfaceDao.selectOne(tpGame.getInterfaceId());
		String url = tpGame.getUrl();
		if("channelOnLineAdids".equals(tpInterface.getInterfaceCode()) 
				|| "flowGameList".equals(tpInterface.getInterfaceCode())){
			respMap.put("adid", tpGame.getGameId()+"");
			respMap.put("ptype", "1");
			if("1".equals(respMap.get("equipmentType"))){
				respMap.put("ptype", "2");
			}
		}
		List<TPParams> tpParamList = tpParamsDao.selectListByInterface(tpGame.getInterfaceId());
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		for (TPParams tpPara : tpParamList) {
			if(tpPara.getType().intValue() == 1){
				if(tpPara.getIsReplace().intValue() == 1){
					url = url.replace(tpPara.getValue().toString(), respMap.get(tpPara.getReplaceCode()));
					tpPara.setValue(respMap.get(tpPara.getReplaceCode()));
				}
			}
			if(tpPara.getInfoEncryptNeed().intValue() == 1){ // 是否加密所需字段
				encrpty.add(tpPara.getValue());
			}
			if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
				if(tpPara.getEncryptType().intValue() == 1){
					if(encrpty.size() > 0){
						String signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							url = url.replace(tpPara.getValue(), signStr);
						}
					}
				}
			}
		}
		LUserGameTask ugame=lUserGameTaskDao.selectInfo(respMap.get("userId"),id);
		if(StringUtil.isNullOrEmpty(ugame)) {
			LUserGameTask userGame=lUserGameTaskDao.selectOne(respMap.get("userId"), vipId,1);
			if(StringUtil.isNullOrEmpty(userGame)) {
				userGame=new LUserGameTask();
				userGame.setUserId(respMap.get("userId"));
				userGame.setVipId(vipId);
				userGame.setGameId(tpGame.getId());
				userGame.setCreateTime(new Date().getTime());
				userGame.setState(1);
				userGame.setTaskType(1);
				lUserGameTaskDao.insert(userGame);
			}else {
				if(userGame.getGameId().intValue()!=id.intValue()) {
					userGame.setGameId(tpGame.getId());
					userGame.setCreateTime(new Date().getTime());
					userGame.setState(1);
					userGame.setTaskType(1);
					lUserGameTaskDao.update(userGame);	
				}
			}
		}
		
		return url;
	}

	@Override
	public String buildTxGameUrl(Integer id, Map<String, String> respMap, Integer cashId) {
		TPGame tpGame = tpGameDao.selectOne(id);
		TPInterface tpInterface = tpInterfaceDao.selectOne(tpGame.getInterfaceId());
		String url = tpGame.getUrl();
		if("channelOnLineAdids".equals(tpInterface.getInterfaceCode()) 
				|| "flowGameList".equals(tpInterface.getInterfaceCode())){
			respMap.put("adid", tpGame.getGameId()+"");
			respMap.put("ptype", "1");
			if("1".equals(respMap.get("equipmentType"))){
				respMap.put("ptype", "2");
			}
		}
		List<TPParams> tpParamList = tpParamsDao.selectListByInterface(tpGame.getInterfaceId());
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		for (TPParams tpPara : tpParamList) {
			if(tpPara.getType().intValue() == 1){
				if(tpPara.getIsReplace().intValue() == 1){
					url = url.replace(tpPara.getValue().toString(), respMap.get(tpPara.getReplaceCode()));
					tpPara.setValue(respMap.get(tpPara.getReplaceCode()));
				}
			}
			if(tpPara.getInfoEncryptNeed().intValue() == 1){ // 是否加密所需字段
				encrpty.add(tpPara.getValue());
			}
			if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
				if(tpPara.getEncryptType().intValue() == 1){
					if(encrpty.size() > 0){
						String signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							url = url.replace(tpPara.getValue(), signStr);
						}
					}
				}
			}
		}
		/*LUserGameTask game=lUserGameTaskDao.selectInfo(respMap.get("userId"),id);
		if(StringUtil.isNullOrEmpty(game)) {
			LUserGameTask userGame=lUserGameTaskDao.selectCashGameOne(respMap.get("userId"),2,1);
			if(StringUtil.isNullOrEmpty(userGame)) {
				userGame=new LUserGameTask();
				userGame.setUserId(respMap.get("userId"));
				userGame.setGameId(tpGame.getId());
				userGame.setCreateTime(new Date().getTime());
				userGame.setState(1);
				userGame.setTaskType(2);
				userGame.setCashId(cashId);
				lUserGameTaskDao.insert(userGame);
			}else {
				if(userGame.getGameId().intValue()!=id.intValue()) {
					userGame.setGameId(tpGame.getId());
					userGame.setCreateTime(new Date().getTime());
					userGame.setState(1);
					userGame.setTaskType(2);
					lUserGameTaskDao.update(userGame);
				}
			}
		}*/
		// 插入我的试玩，如果已存在，则更新我的试玩
		LUserGame lUserGame = lUserGameDao.selectOne(respMap.get("userId"),id);
		if(lUserGame != null){
			lUserGame.setModifyTime(new Date().getTime());
			lUserGameDao.update(lUserGame);
		}else{
			lUserGame = new LUserGame();
			lUserGame.setGameId(id);
			lUserGame.setUserId(respMap.get("userId"));
			lUserGame.setModifyTime(new Date().getTime());
			lUserGameDao.insert(lUserGame);
		}
		return url;
	}

	@Override
	public String buildMrhbGameUrl(Integer id, Map<String, String> respMap) {
		TPGame tpGame = tpGameDao.selectOne(id);
		TPInterface tpInterface = tpInterfaceDao.selectOne(tpGame.getInterfaceId());
		String url = tpGame.getUrl();
		if("channelOnLineAdids".equals(tpInterface.getInterfaceCode()) 
				|| "flowGameList".equals(tpInterface.getInterfaceCode())){
			respMap.put("adid", tpGame.getGameId()+"");
			respMap.put("ptype", "1");
			if("1".equals(respMap.get("equipmentType"))){
				respMap.put("ptype", "2");
			}
		}
		List<TPParams> tpParamList = tpParamsDao.selectListByInterface(tpGame.getInterfaceId());
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		for (TPParams tpPara : tpParamList) {
			if(tpPara.getType().intValue() == 1){
				if(tpPara.getIsReplace().intValue() == 1){
					url = url.replace(tpPara.getValue().toString(), respMap.get(tpPara.getReplaceCode()));
					tpPara.setValue(respMap.get(tpPara.getReplaceCode()));
				}
			}
			if(tpPara.getInfoEncryptNeed().intValue() == 1){ // 是否加密所需字段
				encrpty.add(tpPara.getValue());
			}
			if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
				if(tpPara.getEncryptType().intValue() == 1){
					if(encrpty.size() > 0){
						String signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							url = url.replace(tpPara.getValue(), signStr);
						}
					}
				}
			}
		}
		LUserGameTask game=lUserGameTaskDao.selectInfo(respMap.get("userId"),id);
		if(StringUtil.isNullOrEmpty(game)) {
			LUserGameTask userGame=lUserGameTaskDao.selectCashGameOne(respMap.get("userId"),3,1);
			if(StringUtil.isNullOrEmpty(userGame)) {
				userGame=new LUserGameTask();
				userGame.setUserId(respMap.get("userId"));
				userGame.setGameId(tpGame.getId());
				userGame.setCreateTime(new Date().getTime());
				userGame.setState(1);
				userGame.setTaskType(3);
				lUserGameTaskDao.insert(userGame);
			}else {
				if(userGame.getGameId().intValue()!=id.intValue()) {
					userGame.setGameId(tpGame.getId());
					userGame.setCreateTime(new Date().getTime());
					userGame.setState(1);
					userGame.setTaskType(3);
					lUserGameTaskDao.update(userGame);	
				}
			}
		}
		
		return url;
	}

	@Override
	public TPGame recommendGameTask(int ptype, String userId) {
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		String channelCode = mUserInfo.getChannelCode();
    	if(StringUtil.isNullOrEmpty(channelCode)) {
    		channelCode=mUserInfo.getParentChannelCode();
    	}
    	if(StringUtil.isNullOrEmpty(channelCode)){
    		channelCode = "baozhu";
    	}
    	MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode);
    	TPGame tpGame = new TPGame();
		tpGame.setGameTag(1);
		tpGame.setStatus(1);
		tpGame.setPtype(ptype);
		List<TPGame> tpTasks=new ArrayList<>();
		List<TPGame> tpGames=new ArrayList<>();
		Random r=new Random();
    	if(mChannelConfig.getApplyTask().intValue()==1) {
    		PDictionary rechargeDictionary=pDictionaryDao.selectByName(DictionaryUtil.USER_APPLY_TASK);
    		int taskCount=lUserTptaskDao.selectUserCount(userId);
    		long gameCount=tPCallbackDao.selectCount(userId);
    		long count=gameCount+taskCount;
    		if(count>=Long.parseLong(rechargeDictionary.getDicValue())) {
    			tpTasks=tpGameDao.recommendTaskListNews(userId);
        		tpGames = tpGameDao.recommendGameListNews(ptype,userId);
        		tpGames.addAll(tpTasks);
                int index=r.nextInt(tpGames.size());
                tpGame=tpGames.get(index);
    		}else {
    			tpTasks=tpGameDao.recommendTaskListNews(userId);
    			int index=r.nextInt(tpTasks.size());
                tpGame=tpTasks.get(index);
    		}
    	}else {
    		tpTasks=tpGameDao.recommendTaskListNews(userId);
    		tpGames = tpGameDao.recommendGameListNews(ptype,userId);
    		tpGames.addAll(tpTasks);
            int index=r.nextInt(tpGames.size());
            tpGame=tpGames.get(index);
    	}
		return tpGame;
	}
	
	public void gameStatistic(MUserInfo mUserInfo) {
		LUserStatistic lUserStatistic=lUserStatisticDao.selectOne(mUserInfo.getUserId());
		String currentTime=DateUtil.getCurrentDate5();//当前时间
		String oneTime=DateUtil.getLongToDateDay(mUserInfo.getCreateTime(),"yyyy-MM-dd");//注册时间
		String twoTime=DateUtil.getLongToDateDay(mUserInfo.getCreateTime()+24*60*60*1000, "yyyy-MM-dd");//注册第二天
		if(StringUtil.isNullOrEmpty(lUserStatistic)) {
			lUserStatistic=new LUserStatistic();
			lUserStatistic.setUserId(mUserInfo.getUserId());
			if(currentTime.equals(oneTime)) {
				lUserStatistic.setOneGame(1);
				lUserStatistic.setTwoGame(1);
			}
			if(currentTime.endsWith(twoTime)) {
				lUserStatistic.setTwoGame(1);
			}
			lUserStatistic.setTotalGame(1);
			lUserStatisticDao.insert(lUserStatistic);
		}else {
			if(currentTime.equals(oneTime)) {
				if(StringUtil.isNullOrEmpty(lUserStatistic.getOneGame())) {
					lUserStatistic.setOneGame(1);
				}else {
					lUserStatistic.setOneGame(lUserStatistic.getOneGame()+1);
				}
				if(StringUtil.isNullOrEmpty(lUserStatistic.getTwoGame())) {
					lUserStatistic.setTwoGame(1);
				}else {
					lUserStatistic.setTwoGame(lUserStatistic.getTwoGame()+1);
				}
				/*lUserStatistic.setOneGame(lUserStatistic.getOneGame()+1);
				lUserStatistic.setTwoGame(lUserStatistic.getTwoGame()+1);*/
			}
			if(currentTime.endsWith(twoTime)) {
				if(StringUtil.isNullOrEmpty(lUserStatistic.getTwoGame())) {
					lUserStatistic.setTwoGame(1);
				}else {
					lUserStatistic.setTwoGame(lUserStatistic.getTwoGame()+1);
				}
				//lUserStatistic.setTwoGame(lUserStatistic.getTwoGame()+1);
			}
			if(StringUtil.isNullOrEmpty(lUserStatistic.getTotalGame())) {
				lUserStatistic.setTotalGame(1);
			}else {
				lUserStatistic.setTotalGame(lUserStatistic.getTotalGame()+1);
			}
			//lUserStatistic.setTotalGame(lUserStatistic.getTotalGame()+1);
			lUserStatisticDao.update(lUserStatistic);
		}
	}

	@Override
	public PageInfo<TPGame> lenovoList(TPGame tpGame,String userId) {
		if(StringUtils.isNotBlank(tpGame.getGameTitle())){
			List<String> LUserSearchLogs = lUserSearchLogDao.selectExist(userId, tpGame.getGameTitle(), 1);
			if(LUserSearchLogs.size() == 0){
				LUserSearchLog lUserSearchLog = new LUserSearchLog();
				lUserSearchLog.setCreateTime(new Date().getTime());
				lUserSearchLog.setSearchType(1);
				lUserSearchLog.setSearchName(tpGame.getGameTitle());
				lUserSearchLog.setUserId(userId);
				lUserSearchLogDao.insert(lUserSearchLog);
			}
		}
		PageHelper.startPage(tpGame.getPageNum(),tpGame.getPageSize());
		List<TPGame> tpGameList =  tpGameDao.lenovoSelect(tpGame);
		return new PageInfo<>(tpGameList);
		
	}

	@Override
	public List<String> tjList(TPGame tpGame) {
		return tpGameDao.tjList(tpGame);
	}

	@Override
	public void sendXYZ(XWCallback xwCallback) {
		jmsProducer.gameCallback(JmsWrapper.Type.CALL_BACK_XYZ, xwCallback);
		
	}

	@Override
	public Long settlementXYZ(String userId, long baseReward, String adname) {
		
		WishGain wishGain = new WishGain();
		wishGain.setCreateTime(new Date().getTime());
		wishGain.setMode(3);
		wishGain.setNumber(new Integer(baseReward+""));
		wishGain.setUserId(userId);
		wishGainDao.insert(wishGain);

		UserWishValue wishValue = userWishValueDao.selectOne(userId);
		wishValue.setWishValue(new Integer(baseReward+""));
		wishValue.setUpdateTime(new Date().getTime());
		userWishValueDao.update(wishValue);
		// 根据情况，看是否奖励分红心
		Random r = new Random();
		int randomNum = r.nextInt(100) + 1;
		int initNum = 0 ;
		int sumNum = 0 ;
		List<GameRewardRate> gameRewardRateList = gameRewardRateDao.selectList(null);
		for (int i = 0; i < gameRewardRateList.size(); i++) {
			initNum = sumNum;
			sumNum = sumNum + gameRewardRateList.get(i).getRate();
			if(randomNum >= initNum && randomNum < sumNum){
				if(gameRewardRateList.get(i).getRewardNum().intValue() > 0){
					// 添加用户分红心记录
					UserFhxLog userFhxLog = new UserFhxLog(userId, gameRewardRateList.get(i).getRewardNum().intValue(), 3, 1, new Date().getTime());
					userFhxLogDao.insert(userFhxLog);
					// 修改用户分红心数量
					UserFhxXyx userFhxXyx = userFhxXyxDao.selectOne(userId);
					if(userFhxXyx == null){
						userFhxXyx = new UserFhxXyx(userId, gameRewardRateList.get(i).getRewardNum().intValue(), gameRewardRateList.get(i).getRewardNum().intValue(), 0, 0, new Date().getTime());
						userFhxXyxDao.insert(userFhxXyx);
					}else{
						userFhxXyx.setFhxActive(userFhxXyx.getFhxActive() + gameRewardRateList.get(i).getRewardNum().intValue());
						userFhxXyx.setFhxTotal(userFhxXyx.getFhxTotal() + gameRewardRateList.get(i).getRewardNum().intValue());
						userFhxXyx.setUpdateTime(new Date().getTime());
						userFhxXyxDao.update(userFhxXyx);
					}
					int times =userFhxXyx.getFhxTotal().intValue() / 10000;
					if(times > 0){
						// 2.查询用户通过改方式获得幸运星的次数
						List<UserXyxLog> list = userXyxLogDao.selectListByUserType(userId,1);
						if(times > list.size()){
							// 可以获得幸运星
							UserXyxLog userXyxLog = new UserXyxLog(userId, 1, 1, 1, new Date().getTime());
							userXyxLogDao.insert(userXyxLog);
							userFhxXyx.setXyxTotal(userFhxXyx.getXyxTotal() + 1);
							userFhxXyx.setUpdateTime(new Date().getTime());
							userFhxXyxDao.update(userFhxXyx);
						}
					}
				}
				break;
			}
		}
		return baseReward;
	}

	@Override
	public void xwCallbackInsertXYZ(XWCallback xwCallback, long reward) {
		xwCallback.setXy(reward);
		xwCallbackDao.insertXYZ(xwCallback);
	}

	@Override
	public XWCallback queryByXWOrderNumXYZ(String ordernum) {
		return xwCallbackDao.selectByOrderNumXYZ(ordernum);
	}

}