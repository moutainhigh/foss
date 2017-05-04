/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODFailService.java
 * 
 * FILE NAME        	: BillPayCODFailService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODFailService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AuditResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 汇款失败审核确认服务.
 *
 * @author dp-zengbinwen
 * @date 2012-10-18 下午2:07:40
 */
public class BillPayCODFailService implements IBillPayCODFailService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODFailService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 核销单服务. */
	private IBillWriteoffService billWriteoffService;

	/** The bill payment service. */
	private IBillPaymentService billPaymentService;

	/** 注入应付单服务接口. */
	private IBillPayableService billPayableService;
	
	/**
	 * 付款单查询service接口实现
	 */
	private IPaymentQueryService paymentQueryService;
	
	/**在线通知*/
    private IMessageService messageService;

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * 汇款失败审核查询.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:42:23
	 */
	public List<CODDto> queryBillCODPayFailed(int offset, int limit)
			throws SettlementException {

		LOGGER.trace("Service start,offset:" + offset + ",limit:" + limit);

		// 设置查询DTO
		BillCODPayFailedQueryDto queryDto = getBillCODPayFailedQueryDto();
		queryDto.setLimit(limit);
		queryDto.setOffset(offset);

		// 查询结果
		List<CODDto> queryResult = codCommonService
				.queryBillCODPayFailed(queryDto);

		LOGGER.trace("Service end");

		return queryResult;
	}

	/**
	 * 汇款失败审核查询大小，合计金额
	 *
	 * @return the int
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午5:26:36
	 */
	public CODDto queryBillCODPayFailedSize() throws SettlementException {

		LOGGER.trace("Service start");

		// 设置查询DTO并查询
		BillCODPayFailedQueryDto queryDto = getBillCODPayFailedQueryDto();
		CODDto cod = codCommonService.queryBillCODPayFailedSize(queryDto);

		LOGGER.trace("Service end.");

		return cod;

	}

	/**
	 * 设置查询参数.
	 *
	 * @return the bill cod pay failed query dto
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午10:02:43
	 */
	private BillCODPayFailedQueryDto getBillCODPayFailedQueryDto() {

		// 新增查询DTO
		BillCODPayFailedQueryDto queryDto = new BillCODPayFailedQueryDto();

		// 是否有效、代收货款状态
		queryDto.setActive(FossConstants.ACTIVE);

		// 退款失败申请、反汇款成功
		List<String> status = new ArrayList<String>();
		status.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
		status.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);

		queryDto.setStatuses(status);

		// 应付单是否有效、单据类型
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		// 退款路径
		queryDto.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);
		
		//核销类型
		queryDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);

		return queryDto;

	}

	/**
	 * 处理付款失败确认.
	 *
	 * @param entityIds the entity ids
	 * @param auditResult the audit result
	 * @param currentInfo the current info
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-24 上午10:21:19
	 */
	@Transactional
	public void processPaymentFailed(List<String> entityIds,
			AuditResultEnum auditResult, CurrentInfo currentInfo)
			throws SettlementException {

		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("内部错误，代收货款记录ID为空！");
		}

		LOGGER.info("开始处理代收货款失败审核处理结果");

		CODEntity entity = null;
		String status = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = entity.getStatus();

			// 如果不是反汇款成功或退款失败申请，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS
					.equals(status)
					&& !SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION
							.equals(status)) {
				throw new SettlementException(
						"运单号为{0}的代收货款状态不为反汇款成功或退款失败，不能进行审核操作",
						entity.getWaybillNo());
			}

			// 如果通过,更新代收货款状态
			if (AuditResultEnum.PASSED.equals(auditResult)) {
				entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE);

				// 付款失败申请审核通过后，需要反核销付款冲应付
				writeBackBillWriteoff(entity, currentInfo);
				
				BillPayableEntity payable = codCommonService.getBillPayableEntity(entity);				
				
				// 取消冻结状态、冻结时间、冻结人
				payable.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
				payable.setFrozenTime(null);
				payable.setFrozenUserCode(null);
				payable.setFrozenUserName(null);
				
				// 进行反写应付单支付状态、付款金额、付款单号等操作
				billPayableService.cancelFrozenBillPayable(payable, currentInfo);
				
				
				entity.setModifyUserName(currentInfo.getEmpName());
				entity.setModifyUserCode(currentInfo.getEmpCode());
				entity.setModifyTime(new Date());

				// 更新代收货款信息新
				codCommonService
						.updatePaymentFailedAuditStatus(entity, currentInfo);				

				//如果为即日退、三日退、审核退 则提示应付部门及时处理
				if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(entity.getCodType())
						||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(entity.getCodType())
						||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(entity.getCodType())){
					InstationJobMsgEntity entiy = new InstationJobMsgEntity();
					//发送人和发送部门信息
					entiy.setSenderCode(currentInfo.getEmpCode());
					entiy.setSenderName(currentInfo.getEmpName());
					entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
					entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
					//设置为代收货款消息
					entiy.setMsgType(MessageConstants.MSG_TYPE__CODPAYFAILD);
					//接受方式为组织
					entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					//设置接收部门信息
					entiy.setReceiveOrgCode(entity.getPayableOrgCode());
					//发送方式为
					entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
					entiy.setReceiveOrgName(entity.getPayableOrgName());
					//设置
					entiy.setContext("你部门含有代收货款的单据："+entity.getWaybillNo()+"汇款失败，请及时处理！");
					messageService.createBatchInstationMsg(entiy);
					}	
			}

			// 如果退回，根据代收货款状态更改状态
			else if (AuditResultEnum.RETURNED.equals(auditResult)) {

				// 反汇款成功
				if (SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS
						.equals(status)) {
					entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNED);
				}

				// 退款失败申请
				else if (SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION
						.equals(status)) {
					entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNING);
				}
				
				entity.setModifyUserName(currentInfo.getEmpName());
				entity.setModifyUserCode(currentInfo.getEmpCode());
				entity.setModifyTime(new Date());

				// 更新代收货款信息新
				codCommonService
						.updatePaymentFailedReturnedStatus(entity, currentInfo);
			}

			// 其它 抛异常
			else {
				throw new SettlementException("未识别的代收货款审核结果");
			}

			LOGGER.info("结束处理代收货款失败审核处理结果");
		}
	}

	/**
	 * 代收货款付款冲应付反核销.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-28 上午9:49:31
	 */
	public void writeBackBillWriteoff(CODEntity entity, CurrentInfo currentInfo) {

		// 获取代收货款对应的应付单
		BillPayableEntity billPayable = codCommonService
				.getBillPayableEntity(entity);
		
		// 查询出对应的付款单
		/*这种查询方式过时。
		 * List<BillPaymentEntity> payments = .queryBillPaymentBySourceBillNOs(
		Arrays.asList(new String[] { billPayable.getPayableNo() }),
		SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT,
		FossConstants.ACTIVE);*/
		BillPaymentQueryDto queryDto = new BillPaymentQueryDto();
		queryDto.setSourceBillNos(Arrays.asList(new String[] { billPayable.getPayableNo() }));
		queryDto.setActive(FossConstants.ACTIVE);
		List<BillPaymentEntity> payments = paymentQueryService.queryBillPaymentListBySourceBillNo(queryDto);
				
		// 找不到对应的付款单，则抛出异常
		if (CollectionUtils.isEmpty(payments)) {
			throw new SettlementException("找不到运单号为【" + entity.getWaybillNo()
					+ "】对应的付款单");
		}

		// 存在多个付款单时抛出异常
		if (payments.size() > 1) {
			throw new SettlementException("运单号为【" + entity.getWaybillNo()
					+ "】的代收货款存在多个有效的付款单");
		}

		BillPaymentEntity payment = payments.get(0);
		
		//红冲付款单 2013-02-26
		this.billPaymentService.writeBackBillPayment(payment, currentInfo);

		// 调用核销单接口反核销付款冲应付
		billWriteoffService.writeBackBillWriteoffByPayment(
				payment.getPaymentNo(), currentInfo);
	}

	/**
	 * Sets the cod common service.
	 *
	 * @param codCommonService the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the bill writeoff service.
	 *
	 * @param billWriteoffService the new bill writeoff service
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * Sets the bill payment service.
	 *
	 * @param billPaymentService the new bill payment service
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	
	/**
	 * Sets the bill payable service.
	 *
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @param paymentQueryService
	 */
	public void setPaymentQueryService(IPaymentQueryService paymentQueryService) {
		this.paymentQueryService = paymentQueryService;
	}
	
	
}
