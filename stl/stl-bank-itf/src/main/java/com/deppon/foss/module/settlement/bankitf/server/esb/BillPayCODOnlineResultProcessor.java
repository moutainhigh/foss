/**
 * Copyright 2013 STL TEAM
 * PROJECT NAME	: stl-bank-itf
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/bankitf/server/esb/BillPayCODOnlineResultProcessor.java
 * FILE NAME        	: BillPayCODOnlineResultProcessor.java
 * AUTHOR			: FOSS结算系统开发组
 * HOME PAGE		: http://www.deppon.com
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 */

package com.deppon.foss.module.settlement.bankitf.server.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payment.RemittanceResponseEntity;
import com.deppon.esb.inteface.domain.payment.RemittanceStatus;
import com.deppon.esb.inteface.domain.payment.RemittanceStatusNotification;
import com.deppon.esb.inteface.domain.payment.RemittanceStatusResponse;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineResultService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultEnum;

/**
 * 
 * 接收线上汇代收货款汇款结果
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-12 下午2:29:00
 */
public class BillPayCODOnlineResultProcessor implements IProcess {

	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(BillPayCODOnlineResultProcessor.class);

	/**
	 * 代收货款线上汇款结果服务
	 */
	private IBillPayCODOnlineResultService billPayCODOnlineResultService;
	
	private IOperatingLogService operatingLogService;

	/**
	 * 
	 * 接收代收货款汇款结果
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-12 下午2:29:58
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@SuppressWarnings("finally")
	@Override
	public Object process(Object response) throws ESBBusinessException {
		//记录日志
		logger.info("开始接口代收货款汇款结果.");
		
		RemittanceStatusResponse remittanceStatusResponse = new RemittanceStatusResponse();
		String errMsg = null;
		try {
			//获取接口参数
			RemittanceStatusNotification notification = (RemittanceStatusNotification) response;
			//判空
			if (notification == null) {
				throw new SettlementException("代收货款汇款数据为空");
			}
			// 对结果循环做处理,将结果放入DTO中
			List<BillCODOnlineResultDto> results = new ArrayList<BillCODOnlineResultDto>();
			//循环处理
			for (RemittanceStatus status : notification.getRemittanceStatus()) {
				// 构建DTO并设置值
				//声明dto
				BillCODOnlineResultDto dto = new BillCODOnlineResultDto();
				//设置属性值
				dto.setWaybillNo(status.getWayBillNo());
				//设置属性值
				dto.setBatchNo(status.getBatchNum());
				//设置属性值
				dto.setFailNotes(status.getReason());
				dto.setBankAccount(status.getBankAccount());
				
				// 成功
				if (status.isResult()) {
					if (StringUtils.isBlank(status.getBankAccount())) {
						throw new SettlementException(status.getWayBillNo()+"代收货款公司退款账号为空");
					}
					
					//设置属性值
					dto.setResult(BillCODOnlineResultEnum.SUCCESS);
				}
				// 失败
				else {
					//设置属性值
					dto.setResult(BillCODOnlineResultEnum.FAILURE);
				}
				
				logger.info("代收货款汇款结果--运单"+status.getWayBillNo()+"批次号"+status.getBatchNum()+"状态"+status.isResult()+"失败信息"+status.getReason());
				//设置属性值
				results.add(dto);
				
				RemittanceResponseEntity entity = new RemittanceResponseEntity();
				entity.setBatchNum(status.getBatchNum());
				entity.setWayBillNo(status.getWayBillNo());
				entity.setResult(true);// 默认处理成功
				remittanceStatusResponse.getRemittanceResponseList().add(entity);
			}

			// 获取银企默认用户信息
			CurrentInfo currentInfo = SettlementUtil.getYQCurrentInfo();

			// 调用接口对单据做处理
			billPayCODOnlineResultService.processPayResultService(results,currentInfo);
		} catch (SettlementException e) {
			//记录日志
			logger.error("接收代收货款汇款结果:"+e.getErrorCode() , e);
			errMsg = e.getErrorCode();
			//抛出异常
			//throw new ESBBusinessException(e);
		} catch (BusinessException e) {
			//记录日志
			logger.error("接收代收货款汇款结果:"+e.getErrorCode(), e);
			errMsg = e.getErrorCode();
			//抛出异常
			//throw new ESBBusinessException(e);
		} catch (Exception e) {
			//记录日志
			logger.error("接收代收货款汇款结果:"+e.getMessage(), e);
			errMsg = e.getMessage();
			//抛出异常
			//throw new ESBBusinessException(e);
		}finally{
			if(errMsg!=null){
				this.changeRemittanceResponseFailure(remittanceStatusResponse.getRemittanceResponseList(),errMsg);
			}
			//记录日志
			logger.info("结束接口代收货款汇款结果.");
			//返回结果
			return remittanceStatusResponse;
		}
	}
	
	/**
	 * 设置返回集合状态为处理失败
	 * @param list
	 */
	private void changeRemittanceResponseFailure(List<RemittanceResponseEntity> list,String notes){
		for (RemittanceResponseEntity remittanceResponseEntity : list) {
			remittanceResponseEntity.setResult(false);
		}
		
		final int NumberFive = 500;
		OperatingLogEntity log = new OperatingLogEntity();
		log.setOperateBillNo(list.get(0).getBatchNum());
		log.setOperateBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		log.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__RETREAT);
		log.setNotes(notes.length()>NumberFive ? notes.substring(0, NumberFive) : notes);// 异常信息大于500字符 截取
		
		operatingLogService.addOperatingLog(log, SettlementUtil.getYQCurrentInfo());
	}

	/**
	 * 
	 * 异常处理
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-12 下午3:10:56
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		//记录日志
		logger.error("接收线上汇代收货款汇款异常errorHandler抛出:" + req);
		//返回结果
		return null;
	}

	/**
	 * @param billPayCODOnlineResultService
	 */
	public void setBillPayCODOnlineResultService(
			IBillPayCODOnlineResultService billPayCODOnlineResultService) {
		this.billPayCODOnlineResultService = billPayCODOnlineResultService;
	}

	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

}
