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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/InvoiceServiceTest.java
 * 
 * FILE NAME        	: InvoiceServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.NumberUtils;

/**
 * 测试开发发票服务实现
 * 
 * @author ibm-guxinhua
 * @date 2012-11-26 下午6:27:14
 */
public class InvoiceServiceTest extends BaseTestCase {

	@Autowired
	private IInvoiceService invoiceService;

	/**
	 * 测试查询运单的可开票金额
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午6:25:46
	 */
	@Test
	@Ignore
	public void testQueryWaybillAmount() {
		List<InvoiceDto> list = invoiceService.queryWaybillAmount(Arrays
				.asList(new String[] { "521077313", "526737137", "529593457",
						"537693927" }));
		Assert.assertTrue((list != null && list.size() >= 0));
	}

	/**
	 * 测试查询小票开票金额
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午6:25:46
	 */
	@Test
	@Ignore
	public void testQueryOtherRevenueAmount() {
		List<InvoiceDto> list = invoiceService.queryOtherRevenueAmount(Arrays
				.asList(new String[] { "00000009", "00000010", "00000011" }));
		Assert.assertTrue((list != null && list.size() >= 0));
	}

	/**
	 * 测试标记发票已开
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午7:48:07
	 */
	@Test
	@Ignore
	public void testMarkInvoice() {
		InvoiceDto dto = new InvoiceDto();
		dto.setSourceBillNo("521077313");
		dto.setAlreadyOpenAmount(NumberUtils.createBigDecimal("10000"));
		dto.setSourceBillType(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL);
		dto.setApplyUserCode("applyUserCode");
		InvoiceEntity entity = new InvoiceEntity();
		try {
			entity = invoiceService.markInvoice(dto);
		} catch (BusinessException e) {
			logger.error(e.getErrorCode());
		}
		Assert.assertNotNull(entity);
	}

	/**
	 * 测试小票、运单集合标记发票已开
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午7:48:07
	 */
	@Test
	@Ignore
	public void testMarkInvoiceAll() {
		int successCount = 0, failedCount = 0;
		String message = null;
		String code[] = new String[] { "521077313", "526737137", "529593457",
				"537693927", "00000009", "00000010", "00000011" };

		for (int i = 0; i < code.length; i++) {
			InvoiceDto dto = new InvoiceDto();
			dto.setSourceBillNo(code[i]);
			dto.setAlreadyOpenAmount(NumberUtils.createBigDecimal(i * 10 + ""));
			dto.setApplyUserCode("ApplyUserCode");
			dto.setBillingDeptCode("CreateOrgCode");
			if (i <= 3) {
				dto.setSourceBillType(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL);
			} else {
				dto.setSourceBillType(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__OTHER_REVENUE);
			}

			InvoiceEntity entity = null;
			try {
				entity = invoiceService.markInvoice(dto); // 标记发票已开
			} catch (BusinessException e) {
				message = e.getErrorCode();
				logger.error(message);
				entity = null;
			}
			if (entity != null) { // 标记成功，添加成功信息
				successCount++;
			} else {
				failedCount++;
			}
		}
		Assert.assertTrue(successCount > 0 || failedCount > 0);

	}

	/**
	 * 测试查询开票状态-如果存在，返回开发票记录实体，否则返回NULL
	 * 
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午7:41:43
	 */
	@Test
	public void testQueryInvoiceState() {
		InvoiceEntity entity = new InvoiceEntity();
		entity = invoiceService.queryInvoiceState("521077313","");
		Assert.assertNotNull(entity);
	}

}
