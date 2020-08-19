package com.mc.bzly.dao.passbook;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mc.bzly.model.passbook.MPassbook;

@Mapper
public interface MPassbookDao {
	
	void insert(MPassbook mPassbook);

	void update(MPassbook mPassbook);
	
	void delete(Integer id);
	
	MPassbook selectOne(Integer id);
	
	List<MPassbook> selectList(MPassbook mPassbook);
	
	List<MPassbook> selectRegSend();
}
