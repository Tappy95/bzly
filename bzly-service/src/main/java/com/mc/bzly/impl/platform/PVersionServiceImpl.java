package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PVersionDao;
import com.mc.bzly.model.platform.PVersion;
import com.mc.bzly.service.platform.PVersionService;

@Service(interfaceClass = PVersionService.class,version = WebConfig.dubboServiceVersion)
public class PVersionServiceImpl implements PVersionService {

	@Autowired 
	private PVersionDao pVersionDao;
	
	@Override
	public int add(PVersion pVersion) throws Exception {
		pVersion.setCreateTime(new Date().getTime());
		pVersion.setNeedUpdate(2);
		pVersionDao.insert(pVersion);
		return 1;
	}

	@Override
	public int modify(PVersion pVersion) throws Exception {
		pVersionDao.update(pVersion);
		return 1;
	}

	@Override
	public int remove(Integer id) throws Exception {
		pVersionDao.delete(id);
		return 1;
	}

	@Override
	public PVersion queryOne(Integer id) {
		return pVersionDao.selectOne(id);
	}

	@Override
	public PageInfo<PVersion> queryList(PVersion pVersion) {
		PageHelper.startPage(pVersion.getPageNum(), pVersion.getPageSize());
		List<PVersion> pVersionList = pVersionDao.selectList(pVersion);
		return new PageInfo<>(pVersionList);
	}
}
