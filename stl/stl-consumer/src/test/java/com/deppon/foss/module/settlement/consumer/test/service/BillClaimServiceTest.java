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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillClaimServiceTest.java
 * 
 * FILE NAME        	: BillClaimServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;
import com.deppon.foss.util.define.FossConstants;


/**
 *  理赔测试
 * @author foss-qiaolifeng
 * @date 2013-1-31 上午9:35:38
 */
public class BillClaimServiceTest extends BaseTestCase {

	
	private static final Logger log = LogManager
			.getLogger(OutWarehouseExceptionServiceTest.class);

	@Autowired
	private IBillClaimService billClaimService;
	
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 退回理赔
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 下午1:34:55
	 */
	@Test
	@Rollback(true)
	public void returnBillClainEntityTest() {
		
		log.info("测试开始.......");
		try{
			
			String waybillNo = "810086002";
			String rtnReason = "退回原因测试";
			
			billClaimService.returnBillClainEntity(waybillNo, ConsumerTestUtil.getCurrentInfo(), rtnReason);
		}catch(BusinessException ex){
			Assert.assertNotNull(ex);
		}
		log.info("测试结束.......");
	}
	/**
	 * 冻结测试
	 * 
	 * 
	 * 
	 * 
	 * */
	@Test
	@Rollback(true)
	public void vaildChangeWaybillAndBillPlayableByCodEntityTest() {
		
		log.info("测试开始.......");
		try{
			
			SaleDepartmentEntity saleDept = saleDepartmentService.querySaleDepartmentInfoByCode("W31000206090611");
			if (null != saleDept) {//如果是空的话不执行
				if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept())) {
					// 应付单未代收货款，不能冻结
					if("WHSa"!=SettlementDictionaryConstants.STL__WITHHOLD_STATUS__WITHHOLD_SUCCESS){
						throw new SettlementException("运单[ test ]没有收款，不能资金部冻结");
					}
					//未付全额，不能冻结
					if(true){
						throw new SettlementException("运单[billPayable.getWaybillNo()]未付全额，不能资金部冻结");
					}
				}
			}
			
		}catch(BusinessException ex){
			Assert.assertNotNull(ex);
		}
		log.info("测试结束.......");
	}
}
