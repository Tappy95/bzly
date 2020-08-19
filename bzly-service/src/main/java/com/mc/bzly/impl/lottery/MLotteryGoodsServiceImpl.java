package com.mc.bzly.impl.lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.lottery.MLotteryGoodsDao;
import com.mc.bzly.model.lottery.MLotteryGoods;
import com.mc.bzly.service.lottery.MLotteryGoodsService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MLotteryGoodsService.class,version = WebConfig.dubboServiceVersion)
public class MLotteryGoodsServiceImpl implements MLotteryGoodsService {

	@Autowired 
	private MLotteryGoodsDao mLotteryGoodsDao;
	
	@Transactional
	@Override
	public int add(MLotteryGoods mLotteryGoods) throws Exception {
		mLotteryGoods.setCreateTime(new Date().getTime());
		mLotteryGoodsDao.insert(mLotteryGoods);
		return 1;
	}

	@Transactional
	@Override
	public int modify(MLotteryGoods mLotteryGoods) throws Exception {
		MLotteryGoods good=mLotteryGoodsDao.selectOne(mLotteryGoods.getId());
		if(StringUtil.isNullOrEmpty(good.getPigCoin())) {
			if(good.getPrice().compareTo(mLotteryGoods.getPrice())==0 && mLotteryGoods.getImageUrl().equals(good.getImageUrl())) {
				mLotteryGoodsDao.update(mLotteryGoods);	
				return 1;
			}
		}else {
			if(good.getPrice().compareTo(mLotteryGoods.getPrice())==0 && mLotteryGoods.getImageUrl().equals(good.getImageUrl()) && mLotteryGoods.getPigCoin().longValue()==good.getPigCoin().longValue()) {
				mLotteryGoodsDao.update(mLotteryGoods);	
				return 1;
			}	
		}
		mLotteryGoods.setStatus(1);
		mLotteryGoods.setCreateTime(new Date().getTime());
		mLotteryGoodsDao.insert(mLotteryGoods);
		good.setStatus(3);
		mLotteryGoodsDao.update(good);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		String imgUrl = mLotteryGoodsDao.selectOne(id).getImageUrl();
		mLotteryGoodsDao.delete(id);
		UploadUtil.deleteFromQN(imgUrl);
		return 1;
	}

	@Override
	public MLotteryGoods queryOne(Integer id) {
		return mLotteryGoodsDao.selectOne(id);
	}

	@Override
	public PageInfo<MLotteryGoods> queryList(MLotteryGoods mLotteryGoods) {
		PageHelper.startPage(mLotteryGoods.getPageNum(), mLotteryGoods.getPageSize());
		List<MLotteryGoods> mLotteryGoodsList = mLotteryGoodsDao.selectList(mLotteryGoods);
		return new PageInfo<>(mLotteryGoodsList);
	}

	@Override
	public List<MLotteryGoods> queryByType(Integer typeId) {
		MLotteryGoods mLotteryGoods = new MLotteryGoods();
		mLotteryGoods.setTypeId(typeId);
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
		return resultList;
	}

	@Override
	public PageInfo<MLotteryGoods> queryExchangeList(MLotteryGoods mLotteryGoods) {
		PageHelper.startPage(mLotteryGoods.getPageNum(), mLotteryGoods.getPageSize());
		List<MLotteryGoods> mLotteryGoodsList = mLotteryGoodsDao.selectExchangeList(mLotteryGoods);
		return new PageInfo<>(mLotteryGoodsList);
	}

}
