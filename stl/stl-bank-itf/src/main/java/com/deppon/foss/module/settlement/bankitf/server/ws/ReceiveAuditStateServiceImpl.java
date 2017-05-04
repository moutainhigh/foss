/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-bank-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/bankitf/server/ws/ReceiveAuditStateServiceImpl.java
 * FILE NAME        	: ReceiveAuditStateServiceImpl.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
*/

package com.deppon.foss.module.settlement.bankitf.server.ws;


import javax.xml.ws.Holder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.inteface.domain.payment.PaymentAuditStatusRequest;
import com.deppon.foss.inteface.domain.payment.PaymentAuditStatusResponse;
import com.deppon.foss.module.settlement.bankitf.server.esb.BillPayCODAuditStateProcess;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.receiveauditstateservice.CommonException;
import com.deppon.foss.receiveauditstateservice.ReceiveAuditStateService;

/**
 * 处理代收货款审核结果WS
 * 
 * @author guxinhua
 * @date 2013-1-7 上午10:25:56
 */
public class ReceiveAuditStateServiceImpl implements ReceiveAuditStateService {

	/** 审核不通过. */
	private static final String RESULT_AUDIT_FAILED = "0";

	/** 审核通过. */
	private static final String RESULT_AUDIT_PASS = "1";
	/** 已审核过. */
	private static final String RESULT_AUDIT_PASSED = "2";

	/** 日志. */
	private static final Logger logger = LogManager
			.getLogger(BillPayCODAuditStateProcess.class);

	/** 代收货款线上汇款服务. */
	private IBillPayCODOnlineService billPayCODOnlineService;

	/** 代收货款批次服务. */
	private ICODBatchService codBatchService;
	//自定义常量
	private static final String SUCCESS_MESSAGE = "成功";

	/**
	 * 处理代收货款审核结果
	 * 
	 * @author guxinhua
	 * @param
	 * @date 2013-1-7 上午10:25:25
	 * @return
	 * @see com.deppon.foss.receiveauditstateservice.ReceiveAuditStateService#receiveAuditState(javax.xml.ws.Holder,
	 *      com.deppon.foss.inteface.domain.payment.PaymentAuditStatusRequest)
	 */
	@Override
	public PaymentAuditStatusResponse receiveAuditState(Holder<ESBHeader> esbHeader, PaymentAuditStatusRequest parameters)
			throws CommonException {
		//记录日志
		logger.info("处理代收货款审核结果开始.");
		//发送seb头信息
		ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
		// 审核结果
		PaymentAuditStatusRequest request = (PaymentAuditStatusRequest) parameters;
		//获取批次号
		String batchNumber = request.getBatchNo();
		//获取请求结果集
		String result = request.getResult();
		//获取传入参数
		String remark = request.getRemark();
		//记录日志
		logger.info("batchNumber:" + batchNumber);
		//记录日志
		logger.info("result:" + result);
		//记录日志
		logger.info("remark:" + remark);
		// 返回值
		PaymentAuditStatusResponse response = new PaymentAuditStatusResponse();
		//自定义boolean
		boolean isSuccess = true;
		//自定义常量
		String reason = SUCCESS_MESSAGE;
		try {
			// 校验代收货款批次号信息,对重复发送消息进行判断
			result = checkBatchNumber(batchNumber,result);
			//逻辑判断处理
			if (RESULT_AUDIT_PASS.equals(result)) {
				// 审核通过
				billPayCODOnlineService.processAuditSuccess(batchNumber);
			//逻辑判断处理
			} else if (RESULT_AUDIT_FAILED.equals(result)) {
				// 审核不通过
				billPayCODOnlineService.processAuditFailed(batchNumber, remark);
			}else if(RESULT_AUDIT_PASSED.equals(result)) {
				//如果之前已经审核通过，本次请求按照成功处理
				isSuccess = true;
				//result默认为true；reson默认值为成功
				reason = SUCCESS_MESSAGE;
			}else {
				// 未知类型的审核结果
				isSuccess = false;
				//处理结果
				reason = "处理代收货款审核结果异常：未知类型的审核结果";
			}
		} catch (BusinessException e) {
			//日志
			logger.error(e.getErrorCode() , e);
			//返回信息
			isSuccess = false;
			//处理结果
			reason = "处理代收货款审核结果异常："+e.getErrorCode();
			
			throw new CommonException(reason);
		}catch (Exception e) {
			//日志
			logger.error(e.getMessage() , e);
			//返回信息
			isSuccess = false;
			//处理结果
			reason = "处理代收货款审核结果异常："+e.getMessage();
			
			throw new CommonException(reason);
		}finally{
			//返回信息
			response.setBatchNo(batchNumber);
			//返回信息
			response.setIsSuccess(isSuccess);
			//返回信息
			response.setReason(reason);	
		}
		//记录日志
		logger.info("处理代收货款审核结果结束."+batchNumber);
		//返回结果
		return response;
	}
	
	/**
	 * 校验代收货款批次号是否合法
	 * @author ibm-zhuwei
	 * @date 2013-1-14 下午7:42:09
	 * @param batchNumber 批次号
	 * update by 231438改为返回String的方法，为了拿到更改为已审核过的值做后续处理
	 */
	private String checkBatchNumber(String batchNumber,String result) throws SettlementException {
		//根据批次号查询代收货款
		CODBatchEntity entity = codBatchService.queryByBatchNo(batchNumber);
		//判空
		if (entity == null) {
			throw new SettlementException("不存在该批次号数据");
		}
		// 已经审批过,不允许再审批(数据重发校验)；11-8-update by 231438 已审核成功的按审核成功处理
		if (SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_PASS.equals(entity.getStatus())
			|| SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_FAIL.equals(entity.getStatus())) {
			//throw new SettlementException(batchNumber+"该批次号已经做过审批");
			//改result的值，用于判断是否已审批过的；不抛异常，打印日志;
			result = RESULT_AUDIT_PASSED;
			logger.info("代收货款审核是已审核过的："+batchNumber);
		//判断是否已经发送银企,审核通过才验证（该批次号发送到银企，银企还未处理）
		} else if(SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING.equals(entity.getStatus())
				&&RESULT_AUDIT_PASS.equals(result)){
			throw new SettlementException(batchNumber+"该批次号发送到银企，银企还未处理");
		//判断是否发送失败
		} else if(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_FAIL.equals(entity.getStatus())){
			throw new SettlementException(batchNumber+"该批次号发送到银企失败");
		}
		return result;
	}

	/**
	 * Sets the bill pay cod online service.
	 * 
	 * @param billPayCODOnlineService
	 *            the new bill pay cod online service
	 */
	public void setBillPayCODOnlineService(
			IBillPayCODOnlineService billPayCODOnlineService) {
		this.billPayCODOnlineService = billPayCODOnlineService;
	}

	/**
	 * Sets the cod batch service.
	 * 
	 * @param codBatchService
	 *            the new cod batch service
	 */
	public void setCodBatchService(ICODBatchService codBatchService) {
		this.codBatchService = codBatchService;
	}

}
