package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.utils.Base64;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PCashPriceConfigDao;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.user.LCashGameTaskDao;
import com.mc.bzly.dao.user.LUserCashDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.platform.PCashPriceConfig;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserCash;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.user.LUserCashService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LUserCashService.class,version = WebConfig.dubboServiceVersion)
public class LUserCashServiceImpl implements LUserCashService{
	
	@Autowired
	private LUserCashDao lUserCashDao;
	@Autowired
	private PCashPriceConfigDao pCashPriceConfigDao;
	@Autowired
	private TPGameDao tPGameDao;
	@Autowired
	private LUserGameTaskDao lUserGameTaskDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private LCashGameTaskDao lCashGameTaskDao;

	@Override
	public String insert(LUserCash lUserCash) {
		PCashPriceConfig pCashPriceConfig=pCashPriceConfigDao.selectPriceOne(lUserCash.getMoney(),1);
		if(StringUtil.isNullOrEmpty(pCashPriceConfig)) {
             return "2";
		}else {
			if(pCashPriceConfig.getIsTask().intValue()==2) {
				return "2";
			}
		}
		LUserCash cash=lUserCashDao.selectStateOne(lUserCash.getUserId());
		if(StringUtil.isNullOrEmpty(cash)) {
			String outTradeNo = Base64.getOutTradeNo();
			lUserCash.setOutTradeNo(outTradeNo+"2");
			lUserCash.setState(1);
			lUserCash.setCreatorTime(new Date().getTime());
			String str = new AWorker<String>() {
				@Override
				public String call(String userId) throws Exception {
					LUserCash userCash=lUserCashDao.selectStateOne(lUserCash.getUserId());
					int i=0;
					if(StringUtil.isNullOrEmpty(userCash)) {
						i=lUserCashDao.insert(lUserCash);
					}
					return String.valueOf(i);
				}
			}.execute(lUserCash.getUserId(), "redis_lock_cash_task");
			return str;
		}else {
			cash.setMoney(lUserCash.getMoney());
			cash.setMode(lUserCash.getMode());
			lUserCashDao.update(cash);
		}
		return "1";
	}

	@Override
	public Map<String, Object> taskInfo(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("res", 2);//没有匹配任务
		LUserCash lUserCash=lUserCashDao.selectStateOne(userId);
		if(StringUtil.isNullOrEmpty(lUserCash)) {
			return data;
		}
		data.put("lUserCash", lUserCash);
		List<TPGame> games=tPGameDao.selectCashGameTask(lUserCash.getId());
		if(games.size()>0) {
			data.put("res", 1);//匹配了任务
		}
		data.put("games", games);
		return data;
	}

	@Override
	public Map<String,Object> recommendGameCash(int ptype,String userId, Integer cashId) {
		Map<String,Object> date=new HashMap<String,Object>();
		date.put("res", 1);//成功
		LUserCash lUserCash=lUserCashDao.selectId(cashId,userId);
		if(StringUtil.isNullOrEmpty(lUserCash)) {
			date.put("res", 2);//任务异常
			return date;
		}else if(lUserCash.getState()!=1){
			date.put("res", 3);//任务已完成
			return date;
		}
		int countGame=lUserGameTaskDao.selectCashCount(lUserCash.getId());
		if(countGame>=3) {
			date.put("res", 3);//任务已完成
			return date;
		}
		TPGame tpGame = new TPGame();
		List<TPGame> tpGames = tPGameDao.recommendGameList(ptype,userId);
		
		Random r=new Random();
		int index=r.nextInt(tpGames.size());
        tpGame=tpGames.get(index);
        
		date.put("tpGame", tpGame);
		return date;
	}

	@Override
	public Result cashLaunch(String userId, Integer cashId,String ip) {
		Result result = new Result();
		LUserCash lUserCash=lUserCashDao.selectId(cashId,userId);
		if(StringUtil.isNullOrEmpty(lUserCash)) {
			result.setMessage(RespCodeState.CASH_TASK_ERROR.getMessage());
			result.setStatusCode(RespCodeState.CASH_TASK_ERROR.getStatusCode());
			return result;
		}else if(lUserCash.getState()!=2){
			result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
			result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
			return result;
		}
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		Long cash=lUserCash.getMoney()*11000l;
        if(mUserInfo.getCoin()<cash) {
        	result.setMessage(RespCodeState.USER_BALANCE_NOT_ENOUGH.getMessage());
			result.setStatusCode(RespCodeState.USER_BALANCE_NOT_ENOUGH.getStatusCode());
			return result;
        }
        LCoinChange lCoinChange=new LCoinChange();
        lCoinChange.setUserId(userId);
        lCoinChange.setAmount(cash);
        lCoinChange.setFlowType(ConstantUtil.FLOWTYPE_OUT);
        lCoinChange.setStatus(2);
        lCoinChange.setChangedType(ConstantUtil.COIN_CHANGED_TYPE_3);
        lCoinChange.setAccountType(lUserCash.getMode());
        lCoinChange.setUserIp(ip);
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("lCoinChange", lCoinChange);
        data.put("cashId", lUserCash.getId());
        data.put("outTradeNo", lUserCash.getOutTradeNo());
        jmsProducer.cashTask(Type.CASH_TASK, data);
        
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

	@Override
	public Map<String,Object> selectList(LUserCash lUserCash) {
		Map<String,Object> data=new HashMap<>();
		data.put("list", lUserCashDao.selectList(lUserCash));
		return data;
	}

	@Override
	public Result updateState(Integer id) {
		Result result = new Result();
		LUserCash userCash=lUserCashDao.selectTimeId(id);
		if(StringUtil.isNullOrEmpty(userCash)) {
			result.setMessage(RespCodeState.ORDER_EXPIRE.getMessage());
			result.setStatusCode(RespCodeState.ORDER_EXPIRE.getStatusCode());
			return result;
		}
		if(userCash.getState()!=1) {
			result.setMessage(RespCodeState.CALLBACK_HAS_FINISH.getMessage());
			result.setStatusCode(RespCodeState.CALLBACK_HAS_FINISH.getStatusCode());
			return result;
		}
		int count=lCashGameTaskDao.selectCount(id);
		if(count<3) {
			result.setMessage(RespCodeState.NOT_COMPLETE.getMessage());
			result.setStatusCode(RespCodeState.NOT_COMPLETE.getStatusCode());
			return result;
		}
		lUserCashDao.updateIdState(id);
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

}
