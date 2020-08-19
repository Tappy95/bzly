package com.bzly.common.pay;

import java.io.FileWriter;
import java.io.IOException;

/**
 *支付宝支付
 */
public class AlipayConfig {
    public static String app_id = "";

    public static String merchant_private_key = "";

    public static String alipay_public_key = "";
    
    public static String app_id_cash ="";
    public static String merchant_private_key_cash ="";
    public static String alipay_public_key_cash ="";
    	
    public static String notify_url = "https://api.bzlyplay.com/alipaycallback";

    
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    public static String log_path = "C:\\";
 
    public static String payee_type = "ALIPAY_LOGONID";
    
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
