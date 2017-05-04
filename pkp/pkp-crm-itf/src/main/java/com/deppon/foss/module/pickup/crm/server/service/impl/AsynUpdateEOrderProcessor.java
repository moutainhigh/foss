package com.deppon.foss.module.pickup.crm.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.domain.crm2foss.UpdateEOrderRequest;
import com.deppon.esb.pojo.domain.crm2foss.UpdateEOrderResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.server.dao.IModifEwaybillOrderRecordDao;
import com.deppon.foss.module.pickup.order.api.server.service.IOrderTaskHandleService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class AsynUpdateEOrderProcessor implements IProcess {

	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AsynUpdateEOrderProcessor.class);
	/**
	 * 订单处理service
	 */
	private IOrderTaskHandleService orderTaskHandleService;

	private IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao;

	@SuppressWarnings("finally")
	@Override
	public Object process(Object req) throws ESBBusinessException {
		UpdateEOrderRequest payload = (UpdateEOrderRequest) req;
		LOGGER.info("【更新电子运单"+(payload != null ? payload.getOrderNumber() : "")+"开始】"
				+ ReflectionToStringBuilder.toString(payload));
		UpdateEOrderResponse response = new UpdateEOrderResponse();
		response.setUpdateEOrderRequest(payload);
		// 空则返回
		if (payload == null) {
			response.setIfSuccess(false);
			response.setErrorMeg("传入参数为空，更新失败");
			return response;
		}
		try {
			// 调用添加接货订单接口
			orderTaskHandleService.modifyEwaybillOrder(payload);
			response.setIfSuccess(true);
			response.setOrderNumber(payload.getOrderNumber());
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			// 设置失败返回结果
			response.setIfSuccess(false);
			response.setOrderNumber(payload.getOrderNumber());
			response.setErrorMeg(e.getMessage());
			this.addExceptionLog(payload, e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 设置失败返回结果
			response.setIfSuccess(false);
			response.setOrderNumber(payload.getOrderNumber());
			response.setErrorMeg(e.getMessage());
			this.addExceptionLog(payload, e.getMessage());
		}finally{
			LOGGER.info("【更新电子运单"+payload.getOrderNumber()+"结束】"
					+ ReflectionToStringBuilder.toString(response));
			return response;
		}
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		UpdateEOrderRequest payload = (UpdateEOrderRequest) req;
		UpdateEOrderResponse response = new UpdateEOrderResponse();
		response.setUpdateEOrderRequest(payload);
		response.setOrderNumber(payload.getOrderNumber());
		response.setIfSuccess(false);
		response.setErrorMeg("未知错误");
		LOGGER.info("【更新电子运单"+payload.getOrderNumber()+"失败-未知错误】");
		addExceptionLog(payload, "未知错误");
		return response;
	}

	private void addExceptionLog(UpdateEOrderRequest req, String msg) {
		ModifyEwaybillOrderLogEntity logEntity = new ModifyEwaybillOrderLogEntity();
		logEntity.setId(UUIDUtils.getUUID());
		logEntity.setOrderNo(req.getOrderNumber());
		logEntity.setWaybillNo(req.getWaybillNumber());
		logEntity.setOperateType(DispatchOrderStatusConstants.OPERATE_MODIFIY
				.equals(req.getModifyType()) ? FossConstants.OPERATE_MODIFY
				: FossConstants.OPERATE_DELETE);
		logEntity.setIsSuccess(FossConstants.NO);
		logEntity.setOperateRecord("异常信息["+msg+"]\r\n\r\n"+req.toString());
		logEntity.setCreateTime(new Date());
		modifEwaybillOrderRecordDao.insertSelective(logEntity);
	}

	public void setOrderTaskHandleService(
			IOrderTaskHandleService orderTaskHandleService) {
		this.orderTaskHandleService = orderTaskHandleService;
	}

	public void setModifEwaybillOrderRecordDao(
			IModifEwaybillOrderRecordDao modifEwaybillOrderRecordDao) {
		this.modifEwaybillOrderRecordDao = modifEwaybillOrderRecordDao;
	}

}
