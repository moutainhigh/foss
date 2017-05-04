/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.test.service
 * FILE    NAME: CashCollectionIncomeDaoTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.pay.test.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionIncomeDao;
import com.deppon.foss.module.settlement.pay.api.server.dao.ICashCollectionRptEntityDao;
import com.deppon.foss.module.settlement.pay.api.shared.domain.CashCollectionRptEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

/**
 * 现金收入(缴款记录)信息测试测试
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-17 上午10:47:30
 */
public class CashCollectionIncomeDaoTest extends BaseTestCase {

	/**
	 * 现金收入(缴款记录)信息测试测试Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(CashCollectionIncomeDaoTest.class);

	// 查询现金收入（缴款）service
	@Autowired
	private ICashCollectionIncomeDao cashCollectionIncomeDao;
	
	@Autowired
	private ICashCollectionRptEntityDao cashCollectionRptEntityDao;

	/**
	 * 测试，查询现金收入（缴款）报表所有部门应缴款信息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-17 上午10:50:48
	 */
	@Test
//	@Ignore
	public void testQueryCashCollectionIncome() {

		CashCollectionRptEntity entity=new CashCollectionRptEntity();
		entity.setOrgCode("W011303070309");
		//BillCashRecPayInResultDto  dto=cashCollectionRptEntityDao.queryCashinComerptDebtAmount(entity);
		//logger.info("现金收入(缴款记录)信息测试条数为：营业款" + dto.getTotalClerksAmount()+"预收款"+dto.getTotalPrecollectedAmount());
		
		
		
//		CashCollectionIncomeEntity entity2=new CashCollectionIncomeEntity();
//		entity.setOrgCode("W011303070309");
//		List<CashCollectionIncomeEntity> cashList = cashCollectionIncomeDao.queryCashCollectionIncome(entity2);
//		Assert.assertNotNull(cashList.size());
//		logger.info("现金收入(缴款记录)信息测试条数为：" + cashList.size());
	}
	
	
	
	/**
	 * 查询上传现金缴款金额
	 */
	@Test
//	@Rollback(false)
	public void testQueryCashAmount(){
		Date currentDate = com.deppon.foss.util.DateUtils.getStartDatetime(new Date());
		Date beginDate = com.deppon.foss.util.DateUtils.addDayToDate(currentDate, -1);
		Date endDate = currentDate;
		
		//支付方式  网上支付、现金、银行卡、电汇、支票
		List<String>paymentList=new ArrayList<String>();
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD);
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE);
		paymentList.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		
		List<CashCollectionRptEntity> list = cashCollectionRptEntityDao
				.queryUploadCashAllAmount(
						beginDate,
						endDate,
						paymentList);
		logger.info(list.size());
	}
	

	/**
	 * @return cashCollectionIncomeDao
	 */
	public ICashCollectionIncomeDao getCashCollectionIncomeDao() {
		return cashCollectionIncomeDao;
	}

	/**
	 * @param cashCollectionIncomeDao
	 */
	public void setCashCollectionIncomeDao(
			ICashCollectionIncomeDao cashCollectionIncomeDao) {
		this.cashCollectionIncomeDao = cashCollectionIncomeDao;
	}
}
