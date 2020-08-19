package com.mc.bzly.controller.platfrom;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.UploadUtil;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;

@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

	@Value("${qiniu.url}")
	private String qiniuUrl;

	@Value("${qiniu.url.preffix}")
	private String qiniuUrlPreffix;

	@RequestMapping(value = "")
	public void uploadPicture(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			String token = request.getParameter("token");
			if (StringUtils.isEmpty(token)) {
				respMap.put("token", "");
				result.setMessage(RespCodeState.ADMIN_NO_PERMISSION.getMessage());
				result.setStatusCode(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode());
				outStrJSONWithResult(response, result, respMap);
				return;
			} else {
				String tokenValue = redisService.getString(token);
				if (StringUtils.isEmpty(tokenValue)) {
					respMap.put("token", "");
					result.setMessage(RespCodeState.ADMIN_NO_PERMISSION.getMessage());
					result.setStatusCode(RespCodeState.ADMIN_NO_PERMISSION.getStatusCode());
					outStrJSONWithResult(response, result, respMap);
					return;
				}
			}
			String picFmt = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1,
					file.getOriginalFilename().length());
			String filename = qiniuUrlPreffix + DateUtil.getLongCurrentDate() + "." + picFmt;
			boolean rst = UploadUtil.uploadToQN(filename, file.getInputStream());
			if (rst) {
				result.setData(qiniuUrl + filename);
				result.setSuccess(true);
				result.setStatusCode(RespCodeState.API_PICTURE_UPLOAD_SUCCESS.getStatusCode());
				result.setMessage(RespCodeState.API_PICTURE_UPLOAD_SUCCESS.getMessage());
				outStrJSONWithResult(response, result, respMap);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setData(null);
		result.setSuccess(false);
		result.setStatusCode(RespCodeState.API_PICTURE_UPLOAD_FATLED.getStatusCode());
		result.setMessage(RespCodeState.API_PICTURE_UPLOAD_FATLED.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}

	@NeedToken
	@RequestMapping(path = "/getUploadToken", method = RequestMethod.GET)
	public void getUploadToken(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(UploadUtil.getUploadToken());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		} catch (Exception e) {
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
		}
		outStrJSONWithResult(response, result, respMap);
	}
}
