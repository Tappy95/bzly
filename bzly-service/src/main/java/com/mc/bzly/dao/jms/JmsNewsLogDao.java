package com.mc.bzly.dao.jms;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.jms.JmsNewsLog;

@Mapper
public interface JmsNewsLogDao {
	
	Integer insert(JmsNewsLog jmsNewsLog); 
	
	List<JmsNewsLog> selectListPage(JmsNewsLog jmsNewsLog);
	
	int selectCount(JmsNewsLog jmsNewsLog);

}
