package com.mc.bzly.impl.platform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PMenuBtnDao;
import com.mc.bzly.model.platform.PMenuBtn;
import com.mc.bzly.service.platform.PMenuBtnService;

@Service(interfaceClass = PMenuBtnService.class,version = WebConfig.dubboServiceVersion)
public class PMenuBtnServiceImpl implements PMenuBtnService {
	
	@Autowired 
	private PMenuBtnDao pMenuDao;
	
	@Transactional
	@Override
	public int add(PMenuBtn pMenu) throws Exception {
		pMenuDao.insert(pMenu);
		return 1;
	}


	@Override
	public int remove(Integer id) throws Exception {
		pMenuDao.delete(id);
		return 1;
	}

	@Override
	public PageInfo<PMenuBtn> queryList(PMenuBtn pMenu) {
		PageHelper.startPage(pMenu.getPageNum(), pMenu.getPageSize());
		List<PMenuBtn> pMenuList = pMenuDao.selectList(pMenu);
		return new PageInfo<>(pMenuList);
	}


	@Override
	public List<PMenuBtn> queryBtns(Integer roleId, Integer menuId) {
		// TODO Auto-generated method stub
		return pMenuDao.selectBtns(roleId,menuId);
	}

}
