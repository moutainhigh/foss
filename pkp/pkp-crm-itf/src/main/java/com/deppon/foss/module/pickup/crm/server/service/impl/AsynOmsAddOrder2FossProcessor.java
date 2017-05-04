package com.deppon.foss.module.pickup.crm.server.service.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.AsynOmsGoodsBillReceiveRequest;
import com.deppon.esb.inteface.domain.crm.AsynOmsGoodsBillReceiveResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;

public class AsynOmsAddOrder2FossProcessor implements IProcess {

	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AsynOmsAddOrder2FossProcessor.class);
	/**
	 * 订单处理service
	 */
	private IOrderTaskHandleService orderTaskHandleService;

	private static final String SERVERCODE = "FOSS_ESB2FOSS_ABOUT_CAR";
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		AsynOmsGoodsBillReceiveRequest payload = (AsynOmsGoodsBillReceiveRequest) req;
		LOGGER.info("【"+(payload != null ? payload.getOrderNo():"")+"约车开始】"
				+ ReflectionToStringBuilder.toString(payload));
		AsynOmsGoodsBillReceiveResponse response = new AsynOmsGoodsBillReceiveResponse();
		// 空则返回
		if (payload == null) {
			response.setServerCode(SERVERCODE);
			response.setResult(false);
			response.setReason("约车订单为空！");
			LOGGER.info("【约车订单为空】" + ReflectionToStringBuilder.toString(response));
			return response;
		} else {
			try {
				// 调用添加接货订单接口
				orderTaskHandleService.addOmsPickupOrder(payload);
				// 成功标志
				response.setServerCode(SERVERCODE);
				response.setOrderNumber(payload.getOrderNo());
				response.setResult(true);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage(), e);
				// 设置失败返回结果
				response.setServerCode(SERVERCODE);
				response.setOrderNumber(payload.getOrderNo());
				response.setResult(false);
				response.setReason(e.getErrorCode());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				// 设置失败返回结果
				response.setServerCode(SERVERCODE);
				response.setOrderNumber(payload.getOrderNo());
				response.setResult(false);
				response.setReason(e.getMessage());
			}
		}
		LOGGER.info("【"+payload.getOrderNo()+"约车结束】"
				+ ReflectionToStringBuilder.toString(response));
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		AsynOmsGoodsBillReceiveRequest payload = (AsynOmsGoodsBillReceiveRequest) req;
		AsynOmsGoodsBillReceiveResponse response = new AsynOmsGoodsBillReceiveResponse();
		response.setServerCode(SERVERCODE);
		response.setOrderNumber(payload.getOrderNo());
		response.setResult(false);
		response.setReason("未知错误");
		LOGGER.info("【"+payload.getOrderNo()+"约车失败-未知错误】");
		return response;
	}

	public void setOrderTaskHandleService(
			IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	} 

}
