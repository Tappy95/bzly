package com.mc.bzly.impl.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PRoleDao;
import com.mc.bzly.dao.platform.PRoleMenuBtnDao;
import com.mc.bzly.model.platform.PRole;
import com.mc.bzly.model.platform.PRoleMenuBtn;
import com.mc.bzly.service.platform.PRoleService;

@Service(interfaceClass = PRoleService.class,version = WebConfig.dubboServiceVersion)
public class PRoleServiceImpl implements PRoleService {
	
	@Autowired 
	private PRoleDao pRoleDao;
	
	@Autowired
	private PRoleMenuBtnDao pRoleMenuBtnDao;
	
	@Transactional
	@Override
	public int add(PRole pRole,String menuIds,String btnIds) throws Exception {
		pRole.setCreateTime(new Date().getTime());
		pRoleDao.insert(pRole);
		List<PRoleMenuBtn> menuBtns = new ArrayList<PRoleMenuBtn>();
		if(StringUtils.isNoneEmpty(menuIds)){
			String[] ids = menuIds.split(",");
			for (String id : ids) {
				PRoleMenuBtn pRoleMenuBtn = new PRoleMenuBtn(null,pRole.getId(), 1, new Integer(id));
				menuBtns.add(pRoleMenuBtn);
				if(menuBtns.size() > 50){
					pRoleMenuBtnDao.batchInsert(menuBtns);
					menuBtns.clear();
				}
			}
		}
		if(StringUtils.isNoneEmpty(btnIds)){
			String[] ids = btnIds.split(",");
			for (String id : ids) {
				PRoleMenuBtn pRoleMenuBtn = new PRoleMenuBtn(null,pRole.getId(), 2, new Integer(id));
				menuBtns.add(pRoleMenuBtn);
				if(menuBtns.size() > 50){
					pRoleMenuBtnDao.batchInsert(menuBtns);
					menuBtns.clear();
				}
			}
		}
		pRoleMenuBtnDao.batchInsert(menuBtns);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PRole pRole,String menuIds,String btnIds) throws Exception {
		pRoleDao.update(pRole);
		pRoleMenuBtnDao.deleteByRole(pRole.getId());
		List<PRoleMenuBtn> menuBtns = new ArrayList<PRoleMenuBtn>();
		if(StringUtils.isNoneEmpty(menuIds)){
			String[] ids = menuIds.split(",");
			for (String id : ids) {
				PRoleMenuBtn pRoleMenuBtn = new PRoleMenuBtn(null,pRole.getId(), 1, new Integer(id));
				menuBtns.add(pRoleMenuBtn);
				if(menuBtns.size() > 100){
					pRoleMenuBtnDao.batchInsert(menuBtns);
					menuBtns.clear();
				}
			}
		}
		if(StringUtils.isNoneEmpty(btnIds)){
			String[] ids = btnIds.split(",");
			for (String id : ids) {
				PRoleMenuBtn pRoleMenuBtn = new PRoleMenuBtn(null,pRole.getId(), 2, new Integer(id));
				menuBtns.add(pRoleMenuBtn);
				if(menuBtns.size() > 200){
					pRoleMenuBtnDao.batchInsert(menuBtns);
					menuBtns.clear();
				}
			}
		}
		pRoleMenuBtnDao.batchInsert(menuBtns);
		return 1;
	}

	@Override
	public PageInfo<PRole> queryList(PRole pRole) {
		PageHelper.startPage(pRole.getPageNum(), pRole.getPageSize());
		List<PRole> pRoleList = pRoleDao.selectList(pRole);
		return new PageInfo<>(pRoleList);
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		pRoleDao.delete(id);
		pRoleMenuBtnDao.deleteByRole(id);
		return 1;
	}

	@Override
	public Map<String, Object> queryOne(Integer id) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("role", pRoleDao.selectOne(id));
		resultMap.put("menus", pRoleMenuBtnDao.selectByRole(id));
		return resultMap;
	}

	@Override
	public List<PRole> queryOption() {
		
		return pRoleDao.selectOption();
	}
}
