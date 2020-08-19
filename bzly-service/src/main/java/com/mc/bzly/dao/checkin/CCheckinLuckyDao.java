package com.mc.bzly.dao.checkin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.checkin.CCheckinLucky;

@Mapper
public interface CCheckinLuckyDao {
	
	List<CCheckinLucky> selectAppList();

}
