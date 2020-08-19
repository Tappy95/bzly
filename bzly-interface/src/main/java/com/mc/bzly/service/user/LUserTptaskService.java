package com.mc.bzly.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.Result;
import com.mc.bzly.model.user.LUserTptask;
import com.mc.bzly.model.user.LUserTptaskSimple;

public interface LUserTptaskService {
	
	/**
	 * @param lUserTptask
	 * @return
	 */
	Result add(LUserTptask lUserTptask);

	/**
	 * 查询列表
	 * @param userId
	 * @return
	 */
	List<LUserTptask> queryByUser(String userId,Integer pageSize,Integer pageNum);
	
	/**
	 * @param lUserTptask
	 */
	void modify(LUserTptask lUserTptask);

	/**
	 * 查询用户任务详情
	 * @param taskId
	 * @param userId
	 * @return
	 */
	Result queryInfo(Integer taskId, String userId);

	/**
	 * 发送数据给平台方
	 * @param id 记录id
	 */
	Result sendData(Integer id);

	/**
	 * 用户预约
	 * @param lUserTptask
	 * @return
	 */
	Result addYY(LUserTptask lUserTptask);

	PageInfo<LUserTptaskSimple> queryFlist(LUserTptask lUserTptask);

	Result givein(Integer id,String remark);
}
