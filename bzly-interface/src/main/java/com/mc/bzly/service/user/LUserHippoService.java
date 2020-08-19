package com.mc.bzly.service.user;

import java.util.List;

import com.mc.bzly.model.user.LUserHippo;

public interface LUserHippoService {
	
	void add(LUserHippo lUserHippo,List<String> impTrackingList);

	void sendClkUrl(String clkUrl);
}
