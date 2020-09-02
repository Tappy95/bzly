package com.bzly.common.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class UploadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);
	
	public static final String ACCESSKEY = "J_HgoG0aLf-cjHt1BQxfs85Nbs1ZDZk04P1_Lgn5";
	public static final String SECRETKEY = "WwyxdMT2IyACDTOgnZXprzQ97J1fh7bBW6-tXlCW";
	
	public static final String BUCKET = "bzly-oss";
//	public static final String PRE_URL_1 = "https://image.bzlyplay.com/";
	public static final String PRE_URL_1 = "http://qfkqxz1oa.hb-bkt.clouddn.com/";
//	public static final String PRE_URL_2 = "https://image.baozhu8.com/";
	public static final String PRE_URL_2 = "http://qfkqxz1oa.hb-bkt.clouddn.com/";

	
	public static boolean uploadToQN(String fileName,InputStream is) throws QiniuException{
        Configuration cfg = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(cfg);
    	Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
    	String upToken = auth.uploadToken(BUCKET); 
    	logger.info(">>>>>>>>>>>>>>>>>file upload start<<<<<<<<<<<<<<<<<");
		Response response = uploadManager.put(is, fileName, upToken,null,null);
		logger.info(">>>>>>>>>>>>>>>>>file upload end<<<<<<<<<<<<<<<<<");
		if(response != null){
			return true;
		}else{
			return false;
		}
    }
	
	public static boolean deleteFromQN(String key) {
		if(key==null || key=="") {
			return false;
		}
		key = key.replaceAll(PRE_URL_1, "").replaceAll(PRE_URL_2, "");
		Configuration cfg = new Configuration(Zone.zone0());
		Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			logger.info(">>>>>>>>>>>>>>>>>remove file start<<<<<<<"+key+"<<<<<<<<<<");
		    bucketManager.delete(BUCKET, key);
		    logger.info(">>>>>>>>>>>>>>>>>remove file end<<<<<<<<<"+key+"<<<<<<<");
		    return true;
		} catch (QiniuException ex) {
			logger.error(ex.code()+"");
		    logger.info(ex.response.toString());
		    return false;
		}
	}
	
	public static String getUploadToken() {
    	Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
    	return auth.uploadToken(BUCKET); 
	}
	
}
