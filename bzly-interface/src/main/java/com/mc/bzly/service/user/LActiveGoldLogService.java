package com.mc.bzly.service.user;

import java.util.Map;

import com.mc.bzly.base.Result;

public interface LActiveGoldLogService {
	
     public Map<String,Object> receiveActive(String userId);
     
     public Result receiveActiveNews(String userId,Integer vipId);
     
     public void rechargeRewardPush(String userId,String num,String unit);
     
     public Map<String,Object> recommendGameVip(int ptype,String userId,int vipId);
}
