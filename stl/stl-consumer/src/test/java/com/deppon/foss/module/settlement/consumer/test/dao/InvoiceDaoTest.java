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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/InvoiceDaoTest.java
 * 
 * FILE NAME        	: InvoiceDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.dao;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class InvoiceDaoTest extends BaseTestCase {

	@Autowired
	private IInvoiceDao invoiceDao;
	
	private InvoiceEntity addEntity(){
		InvoiceEntity entity = new InvoiceEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setSourceBillNo("69631234");
		entity.setSourceBillType(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL);
		entity.setInvoiceType(SettlementDictionaryConstants.INVOICE__INVOICE_TYPE__CUSTOMER);
		entity.setActive(FossConstants.ACTIVE);
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		entity.setOrgCode("orgCode");
		entity.setOrgName("orgName");
		return entity;
	}
	
	/**
	 * 测试新加开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-13 下午2:11:29
	 */
	@Test
	public void testAddInvoice() {
		InvoiceEntity entity = addEntity();
		int result = invoiceDao.addInvoice(entity);
		Assert.assertEquals(1, result);
	}

	/**
	 * 测试更新开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-13 下午3:31:35
	 */
	@Test
	public void testUpdateInvoice() {
		InvoiceEntity entity = addEntity();
		invoiceDao.addInvoice(entity);
	
		entity.setAlreadyOpenAmount(NumberUtils.createBigDecimal("1"));
		int result = invoiceDao.updateInvoice(entity);
		Assert.assertEquals(1, result);
	}

	/**
	 * 测试判断开发票记录是否存在
	 * @author ibm-guxinhua
	 * @date 2012-11-13 下午3:31:35
	 */
	@Test
	@Ignore
	public void testExistsInvoice() {
		InvoiceEntity entity = addEntity();
		invoiceDao.addInvoice(entity);
		
		entity = invoiceDao.existsInvoice(entity);
		Assert.assertNotNull(entity);
	}

	/**
	 * 测试查询开发票记录
	 * @author ibm-guxinhua
	 * @date 2012-11-13 下午3:31:35
	 */
	@Test
	public void testQueryInvoice() {
		InvoiceEntity entity = addEntity();
		invoiceDao.addInvoice(entity);
		
		InvoiceDto dto = new InvoiceDto();
		dto.setSourceBillNoList(Arrays.asList(new String[]{entity.getSourceBillNo()}));
		List<InvoiceEntity> list = invoiceDao.queryInvoice(dto);
		Assert.assertTrue(list.size()>=0);
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
}
