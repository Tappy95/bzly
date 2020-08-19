package com.mc.bzly.service.user;

import com.mc.bzly.model.user.LUserActive;

public interface LUserActiveService {
	 
	Integer insert(LUserActive lUserActive);
	 
	LUserActive selectOne(String userId,String activeTime);

}
