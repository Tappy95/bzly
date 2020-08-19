package com.mc.bzly.dao.pay;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.pay.PPayLog;

@Mapper
public interface PPayLogDao {
	/**
	 * 添加支付记录
	 * @param pPayLog
	 * @return
	 */
	Integer insert(PPayLog pPayLog);
	/**
	 * 修改支付记录
	 * @param pPayLog
	 * @return
	 */
	Integer update(PPayLog pPayLog);
	/**
	 * 删除支付记录
	 * @param id
	 * @return
	 */
	Integer delete(@Param("id")int id);
	/**
	 * 根据订单号查询支付记录
	 * @param outTradeNo
	 * @return
	 */
	PPayLog selectOne(@Param("outTradeNo")String outTradeNo);
	/**
	 * 查询支付记录列表
	 * @param pPayLog
	 * @return
	 */
	List<PPayLog> selectList(PPayLog pPayLog);
	/**
	 * 后台查询充值记录列表
	 * @param pPayLog
	 * @return
	 */
	List<PPayLog> selectPageList(PPayLog pPayLog);
	/**
	 * 后台查询充值记录详情
	 * @param id
	 * @return
	 */
	PPayLog selectInfo(@Param("id")Integer id);
	/**
	 * 查询用户待支付的订单
	 * @param userId
	 * @return
	 */
	int selectNoPay(@Param("userId")String userId);
	/**
	 * 查询用户购买vip累计充值金额
	 * @param userId
	 * @return
	 */
	int selectSumRecharge(String userId);
	
	List<PPayLog> selectExcl(PPayLog pPayLog);
	
	Map<String,String> selectCount(PPayLog pPayLog);
	/**
	 * 小计
	 * @param pPayLog
	 * @return
	 */
	Map<String,String> selectSum(PPayLog pPayLog);
	
	Map<String,String> selectCountPrice(PPayLog pPayLog);
	
	Double selectSumUserRecharge(String userId);

}
