/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.test.service
 * FILE    NAME: BillWriteoffServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.web.server.test.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;
import com.deppon.foss.module.settlement.web.test.BaseTestCase;
import com.deppon.foss.module.settlement.web.test.util.StlWebTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预付单测试
 * 
 * @author 095793-foss-LiQin
 * @date 2012-11-13 下午6:15:04
 */
public class BillAdvanceApplysManageServiceTest extends BaseTestCase {

	/**
	 * 预付单测试Logger
	 */
	private static final Logger logger=LogManager.getLogger(BillAdvanceApplysManageServiceTest.class);
	
	// 公共预付单service
	@Autowired
	IBillAdvancedPaymentService billAdvancedPaymentService;

	// 预付单service
	@Autowired
	IBillAdvanceApplysManageService billAdvanceApplysManageService;

	// 结算通用自动产生预付单编号
	@Autowired
	ISettlementCommonService settlementCommonService;
	
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setBillAdvanceApplysManageService(
			IBillAdvanceApplysManageService billAdvanceApplysManageService) {
		this.billAdvanceApplysManageService = billAdvanceApplysManageService;
	}

	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}
	
	
	
	//预付单，新增
	/*@Test
	@Ignore
	@Rollback(false)
	// 插入
	public void testInsertBillAdvancesPay() {
		CurrentInfo cInfo = StlWebTestUtil.getCurrentInfo();
		Date now=DateUtils.truncate(Calendar.getInstance().getTime(),Calendar.SECOND);
		//预付单
		BillAdvancedPaymentEntity entity=new BillAdvancedPaymentEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setAdvancesNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.UF));
		entity.setCustomerCode("000056");
		entity.setCustomerName("liqin");
		entity.setAmount(new BigDecimal("80000000"));
		entity.setVerifyAmount(new BigDecimal("0"));
		//未核销金额等于总金额
		entity.setUnverifyAmount(entity.getAmount());
		entity.setApplyOrgCode("0003");
		entity.setApplyOrgName("test");
		entity.setPaymentOrgCode("0003");
		entity.setPaymentOrgName("test");

		entity.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT);
		entity.setActive(FossConstants.YES);
		entity.setIsRedBack(FossConstants.NO);
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		entity.setPaymentType("00");
		entity.setCreateUserCode("test");
		entity.setBusinessDate(now);
		entity.setAccountDate(now);
		entity.setCreateTime(now);
		entity.setIsInit(FossConstants.NO);
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT);
		entity.setMobilePhone("13916226310");
		
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setAccountNo("6222564978632534");
		entity.setPayeeName("123");
		entity.setPublicPrivateFlag("123");
		entity.setProvinceCode("123");
		entity.setProvinceName("123");
		entity.setBankHqCode("12121212");
		entity.setBankBranchName("123");
		entity.setBankBranchCode("ddd");
		entity.setBankBranchName("dddd");
		entity.setBankHqName("工商银行，板桥支行");
		entity.setCityCode("00078");
		entity.setCityName("岳阳");
		
		
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		int row=billAdvancedPaymentService.addAdvancedPaymentEntity(entity, cInfo);
		logger.info("成功，插入预付单条数:"+row);
		Assert.assertEquals(1,row);
	}
	*/
	/**
	 * 
	 * 测试查询预付单
	 * @author 095793-foss-LiQin
	 * @date 2012-11-21 下午7:50:54
	 *//*
	@Test
	@Ignore
	public void testQueryAdvanceList(){
		//TODO CurrentInfo cInfo = StlWebTestUtil.getCurrentInfo(); 
		BillAdvanceDto billAdDto = new BillAdvanceDto();
		//查询预付单有效地
		billAdDto.setActive("Y");
		BillAdvanceResultDto billAaDto=new BillAdvanceResultDto() ;
		billAaDto=billAdvanceApplysManageService.queryBillAdvancePayApply(billAdDto, 0, 10);
		logger.info("预付单条数为："+billAaDto.getQueryBillAdvancedPayList().size());
		Assert.assertNotNull(billAaDto.getQueryBillAdvancedPayList().size());
	}
	*/

	/*@Ignore
	@Test
	public void testQueryAdvance(){
		BillAdvanceDto billAdDto = new BillAdvanceDto();
		//查询前一个月数据
		Date now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);//设置当前日期
		calendar.add(Calendar.MONTH, -1);
		billAdDto.setBusinessStartDate(calendar.getTime());
		billAdDto.setBusinessEndDate(now);
		BillAdvanceResultDto dto=billAdvanceApplysManageService.queryBillAdvancePayApply(billAdDto, 0, 5);
		logger.info(dto.getQueryBillAdvancedPayList().size());
	}*/
}
