package com.mc.bzly.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserStatisticDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LUserReconciliation;
import com.mc.bzly.service.user.LUserStatisticServcie;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LUserStatisticServcie.class,version = WebConfig.dubboServiceVersion)
public class LUserStatisticServcieImpl implements LUserStatisticServcie{
	@Autowired
	private LUserStatisticDao lUserStatisticDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;

	@Override
	public Map<String, Object> userList(LUserReconciliation lUserReconciliation) {
        Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lUserReconciliation.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(lUserReconciliation.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		lUserReconciliation.setPageIndex(lUserReconciliation.getPageSize() * (lUserReconciliation.getPageNum() - 1));
		List<Map<String,Object>> reconciliationList = lUserStatisticDao.selectUserList(lUserReconciliation);
		int countPriceMap=lUserStatisticDao.selectUserCount(lUserReconciliation);
		result.put("list", reconciliationList); 
		result.put("total", countPriceMap);
		return result;
	}

	@Override
	public List<Map<String, Object>> excl(LUserReconciliation lUserReconciliation) {
		lUserReconciliation.setPageIndex(lUserReconciliation.getPageSize() * (lUserReconciliation.getPageNum() - 1));
		return lUserStatisticDao.selectUserList(lUserReconciliation);
	}

}
