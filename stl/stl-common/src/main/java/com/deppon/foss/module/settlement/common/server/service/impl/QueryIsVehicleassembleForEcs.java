package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryIsVehicleassembleForEcs;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponseVehicleassemble;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 根据运单号  查询配载单数
 * @author 325369
 * @date   2016-9-6
 */
public class QueryIsVehicleassembleForEcs implements IQueryIsVehicleassembleForEcs {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(QueryIsVehicleassembleForEcs.class);
	
	/**
	 * ESB提供的服务端编码（ESB同事提供的）
	 */
	public static String PZD_SERVICE_CODE = "/ECS_ESB2ECS_IS_MAKE_HANDOVERBILL_TRACK";
	
	/**
	 * ECS服务端地址(本地联调用)
	 */
	//public static String LOCAL_SERVICE_CODE = "http://10.224.102.143:8081/tfr-opt-service/v1/ecs/tfr/queryTransferIsMakeBill";
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public String queryIsVehicleassembleForEcs(String wayBillNo) {
		// TODO Auto-generated method stub
		logger.info("从FOSS结算到悟空系统请求：查询配载单数开始..."); 
		String result = "";
		String url = "";
		System.out.println("编码为：" + PZD_SERVICE_CODE);
		// 根据服务端的ESB_CODE查到esb地址
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(PZD_SERVICE_CODE);
		if (null != configEntity && !StringUtils.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
			logger.info("查询到的ESB地址为：" + url);
		} else {
			logger.error("\n\n查询配载单数从FOSS到悟空系统请求接口-读取esb地址有误:\n\n");
			throw new SettlementException("查询配载单数从FOSS到悟空系统请求接口读取esb地址有误!");
		}
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("wayBillNo", wayBillNo);
			JSONObject jsonObject =  JSONObject.fromObject(map);
			String js = jsonObject.toString();
			RequestEntity entity = new StringRequestEntity(js,"application/json", "UTF-8");
			// 构造PostMethod的实例
			PostMethod postMethod = new PostMethod(url);
			
			//PostMethod postMethod = new PostMethod(LOCAL_SERVICE_CODE);//和WK本地联调用的
			
			postMethod.setRequestEntity(entity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			HttpClient httpClient = new HttpClient();
			// 设置编码格式
			httpClient.getParams().setContentCharset("UTF-8");
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			logger.info("客户端方法执行的结果状态:" + statusCode);
			String responseBody = "";
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			logger.info("返回值信息为："+responseBody);
			// 将返回值转换成实体类
			JSONObject jsonOb = JSONObject.fromObject(responseBody);
			if(jsonOb != null){
				Object object = JSONObject.toBean(jsonOb, ResponseVehicleassemble.class);
				ResponseVehicleassemble responseVehicleassemble = (ResponseVehicleassemble) object;
				logger.info("是否成功："+responseVehicleassemble.getStatus() +",失败原因为: "+responseVehicleassemble.getExMsg());
				if(responseVehicleassemble.getStatus() == 1){
					result = responseVehicleassemble.getData();
				}else{
					logger.info("从FOSS到悟空系统请求，查询配载单数接口异常信息：" + responseVehicleassemble.getExMsg()); 
					throw new SettlementException("从FOSS到悟空系统请求，查询配载单数失败,异常信息为: "+ responseVehicleassemble.getExMsg());
				}
			}
		}catch(Exception e){
			logger.info("从FOSS到悟空系统请求，查询配载单数接口异常信息：" + e.getMessage()); 
			throw new SettlementException("从FOSS到悟空系统请求，查询配载单数失败,异常信息为: "+ e.getMessage());
		}
		return result;
	}

}
