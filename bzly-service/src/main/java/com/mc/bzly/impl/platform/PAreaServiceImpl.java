package com.mc.bzly.impl.platform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PAreaDao;
import com.mc.bzly.model.platform.PArea;
import com.mc.bzly.service.platform.PAreaService;

@Service(interfaceClass = PAreaService.class,version = WebConfig.dubboServiceVersion)
public class PAreaServiceImpl implements PAreaService {
	
	@Autowired 
	private PAreaDao pAreaDao;
	
	@Transactional
	@Override
	public int add(PArea pArea) throws Exception {
		pAreaDao.insert(pArea);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PArea pArea) throws Exception {
		pAreaDao.update(pArea);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		pAreaDao.delete(id);
		return 1;
	}

	@Override
	public PArea queryOne(Integer id) {
		return pAreaDao.selectOne(id);
	}

	@Override
	public PageInfo<PArea> queryList(PArea pArea) {
		PageHelper.startPage(pArea.getPageNum(), pArea.getPageSize());
		List<PArea> pAreaList = pAreaDao.selectList(pArea);
		return new PageInfo<>(pAreaList);
	}

	@Override
	public List<PArea> appList(PArea pArea) {
		return pAreaDao.selectList(pArea);
	}

}
