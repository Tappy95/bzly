package com.mc.bzly.impl.thirdparty;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bzly.common.pay.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bzly.common.utils.HttpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mc.bzly.base.WebConfig;
import com.mc.bzly.dao.thirdparty.TPGameDao;
import com.mc.bzly.dao.thirdparty.TPInterfaceDao;
import com.mc.bzly.dao.thirdparty.TPParamsDao;
import com.mc.bzly.dao.thirdparty.TPRespDao;
import com.mc.bzly.model.thirdparty.TPGame;
import com.mc.bzly.model.thirdparty.TPInterface;
import com.mc.bzly.model.thirdparty.TPParams;
import com.mc.bzly.model.thirdparty.TPResp;
import com.mc.bzly.service.thirdparty.TPInterfaceService;
import com.mc.bzly.util.SignUtil;

@Service(interfaceClass = TPInterfaceService.class,version = WebConfig.dubboServiceVersion)
public class TPInterfaceServiceImpl implements TPInterfaceService {

	@Autowired 
	private TPInterfaceDao tpInterfaceDao;
	
	@Autowired
	private TPParamsDao tpParamsDao;
	
	@Autowired
	private TPRespDao tpRespDao;
	
	@Autowired
	private TPGameDao tpGameDao;

	private static final Logger logger = LoggerFactory.getLogger(TPInterfaceServiceImpl.class);

	@Transactional
	@Override
	public int add(TPInterface tpInterface) throws Exception {
		tpInterface.setCreateTime(new Date().getTime());
		tpInterfaceDao.insert(tpInterface);
		return 1;
	}

	@Transactional
	@Override
	public int modify(TPInterface tpInterface) throws Exception {
		tpInterfaceDao.update(tpInterface);
		return 1;
	}

	@Transactional
	@Override
	public int remove(Integer id) throws Exception {
		tpInterfaceDao.delete(id);
		return 1;
	}

	@Override
	public TPInterface queryOne(Integer id) {
		return tpInterfaceDao.selectOne(id);
	}

	@Override
	public PageInfo<TPInterface> queryList(TPInterface tpInterface) {
		PageHelper.startPage(tpInterface.getPageNum(), tpInterface.getPageSize());
		List<TPInterface> tpInterfaceList = tpInterfaceDao.selectList(tpInterface);
		return new PageInfo<>(tpInterfaceList);
	}

	@Override
	public List<TPInterface> queryOpt() {
		return tpInterfaceDao.selectList(null);
	}

	@Transactional
	@Override
	public int getGames(Integer id) {
		// TODO Auto-generated method stub
		TPInterface tpInterface = tpInterfaceDao.selectOne(id);
		List<TPParams> tpParams  = tpParamsDao.selectListByInterface(id);
		String signStr = "";
		Map<String, Object> param = new HashMap<>(); // 接口所需参数
		List<String> encrpty = new ArrayList<>(); // 加密所需参数
		try {
			for (TPParams tpPara : tpParams) {
				if(tpPara.getEncryptNeed().intValue() == 1){ // 是否加密列表所需字段
					encrpty.add(tpPara.getValue());
				}
				if(tpPara.getIsEncrypt().intValue() == 1){ // 是否加密字段
					if(tpPara.getEncryptType().intValue() == 1){
						signStr = SignUtil.getMD5Sign(encrpty, tpPara.getEncryptSeparator());
						if(tpPara.getEncryptType().intValue() == 1){ // md5加密
							tpPara.setValue(signStr);
						}
					}
				}
				if(tpPara.getIsNeed().intValue() == 1){ // 是否是请求列表参数
					param.put(tpPara.getCode(), tpPara.getValue());
				}
			}
			
			List<TPResp> tpResps = tpRespDao.selectListByInterface(id);
			// 调用接口
			int ptype = 2;
			String rspText = "";
			if(tpInterface.getReqType().intValue() == 1){
				rspText = HttpUtil.sendGet(param, tpInterface.getBaseUrl() + tpInterface.getInterfaceCode() +"?");
				JSONObject obj=(JSONObject) JSON.parse(rspText);
				Object tar=obj.get("data");
				if("SDK_PlayList.ashx".equals(tpInterface.getInterfaceCode())){
					JSONObject obj2 = (JSONObject) JSON.parse(tar.toString());
					tar =obj2.get("items");
					ptype = new Integer(param.get("ptype").toString());
				}else if("channelOnLineAdids".equals(tpInterface.getInterfaceCode())){
					JSONObject obj2 = (JSONObject) JSON.parse(obj.toString());
					tar =obj2.get("items");
					ptype = new Integer(param.get("ptype").toString());
				}else if("flowGameList".equals(tpInterface.getInterfaceCode())){
					ptype = new Integer(param.get("pType").toString());
				}
				
				if(tar.getClass().getName().contains("JSONObject")){//JSONObject
				}else if(tar.getClass().getName().contains("JSONArray")){
					JSONArray arr =(JSONArray)tar;
					List<TPGame> oldGames = tpGameDao.selectByInterface(tpInterface.getId());
					List<TPGame> tpGames = new ArrayList<TPGame>();
					for(int i=0;i<arr.size();i++){
						boolean flag = false;
						JSONObject o=(JSONObject)arr.get(i);
						TPGame ga=new TPGame();
						ga.setGameTag(1);
						ga.setOrderId(1);
						ga.setInterfaceId(tpInterface.getId());
						for(TPResp p :tpResps){
							Field field=TPGame.class.getField(p.getField());
							if(field!=null){
								String key=p.getKey();
								if(o.containsKey(key)){
									String clazzName = field.getType().getName();
									switch(clazzName){
										case "java.lang.Integer":
										case "int":
											field.set(ga, new Integer(o.get(key).toString()));
											break;
										case "java.lang.Long":
											field.set(ga,Long.parseLong(String.valueOf(o.get(key))));
											break;
										case "java.math.BigDecimal":
											field.set(ga,new BigDecimal(String.valueOf(o.get(key))));
											break;
										case "java.lang.Double":
											field.set(ga,Double.valueOf(String.valueOf(o.get(key))));
											break;
										default:
											field.set(ga, o.get(key)+"");
											break;
									}
								}
							}
						}
						for (TPGame tpGame : oldGames) {
							if(tpGame.getGameId().intValue() == ga.getGameId().intValue()){
								flag = true;
							}
						}
						if(!flag){
							ga.setStatus(1);
							if("SDK_PlayList.ashx".equals(tpInterface.getInterfaceCode())){
								if("4".equals(param.get("type").toString()) || "5".equals(param.get("type").toString())){
									ga.setGameTag(2);
								}
							}
							ga.setPtype(ptype);
							tpGames.add(ga);
						}
					}
					if(tpGames.size() > 0){
						tpGameDao.batchInsert(tpGames);
					}
					return tpGames.size();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Map<String,Object>> selectDown() {
		return tpInterfaceDao.selectDown();
	}

}