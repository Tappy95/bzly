package com.mc.bzly.impl.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.dao.user.LPigChangeDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.user.LPigChange;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.model.user.MessageList;
import com.mc.bzly.service.user.LPigChangeService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = LPigChangeService.class,version = WebConfig.dubboServiceVersion)
public class LPigChangeServiceImpl implements LPigChangeService{
	
    @Autowired
	private LPigChangeDao lPigChangeDao;
    @Autowired
    private MUserInfoDao mUserInfoDao;
    @Autowired
    private PDictionaryDao pDictionaryDao;
	
	@Override
	public PageInfo<LPigChange> queryList(LPigChange lPigChange) {
		PageHelper.startPage(lPigChange.getPageNum(), lPigChange.getPageSize());
		List<LPigChange> LPigChangeList = lPigChangeDao.selectList(lPigChange);
		return new PageInfo<>(LPigChangeList);
	}

	@Override
	public Map<String, Object> pageList(LPigChange lPigChange) {
		/*if("baozhu".equals(lPigChange.getChannelCode())) {
			PageHelper.startPage(lPigChange.getPageNum(), lPigChange.getPageSize());
			List<LPigChange> LPigChangeList = lPigChangeDao.selectPageList(lPigChange);
			return new PageInfo<>(LPigChangeList);
		}else {
			PageHelper.startPage(lPigChange.getPageNum(), lPigChange.getPageSize());
			List<LPigChange> LPigChangeList = lPigChangeDao.selectChannelList(lPigChange);
			return new PageInfo<>(LPigChangeList);
		}*/
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("res", "1");
		if(!StringUtil.isNullOrEmpty(lPigChange.getAccountId()) || !StringUtil.isNullOrEmpty(lPigChange.getMobile())) {
			MUserInfo mUserInfo=new MUserInfo();
			mUserInfo.setAccountId(lPigChange.getAccountId());
			mUserInfo.setMobile(lPigChange.getMobile());
			MUserInfo user=mUserInfoDao.selectBackstage(mUserInfo);
			if(StringUtil.isNullOrEmpty(user)) {
				result.put("res", "2");
				return result;
			}else {
				lPigChange.setUserId(user.getUserId());
			}
		}
		lPigChange.setPageIndex(lPigChange.getPageSize() * (lPigChange.getPageNum() - 1));
		List<LPigChange> lPigChangeList = lPigChangeDao.selectPageListNew(lPigChange);
		List<Map<String,Object>> subTotalMap=lPigChangeDao.selectSumNew(lPigChange);
		List<Map<String,Object>> countMap = lPigChangeDao.selectCountNew(lPigChange);
		
		result.put("list", lPigChangeList);
		result.put("subRevenuePrice", 0);
		result.put("subExpendPrice", 0);
		int pageCount=0;
		for(int i=0;i<subTotalMap.size();i++) {
			if("1".equals(subTotalMap.get(i).get("flowType").toString())) {
				result.put("subRevenuePrice", subTotalMap.get(i).get("amount"));
				pageCount+=Integer.parseInt(subTotalMap.get(i).get("cou").toString());
			}else {
				result.put("subExpendPrice", subTotalMap.get(i).get("amount"));
				pageCount+=Integer.parseInt(subTotalMap.get(i).get("cou").toString());
			}
			
		}
		result.put("totalRevenuePrice", 0);
		result.put("totalExpendPrice", 0);
		int total=0;
		for(int n=0;n<countMap.size();n++) {
			if("1".equals(countMap.get(n).get("flowType").toString())) {
				result.put("totalRevenuePrice", countMap.get(n).get("amount"));
				total+=Integer.parseInt(countMap.get(n).get("cou").toString());
			}else {
				result.put("totalExpendPrice", countMap.get(n).get("amount"));
				total+=Integer.parseInt(countMap.get(n).get("cou").toString());
			}
			
		}
		result.put("pageCount", pageCount);
		result.put("total", total);
		return result;
	}

	@Override
	public LPigChange info(Integer id) {
		return lPigChangeDao.selectOne(id);
	}

	@Override
	public List<MessageList> queryMessageList() {
		return lPigChangeDao.selectMessageList();
	}

	@Override
	public List<Map<String, Object>> exclPigChange(LPigChange lPigChange) {
		if(!StringUtil.isNullOrEmpty(lPigChange.getAccountId()) || !StringUtil.isNullOrEmpty(lPigChange.getMobile())) {
			MUserInfo mUserInfo=new MUserInfo();
			mUserInfo.setAccountId(lPigChange.getAccountId());
			mUserInfo.setMobile(lPigChange.getMobile());
			MUserInfo user=mUserInfoDao.selectBackstage(mUserInfo);
			if(StringUtil.isNullOrEmpty(user)) {
				return null;
			}else {
				lPigChange.setUserId(user.getUserId());
			}
		}
		return lPigChangeDao.exclPigChangeNew(lPigChange);
	}

	@Transactional
	@Override
	public Result addPigCoin(String userId) throws ParseException {
		Result result = new Result();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		long now = new Date().getTime();
		// 查询每日可领取次数
		int jjCount = new Integer(pDictionaryDao.selectByName("jj_count").getDicValue());
		// 查询每次可领取金猪数
		long preAmount = new Long(pDictionaryDao.selectByName("pre_jj_amount").getDicValue());
		// 查询今日已领取次数
		long startTime = sdf1.parse(sdf2.format(new Date()) +" 00:00:00").getTime();
		Long countL = lPigChangeDao.selectCountJJ(ConstantUtil.PIG_CHANGED_TYPE_12, userId, startTime);
		long count = countL == null?0:countL.longValue();
		 // 判断是否 超过次数
		if(count >= jjCount){
			result.setMessage(RespCodeState.JJ_COUNT_NOT_ENOUGH.getMessage());
			result.setStatusCode(RespCodeState.JJ_COUNT_NOT_ENOUGH.getStatusCode());
			return result;
		}
		MUserInfo mUserInfo = mUserInfoDao.selectOne(userId);
		long totalPig = mUserInfo.getCoin() * 10 + mUserInfo.getPigCoin().longValue();
		 // 判断是否余额不够
		if(totalPig >= 10000){
			result.setMessage(RespCodeState.ACCOUNT_ENOUGH.getMessage());
			result.setStatusCode(RespCodeState.ACCOUNT_ENOUGH.getStatusCode());
			return result;
		}
		
		mUserInfo.setPigCoin(mUserInfo.getPigCoin() + preAmount);
		// 添加金猪记录
		LPigChange lPigChange = new LPigChange(userId, preAmount, ConstantUtil.FLOWTYPE_IN, ConstantUtil.PIG_CHANGED_TYPE_12, now, "救济金猪", mUserInfo.getPigCoin());
		lPigChangeDao.insert(lPigChange);
		// 修改金猪数
		mUserInfoDao.update(mUserInfo);
		
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		return result;
	}

}
