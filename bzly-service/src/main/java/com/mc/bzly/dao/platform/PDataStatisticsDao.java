package com.mc.bzly.dao.platform;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.ExcelDataModelQD;
import com.mc.bzly.model.platform.HomePageData;
import com.mc.bzly.model.platform.PDataStatistics;
import com.mc.bzly.model.platform.ExcelDataModel;

@Mapper
public interface PDataStatisticsDao {
	
	List<PDataStatistics> selectList(PDataStatistics pDataStatistics);
	/**
	 * 导出查询
	 * @param pDataStatistics
	 * @return
	 */
	List<PDataStatistics> selectExcelList(PDataStatistics pDataStatistics);
	
	/**
	 * 首页数据统计（顶部）
	 * @return
	 */
	Map<String, Object> homeTopData();
	
	/**
	 * 首页数据统计（中部）
	 * @param respMap
	 * @return
	 */
	HomePageData homeTable(Map<String, Object> respMap);
	
	Long selectRegCount(Map<String, Object> respMap);
	Long selectSignCount(Map<String, Object> respMap);
	Long selectLoginCount(Map<String, Object> respMap);
	Long firstVip(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Long bindZFB(Map<String, Object> respMap);
	Long vipCounts(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double vipAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double swAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double txzAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double lpdjAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double tdzAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double hdjlAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double hygxAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double jzdhAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double jczAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double zjzAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Double cjzAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Long cashUserNum(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	Long selectVipCount1();
	Long selectVipCount2();
	
	List<String> dtNumber1(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	List<String> dtNumber2(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	List<String> ctNumber(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	Long kszUserNum(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);

	Long qdzUserNum(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	Long lxdlNum(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	Long jcrAmount(@Param("param")Map<String, Object> respMap,@Param("userIds")List<String> userdIds);
	
	List<ExcelDataModel> selectGameData(String date);
	List<ExcelDataModelQD> selectGameDataQD(String date);

}
