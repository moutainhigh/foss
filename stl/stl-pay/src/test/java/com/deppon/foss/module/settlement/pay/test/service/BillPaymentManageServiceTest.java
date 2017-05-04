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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/BillPaymentManageServiceTest.java
 * 
 * FILE NAME        	: BillPaymentManageServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;

import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.server.service.impl.BillPaymentManageService;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.PayTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 测试付款服务
 * @author 045738-foss-maojianqiang
 * @date 2012-12-4 下午3:26:51
 */
public class BillPaymentManageServiceTest extends BaseTestCase{

	/**
	 * 获取日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BillPaymentManageService.class);

	@Autowired
	private IBillPaymentManageService billPaymentManageService;
	@Autowired
	private IBillPaymentService billPaymentService;
	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IBillPayableService billPayableService;
	@Autowired
	private IBillPaymentPayService billPaymentPayService;
	@Autowired
	private IOperatingLogService operatingLogService;
	
	/**
	 * 测试审核功能
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午3:30:43
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Rollback(true)
	@Test
	@Ignore
	public void testAuditPaymentOrderBill(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = PayTestUtil.getCurrentInfo();
			//插入应付单
			BillPayableEntity payableEntity = getBillPayableEntity();
			billPayableService.addBillPayable(payableEntity, currentInfo);
			Map map = getBillPaymentEntity(payableEntity);
			
			BillPaymentEntity paymentEntity = (BillPaymentEntity) map.get("paymentEntity");
			List<BillPaymentAddDto> addDtoList = (List<BillPaymentAddDto>) map.get("addDtoList");
			//模拟录入付款单需要传入参数
			billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, currentInfo);
			BillPayableEntity newPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(newPayableEntity.getPaymentNo());
			String opinions = "测试审核功能！";
			billPaymentManageService.auditPaymentOrderBill(paymentNos, opinions, currentInfo);
			List<BillPaymentEntity> paymentList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			//循环遍历
			for(BillPaymentEntity en:paymentList){
				logger.info("审核状态："+en.getAuditStatus());
				List<OperatingLogEntity> logList =operatingLogService.queryByOperateBillNo(en.getPaymentNo());
				for(OperatingLogEntity log:logList){
					logger.info("日志记录："+log.getOperateBillNo()+","+log.getOperateType()+","+log.getOperateBillType());
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	
	}
	
	/**
	 * 测试反审核功能
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午3:30:43
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Rollback(true)
	@Test
	@Ignore
	public void testReverseAuditPaymentOrderBill(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = PayTestUtil.getCurrentInfo();
			//插入应付单
			BillPayableEntity payableEntity = getBillPayableEntity();
			billPayableService.addBillPayable(payableEntity, currentInfo);
			Map map = getBillPaymentEntity(payableEntity);
			
			BillPaymentEntity paymentEntity = (BillPaymentEntity) map.get("paymentEntity");
			List<BillPaymentAddDto> addDtoList = (List<BillPaymentAddDto>) map.get("addDtoList");
			//模拟录入付款单需要传入参数
			billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, currentInfo);
			BillPayableEntity newPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(newPayableEntity.getPaymentNo());
			//审核
			String opinions = "测试审核功能";
			billPaymentManageService.auditPaymentOrderBill(paymentNos, opinions, currentInfo);
			List<BillPaymentEntity> auditList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			for(BillPaymentEntity audit:auditList){
				logger.info(audit.getAuditStatus()+",意见"+audit.getAuditOpinion());
			}
			String reverseOpinions = "测试反审核功能！";
			billPaymentManageService.reverseAuditPaymentOrder(paymentNos, reverseOpinions, currentInfo);
			List<BillPaymentEntity> paymentList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			//循环遍历
			for(BillPaymentEntity en:paymentList){
				logger.info("反审核状态："+en.getAuditStatus()+"意见"+en.getAuditOpinion());
				List<OperatingLogEntity> logList =operatingLogService.queryByOperateBillNo(en.getPaymentNo());
				for(OperatingLogEntity log:logList){
					logger.info("日志记录："+log.getOperateBillNo()+","+log.getOperateType()+","+log.getOperateBillType());
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 测试作废功能
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午3:30:43
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Rollback(true)
	@Test
	@Ignore
	public void testinvalidPaymentOrder(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = PayTestUtil.getCurrentInfo();
			//插入应付单
			BillPayableEntity payableEntity = getBillPayableEntity();
			billPayableService.addBillPayable(payableEntity, currentInfo);
			Map map = getBillPaymentEntity(payableEntity);
			
			BillPaymentEntity paymentEntity = (BillPaymentEntity) map.get("paymentEntity");
			List<BillPaymentAddDto> addDtoList = (List<BillPaymentAddDto>) map.get("addDtoList");
			//模拟录入付款单需要传入参数
			billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, currentInfo);
			BillPayableEntity newPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(newPayableEntity.getPaymentNo());
			//审核
			String opinions = "测试审核功能";
			billPaymentManageService.auditPaymentOrderBill(paymentNos, opinions, currentInfo);
			List<BillPaymentEntity> auditList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			for(BillPaymentEntity audit:auditList){
				logger.info(audit.getAuditStatus()+",意见"+audit.getAuditOpinion());
			}
			
			String reverseOpinions = "测试反审核功能！";
			billPaymentManageService.reverseAuditPaymentOrder(paymentNos, reverseOpinions, currentInfo);
			//作废
			String invlidOpinions = "测试作废功能";
			currentInfo.getUser().getEmployee().setEmpCode("000122");
			
			UserEntity user = new UserEntity();
			EmployeeEntity employee = new EmployeeEntity();
			employee.setEmpCode("00012311");
			employee.setEmpName("张三1");
			user.setEmployee(employee);
			user.setUserName("zhangsan1");

			OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
			dept.setCode("1");
			dept.setName("德邦物流");

			CurrentInfo currentInfoNew = new CurrentInfo(user, dept);
			
			
			billPaymentManageService.invalidPaymentOrder(paymentNos, invlidOpinions, currentInfoNew);
			List<BillPaymentEntity> paymentList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			logger.info("有效付款单条数："+paymentList.size());
			List<BillPaymentEntity> noList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.NO);
			for(BillPaymentEntity invalid:noList){
				logger.info("付款单："+invalid.getPaymentNo()+","+invalid.getActive()+","+invalid.getIsInit()+","+invalid.getIsRedBack()+","+invalid.getVersionNo());
			}
			//查询应付单
			BillPayableEntity invalidPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			logger.info("应付单："+invalidPayableEntity.getPayStatus()+","+invalidPayableEntity.getPaymentNo()+","+invalidPayableEntity.getPaymentAmount()+","+invalidPayableEntity.getPaymentNotes());
			List<OperatingLogEntity> logList =operatingLogService.queryByOperateBillNo(noList.get(0).getPaymentNo());
			for(OperatingLogEntity log:logList){
				logger.info("日志记录："+log.getOperateBillNo()+","+log.getOperateType()+","+log.getOperateBillType());
			}
			
//			String reverseOpinions = "测试反审核功能！";
//			billPaymentManageService.reverseAuditPaymentOrder(paymentNos, reverseOpinions, currentInfo);
			//List<BillPaymentEntity> paymentList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			
			//List<BillPaymentEntity> noList =billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.NO);
			//循环遍历
//			for(BillPaymentEntity en:paymentList){
//				logger.info("反审核状态："+en.getAuditStatus()+"意见"+en.getAuditOpinion());
//				List<OperatingLogEntity> logList =operatingLogService.queryByOperateBillNo(en.getPaymentNo());
//				for(OperatingLogEntity log:logList){
//					logger.info("日志记录："+log.getOperateBillNo()+","+log.getOperateType()+","+log.getOperateBillType());
//				}
//			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 封装付款单头和明细，给录入付款单用
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-4 下午3:56:00
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map getBillPaymentEntity(BillPayableEntity payableEntity) {
		Map map = new HashMap();
		//录入付款单
		BillPaymentEntity paymentEntity = new BillPaymentEntity();
		paymentEntity.setCustomerCode(payableEntity.getCurrencyCode());
		paymentEntity.setCustomerName(payableEntity.getCustomerName());
		paymentEntity.setPaymentOrgCode(payableEntity.getPayableOrgCode());
		paymentEntity.setPaymentOrgName(payableEntity.getPayableOrgName());
		paymentEntity.setPaymentCompanyName(payableEntity.getPayableComName());
		paymentEntity.setPaymentCompanyCode(payableEntity.getPayableComCode());
		paymentEntity.setPaymentType(SettlementDictionaryConstants.OTHER_REVENUE__PAYMENT_TYPE__CASH);
		//OTHER_REVENUE__PAYMENT_TYPE__CREDIT月结
		paymentEntity.setAmount(new BigDecimal("500"));
		paymentEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);//应付单
		paymentEntity.setNotes("测试数据！");
		
		//付款单明细
		List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();
		BillPaymentAddDto dto = new BillPaymentAddDto();
		dto.setId(payableEntity.getId());
		dto.setAmount(payableEntity.getAmount());
		dto.setBillType("电汇");
		dto.setCurrentPaymentAmount(new BigDecimal("500"));
		dto.setNotes("测试");
		dto.setPayableNo(payableEntity.getPayableNo());
		dto.setUnverifyAmount(payableEntity.getUnverifyAmount());
		dto.setVerifyAmount(payableEntity.getUnverifyAmount());
		dto.setVersionNo(payableEntity.getVersionNo());
		dto.setAccountDate(payableEntity.getAccountDate());
		addDtoList.add(dto);
		
		map.put("paymentEntity", paymentEntity);
		map.put("addDtoList",addDtoList );
		return map;
	}


	/**
	 * 获取应付单数据
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-31 下午5:51:37
	 * @param wayBillNo
	 * @return
	 * @see
	 */
	public BillPayableEntity getBillPayableEntity() {

		BillPayableEntity entity = new BillPayableEntity();

		String waybillNo = Math.round(Math.random() * 1000000000) + "";
		String payableNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(),
				Calendar.SECOND);

		entity.setId(UUIDUtils.getUUID());
		entity.setPayableNo(payableNo);
		entity.setWaybillNo(waybillNo);
		entity.setWaybillId(waybillNo);

		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(billType);
		entity.setSourceBillNo(waybillNo);
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 整车尾款应付单-到达部门来付钱
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)) {
			entity.setPayableOrgCode("CUST_CODE");
		} else {
			entity.setPayableOrgCode("ORIG_CODE");
		}

		entity.setPayableOrgName("上海营业部");

		entity.setPayableComCode("GS1"); 
		entity.setPayableComName("上海德邦物流");
		// 设置出发部门编码、名称
		entity.setOrigOrgCode("CF1");
		entity.setOrigOrgName("上海青浦营业部"); 

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode("DEST1");
		entity.setDestOrgName("北京大营门营业部"); 

		// 设置应付客户编码、名称
		entity.setCustomerCode("YFKH");
		entity.setCustomerName("张三");
		entity.setCustomerContact(""); 
		entity.setCustomerContactName(""); 
		entity.setCustomerPhone("10000");

		// 设置金额、已核销金额、未核销金额
		entity.setAmount(NumberUtils.createBigDecimal("10000"));
		entity.setVerifyAmount(BigDecimal.ZERO);
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);

		// 设置生效日期、创建人、创建人部门
		entity.setEffectiveDate(null);
		entity.setCreateUserCode("CTUS"); 
		entity.setCreateUserName("CTUN");
		entity.setCreateOrgCode("CTORG");
		entity.setCreateOrgName("CTORGN"); 

		// 是否有效、是否红单、是否初始化、版本号
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setIsInit(FossConstants.NO);
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);//已生效
		entity.setCreateTime(now);
		entity.setModifyTime(now);

		return entity;
	}
	
	
	/**
	 * 获取来源单据编号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-1 上午9:53:12
	 */
    public  String getSoureceBillNO() {
    	return "S"+new Date().getTime() ;
    }
	
	
}
