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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/waybill/StlWaybillPickupServiceTest.java
 * 
 * FILE NAME        	: StlWaybillPickupServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.waybill;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.web.test.util.TestUtil;

/**
 * 结算开单Service 测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-29 下午1:56:52
 * @since
 * @version
 */
public class StlWaybillPickupServiceTest extends TestCase {

	protected final Logger logger = LoggerFactory
			.getLogger(StlWaybillPickupServiceTest.class);

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单信息dto
	 */
	private WaybillPickupInfoDto waybill;

	/**
	 * 开单Service
	 */
	private IWaybillPickupService waybillPickupService;

	/**
	 * 始发部门编码
	 */
	private String origOrgCode;

	/**
	 * 始发部门名称
	 */
	private String origOrgName;

	/**
	 * 始发操作者编码
	 */
	private String origUserCode;

	/**
	 * 始发操作者名称
	 */
	private String origUserName;

	/**
	 * 运单到达部门编码
	 */
	private String destOrgCode;

	/**
	 * 到达部门名称
	 */
	private String destOrgName;

	/**
	 * 其他测试类对其赋值：接送货运单Service
	 */
	private IWaybillManagerService waybillManagerService;

	/**
	 * jdbc连接Template
	 */
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public StlWaybillPickupServiceTest() {
	}

	public StlWaybillPickupServiceTest(String testMethod) {
		super(testMethod);
	}

	/**
	 * 公用 添加 财务运单单据信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午7:12:33
	 */
	public void testAddWaybill() {
		try {
			waybillPickupService
					.addWaybill(this.getWaybill(), getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 添加接送货运单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午8:25:33
	 */
	private void addPkpWaybill() {
		String sql = TestUtil.getInsertWaybillSql(this.getWaybillNo(), waybill);
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);

		// 保存运单成功后，查询运单信息
		WaybillEntity waybillEntity = waybillManagerService
				.queryWaybillBasicByNo(this.getWaybillNo());
		BeanUtils.copyProperties(waybillEntity, waybill);
	}

	/**
	 * 添加运单财务信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:20:11
	 */
	private void addStlWaybill() {
		try {
			waybillPickupService
					.addWaybill(this.getWaybill(), getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode(), e);
		}
	}

	/**
	 * 构造当前操作者信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:49:47
	 * @return
	 */
	private CurrentInfo getCurrentInfo() {
		return TestUtil.getCurrentInfo(this.getOrigUserCode(),
				this.getOrigUserName(), this.getOrigOrgCode(),
				this.getOrigOrgName());
	}

	/**
	 * 开单现金
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午8:21:18
	 */
	public void testAddWaybillForCash() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCash(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单现金，且包含有代收货款的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:23:56
	 */
	public void testAddWaybillForCashAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCashAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单现金，且有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:40:10
	 */
	public void testAddWaybillForCashAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCashAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单现金，含有代收货款，且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:40:26
	 */
	public void testAddWaybillForCashAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCashAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单银行卡
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:41:51
	 */
	public void testAddWaybillForCard() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCard(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单银行卡，且存在代收货款金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:41:51
	 */
	public void testAddWaybillForCardAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCardAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单银行卡，且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:41:51
	 */
	public void testAddWaybillForCardAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCardAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单银行卡，含有代收货款且需要支付装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午10:41:51
	 */
	public void testAddWaybillForCardAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCardAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单支付方式-临欠
	 * 
	 */
	public void testAddWaybillForDt() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForDt(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单为临欠，且包含代收货款的
	 * 
	 */
	public void testAddWaybillForDtAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForDtAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单为临欠，且有装卸费的
	 * 
	 */
	public void testAddWaybillForDtAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForDtAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单为临欠，包含代收货款和装卸费
	 * 
	 */
	public void testAddWaybillForDtAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForDtAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为-月结
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:31:22
	 */
	public void testAddWaybillForCt() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCt(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为-月结,且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:31:54
	 */
	public void testAddWaybillForCtAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCtAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单为月结，包含有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:31:54
	 */
	public void testAddWaybillForCtAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCtAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为-月结,且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:31:54
	 */
	public void testAddWaybillForCtAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForCtAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单支付方式-网上支付
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:32:52
	 */
	public void testAddWaybillForOnLine() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForOnLine(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单支付方式-网上支付,且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:33:14
	 */
	public void testAddWaybillForOnLineAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForOnLineAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单支付方式-网上支付,且包含有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:33:14
	 */
	public void testAddWaybillForOnLineAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForOnLineAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单支付方式-网上支付,且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:33:14
	 */
	public void testAddWaybillForOnLineAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForOnLineAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 获取开单付款方式为--全部为到付
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:33:40
	 */
	public void testAddWaybillForFc() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFc(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--全部为到付，且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:34:25
	 */
	public void testAddWaybillForFcAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--全部为到付，且有装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:34:25
	 */
	public void testAddWaybillForFcAndService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--为到付，且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:34:25
	 */
	public void testAddWaybillForFcAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:35:11
	 */
	public void testAddWaybillForFcAndCash() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCash(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有代收货款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:35:11
	 */
	public void testAddWaybillForFcAndCashAndCod() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCashAndCod(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有装卸费
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:35:11
	 */
	public void testAddWaybillForFcAndCashService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCashService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * 开单付款方式为--到付，且始发预付款大于0(存在现金收款单)，且包含有代收货款和装卸费的
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 上午9:35:11
	 */
	public void testAddWaybillForFcAndCashAndCodService() {
		this.setWaybill(TestUtil.getWaybillPickupInfoDtoForFcAndCashAndCodService(
				this.getWaybillNo(),
				SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT,
				this.getOrigOrgCode(), this.getDestOrgCode()));
		this.addPkpWaybill();
		this.addStlWaybill();
	}

	/**
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午8:35:38
	 * @throws Exception
	 */
	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	/**
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午8:35:38
	 * @throws Exception
	 */
	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	/**
	 * @return the waybillPickupService
	 */
	public IWaybillPickupService getWaybillPickupService() {
		return waybillPickupService;
	}

	/**
	 * @param waybillPickupService
	 *            the waybillPickupService to set
	 */
	public void setWaybillPickupService(
			IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	/**
	 * @param origOrgCode
	 *            the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}

	/**
	 * @param origOrgName
	 *            the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	/**
	 * @return the origUserCode
	 */
	public String getOrigUserCode() {
		return origUserCode;
	}

	/**
	 * @param origUserCode
	 *            the origUserCode to set
	 */
	public void setOrigUserCode(String origUserCode) {
		this.origUserCode = origUserCode;
	}

	/**
	 * @return the origUserName
	 */
	public String getOrigUserName() {
		return origUserName;
	}

	/**
	 * @param origUserName
	 *            the origUserName to set
	 */
	public void setOrigUserName(String origUserName) {
		this.origUserName = origUserName;
	}

	/**
	 * @return the waybillManagerService
	 */
	public IWaybillManagerService getWaybillManagerService() {
		return waybillManagerService;
	}

	/**
	 * @param waybillManagerService
	 *            the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return the simpleJdbcTemplate
	 */
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	/**
	 * @param simpleJdbcTemplate
	 *            the simpleJdbcTemplate to set
	 */
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	/**
	 * @return the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 *            the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * @return the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	/**
	 * @param destOrgName
	 *            the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * @return the waybill
	 */
	public WaybillPickupInfoDto getWaybill() {
		return waybill;
	}

	/**
	 * @param waybill
	 *            the waybill to set
	 */
	public void setWaybill(WaybillPickupInfoDto waybill) {
		this.waybill = waybill;
	}

	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
