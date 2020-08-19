package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.MRankDarenConfigDao;
import com.mc.bzly.model.user.MRankDarenConfig;
import com.mc.bzly.service.user.MRankDarenConfigService;
@Service(interfaceClass = MRankDarenConfigService.class,version = WebConfig.dubboServiceVersion)
public class MRankDarenConfigServiceImpl implements MRankDarenConfigService{

	@Autowired
	private MRankDarenConfigDao mRankDarenConfigDao;
	
	@Override
	public int insert(MRankDarenConfig mRankDarenConfig) {
		mRankDarenConfig.setCreateTime(new Date().getTime());
		return mRankDarenConfigDao.insert(mRankDarenConfig);
	}

	@Override
	public PageInfo<MRankDarenConfig> selectList(MRankDarenConfig mRankDarenConfig) {
		PageHelper.startPage(mRankDarenConfig.getPageNum(), mRankDarenConfig.getPageSize());
		List<MRankDarenConfig> mRankDarenConfigList =mRankDarenConfigDao.selectList(mRankDarenConfig);
		return new PageInfo<>(mRankDarenConfigList);
	}

	@Override
	public int update(MRankDarenConfig mRankDarenConfig) {
		return mRankDarenConfigDao.update(mRankDarenConfig);
	}

	@Override
	public int delete(Integer id) {
		return mRankDarenConfigDao.delete(id);
	}

	@Override
	public MRankDarenConfig selectOne(Integer id) {
		return mRankDarenConfigDao.selectOne(id);
	}

}
