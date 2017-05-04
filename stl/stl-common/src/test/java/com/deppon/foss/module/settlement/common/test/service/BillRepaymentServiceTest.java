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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillRepaymentServiceTest.java
 * 
 * FILE NAME        	: BillRepaymentServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 还款单测试类
 * @author ibm-zhuwei
 * @date 2012-10-19 上午9:35:09
 */
public class BillRepaymentServiceTest extends BaseTestCase {

	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IBillRepaymentService billRepaymentService;

	private BillRepaymentEntity getBillRepaymentEntity() {
		
		BillRepaymentEntity entity = new BillRepaymentEntity();
		
		Date now = DateUtils.truncate(new Date(), Calendar.SECOND);
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1);
		entity.setRepaymentNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);
		entity.setConrevenDate(now);
		//TODO 
		entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setOnlinePaymentNo("PAYMENT_NO");
		entity.setOaPaymentNo("OA_PAYMENT_NO");
		entity.setIsInit(FossConstants.NO);

		entity.setSourceBillNo("12345678");
		entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__WAYBILL);
		
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setCreateOrgCode("GS00002");
		entity.setCreateOrgName("部门");
		entity.setGeneratingOrgCode("GS00002");
		entity.setGeneratingOrgName("部门");
		entity.setCollectionOrgCode("GS00002");
		entity.setCollectionOrgName("部门");
		entity.setAmount(NumberUtils.createBigDecimal("101.53"));
		entity.setTrueAmount(NumberUtils.createBigDecimal("101.53"));
		entity.setBverifyAmount(BigDecimal.ZERO);
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setSourceBillType("PL");//外发单
		entity.setWaybillNo("111111");
		
		return entity;
	}

	@Test
	@Rollback(true)
	public void testAddBillRepayment() {
		logger.trace("do...");
		
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
	}
	
	@Test
	@Rollback(true)
	public void testWriteBackBillRepayment() {
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		try{
			billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
			billRepaymentService.writeBackBillRepayment(entity,CommonTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getMessage(), e);
		}
	}
	
	
	@Test
	@Rollback(true)
	@Ignore
	public void testReverseWriteoffBillRepayment(){
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
		
		//反核销部分还款金额
		billRepaymentService.reverseWriteoffBillRepayment(entity, NumberUtils.createBigDecimal("100"),CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 批量审核还款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午11:39:35
	 */
	@Test
	@Rollback(true)
	public void testAuditBillRepayment(){
		BillRepaymentDto dto=new BillRepaymentDto();
		
		//查询还款单数据
		List<String> sourceBillNos=new ArrayList<String>();
		sourceBillNos.add("1351668631171");
		sourceBillNos.add("1351668652593");
		sourceBillNos.add("1351669935359");

		List<BillRepaymentEntity> list=this.billRepaymentService.queryBySourceBillNOs(sourceBillNos,FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(list)){
			dto.setBillRepayments(list);
			dto.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__AUDIT_AGREE);
			try{
				this.billRepaymentService.auditBillRepayments(dto,CommonTestUtil.getCurrentInfo());
			}catch(SettlementException e){
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 批量反审核还款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 上午11:40:13
	 */
	@Test
	@Rollback(true)
	public void testReverseAuditBillRepayment(){
		BillRepaymentDto dto=new BillRepaymentDto();
		
		//查询还款单数据
		List<String> sourceBillNos=new ArrayList<String>();
		sourceBillNos.add("1351668631171");
		sourceBillNos.add("1351668652593");
		sourceBillNos.add("1351669935359");

		List<BillRepaymentEntity> list=this.billRepaymentService.queryBySourceBillNOs(sourceBillNos,FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(list)){
			dto.setBillRepayments(list);
			dto.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
			try{
				this.billRepaymentService.reverseAuditBillRepayments(dto,CommonTestUtil.getCurrentInfo());
			}catch(SettlementException e){
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 批量确认收银还款单
	 * @author ibm-zhuwei
	 * @date 2012-12-18 下午2:29:49
	 */
	@Test
	@Rollback(true)
	public void testConfirmCashierBillRepayments() {
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
		BillRepaymentDto dto = new BillRepaymentDto();
		dto.setBillRepayments(Arrays.asList(entity));
		billRepaymentService.confirmCashierBillRepayments(dto, CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 查询符合条件的还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午8:46:43
	 */
	@Test
	@Rollback(true)
	public void testQueryBillRepaymentByCondition(){
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
		
		BillRepaymentConditionDto dto=new BillRepaymentConditionDto();
		dto.setRepaymentNo(entity.getRepaymentNo());//还款单号
		dto.setSourceBillTypes(new String[]{SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__PARTIAL_LINE});
		dto.setPartailLineAgentCode("CUST");
		try{
			List<BillRepaymentEntity> list=this.billRepaymentService.queryBillRepaymentByCondition(dto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		}catch(SettlementException e){
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 *  根据来源单号集合和部门编码集合，查询还款单信息
	 *   
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午10:50:55
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsAndOrgCodes(){
		BillRepaymentEntity entity = this.getBillRepaymentEntity();
		entity.setCollectionOrgCode("123456789");
		billRepaymentService.addBillRepayment(entity,CommonTestUtil.getCurrentInfo());
		
		List<String> sourceBillNos=new ArrayList<String>();
		List<String> orgCodes=new ArrayList<String>();
		
		sourceBillNos.add(entity.getSourceBillNo());//来源单号
		orgCodes.add(entity.getCollectionOrgCode());//付款部门编码
		
		this.billRepaymentService.queryBySourceBillNOsAndOrgCodes
		(sourceBillNos, orgCodes, FossConstants.ACTIVE,CommonTestUtil.getCurrentInfo());
	}
	
}
