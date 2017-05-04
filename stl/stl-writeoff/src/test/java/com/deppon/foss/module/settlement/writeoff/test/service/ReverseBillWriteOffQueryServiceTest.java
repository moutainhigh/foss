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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/writeoff/test/service/ReverseBillWriteOffQueryServiceTest.java
 * 
 * FILE NAME        	: ReverseBillWriteOffQueryServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.writeoff.test.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReverseBillWriteoffService;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.ReverseBillWriteoffDto;
import com.deppon.foss.module.settlement.writeoff.test.BaseTestCase;
import com.deppon.foss.module.settlement.writeoff.test.CommonTestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 反核消
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 下午2:07:52
 */
public class ReverseBillWriteOffQueryServiceTest extends BaseTestCase {

	@Autowired
	private IReverseBillWriteoffService reverseBillWriteoffService;

	/**
	 * 反核消查询测试
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-10-24 下午2:15:00
	 */
	@Test
	@Rollback(true)
	public void testReverseBillWriteOffQueryTest() {
		// queryBillWriteoffEntityListByParams();
	}

	// 根据参数，查询核销单列表
	public void queryBillWriteoffEntityListByParams() {

		/**
		 * 模拟传入的参数
		 */
		ReverseBillWriteoffDto dto = new ReverseBillWriteoffDto();

		// 业务时间
		Date opDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(opDate);// 设置当前日期
		calendar.add(Calendar.MONTH, -1);// 月份减一
		opDate = calendar.getTime();
		dto.setStartBusinessDate(opDate);
		dto.setEndBusinessDate(new Date());

		// 部门编码
		dto.setOrgCode("YS1");

		// 客户编码
		dto.setCustomerCode("CUST1");

		// 是否红单
		dto.setIsRedBack(FossConstants.NO);
		//
		// 隐含条件，是否有效
		dto.setActive(FossConstants.YES);

		// 获取当前登录用户信息
		CurrentInfo cInfo = CommonTestUtil.getCurrentInfo();

		// 根据传入的运单号和单据类型等参数，查询[到付运费]有效应收单信息
		ReverseBillWriteoffDto dtoResult = reverseBillWriteoffService
				.queryBillWriteoffEntityList(dto, 0, 1000, cInfo);

		Assert.assertNotNull(dtoResult);
	}

	// 根据核销单号，查询核销单列表
	public void queryBillWriteoffEntityListByNo() {

		/**
		 * 模拟传入的参数
		 */
		ReverseBillWriteoffDto dto = new ReverseBillWriteoffDto();

		// 设置核销单号
		dto.setWriteoffBillNo("HX00001032");

		// 获取当前登录用户
		CurrentInfo cInfo = CommonTestUtil.getCurrentInfo();

		// 根据传入的运单号和单据类型等参数，查询[到付运费]有效应收单信息
		List<BillWriteoffEntity> dtoResult = reverseBillWriteoffService
				.queryBillWriteoffEntityByNo(dto, cInfo);

		Assert.assertNotNull(dtoResult);
	}

	// 根据核销单号，反核销
	public void reverseBillWriteOff() {

		// 正常核销
		try {
			/**
			 * 模拟传入的参数
			 */
			ReverseBillWriteoffDto dto = new ReverseBillWriteoffDto();

			// 设置核销单号，正常单号
			dto.setWriteoffBillNo("HX00001032");

			 //获取当前登录用户
			CurrentInfo cInfo = CommonTestUtil.getCurrentInfo();
			
			 reverseBillWriteoffService.reverseBillWriteOff(dto, cInfo);
		} catch (SettlementException ex) {
			Assert.assertNull(ex);
		}

		// 异常核销
		try {
			/**
			 * 模拟传入的参数
			 */
			ReverseBillWriteoffDto dto = new ReverseBillWriteoffDto();

			// 设置核销单号，错误的单号
			dto.setWriteoffBillNo("HX0z0r1c3&0");

			// 获取当前登录用户
			 CurrentInfo cInfo = CommonTestUtil.getCurrentInfo();
			
			 reverseBillWriteoffService.reverseBillWriteOff(dto, cInfo);
		} catch (SettlementException ex) {
			Assert.assertNotNull(ex);
		}

	}

}
