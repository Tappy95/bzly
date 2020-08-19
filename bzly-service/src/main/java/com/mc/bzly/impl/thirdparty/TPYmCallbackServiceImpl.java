package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPYmCallbackDao;
import com.mc.bzly.model.jms.JmsWrapper;
import com.mc.bzly.model.thirdparty.TPYmCallback;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.thirdparty.TPYmCallbackService;

@Service(interfaceClass = TPYmCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TPYmCallbackServiceImpl implements TPYmCallbackService{
    
	@Autowired
	private JMSProducer jmsProducer;
	@Autowired
	private TPYmCallbackDao tPYmCallbackDao;

	@Override
	public TPYmCallback selectCallbackId(String coinCallbackId) {
		return tPYmCallbackDao.selectCallbackId(coinCallbackId);
	}

	@Override
	public void callback(TPYmCallback tPYmCallback) {
		jmsProducer.ymCallback(JmsWrapper.Type.CALL_BACK_YM, tPYmCallback);
	}

	@Override
	public Map<String, Object> page(TPYmCallback tPYmCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		tPYmCallback.setPageIndex(tPYmCallback.getPageSize() * (tPYmCallback.getPageNum() - 1));
		List<TPYmCallback> tPYmCallbackList = tPYmCallbackDao.selectList(tPYmCallback);
		int total=tPYmCallbackDao.selectCount(tPYmCallback);
		Map<String, Object> countMap=tPYmCallbackDao.selectCountSum(tPYmCallback);
		Map<String, Object> smallMap=tPYmCallbackDao.selectSmallSum(tPYmCallback);
		result.put("list", tPYmCallbackList);
		result.put("total", total);
		result.put("successCount", countMap.get("successCount"));
		result.put("moneySum", countMap.get("coinSum")==null ? 0:countMap.get("coinSum"));

		result.put("smallSuccessCount", smallMap.get("smallSuccessCount"));
		result.put("smallMoneySum", smallMap.get("smallCoinSum")==null ? 0:smallMap.get("smallCoinSum"));

		return result;
	}

}
