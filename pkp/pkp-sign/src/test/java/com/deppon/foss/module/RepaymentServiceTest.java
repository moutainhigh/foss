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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/RepaymentServiceTest.java
 * 
 * FILE NAME        	: RepaymentServiceTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.sign.api.server.dao.IRepaymentDao;
import com.deppon.foss.module.pickup.sign.api.shared.define.RepaymentConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


public class RepaymentServiceTest {/*
	private IRepaymentDao repaymentDao = null;

	private static ApplicationContext ctx = null;

	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/sign/test/META-INF/spring.xml"};
		
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || repaymentDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				repaymentDao = ctx.getBean(IRepaymentDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void repayment() 
	{	
		RepaymentEntity repaymentEntity = new RepaymentEntity();
		String UUID = UUIDUtils.getUUID();
		//先更新当前付款信息jobid
		repaymentEntity.setJobId(UUID);
		//更新为生成中
		repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
		//间隔30分
		repaymentEntity.setTimeRange(30);
		//有效
		repaymentEntity.setActive(FossConstants.YES);
		//初始财务状态
		repaymentEntity.setFirStlbillGeneratedStatus(RepaymentConstants.STLBILL_NOGENERATE);
		//初始JobId
		repaymentEntity.setDefJobId("N/A");
		repaymentDao.updateRepaymentListForJob(repaymentEntity);
		RepaymentEntity repaymentEntity1 = new RepaymentEntity();
		repaymentEntity1.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATEING);
		repaymentEntity1.setActive(FossConstants.YES);
		repaymentEntity1.setJobId(UUID);
//		List<RepaymentEntity> repaymentEntityList = repaymentDao.queryRepaymentListForJob(repaymentEntity1);
		for (RepaymentEntity irepaymentEntity : repaymentEntityList)
		{
			String message = operatepaymentSettlement(irepaymentEntity);
			if((SettlementConstants.CONFIRM_PAYMENT_SUCCESS_AND_PAYMENT_SETTLE).equals(message))
			{
				repaymentEntity.setStlbillGeneratedStatus(RepaymentConstants.STLBILL_GENERATED);
				repaymentDao.updateRepayment(repaymentEntity);
				//更新ActualFreight表中的结清状态为已结清
				ActualFreightEntity actualFreightEntity = actualFreightService.queryByWaybillNo(repaymentEntity.getWaybillNo());
				ActualFreightEntity actualFreightEntity1 = new ActualFreightEntity();
				actualFreightEntity1.setId(actualFreightEntity.getId());
				actualFreightEntity1.setSettleStatus("Y");
				actualFreightService.updateWaybillActualFreight(actualFreightEntity);
			}
		}
	}
	
	@Test
	public void updateRepayment()
	{
		RepaymentEntity repaymentEntity = new RepaymentEntity();
		repaymentEntity.setWaybillNo("2012008");
		List<RepaymentEntity> list = repaymentDao.queryRepaymentList(repaymentEntity);
		for(RepaymentEntity repay : list)
		{
			Date newDate = new Date();
			repay.setModifyTime(newDate);
			repay.setActive(FossConstants.INACTIVE);
			// 更新原始付款信息为无效
			repaymentDao.updateRepayment(repay);
			//新增付款信息
			repay.setPaymentTime(newDate);
			repaymentDao.addPaymentInfo(repay);
		}
	}
	
	@Test
	public void queryRepaymentListForSign()
	{
		String waybillNo = "2012117";
		String active = "Y";
		String stlbillGeneratedStatus = RepaymentConstants.STLBILL_GENERATED;
		String stlbillNotStatus = RepaymentConstants.STLBILL_NOTREQUIRE;
		repaymentDao.queryRepaymentListForSign(waybillNo, active, stlbillGeneratedStatus, stlbillNotStatus);
	}
*/}