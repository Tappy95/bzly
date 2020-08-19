package com.mc.bzly.service.user;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.MessageList;

public interface LPigChangeService {
	 
	PageInfo<LPigChange> queryList(LPigChange lPigChange);
	 
	Map<String, Object> pageList(LPigChange lPigChange);
	
	LPigChange info(Integer id);
	
	List<MessageList> queryMessageList();
	
	List<Map<String,Object>> exclPigChange(LPigChange lPigChange);

	Result addPigCoin(String userId) throws ParseException;

}
