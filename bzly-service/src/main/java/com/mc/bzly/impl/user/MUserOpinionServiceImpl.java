package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserVipDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.MUserOpinionDao;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.MUserOpinion;
import com.mc.bzly.service.user.MUserOpinionService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass=MUserOpinionService.class,version=WebConfig.dubboServiceVersion)
public class MUserOpinionServiceImpl implements MUserOpinionService{

	@Autowired 
	private MUserOpinionDao mUserOpinionDao;
	@Autowired 
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LUserVipDao lUserVipDao;
	
	@Override
	public Integer saveOpinion(MUserOpinion mUserOpinion,String userId) {
		MUserInfo user=mUserInfoDao.selectOne(userId);
		mUserOpinion.setAccountId(user.getAccountId());
		mUserOpinion.setExperience(user.getLevelValue());
		String vipName=lUserVipDao.selectMaxVip(userId);
		if(!StringUtil.isNullOrEmpty(vipName)) {
			mUserOpinion.setVipName(vipName);
		}
		mUserOpinion.setState(1);
		mUserOpinion.setCreaterTime(new Date().getTime());
		return mUserOpinionDao.saveOpinion(mUserOpinion);
	}

	@Override
	public PageInfo<MUserOpinion> selectList(MUserOpinion mUserOpinion) {
		PageHelper.startPage(mUserOpinion.getPageNum(), mUserOpinion.getPageSize());
		List<MUserOpinion> mUserOpinionList = mUserOpinionDao.selectList(mUserOpinion);
		return new PageInfo<>(mUserOpinionList);
	}

	@Override
	public MUserOpinion selectOne(Integer id) {
		return mUserOpinionDao.selectOne(id);
	}

	@Override
	public Integer update(MUserOpinion mUserOpinion) {
		return mUserOpinionDao.update(mUserOpinion);
	}

}
