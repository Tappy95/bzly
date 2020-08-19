package com.mc.bzly.dao.thirdparty;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.thirdparty.TpVideoCallback;

@Mapper
public interface TpVideoCallbackDao {
	
	int save(TpVideoCallback tpVideo);
	
	TpVideoCallback selectOne(String transId);
	
	int selectCount(@Param("userId")String userId,@Param("operateType")Integer operateType);
	
	TpVideoCallback selectTime(String userId);
	
	List<TpVideoCallback> selectList(TpVideoCallback tpVideo);
	
	int selectListCount(TpVideoCallback tpVideo);
	
	Map<String,Object> selectSmallSum(TpVideoCallback tpVideo);
	
	Map<String,Object> selectCountSum(TpVideoCallback tpVideo);
	
	int selectNewUserVideo(String userId);
	
	int selectNewUserVideoCoin(String userId);

}
