package com.mc.bzly.impl.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.user.LRankDarenWeekDao;
import com.mc.bzly.model.user.LRankDarenWeek;
import com.mc.bzly.service.user.LRankDarenWeekService;

@Service(interfaceClass = LRankDarenWeekService.class,version = WebConfig.dubboServiceVersion)
public class LRankDarenWeekServiceImpl implements LRankDarenWeekService {

	@Autowired
	private LRankDarenWeekDao lRankDarenWeekDao;
	
	@Override
	public Map<String, Object> queryFlist() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String weekStart = DateUtil.getWeekStart();
			long startTime =sdf.parse(weekStart).getTime();
			List<LRankDarenWeek> lists = lRankDarenWeekDao.selectNowlist(startTime);
			int count = 0;
			for (int i = 0;i<lists.size();i++) {
				if(StringUtils.isBlank(lists.get(i).getAliasName())){
					lists.get(i).setAliasName("小猪猪");
				}
				lists.get(i).setRank(i + 1);
				count = count + lists.get(i).getApprenticeCount();
			}
			resultMap.put("list", lists);
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(weekStart));
			c.add(Calendar.DAY_OF_MONTH, 6);
			String endDate = sdf.format(c.getTime());
			String startDate = weekStart.substring(5,10).replace("-", ".");
			endDate = endDate.substring(5,10).replace("-", ".");
			resultMap.put("date", startDate +"~"+endDate);
			resultMap.put("count", count);
			// 往期情况
			String history = lRankDarenWeekDao.selectLaster5();
			if(StringUtils.isNotBlank(history)){
				resultMap.put("history", history);
			}else{
				resultMap.put("history",null);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> page(LRankDarenWeek lRankDarenWeek) {
		Map<String, Object> result = new HashMap<String, Object>();
		lRankDarenWeek.setPageIndex(lRankDarenWeek.getPageSize() * (lRankDarenWeek.getPageNum() - 1));
		List<LRankDarenWeek> lRankDarenWeekList = lRankDarenWeekDao.selectList(lRankDarenWeek);
		int count= lRankDarenWeekDao.selectCount(lRankDarenWeek);
		result.put("list", lRankDarenWeekList);
		result.put("total", count);
        return result;
	}

	@Override
	public List<Map<String, Object>> selectCycle() {
		List<Map<String, Object>> list=lRankDarenWeekDao.selectCycle();
		for(int i=0;i<list.size();i++) {
			list.get(i).put("id", i);
		}
		return list;
	}

	@Override
	public List<LRankDarenWeek> queryByCycel(String rankCycle) {
		return lRankDarenWeekDao.selectByCycel(rankCycle);
	}

}
