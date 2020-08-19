package com.bzly.common.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bzly.common.utils.HttpUtil;
import com.google.gson.Gson;

public class PayUtil {
	private static final Logger logger = LoggerFactory.getLogger(PayUtil.class);
	
	public static AlipayTradeAppPayResponse aliPay(String outTradeNo,BigDecimal price,String descripte,String aliAppId,String aliPrivateKey,String aliPublicKey) {
		BigDecimal bigDecimal = new BigDecimal("0");
		if(price.compareTo(bigDecimal)==-1) {
			return null;
		}
        try{
            AlipayClient alipayClient = PayCommonUtil.getAliClient(aliAppId,aliPrivateKey,aliPublicKey);
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setOutTradeNo(outTradeNo); 
            model.setTimeoutExpress("144m");
            model.setTotalAmount(String.valueOf(price)); 
            model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(AlipayConfig.notify_url);  
            model.setBody(descripte); 
            model.setSubject(descripte);
            request.setBizModel(model);
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return response;
        } catch (Exception e){
            logger.error(e.getMessage());
           return null;
        }
	}
	
	public static Map<String, Object> transferMoney(String outTradeNo,String amount,String aliNum,String userName) {
		AlipayClient alipayClient = PayCommonUtil.getAliClientCash();
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		AlipayTransfer alipayTransfer=new AlipayTransfer();
		alipayTransfer.setOut_biz_no(outTradeNo); 
		alipayTransfer.setPayee_type(AlipayConfig.payee_type); 
		alipayTransfer.setAmount(amount); 
		alipayTransfer.setPayer_show_name("宝猪乐园提现"); 
		alipayTransfer.setPayee_account(aliNum); 
		alipayTransfer.setPayee_real_name(userName); 
		alipayTransfer.setRemark("宝猪乐园提现");
		String json = new Gson().toJson(alipayTransfer); 
		request.setBizContent(json); 
		AlipayFundTransToaccountTransferResponse response=null; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("res", false);
		map.put("msg", "提现错误");
		try{ 
			response = alipayClient.execute(request); 
			if("10000".equals(response.getCode())){ 
				map.put("res", true);
				map.put("msg", "成功");
				return map; 
			}else{ 
				map.put("res", false);
				map.put("msg", response.getSubMsg());
				return map;
			}
		}catch(AlipayApiException e){
			e.printStackTrace(); 
			return map;
		}
	}
	 
	public static WeiXinPrePay wxpay(BigDecimal price,String outTradeNo,String descripte,String payType,String ip,String appId,String mchId,String apiKey,String wxCallback) {
	        BigDecimal totalAmount = new BigDecimal(String.valueOf(price));
	        String trade_no = "";
	        String description="";
	        try {
	            trade_no = new String(outTradeNo.getBytes("ISO-8859-1"),"UTF-8");
	            description = descripte;
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	
	        Map<String, String> map = weixinPrePay(trade_no,totalAmount,description,appId,payType,mchId,ip,apiKey,wxCallback);
	
	        SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
	   
	        finalpackage.put("appid", appId);
	        
	        finalpackage.put("partnerid",mchId);
	        Long time = (System.currentTimeMillis() / 1000);
	        
	        finalpackage.put("timestamp", time.toString());
	         
	        finalpackage.put("noncestr", map.get("nonce_str"));
	         
	        finalpackage.put("prepayid", map.get("prepay_id"));
	         
	        finalpackage.put("package", "Sign=WXPay");
	
	        WeiXinPrePay prePay = new WeiXinPrePay();
	        prePay.setAppid(appId);
	        prePay.setPartnerid(mchId);
	        prePay.setTimestamp(time.toString());
	        prePay.setNoncestr(map.get("nonce_str"));
	        prePay.setPrepayid(map.get("prepay_id"));
	        prePay.setBzPackage("Sign=WXPay");
	        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
	        signParam.put("appid", appId); 
	        signParam.put("partnerid", mchId); 
	        signParam.put("prepayid", map.get("prepay_id")); 
	        signParam.put("package", "Sign=WXPay"); 
	        signParam.put("noncestr", map.get("nonce_str")); 
	        signParam.put("timestamp",time.toString()); 
	        String signAgain = PayCommonUtil.createSign("UTF-8", signParam,apiKey); 
	        prePay.setSign(signAgain);
	        prePay.setOutTradeNo(map.get("outTradeNo"));
	        return prePay;
	 }
     
	 public static Map<String, String> weixinPrePay(String trade_no,BigDecimal totalAmount,
	         String description,String appId,String payType,String mchId,String ip,String apiKey,String wxCallback) {
		SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
		parameterMap.put("appid",appId);
		parameterMap.put("mch_id",mchId);
		parameterMap.put("nonce_str", PayCommonUtil.getRandomString(32));
		parameterMap.put("body", description);
		parameterMap.put("out_trade_no", trade_no);
		parameterMap.put("fee_type", "CNY");
		BigDecimal total = totalAmount.multiply(new BigDecimal(100));
		java.text.DecimalFormat df=new java.text.DecimalFormat("0");
		parameterMap.put("total_fee", df.format(total));
		parameterMap.put("spbill_create_ip", ip);
		parameterMap.put("notify_url", wxCallback);
		/*if(payType.equals("1")){
		parameterMap.put("trade_type", "APP");
		}else if(payType.equals("2")){
		parameterMap.put("trade_type", "JSAPI");
		parameterMap.put("openid", openid);
		}*/
		parameterMap.put("trade_type", "APP");
		String sign = PayCommonUtil.createSign("UTF-8", parameterMap,apiKey);
		parameterMap.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameterMap);
		
		String result = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST",requestXML);
		
		logger.info("wxpay:"+ result);
		Map<String, String> map = null;
		try {
		map = PayCommonUtil.doXMLParse(result);
		} catch (JDOMException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		map.put("outTradeNo",trade_no);
		return map;
	}
	
	public static void wxCash(Integer amount,String outTradeNo,String openId,String name,String ip,String appId,String mchId,String apiKey) {
		String nonce_str=PayCommonUtil.getRandomString(32);
        //是否校验用户姓名 NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
        String checkName ="FORCE_CHECK";
        String desc = "宝猪乐园提现"+amount+"元";
        Integer am=amount*100;
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("mch_appid", appId);
        parameters.put("mchid", mchId);
        parameters.put("partner_trade_no", outTradeNo);
        parameters.put("nonce_str", nonce_str);
        parameters.put("openid", openId);
        parameters.put("re_user_name", name);
        parameters.put("check_name", checkName);
        parameters.put("amount", am.toString());
        parameters.put("spbill_create_ip", ip);
        parameters.put("desc", desc);
        String sign = PayCommonUtil.createSign("UTF-8", parameters,apiKey); 
        Map<String,String> param = new HashMap<String,String>();
        param.put("mch_appid", appId);
        param.put("mchid", mchId);
        param.put("partner_trade_no", outTradeNo);
        param.put("nonce_str", nonce_str);
        param.put("openid", openId);
        param.put("re_user_name", name);
        param.put("check_name", checkName);
        param.put("amount", am.toString());
        param.put("spbill_create_ip", ip);
        param.put("desc", desc);
        param.put("sign", sign);
        String xmlInfo=PayCommonUtil.GetMapToXML(param);
        try {
            CloseableHttpResponse response =  HttpUtil.postXml("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xmlInfo, true,mchId);
            String transfersXml = EntityUtils.toString(response.getEntity(), "utf-8");
            Map<String, String> transferMap = PayCommonUtil.doXMLParse(transfersXml);
            System.out.println(transferMap);
            if (transferMap.size()>0) {
                if (transferMap.get("result_code").equals("SUCCESS") && transferMap.get("return_code").equals("SUCCESS")) {
                    //成功需要进行的逻辑操作，
                	transferMap.get("err_code_des");
                }else {
                	transferMap.get("err_code_des");
                }
            }
            System.out.println("成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
       
        }
       

	}
	/*public static WeiXinPrePay wxRedenvelopes(BigDecimal price,String outTradeNo,String descripte,String payType,String ip,String appId,String mchId,String apiKey,String wxCallback) {
        String appid = WxConfig.WXAppID;
        String mchId = WxConfig.MCH_ID;
        if(payType.equals("2")){
            appid = WxConfig.MINIAppID;
            mchId = WxConfig.MCH_ID_MINI;
        }

        BigDecimal totalAmount = new BigDecimal(String.valueOf(price));
        String trade_no = "";
        String description="";
        try {
            trade_no = new String(outTradeNo.getBytes("ISO-8859-1"),"UTF-8");
            description = descripte;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> map = weixinPrePay(trade_no,totalAmount,description,appId,payType,mchId,ip,apiKey,wxCallback);

        SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
   
        finalpackage.put("appid", appId);
        
        finalpackage.put("partnerid",mchId);
        Long time = (System.currentTimeMillis() / 1000);
        
        finalpackage.put("timestamp", time.toString());
         
        finalpackage.put("noncestr", map.get("nonce_str"));
         
        finalpackage.put("prepayid", map.get("prepay_id"));
         
        finalpackage.put("package", "Sign=WXPay");

        WeiXinPrePay prePay = new WeiXinPrePay();
        prePay.setAppid(appId);
        prePay.setPartnerid(mchId);
        prePay.setTimestamp(time.toString());
        prePay.setNoncestr(map.get("nonce_str"));
        prePay.setPrepayid(map.get("prepay_id"));
        prePay.setBzPackage("Sign=WXPay");
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", appId); 
        signParam.put("partnerid", mchId); 
        signParam.put("prepayid", map.get("prepay_id")); 
        signParam.put("package", "Sign=WXPay"); 
        signParam.put("noncestr", map.get("nonce_str")); 
        signParam.put("timestamp",time.toString()); 
        String signAgain = PayCommonUtil.createSign("UTF-8", signParam,apiKey); 
        prePay.setSign(signAgain);
        prePay.setOutTradeNo(map.get("outTradeNo"));
        return prePay;
 }*/
	
	public static void main(String[] args) {
		String WXAppID = "wx0a03b2f3261bab6e"; 
	    String MCH_ID = "1531506371";   
	    String API_KEY = "356eeb4cd3de431fae929ccdbbfa7d22";
		wxCash(1,"20190531140622pl1v1eu","oDeJ71BcotSYKdYSSdckq-RLcspE","杨志伟","192.168.1.27",WXAppID,MCH_ID,API_KEY);
		/*WeiXinPrePay wx=PayUtil.wxpay(new BigDecimal(100),"20190415150532ikepm34","ceshi","1","ovKO71Z44JWO2vJwqSkQJOHnaWw4","192.168.1.188");
		System.out.println(wx);*/
		//PayUtil.transferMoney("20190624104603k5qs48k","1","15927053416","杨志伟");
	}
}
