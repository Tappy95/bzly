package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserRewardDao;
import com.mc.bzly.model.user.LUserReward;
import com.mc.bzly.service.user.LUserRewardService;

@Service(interfaceClass = LUserRewardService.class,version = WebConfig.dubboServiceVersion)
public class LUserRewardServiceImpl implements LUserRewardService {

	@Autowired
	private LUserRewardDao lUserRewardDao;
	
	@Transactional
	@Override
	public int add(LUserReward lUserReward) {
		lUserReward.setRewardTime(new Date().getTime());
		lUserRewardDao.insert(lUserReward);
		return 1;
	}

	@Override
	public LUserReward queryOne(Integer addressId) {
		return lUserRewardDao.selectOne(addressId);
	}

	@Override
	public PageInfo<LUserReward> queryList(LUserReward lUserReward) {
		PageHelper.startPage(lUserReward.getPageNum(), lUserReward.getPageSize());
		List<LUserReward> lUserRewardList = lUserRewardDao.selectList(lUserReward);
		return new PageInfo<>(lUserRewardList);
	}

	@Override
	public double queryTotalReward(LUserReward lUserReward) {
		if(lUserReward.getRewardType() == 1){
			return lUserRewardDao.selectTotalReward(lUserReward.getRewardType());
		}else{
			return lUserRewardDao.selectTotalReward(null);
		}
	}

}
