package com.mc.bzly.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.MSignRuleDao;
import com.mc.bzly.model.user.MSignRule;
import com.mc.bzly.service.user.MSignRuleService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MSignRuleService.class,version = WebConfig.dubboServiceVersion)
public class MSignRuleServiceImpl implements MSignRuleService {

	@Autowired 
	private MSignRuleDao mSignRuleDao;
	
	@Override
	public int add(MSignRule mSignRule) {
		MSignRule sign=mSignRuleDao.selectStickTime(mSignRule.getStickTime());
		if(!StringUtil.isNullOrEmpty(sign)) {
			return 3;
		}
	  	Integer res=mSignRuleDao.insert(mSignRule);
	  	if(res>0) {
			return res;
	  	}
		return res;
	}

	@Override
	public PageInfo<MSignRule> queryList(MSignRule mSignRule) {
		PageHelper.startPage(mSignRule.getPageNum(), mSignRule.getPageSize());
		List<MSignRule> mSignRuleList =mSignRuleDao.selectList(mSignRule);
		return new PageInfo<>(mSignRuleList);
	}

	@Override
	public MSignRule info(Integer ruleId) {
		return mSignRuleDao.selectId(ruleId);
	}

	@Override
	public int updateRule(MSignRule mSignRule) {
		return mSignRuleDao.updateRule(mSignRule);
	}

	@Override
	public int deleteRule(Integer ruleId) {
		return mSignRuleDao.deleteRule(ruleId);
	}

	@Override
	public List<MSignRule> appList() {
		List<MSignRule> mSignRuleList =mSignRuleDao.selectAppList();
		return mSignRuleList;
	}

}
