package com.bzly.common.thirdparty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class WxSupport {
	
	public static Map<String,Object> getAccessToken(String code,String appId,String appSecret){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${APPID}&secret=${SECRET}&code=${CODE}&grant_type=authorization_code";
        String urlNameString = url.replace("${APPID}",appId).replace("${SECRET}",appSecret).replace("${CODE}",code);
        return JSONObject.parseObject(getDataFromUrl(urlNameString));
    }
	
	public static Map<String,Object> getUserInfo(String accessToken,String openId){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        System.out.println(url);
        //String urlNameString = url.replace("${accessToken}",accessToken).replace("${openId}",openId);
        return JSONObject.parseObject(getDataFromUrl(url));
    }
	
	public static Map<String,Object> getWxMessageForMini(String code,String appId,String appSecret){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=${APPID}&secret=${SECRET}&js_code=${CODE}&grant_type=authorization_code";
        String urlNameString = url.replace("${APPID}",appId).replace("${SECRET}",appSecret).replace("${CODE}",code);
        return JSONObject.parseObject(getDataFromUrl(urlNameString));
    }
	
	public static String getDataFromUrl(String url){
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
	
}
