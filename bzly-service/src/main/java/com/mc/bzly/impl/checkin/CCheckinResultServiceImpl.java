package com.mc.bzly.impl.checkin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.checkin.CCheckinResultDao;
import com.mc.bzly.model.checkin.CCheckinResult;
import com.mc.bzly.service.checkin.CCheckinResultService;
@Service(interfaceClass=CCheckinResultService.class,version=WebConfig.dubboServiceVersion)
public class CCheckinResultServiceImpl implements CCheckinResultService{
	
	@Autowired
	private CCheckinResultDao cCheckinResultDao;

	@Override
	public Map<String, Object> page(CCheckinResult cCheckinResult) {
		Map<String, Object> result = new HashMap<String, Object>();
		cCheckinResult.setPageIndex(cCheckinResult.getPageSize() * (cCheckinResult.getPageNum() - 1));
		List<CCheckinResult> cCheckinResultList = cCheckinResultDao.selectList(cCheckinResult);
		int count=cCheckinResultDao.selectCount(cCheckinResult);
		result.put("list", cCheckinResultList);
		result.put("total", count);
		return result;
	}

}
