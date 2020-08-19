package com.mc.bzly.impl.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPKsCallbackDao;
import com.mc.bzly.model.thirdparty.TPKsCallback;
import com.mc.bzly.service.thirdparty.TPKsCallbackService;

@Service(interfaceClass = TPKsCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TPKsCallbackServiceImpl implements TPKsCallbackService {
	
	@Autowired
	private TPKsCallbackDao tpKsCallbackDao;
	
	@Override
	public void add(TPKsCallback tpKsCallback) {
		// 接口一：接收到快手上报
		TPKsCallback old = tpKsCallbackDao.selectByImei(tpKsCallback.getImeiMD5());
		if(old == null){
			tpKsCallbackDao.insert(tpKsCallback);
		}else{
			if(old.getStatus().intValue() == 1){
				old.setCallback(tpKsCallback.getCallback());
				tpKsCallbackDao.update(old);
			}
		}
	}

}
