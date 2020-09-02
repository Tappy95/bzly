package com.mc.bzly.impl.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.DictionaryUtil;
import com.bzly.common.constant.InformConstant;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.passbook.MVipInfoDao;
import com.mc.bzly.dao.passbook.RSPassbookTaskDao;
import com.mc.bzly.dao.platform.PCashPriceConfigDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LUserActivityDao;
import com.mc.bzly.dao.user.LUserExchangeCashDao;
import com.mc.bzly.dao.user.LUserHippoDao;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.passbook.MVipInfo;
import com.mc.bzly.model.passbook.RSPassbookTask;
import com.mc.bzly.model.platform.PCashPriceConfig;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserActivity;
import com.mc.bzly.model.user.LUserAdsReward;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.MessageList;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.model.user.UserWithdrawals;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.user.LCoinChangeService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LCoinChangeService.class,version = WebConfig.dubboServiceVersion)
public class LCoinChangeServiceImpl implements LCoinChangeService{
	
    @Autowired
	private LCoinChangeDao lCoinChangeDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
	@Autowired
	private PDictionaryDao pDictionaryDao;
	@Autowired
	private LUserVipDao lUserVipDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	@Autowired
	private RSPassbookTaskDao rsPassbookTaskDao;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private LUserExchangeCashDao lUserExchangeCashDao;
	@Autowired
	private LUserHippoDao lUserHippoDao;
	@Autowired
	private PCashPriceConfigDao pCashPriceConfigDao;
	@Autowired
	private MVipInfoDao mVipInfoDao;
	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	@Autowired
	private LUserActivityDao lUserActivityDao;

	@Override
	public PageInfo<LCoinChange> queryList(LCoinChange lCoinChange) {
		PageHelper.startPage(lCoinChange.getPageNum(), lCoinChange.getPageSize());
		List<LCoinChange> lCoinChangeList = new ArrayList<LCoinChange>();
		if(lCoinChange.getAccountType() != null){
			if(lCoinChange.getAccountType().intValue() == 3){
				lCoinChangeList = lCoinChangeDao.selectListXYZ(lCoinChange);
			}
		}else{
			lCoinChangeList = lCoinChangeDao.selectList(lCoinChange);
		}
		return new PageInfo<>(lCoinChangeList);
	}

	@Override
	public PageInfo<LCoinChange> pageList(LCoinChange lCoinChange) {
		
		PageHelper.startPage(lCoinChange.getPageNum(), lCoinChange.getPageSize());
		List<LCoinChange> lCoinChangeList = lCoinChangeDao.selectChannelList(lCoinChange);
		return new PageInfo<>(lCoinChangeList);	
		
	}

	@Override
	public LCoinChange info(Integer id) {
		return lCoinChangeDao.selectOne(id);
	}

	@Override
	public List<MessageList> queryMessageList() {
		return lCoinChangeDao.selectMessageList();
	}
	
	@Transactional
	@Override
	public Result withdrawalsApply(LCoinChange lCoinChange,int ptype) {
		Result result = new Result();
		MUserInfo user=mUserInfoDao.selectOne(lCoinChange.getUserId());
		
		//查看用户姓名是否受限
		String[] nameAt=pDictionaryDao.selectByName(DictionaryUtil.CASH_LIMIT_NAME).getDicValue().split(",");
		for(String name:nameAt) {
			if(name.equals(user.getUserName())) {
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				return result;
			}
		}
		
		Boolean isDaren=false;
		int qualityScore=100;//质量分数
		
		
		if(user.getRoleType().intValue()==4) {
			isDaren=true;
		}else {
			List<MUserApprentice> apprenticeList=mUserApprenticeDao.selectListByUser(user.getUserId(),2);
			if(!StringUtil.isNullOrEmpty(apprenticeList) && apprenticeList.size()>0) {
				isDaren=true;
			}
		}
		
		
		if(isDaren) {
			PCashPriceConfig pCashPriceConfig=pCashPriceConfigDao.selectPriceOne(Integer.parseInt(lCoinChange.getAmount().toString()),1);
			if(StringUtil.isNullOrEmpty(pCashPriceConfig)) {
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				return result;
			}
			//验证提现是否超过次数
			int cashNum=lUserExchangeCashDao.selectCountUser(lCoinChange.getUserId());//今日提现次数
			int num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.DAREN_CASH_NUMBER).getDicValue());
			if(cashNum>=num) {
				result.setStatusCode(RespCodeState.EXCEED_CASH_NUM.getStatusCode());
				result.setMessage(RespCodeState.EXCEED_CASH_NUM.getMessage());
				return result;
			}
			//验证单次提现金额
			int minMoney=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.DAREN_CASH_MIN_MONEY).getDicValue());
			if(lCoinChange.getAmount().longValue()<minMoney) {
				result.setStatusCode(RespCodeState.DAREN_CASH_MIN_MONEY.getStatusCode());
				result.setMessage(RespCodeState.DAREN_CASH_MIN_MONEY.getMessage()+minMoney);
				return result;
			}
			//验证当天提现金额
			Double dayCash=lUserExchangeCashDao.selectUsetDayCash(lCoinChange.getUserId())+lCoinChange.getAmount();
			int maxMoney=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.DAREN_CASH_DAY_MAX_MONEY).getDicValue());
			if(dayCash>maxMoney) {
				result.setStatusCode(RespCodeState.DAREN_DAY_CASH_MAX.getStatusCode());
				result.setMessage(RespCodeState.DAREN_DAY_CASH_MAX.getMessage()+maxMoney);
				return result;
			}
			//获取质量分
			if(user.getOpenActivity().intValue()==2) {
				LUserActivity lUserActive=lUserActivityDao.selectByDate(DateUtil.getCurrentDate5(), user.getUserId());
				if(StringUtil.isNullOrEmpty(lUserActive)) {
					result.setStatusCode(RespCodeState.DAREN_NO_SCORE.getStatusCode());
					result.setMessage(RespCodeState.DAREN_NO_SCORE.getMessage()+minMoney);
					return result;
				}else {
					if(lUserActive.getQualityScore().intValue()<=39 && lUserActive.getQualityScore().intValue()>=30) {
						qualityScore=80;
					}else if(lUserActive.getQualityScore().intValue()<=29 && lUserActive.getQualityScore().intValue()>=20) {
						qualityScore=60;
					}else if(lUserActive.getQualityScore().intValue()<=19 && lUserActive.getQualityScore().intValue()>=10) {
						qualityScore=40;
					}else if(lUserActive.getQualityScore().intValue()<10){
						qualityScore=20;
					}
				}
			}
		}else {
			String channelCode=StringUtil.isNullOrEmpty(user.getChannelCode())?user.getParentChannelCode():user.getChannelCode();
			if(StringUtil.isNullOrEmpty(channelCode)) {
				channelCode="baozhu";
			}
			int countVip=lUserVipDao.selectUserCount(lCoinChange.getUserId());
			if("zhongqing".equals(channelCode)) {//中青赚点提现规则
				List<MVipInfo> userVips=mVipInfoDao.selectZqVipUser(lCoinChange.getUserId());
				if(userVips.size()<=0) {
					result.setStatusCode(RespCodeState.VIP_BUY.getStatusCode());
					result.setMessage(RespCodeState.VIP_BUY.getMessage());
					return result;
				}else {
					int quota=-1;
					for(MVipInfo v:userVips) {
						if(v.getCashMoney().intValue()==-1) {
							quota=-1;
							break;
						}else {
							if(quota<v.getCashMoney().intValue()) {
								quota=v.getCashMoney();	
							}
						}
					}
					if(quota!=-1) {
						long money=lUserExchangeCashDao.selectSumMoney(lCoinChange.getUserId());
						if(money+lCoinChange.getAmount()>quota) {
							result.setStatusCode(RespCodeState.CASH_QUOTA.getStatusCode());
							result.setMessage(RespCodeState.CASH_QUOTA.getMessage());
							return result;
						}
					}
				}
			}
			

			if(countVip<=0) {
				if("lee".equals(channelCode) && lCoinChange.getAmount().intValue()==1) {
					
				}else {
					PCashPriceConfig pCashPriceConfig=pCashPriceConfigDao.selectPriceOne(Integer.parseInt(lCoinChange.getAmount().toString()),1);
					if(StringUtil.isNullOrEmpty(pCashPriceConfig) || pCashPriceConfig.getIsTask().intValue()==1) {
						result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
						result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
						return result;
					}
				}
					
			}
			
			//验证提现是否超过次数
			int cashNum=lUserExchangeCashDao.selectCountUser(lCoinChange.getUserId());//今日提现次数
			int num=0;
			if(countVip>0) {
				num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIP_DAY_CASH_NUMBER).getDicValue());
			}else {
				if(user.getRoleType()>1) {
					num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.TEAM_DAY_CASH_NUMBER).getDicValue());
				}else {
					num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.USER_DAY_CASH_NUMBER).getDicValue());
				}
			}
			if(cashNum>=num) {
				result.setStatusCode(RespCodeState.EXCEED_CASH_NUM.getStatusCode());
				result.setMessage(RespCodeState.EXCEED_CASH_NUM.getMessage());
				return result;
			}
		}
		
		
		
		int aliCount=mUserInfoDao.selectAliNum(user.getAliNum());
		int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
		if(aliCount>count) {
			result.setStatusCode(RespCodeState.ALI_ACCOUNT_ERROR.getStatusCode());
			result.setMessage(RespCodeState.ALI_ACCOUNT_ERROR.getMessage());
			return result;
		}
		
		long now = new Date().getTime();
		lCoinChange.setChangedTime(now);
		lCoinChange.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_3);
		//MUserInfo mUserInfo = mUserInfoDao.selectOne(lCoinChange.getUserId());
		long actualAmount=(long)Math.floor(lCoinChange.getAmount()*qualityScore/100.0);
		
		long coin_to_money = new Long(pDictionaryDao.selectByName("coin_to_money").getDicValue());
		long needCoin = lCoinChange.getAmount() * coin_to_money; // 最终抵扣金币
		if(needCoin<=0) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			return result;
		}
		if(user.getCoin().longValue() < needCoin ){
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			return result;
		}
		
		/*boolean flag = true;*/ // 是首次提现
		/*boolean flagOneWithdrawals = false; // 能一元提现 
		List<Map<String,Object>> vips = lUserVipDao.selectByUserAll(lCoinChange.getUserId());
		if(vips != null && vips.size() > 0){
			// VIP可以并且拥有一元提现特权
			if(((int)vips.get(0).get("status") == 1) && ((int)vips.get(0).get("oneWithdrawals") == 1)){
				flagOneWithdrawals = true;
			}
		}
		if(!flagOneWithdrawals){ // 根据用户角色来判断是否有一元提现
			if(mUserInfo.getRoleType() > 1){
				flagOneWithdrawals = true;
			}
		}*/
		/*List<LCoinChange> lCoinChanges = lCoinChangeDao.selectByChangeType(lCoinChange.getUserId(), lCoinChange.getChangedType());
		if(lCoinChanges.size() > 0){
			flag = false; // 不是首次提现
		}*/
		/*if(!flagOneWithdrawals){ // 不能一元提现，需要判断提现金额与限额大小
			// 设置提现金额为字典配置
			if(mUserInfo.getRoleType() == 1){
				String channelCode=mUserInfo.getChannelCode();
				if(StringUtil.isNullOrEmpty(channelCode)) {
					if(StringUtil.isNullOrEmpty(mUserInfo.getParentChannelCode())) {
						channelCode="baozhu";
					}else {
						channelCode=mUserInfo.getParentChannelCode();
					}
				}
				MChannelConfig mChannelConfig = mChannelConfigDao.selectByChannelCode(channelCode); // 获取渠道配置
				MFissionScheme mFissionScheme = MFissionSchemeDao.selectOne(mChannelConfig.getFissionId());
				double ordinaryExchange = mFissionScheme.getOrdinaryExchange();
				if(amount < ordinaryExchange){
					result.setStatusCode(RespCodeState.WITHDRAWALS_LIMIT.getStatusCode());
					result.setMessage(RespCodeState.WITHDRAWALS_LIMIT.getMessage());
					return result;
				}
			}
		}else{
			if((BigDecimal.ONE).compareTo(new BigDecimal(amount)) > 0){
				result.setStatusCode(RespCodeState.WITHDRAWALS_LIMIT.getStatusCode());
				result.setMessage(RespCodeState.WITHDRAWALS_LIMIT.getMessage());
				return result;
			}
		}*/
		lCoinChange.setActualAmount(actualAmount);
		lCoinChange.setAmount(needCoin);
		jmsProducer.userCash(Type.USER_CASH, lCoinChange);
		/*// 开始抵扣，插入金币申请表，发送消息
		mUserInfo.setCoin(mUserInfo.getCoin() - needCoin); 
		mUserInfo.setUpdateTime(now);
		mUserInfoDao.update(mUserInfo);
		lCoinChange.setChangedTime(now);
		lCoinChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
		lCoinChange.setAmount(needCoin);
		lCoinChange.setStatus(2);
		lCoinChange.setCoinBalance(mUserInfo.getCoin());
		lCoinChangeDao.insertCoin(lCoinChange);
		String outTradeNo = Base64.getOutTradeNo();
		LUserExchangeCash lUserExchangeCash=new LUserExchangeCash();
		int actualAmount=(int)(needCoin/coin_to_money);
		lUserExchangeCash.setCoinChangeId(lCoinChange.getId());
		lUserExchangeCash.setUserId(lCoinChange.getUserId());
		lUserExchangeCash.setOutTradeNo(outTradeNo);
		lUserExchangeCash.setCoin(needCoin);
		lUserExchangeCash.setActualAmount(new BigDecimal(actualAmount));
		lUserExchangeCash.setServiceCharge(new BigDecimal(needCoin/10000.0-needCoin/coin_to_money));
		lUserExchangeCash.setCoinToMoney((int)coin_to_money);
		lUserExchangeCash.setCreatorTime(new Date().getTime());
		lUserExchangeCash.setState(1);
		lUserExchangeCash.setCoinBalance(mUserInfo.getCoin());
		lUserExchangeCash.setRealName(mUserInfo.getUserName());
		if(lCoinChange.getAccountType().intValue()==2) {
			lUserExchangeCash.setBankAccount(mUserInfo.getAliNum());
		}else if(lCoinChange.getAccountType().intValue()==1) {
			lUserExchangeCash.setBankAccount(mUserInfo.getOpenId());
		}
		result=cashTask(actualAmount,flag,mUserInfo,now,lCoinChange,lUserExchangeCash.getId());
		int money=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CASH_QUOTA).getDicValue());
		if(actualAmount<=money) {
			jmsProducer.userCash(Type.CASH_PAY, lUserExchangeCash.getId());	
			lUserExchangeCash.setIsLocking(1);
			lUserExchangeCash.setLockingMobile("1");
		}else {
			lUserExchangeCash.setIsLocking(2);
		}
		lUserExchangeCashDao.insert(lUserExchangeCash);
		if(flag){
			if(StringUtils.isNotEmpty(mUserInfo.getReferrer())){
				Map<String, String> map = new HashMap<String, String>();
				map.put("referrerId", mUserInfo.getReferrer());
				map.put("userId", mUserInfo.getUserId());
				jmsProducer.sendMessage(Type.USER_FIRST_WITHDRAWALS, map);
    		}
    		// 修改用户账户完成提现任务
    		LUserTask lUserTask = new LUserTask(mUserInfo.getUserId(), 4, now);
    		lUserTaskDao.insert(lUserTask);
    		// 修改用户账户金币
    		MTaskInfo mTaskInfo = mTaskInfoDao.selectOne(4);
    		mUserInfo = mUserInfoDao.selectOne(lCoinChange.getUserId());
    		long baseReward = mTaskInfo.getReward();
    		long jcpassbookReward = 0l;
    		int jcrspassbookId = 0;
    		
    		long fbpassbookReward = 0l;
    		int fbrspassbookId = 0;
    		 
    		// 查看用户是否有加成券
    		List<Map<String, Object>> jcpassbooks = rsUserPassbookDao.selectByUser(lCoinChange.getUserId(), ConstantUtil.PASSBOOK_TYPE_3);
    		for (Map<String, Object> map : jcpassbooks) {
				// 判断加成券是否可用
    			Integer passbookId = new Integer(map.get("id").toString());
    			RSPassbookTask rs = rsPassbookTaskDao.selectByPassbookTask(passbookId,mTaskInfo.getTaskType()); 
    			if(rs!= null){
    				jcrspassbookId = (int) map.get("rsId");
    				long passbook_value = new Integer(map.get("passbook_value").toString());
    				jcpassbookReward = baseReward *  passbook_value / 100;
    				break;
    			}
			}
    		// 查看用户是否有翻倍券
    		List<Map<String, Object>> fbpassbooks = rsUserPassbookDao.selectByUser(lCoinChange.getUserId(), ConstantUtil.PASSBOOK_TYPE_1);
    		for (Map<String, Object> map : fbpassbooks) {
    			// 判断加成券是否可用
    			Integer passbookId = new Integer(map.get("id").toString());
    			RSPassbookTask rs = rsPassbookTaskDao.selectByPassbookTask(passbookId,mTaskInfo.getTaskType()); 
    			if(rs!= null){
    				fbrspassbookId = (int) map.get("rsId");
    				long passbook_value = new Integer(map.get("passbook_value").toString());
    				fbpassbookReward = baseReward * passbook_value;
    				break;
    			}
			}
    		long reward = baseReward + jcpassbookReward + fbpassbookReward;
    		mUserInfo.setUpdateTime(now);
    		mUserInfo.setCoin(mUserInfo.getCoin() + reward);
    		mUserInfoDao.update(mUserInfo);
    		// 设置加成券过期
    		if(jcpassbookReward > 0){
    			RSUserPassbook rsUserPassbook = new RSUserPassbook();
    			rsUserPassbook.setId(jcrspassbookId);
    			rsUserPassbook.setStatus(2);
    			rsUserPassbookDao.update(rsUserPassbook);
    		}
    		// 设置翻倍券过期
    		if(fbpassbookReward > 0){
    			RSUserPassbook rsUserPassbook = new RSUserPassbook();
    			rsUserPassbook.setId(fbrspassbookId);
    			rsUserPassbook.setStatus(2);
    			rsUserPassbookDao.update(rsUserPassbook);
    		}
    		// 新增用户金币变动记录
			LCoinChange lCoinChange2 = new LCoinChange(mUserInfo.getUserId(), reward, ConstantUtil.FLOWTYPE_IN,
							ConstantUtil.COIN_CHANGED_TYPE_10, now,1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange2);
		}*/
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}
	
	public Result cashTask(int actualAmount,Boolean flag,MUserInfo mUserInfo,long now,LCoinChange lCoinChange,int cashId) {
		Result result = new Result();
		int money=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.CASH_QUOTA).getDicValue());
		if(actualAmount<=money) {
			jmsProducer.userCash(Type.CASH_PAY, cashId);	
		}
		if(flag){
			if(StringUtils.isNotEmpty(mUserInfo.getReferrer())){
				Map<String, String> map = new HashMap<String, String>();
				map.put("referrerId", mUserInfo.getReferrer());
				map.put("userId", mUserInfo.getUserId());
				jmsProducer.sendMessage(Type.USER_FIRST_WITHDRAWALS, map);
    		}
    		// 修改用户账户完成提现任务
    		LUserTask lUserTask = new LUserTask(mUserInfo.getUserId(), 4, now);
    		lUserTaskDao.insert(lUserTask);
    		// 修改用户账户金币
    		MTaskInfo mTaskInfo = mTaskInfoDao.selectOne(4);
    		mUserInfo = mUserInfoDao.selectOne(lCoinChange.getUserId());
    		long baseReward = mTaskInfo.getReward();
    		long jcpassbookReward = 0l;
    		int jcrspassbookId = 0;
    		
    		long fbpassbookReward = 0l;
    		int fbrspassbookId = 0;
    		 
    		// 查看用户是否有加成券
    		List<Map<String, Object>> jcpassbooks = rsUserPassbookDao.selectByUser(lCoinChange.getUserId(), ConstantUtil.PASSBOOK_TYPE_3);
    		for (Map<String, Object> map : jcpassbooks) {
				// 判断加成券是否可用
    			Integer passbookId = new Integer(map.get("id").toString());
    			RSPassbookTask rs = rsPassbookTaskDao.selectByPassbookTask(passbookId,mTaskInfo.getTaskType()); 
    			if(rs!= null){
    				jcrspassbookId = (int) map.get("rsId");
    				long passbook_value = new Integer(map.get("passbook_value").toString());
    				jcpassbookReward = baseReward *  passbook_value / 100;
    				break;
    			}
			}
    		// 查看用户是否有翻倍券
    		List<Map<String, Object>> fbpassbooks = rsUserPassbookDao.selectByUser(lCoinChange.getUserId(), ConstantUtil.PASSBOOK_TYPE_1);
    		for (Map<String, Object> map : fbpassbooks) {
    			// 判断加成券是否可用
    			Integer passbookId = new Integer(map.get("id").toString());
    			RSPassbookTask rs = rsPassbookTaskDao.selectByPassbookTask(passbookId,mTaskInfo.getTaskType()); 
    			if(rs!= null){
    				fbrspassbookId = (int) map.get("rsId");
    				long passbook_value = new Integer(map.get("passbook_value").toString());
    				fbpassbookReward = baseReward * passbook_value;
    				break;
    			}
			}
    		long reward = baseReward + jcpassbookReward + fbpassbookReward;
    		mUserInfo.setUpdateTime(now);
    		mUserInfo.setCoin(mUserInfo.getCoin() + reward);
    		mUserInfoDao.update(mUserInfo);
    		// 设置加成券过期
    		if(jcpassbookReward > 0){
    			RSUserPassbook rsUserPassbook = new RSUserPassbook();
    			rsUserPassbook.setId(jcrspassbookId);
    			rsUserPassbook.setStatus(2);
    			rsUserPassbookDao.update(rsUserPassbook);
    		}
    		// 设置翻倍券过期
    		if(fbpassbookReward > 0){
    			RSUserPassbook rsUserPassbook = new RSUserPassbook();
    			rsUserPassbook.setId(fbrspassbookId);
    			rsUserPassbook.setStatus(2);
    			rsUserPassbookDao.update(rsUserPassbook);
    		}
    		// 新增用户金币变动记录
			LCoinChange lCoinChange2 = new LCoinChange(mUserInfo.getUserId(), reward, ConstantUtil.FLOWTYPE_IN,
							ConstantUtil.COIN_CHANGED_TYPE_10, now,1,"",mUserInfo.getCoin());
			lCoinChangeDao.insert(lCoinChange2);
		}
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public PageInfo<UserWithdrawals> queryWithdrawals(UserWithdrawals userWithdrawals) {
		PageHelper.startPage(userWithdrawals.getPageNum(), userWithdrawals.getPageSize());
		List<UserWithdrawals> userWithdrawalsList = lCoinChangeDao.selectWithdrawals(userWithdrawals);
		return new PageInfo<>(userWithdrawalsList);
	}

	@Override
	public UserWithdrawals queryWithdrawalsInfo(Integer id) {
		return lCoinChangeDao.selectWithdrawalsInfo(id);
	}

	@Transactional
	@Override
	public int audit(LCoinChange lCoinChange) {
		long now = new Date().getTime();
		LCoinChange lCoinChangeOld = lCoinChangeDao.selectOne(lCoinChange.getId());
		if(lCoinChangeOld.getChangedType().intValue() != ConstantUtil.COIN_CHANGED_TYPE_3){
			return 0;
		}
		if(lCoinChangeOld.getStatus().intValue() != 2){
			return 2;
		}
		lCoinChange.setAuditTime(now);
		// 1.如果通过，修改金币记录状态
		if(lCoinChange.getStatus().intValue() == 1){
			// 发送提现到账消息
			AppNewsInform appNewsInform=new AppNewsInform();
			appNewsInform.setUserId(lCoinChangeOld.getUserId());
			appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_WITHDRAWALS);
			appNewsInform.setInformContent(InformConstant.PUSH_CONTENT_SUCCESS);
			appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
			appNewsInformService.addPush(appNewsInform);
		}else{
			MUserInfo mUserInfo = mUserInfoDao.selectOne(lCoinChangeOld.getUserId());
			//插入金币变动记录
			LCoinChange coin=new LCoinChange();
			coin.setUserId(lCoinChangeOld.getUserId());
			coin.setAmount(lCoinChangeOld.getAmount().longValue());
			coin.setFlowType(1);
			coin.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_14);     
			coin.setChangedTime(new Date().getTime());
			coin.setCoinBalance(mUserInfo.getCoin().longValue() + lCoinChangeOld.getAmount().longValue());
			lCoinChangeDao.insert(coin);
			// 2.如果不通过，返还金币，修改状态
			mUserInfo.setCoin(mUserInfo.getCoin().longValue() + lCoinChangeOld.getAmount().longValue());
			mUserInfo.setUpdateTime(now);
			mUserInfoDao.update(mUserInfo);
			// 发送提现不通过消息
			AppNewsInform appNewsInform=new AppNewsInform();
			appNewsInform.setUserId(lCoinChangeOld.getUserId());
			appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_WITHDRAWALS);
			appNewsInform.setInformContent(InformConstant.PUSH_CONTENT_REFUSE);
			appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
			appNewsInformService.addPush(appNewsInform);
		}
		lCoinChangeDao.update(lCoinChange);
		return 1;
	}

	@Override
	public Integer coinExchangePig(long coin, String userId) {
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		if(mUserInfo.getCoin()<coin) {
			return 2;//账户余额不足
		}
		if(coin <= 0) {
			return 2;//账户余额不足
		}
		Map<String,Object> jmsData=new HashMap<>();
		jmsData.put("coin", coin);
		jmsData.put("userId", userId);
		jmsProducer.coinExchangePig(Type.COIN_PIG, jmsData);
		
	    // 查询待完成的签到记录 5天，10天，15天
	    /*jmsProducer.sendMessage(Type.EXCHANGE_COIN_TASK_QDZ, userId);*/
		return 3;//兑换成功
	}

	@Override
	public int readZXReward(String userId) {
		long now = new Date().getTime();
		// 资讯奖励金币数
		String coinRewardStr = pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD).getDicValue();
		long coinReward = new Long(coinRewardStr).longValue();
		// 资讯奖励金币限额
		String coinRewardLimitStr = pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD_LIMIT).getDicValue();
		long coinRewardLimit = new Long(coinRewardLimitStr).longValue();
		// 获取当日毫秒值
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = now - (now % onedayMillisecond);
		// 查询用户今日获取了多少金币
		double sum = lCoinChangeDao.selectUserReadZXRewardSum(userId,totayMillisecond);
		if(sum  >= coinRewardLimit){
			return 1;
		}
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		mUserInfo.setCoin(mUserInfo.getCoin()+coinReward);
		mUserInfoDao.updatecCoin(coinReward, userId);
		LCoinChange lCoinChange = new LCoinChange(userId, coinReward, ConstantUtil.FLOWTYPE_IN,
				ConstantUtil.COIN_CHANGED_TYPE_13, now, 1,"",mUserInfo.getCoin());
		lCoinChangeDao.insert(lCoinChange);
		return 2;
	}
	
	@Override
	public int readNewZXReward(String userId,Long now) {
		LCoinChange coin=lCoinChangeDao.selectByChangeTypeTime(userId, ConstantUtil.COIN_CHANGED_TYPE_13, now);
		if(!StringUtil.isNullOrEmpty(coin)) {
			return 3;
		}
		long nowToday = new Date().getTime();
		// 资讯奖励金币数
		String coinRewardStr = pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD).getDicValue();
		long coinReward = new Long(coinRewardStr).longValue();
		// 资讯奖励金币限额
		String coinRewardLimitStr = pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD_LIMIT).getDicValue();
		long coinRewardLimit = new Long(coinRewardLimitStr).longValue();
		// 获取当日毫秒值
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = nowToday - (nowToday % onedayMillisecond);
		// 查询用户今日获取了多少金币
		double sum = lCoinChangeDao.selectUserReadZXRewardSum(userId,totayMillisecond);
		if(sum  >= coinRewardLimit){
			return 1;
		}
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		mUserInfo.setCoin(mUserInfo.getCoin()+coinReward);
		mUserInfoDao.updatecCoin(coinReward, userId);
		LCoinChange lCoinChange = new LCoinChange(userId, coinReward, ConstantUtil.FLOWTYPE_IN, 
				ConstantUtil.COIN_CHANGED_TYPE_13, now, 1,"",mUserInfo.getCoin());
		lCoinChangeDao.insert(lCoinChange);
		return 2;
	}

	@Override
	public Map<String, Object> getZXConifg(String userId) {
		// 获取当日毫秒值
		long now = new Date().getTime();
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = now - (now % onedayMillisecond);
		Map<String,Object> data=new HashMap<>();
		// 资讯停留秒数
		data.put("zx_stop_second", pDictionaryDao.selectByName(DictionaryUtil.ZX_STOP_SECOND).getDicValue());
		// 资讯奖励金币数
		data.put("zx_coin_reward", pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD).getDicValue());
		// 资讯奖励金币限额
		String coinRewardLimitStr = pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD_LIMIT).getDicValue();
		long coinRewardLimit = new Long(coinRewardLimitStr).longValue();
		data.put("zx_coin_reward_limit", coinRewardLimit);
		
		double sum = lCoinChangeDao.selectUserReadZXRewardSum(userId,totayMillisecond);
		if(sum  >= coinRewardLimit){
			data.put("get_limit", 1);
		}else{
			data.put("get_limit", 2);
		}
		return data;
	}
	
	@Override
	public Map<String, Object> getApprenticeReward(String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Double d = lCoinChangeDao.getApprenticeReward(userId);
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		result.put("reward", d.longValue());
		result.put("apprentice", mUserInfo.getApprentice());
		return result;
	}

	@Override
	public PageInfo<LCoinChange> getApprenticeRewardDetail(LCoinChange lCoinChange) {
		PageHelper.startPage(lCoinChange.getPageNum(), lCoinChange.getPageSize());
		List<LCoinChange> lCoinChangeList = lCoinChangeDao.getApprenticeRewardDetail(lCoinChange);
		return new PageInfo<>(lCoinChangeList);
	}

	@Override
	public List<Map<String,Object>> exclCoinChange(LCoinChange lCoinChange) {
		if(!StringUtil.isNullOrEmpty(lCoinChange.getAccountId()) || !StringUtil.isNullOrEmpty(lCoinChange.getMobile())) {
			MUserInfo mUserInfo=new MUserInfo();
			mUserInfo.setAccountId(lCoinChange.getAccountId());
			mUserInfo.setMobile(lCoinChange.getMobile());
			MUserInfo user=mUserInfoDao.selectBackstage(mUserInfo);
			if(StringUtil.isNullOrEmpty(user)) {
				return null;
			}else {
				lCoinChange.setUserId(user.getUserId());
			}
		}
		List<String> userIds = new ArrayList<String>();
		if(!"baozhu".equals(lCoinChange.getChannelCode())){
			userIds = mUserInfoDao.selectUserRelation(lCoinChange.getUserRelation(), lCoinChange.getChannelCode());
		}
		if(userIds.size() > 0){
			lCoinChange.setUserIds(userIds);
		}
		return lCoinChangeDao.exclCoinChangeNew(lCoinChange);
	}
	
	@Override
	public Map<String, Object> getRewardConifg(String userId, Integer type,String content) {
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当日毫秒值
		long now = new Date().getTime();
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = now - (now % onedayMillisecond);
		Map<String,Object> data=new HashMap<>();
		String coinRewardDic = DictionaryUtil.ZX_COIN_REWARD;
		String coinStopSecondDic = DictionaryUtil.ZX_STOP_SECOND;
		String coinRewardLimitDic = DictionaryUtil.ZX_COIN_REWARD_LIMIT;
		if(type == ConstantUtil.COIN_CHANGED_TYPE_19){
			coinRewardDic = DictionaryUtil.GG_COIN_REWARD;
			coinStopSecondDic = DictionaryUtil.GG_STOP_SECOND;
			coinRewardLimitDic = DictionaryUtil.GG_COIN_REWARD_LIMIT;
		}else if(type == ConstantUtil.COIN_CHANGED_TYPE_20){
			coinRewardDic = DictionaryUtil.FS_COIN_REWARD;
			coinRewardLimitDic = DictionaryUtil.FS_COIN_REWARD_LIMIT;
		}
		// 资讯停留秒数
		data.put("coin_reward", pDictionaryDao.selectByName(coinRewardDic).getDicValue());
		data.put("zx_coin_reward", pDictionaryDao.selectByName(DictionaryUtil.ZX_COIN_REWARD).getDicValue());
		data.put("gg_coin_reward", pDictionaryDao.selectByName(DictionaryUtil.GG_COIN_REWARD).getDicValue());
		data.put("fs_coin_reward", pDictionaryDao.selectByName(DictionaryUtil.FS_COIN_REWARD).getDicValue());
		// 资讯奖励金币数
		data.put("stop_second", pDictionaryDao.selectByName(coinStopSecondDic).getDicValue());
		// 资讯奖励金币限额
		String coinRewardLimitStr = pDictionaryDao.selectByName(coinRewardLimitDic).getDicValue();
		long coinRewardLimit = new Long(coinRewardLimitStr).longValue();
		double sum = 0;
		double zxSum = lCoinChangeDao.selectUserReadRewardSum(userId,totayMillisecond,ConstantUtil.COIN_CHANGED_TYPE_13);
		double ggSum = lCoinChangeDao.selectUserReadRewardSum(userId,totayMillisecond,ConstantUtil.COIN_CHANGED_TYPE_19);
		double fsSum = lCoinChangeDao.selectUserReadRewardSum(userId,totayMillisecond,ConstantUtil.COIN_CHANGED_TYPE_20);
		if(type == ConstantUtil.COIN_CHANGED_TYPE_13){
			sum = zxSum;
		}else if(type == ConstantUtil.COIN_CHANGED_TYPE_19) {
			sum = ggSum;
		}else{
			sum = fsSum;
		}
		if(sum  >= coinRewardLimit){
			data.put("get_limit", 1);
		}else{
			data.put("get_limit", 2);
		}
		data.put("has_get",zxSum + ggSum + fsSum);
		// 判断改广告是否已经拿到过奖励
		data.put("can_send", 2);
		if(StringUtils.isNotBlank(content)){
			String rewardDate = sdf5.format(new Date());
			LUserAdsReward lUserAdsReward = new LUserAdsReward(userId, content, rewardDate, 1);
			long count = lUserHippoDao.selectCount(lUserAdsReward);
			if(count > 0){
				data.put("can_send", 1);
			}
		}
		return data;
	}

	@Override
	public int readReward(String userId, Long now, Integer type,String content) {
		SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
		LCoinChange coin=lCoinChangeDao.selectByChangeTypeTime(userId, type, now);
		if(!StringUtil.isNullOrEmpty(coin)) {
			return 3;
		}
		long nowToday = new Date().getTime();
		String coinRewardDic = DictionaryUtil.ZX_COIN_REWARD;
		String coinRewardLimitDic = DictionaryUtil.ZX_COIN_REWARD_LIMIT;
		if(type == ConstantUtil.COIN_CHANGED_TYPE_19){
			coinRewardDic = DictionaryUtil.GG_COIN_REWARD;
			coinRewardLimitDic = DictionaryUtil.GG_COIN_REWARD_LIMIT;
		}else if(type == ConstantUtil.COIN_CHANGED_TYPE_20){
			coinRewardDic = DictionaryUtil.FS_COIN_REWARD;
			coinRewardLimitDic = DictionaryUtil.FS_COIN_REWARD_LIMIT;
		}
		// 资讯奖励金币数
		String coinRewardStr = pDictionaryDao.selectByName(coinRewardDic).getDicValue();
		long coinReward = new Long(coinRewardStr).longValue();
		// 资讯奖励金币限额
		String coinRewardLimitStr = pDictionaryDao.selectByName(coinRewardLimitDic).getDicValue();
		long coinRewardLimit = new Long(coinRewardLimitStr).longValue();
		// 获取当日毫秒值
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = nowToday - (nowToday % onedayMillisecond);
		String str = new AWorker<String>() {
			@Override
			public String call(String request) throws Exception {
				LCoinChange coin=lCoinChangeDao.selectByChangeTypeTime(userId, type, now);
				if(!StringUtil.isNullOrEmpty(coin)) {
					return "3";
				}
				// 查询用户今日获取了多少金币
				double sum = lCoinChangeDao.selectUserReadRewardSum(userId,totayMillisecond,type);
				if(sum  >= coinRewardLimit){
					return "1";
				}
				MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
				mUserInfo.setCoin(mUserInfo.getCoin()+coinReward);
				mUserInfoDao.updatecCoin(coinReward, userId);
				LCoinChange lCoinChange = new LCoinChange(userId, coinReward, ConstantUtil.FLOWTYPE_IN, type, now, 1,"",mUserInfo.getCoin());
				lCoinChangeDao.insert(lCoinChange);
				return "2";
			}
		}.execute(userId, "redis_lock_read_news");
		if("3".equals(str)){
			return 3;
		}else if("1".equals(str)){
			return 1;
		}
		if(StringUtils.isNotBlank(content)){
			String rewardDate = sdf5.format(new Date());
			LUserAdsReward lUserAdsReward = new LUserAdsReward(userId, content, rewardDate, 1);
			lUserHippoDao.insertAdsReward(lUserAdsReward);
		}
		return 2;
	}

	@Override
	public List<MessageList> queryqdzList() {
		List<MessageList> resultList = lCoinChangeDao.selectqdzList();
		if(resultList.size() < 5){
			int num =  5 - resultList.size();
			int size = resultList.size();
			List<MessageList> messageList = lCoinChangeDao.selectMessageList();
			for (int i = 0; i < num;i++) {
				for (int j = 0; j < size; j++) {
					if(resultList.get(j).getId().intValue() != messageList.get(i).getId().intValue()){
						resultList.add(messageList.get(i));
					}else{
						++num;
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public Map<String, Object> selectList(LCoinChange lCoinChange) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lCoinChange.getAccountId()) || !StringUtil.isNullOrEmpty(lCoinChange.getMobile())) {
			MUserInfo mUserInfo=new MUserInfo();
			mUserInfo.setAccountId(lCoinChange.getAccountId());
			mUserInfo.setMobile(lCoinChange.getMobile());
			MUserInfo user=mUserInfoDao.selectBackstage(mUserInfo);
			if(StringUtil.isNullOrEmpty(user)) {
				result.put("res", "2");
				return result;
			}else {
				lCoinChange.setUserId(user.getUserId());
			}
		}
		lCoinChange.setPageIndex(lCoinChange.getPageSize() * (lCoinChange.getPageNum() - 1));
		List<String> userIds = new ArrayList<String>();
		if(!"baozhu".equals(lCoinChange.getChannelCode())){
			userIds = mUserInfoDao.selectUserRelation(lCoinChange.getUserRelation(), lCoinChange.getChannelCode());
		}
		if(userIds.size() > 0){
			lCoinChange.setUserIds(userIds);
		}
		List<LCoinChange> lCoinChangeList = lCoinChangeDao.selectPageListNew(lCoinChange);
		List<Map<String,Object>> subTotalMap=lCoinChangeDao.selectSumNew(lCoinChange);
		//List<Map<String,Object>> countMap = lCoinChangeDao.selectCountNew(lCoinChange);
		
		Map<String,Object> countRevenue = lCoinChangeDao.selectCountNewRevenue(lCoinChange);
		Map<String,Object> countExpend = lCoinChangeDao.selectCountNewExpend(lCoinChange);
		
		result.put("list", lCoinChangeList);
		result.put("subRevenuePrice", 0);
		result.put("subExpendPrice", 0);
		int pageCount=0;
		for(int i=0;i<subTotalMap.size();i++) {
			if("1".equals(subTotalMap.get(i).get("flowType").toString())) {
				result.put("subRevenuePrice", subTotalMap.get(i).get("amount"));
				pageCount+=Integer.parseInt(subTotalMap.get(i).get("cou").toString());
			}else {
				result.put("subExpendPrice", subTotalMap.get(i).get("amount"));
				pageCount+=Integer.parseInt(subTotalMap.get(i).get("cou").toString());
			}
			
		}
		result.put("totalRevenuePrice", 0);
		result.put("totalExpendPrice", 0);
		int total=0;
		result.put("totalRevenuePrice", countRevenue.get("amount"));
		total+=Integer.parseInt(countRevenue.get("cou").toString());
		
		result.put("totalExpendPrice", countExpend.get("amount"));
		total+=Integer.parseInt(countExpend.get("cou").toString());
		/*for(int n=0;n<countMap.size();n++) {
			if("1".equals(countMap.get(n).get("flowType").toString())) {
				result.put("totalRevenuePrice", countMap.get(n).get("amount"));
				total+=Integer.parseInt(countMap.get(n).get("cou").toString());
			}else {
				result.put("totalExpendPrice", countMap.get(n).get("amount"));
				total+=Integer.parseInt(countMap.get(n).get("cou").toString());
			}
			
		}*/
		result.put("pageCount", pageCount);
		result.put("total", total);
		return result;
	}
	
	@Override
	public List<MessageList> querykszList() {
		List<MessageList> resultList = lCoinChangeDao.selectkszList();
		if(resultList.size() < 5){
			int num =  5 - resultList.size();
			int size = resultList.size();
			List<MessageList> messageList = lCoinChangeDao.selectMessageList();
			for (int i = 0; i < num;i++) {
				for (int j = 0; j < size; j++) {
					if(resultList.get(j).getId().intValue() != messageList.get(i).getId().intValue()){
						resultList.add(messageList.get(i));
					}else{
						++num;
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public int cashNum(String userId) {
		MUserInfo user=mUserInfoDao.selectOne(userId);
		int vipNum=lUserVipDao.selectCountVip(userId);
		int num=0;
		if(vipNum>0) {
			num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIP_DAY_CASH_NUMBER).getDicValue());
		}else {
			if(user.getRoleType()>1) {
				num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.TEAM_DAY_CASH_NUMBER).getDicValue());
			}else {
				num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.USER_DAY_CASH_NUMBER).getDicValue());
			}
		}
		return num;
	}

	@Override
	public Map<String, Object> xyxRewardConifg(String userId) {
		// 获取当日毫秒值
		long now = new Date().getTime();
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = now - (now % onedayMillisecond);
		Map<String,Object> data=new HashMap<>();
		// 小游戏奖励次数
		String xyx_day_reward_times = pDictionaryDao.selectByName(DictionaryUtil.XYX_DAY_REWARD_TIMES).getDicValue();
		// 小游戏奖励金币数
		data.put("xyx_times_reward_coin", new Long(pDictionaryDao.selectByName(DictionaryUtil.XYX_TIMES_REWARD_COIN).getDicValue()).longValue());
		// 资讯奖励金币限额
		data.put("xyx_times_limit_second", new Long(pDictionaryDao.selectByName(DictionaryUtil.XYX_TIMES_LIMIT_SECOND).getDicValue()).longValue());
		long coinRewardLimit = new Long(xyx_day_reward_times).longValue();
		data.put("xyx_day_reward_times", coinRewardLimit);
		
		long count = lCoinChangeDao.selectUserXYXRewardSum(userId,totayMillisecond);
		if(count  >= coinRewardLimit){
			data.put("get_limit", 1);
		}else{
			data.put("get_limit", 2);
		}
		return data;
	}

	@Override
	public Result getXYXReward(String userId, Long seconds) {
		Result result = new Result();
		// 获取当日毫秒值
		long now = new Date().getTime();
		long onedayMillisecond =  24*60*60*1000l; // 这个l必须加，否则可能超出long类型的取值范围
		long totayMillisecond = now - (now % onedayMillisecond);
		long count = lCoinChangeDao.selectUserXYXRewardSum(userId,totayMillisecond);
		// 小游戏奖励次数
		String xyx_day_reward_times = pDictionaryDao.selectByName(DictionaryUtil.XYX_DAY_REWARD_TIMES).getDicValue();
		long coinRewardLimit = new Long(xyx_day_reward_times).longValue();
		// 1.判断今日是否已达到领取次数
		if(count  >= coinRewardLimit){
			result.setStatusCode(RespCodeState.JJ_COUNT_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.JJ_COUNT_NOT_ENOUGH.getMessage());
			return result;
		}
		// 2.判断时长是否充足
		String xyx_times_limit_second = pDictionaryDao.selectByName(DictionaryUtil.XYX_TIMES_LIMIT_SECOND).getDicValue();	
		int xyx_times_limit_seconds = new Integer(xyx_times_limit_second);
		if(xyx_times_limit_seconds > seconds){
			result.setStatusCode(RespCodeState.PLAY_TIME_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.PLAY_TIME_NOT_ENOUGH.getMessage());
			return result;
		}
		// 给与奖励
		String xyx_times_reward_coin = pDictionaryDao.selectByName(DictionaryUtil.XYX_TIMES_REWARD_COIN).getDicValue();	
		long xyx_times_reward_coins = new Long(xyx_times_reward_coin);
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		mUserInfo.setCoin(mUserInfo.getCoin() + xyx_times_reward_coins);
		mUserInfo.setUpdateTime(now);
		mUserInfoDao.update(mUserInfo);
		LCoinChange lCoinChange = new LCoinChange(userId, xyx_times_reward_coins, ConstantUtil.FLOWTYPE_IN, 
				ConstantUtil.COIN_CHANGED_TYPE_31, now, 1, "小游戏奖励", mUserInfo.getCoin());
		lCoinChangeDao.insert(lCoinChange);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("hasCount", count + 1);
		resultMap.put("coins", xyx_times_reward_coins);
		resultMap.put("coinRewardLimit", coinRewardLimit);
		result.setData(resultMap);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public Result wishCash(LCoinChange lCoinChange) {
		Result result = new Result();
		String userId=lCoinChange.getUserId();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		
		//查看用户姓名是否受限
		String[] nameAt=pDictionaryDao.selectByName(DictionaryUtil.CASH_LIMIT_NAME).getDicValue().split(",");
		for(String name:nameAt) {
			if(name.equals(user.getUserName())) {
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				return result;
			}
		}
		
		if(lCoinChange.getAmountM()==0.3) {
			int count=lUserExchangeCashDao.selectActualAmount(0.3,userId);//o.3元提现次数
			if(user.getCreateTime()<1583834400000l || count>0) {//验证用户是否可以提现0.3元
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				return result;
			}
		}else {
			PCashPriceConfig pCashPriceConfig=pCashPriceConfigDao.selectPriceOne(lCoinChange.getAmountM().intValue(),3);
			if(StringUtil.isNullOrEmpty(pCashPriceConfig) || pCashPriceConfig.getIsTask().intValue()==1) {
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				return result;
			}
			/*int countApp=mUserApprenticeDao.selectWishRewardCount(userId);
			if(countApp==0) {
				result.setStatusCode(RespCodeState.VALID_FRIEND_NO.getStatusCode());
				result.setMessage(RespCodeState.VALID_FRIEND_NO.getMessage());
				return result;
			}*/
		}
	
		
		
		//验证提现是否超过次数
		int cashNum=lUserExchangeCashDao.selectCountUser(userId);//今日提现次数
		int num=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.VIP_DAY_CASH_NUMBER).getDicValue());
		if(cashNum>=num) {
			result.setStatusCode(RespCodeState.EXCEED_CASH_NUM.getStatusCode());
			result.setMessage(RespCodeState.EXCEED_CASH_NUM.getMessage());
			return result;
		}
		
		int aliCount=mUserInfoDao.selectAliNum(user.getAliNum());
		int count=Integer.parseInt(pDictionaryDao.selectByName(DictionaryUtil.ALI_ACCOUNT_FREQUENCY).getDicValue());
		if(aliCount>count) {
			result.setStatusCode(RespCodeState.ALI_ACCOUNT_ERROR.getStatusCode());
			result.setMessage(RespCodeState.ALI_ACCOUNT_ERROR.getMessage());
			return result;
		}
		
		long now = new Date().getTime();
		lCoinChange.setChangedTime(now);
		lCoinChange.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_3);
		
		Double actualAmount=lCoinChange.getAmountM();
		
		long coin_to_money = new Long(pDictionaryDao.selectByName("coin_to_money").getDicValue());
		long needCoin = (long)(lCoinChange.getAmountM() * coin_to_money); // 最终抵扣金币
		if(needCoin<=0) {
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			return result;
		}
		if(user.getCoin().longValue() < needCoin ){
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			return result;
		}
		
		lCoinChange.setActualAmountM(actualAmount);
		lCoinChange.setAmount(needCoin);
		jmsProducer.wishUserCash(Type.WISH_USER_CASH, lCoinChange);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public Map<String, Object> listHighVip(String userId, Integer pageSize, Integer pageNum) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer pageIndex = (pageNum - 1) * pageSize;
		List<Map<String, Object>> list = lCoinChangeDao.listHighVip(userId,pageIndex,pageSize);
		long count = lCoinChangeDao.selecthHighVipCount(userId);
		resultMap.put("list", list);
		resultMap.put("count", count);
		return resultMap;
	}
}