package com.mc.bzly.impl.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LUserTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.jms.JmsWrapper.Type;
import com.mc.bzly.model.user.LUserCashLog;
import com.mc.bzly.model.user.LUserTask;
import com.mc.bzly.service.jms.JMSProducer;
import com.mc.bzly.service.user.LUserTaskService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LUserTaskService.class,version = WebConfig.dubboServiceVersion)
public class LUserTaskServiceImpl implements LUserTaskService{
	
	@Autowired
	private LUserTaskDao lUserTaskDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private JMSProducer jmsProducer;
	
	@Override
	public Integer userReceiveTask(String userId, Integer id) {
		LUserTask userTask=lUserTaskDao.selectOne(userId, id);
		if(StringUtil.isNullOrEmpty(userTask)) {
			return 1;//任务未完成
		}
		if(userTask.getIsReceive()==2) {
			return 2;//已领取过奖励
		}
		Map<String,Object> jmsData=new HashMap<>();
		jmsData.put("id", id);
		jmsData.put("userId", userId);
		jmsProducer.userReceiveTask(Type.USER_RECEIVE_TASK, jmsData);
		return 3;//领取成功
	}

	@Override
	public Integer welfareTask(String userId) {
    	LUserTask lUserTask=lUserTaskDao.selectOne(userId, 12);
	    if(StringUtil.isNullOrEmpty(lUserTask)) {
	    	LUserTask userTask=new LUserTask();
			userTask.setUserId(userId);
			userTask.setTaskId(12);
			userTask.setCreateTime(new Date().getTime());
			userTask.setIsReceive(1);
			lUserTaskDao.insert(userTask);
			return 1;
	    }else {
	    	return 2;
	    }
	}

	@Override
	public Map<String, Object> list(LUserTask lUserTask) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lUserTask.getAccountId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(lUserTask.getAccountId()))) {
				result.put("res", "2");
				return result;
			}
		}
		/*lUserTask.setPageIndex(lUserTask.getPageSize() * (lUserTask.getPageNum() - 1));*/
		List<Map<String,Object>> lUserTaskList = lUserTaskDao.selectList(lUserTask);
		/*int total=lUserTaskDao.selectCount(lUserTask);*/
		result.put("list", lUserTaskList);
		/*result.put("total", total);*/
		return result;
	}

}
