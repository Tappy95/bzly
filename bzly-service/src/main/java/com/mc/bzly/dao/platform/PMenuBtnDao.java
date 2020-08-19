package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PMenuBtn;
import com.mc.bzly.model.platform.RightCollection;

@Mapper
public interface PMenuBtnDao {

	void insert(PMenuBtn pMenuBtn);
	
	void delete(Integer id);
	
	List<PMenuBtn> selectList(PMenuBtn pMenuBtn);

	List<RightCollection> selectMenuBtnList(PMenuBtn pMenuBtn);

	List<PMenuBtn> selectBtns(@Param("roleId")Integer roleId, @Param("menuId")Integer menuId);
}
