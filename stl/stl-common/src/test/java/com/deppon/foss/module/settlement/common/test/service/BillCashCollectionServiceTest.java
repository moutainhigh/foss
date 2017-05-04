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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/BillCashCollectionServiceTest.java
 * 
 * FILE NAME        	: BillCashCollectionServiceTest.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillCashCollectionDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 现金收款单服务测试类
 * @author ibm-zhuwei
 * @date 2012-10-19 上午9:34:49
 */
public class BillCashCollectionServiceTest extends BaseTestCase {
	
	@Autowired
	private ISettlementCommonService settlementCommonService;
	@Autowired
	private IBillCashCollectionService billCashCollectionService;

	private BillCashCollectionEntity getBillCashCollectionEntity() {
		
		BillCashCollectionEntity entity = new BillCashCollectionEntity();
		
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.SECOND);
		entity.setId(UUIDUtils.getUUID());
		String no1 = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.XS1);
		entity.setCashCollectionNo(no1);
		entity.setActive(FossConstants.ACTIVE);
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setCreateTime(now);
		entity.setModifyTime(now);
		entity.setAccountDate(now);
		entity.setBusinessDate(now);
		//TODO 
		entity.setStatus("STATUS");
		entity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		entity.setIsInit(FossConstants.NO);
		
		//TODO
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__BILL_TYPE__CASH_COLLECTION);
		entity.setSourceBillNo("12345678");
		//TODO
		entity.setProductCode("PRODUCT");

		entity.setBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);
		
		entity.setCustomerCode("CUST");
		entity.setCustomerName("客户");
		entity.setCreateOrgCode("ORG");
		entity.setCreateOrgName("部门");
		entity.setGeneratingOrgCode("ORG");
		entity.setGeneratingOrgName("部门");
		entity.setCollectionOrgCode("ORG");
		entity.setCollectionOrgName("部门");
		entity.setAmount(NumberUtils.createBigDecimal("100.00"));
		entity.setTransportFee(NumberUtils.createBigDecimal("100.00"));	// 公布价运费
		entity.setPickupFee(BigDecimal.ZERO);	// 接货费
		entity.setDeliveryGoodsFee(BigDecimal.ZERO);	// 送货费
		entity.setPackagingFee(BigDecimal.ZERO);	// 包装手续费
		entity.setCodFee(BigDecimal.ZERO);	// 代收货款费
		entity.setInsuranceFee(BigDecimal.ZERO);	// 保价费
		entity.setOtherFee(BigDecimal.ZERO);	// 其他费用
		entity.setValueAddFee(BigDecimal.ZERO);	// 增值费用
		entity.setPromotionsFee(BigDecimal.ZERO);	// 优惠费用
		
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setCollectionType("会员费");
		
		entity.setOrigOrgCode("ch1");
		entity.setOrigOrgName("出发部门");
		
		entity.setDestOrgCode("DD1");//到达部门
		entity.setDestOrgName("到达部门");//到达部门
		
		return entity;
	}

	@Before
	public void setUp() {
//		this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
		this.executeSqlScript("classpath:com/deppon/foss/module/settlement/common/test/META-INF/sql/bill_cash_collection.sql", true);
	}
	
	@After
	public void tearDown() {
		// this.deleteFromTables("T_STL_BILL_CASH_COLLECTION");
	}
	
	@Test
	@Rollback(true)
	public void testAddBillCashCollection() {
		logger.trace("do...");
		
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		billCashCollectionService.addBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void testWriteBackBillCashCollection() {
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		billCashCollectionService.addBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
		billCashCollectionService.writeBackBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testConfirmCashierBillCashCollection() {
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		billCashCollectionService.addBillCashCollection(entity, CommonTestUtil.getCurrentInfo());
		BillCashCollectionDto dto = new BillCashCollectionDto();
		List<BillCashCollectionEntity> list = new ArrayList<BillCashCollectionEntity>();
		list.add(entity);
		dto.setBillCashCollections(list);
		billCashCollectionService.confirmCashierBillCashCollection(dto, CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testConfirmIncomeBillCashCollection() {
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		billCashCollectionService.addBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.SECOND);
		entity.setConrevenDate(now);
		billCashCollectionService.confirmIncomeBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
	}

	@Test
	@Rollback(true)
	public void testReverseConfirmIncomeBillCashCollection() {
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		billCashCollectionService.addBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
		Date now = DateUtils.truncate(Calendar.getInstance().getTime(), Calendar.SECOND);
		entity.setConrevenDate(now);
		billCashCollectionService.reverseConfirmIncomeBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
	}
	
	/**
	 * 根据来源单号集合和部门编码集合，查询现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-22 上午10:00:54
	 */
	@Test
	@Rollback(true)
	public void testQueryBySourceBillNOsAndOrgCodes(){
		BillCashCollectionEntity entity = this.getBillCashCollectionEntity();
		entity.setCollectionOrgCode("456123789");
		entity.setSourceBillNo("123456789");
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);
		billCashCollectionService.addBillCashCollection(entity,CommonTestUtil.getCurrentInfo());
		
		List<String> sourceBillNos=new ArrayList<String>();
		List<String> orgCodes=new ArrayList<String>();
		
		sourceBillNos.add(entity.getSourceBillNo());//来源单号
		orgCodes.add(entity.getCollectionOrgCode());//收款部门编码
		billCashCollectionService.queryBySourceBillNOsAndOrgCodes(sourceBillNos, SettlementDictionaryConstants.
				BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL, orgCodes, FossConstants.ACTIVE
				,CommonTestUtil.getCurrentInfo()
				);
		
		
	}

}
