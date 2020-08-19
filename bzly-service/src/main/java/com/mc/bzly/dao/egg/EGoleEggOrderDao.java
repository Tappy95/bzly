package com.mc.bzly.dao.egg;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.egg.EGoleEggOrder;

@Mapper
public interface EGoleEggOrderDao {
	
	int add(EGoleEggOrder eGoleEggOrder);
	
	int selectUserCount(String userId);
	 
	List<EGoleEggOrder> selectList(EGoleEggOrder eGoleEggOrder);
	
	int selectCount(EGoleEggOrder eGoleEggOrder);
	
	EGoleEggOrder selectOne(long id);
	
	int updateProhibit(@Param("isProhibit")int isProhibit,@Param("id")long id);
	
	long selectMaxId();
	
	List<Map<String,Object>> selectAppList(EGoleEggOrder eGoleEggOrder);
	
	int selectAppCount(EGoleEggOrder eGoleEggOrder);
	
	Map<String,Object> selectAppInfo(long id);
	
	String selectPassword(long id);
	
	EGoleEggOrder selectCard(String cardNumber);
	
	EGoleEggOrder selectCardNumOrPass(@Param("cardNumber")String cardNumber,@Param("cardPassword")String cardPassword);
	
	int update(EGoleEggOrder eGoleEggOrder);
	
	List<Map<String,Object>> selectUseAppList(EGoleEggOrder eGoleEggOrder);
	
	int selectUseAppCount(EGoleEggOrder eGoleEggOrder);
	
	List<Map<String,Object>> selectNewsRoll();

}
