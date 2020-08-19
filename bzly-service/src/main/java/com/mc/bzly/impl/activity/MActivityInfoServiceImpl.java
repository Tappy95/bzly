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
import com.mc.bzly.dao.user.MWeakupCheckinDao;
import com.mc.bzly.model.activity.MActivityInfo;
import com.mc.bzly.model.activity.MActivityLog;
import com.mc.bzly.model.user.MWeakupCheckin;
import com.mc.bzly.service.activity.MActivityInfoService;

@Service(interfaceClass = MActivityInfoService.class,version = WebConfig.dubboServiceVersion)
public class MActivityInfoServiceImpl implements MActivityInfoService {

	@Autowired 
	private MActivityInfoDao mActivityInfoDao;
	
	@Autowired 
	private MActivityLogDao mActivityLogDao;
	
	@Autowired
	private MWeakupCheckinDao mWeakupCheckinDao;
	
	@Transactional
	@Override
	public int add(MActivityInfo mActivityInfo) throws Exception {
		mActivityInfoDao.insert(mActivityInfo);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MActivityInfo mActivityInfo) throws Exception {
		mActivityInfoDao.update(mActivityInfo);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer actId) throws Exception {
		mActivityInfoDao.delete(actId);
		return 1;
	}

	@Override
	public MActivityInfo queryOne(Integer actId) {
		return mActivityInfoDao.selectOne(actId);
	}

	@Override
	public PageInfo<MActivityInfo> queryList(MActivityInfo mActivityInfo) {
		PageHelper.startPage(mActivityInfo.getPageNum(), mActivityInfo.getPageSize());
		List<MActivityInfo> mActivityInfoList = mActivityInfoDao.selectList(mActivityInfo);
		return new PageInfo<>(mActivityInfoList);
	}

	@Override
	public Map<String, Object> queryCheckInRule(String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		MActivityInfo mActivityInfo = mActivityInfoDao.selectByActivityType(1);
		resultMap.put("rule", mActivityInfo);
		List<MActivityLog> logList = mActivityLogDao.selectLatestTwo(mActivityInfo.getActId());
		
		MWeakupCheckin checkin = new MWeakupCheckin();
		checkin.setLogId(logList.get(0).getlId());
		checkin.setCheckinUser(userId);
		List<MWeakupCheckin> mWeakupCheckins = mWeakupCheckinDao.selectList(checkin);
		if(mWeakupCheckins != null && mWeakupCheckins.size() > 0 ){
			resultMap.put("hasJoin", true);
		}else{
			resultMap.put("hasJoin", false);
		}
		
		String sycleNum = logList.get(0).getCycleNum();
		String startTimeStr = sycleNum.substring(0, 4)+"-"+sycleNum.substring(4,6)+"-"+sycleNum.substring(6,8) +" "+mActivityInfo.getStartTime();
		long startTime = DateUtil.praseStringDate(startTimeStr).getTime();
		long now = new Date().getTime();
		resultMap.put("milliseconds",startTime-now);
		return resultMap;
	}

}
