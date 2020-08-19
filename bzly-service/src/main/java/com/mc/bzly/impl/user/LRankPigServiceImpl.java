package com.mc.bzly.impl.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.LRankPigDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LRankCoin;
import com.mc.bzly.model.user.LRankPig;
import com.mc.bzly.model.user.LRankPigSimple;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.LRankPigService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LRankPigService.class,version = WebConfig.dubboServiceVersion)
public class LRankPigServiceImpl implements LRankPigService {

	@Autowired
	private LRankPigDao lRankPigDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LPigChangeDao lPigChangeDao;
	
	@Override
	public PageInfo<LRankPig> queryList(LRankPig lRankPig) {
		if(!StringUtil.isNullOrEmpty(lRankPig.getRankType())){
			if(lRankPig.getRankType().intValue() == 3){
				if(!StringUtil.isNullOrEmpty(lRankPig.getRankDate())) {
					lRankPig.setRankDate(lRankPig.getRankDate().substring(0, 7));
				}
			}
		}
		PageHelper.startPage(lRankPig.getPageNum(), lRankPig.getPageSize());
		List<LRankPig> lRankPigs = lRankPigDao.selectList(lRankPig);
		return new PageInfo<>(lRankPigs);
	}

	@Override
	public PageInfo<LRankPigSimple> listF(LRankPig lRankPig) throws Exception {
		// 查询当前上个月份
		lRankPig.setRankDate(DateUtil.getLastMonth());
		PageHelper.startPage(lRankPig.getPageNum(), lRankPig.getPageSize());
		List<LRankPigSimple> LRankPigSimples= lRankPigDao.selectFlist(lRankPig);
		return new PageInfo<>(LRankPigSimples);
	}

	@Override
	public Map<String, Object> queryMyRank(String userId,Integer rankType) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String rankDate = DateUtil.getLastMonth();
		String startDate = rankDate+"-01";
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(startDate));
		String s = sdf.format(c.getTime());
		
		String startStr = sdf1.format(c.getTime());
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		String e = sdf.format(c.getTime());
		
		String endStr = sdf1.format(c.getTime()).substring(4,8);
		resultMap.put("startStr", startStr);
		resultMap.put("endStr", endStr);
		
		
		LRankPigSimple lRankPigSimple = lRankPigDao.selectByUser(userId,rankDate,rankType);
		if(lRankPigSimple == null){
			lRankPigSimple = new LRankPigSimple();
			SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String endStr2 = e + " 23:59:59";
			String startStr2 = s +" 00:00:00";
			long startTime = sdf2.parse(startStr2).getTime();
			long endTime = sdf2.parse(endStr2).getTime();
			long pigBalance = lPigChangeDao.selectLastMonthPigWin(userId,startTime,endTime);
			lRankPigSimple.setRankOrder(101);
			lRankPigSimple.setPigBalance(pigBalance);
		}
		resultMap.put("lRankPigSimple", lRankPigSimple);
		return resultMap;
	}

	@Override
	public PageInfo<LRankPigSimple> listFDay(LRankPig lRankPig) {
		PageHelper.startPage(lRankPig.getPageNum(), lRankPig.getPageSize());
		List<LRankPigSimple> LRankPigSimples= lRankPigDao.selectFlist(lRankPig);
		return new PageInfo<>(LRankPigSimples);
	}

	@Override
	public Map<String, Object> queryMyRankDay(String userId, int rankType) throws ParseException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String currentDate = DateUtil.getCurrentDate5();
		LRankPig lRankpig = new LRankPig();
		lRankpig.setRankDate(currentDate);
		lRankpig.setRankType(rankType);
		List<LRankPigSimple> LRankPigSimples = lRankPigDao.selectFlist(lRankpig);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long todayTime = sdf.parse(currentDate + " 00:00:00").getTime();
		
		String latestDate = lRankPigDao.selectLatest7Date();
		lRankpig.setUserId(userId);
		LRankPig me = lRankPigDao.selectByUserOne(lRankpig);
		Double coinsD = lPigChangeDao.selectByChangeTypeRank(userId, todayTime);
		long amount = coinsD == null?0l:coinsD.longValue();
		// 查询当前用户累计金币
		if(me == null){
			MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
			me = new LRankPig();
			me.setImageUrl(mUserInfo.getProfile());
			me.setMobile(mUserInfo.getMobile());
			me.setRankOrder(31);
		}
		me.setPigBalance(amount);
		me.setMobile(me.getMobile().substring(0,3)+"****"+me.getMobile().substring(7,11));
		resultMap.put("lRankCoins", LRankPigSimples);
		resultMap.put("historys", latestDate);
		resultMap.put("my", me);
		return resultMap;
	}
}
