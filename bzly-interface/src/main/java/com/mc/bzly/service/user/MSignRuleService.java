package com.mc.bzly.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.MSignRule;

public interface MSignRuleService {
	 
	int add(MSignRule mSignRule);
    
    PageInfo<MSignRule> queryList(MSignRule mSignRule);
     
    MSignRule info(Integer ruleId);
     
    int updateRule(MSignRule mSignRule);
     
    int deleteRule(Integer ruleId);
    
    List<MSignRule> appList();
}
