package com.mc.bzly.service.user;

import java.util.List;

public interface LUserSearchLogService {
	
	List<String> queryList(String userId,Integer searchType);
	
	void removeAll(String userId,Integer searchType);
}
