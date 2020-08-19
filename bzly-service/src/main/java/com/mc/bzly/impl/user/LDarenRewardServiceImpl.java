package com.mc.bzly.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LDarenRewardDao;
import com.mc.bzly.model.user.LDarenReward;
import com.mc.bzly.service.user.LDarenRewardService;

@Service(interfaceClass = LDarenRewardService.class,version = WebConfig.dubboServiceVersion)
public class LDarenRewardServiceImpl implements LDarenRewardService {

	@Autowired
	private LDarenRewardDao lDarenRewardDao;
	
	@Override
	public PageInfo<LDarenReward> list(LDarenReward lDarenReward) {
		PageHelper.startPage(lDarenReward.getPageNum(), lDarenReward.getPageSize());
		List<LDarenReward> lDarenRewardList = lDarenRewardDao.selectList(lDarenReward);
		return new PageInfo<>(lDarenRewardList);
	}

}
