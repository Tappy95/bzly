package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.XWCallbackDao;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.service.thirdparty.XWCallbackService;

@Service(interfaceClass = XWCallbackService.class,version = WebConfig.dubboServiceVersion)
public class XWCallbackServiceImpl implements XWCallbackService{
    
	@Autowired
	private XWCallbackDao xWCallbackDao;
	
	@Override
	public Map<String, Object> page(XWCallback xwCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		xwCallback.setPageIndex(xwCallback.getPageSize() * (xwCallback.getPageNum() - 1));
		List<XWCallback> xwCallbackList = xWCallbackDao.selectListPage(xwCallback);
		int total=xWCallbackDao.selectCount(xwCallback);
		Map<String, Object> countMap=xWCallbackDao.selectCountSum(xwCallback);
		Map<String, Object> smallMap=xWCallbackDao.selectSmallSum(xwCallback);
		result.put("list", xwCallbackList);
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
	public XWCallback queryByOrdernum(String ordernum) {
		XWCallback xwCallback = xWCallbackDao.selectByOrderNumStatus(ordernum, 1);
		if(xwCallback != null){
			// 删除处理失败的记录
			xWCallbackDao.deleteByOrderNum(ordernum);
			return xwCallback;
		}else{
			xwCallback = xWCallbackDao.selectByOrderNumStatus(ordernum, 2);
			return xwCallback;
		}
	}

	@Override
	public Map<String, Object> xyzPage(XWCallback xwCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		xwCallback.setPageIndex(xwCallback.getPageSize() * (xwCallback.getPageNum() - 1));
		List<XWCallback> xwCallbackList = xWCallbackDao.selectXyzListPage(xwCallback);
		int total=xWCallbackDao.selectXyzCount(xwCallback);
		Map<String, Object> countMap=xWCallbackDao.selectXyzCountSum(xwCallback);
		Map<String, Object> smallMap=xWCallbackDao.selectXyzSmallSum(xwCallback);
		result.put("list", xwCallbackList);
		result.put("total", total);
		result.put("successCount", countMap.get("successCount"));
		result.put("priceSum", countMap.get("priceSum")==null ? 0:countMap.get("priceSum"));
		result.put("moneySum", countMap.get("moneySum")==null ? 0:countMap.get("moneySum"));
		result.put("smallSuccessCount", smallMap.get("smallSuccessCount"));
		result.put("smallPriceSum", smallMap.get("smallPriceSum")==null ? 0:smallMap.get("smallPriceSum"));
		result.put("smallMoneySum", smallMap.get("smallMoneySum")==null ? 0:smallMap.get("smallMoneySum"));
		return result;
	}

}
