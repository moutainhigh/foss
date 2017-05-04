package com.deppon.foss.module.settlement.common.server.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IAbnormalCargoExamineVtsCheck;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillApprovalDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * FOSS系统根据QMS系统异常货处置审批是否结束来判断
 * 
 * @author zhang 347069
 * @date 2016-9-22
 *  
 */
public class AbnormalCargoExamineVtsCheck implements IAbnormalCargoExamineVtsCheck {
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbnormalCargoExamineVtsCheck.class);

	/**
	 * ESB提供的服务端编码（ESB同事提供的） VTS的服务端编码
	 */
	public static String ESB_SERVICE_CODE = "/QMS_ESB2QMS_QUERY_TICKET_STATUS";

	/**
	 * ESB提供的客户端编码（ESB同事提供的） FOSS这边客户端编码
	 */
	public static String ESB_CLIENT_CODE = "/ESB_CUBC2ESB_QUERY_TICKET_STATUS";

	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public AbnormalBillApprovalDto sendVtsPayableCheck(String paycode) throws IOException {
		logger.info("从QMS获取");
		//try {
			// 设置请求发送的JSON
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("paycode", paycode);
			String json = jsonObject.toString();
			RequestEntity entity = new StringRequestEntity(json,"application/json", "UTF-8");
			
			// 根据服务端的ESB_CODE查到esb地址
			FossConfigEntity fossConfig = fossConfigEntityService
					.queryFossConfigEntityByServerCode(ESB_CLIENT_CODE);
			String esbAddr = fossConfig.getEsbAddr();			//测试用日常IP
			//String IP = "http://192.168.67.12:8180/esb2/rs";
			// 构造PostMethod的实例
			PostMethod postMethod = new PostMethod(esbAddr + ESB_CLIENT_CODE);
			postMethod.setRequestEntity(entity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			// 定义HttpClient客户端
			HttpClient httpClient = new HttpClient();
			// 设置编码格式
			httpClient.getParams().setContentCharset("UTF-8");
			// 执行postMethod
			int statuCode = httpClient.executeMethod(postMethod);
			logger.info("客户端方法执行的结果状态:" + statuCode);
			String responseBody = "";
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			logger.info("返回值信息为：" + responseBody);
			// 将返回值转换成实体
			// 转换请求
			JSONObject object = (JSONObject) JSONObject.parseObject(responseBody);
			AbnormalBillApprovalDto responseEntity = JSONObject.toJavaObject(object, AbnormalBillApprovalDto.class);
			logger.info("查出的信息:" + responseEntity);
			return responseEntity;
		/*} catch (Exception e) {
			throw new SettlementException("去QMS获取异常货处置审批，返回的异常信息为: " + e.getMessage());
		}*/
	}
}