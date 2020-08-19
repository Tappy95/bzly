package com.mc.bzly.impl.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.constant.DictionaryUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.lottery.MLotteryGoodsDao;
import com.mc.bzly.dao.lottery.MLotteryTypeDao;
import com.mc.bzly.dao.platform.PDictionaryDao;
import com.mc.bzly.model.lottery.MLotteryGoods;
import com.mc.bzly.model.lottery.MLotteryType;
import com.mc.bzly.service.lottery.MLotteryTypeService;

@Service(interfaceClass=MLotteryTypeService.class,version=WebConfig.dubboServiceVersion)
public class MLotteryTypeServiceImpl implements MLotteryTypeService {

	@Autowired
    private MLotteryTypeDao mLotteryTypeDao;
	@Autowired
	private MLotteryGoodsDao mLotteryGoodsDao;
	@Autowired 
	private PDictionaryDao pDictionaryDao;
	
	@Override
	public Integer insert(MLotteryType mLotteryType) {
		mLotteryType.setCreateTime(new Date().getTime());
		return mLotteryTypeDao.insert(mLotteryType);
	}

	@Override
	public MLotteryType selectOne(Integer id) {
		return mLotteryTypeDao.selectOne(id);
	}

	@Override
	public PageInfo<MLotteryType> selectPageList(MLotteryType mLotteryType) {
		PageHelper.startPage(mLotteryType.getPageNum(), mLotteryType.getPageSize());
		List<MLotteryType> mLotteryTypeList =mLotteryTypeDao.selectPageList(mLotteryType);
		return new PageInfo<>(mLotteryTypeList);
	}

	@Override
	public List<MLotteryType> selectList() {
		return mLotteryTypeDao.selectList();
	}

	@Override
	public Integer update(MLotteryType mLotteryType) {
		return mLotteryTypeDao.update(mLotteryType);
	}

	@Override
	public Integer delete(Integer id) {
		return mLotteryTypeDao.delete(id);
	}

	/**
	 * 抽奖首页
	 * 1.类型信息
	 * 2.奖品列表
	 * 3.用户今日抽奖次数
	 * @return
	 */
	@Override
	public Map<String, Object> home() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 类型信息
		MLotteryType mLotteryType = mLotteryTypeDao.selectEnableType();
		// 奖品列表
		MLotteryGoods mLotteryGoods = new MLotteryGoods();
		mLotteryGoods.setTypeId(mLotteryType.getId());
		mLotteryGoods.setStatus(1);
		List<MLotteryGoods> list = mLotteryGoodsDao.selectList(mLotteryGoods);
		List<MLotteryGoods> resultList = new ArrayList<>();
		int count = 12;
		if(list.size() < 12 ){
			count = list.size();
		}
		for (int i = 0; i < count; i++) {
			list.get(i).setRate(null);
			resultList.add(list.get(i));
		}
		// 用户今日抽奖次数
		resultMap.put("mLotteryType", mLotteryType);
		resultMap.put("resultList", resultList);
		return resultMap;
	}

	@Override
	public Map<String, Object> selectTypeOne() {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("qqGroup", pDictionaryDao.selectByName(DictionaryUtil.BZ_QQ_GROUP).getDicValue());
		data.put("serviceQq", pDictionaryDao.selectByName(DictionaryUtil.BZ_SERVICE_GROUP).getDicValue());
		data.put("mLotteryType", mLotteryTypeDao.selectTypeOne());
		return data;
	}

}
