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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/CODEntityDaoTest.java
 * 
 * FILE NAME        	: CODEntityDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.dao;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODQueryByWaybillDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款记录DaoTest
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-26 下午4:45:39
 */
public class CODEntityDaoTest extends BaseTestCase {

	@Autowired
	private ICODEntityDao codEntityDao;

	/**
	 * 测试添加
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午4:53:43
	 */
	@Test
	public void testAddCODEntity() {

		CODEntity codEntity = new CODEntity();
		codEntity.setId(UUIDUtils.getUUID());
		codEntity.setWaybillNo("Waybill001");
		codEntity.setCodType("codType");

		codEntity.setBusinessDate(new Date());
		codEntity.setIsInit("N");
		codEntity.setActive("Y");
		codEntity.setStatus("Y");
		codEntity.setCodAmount(new BigDecimal(0));
		int result = this.getCodEntityDao().addCod(codEntity);
		Assert.assertEquals(1, result);

	}

	/**
	 * 
	 * 测试运单进行查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午9:55:45
	 */
	@Test
	public void testQueryByWaybill() {
		this.testAddCODEntity();

		CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
		dto.setWaybillNo("Waybill001");
		dto.setActive(FossConstants.ACTIVE);
		CODEntity codEntity = this.getCodEntityDao().queryByWaybill(dto);
		Assert.assertEquals("Waybill001", codEntity.getWaybillNo());
	}

	/**
	 * 更新银行帐号信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午5:14:45
	 */
	@Test
	public void testUpdateBankAccounts() {

		// 信息记录
		this.testAddCODEntity();

		// 根据运单单号查询代收货款记录
		CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
		dto.setWaybillNo("Waybill001");
		dto.setActive(FossConstants.ACTIVE);
		CODEntity codEntity = this.getCodEntityDao().queryByWaybill(dto);
		codEntity.setBankHQName("ICBC");
		codEntity.setBankBranchCode("007");
		codEntity.setBankBranchName("徐泾");
		codEntity.setCityName("上海");
		codEntity.setProvinceName("上海");
		codEntity.setAccountNo("622588200");
		codEntity.setPayeeName("huangxb");
		codEntity.setPayeePhone("13916510057");

		// 执行更新
		int result = this.getCodEntityDao().updateBankAccounts(codEntity);
		Assert.assertEquals(1, result);

		// 判断是否更新成功
		CODQueryByWaybillDto queryByWaybillDto = new CODQueryByWaybillDto();
		queryByWaybillDto.setWaybillNo("Waybill001");
		queryByWaybillDto.setActive(FossConstants.ACTIVE);
		codEntity = this.getCodEntityDao().queryByWaybill(queryByWaybillDto);
		Assert.assertEquals("Waybill001", codEntity.getWaybillNo());
		Assert.assertEquals("ICBC", codEntity.getBankHQName());
		Assert.assertEquals("007", codEntity.getBankBranchCode());
		Assert.assertEquals("徐泾", codEntity.getBankBranchName());
		Assert.assertEquals("上海", codEntity.getProvinceName());
		Assert.assertEquals("上海", codEntity.getCityName());
		Assert.assertEquals("622588200", codEntity.getAccountNo());
		Assert.assertEquals("huangxb", codEntity.getPayeeName());
		Assert.assertEquals("13916510057", codEntity.getPayeePhone());

	}

	/**
	 * 更新冻结状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午11:00:35
	 */
	@Test
	public void testUpdateFreezeState() {
		// 新加
		this.testAddCODEntity();

		// 查询插入结果
		CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
		dto.setWaybillNo("Waybill001");
		dto.setActive(FossConstants.ACTIVE);
		CODEntity codEntity = this.getCodEntityDao().queryByWaybill(dto);

		// 对状态进行初始化
		codEntity.setStatus("FreezeState");

		// 执行更新
		int result = this.getCodEntityDao().updateFreezeState(codEntity);
		Assert.assertEquals(1, result);

		// 校验更新状态
		CODQueryByWaybillDto queryByWaybillDto = new CODQueryByWaybillDto();
		queryByWaybillDto.setWaybillNo("Waybill001");
		queryByWaybillDto.setActive(FossConstants.ACTIVE);
		codEntity = this.getCodEntityDao().queryByWaybill(dto);
		Assert.assertEquals("FreezeState", codEntity.getStatus());

	}

	/**
	 * 更新支付状态
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午11:26:22
	 */
	@Test
	public void updatePaymentState() {
		// 新加
		this.testAddCODEntity();

		// 查询插入结果
		CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
		dto.setWaybillNo("Waybill001");
		dto.setActive(FossConstants.ACTIVE);
		CODEntity codEntity = this.getCodEntityDao().queryByWaybill(dto);

		// 对状态进行初始化
		codEntity.setStatus("PaymentState");

		// 执行更新
		int result = this.getCodEntityDao().updatePaymentState(codEntity);
		Assert.assertEquals(1, result);

		// 校验更新状态
		codEntity = this.getCodEntityDao().queryByWaybill(dto);
		Assert.assertEquals("PaymentState", codEntity.getStatus());
	}

	public ICODEntityDao getCodEntityDao() {
		return codEntityDao;
	}

	public void setCodEntityDao(ICODEntityDao codEntityDao) {
		this.codEntityDao = codEntityDao;
	}

}
