package com.mc.bzly.service.thirdparty;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.MChannelInfo;

public interface MChannelInfoService {

	int add(MChannelInfo mChannelInfo) throws Exception;
	
	int modify(MChannelInfo mChannelInfo) throws Exception;
	
	int remove(Integer id) throws Exception;
	
	MChannelInfo queryOne(Integer id);
	
	PageInfo<MChannelInfo> queryList(MChannelInfo mChannelInfo);

	String getDownloadUrl(int pType,String channelCode);
	/**
	 * 后台获取渠道下拉列表
	 * @return
	 */
	List<Map<String,Object>> selectDownList();
	/**
	 * 根据订单号查询渠道详情
	 * @param outTradeNo
	 * @return
	 */
	MChannelInfo selectCode(String outTradeNo);
	/**
	 * 后台渠道设置页面获取渠道下拉列表
	 * @return
	 */
	List<Map<String,Object>> selectChannelDownList();
	
	Map<String,Object> openPay(String userId);
	
	String queryByQrCode(String qrCode);
	/**
	 * 后台版本管理页面获取渠道下拉列表
	 * @return
	 */
	List<Map<String,Object>> selectVersionDownList();

}
