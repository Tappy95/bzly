package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TPGame;

@Mapper
public interface TPGameDao {

	void insert(TPGame tpGame);
	
	void update(TPGame tpGame);
	
	void delete(Integer id);

	void deleteByInterface(Integer interfaceId);

	List<TPGame> selectByInterface(Integer interfaceId);
	
	TPGame selectOne(Integer id);
	
	List<TPGame> selectList(TPGame tpGame);
	
	void batchInsert(List<TPGame> tpGames);

	List<TPGame> selectBList(TPGame tpGame);

	List<TPGame> recommendGameList(@Param("ptype") int ptype,@Param("userId") String userId);
	
	TPGame selectByGameInterface(@Param("gameId") Integer gameId,@Param("interfaceId") Integer interfaceId);

	int selectOrderId(TPGame tPGame);
	
	List<TPGame> selectCashTask(Integer cashId);
	
	TPGame selectSignTask(String userId);
	
	List<TPGame> recommendGameListNews(@Param("ptype") int ptype,@Param("userId") String userId);
	
	List<TPGame> recommendTaskListNews(@Param("userId") String userId);
	
	Map<String,Object> selectHtOne(Integer id);
	
	List<TPGame> selectCashGameTask(Integer cashId);
	
	List<TPGame> recommendGameListIOS(@Param("ptype") int ptype,@Param("userId") String userId);

	List<TPGame> lenovoSelect(TPGame tpGame);

	List<String> tjList(TPGame tpGame);
}
