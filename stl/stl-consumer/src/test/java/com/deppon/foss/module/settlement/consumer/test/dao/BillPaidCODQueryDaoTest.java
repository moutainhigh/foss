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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/BillPaidCODQueryDaoTest.java
 * 
 * FILE NAME        	: BillPaidCODQueryDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODCompositeQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 按运单查询
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 下午4:48:29
 */
public class BillPaidCODQueryDaoTest extends BaseTestCase {

	@Autowired
	private ICODCompositeQueryDao cODCompositeQueryDao;

	@Autowired
	private ICODEntityDao codEntityDao;

	@Autowired
	private IBillPayableEntityDao billPayableEntityDao;

	/**
	 * 按运单单号进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 下午4:49:33
	 */
	@Test
	public void testQueryByWayBill() {

		// 添加第一条
		this.testAddCODEntity();

		// 测试总行数
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add("Waybill001");

		CODCompositeQueryDto dto = new CODCompositeQueryDto();
		dto.setWaybillNos(waybillNos);
		dto.setActive(FossConstants.ACTIVE);
		//dto.setStatus("Y");
		//dto.setCodType("codType");

		List<CODCompositeGridDto> queryResult = this.getBillPaidCODDao()
				.queryByWaybillNo(dto);
		Assert.assertNotNull(queryResult);
		Assert.assertEquals(1, queryResult.size());
		Assert.assertEquals("Waybill001", queryResult.get(0).getWaybillNo());
	}

	/**
	 * 新加代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午2:09:42
	 */
	@Test
	public void testAddCODEntity() {

		CODEntity codEntity = new CODEntity();
		codEntity.setId(UUIDUtils.getUUID());
		codEntity.setWaybillNo("Waybill001");
		codEntity.setCodType("codType");

		codEntity.setBusinessDate(new Date());
		codEntity.setIsInit("N");
		codEntity.setActive(FossConstants.ACTIVE);
		codEntity.setStatus("Y");
		// 代收货款金额
		codEntity.setCodAmount(new BigDecimal("10000"));
		// 收款人
		codEntity.setPayeeName("payeeName");
//		codEntity.setPayeeCode("payeeCode");

		//
		codEntity.setPayableOrgCode("payableOrgCode");

		codEntity.setBankHQName("ICBC");
		codEntity.setBatchNumber("2012110501");

		// 代收货款类型
		codEntity.setCodType("codType");
		// 代收货款状态
		codEntity.setStatus("Y");
		// 退款路径
		codEntity.setRefundPath("test");

		// 防止程序报错，故意设置一个较小的日期
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(1980, 10, 2, 14, 1);

		// 退款成功时间
		codEntity.setRefundSuccessTime(currentDate.getTime());
		// 设置付款申请时间
		codEntity.setCodExportTime(currentDate.getTime());
		codEntity.setTusyorgRfdApptime(currentDate.getTime());
		// 业务日期
		codEntity.setBusinessDate(currentDate.getTime());

		int result = this.getCodEntityDao().addCod(codEntity);
		Assert.assertEquals(1, result);

		// 同时添加应付单
		BillPayableEntity billPayable = new BillPayableEntity();
		billPayable.setId(UUIDUtils.getUUID());
		billPayable.setWaybillNo("Waybill001");
		billPayable.setWaybillId("waybillId");

		billPayable.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		billPayable.setPayableNo("001");
		billPayable.setActive(FossConstants.ACTIVE);
		billPayable.setCreateType("AUTO_TEST");
		billPayable.setBillType("COD_TYPE");

		billPayable.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		billPayable.setSourceBillType("waybill");
		billPayable.setSourceBillNo("waybill");
		billPayable.setAmount(new BigDecimal("100000"));
		billPayable.setVerifyAmount(new BigDecimal("100000"));
		billPayable.setUnverifyAmount(new BigDecimal("100000"));
		
		
		billPayable.setCurrencyCode("RMB");
		billPayable.setAccountDate(new Date());
		billPayable.setBusinessDate(new Date());
		billPayable.setIsRedBack("N");
		billPayable.setIsInit("N");
		billPayable.setCustomerContactName("payeeName");
		billPayable.setVersionNo(FossConstants.INIT_VERSION_NO);

		int rows = this.billPayableEntityDao.add(billPayable);

		Assert.assertEquals(1, rows);

	}

	/**
	 * 按业务日期进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-3 下午2:10:10
	 */
	@Test
	public void testQueryByBizDate() {
		// 新加
		this.testAddCODEntity();

		// 查询
		Calendar inceptDate = Calendar.getInstance();
		inceptDate.set(1980, 10, 2, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(1980, 10, 3, 0, 0);

		// 获得行数
		CODCompositeQueryDto queryDto = new CODCompositeQueryDto();
		queryDto.setInceptBizDate(inceptDate.getTime());
		queryDto.setEndBizDate(endDate.getTime());
		queryDto.setCodType("test");
		queryDto.setStatus("Y");
		// 退款路径
		queryDto.setRefundPath("test");
		//
		queryDto.setPayableOrgCode("payableOrgCode");
		queryDto.setPayeeName("payeeName");
		queryDto.setConsignee("payeeName");

		queryDto.setBank("ICBC");
		queryDto.setBatchNumber("2012110501");

		//
		queryDto.setInceptCodAmount(new BigDecimal("4000"));
		queryDto.setEndCodAmount(new BigDecimal("50000"));
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setStatus("Y");
		queryDto.setCodType("codType");
		queryDto.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		int rows = this.getBillPaidCODDao().queryTotalRowsByBizDate(queryDto);
		Assert.assertEquals(1, rows);

		List<CODCompositeGridDto> resultSet = this.getBillPaidCODDao()
				.queryByBizDate(queryDto, 0, 1);
		Assert.assertEquals(1, resultSet.size());

	}

	@Test
	public void testQueryByPayDate() {
		// 新加
		this.testAddCODEntity();

		// 查询
		Calendar inceptDate = Calendar.getInstance();
		inceptDate.set(1980, 10, 2, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(1980, 10, 3, 0, 0);

		// 获得行数
		CODCompositeQueryDto queryDto = new CODCompositeQueryDto();
		queryDto.setInceptPaymentDate(inceptDate.getTime());
		queryDto.setEndPaymentDate(endDate.getTime());
		queryDto.setCodType("codType");
		queryDto.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		queryDto.setStatus("Y");
		// 退款路径
		queryDto.setRefundPath("test");
		//
		queryDto.setPayableOrgCode("payableOrgCode");
		queryDto.setPayeeName("payeeName");
		queryDto.setConsignee("payeeName");

		queryDto.setBank("ICBC");
		queryDto.setBatchNumber("2012110501");
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setStatus("Y");
		queryDto.setCodType("codType");

		//
		queryDto.setInceptCodAmount(new BigDecimal("4000"));
		queryDto.setEndCodAmount(new BigDecimal("50000"));

		int rows = this.getBillPaidCODDao().queryTotalRowsByPayDate(queryDto);
		Assert.assertEquals(1, rows);

		List<CODCompositeGridDto> resultSet = this.getBillPaidCODDao()
				.queryByPayDate(queryDto, 0, 1);
		Assert.assertEquals(1, resultSet.size());
	}

	/**
	 * 按签收日期查询
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-6 上午10:59:22
	 */
	@Test
	@Ignore
	public void testQueryBySignDate() {
		// 新加
		this.testAddCODEntity();

		// 查询
		Calendar inceptDate = Calendar.getInstance();
		inceptDate.set(1980, 10, 2, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(1980, 10, 3, 0, 0);

		// 获得行数
		CODCompositeQueryDto queryDto = new CODCompositeQueryDto();
		//签收日期
		queryDto.setInceptSignDate(inceptDate.getTime());
		queryDto.setEndSignDate(endDate.getTime());
		queryDto.setCodType("codType");
		queryDto.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		queryDto.setStatus("Y");
		// 退款路径
		queryDto.setRefundPath("test");
		//
		queryDto.setPayableOrgCode("payableOrgCode");
		queryDto.setPayeeName("payeeName");
		queryDto.setConsignee("payeeName");

		queryDto.setBank("ICBC");
		queryDto.setBatchNumber("2012110501");
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setStatus("Y");
		queryDto.setCodType("codType");

		//
		queryDto.setInceptCodAmount(new BigDecimal("4000"));
		queryDto.setEndCodAmount(new BigDecimal("50000"));

		int rows = this.getBillPaidCODDao().queryTotalRowsSignDate(queryDto);
		Assert.assertEquals(1, rows);

		List<CODCompositeGridDto> resultSet = this.getBillPaidCODDao()
				.queryBySignDate(queryDto, 0, 1);
		Assert.assertEquals(1, resultSet.size());
	}

	public ICODCompositeQueryDao getBillPaidCODDao() {
		return cODCompositeQueryDao;
	}

	public void setBillPaidCODDao(ICODCompositeQueryDao cODCompositeQueryDao) {
		this.cODCompositeQueryDao = cODCompositeQueryDao;
	}

	public ICODEntityDao getCodEntityDao() {
		return codEntityDao;
	}

	public void setCodEntityDao(ICODEntityDao codEntityDao) {
		this.codEntityDao = codEntityDao;
	}

	public void setBillPayableEntityDao(
			IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}

}
