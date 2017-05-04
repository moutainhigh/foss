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
 * PROJECT NAME	: stl-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillPaymentServiceTest.java
 * 
 * FILE NAME        	: BillPaymentServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 付款单服务测试类
 * @author ibm-zhuwei
 * @date 2012-10-19 上午9:34:49
 */
public class BillPaymentServiceTest extends BaseTestCase {
	
	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IBillPaymentService billPaymentService;

	private BillPaymentEntity getBillPaymentEntity() {
		
		BillPaymentEntity entity = new BillPaymentEntity();
		
		Date now=DateUtils.truncate(new Date(),
				Calendar.SECOND);
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF1);
		entity.setPaymentNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);
		//TODO 
//		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		
		//TODO
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		entity.setSourceBillNo(this.getSoureceBillNO());
		//TODO
		entity.setBankHqName("BANK_HQ_NAME");
		entity.setBankHqCode("BANK_HQ_CODE");
		entity.setPaymentType("TEST");
		entity.setRemitStatus("TEST");
		entity.setAuditStatus("TEST");
		entity.setBillType("TEST");
		entity.setPaymentOrgCode("ORG");
		entity.setPaymentOrgName("部门");
		entity.setMobilePhone("MOBILE");
		entity.setAccountNo("BANK_NO");
		entity.setPayeeName("USER_NAME");
		entity.setAccountType("ACCOUNT_TYPE");
		entity.setProvinceName("PROV");
		entity.setProvinceCode("PROVCODE");
		entity.setCityCode("CITY");
		entity.setCityName("CITYNAME");
		entity.setBankBranchCode("BANK_BRANCH_CODE");
		entity.setBankBranchName("BANK_BRANCH_NAME");
		entity.setApplyWorkFlowNo("APPLY_WORK_FLOW_NO");
		
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setAmount(NumberUtils.createBigDecimal("100.00"));
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);//默认设置为未审核
		return entity;
	}

	@Before
	public void setUp() {
//		this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
//		this.executeSqlScript("classpath:com/deppon/foss/module/settlement/common/test/META-INF/sql/bill_cash_collection.sql", true);
	}
	
	@After
	public void tearDown() {
		// this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
	}
	
	@Test
	@Rollback(true)
	public void testAddBillPayment() {
		logger.trace("do...");
		
		BillPaymentEntity entity = this.getBillPaymentEntity();
		billPaymentService.addBillPayment(entity,CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testWriteBackBillPayment() {
		BillPaymentEntity entity = this.getBillPaymentEntity();
		billPaymentService.addBillPayment(entity,CommonTestUtil.getCurrentInfo());
		billPaymentService.writeBackBillPayment(entity,CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 批量审核付款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午11:35:44
	 */
	@Test
	@Rollback(true)
	public void testBatchAuditBillPayment(){
		BillPaymentEntity entity=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entity, CommonTestUtil.getCurrentInfo());
		
		BillPaymentEntity entityTwo=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entityTwo, CommonTestUtil.getCurrentInfo());
		
		BillPaymentEntity entityThree=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entityThree, CommonTestUtil.getCurrentInfo());
		
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		list.add(entity);
		list.add(entityTwo);
		list.add(entityThree);
		BillPaymentDto dto=new BillPaymentDto();
		dto.setBillPayments(list);
		try{
			this.billPaymentService.batchAuditBillPayment(dto, CommonTestUtil.getCurrentInfo());
			//查询出付款单
			List<String> sourceBillNos=new ArrayList<String>();
			sourceBillNos.add(entity.getSourceBillNo());
			sourceBillNos.add(entityTwo.getSourceBillNo());
			sourceBillNos.add(entityThree.getSourceBillNo());
			List<BillPaymentEntity> billPayments=this.billPaymentService.queryBillPaymentBySourceBillNOs(sourceBillNos, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT, FossConstants.ACTIVE);
			Assert.assertTrue(CollectionUtils.isNotEmpty(billPayments));
			for(BillPaymentEntity paymentEntity:billPayments){
				Assert.assertEquals(paymentEntity.getAuditStatus(), SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
			}
		}catch(SettlementException e){
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 批量反审核付款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 上午11:36:17
	 */
	@Test
	@Rollback(true)
	public void testBatchReverseAuditBillPayment(){
		BillPaymentEntity entity=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entity, CommonTestUtil.getCurrentInfo());
		
		BillPaymentEntity entityTwo=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entityTwo, CommonTestUtil.getCurrentInfo());
		
		BillPaymentEntity entityThree=this.getBillPaymentEntity();
		this.billPaymentService.addBillPayment(entityThree, CommonTestUtil.getCurrentInfo());
		
		List<BillPaymentEntity> list=new ArrayList<BillPaymentEntity>();
		list.add(entity);
		list.add(entityTwo);
		list.add(entityThree);
		BillPaymentDto dto=new BillPaymentDto();
		dto.setBillPayments(list);
		
		try{
			this.billPaymentService.batchAuditBillPayment(dto, CommonTestUtil.getCurrentInfo());
			//查询出付款单
			List<String> sourceBillNos=new ArrayList<String>();
			sourceBillNos.add(entity.getSourceBillNo());
			sourceBillNos.add(entityTwo.getSourceBillNo());
			sourceBillNos.add(entityThree.getSourceBillNo());
			List<BillPaymentEntity> billPayments=this.billPaymentService.queryBillPaymentBySourceBillNOs(sourceBillNos, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT, FossConstants.ACTIVE);
			Assert.assertTrue(CollectionUtils.isNotEmpty(billPayments));
			for(BillPaymentEntity paymentEntity:billPayments){
				Assert.assertEquals(paymentEntity.getAuditStatus(), SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
			}
			
			dto.setBillPayments(billPayments);
			this.billPaymentService.batchReverseAuditBillPayment(dto, CommonTestUtil.getCurrentInfo());
			
			billPayments=this.billPaymentService.queryBillPaymentBySourceBillNOs(sourceBillNos, SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT, FossConstants.ACTIVE);
			Assert.assertTrue(CollectionUtils.isNotEmpty(billPayments));
			for(BillPaymentEntity paymentEntity:billPayments){
				Assert.assertEquals(paymentEntity.getAuditStatus(), SettlementDictionaryConstants.BILL_PAYMENT__AUDIT_STATUS__NOT_AUDIT);
			}
		}catch(SettlementException e){
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据来源单号集合和部门编码集合，查询付款单信息 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午10:36:46
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsAndOrgCodes(){
		BillPaymentEntity entity=this.getBillPaymentEntity();
		entity.setPaymentOrgCode("1232456789");
		this.billPaymentService.addBillPayment(entity, CommonTestUtil.getCurrentInfo());
		
		List<String> sourceBillNos=new ArrayList<String>();
		List<String> orgCodes=new ArrayList<String>();
		
		sourceBillNos.add(entity.getSourceBillNo());//来源单号
		orgCodes.add(entity.getPaymentOrgCode());//付款部门编码
		
		this.billPaymentService.queryBySourceBillNOsAndOrgCodes(sourceBillNos, null, orgCodes, FossConstants.ACTIVE,CommonTestUtil.getCurrentInfo());
		
	}
	
	public  String getSoureceBillNO() {
		return "S"+new Date().getTime() ;
	}

	@Test
	public void testqueryPaymentRemitStatusByPaymentNO(){
		BillPaymentEntity entity=this.getBillPaymentEntity();
		entity.setPaymentOrgCode("1232456789");
		this.billPaymentService.addBillPayment(entity, CommonTestUtil.getCurrentInfo());
		this.billPaymentService.queryPaymentRemitStatusByPaymentNO(entity.getPaymentNo(), FossConstants.ACTIVE);
	}
}
