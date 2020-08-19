package com.bzly.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
 
import javax.net.ssl.SSLContext;
 
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

public class CertUtil {
	/**
     * 加载证书
     */
    public static SSLConnectionSocketFactory initCert(String mchId) throws Exception {
        FileInputStream instream = null;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        instream = new FileInputStream(new File("D:\\apiclient_cert.p12"));
        keyStore.load(instream, mchId.toCharArray());
 
        if (null != instream) {
            instream.close();
        }
 
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,mchId.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
 
        return sslsf;
    }

}
