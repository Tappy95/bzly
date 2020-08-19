package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.YoleCallbackDao;
import com.mc.bzly.model.thirdparty.YoleCallback;
import com.mc.bzly.service.thirdparty.YoleCallbackService;

@Service(interfaceClass = YoleCallbackService.class,version = WebConfig.dubboServiceVersion)
public class YoleCallbackServiceImpl implements YoleCallbackService{
    
	@Autowired
	private YoleCallbackDao yoleCallbackDao;
	
	@Override
	public Map<String, Object> page(YoleCallback yoleCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		yoleCallback.setPageIndex(yoleCallback.getPageSize() * (yoleCallback.getPageNum() - 1));
		List<YoleCallback> yoleCallbackList = yoleCallbackDao.selectListPage(yoleCallback);
		int total=yoleCallbackDao.selectCount(yoleCallback);
		Map<String, Object> countMap=yoleCallbackDao.selectCountSum(yoleCallback);
		Map<String, Object> smallMap=yoleCallbackDao.selectSmallSum(yoleCallback);
		result.put("list", yoleCallbackList);
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
	public YoleCallback queryByYoleid(String yoleid) {
		YoleCallback yoleCallback = yoleCallbackDao.selectByYoleidStatus(yoleid, 1);
		if(yoleCallback != null){
			// 删除处理失败的记录
			yoleCallbackDao.deleteByYoleid(yoleid);
			return yoleCallback;
		}else{
			yoleCallback = yoleCallbackDao.selectByYoleidStatus(yoleid, 2);
			return yoleCallback;
		}
	}

}
