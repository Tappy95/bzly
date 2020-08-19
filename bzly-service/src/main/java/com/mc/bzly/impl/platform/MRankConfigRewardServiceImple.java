package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.MRankConfigRewardDao;
import com.mc.bzly.model.platform.MRankConfigReward;
import com.mc.bzly.service.platform.MRankConfigRewardService;

@Service(interfaceClass = MRankConfigRewardService.class,version = WebConfig.dubboServiceVersion)
public class MRankConfigRewardServiceImple implements MRankConfigRewardService {

	@Autowired
	private MRankConfigRewardDao mRankConfigRewardDao;
	@Override
	public void add(MRankConfigReward mRankConfigReward) {
		
		mRankConfigReward.setCreateTime(new Date().getTime());
		mRankConfigRewardDao.insert(mRankConfigReward);
	}

	@Override
	public void modify(MRankConfigReward mRankConfigReward) {

		mRankConfigRewardDao.update(mRankConfigReward);
	}

	@Override
	public void remove(Integer id) {
		
		mRankConfigRewardDao.delete(id);
	}

	@Override
	public MRankConfigReward queryOne(Integer id) {

		return mRankConfigRewardDao.selectOne(id);
	}

	@Override
	public PageInfo<MRankConfigReward> queryList(MRankConfigReward mRankConfigReward) {
		PageHelper.startPage(mRankConfigReward.getPageNum(), mRankConfigReward.getPageSize());
		List<MRankConfigReward> list = mRankConfigRewardDao.selectList(mRankConfigReward);
		return new PageInfo<>(list);
	}

}
