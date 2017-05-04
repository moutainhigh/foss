package com.deppon.foss.module.pickup.qms.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.qms.service.IGrandGoodsAbnormalHandleService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IGrandGoodAbnormalService;

/**
 * FOSS对接QMS，重大货物异常处理
 * @author 231434-FOSS-bieyexiong
 * @date 2015-7-14 上午8:20:14
 */
public class GrandGoodsAbnormalHandleService implements IGrandGoodsAbnormalHandleService{

	/**
	 * 返回值（失败）
	 */
	private final static String FAILURE = "0";
	
	/**
	 * 运单号
	 */
	private static final String WAYBILL_NO = "waybillNo";
	
	/**
	 * 重大货物异常服务
	 */
	@Resource(name = "grandGoodAbnormalService")
	private IGrandGoodAbnormalService grandGoodAbnormalService ;
	
	/**
	 * 重大货物异常处理
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-7-14 上午8:26:35
 	 */
	@Override
	public @ResponseBody String grandGoodAbnormalHandle
			(HttpServletResponse servletResponse,@RequestBody String grandGoodAbnormalJson) {
		
		JSONObject jsonObj = JSONObject.parseObject(grandGoodAbnormalJson);
		String response = "";
		try{
			//返回值
			response = grandGoodAbnormalService.grandGoodAbnormalHandle(grandGoodAbnormalJson);
			return response;
		}catch(Exception e){
			response = this.getResponseJson
					((String)jsonObj.get(WAYBILL_NO),FAILURE,"重大货物异常处理失败：系统异常！");
			return response;
		}
	}
	
	/**
	 * 获取返回信息
 	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-7-14 上午10:30:35
 	 */
	public String getResponseJson(String waybillNo,String resultCode,String message){
		Map<String,Object> resMap = new HashMap<String,Object>();
		//设置返回信息
		if(StringUtils.isNotBlank(waybillNo)){
			resMap.put("waybillNo",waybillNo);
		}
		resMap.put("resultCode",resultCode);
		resMap.put("message",message);
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	}

}
