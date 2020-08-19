package com.mc.bzly.impl.thirdparty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPCallbackDao;
import com.mc.bzly.model.thirdparty.TPCallback;
import com.mc.bzly.service.thirdparty.TPCallbackService;

@Service(interfaceClass = TPCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TPCallbackServiceImpl implements TPCallbackService {

	@Autowired
	private TPCallbackDao tpCallbackDao;
	
	@Override
	public PageInfo<TPCallback> queryList(TPCallback tpCallback) {
		PageHelper.startPage(tpCallback.getPageNum(), tpCallback.getPageSize());
		List<TPCallback> tpCallbackList = tpCallbackDao.selectList(tpCallback);
		return new PageInfo<>(tpCallbackList);
	}

	@Override
	public List<TPCallback> selectDownList() {
		return tpCallbackDao.selectDownList();
	}

	@Override
	public List<TPCallback> selectExcl(TPCallback tpCallback) {
		return tpCallbackDao.selectExcl(tpCallback);
	}
}
