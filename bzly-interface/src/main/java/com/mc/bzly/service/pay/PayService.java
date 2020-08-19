package com.mc.bzly.service.pay;

import java.util.List;
import java.util.Map;


import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.pay.PPayLog;

public interface PayService {
	 
	Result trade(PPayLog pPayLog);
	 
	Map<String,Object> payType(String outTradeNo,String payMode,String wxCallback);
	 
	Integer payAppSuccess(String outTradeNo);
	 
	Integer cancelPay(String outTradeNo);
	 
	void paySuccess(String outTradeNo);
	 
	Map<String, Object> queryPageList(PPayLog pPayLog);
	 
	PPayLog selectInfo(Integer id);
	/**
	 * 购买团队长下单
	 * @param pPayLog
	 * @return
	 */
	Map<String,Object> tradeTame(PPayLog pPayLog);
	
	List<PPayLog> selectExcl(PPayLog pPayLog);
	
	PPayLog selectOne(String outTradeNo);

}
