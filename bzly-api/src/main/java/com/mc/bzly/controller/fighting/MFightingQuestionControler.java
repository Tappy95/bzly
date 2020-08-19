package com.mc.bzly.controller.fighting;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.annotation.NeedToken;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.fighting.MFightingQuestion;
import com.mc.bzly.service.fighting.MFightingQuestionService;

@RestController
@RequestMapping("/mFightingQuestion")
public class MFightingQuestionControler extends BaseController {

	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=MFightingQuestionService.class,check=false,timeout=10000)
	private MFightingQuestionService mFightingQuestionService;
	
	@NeedToken
	@RequestMapping(value = "appTypeList", method = RequestMethod.GET)
	void appTypeList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mFightingQuestionService.questionTypeList());
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
	@RequestMapping(value="appAdd",method = RequestMethod.POST)
	void appAdd(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion,String token,String answer1,String answer2,String answer3,String answer4) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		mFightingQuestion.setCreateTime(new Date().getTime());
		mFightingQuestion.setQuestionState(1);
		try {
			mFightingQuestion.setCreator(getUserId(token));
			int i=mFightingQuestionService.appSave(mFightingQuestion,answer1,answer2,answer3,answer4);
			if(i==3) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.ANSWER_NO.getMessage());
			}else if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
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
	@RequestMapping(value="add",method = RequestMethod.POST)
	void add(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		mFightingQuestion.setCreateTime(new Date().getTime());
		try {
			int i=mFightingQuestionService.save(mFightingQuestion);
			if(i==3) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.ANSWER_NO.getMessage());
			}else if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
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
	@RequestMapping(value = "typeList", method = RequestMethod.GET)
	void typeList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mFightingQuestionService.questionTypeList());
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
	 
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void list(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try{
			result.setData(mFightingQuestionService.pageList(mFightingQuestion));
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
	@NeedAuth
	@RequestMapping(value="update",method = RequestMethod.POST)
	void update(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int i=mFightingQuestionService.update(mFightingQuestion);
			if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
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
	@RequestMapping(value="delete",method = RequestMethod.GET)
	void delete(HttpServletRequest request, HttpServletResponse response,Integer qId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int i=mFightingQuestionService.delete(qId);
			if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
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
	@RequestMapping(value = "info", method = RequestMethod.GET)
	void info(HttpServletRequest request, HttpServletResponse response,Integer qId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		result.setData(mFightingQuestionService.info(qId));
		result.setSuccess(true);
		result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
		result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		outStrJSONWithResult(response, result, respMap);
	}
	 
	@NeedToken
	@RequestMapping(value = "appCreatorList", method = RequestMethod.GET)
	void appCreatorList(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingQuestionService.pageCreatorList(mFightingQuestion));
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
	@RequestMapping(value = "appResult", method = RequestMethod.GET)
	void appResult(HttpServletRequest request, HttpServletResponse response,Integer qId) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingQuestionService.appResult(qId));
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
	 
	@NeedAuth
	@RequestMapping(value="audit",method = RequestMethod.POST)
	void audit(HttpServletRequest request, HttpServletResponse response,MFightingQuestion mFightingQuestion) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			int i=mFightingQuestionService.auditQuestion(mFightingQuestion);
			if(i==0) {
				result.setSuccess(false);
				result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
				result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
			}else {
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
	 
	@NeedToken
	@RequestMapping(value = "getUserTopic", method = RequestMethod.GET)
	void getUserTopic(HttpServletRequest request, HttpServletResponse response,String token) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(mFightingQuestionService.getUserTopic(getUserId(token)));
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
}
