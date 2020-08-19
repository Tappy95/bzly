package com.mc.bzly.service.egg;

import java.util.List;
import java.util.Map;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.egg.EGoleEggOrder;

public interface EGoleEggOrderServcie {

	Map<String,Object> page(EGoleEggOrder eGoleEggOrder);
	
	EGoleEggOrder info(long id);
	
	int updateProhibit(int isProhibit,long id);
	
	Result smashEggs(Integer typeId,String userId);
	
	Map<String,Object> eggList(EGoleEggOrder eGoleEggOrder);
	
	Map<String,Object> appInfo(long id);
	
	Result extractPassword(long id,String payPassword,String userId);
	
	Map<String,Object> isCard(String cardNumber);
	
	int activationCard(String cardNumber,String cardPassword,Integer accountId);
	
	Map<String,Object> useList(EGoleEggOrder eGoleEggOrder);
	
	List<Map<String,Object>> selectNewsRoll();
	
	Result modifyPassword(String cardNumber,String cardPassword,Integer accountId);
}
