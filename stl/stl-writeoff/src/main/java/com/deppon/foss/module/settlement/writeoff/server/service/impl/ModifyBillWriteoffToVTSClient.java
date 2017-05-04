package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.vtsWriteomodifyBillWriteOffDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.VtsToFossResultDto;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IModifyBillWriteoffToVTSClient;

/**
 * 更改单核销报表（核销状态）同步接口
 * 
 * @author 331556 fanjingwei
 * @date 2016-05-12 13:40:41
 */
public class ModifyBillWriteoffToVTSClient implements
		IModifyBillWriteoffToVTSClient {
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ModifyBillWriteoffToVTSClient.class);
	/**
	 * ESB提供的服务端编码
	 */

	public static String ESB_SERVICE_CODE = "TPS_ESB2TPS_VERIFICATIONREPORT_FOR_VTS";

	/**
	 * ESB提供的客户端编码
	 */

	public static String ESB_CLIENT_CODE = "ESB_FOSS2ESB_VERIFICATIONREPORT_FOR_VTS";

	/**
	 * VTS服务端地址（本地联调用）
	 */
	public static String VTS_SERVICE_CODE = "";

	/**
	 * 查询ESB地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public void sendWriteoffWrapToVTS(
			vtsWriteomodifyBillWriteOffDto vtsWriteomodifyBillWriteOffDto) {
		logger.info("信息加载开始...");
		if (vtsWriteomodifyBillWriteOffDto != null) {
			try {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("type", "verificationReport");
				obj.put("requestEntity", vtsWriteomodifyBillWriteOffDto);
				// String js = JSONObject.fromObject(obj).toString();
				String js = JSONObject.toJSON(obj).toString();
				RequestEntity entity = new StringRequestEntity(js,
						"application/json", "UTF-8");

				// 根据ESB_CODE查到ESB地址信息
				FossConfigEntity fossConfigEntity = fossConfigEntityService
						.queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
				String esbBTPSAddr = fossConfigEntity.getEsbAddr();
				logger.info("查询到esb地址为" + esbBTPSAddr);
				logger.info("VTS从FOSS请求的ESB地址为" + esbBTPSAddr + ESB_CLIENT_CODE);
				// 构造postMethod实例
				PostMethod postMethod = new PostMethod(esbBTPSAddr
						+ ESB_CLIENT_CODE);

				// PostMethod postMethod = new PostMethod(VTS_SERVICE_CODE);//
				// 和VTS本地联调用的

				postMethod.setRequestEntity(entity);
				postMethod.addRequestHeader("Content-Type",
						"application/json;charset=UTF-8");
				HttpClient httpClient = new HttpClient();
				// 设置编码格式
				httpClient.getParams().setContentCharset("UTF-8");
				// 执行postMethod
				int statusCode = httpClient.executeMethod(postMethod);
				logger.info("客户端方法执行的结果状态:" + statusCode);
				String responseBody = "";
				// 获取返回值
				responseBody = postMethod.getResponseBodyAsString();
				logger.info("返回值信息为：" + responseBody);

				// 解析传入json，封装实体类
				JSONObject object = JSONObject.parseObject(responseBody);
				VtsToFossResultDto dto = object.getObject("responseEntity",
						VtsToFossResultDto.class);

				logger.info("调用VTS服务端接口后响应的结果...");
				logger.info("是否成功：" + dto.isSuccess());
				logger.info("失败原因为: " + dto.getMsg());

			} catch (Exception e) {
				throw new SettlementException("VTS从FOSS同步失败,异常信息为: "
						+ e.getMessage());
			}
		} else {
			logger.info("FOSS到VTS信息为空");
		}

	}

}
