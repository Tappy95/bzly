package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LRankMachineDao;
import com.mc.bzly.model.user.LRankMachine;
import com.mc.bzly.service.user.LRankMachineService;

@Service(interfaceClass = LRankMachineService.class,version = WebConfig.dubboServiceVersion)
public class LRankMachineServiceImpl implements LRankMachineService{
	
    @Autowired
	private LRankMachineDao lRankMachineDao;
	
	@Override
	public int add(LRankMachine lRankMachine) {
		lRankMachine.setCreateTime(new Date().getTime());
		return lRankMachineDao.add(lRankMachine);
	}

	@Override
	public PageInfo<LRankMachine> list(LRankMachine lRankMachine) {
		PageHelper.startPage(lRankMachine.getPageNum(), lRankMachine.getPageSize());
		List<LRankMachine> lRankMachineList =lRankMachineDao.selectList(lRankMachine);
		return new PageInfo<>(lRankMachineList);
	}

	@Override
	public LRankMachine info(int id) {
		return lRankMachineDao.selectOne(id);
	}

	@Override
	public int update(LRankMachine lRankMachine) {
		return lRankMachineDao.update(lRankMachine);
	}

	@Override
	public int delete(int id) {
		return lRankMachineDao.delete(id);
	}

}
