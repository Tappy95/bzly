package com.bzly.common.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtil {

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager(); 
        httpPool.setMaxTotal(200); 
        httpClient = HttpClients.custom().setConnectionManager(httpPool).build(); 
    }
    
    public static String sendGet(Map<String, Object> param,String url) throws ParseException, ClientProtocolException, IOException{
    	Iterator<Entry<String, Object>> it = param.entrySet().iterator();
    	while (it.hasNext()) {
    		Map.Entry<String, Object> entry = it.next();
            url = url+entry.getKey()+"="+entry.getValue()+"&";
    	}
    	url = url.substring(0,url.length()-1);
    	System.err.println("url = "+url);
    	HttpGet get = new HttpGet(url);
    	get.addHeader("Charset", "UTF-8");
        String rspText = EntityUtils.toString(httpClient.execute(get).getEntity(), "utf-8"); 
        return rspText;
    }
    
    public static void sendPOST(Map<String, Object> param,String url) throws ParseException, ClientProtocolException, IOException{
    	HttpPost post = new HttpPost(url);
    	 String requestJson = JSON.toJSONString(param);
         post.addHeader("Content-Type", "application/json");
         post.addHeader("Charset", "UTF-8");
         StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
         post.setEntity(paramsEntity);
         String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8"); 
         System.out.println("rspText = "+rspText);
    }
    
    public static String sendNewsPOST(Map<String, Object> param,String url,String token) throws ParseException, ClientProtocolException, IOException{
    	HttpPost post = new HttpPost(url);
    	String requestJson = JSON.toJSONString(param);
    	post.addHeader("Content-Type", "application/json");
    	post.addHeader("Charset", "UTF-8");
    	post.addHeader("token", token);
    	StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
    	post.setEntity(paramsEntity);
    	String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");// 发送http请求并获得响应结果
    	return rspText;
    }

    public static void sendGetUrl(String url) throws ParseException, ClientProtocolException, IOException{
    	HttpGet get = new HttpGet(url);
    	EntityUtils.toString(httpClient.execute(get).getEntity(), "utf-8");// 发送http请求并获得响应结果
    }
    
    public static String sendGetUrlNew(String url) throws ParseException, ClientProtocolException, IOException{
    	HttpGet get = new HttpGet(url);
    	return EntityUtils.toString(httpClient.execute(get).getEntity(), "utf-8");// 发送http请求并获得响应结果
    }
    
    public static CloseableHttpResponse postXml(String url, String outputEntity, boolean isLoadCert,String mchId) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(new StringEntity(outputEntity, "UTF-8"));
        
        if (isLoadCert) {
            // 加载含有证书的http请求
            return HttpClients.custom().setSSLSocketFactory(CertUtil.initCert(mchId)).build().execute(httpPost);
        } else {
            return HttpClients.custom().build().execute(httpPost);
        }
        
    }
    
    public static String sendPOST1(Map<String, Object> param,String url) throws ParseException, ClientProtocolException, IOException{
    	HttpPost post = new HttpPost(url);
    	 String requestJson = JSON.toJSONString(param);
         post.addHeader("Content-Type", "application/json");
         post.addHeader("Charset", "UTF-8");
         StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
         post.setEntity(paramsEntity);
         String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8"); 
         return rspText;
    }
    
   /* public static void main(String[] args) throws ParseException, ClientProtocolException, IOException {
		
    	String url = "https://h5.51xianwan.com/adwall/api/quickEarnList?";
    	Map<String, Object> param = new HashMap<>();
    	String ptype = "2";
    	String appsign = "userId";
    	String deviceid = "imei";
    	String appid = "3580";
    	String appsecret = "z58k7rohzpjf99kmv";
    	String ketStr = appid+deviceid+ptype+appsign+appsecret;
    	System.out.println("ketStr = "+ketStr);
    	String keycode = MD5Util.getMd5(ketStr);
    	System.out.println("keycode = "+keycode);
    	param.put("ptype",ptype );
    	param.put("deviceid","imei" );
    	param.put("appid",appid );
    	param.put("appsign",appsign);
    	param.put("keycode",keycode );
    	param.put("page",1 );
    	param.put("pagesize",200);
    	
    	String resp = sendGet(param, url);
    	System.out.println("resp = "+resp);
	}*/
    
}
