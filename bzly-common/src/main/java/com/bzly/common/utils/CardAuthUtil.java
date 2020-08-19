package com.bzly.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CardAuthUtil {
	
	private static CloseableHttpClient httpClient;

	private static final String APPID = "";
	private static final String APPKEY = "";

    static {
        PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager(); 
        httpPool.setMaxTotal(200); 
        httpClient = HttpClients.custom().setConnectionManager(httpPool).build(); 
    }
    
    public static String auth(String name,String idNum,String cardNo,String mobile) throws Exception{
    	
    	List<NameValuePair> strList = new ArrayList<NameValuePair>();
    	strList.add(new BasicNameValuePair("appId", APPID));
    	strList.add(new BasicNameValuePair("appKey", APPKEY));
    	strList.add(new BasicNameValuePair("name", name));
    	strList.add(new BasicNameValuePair("idNum", idNum));
    	strList.add(new BasicNameValuePair("cardNo", cardNo));
    	strList.add(new BasicNameValuePair("mobile", mobile));
    	
    	HttpPost post = new HttpPost("https://api.253.com/open/bankcard/card-auth");
    	post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(strList, "utf-8");
        post.setEntity(entity);
        String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");
        JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(rspText).getAsJsonObject();
		String code = jsonObject.get("code").getAsString();
		 if ("200000".equals(code) && jsonObject.get("data") != null) {
             String result = jsonObject.get("data").getAsJsonObject().get("result").getAsString();
             System.out.println("auth success,result:" + result);
             return result;
         } else {
             System.out.println("auth fail,code:" + code + ",msg:" + jsonObject.get("message").getAsString());
             return "05";
         }
    	
    }

}
