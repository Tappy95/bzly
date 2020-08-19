package com.mc.bzly.service.platform;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PMenuBtn;

public interface PMenuBtnService {

	int add(PMenuBtn pMenuBtn) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	PageInfo<PMenuBtn> queryList(PMenuBtn pMenuBtn);

	List<PMenuBtn> queryBtns(Integer roleId, Integer menuId);
}
