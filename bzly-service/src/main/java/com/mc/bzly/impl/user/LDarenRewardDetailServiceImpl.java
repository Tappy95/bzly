package com.mc.bzly.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LDarenRewardDetailDao;
import com.mc.bzly.model.user.LDarenRewardDetail;
import com.mc.bzly.service.user.LDarenRewardDetailService;

@Service(interfaceClass = LDarenRewardDetailService.class,version = WebConfig.dubboServiceVersion)
public class LDarenRewardDetailServiceImpl implements LDarenRewardDetailService {

	@Autowired
	private LDarenRewardDetailDao lDarenRewardDetailDao;
	@Override
	public Map<String, Object> list(LDarenRewardDetail lDarenRewardDetail) {
		Map<String, Object> result = new HashMap<String, Object>();
		lDarenRewardDetail.setPageIndex(lDarenRewardDetail.getPageSize() * (lDarenRewardDetail.getPageNum() - 1));
		List<LDarenRewardDetail> lDarenRewardDetailList = lDarenRewardDetailDao.selectList(lDarenRewardDetail);
		int total=lDarenRewardDetailDao.selectCount(lDarenRewardDetail);
		long rewardSum=lDarenRewardDetailDao.selectSum(lDarenRewardDetail);
		result.put("list", lDarenRewardDetailList);
		result.put("total", total);
		result.put("rewardSum", rewardSum);
		return result;
	}
	
	@Override
	public Map<String, Object> listF(String userId, String accountId,Integer pageSize,Integer pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(pageNum == null){
			pageNum = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		int pageIndex = (pageNum - 1) * pageSize;
		List<Map<String, Object>> list = lDarenRewardDetailDao.selectListF(userId,accountId, pageIndex, pageSize);
		long total = lDarenRewardDetailDao.selectListCountF(userId,accountId);
		result.put("list", list);
		result.put("total", total);
		return result;
	}

}
