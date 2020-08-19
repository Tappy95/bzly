package com.mc.bzly.service.platform;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.PDictionary;

public interface PDictionaryService {

	int add(PDictionary pDictionary) throws Exception;
	
	int modify(PDictionary pDictionary) throws Exception;
	
	PDictionary queryOne(Integer id);

	PDictionary queryByName(String	dicName);
	
	PageInfo<PDictionary> queryList(PDictionary pDictionary);

	List<PDictionary> queryListByDicName(String dicName);
	/**
	 * app首页获取配置信息
	 * @return
	 */
	Map<String,Object> homePageDispose(Map<String,String> resp);
	/**
	 * app获取新手引导新手任务开关
	 * @param userId
	 * @return
	 */
	Map<String,Object> noviceBut(String userId,String versionNo,String channelCode);
	/**
	 * 获取官方联系方式
	 * @return
	 */
	Map<String,Object> contactInformation();
	
	Map<String,Object> homeIosPageDispose(Map<String,String> resp);
}