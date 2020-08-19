package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.MRankConfigDao;
import com.mc.bzly.model.platform.MRankConfig;
import com.mc.bzly.service.platform.MRankConfigService;

@Service(interfaceClass = MRankConfigService.class,version = WebConfig.dubboServiceVersion)
public class MRankConfigServiceImpl implements MRankConfigService {

	@Autowired 
	private MRankConfigDao mRankConfigDao;
	
	@Transactional
	@Override
	public int add(MRankConfig mRankConfig) throws Exception {
		long now = new Date().getTime();
		mRankConfig.setCreateTime(now);
		mRankConfig.setUpdateTime(now);
		mRankConfigDao.insert(mRankConfig);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MRankConfig mRankConfig) throws Exception {
		long now = new Date().getTime();
		mRankConfig.setUpdateTime(now);
		mRankConfigDao.update(mRankConfig);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		mRankConfigDao.delete(id);
		return 1;
	}

	@Override
	public MRankConfig queryOne(Integer id) {
		return mRankConfigDao.selectOne(id);
	}

	@Override
	public PageInfo<MRankConfig> queryList(MRankConfig mRankConfig) {
		PageHelper.startPage(mRankConfig.getPageNum(), mRankConfig.getPageSize());
		List<MRankConfig> mRankConfigList = mRankConfigDao.selectList(mRankConfig);
		return new PageInfo<>(mRankConfigList);
	}

	@Override
	public List<MRankConfig> querySelect() {
		MRankConfig mRankConfig = new MRankConfig();
		mRankConfig.setStatus(1);
		return mRankConfigDao.selectList(mRankConfig);
	}
}
