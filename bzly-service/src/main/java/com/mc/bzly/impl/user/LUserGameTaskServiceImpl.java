package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserCashDao;
import com.mc.bzly.dao.user.LUserGameTaskDao;
import com.mc.bzly.model.user.LUserGameTask;
import com.mc.bzly.service.user.LUserGameTaskService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserGameTaskService.class,version = WebConfig.dubboServiceVersion)
public class LUserGameTaskServiceImpl implements LUserGameTaskService{
	
	@Autowired
	private LUserGameTaskDao lUserGameTaskDao;
	@Autowired
	private LUserCashDao lUserCashDao;

	@Override
	public int add(String userId, int vipId, int gameId) {
		LUserGameTask userGame=lUserGameTaskDao.selectOne(userId, vipId,1);
		if(StringUtil.isNullOrEmpty(userGame)) {
			userGame=new LUserGameTask();
			userGame.setUserId(userId);
			userGame.setVipId(vipId);
			userGame.setGameId(gameId);
			userGame.setCreateTime(new Date().getTime());
			userGame.setState(1);
			userGame.setTaskType(1);
			lUserGameTaskDao.insert(userGame);
		}else {
			userGame.setGameId(gameId);
			userGame.setCreateTime(new Date().getTime());
			userGame.setState(1);
			userGame.setTaskType(1);
			lUserGameTaskDao.update(userGame);
		}
		return 1;
	}

	@Override
	public Map<String, Object> selectlist(String createTime,Integer accountId) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Map<String, Object>> data=lUserGameTaskDao.selectlist(createTime,accountId);
		map.put("list", data);
		return map;
	}

	@Override
	public int updateState(Integer id, Integer state) {
		LUserGameTask lUserGameTask=lUserGameTaskDao.selectId(id);
		int i=lUserGameTaskDao.updateState(id, state);
		if(i>0 && lUserGameTask.getTaskType().intValue()==2) {
			int count=lUserGameTaskDao.selectCashCount(lUserGameTask.getCashId());
			if(count>=3) {
				lUserCashDao.updateIdState(lUserGameTask.getCashId());
			}
		}
		return lUserGameTaskDao.updateState(id, state);
	}

	@Override
	public int updateHide(LUserGameTask lUserGameTask) {
		LUserGameTask gameTask=lUserGameTaskDao.selectInfo(lUserGameTask.getUserId(),lUserGameTask.getGameId());
		if(gameTask.getState()==1) {
			return 2;
		}else {
			lUserGameTask.setIsHide(2);
			lUserGameTaskDao.updateHide(lUserGameTask);
		}
		return 1;
	}

}
