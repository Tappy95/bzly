package com.mc.bzly.service.platform;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.model.platform.ExcelDataModel;
import com.mc.bzly.model.platform.ExcelDataModelQD;
import com.mc.bzly.model.platform.HomePageData;
import com.mc.bzly.model.platform.HomePageData1;
import com.mc.bzly.model.platform.HomePageData2;
import com.mc.bzly.model.platform.HomePageData3;
import com.mc.bzly.model.platform.PDataStatistics;

public interface PDataStatisticsService {
	
	PageInfo<PDataStatistics> queryList(PDataStatistics pDataStatistics);
	
	List<PDataStatistics> selectExcelList(PDataStatistics pDataStatistics);

	Map<String, Object> homeTopData();

	HomePageData homeTable(Map<String, String> respMap);

	HomePageData1 homeTable1(Map<String, String> respMap);
	
	HomePageData2 homeTable2(Map<String, String> respMap);

	HomePageData3 homeTable3(Map<String, String> respMap);

	List<ExcelDataModel> selectGameData(String date);

	List<ExcelDataModelQD> selectGameDataQD(String date);

}
