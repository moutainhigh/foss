package com.deppon.foss.module.settlement.agency.server.service.impl;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.deppon.foss.module.settlement.agency.api.server.service.ICheckLdpExternalBillClient;
import com.deppon.foss.module.settlement.agency.api.shared.domain.RequestLdpExternalBill;
import com.deppon.foss.module.settlement.agency.api.shared.domain.ResponseLdpExternalBill;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 
 * @author 325369
 * @date  2016-07-05 下午19:36:42
 * 校验运单号和快递代理在外发单中是否存在接口
 */
public class CheckLdpExternalBillClient implements ICheckLdpExternalBillClient {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CheckLdpExternalBillClient.class);
	
	/**
	 * ESB提供的服务端编码（ESB同事提供的）
	 */
	public static String ESB_SERVICE_CODE = "/ECS_ESB2ECS_VALIDATE_OUTSOURCEBILL";
	
	/**
	 * ESB提供的客户端编码（ESB同事提供的）
	 */
	public static String ESB_CLIENT_CODE = "/ESB_FOSS2ESB_VALIDATE_OUTSOURCEBILL";
	
	/**
	 * WK服务端地址(本地联调用)
	 */
	//public static String WK_SERVICE_CODE = "http://10.224.228.141:8088/tfr-common-service/v1/ecs/tfr/outward/outwardService/queryOutsourceBillByNoAgenComNo";
	
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public boolean sendLdpExternalBillMsgToWK(String waybillNo,
			String agencyCompanyCode) {
		// TODO Auto-generated method stub
		logger.info("快递代理从FOSS到悟空系统请求：校验外发单开始..."); 
		String url = "";
		System.out.println("编码为：" + ESB_SERVICE_CODE);
		// 根据服务端的ESB_CODE查到esb地址
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
		if (null != configEntity && !StringUtils.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
			logger.info("查询到的ESB地址为：" + url);
		} else {
			logger.error("\n\n快递代理从FOSS到悟空系统请求接口-读取esb地址有误:\n\n");
			throw new SettlementException("快递代理从FOSS到悟空系统请求接口读取esb地址有误!");
		}
		try{
			RequestLdpExternalBill req = new RequestLdpExternalBill();
			req.setWaybillNo(waybillNo);
			req.setAgentCompanyCode(agencyCompanyCode);
			//req.setAgentCompanyCode("LDP20221");
			JSONObject jsonObject =  JSONObject.fromObject(req);
			String js = jsonObject.toString();
			RequestEntity entity = new StringRequestEntity(js,"application/json", "UTF-8");
			// 构造PostMethod的实例
			PostMethod postMethod = new PostMethod(url);
			
			//PostMethod postMethod = new PostMethod(WK_SERVICE_CODE);//和WK本地联调用的
			
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
				Object object = JSONObject.toBean(jsonOb, ResponseLdpExternalBill.class);
				ResponseLdpExternalBill responseLdpExternalBill = (ResponseLdpExternalBill) object;
				logger.info("是否成功："+responseLdpExternalBill.getStatus() +",失败原因为: "+responseLdpExternalBill.getExMsg());
				if(responseLdpExternalBill.getStatus() == 1){
					return true;
				}else{
					return false;
				}
			}
		}catch(Exception e){
			logger.info("快递代理从FOSS到悟空系统请求接口异常信息：" + e.getMessage()); 
			throw new SettlementException("快递代理从FOSS到悟空系统请求失败,异常信息为: "+ e.getMessage());
		}
		return false;
	}
	
}
