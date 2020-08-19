package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.model.user.MUserGyro;

public interface MUserGyroService {

	int addGyro(MUserGyro mUserGyro);
	
	Map<String, Object> page(Integer accountId);
}
