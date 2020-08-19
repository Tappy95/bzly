package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PMenu;
import com.mc.bzly.model.platform.RightCollection;

public interface PMenuService {

	int add(PMenu pMenu) throws Exception;
	
	int modify(PMenu pMenu) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PMenu queryOne(Integer id);
	
	PageInfo<PMenu> queryList(PMenu pMenu);
	
	List<PMenu> queryParentMenu();
	
	List<RightCollection> queryRightCollection();

	List<RightCollection> queryMenu(Integer roleId);
}
