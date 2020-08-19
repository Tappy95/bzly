package com.mc.bzly.impl.egg;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.egg.EGoldEggTypeDao;
import com.mc.bzly.dao.egg.EGoleEggOrderDao;
import com.mc.bzly.dao.egg.EUserGoldEggDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.egg.EGoldEggType;
import com.mc.bzly.model.egg.EGoleEggOrder;
import com.mc.bzly.model.egg.EUserGoldEgg;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.egg.EGoleEggOrderServcie;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=EGoleEggOrderServcie.class,version=WebConfig.dubboServiceVersion)
public class EGoleEggOrderServcieImpl implements EGoleEggOrderServcie{
	
    @Autowired
	private EGoleEggOrderDao eGoleEggOrderDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
    @Autowired
    private EGoldEggTypeDao eGoldEggTypeDao;
    @Autowired
    private EUserGoldEggDao eUserGoldEggDao;
    @Autowired
	private JMSProducer jmsProducer;
    
	@Override
	public Map<String, Object> page(EGoleEggOrder eGoleEggOrder) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(eGoleEggOrder.getAccountId())) {
			MUserInfo user1=mUserInfoDao.selectByAccountId(eGoleEggOrder.getAccountId());
			if(StringUtil.isNullOrEmpty(user1)) {
				result.put("res", "2");
				return result;
			}else {
				eGoleEggOrder.setUserId(user1.getUserId());
			}
		}
		if(!StringUtil.isNullOrEmpty(eGoleEggOrder.getExchangeAccountId())) {
			MUserInfo user2=mUserInfoDao.selectByAccountId(eGoleEggOrder.getExchangeAccountId());
			if(StringUtil.isNullOrEmpty(user2)) {
				result.put("res", "2");
				return result;
			}else {
				eGoleEggOrder.setExchangeUserId(user2.getUserId());
			}
		}
		eGoleEggOrder.setPageIndex(eGoleEggOrder.getPageSize() * (eGoleEggOrder.getPageNum() - 1));
		List<EGoleEggOrder> eGoleEggOrderList = eGoleEggOrderDao.selectList(eGoleEggOrder);
		int total=eGoleEggOrderDao.selectCount(eGoleEggOrder);
		result.put("list", eGoleEggOrderList);
		result.put("total", total);
		return result;
	}

	@Override
	public EGoleEggOrder info(long id) {
		return eGoleEggOrderDao.selectOne(id);
	}

	@Override
	public int updateProhibit(int isProhibit, long id) {
		return eGoleEggOrderDao.updateProhibit(isProhibit, id);
	}

	@Override
	public Result smashEggs(Integer typeId, String userId) {
		Result result=new Result();
		EGoldEggType eGoldEggType=eGoldEggTypeDao.selectOne(typeId);
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(eGoldEggType.getPigCoin()>user.getPigCoin()) {
			result.setStatusCode(RespCodeState.PIG_NO.getStatusCode());
			result.setMessage(RespCodeState.PIG_NO.getMessage());	
			return result;
		}
		EUserGoldEgg eUserGoldEgg=eUserGoldEggDao.selectOne(userId);
		if(StringUtil.isNullOrEmpty(eUserGoldEgg) || eUserGoldEgg.getFrequency()==0) {
			result.setStatusCode(RespCodeState.SMASH_EGGS_NO.getStatusCode());
			result.setMessage(RespCodeState.SMASH_EGGS_NO.getMessage());	
			return result;
		}
		int count=eGoleEggOrderDao.selectUserCount(userId);
		if(count>=eUserGoldEgg.getFrequency()) {
			result.setStatusCode(RespCodeState.SMASH_EGGS_NO.getStatusCode());
			result.setMessage(RespCodeState.SMASH_EGGS_NO.getMessage());	
			return result;
		}
		EGoleEggOrder order=new EGoleEggOrder();
		order.setUserId(userId);
		order.setPigCoin(eGoldEggType.getPigCoin());
		order.setObtainPigCoin(eGoldEggType.getPigCoin()-eGoldEggType.getServicePigCoin());
		order.setState(1);
		order.setIsProhibit(1);
		order.setCardNumber(eGoldEggType.getCardSign());
		order.setCardPassword(new Date().getTime()+StringUtil.getRandomString(3));
		order.setModifyPassword(0);
		jmsProducer.smashEggs(Type.SMASH_EGGS, order);
		
		result.setData(order.getObtainPigCoin());
        result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		
		return result;
	}

	@Override
	public Map<String, Object> eggList(EGoleEggOrder eGoleEggOrder) {
		Map<String, Object> result = new HashMap<String, Object>();
		eGoleEggOrder.setPageIndex(eGoleEggOrder.getPageSize() * (eGoleEggOrder.getPageNum() - 1));
		List<Map<String, Object>> eGoleEggOrderList = eGoleEggOrderDao.selectAppList(eGoleEggOrder);
		int total=eGoleEggOrderDao.selectAppCount(eGoleEggOrder);
		result.put("list", eGoleEggOrderList);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> appInfo(long id) {
		return eGoleEggOrderDao.selectAppInfo(id);
	}

	@Override
	public Result extractPassword(long id, String payPassword,String userId) {
		Result result=new Result();
		MUserInfo user=mUserInfoDao.selectOne(userId);
		if(payPassword.equals(user.getPayPassword())) {
			result.setData(eGoleEggOrderDao.selectPassword(id));
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}else {
			result.setStatusCode(RespCodeState.PAY_PASSWORD_ERROR.getStatusCode());
			result.setMessage(RespCodeState.PAY_PASSWORD_ERROR.getMessage());
			//支付密码错误
		}
		return result;
	}

	@Override
	public Map<String,Object> isCard(String cardNumber) {
		Map<String,Object> data=new HashMap<>();
		EGoleEggOrder order=eGoleEggOrderDao.selectCard(cardNumber);
		if(StringUtil.isNullOrEmpty(order) || order.getState().intValue()==2 || order.getIsProhibit().intValue()==2) {
			data.put("res", 2);
			return data;
		}
		data.put("res", 1);
		data.put("pigCoin", order.getObtainPigCoin());
		return data;
	}

	@Override
	public int activationCard(String cardNumber, String cardPassword, Integer accountId) {
		MUserInfo user=mUserInfoDao.selectByAccountId(accountId);
		if(StringUtil.isNullOrEmpty(user)) {
			return 2;//用户不存在
		}
		EGoleEggOrder order=eGoleEggOrderDao.selectCardNumOrPass(cardNumber, cardPassword);
		if(StringUtil.isNullOrEmpty(order)) {
			return 2;//激活卡不存在
		}
		if(order.getState().intValue()==2 || order.getIsProhibit()==2) {
			return 2;//激活卡无效
		}
		Map<String,Object> data=new HashMap<>();
		data.put("id", order.getId());
		data.put("userId", user.getUserId());
		
		jmsProducer.activationCard(Type.ACT_CARD, data);
		return 1;
	}

	@Override
	public Map<String, Object> useList(EGoleEggOrder eGoleEggOrder) {
		Map<String, Object> result = new HashMap<String, Object>();
		eGoleEggOrder.setPageIndex(eGoleEggOrder.getPageSize() * (eGoleEggOrder.getPageNum() - 1));
		List<Map<String, Object>> eGoleEggOrderList = eGoleEggOrderDao.selectUseAppList(eGoleEggOrder);
		int total=eGoleEggOrderDao.selectUseAppCount(eGoleEggOrder);
		result.put("list", eGoleEggOrderList);
		result.put("total", total);
		return result;
	}

	@Override
	public List<Map<String, Object>> selectNewsRoll() {
		return eGoleEggOrderDao.selectNewsRoll();
	}

	@Override
	public Result modifyPassword(String cardNumber, String cardPassword, Integer accountId) {
		Result result=new Result();
		MUserInfo user=mUserInfoDao.selectByAccountId(accountId);
		if(StringUtil.isNullOrEmpty(user)) {
			result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
			result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			return result;//用户不存在
		}
		EGoleEggOrder order=eGoleEggOrderDao.selectCardNumOrPass(cardNumber, cardPassword);
		if(StringUtil.isNullOrEmpty(order)) {
			result.setStatusCode(RespCodeState.CARD_NO_PASSWORD.getStatusCode());
			result.setMessage(RespCodeState.CARD_NO_PASSWORD.getMessage());
			return result;//激活卡不存在
		}
		if(order.getModifyPassword()>0) {
			result.setStatusCode(RespCodeState.CARD_MODIFY_PASSWORD.getStatusCode());
			result.setMessage(RespCodeState.CARD_MODIFY_PASSWORD.getMessage());
			return result;//该激活卡已修改过密码
		}
		if(order.getState().intValue()==2 || order.getIsProhibit()==2) {
			result.setStatusCode(RespCodeState.CARD_USE.getStatusCode());
			result.setMessage(RespCodeState.CARD_USE.getMessage());
			return result;//激活卡无效
		}
		order.setExchangeUserId(user.getUserId());
		order.setExchangeTime(new Date().getTime());
		order.setState(2);
		int res=eGoleEggOrderDao.update(order);
		if(res>0) {
			EGoleEggOrder eggOrder=new EGoleEggOrder();
			eggOrder.setUserId(user.getUserId());
			eggOrder.setPigCoin(order.getPigCoin());
			eggOrder.setObtainPigCoin(order.getObtainPigCoin());
			eggOrder.setState(1);
			eggOrder.setIsProhibit(1);
			eggOrder.setCardNumber(order.getCardNumber());
			eggOrder.setCardPassword(new Date().getTime()+StringUtil.getRandomString(3));
			eggOrder.setModifyPassword(1);
			jmsProducer.modifyCardPassword(Type.CARD_PASSWORD, eggOrder);
		}
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

}
