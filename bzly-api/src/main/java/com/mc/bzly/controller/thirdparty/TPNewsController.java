package com.mc.bzly.controller.thirdparty;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.bzly.common.utils.HttpUtil;
import com.bzly.common.utils.MD5Util;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.IPUtil;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.thirdparty.CashtoutiaoResp;
import com.mc.bzly.model.thirdparty.CashtoutiaoRespCover;
import com.mc.bzly.model.thirdparty.CashtoutiaoRespData;
import com.mc.bzly.model.thirdparty.HippoResp;
import com.mc.bzly.model.thirdparty.HippoRespAd;
import com.mc.bzly.model.user.LUserCashtoutiao;
import com.mc.bzly.model.user.LUserHippo;
import com.mc.bzly.service.user.LUserCashtoutiaoService;
import com.mc.bzly.service.user.LUserHippoService;

@RestController
@RequestMapping("tpNews")
public class TPNewsController extends BaseController {
	
	@Value("${cashtoutiao.appSecret}")
	private String appSecret;
	
	@Value("${cashtoutiao.appKey}")
	private String appKey;

	@Value("${cashtoutiao.url}")
	private String url;

	@Value("${cashtoutiao.funcation}")
	private String funcation;
	
	@Value("${hippo.submedia}")
	private String submedia;

	@Value("${hippo.media}")
	private String media;
	
	@Value("${hippo.baseUrl}")
	private String baseUrl;
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserCashtoutiaoService.class,check=false,timeout=10000)
	private LUserCashtoutiaoService lUserCashtoutiaoService;

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=LUserHippoService.class,check=false,timeout=10000)
	private LUserHippoService lUserHippoService;
	
	
	@RequestMapping(value = "getNews",method = RequestMethod.GET)
	void getNews(HttpServletRequest request,HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		long timestamp = new Date().getTime();
		String userId = "";
		int success = 2;
		try {
			if(token != null){
				userId = getUserId(token);
			} 
			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("type", 2);
			paramMap.put("channel", respMap.get("channel"));
			paramMap.put("appSecret", appSecret);
			paramMap.put("timestamp", timestamp);
			paramMap.put("url",funcation);
			if(StringUtils.isNoneEmpty(userId)){
				paramMap.put("userId", appKey+userId);
			}
			Iterator<String> it = paramMap.keySet().iterator();
			String tokenString = "";
			while (it.hasNext()) {
				String key=it.next();
				tokenString = tokenString+key+"="+paramMap.get(key)+"&";
			}
			tokenString = tokenString.substring(0, tokenString.length() - 1);
			String md5Str = MD5Util.getMd5(tokenString).toLowerCase();
			paramMap.put("appKey", appKey);
			String resp = HttpUtil.sendNewsPOST(paramMap, url, md5Str);
			CashtoutiaoResp cashtoutiaoResp= JSON.parseObject(resp,CashtoutiaoResp.class);
			if( cashtoutiaoResp.getState()){// 请求为成功
				success = 1;
				List<CashtoutiaoRespData> dataList = cashtoutiaoResp.getData();
				for (CashtoutiaoRespData cashtoutiaoRespData : dataList) {
					String cover = cashtoutiaoRespData.getCover();
					List<CashtoutiaoRespCover> cashtoutiaoRespCovers = JSON.parseArray(cover,CashtoutiaoRespCover.class);
					cashtoutiaoRespData.setCovers(cashtoutiaoRespCovers);
					cashtoutiaoRespData.setCover(null);
				}
				result.setData(dataList);
				result.setSuccess(true);
				result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
				result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			}else{
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}finally {
			if(StringUtils.isNoneEmpty(userId)){
				LUserCashtoutiao lUserCashtoutiao = new LUserCashtoutiao();
				lUserCashtoutiao.setChannel(respMap.get("channel"));
				lUserCashtoutiao.setCreateTime(timestamp);
				lUserCashtoutiao.setUserId(userId);
				lUserCashtoutiao.setStatus(success);
				lUserCashtoutiaoService.add(lUserCashtoutiao);
			}
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 获取瑞狮资讯平台新闻资讯
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getHIPPONews",method = RequestMethod.GET)
	void getHIPPONews(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		long timestamp = new Date().getTime();
		int success = 2;
		Result result = new Result();
		List<String> impTrackingList = new ArrayList<String>();
		try {
			Map<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("media", media);
			paramMap.put("submedia", submedia);
			paramMap.put("os", respMap.get("os")); // 操作系统 1-安卓 2-iOS
			paramMap.put("osv", respMap.get("osv")); // 操作系统版本号
			paramMap.put("imei",respMap.get("imei")); // imei
			paramMap.put("anid", respMap.get("anid")); // android id
			paramMap.put("make", URLEncoder.encode(respMap.get("make"), "utf-8")); // 设备生产商
			paramMap.put("model", URLEncoder.encode(respMap.get("model"), "utf-8")); // 设备型号
			paramMap.put("sh", respMap.get("sh")); // 屏幕高度
			paramMap.put("sw", respMap.get("sw")); // 屏幕宽度
			paramMap.put("devicetype", respMap.get("devicetype")); // 设备类型 1-手机 2-平板
			paramMap.put("ip", IPUtil.getIp(request));
			paramMap.put("ua", ""); 
			paramMap.put("conn", respMap.get("conn")); // 网络连接类型 1-wifi 2-2G 3-3G 4-4G
			paramMap.put("carrier", respMap.get("carrier")); // 运营商 1-中国移动 2-中国联通 3-中国电信
			
			paramMap.put("appname", respMap.get("appname") == null?"":URLEncoder.encode(respMap.get("appname"), "utf-8")); // 媒体名称
			paramMap.put("pkgname", respMap.get("pkgname") == null?"":URLEncoder.encode(respMap.get("pkgname"), "utf-8")); // 媒体包名
			paramMap.put("app_version", respMap.get("app_version") == null?"":URLEncoder.encode(respMap.get("app_version"), "utf-8")); // 媒体版本
			paramMap.put("lon", respMap.get("lon") == null?"":URLEncoder.encode(respMap.get("lon"), "utf-8")); // GPS经度
			paramMap.put("lat", respMap.get("lat") == null?"":URLEncoder.encode(respMap.get("lat"), "utf-8")); // GPS纬度
			
			// 分页，或下拉刷新次数。 用户下拉刷新，可传入page=-1。第二次下拉刷新，可传入page=-2。以此类推。
			paramMap.put("page", respMap.get("page"));
			paramMap.put("category", URLEncoder.encode(respMap.get("category"), "utf-8")); 
			
			String respText = HttpUtil.sendGet(paramMap, baseUrl);
			List<HippoResp> HippoResps = JSON.parseArray(respText, HippoResp.class);
			// 创建需要曝光检测的集合，并开始往集合里面设置数据
			
			for (int i = 0; i < HippoResps.size(); i++) {
				// 资讯曝光
				String [] impTrackingArr = HippoResps.get(i).getImp_tracking();
				for (int j = 0; j < impTrackingArr.length; j++) {
					impTrackingList.add(impTrackingArr[j]);
				}
				// 广告曝光
				if(HippoResps.get(i).isIs_ad()){
					List<HippoRespAd> ads = HippoResps.get(i).getAd();
					for (int j = 0; j < ads.size(); j++) {
						String [] impTrackingAdArr = ads.get(j).getImp_tracking();
						for (int m = 0; m < impTrackingAdArr.length; m++) {
							impTrackingList.add(impTrackingAdArr[m]);
						}
					}
				}
			}
			result.setData(HippoResps);
			result.setSuccess(true);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 添加用户点击记录
			String userId = "";	
			if(StringUtils.isNotEmpty(respMap.get("token"))){
				userId = getUserId(respMap.get("token"));
			}
			LUserHippo lUserHippo = new LUserHippo();
			lUserHippo.setCategory(respMap.get("category"));
			lUserHippo.setCreateTime(timestamp);
			lUserHippo.setUserId(userId);
			lUserHippo.setStatus(success);
			lUserHippoService.add(lUserHippo,impTrackingList);
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "clkNewsAd",method = RequestMethod.GET)
	void clkNewsAd(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		String clkUrl = respMap.get("clkUrl");
		Result result = new Result();
		try {
			lUserHippoService.sendClkUrl(clkUrl);
			result.setSuccess(true);
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}