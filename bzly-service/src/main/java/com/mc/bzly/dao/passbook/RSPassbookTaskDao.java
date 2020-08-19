package com.mc.bzly.dao.passbook;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.passbook.RSPassbookTask;

@Mapper
public interface RSPassbookTaskDao {

	void batchInsert(List<RSPassbookTask> rsPassbookTask);
	
	void deleteByPassbook(Integer passbookId);
	
	List<RSPassbookTask> selectByPassbook(Integer passbookId);

	RSPassbookTask selectByPassbookTask(@Param("passbookId")Integer passbookId,@Param("taskType") Integer taskType);
}
