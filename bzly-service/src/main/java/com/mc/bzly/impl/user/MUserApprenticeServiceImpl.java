package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.MUserApprenticeDao;
import com.mc.bzly.model.user.MUserApprentice;
import com.mc.bzly.service.user.MUserApprenticeService;

@Service(interfaceClass = MUserApprenticeService.class,version = WebConfig.dubboServiceVersion)
public class MUserApprenticeServiceImpl implements MUserApprenticeService {

	@Autowired
	private MUserApprenticeDao mUserApprenticeDao;
	
	@Transactional
	@Override
	public int add(MUserApprentice mUserApprentice) {
		mUserApprentice.setCreateTime(new Date().getTime());
		mUserApprentice.setUpdateTime(new Date().getTime());
		mUserApprenticeDao.insert(mUserApprentice);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MUserApprentice mUserApprentice) {
		mUserApprentice.setUpdateTime(new Date().getTime());
		mUserApprenticeDao.update(mUserApprentice);
		return 1;
	}

	@Override
	public int remove(Integer apprenticeId) {
		mUserApprenticeDao.delete(apprenticeId);
		return 1;
	}

	@Override
	public MUserApprentice queryOne(Integer apprenticeId) {
		return mUserApprenticeDao.selectOne(apprenticeId);
	}

	@Override
	public PageInfo<MUserApprentice> queryList(MUserApprentice mUserApprentice) {
		PageHelper.startPage(mUserApprentice.getPageNum(), mUserApprentice.getPageSize());
		List<MUserApprentice> mUserApprenticeList = mUserApprenticeDao.selectList(mUserApprentice);
		return new PageInfo<>(mUserApprenticeList);
	}

	@Override
	public PageInfo<MUserApprentice> selectPage(MUserApprentice mUserApprentice) {
		PageHelper.startPage(mUserApprentice.getPageNum(), mUserApprentice.getPageSize());
		List<MUserApprentice> mUserApprenticeList = mUserApprenticeDao.selectPage(mUserApprentice);
		return new PageInfo<>(mUserApprenticeList);
	}

	@Override
	public Map<String, Object> wishMentorCount(String userId) {
		return mUserApprenticeDao.selectWishCount(userId);
	}

	@Override
	public Map<String, Object> wishRewardPage(String userId, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		int pageIndex=pageSize*(pageNum-1);
		List<Map<String, Object>> rewardList = mUserApprenticeDao.selectWishRewardList(userId, pageIndex, pageSize);
		int total=mUserApprenticeDao.selectWishRewardCount(userId);
		result.put("list", rewardList);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> wishNumberPage(String userId, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		int pageIndex=pageSize*(pageNum-1);
		List<Map<String, Object>> numberList = mUserApprenticeDao.selectWishNumberList(userId, pageIndex, pageSize);
		int total=mUserApprenticeDao.selectWishNumberCount(userId);
		result.put("list", numberList);
		result.put("total", total);
		return result;
	}

}
