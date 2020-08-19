package com.mc.bzly.impl.platform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.bzly.common.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PBannerDao;
import com.mc.bzly.model.platform.PBanner;
import com.mc.bzly.service.platform.PBannerService;

@Service(interfaceClass = PBannerService.class,version = WebConfig.dubboServiceVersion)
public class PBannerServiceImpl implements PBannerService {

	@Autowired 
	private PBannerDao pBannerDao;
	
	@Transactional
	@Override
	public int add(PBanner pBanner) throws Exception {
		pBanner.setCreateTime(new Date().getTime());
		pBannerDao.insert(pBanner);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PBanner pBanner) throws Exception {
		pBannerDao.update(pBanner);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer bannerId) throws Exception {
		String imgUrl = pBannerDao.selectOne(bannerId).getImageUrl();
		pBannerDao.delete(bannerId);
		UploadUtil.deleteFromQN(imgUrl);
		return 1;
	}

	@Override
	public PBanner queryOne(Integer bannerId) {
		return pBannerDao.selectOne(bannerId);
	}

	@Override
	public PageInfo<PBanner> queryList(PBanner pBanner) {
		PageHelper.startPage(pBanner.getPageNum(), pBanner.getPageSize());
		List<PBanner> pBannerList = pBannerDao.selectList(pBanner);
		return new PageInfo<>(pBannerList);
	}

	@Override
	public List<PBanner> queryBanner(Integer position) {
		PBanner pBanner = new PBanner();
		pBanner.setPosition(position);
		pBanner.setStatus(1);
		return pBannerDao.selectList(pBanner);
	}

}
