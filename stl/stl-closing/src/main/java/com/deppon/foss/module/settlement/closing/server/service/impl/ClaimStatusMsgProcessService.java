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
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/closing/server/service/impl/ClaimStatusMsgProcessService.java
 * 
 * FILE NAME        	: ClaimStatusMsgProcessService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.crm.inteface.foss.domain.ReturnVoucherPaymentResultRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.dto.SmsParamDto;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.NotifyCustomerException;
import com.deppon.foss.module.settlement.closing.api.server.service.IClaimStatusMsgProcessService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 理赔处理消息服务.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-12-3 下午2:49:06
 */
public class ClaimStatusMsgProcessService implements
		IClaimStatusMsgProcessService {
	
	/** 银行账号后四位. */
	private static final int LASTFOUR = 4;

	/** 服务编码. */
	private static final String SERVICE_CODE = "ESB_FOSS2ESB_NOTIFY_CLAIMS_STATE";

	/** 版本号. */
	private static final String VERSION = "1.0";

	/** 业务描述. */
	private static final String BUSINESS_DESC = "通知理赔支付状态";

	/** 日志. */
	private static final  Logger logger = LogManager.getLogger(ClaimStatusMsgProcessService.class);

	/** 理赔支付状态service. */
	private IClaimStatusMsgService claimStatusMsgService;

	/** 应付单服务. */
	private IBillPayableService billPayableService;
	
	/** 付款单明细 */
	private IBillPaymentDService billPaymentDService;

	/** 组织服务. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/** 短信模板service接口. */
	private ISMSTempleteService sMSTempleteService;
	
	/** 通知客户Service */
	private INotifyCustomerService notifyCustomerService;
	
	/** 理赔支付消息处理服务 */
	private IClaimStatusMsgProcessService claimStatusMsgProcessService;

	/**
	 * 发送理赔消息.
	 *
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-26 下午4:19:40
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IClaimStatusMsgProcessService#sendPaymentMsg()
	 */
	@Override
	public void sendPaymentMsg() {

		logger.info("Service start ...");

		// 查询未发送的消息
		List<ClaimStatusMsgEntity> entitys = claimStatusMsgService.queryNotSendMsg();

		// 如果消息不为空，则发送消息
		if (CollectionUtils.isNotEmpty(entitys)) {
			// 循环理赔支付状态消息
			for (ClaimStatusMsgEntity entity : entitys) {

				try {
					// 发送理赔支付状态消息
					claimStatusMsgProcessService.sendClaimPayStatusMsg(entity);

				} catch (Exception e) {
					// 异常
					logger.error(ToStringBuilder.reflectionToString(entity));
					logger.error(e.getMessage(), e);
				}

			}
		}

		logger.info("Service end.");

	}

	/**
	 * 返回ESB请求头消息.
	 *
	 * @param waybillNum the waybill num
	 * @return the access header
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:41:17
	 */
	private AccessHeader buildHeader(String waybillNum) {
		//请求头消息
		AccessHeader header = new AccessHeader();
		header.setBusinessId(waybillNum);
		// 服务编码
		header.setEsbServiceCode(SERVICE_CODE);
		// 版本号
		header.setVersion(VERSION);
		// 业务描述
		header.setBusinessDesc1(BUSINESS_DESC);

		return header;

	}

	/**
	 * 发送理赔支付状态消息.
	 *
	 * @param entity the entity
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午3:38:18
	 * update by 231434 2315-12-29 增加客户短信提醒功能
	 */
	@Override
	@Transactional
	public void sendClaimPayStatusMsg(ClaimStatusMsgEntity entity) {

		// 判断实体不能为空
		if (entity == null) {
			//理赔支付状态消息实体为空
			throw new SettlementException("理赔支付状态消息实体为空.");
		}

		// 判断运单号不能为空
		if (StringUtils.isEmpty(entity.getWaybillNo())) {
			//理赔支付状态消息运单号为空
			throw new SettlementException("理赔支付状态消息运单号为空.");
		}

		// 判断支付结果不能为空
		if (StringUtils.isEmpty(entity.getMsgAction())) {
			//理赔支付状态消息支付结果为空
			throw new SettlementException("理赔支付状态消息支付结果为空.");
		}

		String waybillNo = entity.getWaybillNo();

		// 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
		/**
		 * Parameters:
			waybillNos 
			billTypes 
		 */
		List<BillPayableEntity> billPayables = billPayableService
				.queryByWaybillNosAndByBillTypes(
						Arrays.asList(waybillNo),
						Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));

		// 理赔应付单不存在，则抛异常
		if (CollectionUtils.isEmpty(billPayables)) {
			//理赔应付单不存在
			throw new SettlementException("理赔应付单不存在.");
		}

		// 理赔应付单不唯一，则抛异常
		if (billPayables.size() != 1) {
			//存在多个理赔应付单
			throw new SettlementException("存在多个理赔应付单.");
		}
		
		//获得billPayable
		BillPayableEntity billPayable = billPayables.get(0);
		
		//DEFECT-13350 修复查询付款单明细
//		//获取理赔付款单
//		List<BillPaymentEntity> billPayments = billPaymentService
//				.queryBillPaymentByPaymentNOs(Arrays.asList(billPayable.getPaymentNo()),
//						FossConstants.ACTIVE);
//		if(CollectionUtils.isNotEmpty(billPayments) && billPayments.size() != 1){
//			//存在多个理赔付款单
//			throw new SettlementException("存在多个理赔付款单.");
//		}
	
		//查询运单对应的付款单明细
		BillPaymentDEntity billDPayment = billPaymentDService.
				queryPaymentDEntityByWaybillNo(waybillNo,billPayable.getPaymentNo());
		
		//是否发给短信（可能存在反核销后，又重新核销）,默认未发送短信
		boolean isSendMsg = false;
		//如果发过短信，就不能再发（可能存在反核销后，又重新核销）
		List<ClaimStatusMsgEntity> msgEntitys = claimStatusMsgService
						.queryClaimStatusMsgByWaybillNO(waybillNo);
		if(CollectionUtils.isNotEmpty(msgEntitys)){
			for(ClaimStatusMsgEntity msgEntity : msgEntitys){
				if(FossConstants.YES.equals(msgEntity.getMsgStatus())){
					//消息状态为“Y”，证明已发送过短信
					isSendMsg = true;
					break;
				}
			}
		}
		
		// 构建发送消息实体
		ReturnVoucherPaymentResultRequest request = new ReturnVoucherPaymentResultRequest();

		// 运单号、部门编码、金额、客户编码、付款方式、结果、原因
		request.setWaybillNum(waybillNo);
		// 金额
		request.setAmount(billPayable.getAmount());
		// 客户编码
		request.setCustCode(billPayable.getCustomerCode());
		
		// 获取部门标杆编码
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(billPayable.getPayableOrgCode());
		// 设置UnifiedCode
		request.setDeptCode(orgEntity.getUnifiedCode());
		// 付款方式
		if(StringUtils.isNotBlank(billPayable.getPaymentCategories())){
			request.setPaymentType(billPayable.getPaymentCategories());
		}else{
			request.setPaymentType("异常收款类型！");//正常是没问题的，怕为空的时候这个状态返回不回去。这个状态crm没用到该字段
		}
		
		// 如果成功状态返回TRUE
		if (SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__PASS
				.equals(entity.getMsgAction())) {
			// 结果
			request.setPayResult(true);
		} else if (SettlementDictionaryConstants.CLAIM_STATUS_MSG__MSG_ACTION__FAIL
				.equals(entity.getMsgAction())) {
			// 结果
			request.setPayResult(false);
		}
		// 原因
		request.setReason(entity.getMsgContent());

		// 构建消息头
		AccessHeader header = buildHeader(waybillNo);
		
		logger.debug(ToStringBuilder.reflectionToString(request));
		
		try {
			// 发送消息
			ESBJMSAccessor.asynReqeust(header, request);
			
			// 更新已发送状态
			claimStatusMsgService.updateMsgStatusSended(entity.getId());
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			// 发送理赔消息失败
			throw new SettlementException("发送理赔消息失败：" + ex.getMessage());
		}
		
		//应付理赔金额
		BigDecimal payableAmount = billPayable.getAmount();
		//短信内容
		String smsContent = "";
		try{
			validaBill(billPayable, billDPayment, isSendMsg, payableAmount, smsContent);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			// 发送理赔消息失败
			throw new SettlementException("发送短信失败：" + e.getMessage());
		}
		
	}

	private void validaBill(BillPayableEntity billPayable,
			BillPaymentDEntity billDPayment, boolean isSendMsg,
			BigDecimal payableAmount, String smsContent) {
		//			BillPaymentEntity billPayment = billPayments.get(0);
					//实际理赔付款金额
					BigDecimal paymentAmount = new BigDecimal(NumberConstants.ZERO);
					if(billDPayment != null){
						paymentAmount = billDPayment.getPayAmount();
					}
					//冲销金额
					BigDecimal writeoffAmount = payableAmount.subtract(paymentAmount);
					if(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF
							.equals(billPayable.getPaymentCategories())){
						//获取短信内容(全部冲销)
						smsContent = getSmsContent(null,billPayable,
								SettlementDictionaryConstants.CLAIMS_PAYMENT_W,null);
					}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER
							.equals(billPayable.getPaymentCategories())){
						//如果付款方式为电汇
						smsContent = getSmsContent(billDPayment,billPayable,
								SettlementDictionaryConstants.CLAIMS_PAYMENT_TT,null);
					}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER
							.equals(billPayable.getPaymentCategories())){
						//如果付款方式为冲销+电汇
						smsContent = getSmsContent(billDPayment,billPayable,
								SettlementDictionaryConstants.CLAIMS_PAYMENT_WTT,writeoffAmount);
					}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__CASH
							.equals(billPayable.getPaymentCategories())){
						//如果付款方式为现金
						smsContent = getSmsContent(billDPayment,billPayable,
								SettlementDictionaryConstants.CLAIMS_PAYMENT_CH,null);
					}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH
							.equals(billPayable.getPaymentCategories())){
						//如果付款方式为冲销+现金
						smsContent = getSmsContent(billDPayment,billPayable,
								SettlementDictionaryConstants.CLAIMS_PAYMENT_WCH,writeoffAmount);
					}
					if(StringUtils.isBlank(smsContent)){
						logger.error("短信内容为空");//记录日志
						throw new SettlementException("短信内容为空");//短信内容为空
					}
					//只有未发送过短信的运单，并且应付单手机号不为空（手机号可能为空，譬如客户填的座机），才发送短信
					if(!isSendMsg && StringUtils.isNotBlank(billPayable.getCustomerPhone())){
						//发送短信
						this.sendMess(billPayable, smsContent);
					}
	}

	/**
	 * 获取短信内容
	 * @author 231434-foss-bieyexiong
	 * @date 2015-12-17 下午14:28:18
	 */
	private String getSmsContent(BillPaymentDEntity billDPayment,BillPayableEntity billPayable,String smsCode,BigDecimal writeoffAmount){
		String sms="";//返回短信
		// 模版参数
		SmsParamDto smsParamDto = new SmsParamDto();
		// 短信编码
		smsParamDto.setSmsCode(smsCode);
		smsParamDto.setMap(this.getSmsParam(billDPayment,billPayable,writeoffAmount));
		try {
			sms = sMSTempleteService.querySmsByParam(smsParamDto);
		} catch (SMSTempleteException e) {//捕获异常
			logger.error("短信内容为空" + e.getErrorCode(), e);//记录日志
			throw new SettlementException("短信内容为空" + e.getMessage());//短信内容为空
		}
		if (StringUtil.isBlank(sms)) {
			logger.error("没有对应的短信模版");//记录日志
			throw new SettlementException("没有对应的短信模版");//没有对应的短信模版
		}
		return sms;
	}
	
	/**
	 * 替换短信内容参数
	 * @author 231434-foss-bieyexiong
	 * @date 2015-12-17 下午14:35:29
	 */
	private Map<String,String> getSmsParam(BillPaymentDEntity billDPayment,BillPayableEntity billPayable,BigDecimal writeoffAmount){
		Map<String,String> map = new HashMap<String,String>();
		
		//运单号
		map.put("waybillNo", billPayable.getWaybillNo());
		//理赔金额(全冲销)
		map.put("amount", billPayable.getAmount().toString());
		//收款人
		map.put("customerName", billPayable.getCustomerName());
		
		//银行账户尾号(后4位)
		String accountNo = billPayable.getAccountNo();
		if(StringUtils.isNotBlank(accountNo)){
			map.put("accountNo", accountNo.substring(accountNo.length()-LASTFOUR));			
		}
		
		//冲销金额
		if(writeoffAmount != null){
			map.put("writeoffAmount", writeoffAmount.toString());
		}
		
		//付款金额（冲销剩余金额）
		if(billDPayment != null){
			map.put("paymentAmount", billDPayment.getPayAmount().toString());
		}
		
		//理赔合计金额
		map.put("payableAmount", billPayable.getAmount().toString());
		
		return map;
	}
	
	/**
	 * 替换短信内容参数
	 * @author 231434-foss-bieyexiong
	 * @date 2015-12-17 下午15:21:06
	 */
	private void sendMess(BillPayableEntity billPayable,String smsContent){
		logger.info("短信发送开始!");//记录日志
		try {
			NotificationEntity notificationEntity = new NotificationEntity();
			// 运单号
			notificationEntity.setWaybillNo(billPayable.getWaybillNo());
			// 通知类型为短信通知
			notificationEntity.setNoticeType(NotifyCustomerConstants.NOTIFY_TYPE_SMS_NOTICE);	
			// 通知内容 
			notificationEntity.setNoticeContent(smsContent);
			// 操作人
			notificationEntity.setOperator(billPayable.getCreateUserName());
			// 操作人编码
			notificationEntity.setOperatorNo(billPayable.getCreateUserCode());
			// 操作部门
			notificationEntity.setOperateOrgName(billPayable.getPayableOrgName());
			// 操作部门编码
			notificationEntity.setOperateOrgCode(billPayable.getPayableOrgCode());
			// 收款人
			notificationEntity.setConsignee(
					StringUtils.isBlank(billPayable.getPayeeName())?
							billPayable.getCustomerName() : billPayable.getPayeeName());
			// 手机号
			notificationEntity.setMobile(billPayable.getCustomerPhone());
			// 操作时间
			notificationEntity.setOperateTime(new Date());
			// 模块名称
			notificationEntity.setModuleName(NotifyCustomerConstants.SMS_STL_CLAIMS_PAYMENT);
			// 发送短信
			notifyCustomerService.sendMessage(notificationEntity);
			logger.error("--短信发送成功!");//记录日志
		} catch (NotifyCustomerException e) {//捕获异常
			// 异常处理
			logger.error("--短信发送失败!" + e.getErrorCode(), e);//记录日志
			// 发送理赔消息失败
			throw new SettlementException("短信发送失败：" + e.getMessage());
		}
	}

	/**
	 * Sets the claim status msg service.
	 *
	 * @param claimStatusMsgService the new claim status msg service
	 */
	public void setClaimStatusMsgService(
			IClaimStatusMsgService claimStatusMsgService) {
		this.claimStatusMsgService = claimStatusMsgService;
	}
	
	/**
	 * Sets the bill payable service.
	 *
	 * @param billPayableService the new bill payable service
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * Sets the org administrative info service.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
		this.sMSTempleteService = sMSTempleteService;
	}

	public void setNotifyCustomerService(
			INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	public void setClaimStatusMsgProcessService(
			IClaimStatusMsgProcessService claimStatusMsgProcessService) {
		this.claimStatusMsgProcessService = claimStatusMsgProcessService;
	}

	public void setBillPaymentDService(IBillPaymentDService billPaymentDService) {
		this.billPaymentDService = billPaymentDService;
	}
	
}
