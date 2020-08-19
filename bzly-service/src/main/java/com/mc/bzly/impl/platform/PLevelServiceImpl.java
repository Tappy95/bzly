package com.mc.bzly.impl.platform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PLevelDao;
import com.mc.bzly.model.platform.PLevel;
import com.mc.bzly.service.platform.PLevelService;

@Service(interfaceClass = PLevelService.class,version = WebConfig.dubboServiceVersion)
public class PLevelServiceImpl implements PLevelService {

	@Autowired 
	private PLevelDao pLevelDao;
	
	@Transactional
	@Override
	public int add(PLevel pLevel) throws Exception {
		pLevelDao.insert(pLevel);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PLevel pLevel) throws Exception {
		pLevelDao.update(pLevel);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer bannerId) throws Exception {
		pLevelDao.delete(bannerId);
		return 1;
	}

	@Override
	public PLevel queryOne(Integer bannerId) {
		return pLevelDao.selectOne(bannerId);
	}

	@Override
	public PageInfo<PLevel> queryList(PLevel pLevel) {
		PageHelper.startPage(pLevel.getPageNum(), pLevel.getPageSize());
		List<PLevel> pLevelList = pLevelDao.selectList(pLevel);
		return new PageInfo<>(pLevelList);
	}

	@Override
	public PLevel queryLowerLevel() {
		return pLevelDao.selectList(null).get(0);
	}

	@Override
	public List<PLevel> downLevelList() {
		return pLevelDao.selectList(new PLevel());
	}

}
