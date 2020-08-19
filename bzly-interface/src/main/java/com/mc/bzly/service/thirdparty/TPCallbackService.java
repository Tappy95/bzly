package com.mc.bzly.service.thirdparty;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.thirdparty.TPCallback;

public interface TPCallbackService {
	
	PageInfo<TPCallback> queryList(TPCallback tpCallback);
	
	/**
	 * 后台获取游戏商户下拉列表
	 * @return
	 */
	List<TPCallback> selectDownList();
	
	List<TPCallback> selectExcl(TPCallback tpCallback);
}
