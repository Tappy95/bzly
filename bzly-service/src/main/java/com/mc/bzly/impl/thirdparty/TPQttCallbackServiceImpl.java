package com.mc.bzly.impl.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPQttCallbackDao;
import com.mc.bzly.model.thirdparty.TPQttCallback;
import com.mc.bzly.service.thirdparty.TPQttCallbackService;

@Service(interfaceClass = TPQttCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TPQttCallbackServiceImpl implements TPQttCallbackService {
	
	@Autowired
	private TPQttCallbackDao tpQttCallbackDao;
	
	@Override
	public void add(TPQttCallback tpQttCallback) {
		// 接口一：接收到快手上报
		TPQttCallback old = tpQttCallbackDao.selectByImei(tpQttCallback.getImeiMD5());
		if(old == null){
			tpQttCallbackDao.insert(tpQttCallback);
		}else{
			if(old.getStatus().intValue() == 1){
				old.setCallback(tpQttCallback.getCallback());
				tpQttCallbackDao.update(old);
			}
		}
	}

}
