package com.mc.bzly.impl.thirdparty;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.MDarenRewardDao;
import com.mc.bzly.model.thirdparty.MDarenReward;
import com.mc.bzly.service.thirdparty.MDarenRewardService;

@Service(interfaceClass = MDarenRewardService.class,version = WebConfig.dubboServiceVersion)
public class MDarenRewardServiceImpl implements MDarenRewardService{
	
	@Autowired
	private MDarenRewardDao mDarenRewardDao;

	@Override
	public int save(MDarenReward mDarenReward) {
		mDarenReward.setCreateTime(new Date().getTime());
		return mDarenRewardDao.save(mDarenReward);
	}

	@Override
	public PageInfo<MDarenReward> selectList(MDarenReward mDarenReward) {
		PageHelper.startPage(mDarenReward.getPageNum(), mDarenReward.getPageSize());
		List<MDarenReward> mDarenRewards =mDarenRewardDao.selectList(mDarenReward);
		return new PageInfo<>(mDarenRewards);
	}

	@Override
	public MDarenReward selectOne(int id) {
		return mDarenRewardDao.selectOne(id);
	}

	@Override
	public int update(MDarenReward mDarenReward) {
		MDarenReward old=mDarenRewardDao.selectOne(mDarenReward.getId());
		if(old.getCoin().intValue()!=mDarenReward.getCoin().intValue()) {
			mDarenRewardDao.delete(old.getId());
			mDarenReward.setCreateTime(new Date().getTime());
			mDarenRewardDao.save(mDarenReward);
			return 1;
		}
		mDarenRewardDao.update(mDarenReward);
		return 1;
	}

	@Override
	public int delete(int id) {
		return mDarenRewardDao.delete(id);
	}

}
