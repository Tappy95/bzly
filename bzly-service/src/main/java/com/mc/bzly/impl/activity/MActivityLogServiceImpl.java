package com.mc.bzly.impl.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.activity.MActivityInfoDao;
import com.mc.bzly.dao.activity.MActivityLogDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.user.MWeakupCheckinDao;
import com.mc.bzly.model.activity.MActivityInfo;
import com.mc.bzly.model.activity.MActivityLog;
import com.mc.bzly.model.user.MWeakupCheckin;
import com.mc.bzly.service.activity.MActivityLogService;

@Service(interfaceClass = MActivityLogService.class,version =  WebConfig.dubboServiceVersion)
public class MActivityLogServiceImpl implements MActivityLogService {

	@Autowired 
	private MActivityLogDao mActivityLogDao;

	@Autowired 
	private MActivityInfoDao mActivityInfoDao;

	@Autowired 
	private PDictionaryDao pDictionaryDao;
	
	@Autowired
	private MWeakupCheckinDao mWeakupCheckinDao;

	@Transactional
	@Override
	public int add(MActivityLog mActivityLog) throws Exception {
		mActivityLogDao.insert(mActivityLog);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MActivityLog mActivityLog) throws Exception {
		mActivityLogDao.update(mActivityLog);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer lId) throws Exception {
		mActivityLogDao.delete(lId);
		return 1;
	}

	@Override
	public MActivityLog queryOne(Integer lId) {
		return mActivityLogDao.selectOne(lId);
	}

	@Override
	public PageInfo<MActivityLog> queryList(MActivityLog mActivityLog) {
		PageHelper.startPage(mActivityLog.getPageNum(), mActivityLog.getPageSize());
		List<MActivityLog> mActivityLogList = mActivityLogDao.selectList(mActivityLog);
		return new PageInfo<>(mActivityLogList);
	}

	/**
	 * 0点到7点---待揭晓
	 * 7点到8点---不展示
	 * 8点到24点--展示今日结果
	 */
	@Override
	public Map<String, Object> queryCheckinHome(String userId) {
		
		MActivityInfo info = mActivityInfoDao.selectByActivityType(1);
		
		String startTime = DateUtil.getCurrentDate().substring(0,10) + " " + info.getStartTime();
		String stopTime = DateUtil.getCurrentDate().substring(0,10) + " " + info.getStopTime();
		
		Date startDate = DateUtil.praseStringDate(startTime);
		Date stopDate = DateUtil.praseStringDate(stopTime);
		
		Map<String, Object> resultMap = new HashMap<>();
		Date date = new Date();
		List<MActivityLog> logList = mActivityLogDao.selectLatestTwo(info.getActId());
		
		MWeakupCheckin checkin = new MWeakupCheckin();
		checkin.setLogId(logList.get(0).getlId());
		checkin.setCheckinUser(userId);
		List<MWeakupCheckin> mWeakupCheckins = mWeakupCheckinDao.selectList(checkin);
		if(mWeakupCheckins != null && mWeakupCheckins.size() > 0 ){
			resultMap.put("hasJoin", true);
		}else{
			resultMap.put("hasJoin", false);
		}
		resultMap.put("thisLog", logList.get(0));
		if(date.getTime() < startDate.getTime()){ // 0点到7点---待揭晓
			resultMap.put("todayLog", new MActivityLog());
			resultMap.put("title", pDictionaryDao.selectByName("wait_publish").getDicValue());
			resultMap.put("code", 1);
		}else if(date.getTime() < stopDate.getTime() ){
			resultMap.put("todayLog", new MActivityLog());
			resultMap.put("title", pDictionaryDao.selectByName("checkinning").getDicValue()); // 打卡中
			resultMap.put("code", 2);
		}else{
			resultMap.put("todayLog", logList.get(1));
			resultMap.put("title",  pDictionaryDao.selectByName("check_result").getDicValue());
			resultMap.put("code", 3);
		}
		
		String sycleNum = logList.get(0).getCycleNum();
		String startTimeStr = sycleNum.substring(0, 4)+"-"+sycleNum.substring(4,6)+"-"+sycleNum.substring(6,8) +" "+info.getStartTime();
		long startTimeL = DateUtil.praseStringDate(startTimeStr).getTime();
		long now = new Date().getTime();
		resultMap.put("milliseconds",startTimeL-now);
		
		// 查询最新的两条记录
		return resultMap;
	}

}
