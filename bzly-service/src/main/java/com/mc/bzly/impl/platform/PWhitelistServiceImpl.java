package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PWhitelistDao;
import com.mc.bzly.model.platform.PWhitelist;
import com.mc.bzly.service.platform.PWhitelistService;

@Service(interfaceClass = PWhitelistService.class,version = WebConfig.dubboServiceVersion)
public class PWhitelistServiceImpl implements PWhitelistService {

	@Autowired 
	private PWhitelistDao pWhitelistDao;
	
	@Transactional
	@Override
	public int add(PWhitelist pWhitelist) throws Exception {
		pWhitelist.setCreateTime(new Date().getTime());
		pWhitelistDao.insert(pWhitelist);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PWhitelist pWhitelist) throws Exception {
		pWhitelistDao.update(pWhitelist);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		pWhitelistDao.delete(id);
		return 1;
	}

	@Override
	public PWhitelist queryOne(Integer id) {
		return pWhitelistDao.selectOne(id);
	}

	@Override
	public PageInfo<PWhitelist> queryList(PWhitelist pWhitelist) {
		PageHelper.startPage(pWhitelist.getPageNum(), pWhitelist.getPageSize());
		List<PWhitelist> pWhitelistList = pWhitelistDao.selectList(pWhitelist);
		return new PageInfo<>(pWhitelistList);
	}

	@Override
	public List<PWhitelist> queryByContent(String content) {
		return pWhitelistDao.selectByContent(content);
	}

}
