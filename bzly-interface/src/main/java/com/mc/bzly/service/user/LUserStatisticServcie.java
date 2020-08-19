package com.mc.bzly.service.user;

import java.util.List;
import java.util.Map;

import com.mc.bzly.model.user.LUserReconciliation;

public interface LUserStatisticServcie {
	
	Map<String,Object> userList(LUserReconciliation lUserReconciliation);
	
	List<Map<String, Object>> excl(LUserReconciliation lUserReconciliation);

}
