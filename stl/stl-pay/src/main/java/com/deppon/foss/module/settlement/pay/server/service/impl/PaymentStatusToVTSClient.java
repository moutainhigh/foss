package com.deppon.foss.module.settlement.pay.server.service.impl;

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
import com.deppon.foss.module.settlement.common.api.shared.dto.BillInfoEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentStatusToVTSClient;
/**
 * (FOSS向VTS回传字段需求)同步接口
 * @author 395982
 *
 */
public class PaymentStatusToVTSClient implements IPaymentStatusToVTSClient {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PaymentStatusToVTSClient.class);
	/**
	 * ESB服务端编码
	 */
	public static String ESB_SERVICE_CODE = "ESB_FOSS2ESB_FOSS_PAYMENTSTATUS_FOR_VTS";
	/**
	 * 查询ESB地址信息
	 */
	private IFossConfigEntityService fossConfigEntityService;

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public void sendPaymentStatusToVTS(
			BillInfoEntity billInfoEntity) {
		logger.info("信息加载开始......");
		if(null != billInfoEntity){
			//打印参数
			logger.info("运单号个数为：" + billInfoEntity.getBillIds().size());
			logger.info("工作流号为：" + billInfoEntity.getPaymentNumber());
			logger.info("汇款状态为：" + billInfoEntity.getPaymentStatus());
			try {
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("type", "updatePaymentStatusByFoss");
				obj.put("requestEntity", billInfoEntity);
				String js = JSONObject.toJSON(obj).toString();
				RequestEntity entity = new StringRequestEntity(js,
						"application/json", "UTF-8");
				// 根据ESB_CODE查到ESB地址信息
				FossConfigEntity fossConfigEntity = fossConfigEntityService
						.queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
				String esbBTPSAddr = fossConfigEntity.getEsbAddr();
				logger.info("查询到esb地址为" + esbBTPSAddr);
				logger.info("VTS从FOSS请求的ESB地址为" + esbBTPSAddr);
				// 构造postMethod实例
				PostMethod postMethod = new PostMethod(esbBTPSAddr);
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
				FossToVtsTail dto = object.getObject("responseEntity",
						FossToVtsTail.class);
				logger.info("调用VTS服务端接口后响应的结果...");
				logger.info("是否成功：" + dto.isSuccess());
				logger.info("失败原因为: " + dto.getMsg());
			} catch (Exception e) {
				logger.info("VTS从FOSS同步失败,异常信息为: " + e.getMessage());
			}
		}else{
			logger.info("FOSS到VTS信息为空");
		}

	}

}


