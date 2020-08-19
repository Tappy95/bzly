package com.mc.bzly.service.platform;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.platform.PAdmin;

public interface PAdminService {

	int add(PAdmin pAdmin) throws Exception;
	
	int modify(PAdmin pAdmin,PAdmin logAdmin) throws Exception;
	
	int remove(String adminId) throws Exception;
	
	PAdmin queryOne(String adminId);
	
	PageInfo<PAdmin> queryList(PAdmin pAdmin);
	
	Result login(PAdmin pAdmin);
	
	int updatePassword(PAdmin pAdmin);
}
