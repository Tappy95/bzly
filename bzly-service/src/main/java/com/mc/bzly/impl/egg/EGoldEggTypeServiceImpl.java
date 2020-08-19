package com.mc.bzly.impl.egg;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.egg.EGoldEggTypeDao;
import com.mc.bzly.dao.egg.EGoleEggOrderDao;
import com.mc.bzly.dao.egg.EUserGoldEggDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.egg.EGoldEggType;
import com.mc.bzly.model.egg.EUserGoldEgg;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.egg.EGoldEggTypeService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass=EGoldEggTypeService.class,version=WebConfig.dubboServiceVersion)
public class EGoldEggTypeServiceImpl implements EGoldEggTypeService{
	
    @Autowired
	private EGoldEggTypeDao eGoldEggTypeDao;
    @Autowired
    private EUserGoldEggDao eUserGoldEggDao;
    @Autowired
    private EGoleEggOrderDao eGoleEggOrderDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
	
	@Override
	public int add(EGoldEggType eGoldEggType) {
		eGoldEggType.setCreatorTime(new Date().getTime());
		return eGoldEggTypeDao.add(eGoldEggType);
	}

	@Override
	public Map<String,Object> selectList() {
		Map<String,Object> data=new HashMap<String,Object>();
		List<EGoldEggType> list=eGoldEggTypeDao.selectList();
		data.put("list", list);
		return data;
	}

	@Override
	public EGoldEggType selectOne(Integer id) {
		return eGoldEggTypeDao.selectOne(id);
	}

	@Override
	public int update(EGoldEggType eGoldEggType) {
		return eGoldEggTypeDao.update(eGoldEggType);
	}

	@Override
	public int delete(Integer id) {
		return eGoldEggTypeDao.delete(id);
	}

	@Override
	public Map<String, Object> smashEggInfo(String userId) {
		Map<String,Object> data=new HashMap<String,Object>();
		MUserInfo mUserInfo=mUserInfoDao.selectOne(userId);
		List<EGoldEggType> list=eGoldEggTypeDao.selectList();
		data.put("list", list);
		data.put("pigCoin", mUserInfo.getPigCoin());//账户金猪数
		EUserGoldEgg eUserGoldEgg=eUserGoldEggDao.selectOne(userId);
		if(StringUtil.isNullOrEmpty(eUserGoldEgg) || eUserGoldEgg.getFrequency()==0) {
			
			eUserGoldEgg=new EUserGoldEgg();
			eUserGoldEgg.setUserId(userId);
			eUserGoldEgg.setFrequency(10);
			eUserGoldEgg.setCreatorTime(new Date().getTime());
			eUserGoldEggDao.add(eUserGoldEgg);
			
			data.put("useFrequency", 0);//砸金蛋使用次数
			data.put("totalFrequency", 10);//总次数
			return data;
		}
		int count=eGoleEggOrderDao.selectUserCount(userId);
		data.put("useFrequency", count);//砸金蛋使用次数
		data.put("totalFrequency", eUserGoldEgg.getFrequency());//总次数
		return data;
	}

}
