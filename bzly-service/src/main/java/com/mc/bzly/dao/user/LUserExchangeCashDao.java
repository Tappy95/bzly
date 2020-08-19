package com.mc.bzly.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.user.LUserExchangeCash;
import com.mc.bzly.model.user.LuserCashGameStatistic;

@Mapper
public interface LUserExchangeCashDao {
	/**
	 * 添加提现记录
	 * @param lUserExchangeCash
	 * @return
	 */
	int insert(LUserExchangeCash lUserExchangeCash);
	/**
	 * 修改信息
	 * @param lUserExchangeCash
	 * @return
	 */
	int update(LUserExchangeCash lUserExchangeCash);
	/**
	 * 查询记录
	 * @param lUserExchangeCash
	 * @return
	 */
	List<LUserExchangeCash> selectList(LUserExchangeCash lUserExchangeCash);
	/**
	 * 记录数
	 * @param lUserExchangeCash
	 * @return
	 */
	Map<String,String> selectCount(LUserExchangeCash lUserExchangeCash);
	Map<String,String> selecCountPrice(LUserExchangeCash lUserExchangeCash);
	/**
	 * 锁定
	 * @param lUserExchangeCash
	 * @return
	 */
	int updateLocking(LUserExchangeCash lUserExchangeCash);
	/**
	 * 批量锁定
	 * @param lUserExchangeCashs
	 * @return
	 */
	int updateLockingList(List<LUserExchangeCash> lUserExchangeCashs);
	
	LUserExchangeCash selectOne(Integer id);
	
	LUserExchangeCash selectInfo(Integer id);
	
	List<LUserExchangeCash> selectExcl(LUserExchangeCash lUserExchangeCash);
	
	int selectCountUser(String userId);
	
	Map<String,String> selectSum(LUserExchangeCash lUserExchangeCash);
	
	List<Map<String,Object>> selectH5List(LUserExchangeCash lUserExchangeCash);
	
	int selectH5Count(LUserExchangeCash lUserExchangeCash);
	
	long selectSumMoney(String userId);
	
	List<Map<String,Object>> selectCashGame(LuserCashGameStatistic luserCashGameStatistic);
	
	int selectCashGameCount(LuserCashGameStatistic luserCashGameStatistic);
	
	List<Map<String,Object>> selectCashGameExcl(LuserCashGameStatistic luserCashGameStatistic);
	
	Double selectUsetDayCash(String userId);
	
	int selectTradeCount(String outTradeNo);
	
	int selectActualAmount(@Param("actualAmount")Double actualAmount,@Param("userId")String userId);

}
