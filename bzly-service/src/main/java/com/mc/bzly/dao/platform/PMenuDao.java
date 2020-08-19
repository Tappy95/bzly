package com.mc.bzly.dao.platform;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mc.bzly.model.platform.PMenu;
import com.mc.bzly.model.platform.RightCollection;

@Mapper
public interface PMenuDao {

	void insert(PMenu pMenu);
	
	void update(PMenu pMenu);
	
	void delete(Integer id);
	
	PMenu selectOne(Integer id);
	
	List<PMenu> selectList(PMenu pMenu);

	List<PMenu> selectParentMenu();

	List<RightCollection> selectRightCollection();
	
	List<RightCollection> selectMenus(@Param("roleId")Integer roleId,@Param("parentId")Integer parentId);
}
