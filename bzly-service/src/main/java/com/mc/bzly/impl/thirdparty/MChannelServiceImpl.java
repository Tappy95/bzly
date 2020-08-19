package com.mc.bzly.impl.thirdparty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.MChannelDao;
import com.mc.bzly.model.thirdparty.MChannel;
import com.mc.bzly.service.thirdparty.MChannelService;
import com.mc.bzly.util.StringUtil;

@Service(interfaceClass = MChannelService.class,version = WebConfig.dubboServiceVersion)
public class MChannelServiceImpl implements MChannelService {

	@Autowired 
	private MChannelDao mChannelDao;
	
	@Transactional
	@Override
	public Result add(MChannel mChannel) throws Exception {
		Result result = new Result();
		MChannel cha=mChannelDao.selectNmae(mChannel.getChannelName());
		if(!StringUtil.isNullOrEmpty(cha)) {
			result.setStatusCode(RespCodeState.CHANNEL_CODE_EXISE.getStatusCode());
			result.setMessage(RespCodeState.CHANNEL_CODE_EXISE.getMessage());
			return result;
		}
		mChannelDao.insert(mChannel);
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		return result;
	}

	@Override
	public List<MChannel> queryList() {
		return mChannelDao.selectList();
	}

}
