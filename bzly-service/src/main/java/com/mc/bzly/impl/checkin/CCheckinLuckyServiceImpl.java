package com.mc.bzly.impl.checkin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.checkin.CCheckinLuckyDao;
import com.mc.bzly.model.checkin.CCheckinLucky;
import com.mc.bzly.service.checkin.CCheckinLuckyService;

@Service(interfaceClass=CCheckinLuckyService.class,version=WebConfig.dubboServiceVersion)
public class CCheckinLuckyServiceImpl implements CCheckinLuckyService{
    
	@Autowired
	private CCheckinLuckyDao cCheckinLuckyDao;

	@Override
	public List<CCheckinLucky> selectAppList() {
		return cCheckinLuckyDao.selectAppList();
	}

}
