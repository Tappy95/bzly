package com.mc.bzly.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserSearchLogDao;
import com.mc.bzly.service.user.LUserSearchLogService;

@Service(interfaceClass = LUserSearchLogService.class,version = WebConfig.dubboServiceVersion)
public class LUserSearchLogServiceImpl implements LUserSearchLogService {

	@Autowired 
	private LUserSearchLogDao lUserSearchLogDao;
	
	@Override
	public List<String> queryList(String userId,Integer searchType) {
		return lUserSearchLogDao.selectList(userId,searchType);
	}

	@Override
	public void removeAll(String userId,Integer searchType) {
		lUserSearchLogDao.deleteAll(userId,searchType);
	}

}
