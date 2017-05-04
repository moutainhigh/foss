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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/AirBillPaidCodDaoTest.java
 * 
 * FILE NAME        	: AirBillPaidCodDaoTest.java
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
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillReceivableEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IAirBillPaidCODQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.AirBillPaidCODQueryDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运代收货款Dao测试
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-6 下午5:41:00
 */
public class AirBillPaidCodDaoTest extends BaseTestCase {

	@Autowired
	private IAirBillPaidCODQueryDao airBillPaidCodQueryDao;

	@Autowired
	private ICODEntityDao codEntityDao;

	@Autowired
	private IBillPayableEntityDao billPayableEntityDao;

	@Autowired
	private IBillReceivableEntityDao billReceivableEntityDao;

	/**
	 * 
	 * 测试按运单进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-6 下午5:50:00
	 */
	@Test
	@Ignore
	public void queryByWayBill() {

		// 添加一套操作记录
		this.addCODEntity();

		// 查询
		List<String> waybillNos = new ArrayList<String>();
		waybillNos.add("Waybill001");

		AirBillPaidCODQueryDto dto = getDefaultQueryDto();

		// 查询条件
		dto.setDestOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		dto.setWaybillNoSet(waybillNos);

		// 测试查询结果集
		List<AirBillPaidCODGridDto> queryResult = this
				.getAirBillPaidCodQueryDao().queryByWaybillNos(dto);
		Assert.assertEquals(1, queryResult.size());

	}

	/**
	 * 按签收时间
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午11:48:40
	 */
	@Test
	@Ignore
	public void testQueryBySignDate() {

		// 添加一套操作记录
		this.addCODEntity();

		// 防止程序报错，故意设置一个较小的日期
		Calendar inceptDate = Calendar.getInstance();
		inceptDate.set(1980, 10, 2, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.set(1980, 10, 3, 14, 1);

		AirBillPaidCODQueryDto dto = getDefaultQueryDto();

		// 查询条件
		dto.setDestOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		dto.setInceptSignDate(inceptDate.getTime());
		dto.setEndSignDate(endDate.getTime());

		// 判断行数
		long rows = this.airBillPaidCodQueryDao.queryTotalRowsBySignDate(dto).getTotalCount();
		Assert.assertEquals(1, rows);

		// 判断结果集大小
		List<AirBillPaidCODGridDto> queryResult = this.airBillPaidCodQueryDao
				.queryBySignDate(dto, 0, 1);
		Assert.assertEquals(1, queryResult.size());

	}

	/**
	 * 按审核时间查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午11:48:40
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryByAuditDate() {

		// 添加一套操作记录
		String codId = this.addCODEntity();

		// 防止程序报错，故意设置一个较小的日期
		Calendar inceptDate = Calendar.getInstance();
		inceptDate.set(1980, 10, 2, 14, 1);

		Calendar endDate = Calendar.getInstance();
		endDate.add(1, Calendar.DATE);
		// 空运审核日期（当前日期 + 1）
		Calendar airAuditDate = Calendar.getInstance();
		airAuditDate.add(Calendar.DATE, 1);

		// 查询获得代收货款记录
		CODEntity codEntity = this.codEntityDao.queryById(codId);

		// 执行更新
		long rows = codEntityDao.updateAirPaidCodStatus(codEntity,
				SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE,
				SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE);
		Assert.assertEquals(1, rows);

		AirBillPaidCODQueryDto dto = getDefaultQueryDto();

		// 查询条件
		dto.setDestOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		dto.setInceptAuditDate(inceptDate.getTime());
		dto.setEndAuditDate(endDate.getTime());
		dto.setCodType("codType");
		//已经统一
		dto.setAirStatus(SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE);

		// 判断行数
		rows = this.airBillPaidCodQueryDao.queryTotalRowsByAuditDate(dto).getTotalCount();
		Assert.assertEquals(true, rows >= 1);

		// 判断结果集大小
		List<AirBillPaidCODGridDto> queryResult = this.airBillPaidCodQueryDao
				.queryByAuditDate(dto, 0, 1);
		Assert.assertEquals(1, queryResult.size());

	}

	/**
	 * 新加一条记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-6 下午5:51:29
	 */
	private String addCODEntity() {
		// 防止程序报错，故意设置一个较小的日期
		Calendar currentDate = Calendar.getInstance();
		currentDate.set(1980, 10, 2, 14, 1);

		// 代收货款记录
		CODEntity codEntity = new CODEntity();
		codEntity.setId(UUIDUtils.getUUID());
		codEntity.setWaybillNo("Waybill001");
		codEntity.setCodType("codType");

		codEntity.setBusinessDate(new Date());
		codEntity.setIsInit("N");

		// 代收货款金额
		codEntity.setCodAmount(new BigDecimal("10000"));

		// 收款人
		codEntity.setPayeeName("payeeName");
		// codEntity.setPayeeCode("payeeCode");

		//
		codEntity.setPayableOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());

		codEntity.setBankHQName("ICBC");
		codEntity.setBatchNumber("2012110501");

		// 代收货款类型
		codEntity.setCodType("codType");
		// 代收货款状态
		codEntity.setStatus("Y");
		codEntity
				.setAirStatus(SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE);
		codEntity.setActive("Y");

		// 退款路径
		codEntity.setRefundPath("test");

		// 退款成功时间
		codEntity.setRefundSuccessTime(currentDate.getTime());
		// 代收货款空运组织审核时间
		codEntity.setAirOrgAuditTime(currentDate.getTime());

		// 设置付款申请时间
		codEntity.setCodExportTime(currentDate.getTime());
		codEntity.setTusyorgRfdApptime(currentDate.getTime());
		// 业务日期
		codEntity.setBusinessDate(currentDate.getTime());

		this.getCodEntityDao().addCod(codEntity);

		// 测试添加应付单
		addBillPayable(currentDate);

		// 添加应收单
		this.addBillReceivable(currentDate);

		return codEntity.getId();

	}

	/**
	 * 测试添加应付单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:22:05
	 */
	private void addBillPayable(Calendar currentDate) {
		// 同时添加应付单
		BillPayableEntity billPayable = new BillPayableEntity();
		billPayable.setId(UUIDUtils.getUUID());
		billPayable.setWaybillNo("Waybill001");
		billPayable.setWaybillId("waybillId");

		billPayable.setPayableNo("001");
		billPayable.setActive(FossConstants.ACTIVE);
		billPayable.setCreateType("AUTO_TEST");
		billPayable.setBillType("COD_TYPE");

		billPayable.setPayableType("codType");
		billPayable.setSourceBillType("waybill");
		billPayable.setSourceBillNo("waybill");
		// 代收货款金额
		billPayable.setAmount(new BigDecimal("100000"));
		// 已经核销金额
		billPayable.setVerifyAmount(BigDecimal.ZERO);
		// 未核销金额
		billPayable.setUnverifyAmount(new BigDecimal("100000"));
		// 应付组织编码
		billPayable.setPayableOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		// 生效状态
		billPayable.setEffectiveStatus("Y");
		// 支付状态
		billPayable.setPayStatus("N");

		billPayable.setCurrencyCode("RMB");
		billPayable.setAccountDate(new Date());
		billPayable.setBusinessDate(new Date());
		billPayable.setIsRedBack("N");
		billPayable.setIsInit("N");
		billPayable.setCustomerContactName("payeeName");
		billPayable.setVersionNo(FossConstants.INIT_VERSION_NO);
		billPayable.setSignDate(currentDate.getTime()); //签收时间

		// 到达组织
		billPayable.setDestOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		// 应付单的单据子类型 = 应付代收货款
		billPayable
				.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		int rows = this.billPayableEntityDao.add(billPayable);

		Assert.assertEquals(1, rows);
	}

	/**
	 * 测试添加应收单
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午5:22:05
	 */
	private void addBillReceivable(Calendar currentDate) {
		// 同时添加应付单
		BillReceivableEntity billReceivable = new BillReceivableEntity();
		billReceivable.setId(UUIDUtils.getUUID());
		billReceivable.setWaybillNo("Waybill001");
		billReceivable.setWaybillId("waybillId");

		billReceivable.setActive(FossConstants.ACTIVE);
		billReceivable.setCreateType("AUTO_TEST");
		billReceivable.setBillType("COD_TYPE");

		billReceivable.setSourceBillType("waybill");
		billReceivable.setSourceBillNo("waybill");
		// 代收货款金额
		billReceivable.setAmount(new BigDecimal("100000"));
		// 已经核销金额
		billReceivable.setVerifyAmount(BigDecimal.ZERO);
		// 未核销金额
		billReceivable.setUnverifyAmount(new BigDecimal("100000"));
		// 应付组织编码
		billReceivable.setReceivableOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		// 应收单单号
		billReceivable.setReceivableNo("Waybill001");

		billReceivable.setCurrencyCode("RMB");
		billReceivable.setAccountDate(new Date());
		billReceivable.setBusinessDate(new Date());
		billReceivable.setIsRedBack("N");
		billReceivable.setIsInit("N");
		billReceivable.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 到达组织
		billReceivable.setDestOrgCode(ConsumerTestUtil.getCurrentInfo()
				.getCurrentDeptCode());
		
		// 应收单的单据子类型-空运代理代收货款应收单
		billReceivable
				.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);

		int rows = this.billReceivableEntityDao.add(billReceivable);

		Assert.assertEquals(1, rows);
	}

	/**
	 * 设置默认查询条件
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午10:55:31
	 */
	private AirBillPaidCODQueryDto getDefaultQueryDto() {

		AirBillPaidCODQueryDto dto = new AirBillPaidCODQueryDto();
		// 默认值
		// 未核销的应付单
		dto.setVerifyAmount(BigDecimal.ZERO);
		// 状态为有效
		dto.setActive(FossConstants.ACTIVE);
		// 应付单 等于 代收货款应付单BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
		dto.setBillPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		// 应收单 等于代收货款应收单BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
		dto.setBillReceivableType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);

		return dto;
	}

	public IAirBillPaidCODQueryDao getAirBillPaidCodQueryDao() {
		return airBillPaidCodQueryDao;
	}

	public void setAirBillPaidCodQueryDao(
			IAirBillPaidCODQueryDao airBillPaidCodDao) {
		this.airBillPaidCodQueryDao = airBillPaidCodDao;
	}

	public ICODEntityDao getCodEntityDao() {
		return codEntityDao;
	}

	public void setCodEntityDao(ICODEntityDao codEntityDao) {
		this.codEntityDao = codEntityDao;
	}

	public IBillReceivableEntityDao getBillReceivableEntityDao() {
		return billReceivableEntityDao;
	}

	public void setBillReceivableEntityDao(
			IBillReceivableEntityDao billReceivableEntityDao) {
		this.billReceivableEntityDao = billReceivableEntityDao;
	}

}
