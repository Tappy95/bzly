package com.mc.bzly.service.jms;

import java.util.Map;

import com.mc.bzly.model.jms.JmsNewsLog;

public interface JmsNewsLogService {
	
	Map<String, Object> page(JmsNewsLog jmsNewsLog);

}
