package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.MUserGyroDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.impl.redis.AWorker;
import com.mc.bzly.model.user.MUserGyro;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.user.MUserGyroService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MUserGyroService.class,version = WebConfig.dubboServiceVersion)
public class MUserGyroServiceImpl implements MUserGyroService{
	@Autowired
	private MUserGyroDao mUserGyroDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;

	@Override
	public int addGyro(MUserGyro mUserGyro) {
		long time=new Date().getTime();
		String res=new AWorker<String>() {
			@Override
			public String call(String userId) throws Exception {
				MUserGyro gyro1=mUserGyroDao.selectOne(mUserGyro.getUserId(), mUserGyro.getPageType(), 1);
				if(StringUtil.isNullOrEmpty(gyro1)) {
					mUserGyro.setUpdateTime(time);
					mUserGyro.setNumber(1);
					mUserGyroDao.add(mUserGyro);
					return "1";
				}else {
					if(time-gyro1.getUpdateTime()>=600000) {
						MUserGyro gyro2=mUserGyroDao.selectOne(mUserGyro.getUserId(), mUserGyro.getPageType(), 2);
						if(StringUtil.isNullOrEmpty(gyro2)) {
							mUserGyro.setUpdateTime(time);
							mUserGyro.setNumber(2);
							mUserGyroDao.add(mUserGyro);
							return "1";
						}else {
							if(time-gyro2.getUpdateTime()>=600000) {
								if(gyro2.getUpdateTime()>gyro1.getUpdateTime()) {
									gyro1.setGyroX(mUserGyro.getGyroX());
									gyro1.setGyroY(mUserGyro.getGyroY());
									gyro1.setGyroZ(mUserGyro.getGyroZ());
									gyro1.setUpdateTime(time);
									mUserGyroDao.update(gyro1);	
									return "1";
								}else {
									gyro2.setGyroX(mUserGyro.getGyroX());
									gyro2.setGyroY(mUserGyro.getGyroY());
									gyro2.setGyroZ(mUserGyro.getGyroZ());
									gyro2.setUpdateTime(time);
									mUserGyroDao.update(gyro2);	
									return "1";
								}
							}
						}
					}
				}
				return "0";
			}
		}.execute(mUserGyro.getUserId(),"redis_lock_gryo_"+mUserGyro.getUserId());
		
		return Integer.parseInt(res);
	}

	@Override
	public Map<String, Object> page(Integer accountId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		MUserInfo user=mUserInfoDao.selectByAccountId(accountId);
		if(StringUtil.isNullOrEmpty(user)) {
			result.put("res", "2");
			return result;
		}
		result.put("list", mUserGyroDao.selectList(user.getUserId()));
		return result;
	}

}
