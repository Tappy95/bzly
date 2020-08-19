package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserHippoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.user.LUserHippo;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.user.LUserHippoService;

@Service(interfaceClass = LUserHippoService.class,version = WebConfig.dubboServiceVersion)
public class LUserHippoServiceImpl implements LUserHippoService {

	@Autowired
	private LUserHippoDao LUserHippoDao;
	@Autowired
	private JMSProducer jmsProducer;
	@Override
	public void add(LUserHippo lUserHippo,List<String> impTrackingList) {
		LUserHippoDao.insert(lUserHippo);
		if(impTrackingList != null && impTrackingList.size() > 0){
			jmsProducer.sendMessage(Type.HIPPO_SEND_IMP_TRACKING, impTrackingList);
		}
	}
	
	@Override
	public void sendClkUrl(String clkUrl) {
		String[] url = clkUrl.split(",");
		List<String> clkList = new ArrayList<>();
		for (int i = 0; i < url.length; i++) {
			clkList.add(url[i]);
		}
		if(clkList != null && clkList.size() > 0){
			jmsProducer.sendMessage(Type.HIPPO_SEND_CLK_TRACKING, clkList);
		}
	}

}
