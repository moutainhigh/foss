package com.deppon.foss.module.pickup.oms.server.service.impl;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.AsynGoodsBillReceiveRequest;
import com.deppon.esb.inteface.domain.crm.AsynGoodsBillReceiveResponse;
import com.deppon.esb.pojo.domain.oms2foss.AsynOmsOrderRequest;
import com.deppon.esb.pojo.domain.oms2foss.AsynOmsOrderResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICrmOrderJMSService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IOmsOrderService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.OmsOrderEntity;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;

public class AsynOmsOrder2FossProcessor implements IProcess {

	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AsynOmsOrder2FossProcessor.class);
	
	/**
	 * 订单处理service
	 */
	private IOmsOrderService omsOrderService;
	
	/**
	 * 记录零担电子运单程序的日志
	 */
	private IWaybillProcessLogDao waybillProcessLogDao;
	
	private ICrmOrderJMSService crmOrderJMSService;
	
	/**
	 * 同步接口服务编码
	 */
	private static final String SERVERCODE = "FOSS_ESB2FOSS_SYNC_ORDER_ABOUT";
	
	/**
	 * 同步订单/取消订单
	 */
	private static final String CANCEL_ORDER = "1";
	
	private static final String ASYN_ORDER = "0";
	
	
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		AsynOmsOrderRequest request = (AsynOmsOrderRequest) req;
		String requestContent = ReflectionToStringBuilder.toString(request);
		LOGGER.info("Oms同步订单至FOSS,开始下单："+ requestContent);
		AsynOmsOrderResponse  response = new AsynOmsOrderResponse();
		String responseContent = "";
		Date currentDate = new Date();
		String orderNo = "";
		String waybillNo = "";
		// 空则返回
		if (request == null) {
			response.setIsSuccess(false);
			response.setServiceCode(SERVERCODE);
			response.setErrorMeg("Oms传至FOSS订单为空！");
			responseContent = ReflectionToStringBuilder.toString(response);
			waybillProcessLogDao.saveLog(responseContent, orderNo, waybillNo, currentDate, WaybillConstants.LTLEWAYBILL_ASYN_OMS_ORDER_RESPONSE_LOG, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE,null);
			return response;
		} else if(StringUtils.isEmpty(request.getWaybillNo())||StringUtils.isEmpty(request.getOrderNo())){
			response.setIsSuccess(false);
			response.setServiceCode(SERVERCODE);
			response.setErrorMeg("Oms传至FOSS订单号或运单号为空！");
			responseContent = JSONObject.toJSONString(response);
			waybillProcessLogDao.saveLog(responseContent, orderNo, waybillNo, currentDate, WaybillConstants.LTLEWAYBILL_ASYN_OMS_ORDER_RESPONSE_LOG, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE,null);
			return response;
		} 
		try {
			/**
			 * 记录请求日志
			 */
			orderNo = request.getOrderNo();
			waybillNo = request.getWaybillNo();
			waybillProcessLogDao.saveLog(requestContent, orderNo, waybillNo, currentDate, WaybillConstants.LTLEWAYBILL_ASYN_OMS_ORDER_REQUEST_LOG, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS,null);
			/**
			 * OMS和FOSS可能有部分字段的值需要进行转换，后期可能需要和OMS人员进行字段值的调整。
			 */
			OmsOrderEntity omsOrder = new OmsOrderEntity();
			BeanUtils.copyProperties(omsOrder , request);
			omsOrder.setCreateTime(currentDate);
			omsOrder.setModifyTime(currentDate);
			String operationType = request.getOperationType();
			if(StringUtils.isNotEmpty(operationType)&&StringUtils.equals(ASYN_ORDER,operationType.trim())){
				String exceptionMessage = null;
				String exceptionCode = null;
				String waybillStatus = null;
				try{
					omsOrderService.asynOmsOrder(omsOrder);
					LOGGER.info("生成代补录orderNo = " + orderNo+" , waybillNo = " + waybillNo + " , info = " + ReflectionToStringBuilder.toString(omsOrder));
				}catch(BusinessException be){
					exceptionMessage = ExceptionUtils.getFullStackTrace(be);
					exceptionCode = be.getErrorCode();
					waybillStatus = WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
					crmOrderJMSService.pushOmsOrderInfoStatust(orderNo , waybillNo , WaybillConstants.LTLEWAYBILL_GENERATE_WAYBILL_PENDING_LOG , null , waybillStatus, null, exceptionCode,exceptionMessage);
				}catch(Exception e){
					exceptionMessage = ExceptionUtils.getFullStackTrace(e);
					exceptionCode = WaybillValidateException.EWAYBILL_UNKOWN_EXCEPTION;
					waybillStatus = WaybillConstants.WAYBILL_PICTURE_TYPE_RETURN;
					crmOrderJMSService.pushOmsOrderInfoStatust(orderNo , waybillNo , WaybillConstants.LTLEWAYBILL_GENERATE_WAYBILL_PENDING_LOG , null , waybillStatus, null, exceptionCode,exceptionMessage);
				}
			}else if(StringUtils.isNotEmpty(operationType)&&StringUtils.equals(CANCEL_ORDER, operationType.trim())){
				/**
				 * 取消订单
				 */
				omsOrderService.cancelOrder(omsOrder);
			}
			
			/**
			 * 同步成功返回信息
			 */
			response.setServiceCode(SERVERCODE);
			response.setOrderNo(request.getOrderNo());
			response.setWaybillNo(response.getWaybillNo());
			response.setIsSuccess(true);
			response.setErrorMeg("");
			/**
			 * 记录同步订单成功返回信息
			 */
			responseContent = ReflectionToStringBuilder.toString(response);
			waybillProcessLogDao.saveLog(responseContent, orderNo, waybillNo, new Date(), WaybillConstants.LTLEWAYBILL_ASYN_OMS_ORDER_RESPONSE_LOG, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_SUCCESS,"");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// 设置失败返回结果
			response.setIsSuccess(false);
			response.setServiceCode(SERVERCODE);
			response.setErrorMeg(e.getMessage());
			response.setWaybillNo(request.getWaybillNo());
			response.setOrderNo(request.getOrderNo());
			/**
			 * 记录失败返回日志  
			 */
			responseContent = ReflectionToStringBuilder.toString(response);
			waybillProcessLogDao.saveLog(responseContent, orderNo, waybillNo, new Date(), WaybillConstants.LTLEWAYBILL_ASYN_OMS_ORDER_RESPONSE_LOG, WaybillConstants.LTLEWAYBILL_PROCESS_LOG_FAILURE,"");
		}
		return response;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		AsynGoodsBillReceiveRequest payload = (AsynGoodsBillReceiveRequest) req;
		AsynGoodsBillReceiveResponse response = new AsynGoodsBillReceiveResponse();
		response.setServerCode(SERVERCODE);
		response.setOrderNumber(payload.getOrderNumber());
		response.setResult(false);
		response.setReason("未知错误");
		LOGGER.info("【"+payload.getOrderNumber()+"约车失败-未知错误】");
		return response;
	}

	public void setOmsOrderService(IOmsOrderService omsOrderService) {
		this.omsOrderService = omsOrderService;
	}

	public void setWaybillProcessLogDao(IWaybillProcessLogDao waybillProcessLogDao) {
		this.waybillProcessLogDao = waybillProcessLogDao;
	}

	public void setCrmOrderJMSService(ICrmOrderJMSService crmOrderJMSService) {
		this.crmOrderJMSService = crmOrderJMSService;
	}
	
	
}
