package com.mc.bzly.impl.jms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.jms.JmsNewsLogDao;
import com.mc.bzly.model.jms.JmsNewsLog;
import com.mc.bzly.service.jms.JmsNewsLogService;

@Service(interfaceClass=JmsNewsLogService.class,version=WebConfig.dubboServiceVersion)
public class JmsNewsLogServiceImpl implements JmsNewsLogService{
	
    @Autowired
	private JmsNewsLogDao jmsNewsLogDao;
	
	@Override
	public Map<String, Object> page(JmsNewsLog jmsNewsLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		jmsNewsLog.setPageIndex(jmsNewsLog.getPageSize() * (jmsNewsLog.getPageNum() - 1));
		List<JmsNewsLog> jmsNewsLogList = jmsNewsLogDao.selectListPage(jmsNewsLog);
		int total=jmsNewsLogDao.selectCount(jmsNewsLog);
		result.put("list", jmsNewsLogList);
		result.put("total", total);
		return result;
	}

}
