package com.mc.bzly.impl.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.task.MTaskTypeDao;
import com.mc.bzly.model.task.MTaskType;
import com.mc.bzly.service.task.MTaskTypeService;

@Service(interfaceClass = MTaskTypeService.class,version =WebConfig.dubboServiceVersion)
public class MTaskTypeServiceImpl implements MTaskTypeService {

	@Autowired MTaskTypeDao mTaskTypeDao;
	
	@Override
	public List<MTaskType> queryList() {
		return mTaskTypeDao.selectDownList();
	}

}
