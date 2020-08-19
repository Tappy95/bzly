package com.mc.bzly.impl.thirdparty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TaskCallbackDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.thirdparty.TaskCallback;
import com.mc.bzly.service.thirdparty.TaskCallbackService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = TaskCallbackService.class,version = WebConfig.dubboServiceVersion)
public class TaskCallbackServiceImpl implements TaskCallbackService{
	
	@Autowired
    private TaskCallbackDao taskCallbackDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Override
	public Map<String, Object> selectList(TaskCallback taskCallback) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(taskCallback.getUserId())) {
			if(StringUtil.isNullOrEmpty(mUserInfoDao.selectByAccountId(Integer.parseInt(taskCallback.getUserId())))) {
				result.put("res", "2");
				return result;
			}
		}
		taskCallback.setPageIndex(taskCallback.getPageSize() * (taskCallback.getPageNum() - 1));
		List<TaskCallback> taskCallbacks=taskCallbackDao.selectList(taskCallback);
		int count=taskCallbackDao.selectCount(taskCallback);
		result.put("list", taskCallbacks);
		result.put("total", count);
		return result;
	}

}
