package com.mc.bzly.service.checkin;

import java.util.Map;

import com.mc.bzly.model.checkin.CCheckinResult;

public interface CCheckinResultService {
	
    Map<String,Object> page(CCheckinResult cCheckinResult);
}
