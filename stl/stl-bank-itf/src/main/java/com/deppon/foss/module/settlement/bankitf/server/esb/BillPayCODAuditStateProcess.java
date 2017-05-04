/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-bank-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/bankitf/server/esb/BillPayCODAuditStateProcess.java
 * FILE NAME        	: BillPayCODAuditStateProcess.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
*/

package com.deppon.foss.module.settlement.bankitf.server.esb;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payment.PaymentAuditStatusRequest;
import com.deppon.esb.inteface.domain.payment.PaymentAuditStatusResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;

/**
 * 接收代收货款审核结果.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-11-19 上午11:57:21
 */
public class BillPayCODAuditStateProcess implements IProcess {

	/** 审核通过. */
	private static final String RESULT_AUDIT_PASS = "0";

	/** 审核不通过. */
	private static final String RESULT_AUDIT_FAILED = "1";

	/** 日志. */
	private static final Logger logger = LogManager
			.getLogger(BillPayCODAuditStateProcess.class);

	/** 代收货款综合服务. */
	private ICodCommonService codCommonService;

	/** 代收货款线上汇款服务. */
	private IBillPayCODOnlineService billPayCODOnlineService;

	/** 代收货款批次服务. */
	private ICODBatchService codBatchService;

	/**
	 * Sets the cod common service.
	 *
	 * @param codCommonService the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the bill pay cod online service.
	 *
	 * @param billPayCODOnlineService the new bill pay cod online service
	 */
	public void setBillPayCODOnlineService(
			IBillPayCODOnlineService billPayCODOnlineService) {
		this.billPayCODOnlineService = billPayCODOnlineService;
	}

	/**
	 * Sets the cod batch service.
	 *
	 * @param codBatchService the new cod batch service
	 */
	public void setCodBatchService(ICODBatchService codBatchService) {
		this.codBatchService = codBatchService;
	}

	/**
	 * 接收代收货款审核结果.
	 *
	 * @param obj the obj
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午4:42:20
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object obj) throws ESBBusinessException {
		// 审核结果
		PaymentAuditStatusRequest request = (PaymentAuditStatusRequest) obj;
		//获取接口参数
		String batchNumber = request.getBatchNo();
		//获取接口参数
		String result = request.getResult();
		//获取接口参数
		String remark = request.getRemark();
		//记录日志
		logger.info("batchNumber:" + batchNumber);
		//记录日志
		logger.info("result:" + result);
		//记录日志
		logger.info("remark:" + remark);
		// 返回值
		PaymentAuditStatusResponse response = new PaymentAuditStatusResponse();
		try {
			// 审核通过
			if (RESULT_AUDIT_PASS.equals(result)) {
				//调用审核成功方法
				processAuditSuccess(batchNumber);
			}

			// 审核不通过
			else if (RESULT_AUDIT_FAILED.equals(result)) {
				//调用审核失败方法
				processAuditFailed(batchNumber, remark);
			}
			// 未知类型的审核结果
			else {
				//抛出异常
				throw new SettlementException("未知类型的审核结果");
			}
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			throw new ESBBusinessException(e);
		}
		//返回结果
		return response;
	}

	/**
	 * 异常处理.
	 *
	 * @param req the req
	 * @return the object
	 * @throws ESBBusinessException the eSB business exception
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:33:22
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		//异常处理
		logger.error("接收代收货款审核出错:" + req);
		//返回结果
		return null;
	}

	/**
	 * 处理审核结果失败.
	 *
	 * @param batchNumber the batch number
	 * @param remark the remark
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:20:08
	 */
	private void processAuditFailed(String batchNumber, String remark)
			throws SettlementException {

		// 获取银企默认用户信息
		CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();
		// 按批次号查询代收货款
		List<CODEntity> codList = codCommonService.queryByBatchNumber(batchNumber);
		//循环处理
		for (CODEntity entity : codList) {
			// 代收货款状态、批次号、申请时间、申请人、退款路径
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
			//设置属性值
			entity.setBatchNumber(null);
			//设置属性值
			entity.setTusyorgRfdApptime(null);
			//设置属性值
			entity.setTusyorgRfdAppUserCode(null);
			//设置属性值
			entity.setTusyorgRfdAppUserName(null);
			//设置属性值
			entity.setRefundPath(null);
			// 更新代收货款状态
			codCommonService.updateCODBankBatchBackStatus(entity, currentInfo);

		}

		// 更新代收货款批次状态
		CODBatchDto entity = new CODBatchDto();

		// 批次号、状态、失败原因
		entity.setBatchNo(batchNumber);
		//设置属性值
		entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_FAIL);
		//设置属性值
		entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS);
		//设置属性值
		entity.setFailNotes(remark);
		// 更新批次状态
		codBatchService.updateCODBatchStatus(entity);
	}

	/**
	 * 处理审核成功.
	 *
	 * @param batchNumber the batch number
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午9:32:58
	 */
	@Transactional
	private void processAuditSuccess(String batchNumber)throws SettlementException {
		// 获取银企默认用户信息
		CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();
		// 按批次号查询代收货款
		List<CODEntity> codList = codCommonService
				.queryByBatchNumber(batchNumber);

		// 对代收货款循环处理
		for (CODEntity entity : codList) {
			billPayCODOnlineService.generateCODPaymentBill(entity, currentInfo);
		}
		// 更新代收货款批次状态
		CODBatchDto entity = new CODBatchDto();

		// 批次号、状态、失败原因
		entity.setBatchNo(batchNumber);
		//设置属性值
		entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_PASS);
		//设置属性值
		entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS);

		// 更新批次状态
		codBatchService.updateCODBatchStatus(entity);
	}
}
