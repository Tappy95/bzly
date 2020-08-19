package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.PCDDCallbackDao;
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.service.thirdparty.PCDDCallbackService;

@Service(interfaceClass = PCDDCallbackService.class,version = WebConfig.dubboServiceVersion)
public class PCDDCallbackServiceImpl implements PCDDCallbackService{
    
	@Autowired
	private PCDDCallbackDao pCDDCallbackDao;
	
	@Override
	public Map<String, Object> page(PCDDCallback pcDDCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		pcDDCallback.setPageIndex(pcDDCallback.getPageSize() * (pcDDCallback.getPageNum() - 1));
		List<PCDDCallback> pcDDCallbackList = pCDDCallbackDao.selectListPage(pcDDCallback);
		int total=pCDDCallbackDao.selectCount(pcDDCallback);
		Map<String, Object> countMap=pCDDCallbackDao.selectCountSum(pcDDCallback);
		Map<String, Object> smallMap=pCDDCallbackDao.selectSmallSum(pcDDCallback);
		result.put("list", pcDDCallbackList);
		result.put("total", total);
		result.put("successCount", countMap.get("successCount"));
		result.put("priceSum", countMap.get("priceSum")==null ? 0:countMap.get("priceSum"));
		result.put("moneySum", countMap.get("moneySum")==null ? 0:countMap.get("moneySum"));
		result.put("smallSuccessCount", smallMap.get("smallSuccessCount"));
		result.put("smallPriceSum", smallMap.get("smallPriceSum")==null ? 0:smallMap.get("smallPriceSum"));
		result.put("smallMoneySum", smallMap.get("smallMoneySum")==null ? 0:smallMap.get("smallMoneySum"));
		return result;
	}

	@Override
	public PCDDCallback queryByOrdernum(String ordernum) {
		PCDDCallback pCDDCallback = pCDDCallbackDao.selectByOrderNumStatus(ordernum, 2);
		if(pCDDCallback != null){
			// 删除处理失败的记录
			pCDDCallbackDao.deleteByOrderNum(ordernum);
			return pCDDCallback;
		}else{
			pCDDCallback = pCDDCallbackDao.selectByOrderNumStatus(ordernum, 1);
			return pCDDCallback;
		}
	}

}
