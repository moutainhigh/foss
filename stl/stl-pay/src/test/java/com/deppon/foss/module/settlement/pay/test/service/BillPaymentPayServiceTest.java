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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/service/BillPaymentPayServiceTest.java
 * 
 * FILE NAME        	: BillPaymentPayServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import com.deppon.foss.module.settlement.pay.test.PayTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;



/**
 * 测试录入付款单服务
 * @author 045738-foss-maojianqiang
 * @date 2012-12-1 上午9:46:30
 */
public class BillPaymentPayServiceTest extends BaseTestCase{
	
	private static final Logger log = LoggerFactory.getLogger(BillPaymentPayServiceTest.class);
	@Autowired
	private IBillPaymentPayService billPaymentPayService;
	@Autowired
	private IBillPaymentService billPaymentService;
	@Autowired
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 测试更新付款单工作流号
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-10 上午8:25:29
	 */
	@Test
	@Rollback(true)
	public void testUpdateWorkFlow(){
		try{
			BillPaymentEntity entity = getBillPaymentEntity();
			//插入数据
			billPaymentService.addBillPayment(entity, PayTestUtil.getCurrentInfo());
			List<String> paymentNos = new ArrayList<String>();
			paymentNos.add(entity.getPaymentNo());
			String batchNos= entity.getBatchNo();
			String workflowNo = "00000001";
			//更新
			billPaymentPayService.updateWorkFlow(batchNos,workflowNo);
			//查询
			List<BillPaymentEntity> list = billPaymentService.queryBillPaymentByPaymentNOs(paymentNos, FossConstants.ACTIVE);
			BillPaymentEntity updateEntity = list.get(0);
			Assert.assertTrue(updateEntity.getWorkflowNo().equals(workflowNo));
		}catch(BusinessException e){
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 获取付款单
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-10 上午8:33:31
	 */
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
		
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		
		
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		entity.setSourceBillNo(this.getSoureceBillNO());
		entity.setBankHqName("BANK_HQ_NAME");
		entity.setBankHqCode("BANK_HQ_CODE");
		entity.setPaymentType("TEST");
		entity.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRING);
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
		entity.setBatchNo("FK00001");
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
