package com.mc.bzly.impl.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserFirstLogDao;
import com.mc.bzly.model.user.LUserFirstLog;
import com.mc.bzly.service.user.LUserFirstLogService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserFirstLogService.class,version = WebConfig.dubboServiceVersion)
public class LUserFirstLogServiceImpl implements LUserFirstLogService{

	@Autowired
	private LUserFirstLogDao lUserFirstLogDao;
	
	@Override
	public Integer insert(LUserFirstLog lUserFirstLog) {
		LUserFirstLog user=lUserFirstLogDao.selectOne(lUserFirstLog.getUserId());
		if(!StringUtil.isNullOrEmpty(user)) {
			return 0;
		}
		lUserFirstLog.setIsOne(1);
		return lUserFirstLogDao.insert(lUserFirstLog);
	}

}
