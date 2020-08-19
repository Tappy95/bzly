package com.mc.bzly.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.user.LRankMachine;
@Mapper
public interface LRankMachineDao {
	
	int add(LRankMachine lRankMachine);
	
	List<LRankMachine> selectList(LRankMachine lRankMachine);
	
	LRankMachine selectOne(int id);
	
	int update(LRankMachine lRankMachine);
	
	int delete(int id);

}
