package com.mc.bzly.controller.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.Data;
import com.alipay.api.internal.util.AlipaySignature;
import com.bzly.common.pay.AlipayConfig;
import com.bzly.common.pay.PayCommonUtil;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.pay.PPayLog;
import com.mc.bzly.model.thirdparty.MChannelInfo;
import com.mc.bzly.service.pay.PayService;
import com.mc.bzly.service.thirdparty.MChannelInfoService;
import com.mc.bzly.service.user.MUserInfoService;

@RestController
public class PayControler extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(PayControler.class);
	
	@Value("${wxPay.url}")
	private String wxCallback;
	
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = PayService.class, check = false, timeout = 10000)
	private PayService payService;
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MChannelInfoService.class, check = false, timeout = 10000)
	private MChannelInfoService mChannelInfoService;
	@Reference(version = WebConfig.dubboServiceVersion, interfaceClass = MUserInfoService.class, check = false, timeout = 10000)
	private MUserInfoService mUserInfoService;
	
	@NeedToken
	@RequestMapping(value = "/pay/trade", method = RequestMethod.GET)
	void trade(HttpServletRequest request, HttpServletResponse response, PPayLog pPayLog,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			pPayLog.setPayIp(IPUtil.getIp(request));
			pPayLog.setUserId(getUserId(token));
			result=payService.trade(pPayLog);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}
	 
	@NeedToken
	@RequestMapping(value = "/pay/payType", method = RequestMethod.GET)
	void payType(HttpServletRequest request, HttpServletResponse response, String outTradeNo, String payMode) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(payService.payType(outTradeNo,payMode,wxCallback));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}
	 
	@NeedToken
	@RequestMapping(value = "/pay/paySuccess", method = RequestMethod.GET)
	void paySuccess(HttpServletRequest request, HttpServletResponse response, String outTradeNo) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(payService.payAppSuccess(outTradeNo));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}
	 
	@NeedToken
	@RequestMapping(value = "/pay/cancelPay", method = RequestMethod.GET)
	void cancelPay(HttpServletRequest request, HttpServletResponse response, String outTradeNo) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(payService.cancelPay(outTradeNo));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);

	}
	 
	@RequestMapping(value = "alipaycallback", method = RequestMethod.POST)
	String notifyAliPay(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {
		logger.info("get ZFB POST alipaycallback");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified) { 
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
            	return "fail";
            }else if (trade_status.equals("TRADE_SUCCESS")){
            	PPayLog payLog=payService.selectOne(out_trade_no);
            	if(payLog.getPayPurpose()==1) {
            		payService.paySuccess(out_trade_no);
            	}else {
            		mUserInfoService.changeRoleType(out_trade_no,new Date().getTime());	
            	}
            	/*payService.paySuccess(out_trade_no);*/
            	return "success";
            }
            return "fail";
        }else { 
        	return "fail";
        }
    }

	@RequestMapping(value = "/weixin/callback", method = RequestMethod.POST)
	String notifyWxPay(HttpServletRequest request,HttpServletResponse response) throws IOException, JDOMException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");
        Map<String, String> params = PayCommonUtil.doXMLParse(resultxml);
        outSteam.close();
        inStream.close();
       
        Map<String,String> return_data = new HashMap<String,String>(); 
        String out_trade_no = String.valueOf(params.get("out_trade_no"));
        MChannelInfo mChannelInfo=mChannelInfoService.selectCode(out_trade_no);
        if (!PayCommonUtil.isTenpaySign(params,mChannelInfo.getApiKey())) {
            // 支付失败
        	return_data.put("return_code", "FAIL");  
            return_data.put("return_msg", "return_code不正确");
        	return PayCommonUtil.GetMapToXML(return_data);
        } else {
        	PPayLog payLog=payService.selectOne(out_trade_no);
        	if(payLog.getPayPurpose()==1) {
        		payService.paySuccess(out_trade_no);
        	}else {
        		mUserInfoService.changeRoleType(out_trade_no,new Date().getTime());	
        	}
			return_data.put("return_code", "SUCCESS");  
            return_data.put("return_msg", "OK");  
			return PayCommonUtil.GetMapToXML(return_data);
        }
    }
	
	@NeedAuth
	@RequestMapping(value = "/pay/page", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, PPayLog pPayLog) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			Map<String, Object> data=payService.queryPageList(pPayLog);
			if("2".equals(data.get("res"))) {
				result.setStatusCode(RespCodeState.MOBILE_NOT_EXIST.getStatusCode());
				result.setMessage(RespCodeState.MOBILE_NOT_EXIST.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());	
			}
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	 
	@NeedAuth
	@RequestMapping(value = "/pay/info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(payService.selectInfo(id));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/*@RequestMapping(value = "/pay/test", method = RequestMethod.GET)
	void test(HttpServletRequest request, HttpServletResponse response,String outTradeNo) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		PPayLog payLog=payService.selectOne(outTradeNo);
    	if(payLog.getPayPurpose()==1) {
    		payService.paySuccess(outTradeNo);
    	}else {
    		mUserInfoService.changeRoleType(outTradeNo,new Date().getTime());	
    	}
		payService.paySuccess(outTradeNo);
		result.setData("1");
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}*/
	@NeedToken
	@RequestMapping(value = "/pay/tradeTame", method = RequestMethod.GET)
	void tradeTame(HttpServletRequest request, HttpServletResponse response, PPayLog pPayLog,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			pPayLog.setPayIp(IPUtil.getIp(request));
			pPayLog.setUserId(getUserId(token));
			Map<String,Object> data=payService.tradeTame(pPayLog);
			if("0".equals(data.get("res"))) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.PLACE_ORDER_FAIL.getStatusCode());
				result.setMessage(RespCodeState.PLACE_ORDER_FAIL.getMessage());
			}else if("4".equals(data.get("res"))) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.NO_PAY.getStatusCode());
				result.setMessage(RespCodeState.NO_PAY.getMessage());
			}else if("5".equals(data.get("res"))) {
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.DAREN_NO_TAME.getStatusCode());
				result.setMessage(RespCodeState.DAREN_NO_TAME.getMessage());
			}else {
				result.setData(data);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "/pay/payExcl", method = RequestMethod.GET)
	void payExcl(HttpServletRequest request, HttpServletResponse response,PPayLog pPayLog) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(payService.selectExcl(pPayLog));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}
