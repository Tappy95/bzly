package com.mc.bzly.impl.platform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDataStatisticsDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.ExcelDataModelQD;
import com.mc.bzly.model.platform.HomePageData;
import com.mc.bzly.model.platform.HomePageData1;
import com.mc.bzly.model.platform.HomePageData2;
import com.mc.bzly.model.platform.HomePageData3;
import com.mc.bzly.model.platform.PDataStatistics;
import com.mc.bzly.model.platform.ExcelDataModel;
import com.mc.bzly.service.platform.PDataStatisticsService;

@Service(interfaceClass = PDataStatisticsService.class,version = WebConfig.dubboServiceVersion,retries=0,timeout=120000)
public class PDataStatisticsServiceImpl implements PDataStatisticsService{
    @Autowired
	private PDataStatisticsDao pDataStatisticsDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
	@Override
	public PageInfo<PDataStatistics> queryList(PDataStatistics pDataStatistics) {
		PageHelper.startPage(pDataStatistics.getPageNum(), pDataStatistics.getPageSize());
		List<PDataStatistics> pDataStatisticsList = pDataStatisticsDao.selectList(pDataStatistics);
		return new PageInfo<>(pDataStatisticsList);
	}

	@Override
	public List<PDataStatistics> selectExcelList(PDataStatistics pDataStatistics) {
		return pDataStatisticsDao.selectExcelList(pDataStatistics);
	}

	@Override
	public Map<String, Object> homeTopData() {
		Map<String, Object> resultMap = pDataStatisticsDao.homeTopData();
		long vipCount = pDataStatisticsDao.selectVipCount1();
		if(vipCount > 0){
			vipCount = pDataStatisticsDao.selectVipCount2();
		}
		resultMap.put("vipCount", vipCount);
		return resultMap;
	}

	@Override
	public HomePageData homeTable(Map<String, String> respMap){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();
		//long dayTime = 24*60*60*1000l;
		// 1-昨天
		String type = respMap.get("type");
		if(StringUtils.isNotBlank(type)){
			// 获取昨天的开始时间戳，结束时间戳
			long startTime =DateUtil.getYesterDay();
			long endTime = startTime+86400000;
			int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
			paramMap.put("days", days);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("dateStr",DateUtil.getYesterday2());
			paramMap.put("dateEnd","1");
		}else{
			String startDateStr = respMap.get("startDate");
			String endDateStr = respMap.get("endDate");
			if(StringUtils.isNotBlank(startDateStr) && StringUtils.isNotBlank(endDateStr)){
				try{
					long startTime = sdf1.parse(startDateStr).getTime();
					long endTime = sdf1.parse(endDateStr).getTime();
					int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
					paramMap.put("days", days);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					paramMap.put("dateStr",startDateStr.substring(0,10));
					paramMap.put("dateEnd",DateUtil.getLongToDateDay(endTime, DateUtils.SIMPLE_DATE));
				}catch (Exception e) {
				}
			}
		}
		paramMap.put("channelCode", respMap.get("channelCode"));
		HomePageData homePageData = new HomePageData();
		homePageData.setBindZFB(pDataStatisticsDao.bindZFB(paramMap)); // 绑定支付通道人数
		// 查询当前渠道过来的人
		List<String> userIds = mUserInfoDao.selectChannelUsers(respMap.get("channelCode"));
		
		if(userIds.size()>0) {
			//homePageData.setFirstVip(pDataStatisticsDao.firstVip(paramMap,userIds)); // 首次购买VIP人数
			
			homePageData.setVipCounts(pDataStatisticsDao.vipCounts(paramMap,userIds)); // 购买VIP人数

			Double vipAmount = pDataStatisticsDao.vipAmount(paramMap,userIds);
			homePageData.setVipAmount(vipAmount == null?0:vipAmount); // 购买VIP总金额
			
			Double swAmount = pDataStatisticsDao.swAmount(paramMap,userIds);
			homePageData.setSwAmount(swAmount == null?0:swAmount / 10000 ); // 游戏试玩累计金额

			Double txzAmount = pDataStatisticsDao.txzAmount(paramMap,userIds);
			homePageData.setTxzAmount(txzAmount == null? 0 :txzAmount/11000); // 总提现金额

			//Double lpdjAmount = pDataStatisticsDao.lpdjAmount(paramMap,userIds);
			//homePageData.setLpdjAmount(lpdjAmount == null?0:lpdjAmount); // 礼品等价总金额

			//Double tdzAmount = pDataStatisticsDao.tdzAmount(paramMap,userIds);
			//homePageData.setTdzAmount(tdzAmount == null?0:tdzAmount); // 升级运营总监总金额
			
			//Double hdjlAmount = pDataStatisticsDao.hdjlAmount(paramMap,userIds);
			//homePageData.setHdjlAmount(hdjlAmount == null?0:hdjlAmount / 10000); // 活动奖励
			
			Double hygxAmount = pDataStatisticsDao.hygxAmount(paramMap,userIds);
			homePageData.setHygxAmount(hygxAmount == null?0:hygxAmount / 10000); // 好友贡献

			//Double jzdhAmount = pDataStatisticsDao.jzdhAmount(paramMap,userIds);
			//homePageData.setJzdhAmount(jzdhAmount == null?0:jzdhAmount / 100000); // 兑换金猪总金额
			
			Double jczAmount = pDataStatisticsDao.jczAmount(paramMap,userIds);
			homePageData.setJczAmount(jczAmount == null ?0: jczAmount / 100000); // 总竞猜金额

			Double zjzAmount = pDataStatisticsDao.zjzAmount(paramMap,userIds);
			homePageData.setZjzAmount(zjzAmount == null?0:zjzAmount / 100000); // 中奖总金额

			Double cjzAmount = pDataStatisticsDao.cjzAmount(paramMap,userIds);
			homePageData.setCjzAmount(cjzAmount == null?0:cjzAmount/100000); // 抽奖总金额

			homePageData.setCashUserNum(pDataStatisticsDao.cashUserNum(paramMap,userIds)); // 提现人数
			
			homePageData.setJcrAmount(pDataStatisticsDao.jcrAmount(paramMap,userIds));//竞猜人数
			List<String> dtCountList=new ArrayList<>();
			dtCountList.addAll(pDataStatisticsDao.dtNumber1(paramMap,userIds));
			dtCountList.addAll(pDataStatisticsDao.dtNumber2(paramMap,userIds));
			dtCountList.addAll(pDataStatisticsDao.ctNumber(paramMap,userIds));
			Set<String> dtCount=new HashSet<String>(dtCountList);
			homePageData.setDtCount((long)dtCount.size());
			
			long kszNum = pDataStatisticsDao.kszUserNum(paramMap,userIds); // 快速赚人数
			long qdzNum = pDataStatisticsDao.qdzUserNum(paramMap,userIds); // 签到赚人数
			long lxdlCount = pDataStatisticsDao.lxdlNum(paramMap,userIds); // 连续登陆人数
			homePageData.setKszUserNum(kszNum);
			homePageData.setQdzUserNum(qdzNum);
			homePageData.setLxdlCount(lxdlCount);
		}else {
            homePageData.setFirstVip(0l); // 首次购买VIP人数
			
			homePageData.setVipCounts(0l); // 购买VIP人数

			homePageData.setVipAmount(0.0); // 购买VIP总金额
			
			homePageData.setSwAmount(0.0); // 游戏试玩累计金额

			homePageData.setTxzAmount(0.0); // 总提现金额

			homePageData.setLpdjAmount(0.0); // 礼品等价总金额

			homePageData.setTdzAmount(0.0); // 升级运营总监总金额

			homePageData.setHdjlAmount(0.0); // 活动奖励
			
			homePageData.setHygxAmount(0.0); // 好友贡献

			homePageData.setJzdhAmount(0.0); // 兑换金猪总金额
			
			homePageData.setJczAmount(0.0); // 总竞猜金额

			homePageData.setZjzAmount(0.0); // 中奖总金额

			homePageData.setCjzAmount(0.0); // 抽奖总金额

			homePageData.setCashUserNum(0l); // 提现人数
			
			homePageData.setJcrAmount(0l);//竞猜人数
			
			homePageData.setDtCount(0l);
			
			homePageData.setKszUserNum(0l);
			homePageData.setQdzUserNum(0l);
			homePageData.setLxdlCount(0l);
		}
		
		
		
		homePageData.setLoginCount(pDataStatisticsDao.selectLoginCount(paramMap)); // 登录人数
		
		homePageData.setRegUser(pDataStatisticsDao.selectRegCount(paramMap)); // 新增注册量
		homePageData.setSignCount(pDataStatisticsDao.selectSignCount(paramMap)); // 签到人数
		
		
		return homePageData;
	}
	
	@Override
	public HomePageData1 homeTable1(Map<String, String> respMap){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();
		//long dayTime = 24*60*60*1000l;
		// 1-昨天
		String type = respMap.get("type");
		if(StringUtils.isNotBlank(type)){
			// 获取昨天的开始时间戳，结束时间戳
			long startTime =DateUtil.getYesterDay();
			long endTime = startTime+86400000;
			int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
			paramMap.put("days", days);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("dateStr",DateUtil.getYesterday2());
			paramMap.put("dateEnd","1");
		}else{
			String startDateStr = respMap.get("startDate");
			String endDateStr = respMap.get("endDate");
			if(StringUtils.isNotBlank(startDateStr) && StringUtils.isNotBlank(endDateStr)){
				try{
					long startTime = sdf1.parse(startDateStr).getTime();
					long endTime = sdf1.parse(endDateStr).getTime();
					int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
					paramMap.put("days", days);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					paramMap.put("dateStr",startDateStr.substring(0,10));
					paramMap.put("dateEnd",DateUtil.getLongToDateDay(endTime, DateUtils.SIMPLE_DATE));
				}catch (Exception e) {
				}
			}
		}
		paramMap.put("channelCode", respMap.get("channelCode"));
		HomePageData1 homePageData = new HomePageData1();
		// 查询当前渠道过来的人
		List<String> userIds = mUserInfoDao.selectChannelUsers(respMap.get("channelCode"));
		
		if(userIds.size()>0) {
			/*List<String> dtCountList=new ArrayList<>();
			dtCountList.addAll(pDataStatisticsDao.dtNumber1(paramMap,userIds));
			dtCountList.addAll(pDataStatisticsDao.dtNumber2(paramMap,userIds));
			dtCountList.addAll(pDataStatisticsDao.ctNumber(paramMap,userIds));
			Set<String> dtCount=new HashSet<String>(dtCountList);
			homePageData.setDtCount((long)dtCount.size());*/
			
			/*long kszNum = pDataStatisticsDao.kszUserNum(paramMap,userIds); // 快速赚人数
			long qdzNum = pDataStatisticsDao.qdzUserNum(paramMap,userIds); */// 签到赚人数
			long lxdlCount = pDataStatisticsDao.lxdlNum(paramMap,userIds); // 连续登陆人数
			homePageData.setDtCount(0l);
			homePageData.setKszUserNum(0l);
			homePageData.setQdzUserNum(0l);
			homePageData.setLxdlCount(lxdlCount);
		}else {
			
			homePageData.setDtCount(0l);
			homePageData.setKszUserNum(0l);
			homePageData.setQdzUserNum(0l);
			homePageData.setLxdlCount(0l);
		}
		
		homePageData.setLoginCount(pDataStatisticsDao.selectLoginCount(paramMap)); // 登录人数
		homePageData.setRegUser(pDataStatisticsDao.selectRegCount(paramMap)); // 新增注册量
		homePageData.setSignCount(pDataStatisticsDao.selectSignCount(paramMap)); // 签到人数
		
		
		return homePageData;
	}
	
	@Override
	public HomePageData2 homeTable2(Map<String, String> respMap){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();
		//long dayTime = 24*60*60*1000l;
		// 1-昨天
		String type = respMap.get("type");
		if(StringUtils.isNotBlank(type)){
			// 获取昨天的开始时间戳，结束时间戳
			long startTime =DateUtil.getYesterDay();
			long endTime = startTime+86400000;
			int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
			paramMap.put("days", days);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("dateStr",DateUtil.getYesterday2());
			paramMap.put("dateEnd","1");
		}else{
			String startDateStr = respMap.get("startDate");
			String endDateStr = respMap.get("endDate");
			if(StringUtils.isNotBlank(startDateStr) && StringUtils.isNotBlank(endDateStr)){
				try{
					long startTime = sdf1.parse(startDateStr).getTime();
					long endTime = sdf1.parse(endDateStr).getTime();
					int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
					paramMap.put("days", days);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					paramMap.put("dateStr",startDateStr.substring(0,10));
					paramMap.put("dateEnd",DateUtil.getLongToDateDay(endTime, DateUtils.SIMPLE_DATE));
				}catch (Exception e) {
				}
			}
		}
		paramMap.put("channelCode", respMap.get("channelCode"));
		HomePageData2 homePageData = new HomePageData2();
		homePageData.setBindZFB(pDataStatisticsDao.bindZFB(paramMap)); // 绑定支付通道人数
		// 查询当前渠道过来的人
		List<String> userIds = mUserInfoDao.selectChannelUsers(respMap.get("channelCode"));
		
		if(userIds.size()>0) {
			
			homePageData.setVipCounts(pDataStatisticsDao.vipCounts(paramMap,userIds)); // 购买VIP人数

			Double vipAmount = pDataStatisticsDao.vipAmount(paramMap,userIds);
			homePageData.setVipAmount(vipAmount == null?0:vipAmount); // 购买VIP总金额
			
			Double swAmount = pDataStatisticsDao.swAmount(paramMap,userIds);
			homePageData.setSwAmount(swAmount == null?0:swAmount / 10000 ); // 游戏试玩累计金额

			Double txzAmount = pDataStatisticsDao.txzAmount(paramMap,userIds);
			homePageData.setTxzAmount(txzAmount == null? 0 :txzAmount/11000); // 总提现金额

			Double hygxAmount = pDataStatisticsDao.hygxAmount(paramMap,userIds);
			homePageData.setHygxAmount(hygxAmount == null?0:hygxAmount / 10000); // 好友贡献

			homePageData.setCashUserNum(pDataStatisticsDao.cashUserNum(paramMap,userIds)); // 提现人数
			
		}else {
			
			homePageData.setVipCounts(0l); // 购买VIP人数

			homePageData.setVipAmount(0.0); // 购买VIP总金额
			
			homePageData.setSwAmount(0.0); // 游戏试玩累计金额

			homePageData.setTxzAmount(0.0); // 总提现金额

			homePageData.setCashUserNum(0l); // 提现人数
			
		}
		return homePageData;
	}
	
	@Override
	public HomePageData3 homeTable3(Map<String, String> respMap){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> paramMap = new HashMap<>();
		//long dayTime = 24*60*60*1000l;
		// 1-昨天
		String type = respMap.get("type");
		if(StringUtils.isNotBlank(type)){
			// 获取昨天的开始时间戳，结束时间戳
			long startTime =DateUtil.getYesterDay();
			long endTime = startTime+86400000;
			int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
			paramMap.put("days", days);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("dateStr",DateUtil.getYesterday2());
			paramMap.put("dateEnd","1");
		}else{
			String startDateStr = respMap.get("startDate");
			String endDateStr = respMap.get("endDate");
			if(StringUtils.isNotBlank(startDateStr) && StringUtils.isNotBlank(endDateStr)){
				try{
					long startTime = sdf1.parse(startDateStr).getTime();
					long endTime = sdf1.parse(endDateStr).getTime();
					int days =(int) Math.ceil((endTime - startTime) * 1.0 / (24*60*60*1000));
					paramMap.put("days", days);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					paramMap.put("dateStr",startDateStr.substring(0,10));
					paramMap.put("dateEnd",DateUtil.getLongToDateDay(endTime, DateUtils.SIMPLE_DATE));
				}catch (Exception e) {
				}
			}
		}
		paramMap.put("channelCode", respMap.get("channelCode"));
		HomePageData3 homePageData = new HomePageData3();
		// 查询当前渠道过来的人
		List<String> userIds = mUserInfoDao.selectChannelUsers(respMap.get("channelCode"));
		
		if(userIds.size()>0) {
			Double jczAmount = pDataStatisticsDao.jczAmount(paramMap,userIds);
			homePageData.setJczAmount(jczAmount == null ?0: jczAmount / 100000); // 总竞猜金额

			Double zjzAmount = pDataStatisticsDao.zjzAmount(paramMap,userIds);
			homePageData.setZjzAmount(zjzAmount == null?0:zjzAmount / 100000); // 中奖总金额

			Double cjzAmount = pDataStatisticsDao.cjzAmount(paramMap,userIds);
			homePageData.setCjzAmount(cjzAmount == null?0:cjzAmount/100000); // 抽奖总金额

			homePageData.setJcrAmount(pDataStatisticsDao.jcrAmount(paramMap,userIds));//竞猜人数 
			
		}else {
			
			homePageData.setJczAmount(0.0); // 总竞猜金额

			homePageData.setZjzAmount(0.0); // 中奖总金额

			homePageData.setCjzAmount(0.0); // 抽奖总金额

			homePageData.setJcrAmount(0l);//竞猜人数
			
		}
		return homePageData;
	}

	@Override
	public List<ExcelDataModel> selectGameData(String date) {
		return pDataStatisticsDao.selectGameData(date);
	}

	@Override
	public List<ExcelDataModelQD> selectGameDataQD(String date) {
		return pDataStatisticsDao.selectGameDataQD(date);
	}

}
