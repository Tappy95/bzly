package com.mc.bzly.impl.platform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PCashPriceConfigDao;
import com.mc.bzly.dao.user.LUserExchangeCashDao;
import com.mc.bzly.dao.user.MUserInfoDao;
import com.mc.bzly.model.platform.PCashPriceConfig;
import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.MUserInfo;
import com.mc.bzly.service.platform.PCashPriceConfigService;

@Service(interfaceClass = PCashPriceConfigService.class,version = WebConfig.dubboServiceVersion)
public class PCashPriceConfigServiceImpl implements PCashPriceConfigService{
    
	@Autowired
	private PCashPriceConfigDao pCashPriceConfigDao;
	@Autowired
	private MUserInfoDao mUserInfoDao;
	@Autowired
	private LUserExchangeCashDao lUserExchangeCashDao;
	
	@Override
	public int save(PCashPriceConfig pCashPriceConfig) {
		return pCashPriceConfigDao.save(pCashPriceConfig);
	}

	@Override
	public PageInfo<PCashPriceConfig> selectList(PCashPriceConfig pCashPriceConfig) {
		PageHelper.startPage(pCashPriceConfig.getPageNum(), pCashPriceConfig.getPageSize());
		List<PCashPriceConfig> pCashPriceConfigList =pCashPriceConfigDao.selectList(pCashPriceConfig);
		return new PageInfo<>(pCashPriceConfigList);
	}

	@Override
	public PCashPriceConfig selectOne(Integer id) {
		return pCashPriceConfigDao.selectOne(id);
	}

	@Override
	public int update(PCashPriceConfig pCashPriceConfig) {
		return pCashPriceConfigDao.update(pCashPriceConfig);
	}

	@Override
	public int delete(Integer id) {
		return pCashPriceConfigDao.delete(id);
	}

	@Override
	public List<PCashPriceConfig> appList(PCashPriceConfig pCashPriceConfig) {
		pCashPriceConfig.setWebType(1);
		return pCashPriceConfigDao.selectList(pCashPriceConfig);
	}

	@Override
	public List<PCashPriceConfig> appWishList(PCashPriceConfig pCashPriceConfig,String userId) {
		MUserInfo userInfo=mUserInfoDao.selectOne(userId);
		int count=lUserExchangeCashDao.selectActualAmount(0.3,userId);//o.3元提现次数
		List<PCashPriceConfig> list=new ArrayList<PCashPriceConfig>();
		
		if(userInfo.getCreateTime()>1583834400000l && count==0) {//注册时间大于功能上线时间且未提现过0.3元
			PCashPriceConfig cashConfig=new PCashPriceConfig();
			cashConfig.setPriceDouble(0.3);
			cashConfig.setIsTask(2);
			cashConfig.setOrders(0);
			list.add(cashConfig);
		}
		pCashPriceConfig.setWebType(3);
		list.addAll(pCashPriceConfigDao.selectList(pCashPriceConfig));
		return list;
	}

}
