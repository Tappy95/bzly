package com.mc.bzly.service.user;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.user.LRankMachine;

public interface LRankMachineService {
	
    int add(LRankMachine lRankMachine);
	
    PageInfo<LRankMachine> list(LRankMachine lRankMachine);
	
	LRankMachine info(int id);
	
	int update(LRankMachine lRankMachine);
	
	int delete(int id);
}
