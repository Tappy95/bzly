package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PIpBlacklistDao;
import com.mc.bzly.model.platform.PIpBlacklist;
import com.mc.bzly.service.platform.PIpBlacklistService;

@Service(interfaceClass = PIpBlacklistService.class,version = WebConfig.dubboServiceVersion)
public class PIpBlacklistServiceImpl implements PIpBlacklistService {

	@Autowired 
	private PIpBlacklistDao pIpBlacklistDao;
	
	@Transactional
	@Override
	public int add(PIpBlacklist pIpBlacklist) throws Exception {
		pIpBlacklist.setCreateTime(new Date().getTime());
		pIpBlacklistDao.insert(pIpBlacklist);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PIpBlacklist pIpBlacklist) throws Exception {
		pIpBlacklistDao.update(pIpBlacklist);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		pIpBlacklistDao.delete(id);
		return 1;
	}

	@Override
	public PIpBlacklist queryOne(Integer id) {
		return pIpBlacklistDao.selectOne(id);
	}

	@Override
	public PIpBlacklist queryByIp(String ip) {
		return pIpBlacklistDao.selectByIp(ip);
	}

	@Override
	public PageInfo<PIpBlacklist> queryList(PIpBlacklist pIpBlacklist) {
		PageHelper.startPage(pIpBlacklist.getPageNum(), pIpBlacklist.getPageSize());
		List<PIpBlacklist> pIpBlacklistList = pIpBlacklistDao.selectList(pIpBlacklist);
		return new PageInfo<>(pIpBlacklistList);
	}

}
