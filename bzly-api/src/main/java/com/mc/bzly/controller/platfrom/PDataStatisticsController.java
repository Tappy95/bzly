package com.mc.bzly.controller.platfrom;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mc.bzly.util.StringUtil;
import com.bzly.common.utils.DateUtil;
import com.bzly.common.utils.ExcelUtil;
import com.mc.bzly.annotation.NeedAuth;
import com.mc.bzly.base.BaseController;
import com.mc.bzly.base.RespCodeState;
import com.mc.bzly.base.Result;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.model.platform.PDataStatistics;
import com.mc.bzly.model.platform.ExcelDataModel;
import com.mc.bzly.model.platform.ExcelDataModelQD;
import com.mc.bzly.service.platform.PDataStatisticsService;

@RestController
@RequestMapping("/pDataStatistics")
public class PDataStatisticsController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=PDataStatisticsService.class,check=false,timeout=120000,retries=0)
	private PDataStatisticsService pDataStatisticsService;
	
	@NeedAuth
	@RequestMapping(value = "list", method = RequestMethod.GET)
	void page(HttpServletRequest request, HttpServletResponse response, PDataStatistics pDataStatistics) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.queryList(pDataStatistics));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "listExcel", method = RequestMethod.GET)
	void listExcel(HttpServletRequest request, HttpServletResponse response, PDataStatistics pDataStatistics) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.selectExcelList(pDataStatistics));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "homeTopData", method = RequestMethod.GET)
	void homeTopData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.homeTopData());
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@NeedAuth
	@RequestMapping(value = "homeTable", method = RequestMethod.GET)
	void homeTable(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.homeTable(respMap));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value = "homeTable1", method = RequestMethod.GET)
	void homeTable1(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.homeTable1(respMap));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value = "homeTable2", method = RequestMethod.GET)
	void homeTable2(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.homeTable2(respMap));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	@NeedAuth
	@RequestMapping(value = "homeTable3", method = RequestMethod.GET)
	void homeTable3(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		Result result = new Result();
		try {
			result.setData(pDataStatisticsService.homeTable3(respMap));
			result.setSuccess(true);
			result.setStatusCode(RespCodeState.API_OPERATOR_SUCCESS.getStatusCode());
			result.setMessage(RespCodeState.API_OPERATOR_SUCCESS.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatusCode(RespCodeState.API_ERROE_CODE_3000.getStatusCode());
			result.setMessage(RespCodeState.API_ERROE_CODE_3000.getMessage());
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping("/gameExcel")
    public void userGameStatisticExcl(HttpServletRequest request, HttpServletResponse response,String date,String pwd) throws Exception{
		 if(StringUtil.isEmpty(pwd)){
			Map<String, String> respMap = new HashMap<String, String>();
			Result result = new Result();
			outStrJSONWithResult(response, result, respMap);
		 }
		 if(!"123456".equals(pwd)){
			Map<String, String> respMap = new HashMap<String, String>();
			Result result = new Result();
			outStrJSONWithResult(response, result, respMap);
		 }
	 	//excel标题
        String[] title = new String[]{"序号","订单号","游戏名称","奖励等级","奖励说明","渠道奖励","用户奖励","回调时间","用户id","用户渠道","游戏平台"};
        //excel文件名
        if(StringUtil.isEmpty(date)){
        	date = DateUtil.getYesterday2();
        }
        String fileName = date+"用户试玩数据表"+".xls";
        //sheet名
        String sheetName = "用户试玩数据";
        List<ExcelDataModel> data = pDataStatisticsService.selectGameData(date);
        String[][] content = new String[data.size()][1];
        try {
        for (int i = 0; i < data.size(); i++) {
            content[i] = new String[title.length];
            ExcelDataModel obj = data.get(i);
            content[i][0] = i + 1 + "";
            if(obj.getOrdernum()!=null) {
                content[i][1] = obj.getOrdernum().toString();
            }
            if(obj.getAdname()!=null) {
                content[i][2] = obj.getAdname().toString();
            }
            if(obj.getDlevel()!=null) {
                content[i][3] = obj.getDlevel().toString();
            }
            if(obj.getEvent()!=null) {
                content[i][4] = obj.getEvent().toString();
            }
            if(obj.getPrice()!=null) {
                content[i][5] = obj.getPrice()+"";
            }
            if(obj.getMoney()!=null) {
            	content[i][6] = obj.getMoney()+"";
            }
            if(obj.getCreateTime()!=null) {
            	content[i][7] = obj.getCreateTime()+"";
            }
            if(obj.getAccount_id()!=null) {
            	content[i][8] = obj.getAccount_id()+"";
            }
            if(obj.getChannelCode()!=null) {
            	content[i][9] = obj.getChannelCode()+"";
            }
            if(obj.getPlatform()!=null) {
            	content[i][10] = obj.getPlatform()+"";
            }
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        
            try {
                try {
                    fileName = new String(fileName.getBytes(),"ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                response.setContentType("application/octet-stream;charset=ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                response.setContentType("text/html;charset=UTF-8");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 启东jie数据导出
	 * @param request
	 * @param response
	 * @param date
	 * @param pwd
	 * @throws Exception
	 */
	@RequestMapping("/gameExcelQD")
	public void gameExcelQD(HttpServletRequest request, HttpServletResponse response,String date,String pwd) throws Exception{
		if(StringUtil.isEmpty(pwd)){
			Map<String, String> respMap = new HashMap<String, String>();
			Result result = new Result();
			outStrJSONWithResult(response, result, respMap);
		}
		if(!"12345".equals(pwd)){
			Map<String, String> respMap = new HashMap<String, String>();
			Result result = new Result();
			outStrJSONWithResult(response, result, respMap);
		}
		//excel标题
		String[] title = new String[]{"序号","游戏名称","奖励等级","奖励说明","渠道奖励","用户奖励","回调时间","用户id"};
		//excel文件名
		if(StringUtil.isEmpty(date)){
			date = DateUtil.getYesterday2();
		}
		String fileName = date+"渠道jie用户试玩数据表"+".xls";
		//sheet名
		String sheetName = "用户试玩数据";
		List<ExcelDataModelQD> data = pDataStatisticsService.selectGameDataQD(date);
		String[][] content = new String[data.size()][1];
		try {
			for (int i = 0; i < data.size(); i++) {
				content[i] = new String[title.length];
				ExcelDataModelQD obj = data.get(i);
				content[i][0] = i + 1 + "";
				if(obj.getOrdernum()!=null) {
					content[i][1] = obj.getOrdernum().toString();
				}
				if(obj.getAdname()!=null) {
					content[i][2] = obj.getAdname().toString();
				}
				if(obj.getDlevel()!=null) {
					content[i][3] = obj.getDlevel().toString();
				}
				if(obj.getEvent()!=null) {
					content[i][4] = obj.getEvent().toString();
				}
				if(obj.getPrice()!=null) {
					content[i][5] = obj.getPrice()+"";
				}
				if(obj.getMoney()!=null) {
					content[i][6] = obj.getMoney()+"";
				}
				if(obj.getCreateTime()!=null) {
					content[i][7] = obj.getCreateTime()+"";
				}
				if(obj.getAccount_id()!=null) {
					content[i][8] = obj.getAccount_id()+"";
				}
			}
			//创建HSSFWorkbook
			HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
			//响应到客户端
			try {
				try {
					fileName = new String(fileName.getBytes(),"ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				response.setContentType("application/octet-stream;charset=ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
				response.addHeader("Pargam", "no-cache");
				response.addHeader("Cache-Control", "no-cache");
				response.setContentType("text/html;charset=UTF-8");
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
