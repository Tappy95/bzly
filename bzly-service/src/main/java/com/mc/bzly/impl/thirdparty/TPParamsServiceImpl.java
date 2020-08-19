package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPParamsDao;
import com.mc.bzly.model.thirdparty.TPParams;
import com.mc.bzly.service.thirdparty.TPParamsService;

@Service(interfaceClass = TPParamsService.class,version = WebConfig.dubboServiceVersion)
public class TPParamsServiceImpl implements TPParamsService {

	@Autowired 
	private TPParamsDao tpParamsDao;
	
	@Transactional
	@Override
	public int add(TPParams tpParams) throws Exception {
		tpParams.setCreateTime(new Date().getTime());
		tpParamsDao.insert(tpParams);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPParams tpParams) throws Exception {
		tpParamsDao.update(tpParams);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		tpParamsDao.delete(id);
		return 1;
	}

	@Override
	public TPParams queryOne(Integer id) {
		return tpParamsDao.selectOne(id);
	}

	@Override
	public PageInfo<TPParams> queryList(TPParams tpParams) {
		PageHelper.startPage(tpParams.getPageNum(), tpParams.getPageSize());
		List<TPParams> tpParamsList = tpParamsDao.selectList(tpParams);
		return new PageInfo<>(tpParamsList);
	}
}
