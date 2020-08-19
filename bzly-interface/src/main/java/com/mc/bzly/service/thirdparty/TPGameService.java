package com.mc.bzly.service.thirdparty;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.BZCallback;
import com.mc.bzly.model.thirdparty.PCDDCallback;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.thirdparty.XWCallback;
import com.mc.bzly.model.thirdparty.YoleCallback;

public interface TPGameService {

	int add(TPGame tpGame) throws Exception;
	
	int modify(TPGame tpGame) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	Map<String,Object> queryOne(Integer id);
	
	PageInfo<TPGame> queryList(TPGame tpGame,Integer marketId);

	String buildUrl(Integer id,Map<String, String> respMap);
	
	void yoleCallbackInsert(YoleCallback yoleCallback,long reward);

	PageInfo<TPGame> queryBList(TPGame tpGame);

	void pcddCallbackInsert(PCDDCallback pcDDCallback,long reward);
	
	Long settlementGame(String userId,long baseReward,String gameName) throws Exception;
	
	void xwCallbackInsert(XWCallback xwCallback,long reward);

	XWCallback queryByXWOrderNum(String ordernum);

	PCDDCallback queryByPCDDOrderNum(String ordernum);

	YoleCallback queryByYoLeId(String yoleid);

	TPGame recommendGame(int ptype,String userId);
	
	TPGame recommendGameNew(int ptype,String userId);

	String buildQDGameUrl(Integer id, Map<String, String> respMap, Integer signinId);
	
	void sendYOLE(YoleCallback yoleCallback);
	void sendPCDD(PCDDCallback pcDDCallback);
	void sendXW(XWCallback xwCallback);
	void sendBZ(BZCallback bzCallback);
	
	void bzCallbackInsert(BZCallback bzCallback,long reward);
	
	BZCallback queryByBZOrderNum(String ordernum);
	
	String buildHyjGameUrl(Integer id, Map<String, String> respMap, Integer vipId);
	
	String buildTxGameUrl(Integer id, Map<String, String> respMap, Integer cashId);
	
	String buildMrhbGameUrl(Integer id, Map<String, String> respMap);
	
	TPGame recommendGameTask(int ptype,String userId);

	PageInfo<TPGame> lenovoList(TPGame tpGame,String userId);

	List<String> tjList(TPGame tpGame);

	void sendXYZ(XWCallback xwCallback);

	Long settlementXYZ(String appsign, long baseReward, String adname);

	void xwCallbackInsertXYZ(XWCallback xwCallback, long reward);
	
	XWCallback queryByXWOrderNumXYZ(String ordernum);
	
}