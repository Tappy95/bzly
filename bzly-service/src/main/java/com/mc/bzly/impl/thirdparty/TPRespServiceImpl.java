package com.mc.bzly.impl.thirdparty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPRespDao;
import com.mc.bzly.model.thirdparty.TPResp;
import com.mc.bzly.service.thirdparty.TPRespService;

@Service(interfaceClass = TPRespService.class,version = WebConfig.dubboServiceVersion)
public class TPRespServiceImpl implements TPRespService {

	@Autowired 
	private TPRespDao tpRespDao;
	
	@Transactional
	@Override
	public int add(TPResp tpResp) throws Exception {
		tpRespDao.insert(tpResp);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPResp tpResp) throws Exception {
		tpRespDao.update(tpResp);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		tpRespDao.delete(id);
		return 1;
	}

	@Override
	public TPResp queryOne(Integer id) {
		return tpRespDao.selectOne(id);
	}

	@Override
	public PageInfo<TPResp> queryList(TPResp tpResp) {
		PageHelper.startPage(tpResp.getPageNum(), tpResp.getPageSize());
		List<TPResp> tpRespList = tpRespDao.selectList(tpResp);
		return new PageInfo<>(tpRespList);
	}
}
