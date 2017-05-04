/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-bank-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/bankitf/server/esb/BillPayCODOnlineSendCallBackProcess.java
 * FILE NAME        	: BillPayCODOnlineSendCallBackProcess.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */

package com.deppon.foss.module.settlement.bankitf.server.esb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.custom.yq.refund.SysRefundbillResponse;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODBatchEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODBatchDto;

/**
 * 更新代收货款状态
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-12-24 下午8:54:04
 */
public class BillPayCODOnlineSendCallBackProcess implements ICallBackProcess {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager
			.getLogger(BillPayCODOnlineSendCallBackProcess.class);

	/**
	 * 代收货款综合服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 代收货款批次服务
	 */
	private ICODBatchService codBatchService;

	/**
	 * 回调方法,请尽量把所有异常都抛出来
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午4:32:25
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object response) throws ESBException {
		// 记录日志
		logger.info("发送银企代收货款回调方法开始.");
		// 返回结果
		SysRefundbillResponse result = (SysRefundbillResponse) response;
		// 记录日志
		logger.info(result.getBatchNum());
		// 记录日志
		logger.info(result.isIsSuccess());
		// 记录日志
		logger.info(result.getReason());
		// 声明变量
		String reason = null;
		String batchNumber = null;

		try {
			// 如果不成功，需要更新代收货款数据
			boolean success = result.isIsSuccess();
			// 获取批次号
			batchNumber = result.getBatchNum();
			// 获取处理结果
			reason = result.getReason();
			// 判空
			if (!checkBatchNumber(batchNumber)) {
				return;
			}
			// 判断是否成功
			if (!success) {
				// 错误处理
				errorProcess(batchNumber, reason);
			} else {
				// 如果成功，需要更新代收货款批次状态
				// 更新代收货款批次状态
				CODBatchDto entity = new CODBatchDto();

				// 批次号、状态、失败原因
				entity.setBatchNo(batchNumber);
				// 设置属性值
				entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS);
				// 设置属性值
				entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);
				// 设置属性值
				entity.setFailNotes(reason);

				// 更新批次状态
				codBatchService.updateCODBatchStatus(entity);
			}
		} catch (BusinessException e) {
			// 记录日志
			logger.error("发送银企代收货款回调方法异常." + e.getErrorCode(), e);
			// 错误处理
			errorProcess(batchNumber, "发送银企代收货款回调方法异常." + e.getErrorCode());
		} catch (Exception e) {
			// 记录日志
			logger.error("发送银企代收货款回调方法异常." + e.getMessage(), e);
			// 错误处理
			errorProcess(batchNumber, "发送银企代收货款回调方法异常." + e.getMessage());
		}
		// 记录日志
		logger.info("发送银企代收货款回调方法结束.");
	}

	/**
	 * 发送银企失败，异常处理
	 * 
	 * @author @author guxinhua
	 * @param
	 * @date 2013-2-2 下午1:57:47
	 * @return
	 * @throws ESBBusinessException
	 */
	private void errorProcess(String batchNumber, String reason) throws ESBBusinessException {
		try {
			// 获取银企默认用户信息
			CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();

			// 按批次号查询代收货款
			List<CODEntity> codList = codCommonService.queryByBatchNumber(batchNumber);

			// 银企返回消息中存在重复消息，截取处理
			int index = StringUtils.indexOf(reason, "！");
			if (index != -1) {
				reason = reason.substring(0, index + 1);
			}

			logger.error("批次号:" + batchNumber + "，发送失败退回异常:" + reason);

			// 循环处理
			for (CODEntity entity : codList) {
				// 代收货款状态、批次号、申请时间、申请人、退款路径
				entity.setStatus(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
				// 设置属性值
				// entity.setBatchNumber(null);
				// 设置属性值
				entity.setTusyorgRfdApptime(null);
				// 设置属性值
				entity.setTusyorgRfdAppUserCode(null);
				// 设置属性值
				entity.setTusyorgRfdAppUserName(null);
				// 设置属性值
				entity.setRefundPath(null);
				entity.setMergeCode(null);
				// 为了添加发送异常到cod日志显示
				entity.setPayeeRelationship("批次号:" + batchNumber + "，发送失败退回异常:" + reason);
				// 更新代收货款状态
				codCommonService.updateCODBankBatchBackStatus(entity, currentInfo);
			}
			// 更新代收货款批次状态
			CODBatchDto entity = new CODBatchDto();
			// 批次号、状态、失败原因
			entity.setBatchNo(batchNumber);
			// 设置属性值
			entity.setStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_FAIL);
			// 设置属性值
			entity.setOldStatus(SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);
			// 设置属性值
			entity.setFailNotes(reason);
			// 更新批次状态
			codBatchService.updateCODBatchStatus(entity);
		} catch (Exception e) {
			// 异常处理记录日志
			logger.error("发送银企代收货款回调方法异常." + e.getMessage(), e);
			throw new ESBBusinessException(e);
		}

	}

	/**
	 * 校验代收货款批次号状态是否合法
	 * 
	 * @author ibm-zhuwei
	 * @date 2013-1-14 下午7:42:09
	 * @param batchNumber
	 *            批次号
	 */
	private boolean checkBatchNumber(String batchNumber) {
		// 声明合法标志位
		boolean bool = true;
		// 调用接口查询
		CODBatchEntity entity = codBatchService.queryByBatchNo(batchNumber);
		// 判空
		if (entity == null) {
			// 设置为不合法
			bool = false;
			// 抛出异常
			throw new SettlementException("不存在该批次号数据");
		}

		// 已经审批过,不允许再审批(数据重发校验)
		if (SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_PASS.equals(entity
				.getStatus())
				|| SettlementDictionaryConstants.COD_BATCH__STATUS__BANK_AUDIT_FAIL.equals(entity
						.getStatus())) {
			// 设置为不合法
			bool = false;
			// 记录日志
			logger.error("发送银企代收货款回调方法异常：该批次号已经做过审批");
		} else if (SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_SUCCESS.equals(entity
				.getStatus())) {
			// 设置为不合法
			bool = false;
			// 记录日志
			logger.error("发送银企代收货款回调方法异常：该批次号已成功发送到银企，不能重复。");
		} else if (SettlementDictionaryConstants.COD_BATCH__STATUS__SEND_FAIL.equals(entity
				.getStatus())) {
			// 设置为不合法
			bool = false;
			// 记录日志
			logger.error("发送银企代收货款回调方法异常：该批次号发送到银企已失败");
		}
		// 返回标志位
		return bool;
	}

	/**
	 * 异常处理
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:36:13
	 * @param errorResponse
	 * @throws ESBException
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {

		ErrorResponse response = (ErrorResponse) errorResponse;

		// 错误处理
		errorProcess(response.getBusinessId(), "接收线上汇代收货款汇款,发送银企代收货款回调异常errorHandler抛出:" + errorResponse);
		// 记录日志
		logger.error("接收线上汇代收货款汇款,发送银企代收货款回调异常errorHandler抛出:" + errorResponse);
	}

	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param codBatchService
	 */
	public void setCodBatchService(ICODBatchService codBatchService) {
		this.codBatchService = codBatchService;
	}

}
