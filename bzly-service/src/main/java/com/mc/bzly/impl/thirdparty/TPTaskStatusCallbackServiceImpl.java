package com.mc.bzly.impl.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPTaskStatusCallbackDao;
import com.mc.bzly.model.thirdparty.TPTaskStatusCallback;
import com.mc.bzly.service.thirdparty.TPTaskStatusCallbackService;

@Service(interfaceClass = TPTaskStatusCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TPTaskStatusCallbackServiceImpl implements TPTaskStatusCallbackService {
	
	@Autowired
	private TPTaskStatusCallbackDao tpTaskStatusCallbackDao;
	
	@Override
	public void add(TPTaskStatusCallback tpTaskStatusCallback) {
		tpTaskStatusCallbackDao.insert(tpTaskStatusCallback);
	}

	@Override
	public TPTaskStatusCallback queryByNum(String flewNum,Integer status) {
		return tpTaskStatusCallbackDao.selectByNum(flewNum,status);
	}

}
