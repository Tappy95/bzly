package com.mc.bzly.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.RSUserPassbook;

public interface RSUserPassbookService {
	
	 
	void checkOverdue();
	 
	PageInfo<RSUserPassbook> selectAppList(RSUserPassbook rsUserPassbook);
	 
	List<RSUserPassbook> selectDiscount(String userId);
	
	RSUserPassbook selectAddDiscount(String userId);
}
