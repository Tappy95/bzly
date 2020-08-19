package com.mc.bzly.impl.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.platform.PMenuBtnDao;
import com.mc.bzly.dao.platform.PMenuDao;
import com.mc.bzly.model.platform.PMenu;
import com.mc.bzly.model.platform.PMenuBtn;
import com.mc.bzly.model.platform.RightCollection;
import com.mc.bzly.service.platform.PMenuService;

@Service(interfaceClass = PMenuService.class,version = WebConfig.dubboServiceVersion)
public class PMenuServiceImpl implements PMenuService {
	
	@Autowired 
	private PMenuDao pMenuDao;
	
	@Autowired
	private PMenuBtnDao pMenuBtnDao;
	
	@Transactional
	@Override
	public int add(PMenu pMenu) throws Exception {
		pMenu.setCreateTime(new Date().getTime());
		pMenuDao.insert(pMenu);
		return 1;
	}

	@Transactional
	@Override
	public int modify(PMenu pMenu) throws Exception {
		pMenuDao.update(pMenu);
		return 1;
	}

	@Override
	public PageInfo<PMenu> queryList(PMenu pMenu) {
		PageHelper.startPage(pMenu.getPageNum(), pMenu.getPageSize());
		List<PMenu> pMenuList = pMenuDao.selectList(pMenu);
		return new PageInfo<>(pMenuList);
	}

	@Override
	public int remove(Integer id) throws Exception {
		pMenuDao.delete(id);
		return 1;
	}

	@Override
	public PMenu queryOne(Integer id) {
		return pMenuDao.selectOne(id);
	}

	@Override
	public List<PMenu> queryParentMenu() {
		List<PMenu> results = new ArrayList<>();
		PMenu top = new PMenu();
		top.setId(0);
		top.setMenuName("顶级菜单");
		results.add(top);
		results.addAll(pMenuDao.selectParentMenu());
		return results;
	}

	@Override
    public List<RightCollection> queryRightCollection() {
		List<RightCollection> resultList = new ArrayList<>();
		resultList = pMenuDao.selectRightCollection();
		for (int i = 0; i < resultList.size(); i++) {
			if(resultList.get(i).getSonCount() == 0){
				// 查询菜单上的按钮
				PMenuBtn pMenuBtn = new PMenuBtn(resultList.get(i).getId());
				List<RightCollection> collections = pMenuBtnDao.selectMenuBtnList(pMenuBtn);
				resultList.get(i).setRightCollections(collections);
			}else {
				List<RightCollection> sonList = resultList.get(i).getRightCollections();
				for(int j = 0; j < sonList.size(); j++){
					// 查询子菜单上的按钮
					PMenuBtn pMenuBtn = new PMenuBtn(sonList.get(j).getId());
					List<RightCollection> collections = pMenuBtnDao.selectMenuBtnList(pMenuBtn);
					sonList.get(j).setRightCollections(collections);
				}
			}
		}
        return resultList;
    }

	@Override
	public List<RightCollection> queryMenu(Integer roleId) {
		// 查询一级菜单
		List<RightCollection> resultList = new ArrayList<>();
		resultList = pMenuDao.selectMenus(roleId, 0);
		for (RightCollection rightCollection : resultList) {
			// 查询二级菜单
			List<RightCollection> sonList = new ArrayList<>();
			sonList = pMenuDao.selectMenus(roleId, rightCollection.getId());
			rightCollection.setRightCollections(sonList);
		}
		return resultList;
	}

}
