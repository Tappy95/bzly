package com.mc.bzly.impl.checkin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.checkin.CCheckinRewardRuleDao;
import com.mc.bzly.model.checkin.CCheckinRewardRule;
import com.mc.bzly.service.checkin.CCheckinRewardRuleService;

@Service(interfaceClass=CCheckinRewardRuleService.class,version=WebConfig.dubboServiceVersion)
public class CCheckinRewardRuleServiceImpl implements CCheckinRewardRuleService{
    
	@Autowired
	private CCheckinRewardRuleDao cCheckinRewardRuleDao;
	
	@Override
	public int add(CCheckinRewardRule cCheckinRewardRule) {
		cCheckinRewardRule.setCreateTime(new Date().getTime());
		return cCheckinRewardRuleDao.add(cCheckinRewardRule);
	}

	@Override
	public Map<String, Object> selectList() {
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("list", cCheckinRewardRuleDao.selectList());
		return data;
	}

	@Override
	public CCheckinRewardRule selectOne(Integer id) {
		return cCheckinRewardRuleDao.selectOne(id);
	}

	@Override
	public int update(CCheckinRewardRule cCheckinRewardRule) {
		return cCheckinRewardRuleDao.update(cCheckinRewardRule);
	}

	@Override
	public int delete(Integer id) {
		return cCheckinRewardRuleDao.delete(id);
	}

}
