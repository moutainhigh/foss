/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/waybill/server/dao/WaybillRfcVarificationDaoTest.java
 * 
 * FILE NAME        	: WaybillRfcVarificationDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillPaymentDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcForAccountServiceEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.util.define.FossConstants;

public class WaybillRfcVarificationDaoTest {
	private IWaybillRfcVarificationDao dao;
	private IWaybillDao pkpWaybillDao;
	private IWaybillPaymentDao paymentDao;
	private IWaybillPendingDao waybillPendingDao;
	private IWaybillRfcDao waybillRfcDao;
	WaybillRfcCondition con;
	@Before
	public void setUp() throws Exception {
		dao = TestHelper.get().getBeanByClass(WaybillRfcVarificationDao.class);
		pkpWaybillDao = TestHelper.get().getBeanByClass(WaybillDao.class); 
		paymentDao = TestHelper.get().getBeanByClass(WaybillPaymentDao.class);
		waybillPendingDao = TestHelper.get().getBeanByClass(WaybillPendingDao.class); 
		waybillRfcDao = TestHelper.get().getBeanByClass(WaybillRfcDao.class); 
		con = new WaybillRfcCondition();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}
	
	
	
	//@Test
	public void testInsertActionoHistory(){
		WaybillRfcActionHistoryEntity actionHistory = new WaybillRfcActionHistoryEntity();
		actionHistory.setCreateDate(new Date());
		actionHistory.setCreateUser("005072");
		actionHistory.setModifyDate(new Date());
		actionHistory.setNotes("更改单更改单");
		actionHistory.setWaybillRfcId("b97bfb88-6f9c-4732-baf4-746c7937d533");
		actionHistory.setStatus(WaybillRfcConstants.ACCECPT);
		actionHistory.setOperator("suyujun");
		actionHistory.setOperatorCode("005072");
		actionHistory.setOperateOrgName("上海德邦");
		actionHistory.setOperateOrgCode("123121");
		actionHistory.setOperateTime(new Date());
		dao.addWaybillRfcActionHistory(actionHistory);
	}
	
	//@Test
	public void testGetWaybillEntityById(){
		String waybillId = "2db4407d-5b6d-48d3-8247-719fb3d324eb";
		WaybillEntity e = pkpWaybillDao.getWaybillEntityById(waybillId);
		Assert.assertNotNull(e);
	}
	
	//@Test
	public void testUpdateWaybillPayment(){
		String waybillNo = "741276498";
		List<WaybillPaymentEntity> list = paymentDao.queryPaymentEntityByNo(waybillNo);
		for(WaybillPaymentEntity e : list){
			e.setActive(FossConstants.NO);
			e.setType("测试类型");
			paymentDao.updateWaybillPaymentEntity(e);
		}
	}
	
	//@Test
	public void testQueryPending() {
		WaybillPendingDto waybillPendingDto = new WaybillPendingDto();
		// 运单号/订单号
		waybillPendingDto.setMixNo("2012004");
		WaybillPendingEntity waybillPending = new WaybillPendingEntity();
		// 运单类型为PDA待处理
		waybillPending.setPendingType(WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		waybillPendingDto.setWaybillPending(waybillPending);
		waybillPending.setProductCode("all");
		waybillPendingDto.setCreateStartTime(new Date());
//		waybillPendingDto.setCreateEndTime(new Date());
		waybillPendingDao.queryPending(waybillPendingDto);
	}
	
	//@Test
	public void testApplyChangeOrder() {
		WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity = new WaybillRfcForAccountServiceEntity();
		waybillRfcForAccountServiceEntity.setActive(FossConstants.INACTIVE);
		waybillRfcForAccountServiceEntity.setApplyPerson("用户名1");
		waybillRfcForAccountServiceEntity.setApplyTime(new Date());
		waybillRfcForAccountServiceEntity.setChangeContent("更改单修改1");
		waybillRfcForAccountServiceEntity.setContactName("联系人A");
		waybillRfcForAccountServiceEntity.setUnifieldCode("标杆编码A");
		waybillRfcForAccountServiceEntity.setWaybillNumber("运单号123");
		waybillRfcDao.applyChangeOrder(waybillRfcForAccountServiceEntity);
	}
	
	//@Test
	public void testQueryChangeOrder() {
		WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition = new WaybillRfcForAccountServiceCondition();
		waybillRfcForAccountServiceCondition.setActive(FossConstants.INACTIVE);
		waybillRfcForAccountServiceCondition.setApplyPerson("用户名1");
		waybillRfcForAccountServiceCondition.setStartDate(new Date());
		waybillRfcForAccountServiceCondition.setWaybillNumber("运单号123");
		waybillRfcDao.queryChangeOrder(waybillRfcForAccountServiceCondition);
	}
	
	//@Test
	public void testQueryChangeOrderList() {
		WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition = new WaybillRfcForAccountServiceCondition();
		waybillRfcForAccountServiceCondition.setActive(FossConstants.INACTIVE);
		waybillRfcForAccountServiceCondition.setApplyPerson("用户名1");
		waybillRfcForAccountServiceCondition.setStartDate(new Date());
		waybillRfcForAccountServiceCondition.setWaybillNumber("运单号123");
		waybillRfcDao.queryChangeOrderList(waybillRfcForAccountServiceCondition, 0, 999);
	}
	
	//@Test
	public void testQueryChangeOrderCount() {
		WaybillRfcForAccountServiceCondition waybillRfcForAccountServiceCondition = new WaybillRfcForAccountServiceCondition();
		waybillRfcForAccountServiceCondition.setActive(FossConstants.INACTIVE);
		waybillRfcForAccountServiceCondition.setApplyPerson("用户名1");
//		waybillRfcForAccountServiceCondition.setStartDate(new Date());
//		waybillRfcForAccountServiceCondition.setWaybillNumber("运单号123");
		waybillRfcDao.queryChangeOrderCount(waybillRfcForAccountServiceCondition);
	}
	
	//@Test
	public void testUpdateWaybillRfcForAccountServiceEntity() {
		WaybillRfcForAccountServiceEntity waybillRfcForAccountServiceEntity = new WaybillRfcForAccountServiceEntity();
		String [] s = {"73424ee9-eefa-40ad-90e2-e5ba49ea02e7","6946ce73-9422-42da-a5c6-6ea519bf5050"};
		waybillRfcForAccountServiceEntity.setIds(s);
		waybillRfcForAccountServiceEntity.setActive(FossConstants.ACTIVE);
		waybillRfcForAccountServiceEntity.setProcessTime(new Date());
		waybillRfcForAccountServiceEntity.setProcessOrgCode("操作部门code");
		waybillRfcForAccountServiceEntity.setProcessOrgName("操作部门name");
		waybillRfcForAccountServiceEntity.setProcessUserCode("操作人code");
		waybillRfcForAccountServiceEntity.setProcessUserName("操作人name");
		waybillRfcDao.updateWaybillRfcIds(waybillRfcForAccountServiceEntity);
	}
	
	//@Test
	public void testQueryWaybillRfcPrintDto(){
		WaybillRfcPrintDto dto = dao.queryWaybillRfcPrintDto("99300008");
		System.out.println(dto.getChangeList().size());
		System.out.println(dto.getRfcType());
	}
	
	//@Test
	public void testQueryWaybillRfcPrintDtoByRfcid(){
		WaybillRfcPrintDto dto = dao.queryWaybillRfcPrintDtoByRfcid("1b60a491-69f7-450b-848d-f285de57615c");
		System.out.println(dto.getChangeList().size());
		System.out.println(dto.getRfcType());
	}
}