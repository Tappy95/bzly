package com.mc.bzly.service.sms;

import com.mc.bzly.model.sms.SSmsLog;

import java.util.Map;

public interface ValidateCodeService {
	
	Map<String, Object> sendSmsCode(SSmsLog smsLog, String statusCallBack);

	Map<String, Object> validateSmsCode(SSmsLog smsLog);

	void checkReSend(String smsMessageId, String status);

	Map<String, Object> page(SSmsLog smsLog);

	Map<String, Object> sendSmsCodeZQ(SSmsLog smsLog, String statusCallBack);

	Map<String, Object> sendSmsCodeSDM(SSmsLog smsLog, String statusCallBack);

	Map<String, Object> sendSmsCodeXQ(SSmsLog smsLog, String callback, String channelCode);

	void remove(Integer id);
}
