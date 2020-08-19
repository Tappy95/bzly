package com.mc.bzly.service.thirdparty;

import java.util.List;

public interface TpGameRelationTypeService {

	int update(Integer gameId,String typeIds);
	
	List<Integer> listType(Integer gameId);
}
