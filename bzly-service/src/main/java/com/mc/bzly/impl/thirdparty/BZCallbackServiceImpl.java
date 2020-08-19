package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.BZCallbackDao;
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.service.thirdparty.BZCallbackService;

@Service(interfaceClass = BZCallbackService.class,version = WebConfig.dubboServiceVersion)
public class BZCallbackServiceImpl implements BZCallbackService{
    
	@Autowired
	private BZCallbackDao bZCallbackDao;
	
	@Override
	public Map<String, Object> page(BZCallback bzCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		bzCallback.setPageIndex(bzCallback.getPageSize() * (bzCallback.getPageNum() - 1));
		List<BZCallback> bzCallbackList = bZCallbackDao.selectListPage(bzCallback);
		int total=bZCallbackDao.selectCount(bzCallback);
		Map<String, Object> countMap=bZCallbackDao.selectCountSum(bzCallback);
		Map<String, Object> smallMap=bZCallbackDao.selectSmallSum(bzCallback);
		result.put("list", bzCallbackList);
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
	public BZCallback queryByOrdernum(String ordernum) {
		BZCallback bzCallback = bZCallbackDao.selectByOrderNumStatus(ordernum, 1);
		if(bzCallback != null){
			// 删除处理失败的记录
			bZCallbackDao.deleteByOrderNum(ordernum);
			return bzCallback;
		}else{
			bzCallback = bZCallbackDao.selectByOrderNumStatus(ordernum, 2);
			return bzCallback;
		}
	}

}
