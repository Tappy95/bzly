package com.mc.bzly.impl.passbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.ConstantUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.passbook.MPassbookDao;
import com.mc.bzly.dao.passbook.RSPassbookTaskDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.model.passbook.MPassbook;
import com.mc.bzly.model.passbook.RSPassbookTask;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.service.passbook.MPassbookService;

@Service(interfaceClass=MPassbookService.class,version=WebConfig.dubboServiceVersion)
public class MPassbookServiceImpl implements MPassbookService {

	@Autowired
	private MPassbookDao mPassbookDao;
	
	@Autowired
	private RSPassbookTaskDao rsPassbookTaskDao;
	
	@Autowired
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	
	@Override
	public void add(MPassbook mPassbook,String taskTypes) throws Exception {
		mPassbook.setCreateTime(new Date().getTime());
		mPassbookDao.insert(mPassbook);
		String taskType[] = taskTypes.split(",");
		List<RSPassbookTask> rsPassbookTasks = new ArrayList<>();
		for (String id : taskType) {
			RSPassbookTask rsPassbookTask = new RSPassbookTask();
			rsPassbookTask.setPassbookId(mPassbook.getId());
			rsPassbookTask.setTaskTypeId(new Integer(id));
			rsPassbookTasks.add(rsPassbookTask);
		}
		rsPassbookTaskDao.batchInsert(rsPassbookTasks);
	}

	@Override
	public void modify(MPassbook mPassbook,String taskTypes) throws Exception {
		mPassbookDao.update(mPassbook);
		rsPassbookTaskDao.deleteByPassbook(mPassbook.getId());
		String taskType[] = taskTypes.split(",");
		List<RSPassbookTask> rsPassbookTasks = new ArrayList<>();
		for (String id : taskType) {
			RSPassbookTask rsPassbookTask = new RSPassbookTask();
			rsPassbookTask.setPassbookId(mPassbook.getId());
			rsPassbookTask.setTaskTypeId(new Integer(id));
			rsPassbookTasks.add(rsPassbookTask);
		}
		rsPassbookTaskDao.batchInsert(rsPassbookTasks);
	}

	@Override
	public void remove(Integer id) throws Exception {
		mPassbookDao.delete(id);
		rsPassbookTaskDao.deleteByPassbook(id);
	}

	@Override
	public Map<String, Object> queryOne(Integer id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MPassbook mPassbook = mPassbookDao.selectOne(id);
		List<RSPassbookTask> rsPassbookTasks = rsPassbookTaskDao.selectByPassbook(id);
		resultMap.put("mPassbook", mPassbook);
		resultMap.put("rsPassbookTasks", rsPassbookTasks);
		return resultMap;
	}

	@Override
	public PageInfo<MPassbook> queryList(MPassbook mPassbook) {
		PageHelper.startPage(mPassbook.getPageNum(), mPassbook.getPageSize());
		List<MPassbook> mPassbooks =mPassbookDao.selectList(mPassbook);
		return new PageInfo<>(mPassbooks);
	}

	@Transactional
	@Override
	public void batchSendPassbook(String mobiles, Integer id) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MPassbook mPassbook = mPassbookDao.selectOne(id);
		int userDay = mPassbook.getUseDay();
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, userDay);
		String mobile[] = mobiles.split(",");
		List<RSUserPassbook> rss = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		for (String m : mobile) {
			paramMap.put("mobile", m);
			MUserInfo mUserInfo = mUserInfoDao.selectUserBaseInfo(paramMap);
			RSUserPassbook rs = new RSUserPassbook();
			rs.setPassbookId(mPassbook.getId());
			rs.setStatus(1);
			rs.setUserId(mUserInfo.getUserId());
			rs.setExpirationDay(userDay - 1);
			rs.setExpirationTime(sdf.format(cal.getTime()));
			rss.add(rs);
			if(rss.size() > 50){
				rsUserPassbookDao.batchInsert(rss);
				rss.clear();
			}
		}
		if(rss.size() > 0 ){
			rsUserPassbookDao.batchInsert(rss);
			rss.clear();
		}
	}
	@Override
	public Map<String,Object> taskUsePassbook(String userId,Integer taskType) {
		Map<String,Object> date=new HashMap<>();
		long res=0;
		int fbrspassbookId=0;
		// 查看用户是否有翻倍券
		List<Map<String, Object>> fbpassbooks = rsUserPassbookDao.selectByUser(userId, ConstantUtil.PASSBOOK_TYPE_1);
		for (Map<String, Object> map : fbpassbooks) {
			// 判断加成券是否可用
			Integer passbookId = new Integer(map.get("id").toString());
			RSPassbookTask rs = rsPassbookTaskDao.selectByPassbookTask(passbookId,taskType); 
			if(rs!= null){
				fbrspassbookId = (int) map.get("rsId");
				res = new Integer(map.get("passbook_value").toString());
				break;
			}
		}
		date.put("res", res);
		date.put("fbrspassbookId", fbrspassbookId);
	    return date;
	}
	// 设置翻倍券过期
	@Override
	public void passbookOverdue(Integer passbookId) {
		if(passbookId > 0){
			RSUserPassbook rsUserPassbook = new RSUserPassbook();
			rsUserPassbook.setId(passbookId);
			rsUserPassbook.setStatus(2);
			rsUserPassbookDao.update(rsUserPassbook);
		}
	}
}