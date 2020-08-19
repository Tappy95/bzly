package com.mc.bzly.impl.user;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LCoinChangeDao;
import com.mc.bzly.dao.user.LRankCoinDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LRankCoin;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.LRankCoinService;

@Service(interfaceClass = LRankCoinService.class,version = WebConfig.dubboServiceVersion)
public class LRankCoinServiceImpl implements LRankCoinService {

	@Autowired
	private LRankCoinDao lRankCoinDao;
	@Autowired
	private LCoinChangeDao lCoinChangeDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	
	@Override
	public PageInfo<LRankCoin> list(LRankCoin lRankCoin) throws Exception {
		PageHelper.startPage(lRankCoin.getPageNum(), lRankCoin.getPageSize());
		List<LRankCoin> lRankCoins = lRankCoinDao.selectList(lRankCoin);
		return new PageInfo<>(lRankCoins);
	}

	@Override
	public PageInfo<LRankCoin> listF(LRankCoin lRankCoin) {
		PageHelper.startPage(lRankCoin.getPageNum(), lRankCoin.getPageSize());
		List<LRankCoin> lRankCoins = lRankCoinDao.selectList(lRankCoin);
		for (int i = 0; i < lRankCoins.size(); i++) {
			String mobile = lRankCoins.get(i).getMobile();
			mobile = mobile.substring(0,3)+"****"+mobile.substring(7,11);
			lRankCoins.get(i).setMobile(mobile);
		}
		return new PageInfo<>(lRankCoins);
	}

	@Override
	public Map<String, Object> queryHisMy(LRankCoin lRankCoin, String userId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String currentDate = DateUtil.getCurrentDate5();
		lRankCoin.setRankDate(currentDate);
		List<LRankCoin> lRankCoins = lRankCoinDao.selectList(lRankCoin);
		for (int i = 0; i < lRankCoins.size(); i++) {
			String mobile = lRankCoins.get(i).getMobile();
			mobile = mobile.substring(0,3)+"****"+mobile.substring(7,11);
			lRankCoins.get(i).setMobile(mobile);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long todayTime = sdf.parse(currentDate + " 00:00:00").getTime();
		String latestDate = lRankCoinDao.selectLatest7Date();
		lRankCoin.setUserId(userId);
		LRankCoin me = lRankCoinDao.selectOne(lRankCoin);
		Double coinsD = lCoinChangeDao.selectByChangeTypeRank(userId, todayTime);
		long amount = coinsD == null?0l:coinsD.longValue();
		// 查询当前用户累计金币
		if(me == null){
			MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
			me = new LRankCoin();
			me.setImageUrl(mUserInfo.getProfile());
			me.setMobile(mUserInfo.getMobile());
			me.setRankOrder(31);
		}
		me.setCoinBalance(amount);
		me.setMobile(me.getMobile().substring(0,3)+"****"+me.getMobile().substring(7,11));
		resultMap.put("lRankCoins", lRankCoins);
		resultMap.put("historys", latestDate);
		resultMap.put("my", me);
		return resultMap;
	}

}
