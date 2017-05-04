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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/ReceiveHandleResultTest.java
 * 
 * FILE NAME        	: ReceiveHandleResultTest.java
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
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.esb.api.util.ESBInitUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.server.service.IReceiveHandleResult;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.CostcontrolToFossDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.PayTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 接口费空处理结果进行核销
 * @author 045738-foss-maojianqiang
 * @date 2012-12-7 下午5:02:28
 */
public class ReceiveHandleResultTest extends BaseTestCase {
	
	
	private static final Logger log = LoggerFactory.getLogger(BillPaymentPayServiceTest.class);
	@Autowired
	private IBillPaymentPayService billPaymentPayService;
	@Autowired
	private IBillPaymentService billPaymentService;
	@Autowired
	private IBillPayableService billPayableService;
	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IReceiveHandleResult receiveHandleResult;
	@Autowired
	private IBillWriteoffService billWriteoffService;
	@Autowired
	private IClaimStatusMsgService claimStatusMsgService;
	
	/** 
	 * 初始化服务编码
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-10 下午2:12:41
	 */
	@Before
	public void init() throws Exception{
		ESBInitUtil util = new ESBInitUtil();
		util.process();
	}
	
	/**
	 * 测试处理备用金接口
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-7 下午5:04:29
	 */
	@Rollback(true)
	@Test
	@SuppressWarnings({ "rawtypes", "unused" })
	public void testDealPettyCashWorkFlow(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = PayTestUtil.getCurrentInfo();
			//获取应付单
			BillPayableEntity payableEntity = getBillPayableEntity();
			//插入应付单
			billPayableService.addBillPayable(payableEntity, currentInfo);
			//获取map
			Map map = getBillPaymentEntity(payableEntity);
			BillPaymentEntity paymentEntity = (BillPaymentEntity) map.get("paymentEntity");
			List<BillPaymentAddDto> addDtoList = (List<BillPaymentAddDto>) map.get("addDtoList");
			
			//模拟录入付款单需要传入参数
			billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, currentInfo);
			
			CostcontrolToFossDto dto = new CostcontrolToFossDto();
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(paymentEntity.getPaymentNo());
			dto.setPaymentBillNo(paymentEntity.getBatchNo());
			dto.setAmount(new BigDecimal("500"));
			dto.setWorkflowNo("00005");
			dto.setWorkflowtype(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_APPLY);//COST_CONTROL__PAY_WORK_FLOW_TYPE__PAYMENT --付款工作流
			dto.setDepno("000123");
			dto.setExamineConment("success!");//COST_CONTROL__RESULT__SUCCESS_TO_FAIL
			dto.setExamineResult(SettlementESBDictionaryConstants.COST_CONTROL__RESULT__FAIL);
			//处理工作流 --成功
			receiveHandleResult.dealPettyCashWorkFlow(dto);
			//查询付款单
			List<BillPaymentEntity> paymentList = billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			for(BillPaymentEntity payment:paymentList){
				log.info("付款单：================="+payment.getPaymentNo()+"  "+payment.getRemitStatus()+" "+payment.getAmount());
			}
			//查询应付单
			BillPayableEntity newPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			log.info("应付单：================="+newPayableEntity.getPayableNo()+"  "+newPayableEntity.getUnverifyAmount()+" "+newPayableEntity.getVerifyAmount()+" "+newPayableEntity.getAmount()+newPayableEntity.getPaymentNo()+" "+newPayableEntity.getPayStatus());
			
			//查询核销单
			List<BillWriteoffEntity> writeoffList = billWriteoffService.queryBillWriteoffByBeginOrEndNo(paymentEntity.getPaymentNo(), FossConstants.ACTIVE,SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE );
			//循环核销单
			for(BillWriteoffEntity writeEntity:writeoffList){
				log.info("核销单：================="+writeEntity.getWriteoffBillNo()+"  "+writeEntity.getAmount()+" "+writeEntity.getBeginNo()+" "+writeEntity.getEndNo()+"  "+writeEntity.getWriteoffType());
			}
			//理赔
			claimStatusMsgService.queryClaimStatusMsgByWaybillNO(payableEntity.getWaybillNo());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 处理费控工作流接口
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-10 上午8:41:32
	 */
	@Rollback(true)
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public void testDealPaymentWorkFlow(){
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = PayTestUtil.getCurrentInfo();
			//获取应付单
			BillPayableEntity payableEntity = getBillPayableEntity();
			//插入应付单
			billPayableService.addBillPayable(payableEntity, currentInfo);
			//获取map
			Map map = getBillPaymentEntity(payableEntity);
			BillPaymentEntity paymentEntity = (BillPaymentEntity) map.get("paymentEntity");
			List<BillPaymentAddDto> addDtoList = (List<BillPaymentAddDto>) map.get("addDtoList");
			
			//模拟录入付款单需要传入参数
			billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, currentInfo);
			
			CostcontrolToFossDto dto = new CostcontrolToFossDto();
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(paymentEntity.getPaymentNo());
			dto.setPaymentBillNo(paymentEntity.getBatchNo());
			dto.setAmount(new BigDecimal("500"));
			dto.setWorkflowNo("00005");
			dto.setWorkflowtype(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_DAY_PAY);
			dto.setDepno("000123");
			dto.setExamineConment("failure!");//COST_CONTROL__RESULT__SUCCESS_TO_FAIL
			dto.setExamineResult(SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCESS);
			//处理工作流 --成功
			receiveHandleResult.dealPaymentWorkFlow(dto);
			dto.setExamineResult(SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCCESS_TO_FAIL);//COST_CONTROL__RESULT__SUCESS --成功  COST_CONTROL__RESULT__FAIL--失败
			//成功转失败
			receiveHandleResult.dealPaymentWorkFlow(dto);
			//查询付款单
			List<BillPaymentEntity> paymentList = billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.INACTIVE);
			for(BillPaymentEntity payment:paymentList){
				log.info("付款单：================="+payment.getPaymentNo()+"  "+payment.getRemitStatus()+" "+payment.getAmount());
			}
			//查询应付单
			BillPayableEntity newPayableEntity = billPayableService.queryByPayableNO(payableEntity.getPayableNo(), FossConstants.ACTIVE);
			log.info("应付单：================="+newPayableEntity.getPayableNo()+"  "+newPayableEntity.getUnverifyAmount()+" "+newPayableEntity.getVerifyAmount()+" "+newPayableEntity.getAmount()+newPayableEntity.getPaymentNo()+" "+newPayableEntity.getPayStatus());
			
			//查询核销单
			List<BillWriteoffEntity> writeoffList = billWriteoffService.queryBillWriteoffByBeginOrEndNo(paymentEntity.getPaymentNo(), FossConstants.INACTIVE,SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__PAYMENT_PAYABLE );
			//循环核销单
			for(BillWriteoffEntity writeEntity:writeoffList){
				log.info("核销单：================="+writeEntity.getWriteoffBillNo()+" "+writeEntity.getAmount()+" "+writeEntity.getBeginNo()+" "+writeEntity.getEndNo()+"  "+writeEntity.getWriteoffType());
			}
			//理赔
			claimStatusMsgService.queryClaimStatusMsgByWaybillNO(payableEntity.getWaybillNo());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
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
		paymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);//电汇
		paymentEntity.setAccountNo("610111198712304016");
		paymentEntity.setProvinceCode("SX");
		paymentEntity.setProvinceName("陕西省");
		paymentEntity.setCityCode("XA");
		paymentEntity.setCityName("西安");
		paymentEntity.setBankHqCode("ZGGSYH");
		paymentEntity.setBankHqName("中国工商银行");
		paymentEntity.setBankBranchCode("BQZH");
		paymentEntity.setBankBranchName("灞桥支行");
		paymentEntity.setAccountType("DG");
		paymentEntity.setPayeeName("张三");
		paymentEntity.setMobilePhone("13917377316");
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
		String billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM;
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

		entity.setPayableOrgCode("000123");

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
