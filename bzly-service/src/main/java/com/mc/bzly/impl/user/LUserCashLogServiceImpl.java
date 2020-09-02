package com.mc.bzly.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserCashLogDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LCoinChange;
import com.mc.bzly.model.user.LUserCashLog;
import com.mc.bzly.service.user.LUserCashLogService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = LUserCashLogService.class,version = WebConfig.dubboServiceVersion)
public class LUserCashLogServiceImpl implements LUserCashLogService{
	@Autowired
	private LUserCashLogDao lUserCashLogDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;

	@Override
	public Map<String, Object> selectList(LUserCashLog lUserCashLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lUserCashLog.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(lUserCashLog.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		lUserCashLog.setPageIndex(lUserCashLog.getPageSize() * (lUserCashLog.getPageNum() - 1));
		List<LUserCashLog> lUserCashLogList = lUserCashLogDao.selectList(lUserCashLog);
		int total=lUserCashLogDao.selectCount(lUserCashLog);
		result.put("list", lUserCashLogList);
		result.put("total", total);
		return result;
	}

	@Override
	public List<LUserCashLog> excl(LUserCashLog lUserCashLog) {
		lUserCashLog.setPageIndex(lUserCashLog.getPageSize() * (lUserCashLog.getPageNum() - 1));
		List<LUserCashLog> lUserCashLogList = lUserCashLogDao.selectList(lUserCashLog);
		return lUserCashLogList;
	}

}
