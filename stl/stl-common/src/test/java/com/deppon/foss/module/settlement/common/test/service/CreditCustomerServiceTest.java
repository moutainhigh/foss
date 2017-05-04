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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/CreditCustomerServiceTest.java
 * 
 * FILE NAME        	: CreditCustomerServiceTest.java
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CustomerInUseDto;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;

/**
 * 客户收支凭证服务测试
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-17 下午6:02:23
 */
public class CreditCustomerServiceTest extends BaseTestCase
{

	@Autowired
	private ICreditCustomerService creditCustomerService;

	/**
	 * 测试更新客户信用额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午6:03:31
	 */
	@Test
	public void testUpdateCreditCustomer()
	{
		CreditCustomerEntity item = this.getCreditCustomerEntity();
		this.creditCustomerService.addCreditCustomer(item);
		// 执行更新
		this.creditCustomerService.updateCreditCustomer(item);
	}

	/**
	 * 构建一个客户欠款额度信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-17 下午6:05:07
	 */
	private CreditCustomerEntity getCreditCustomerEntity()
	{
		CreditCustomerEntity item = new CreditCustomerEntity();
		item.setCustomerCode("00001");
		item.setCustomerName("18M");
		item.setUsedAmount(new BigDecimal(0));
		item.setBusinessDate(new Date());
		item.setModifyTime(new Date());
		item.setCreateTime(new Date());
		item.setId(UUID.randomUUID().toString());
		return item;
	}

	@Test
	@Ignore
	public void testIsCustomerInUse()
	{
		List<String> customerCodes = new ArrayList<String>();
		customerCodes.add("649526");
		List<CustomerInUseDto> dtoList = creditCustomerService
				.isCustomerInUse(customerCodes);
		if (CollectionUtils.isNotEmpty(dtoList))
		{
			for (CustomerInUseDto dto : dtoList)
			{
				System.out.println(String.format("customerCode:%s,inUse:%s",
						dto.getCustomerCode(), dto.isInUse()));
			}
		}
	}

	public void setCreditCustomerService(
			ICreditCustomerService creditCustomerService)
	{
		this.creditCustomerService = creditCustomerService;
	}

}
