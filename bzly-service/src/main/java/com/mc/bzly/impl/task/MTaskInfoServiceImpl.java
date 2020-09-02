package com.mc.bzly.impl.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.task.MTaskInfoDao;
import com.mc.bzly.dao.task.MTaskTypeDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.model.task.MTaskInfo;
import com.mc.bzly.model.task.MTaskType;
import com.mc.bzly.service.task.MTaskInfoService;
import com.bzly.common.utils.StringUtil;

@Service(interfaceClass = MTaskInfoService.class,version = WebConfig.dubboServiceVersion)
public class MTaskInfoServiceImpl implements MTaskInfoService {

	@Autowired
	private MTaskInfoDao mTaskInfoDao;
	@Autowired
	private MTaskTypeDao mTaskTypeDao;
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	
	@Override
	public void add(MTaskInfo mTaskInfo) throws Exception {
		mTaskInfo.setCreateTime(new Date().getTime());
		mTaskInfoDao.insert(mTaskInfo);
	}

	@Override
	public void modify(MTaskInfo mTaskInfo) throws Exception {
		mTaskInfoDao.update(mTaskInfo);
	}

	@Override
	public void remove(Integer id) throws Exception {
		MTaskInfo mTaskInfo=mTaskInfoDao.selectOne(id);
		String imgUrl = mTaskInfo.getTaskImg();
		String imgUrl2=mTaskInfo.getFulfilTaskImg();
		mTaskInfoDao.delete(id);
		UploadUtil.deleteFromQN(imgUrl);	
		UploadUtil.deleteFromQN(imgUrl2);	
		
	}

	@Override
	public MTaskInfo queryOne(Integer id) {
		return mTaskInfoDao.selectOne(id);
	}

	@Override
	public PageInfo<MTaskInfo> queryList(MTaskInfo mTaskInfo) {
		PageHelper.startPage(mTaskInfo.getPageNum(), mTaskInfo.getPageSize());
		List<MTaskInfo> mTaskInfos =mTaskInfoDao.selectList(mTaskInfo);
		return new PageInfo<>(mTaskInfos);
	}

	@Override
	public List<MTaskType> selectUserTask(String userId) {
		List<MTaskType> mTaskTypes=mTaskTypeDao.selectList();
		for(MTaskType mTaskType:mTaskTypes) {
			mTaskType.setTasks(mTaskInfoDao.selectUserTask(userId,mTaskType.getId()));
		}
		return mTaskTypes;
	}

	@Override
	public List<Map<String, Object>> selectNoviceTask(String userId) {
		return mTaskInfoDao.selectUserTask(userId,6);
	}

	@Override
	public List<Map<String, Object>> selectAppUserTask(String userId) {
		return mTaskInfoDao.selectAppUserTask(userId);
	}

	@Override
	public Map<String, Object> selectNoviceTaskNew(String userId) {
		Map<String,Object> data=new HashMap<String, Object>();
		List<Map<String, Object>> taskList=mTaskInfoDao.selectUserTask(userId,6);
		data.put("passbook", rsUserPassbookDao.selectAddDiscountCount(userId));
		data.put("task", taskList);
		return data;
	}

}
