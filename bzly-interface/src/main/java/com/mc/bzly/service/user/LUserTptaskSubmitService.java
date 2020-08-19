package com.mc.bzly.service.user;

import java.util.List;

import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.LUserTptaskSubmit;

public interface LUserTptaskSubmitService {
	
	Result add(List<LUserTptaskSubmit> lUserTptaskSubmit,String userId,Integer tpTaskId,Integer lTpTaskId);
}
