package com.deppon.foss.module.settlement.common.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IClientSendVtsPayableCheck;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestPayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ResponsePayableCheckEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.RequestPayableCheckDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 218392 zhangyongxue
 * @date 2016-06-22 11:08:15
 * VTS整车项目：FOSS付款的时候，校验单号+来源单号（合同编码）
 * 1.单号是否作废
 * 2.单号+合同编码是否唯一
 * 3.合同编码是否存在
 */
public class ClientSendVtsPayableCheck implements IClientSendVtsPayableCheck{
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ClientSendVtsPayableCheck.class);
	
	/**
	 * ESB提供的服务端编码（ESB同事提供的）
	 * VTS的服务端编码
	 */
	public static String ESB_SERVICE_CODE = "/TPS_ESB2TPS_CARRIAGE_CONTRACT";
	
	/**
	 * ESB提供的客户端编码（ESB同事提供的）
	 * FOSS这边客户端编码
	 */
	public static String ESB_CLIENT_CODE = "/ESB_FOSS2ESB_CARRIAGE_CONTRACT";
	
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public ResponsePayableCheckEntity sendVtsPayableCheck(List<RequestPayableCheckEntity> requestPayableCheckEntity) {
		logger.info("校验VTS的单号和合同编码开始...");
	   try{
		   
		   RequestPayableCheckDto requestPayableCheckDto = new RequestPayableCheckDto();
		   requestPayableCheckDto.setRequestEntity(requestPayableCheckEntity);
		   requestPayableCheckDto.setType("contractOfCarriageService");
		   
		   String questString = JSONObject.toJSON(requestPayableCheckDto).toString();
		   
			System.out.println("JSON字符串为：" + questString);
			RequestEntity entity = new StringRequestEntity(questString,"application/json", "UTF-8");
			
			//这段提交的时候再解开
			// 根据服务端的ESB_CODE查到esb地址
			FossConfigEntity fossConfig = fossConfigEntityService.queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
			String esbTPSAddr = fossConfig.getEsbAddr();
			//构造PostMethod的实例
			PostMethod postMethod = new PostMethod(esbTPSAddr+ ESB_CLIENT_CODE);
//		    /**
//		     * 本地测试：服务端地址写死
//		     */
//			 PostMethod postMethod = new PostMethod("http://10.224.198.16:8081/tps/webservice/tpsFossSynscInfoRest/receiveFossRestFulMethod");
//		    
		    postMethod.setRequestEntity(entity);
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			//定义HttpClien 客户端
		    HttpClient httpClient = new HttpClient();

			httpClient.getParams().setContentCharset("UTF-8");
			int statuCode = httpClient.executeMethod(postMethod);
			logger.info("客户端方法执行的结果状态:" + statuCode);
			String responseBody = "";
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			logger.info("返回值信息为："+responseBody);
			// 将返回值转换成实体类ReceiveTPSResponseEntity
			
			//转换请求
			JSONObject object = (JSONObject) JSONObject.parseObject(responseBody);
			ResponsePayableCheckEntity responseEntity = JSONObject.toJavaObject(object, ResponsePayableCheckEntity.class);
			
			return responseEntity;
			
		}catch(Exception e){
			
			throw new SettlementException("去VTS校验单号+合同编码，返回的异常信息为: "+ e.getMessage());
			
		}
	}

}
