package com.mc.bzly.impl.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserActivityDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LUserActivity;
import com.mc.bzly.service.user.LUserActivityService;

@Service(interfaceClass = LUserActivityService.class,version = WebConfig.dubboServiceVersion)
public class LUserActivityServiceImpl implements LUserActivityService {

	@Autowired
	private LUserActivityDao lUserActivityDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Override
	public Map<String, Object> myActivity(String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String today = sdf.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = sdf.format(c.getTime());
		resultMap.put("today", today);
		LUserActivity todayLUserActivity = lUserActivityDao.selectByDate(today, userId);
		if(todayLUserActivity == null){
			resultMap.put("score", 100);
			resultMap.put("updownStatus", 1); // 1-上升 2-下降
			resultMap.put("updownScore", 0);
		}else{
			resultMap.put("score", todayLUserActivity.getQualityScore());
			LUserActivity yesterdayLUserActivity = lUserActivityDao.selectByDate(yesterday, userId);
			if(yesterdayLUserActivity == null){
				resultMap.put("updownStatus", 1); // 1-上升 2-下降
				resultMap.put("updownScore", 0);
			}else{
				int cz = todayLUserActivity.getQualityScore() - yesterdayLUserActivity.getQualityScore();
				if(cz > 0){
					resultMap.put("updownStatus", 1); // 1-上升 2-下降
					resultMap.put("updownScore", cz);
				}else{
					cz = yesterdayLUserActivity.getQualityScore() - todayLUserActivity.getQualityScore();
					resultMap.put("updownStatus", 2); // 1-上升 2-下降
					resultMap.put("updownScore", cz);
				}
			}
		}
		return resultMap;
	}

	@Override
	public void modifyScore(LUserActivity lUserActivity) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		lUserActivity.setCreateDate(today);
		lUserActivityDao.updateScore(lUserActivity);
	}

	@Override
	public void openActivity(String userId, Integer openActivity) {
		mUserInfoDao.updateOpenActivity(userId,openActivity);
	}

}
