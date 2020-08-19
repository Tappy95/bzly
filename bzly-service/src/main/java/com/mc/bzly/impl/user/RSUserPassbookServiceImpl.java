package com.mc.bzly.impl.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.InformConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.news.AppNewsInformDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.dao.user.RSUserPassbookDao;
import com.mc.bzly.model.news.AppNewsInform;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.RSUserPassbook;
import com.mc.bzly.service.user.RSUserPassbookService;

@Service(interfaceClass=RSUserPassbookService.class,version=WebConfig.dubboServiceVersion)
public class RSUserPassbookServiceImpl implements RSUserPassbookService {

	private Logger logger = LoggerFactory.getLogger(RSUserPassbookServiceImpl.class);
	
	@Autowired
	private RSUserPassbookDao rsUserPassbookDao;
	
	@Autowired 
	private MUserInfoDao mUserInfoDao;
	
	@Autowired
	private AppNewsInformDao appNewsInformDao;
	
	@Override
	public void checkOverdue() {
		long hours = 60*60*1000*7;
		long now = new Date().getTime();
		logger.info("RSUserPassbookServiceImpl.checkOverdue...start");
		// 查看未使用的卡券
		RSUserPassbook param = new RSUserPassbook();
		param.setStatus(1);
		List<RSUserPassbook> rsUserPassbooks = rsUserPassbookDao.selectListByExample(param);
		List<RSUserPassbook> updates = new ArrayList<RSUserPassbook>();
		
		List<AppNewsInform> AppNewsInforms = new ArrayList<AppNewsInform>();
		for (RSUserPassbook rsUserPassbook : rsUserPassbooks) {
			MUserInfo mUserInfo = mUserInfoDao.selectOne(rsUserPassbook.getUserId());
			try{
				if(rsUserPassbook.getExpirationDay().intValue() == 0){
					// 设置状态未已过期
					rsUserPassbook.setStatus(3);
					// 通知已过期
					AppNewsInform appNewsInform = new AppNewsInform();
					appNewsInform.setUserId(rsUserPassbook.getUserId());
					appNewsInform.setMobile(mUserInfo.getMobile());
					appNewsInform.setPushTime(now+hours);
					appNewsInform.setInformTitle(InformConstant.CARD_TITLE_OVERDUE);
					appNewsInform.setInformContent(InformConstant.CARD_CONTENT_OVERDUE);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInform.setCreaterTime(now);
					appNewsInform.setPushObject(1);
					appNewsInform.setIsRelease(1);
					appNewsInform.setInformType(1);
					appNewsInform.setIsPush(1);
					appNewsInform.setAddMode(2);
					appNewsInform.setInformUrl("1");
					AppNewsInforms.add(appNewsInform);
					
				}else if(rsUserPassbook.getExpirationDay().intValue() < 4 ){ // 今天小于4，说明剩余3天，开始提醒用户
					rsUserPassbook.setExpirationDay(rsUserPassbook.getExpirationDay().intValue() - 1 );
					// 通知即将过期
					AppNewsInform appNewsInform = new AppNewsInform();
					appNewsInform.setUserId(rsUserPassbook.getUserId());
					appNewsInform.setMobile(mUserInfo.getMobile());
					appNewsInform.setPushTime(now+hours);
					appNewsInform.setCreaterTime(now);
					appNewsInform.setInformTitle(InformConstant.CARD_TITLE_OVERDUE);
					appNewsInform.setInformContent(InformConstant.CARD_CONTENT_SOON_OVERDUE);
					appNewsInform.setPushMode(InformConstant.PUSH_MODE_AURORA);
					appNewsInform.setPushObject(1);
					appNewsInform.setIsRelease(1);
					appNewsInform.setInformType(1);
					appNewsInform.setIsPush(1);
					appNewsInform.setAddMode(2);
					appNewsInform.setInformUrl("1");
					AppNewsInforms.add(appNewsInform);
					
				}else{ // 只做剩余天数修改
					rsUserPassbook.setExpirationDay(rsUserPassbook.getExpirationDay().intValue() - 1 );
				}
				updates.add(rsUserPassbook);
				// 批量修改
				if(updates.size() > 50 ){
					rsUserPassbookDao.batchUpdate(updates);
					updates.clear();
				}
				// 插入消息
				if(AppNewsInforms.size() > 50){
					appNewsInformDao.saveInformList(AppNewsInforms);
					AppNewsInforms.clear();
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("RSUserPassbookServiceImpl.checkOverdue异常，id：{}",rsUserPassbook.getId());
				continue;
			}
		}
		// 批量修改
		if(updates.size() > 0){
			rsUserPassbookDao.batchUpdate(updates);
			updates.clear();
		}
		// 插入消息
		if(AppNewsInforms.size() > 0){
			appNewsInformDao.saveInformList(AppNewsInforms);
			AppNewsInforms.clear();
		}
		logger.info("RSUserPassbookServiceImpl.checkOverdue...end");
		
	}

	@Override
	public PageInfo<RSUserPassbook> selectAppList(RSUserPassbook rsUserPassbook) {
		PageHelper.startPage(rsUserPassbook.getPageNum(), rsUserPassbook.getPageSize());
		List<RSUserPassbook> rsUserPassbooks =rsUserPassbookDao.selectAppList(rsUserPassbook);
		return new PageInfo<>(rsUserPassbooks);
	}

	@Override
	public List<RSUserPassbook> selectDiscount(String userId) {
		return rsUserPassbookDao.selectDiscount(userId);
	}

	@Override
	public RSUserPassbook selectAddDiscount(String userId) {
		return rsUserPassbookDao.selectAddDiscount(userId);
	}
	
}
