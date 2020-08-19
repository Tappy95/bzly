package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.bzly.common.constant.InformConstant;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LUserExchangeCashDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.LuserCashGameStatistic;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.news.AppNewsInformService;
import com.mc.bzly.service.user.LUserExchangeCashService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserExchangeCashService.class,version = WebConfig.dubboServiceVersion)
public class LUserExchangeCashServiceImpl implements LUserExchangeCashService{
	
	@Autowired
	private LUserExchangeCashDao lUserExchangeCashDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
    private MUserInfoDao mUserInfoDao;
	@Autowired
	private AppNewsInformService appNewsInformService;
	@Autowired
	private JMSProducer jmsProducer;
	@Override
	public int update(LUserExchangeCash lUserExchangeCash) {
		if(lUserExchangeCash.getState().intValue()==1 || lUserExchangeCash.getState().intValue()==4) {
			return 3;
		}

		LCoinChange lCoinChangeOld = lCoinChangeDao.selectOne(lUserExchangeCash.getCoinChangeId());
		if(lCoinChangeOld.getChangedType().intValue() != ConstantUtil.COIN_CHANGED_TYPE_3){
			return 0;
		}
		if(lCoinChangeOld.getStatus().intValue() != 2){
			return 2;
		}
		lUserExchangeCash.setIsLocking(1);
		jmsProducer.cashExamine(Type.CASH_EXAMINE, lUserExchangeCash);
		
		/*lCoinChangeOld.setReason(lUserExchangeCash.getRemarks());
		lCoinChangeOld.setAuditTime(now);
		if(lUserExchangeCash.getState().intValue()==2) {
			lCoinChangeOld.setStatus(1);
			// 发送提现到账消息
			AppNewsInform appNewsInform=new AppNewsInform();
			appNewsInform.setUserId(lCoinChangeOld.getUserId());
			appNewsInform.setInformTitle(InformConstant.PUSH_TITLE_WITHDRAWALS);
			appNewsInform.setInformContent(InformConstant.PUSH_CONTENT_SUCCESS);
			appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
			appNewsInformService.addPush(appNewsInform);
		}else {
			MUserInfo mUserInfo = mUserInfoDao.selectOne(lCoinChangeOld.getUserId());
			lCoinChangeOld.setStatus(3);
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
		lCoinChangeDao.update(lCoinChangeOld);
		lUserExchangeCashDao.update(lUserExchangeCash);*/
		return 1;
	}

	@Override
	public Map<String, Object> selectList(LUserExchangeCash lUserExchangeCash) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lUserExchangeCash.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(lUserExchangeCash.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		
		lUserExchangeCash.setPageIndex(lUserExchangeCash.getPageSize() * (lUserExchangeCash.getPageNum() - 1));
		List<LUserExchangeCash> lUserExchangeCashList = lUserExchangeCashDao.selectList(lUserExchangeCash);
		Map<String,String> subTotalMap=lUserExchangeCashDao.selectSum(lUserExchangeCash);
		Map<String,String> countPriceMap=lUserExchangeCashDao.selecCountPrice(lUserExchangeCash);
		Map<String, String> countMap = lUserExchangeCashDao.selectCount(lUserExchangeCash);
		
		result.put("list", lUserExchangeCashList);
		result.put("count", countPriceMap.get("cou"));
		result.put("subTotalPrice", subTotalMap.get("subTotalPrice"));
		result.put("pageCount", subTotalMap.get("pageCount"));
		result.put("totalPrice", countPriceMap.get("totalPrice"));
		result.put("total", countMap.get("cou"));
		return result;
	}

	@Override
	public int updateLocking(LUserExchangeCash lUserExchangeCash) {
		LUserExchangeCash cash=lUserExchangeCashDao.selectOne(lUserExchangeCash.getId());
		if(cash.getIsLocking()==1) {
			if(!lUserExchangeCash.getLockingMobile().equals(cash.getLockingMobile())) {
				return 2;
			}
		}
		
		lUserExchangeCash.setIsLocking(1);
		return lUserExchangeCashDao.updateLocking(lUserExchangeCash);
	}

	@Override
	public int updateLockingList(String ids,String admin) {
		String[] idArr=ids.split(",");
		List<LUserExchangeCash> lUserExchangeCashList=new ArrayList<>();
		LUserExchangeCash lUserExchangeCash=null;
		LUserExchangeCash cash=null;
		for(int i=0;i<idArr.length;i++) {
			cash=lUserExchangeCashDao.selectOne(Integer.parseInt(idArr[i]));
			if(cash.getIsLocking()==1) {
				if(!StringUtil.isNullOrEmpty(cash.getLockingMobile()) && !admin.equals(cash.getLockingMobile())) {
					/*return 2;//已被其他管理员锁定*/
					continue;
				}	
			}
			
				
			if(cash.getState()==1) {
				lUserExchangeCash=new LUserExchangeCash();
				lUserExchangeCash.setId(Integer.parseInt(idArr[i]));
				lUserExchangeCash.setLockingMobile(admin);
				lUserExchangeCashList.add(lUserExchangeCash);	
			}
		}
		if(lUserExchangeCashList.size()>0) {
		   lUserExchangeCashDao.updateLockingList(lUserExchangeCashList);	
		}
		return 0;
	}

	@Override
	public int relieveLocking(Integer id, String admin) {
		LUserExchangeCash cash=lUserExchangeCashDao.selectOne(id);
		if(!admin.equals(cash.getLockingMobile())) {
			return 2;//解除锁定失败
		}
		LUserExchangeCash lUserExchangeCash=new LUserExchangeCash();
		lUserExchangeCash.setLockingMobile("");
		lUserExchangeCash.setIsLocking(2);
		lUserExchangeCash.setId(id);
		return lUserExchangeCashDao.updateLocking(lUserExchangeCash);
	}

	@Override
	public LUserExchangeCash selectInfo(Integer id) {
		return lUserExchangeCashDao.selectInfo(id);
	}

	@Override
	public List<LUserExchangeCash> selectExcl(LUserExchangeCash lUserExchangeCash) {
		return lUserExchangeCashDao.selectExcl(lUserExchangeCash);
	}

	@Override
	public Map<String, Object> selectH5List(LUserExchangeCash lUserExchangeCash) {
		Map<String, Object> result = new HashMap<String, Object>();
		lUserExchangeCash.setPageIndex(lUserExchangeCash.getPageSize() * (lUserExchangeCash.getPageNum() - 1));
		List<Map<String,Object>> map = lUserExchangeCashDao.selectH5List(lUserExchangeCash);
		int total = lUserExchangeCashDao.selectH5Count(lUserExchangeCash);
		
		result.put("list", map);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> selectCashGame(LuserCashGameStatistic luserCashGameStatistic) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(luserCashGameStatistic.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(luserCashGameStatistic.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		
		luserCashGameStatistic.setPageIndex(luserCashGameStatistic.getPageSize() * (luserCashGameStatistic.getPageNum() - 1));
		List<Map<String,Object>> list =lUserExchangeCashDao.selectCashGame(luserCashGameStatistic);
		int total=lUserExchangeCashDao.selectCashGameCount(luserCashGameStatistic);
		
		result.put("list", list);
		result.put("total",total);
		return result;
	}

	@Override
	public List<Map<String,Object>> channelStatisticExcl(LuserCashGameStatistic luserCashGameStatistic) {
		luserCashGameStatistic.setPageIndex(luserCashGameStatistic.getPageSize() * (luserCashGameStatistic.getPageNum() - 1));
		List<Map<String,Object>> list =lUserExchangeCashDao.selectCashGameExcl(luserCashGameStatistic);
		return list;
	}

}
