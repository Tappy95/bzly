package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserBQDao;
import com.mc.bzly.dao.user.LUserSigninDao;
import com.mc.bzly.model.user.LUserBQ;
import com.mc.bzly.model.user.LUserSignin;
import com.mc.bzly.service.user.LUserBQService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserBQService.class,version = WebConfig.dubboServiceVersion)
public class LUserBQServiceImpl implements LUserBQService {

	@Autowired
	private LUserBQDao lUserBQDao;
	@Autowired
	private LUserSigninDao lUserSigninDao;
	
	@Transactional
	@Override
	public void add(LUserBQ lUserBQ) {
		long now = new Date().getTime();
		LUserBQ oldlUserBQ = lUserBQDao.selectByUserId(lUserBQ.getUserId(),1);
		if(oldlUserBQ != null){
			oldlUserBQ.setCardCount(oldlUserBQ.getCardCount() + 1);
			oldlUserBQ.setUpdateTime(now);
			lUserBQDao.update(oldlUserBQ);
		}else{
			lUserBQ.setUpdateTime(now);
			lUserBQ.setCardCount(1);
			lUserBQ.setBqType(1);
			lUserBQDao.insert(lUserBQ);
		}
	}

	@Transactional
	@Override
	public void modify(LUserBQ lUserBQ) {
		LUserBQ oldlUserBQ = lUserBQDao.selectByUserId(lUserBQ.getUserId(),1);
		long now = new Date().getTime();
		oldlUserBQ.setUpdateTime(now);
		lUserBQDao.update(oldlUserBQ);
	}

	@Transactional
	@Override
	public int queryCount(String userId) {
		LUserBQ lUserBQ = lUserBQDao.selectByUserId(userId,1);
		if(lUserBQ == null){
			return 0;
		}else{
			return lUserBQ.getCardCount();
		}
	}

	@Transactional
	@Override
	public int useCard(String userId, int signinId) {
		long now = new Date().getTime();
		// 判断是否有补签卡
		LUserBQ lUserBQ = lUserBQDao.selectByUserId(userId,1);
		if(lUserBQ == null || lUserBQ.getCardCount() == 0){
			return 2;
		}
		// 判断待补签的记录是否需要补签
		LUserSignin lUserSignin = lUserSigninDao.selectOne(signinId);
		if(lUserSignin.getStatus().intValue() != 1){
			return 3;
		}
		// 是否之前还有待补签的
		List<LUserSignin> dbqs = lUserSigninDao.selectDBQ(userId,lUserSignin.getSignDay());
		if(dbqs.size() > 0){
			return 4;
		}
		// 修改我的补签卡数
		lUserBQ.setCardCount(lUserBQ.getCardCount() - 1);
		lUserBQ.setUpdateTime(now);
		lUserBQDao.update(lUserBQ);
		// 修改待补签的记录为可进行状态
		lUserSignin.setStatus(2);
		lUserSignin.setUpdateTime(now);
		lUserSigninDao.update(lUserSignin);
		return 1;
	}

	@Override
	public Map<String,Object> checkinCount(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("number", 0);
		LUserBQ lUserBQ = lUserBQDao.selectByUserId(userId,2);
		if(!StringUtil.isNullOrEmpty(lUserBQ)){
			data.put("number", lUserBQ.getCardCount());
			return data;
		}
		return data;
	}

}
