package com.mc.bzly.impl.platform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PApplicationMarketDao;
import com.mc.bzly.model.platform.PAdmin;
import com.mc.bzly.model.platform.PApplicationMarket;
import com.mc.bzly.service.platform.PApplicationMarketService;

@Service(interfaceClass = PApplicationMarketService.class,version = WebConfig.dubboServiceVersion)
public class PApplicationMarketServiceImpl implements PApplicationMarketService{
	
    @Autowired
	private PApplicationMarketDao pApplicationMarketDao;
	
	@Override
	public Integer save(PApplicationMarket pApplicationMarket) {
		return pApplicationMarketDao.save(pApplicationMarket);
	}

	@Override
	public PageInfo<PApplicationMarket> selectList(PApplicationMarket pApplicationMarket) {
		PageHelper.startPage(pApplicationMarket.getPageNum(), pApplicationMarket.getPageSize());
		List<PApplicationMarket> pApplicationMarketList = pApplicationMarketDao.selectList(pApplicationMarket);
		return new PageInfo<>(pApplicationMarketList);
	}

	@Override
	public PApplicationMarket selectOne(Integer id) {
		return pApplicationMarketDao.selectOne(id);
	}

	@Override
	public Integer update(PApplicationMarket pApplicationMarket) {
		return pApplicationMarketDao.update(pApplicationMarket);
	}

	@Override
	public Integer delete(Integer id) {
		return pApplicationMarketDao.delete(id);
	}

}
