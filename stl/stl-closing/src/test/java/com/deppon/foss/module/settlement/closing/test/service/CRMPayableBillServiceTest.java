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
 * PROJECT NAME	: stl-closing
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/closing/test/service/CRMPayableBillServiceTest.java
 * 
 * FILE NAME        	: CRMPayableBillServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.closing.test.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.server.service.ICRMPayableBillService;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateRes;
import com.deppon.foss.module.settlement.closing.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;


/**
 * 测试
 * @author foss-qiaolifeng
 * @date 2013-1-25 上午11:40:17
 */
public class CRMPayableBillServiceTest extends BaseTestCase {

	/** CRM应付单服务. */
	@Autowired
	private ICRMPayableBillService crmPayableBillService;
	
	@Test
	@Rollback(false)
	public void testAddCRMBillPayable() {
		
		ClaimsPayBillGenerateRes request = new ClaimsPayBillGenerateRes();
		request.setBankPayInfo(null);
		request.setBillNo("101511120");
		request.setBusinessType(SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__REFUND);
		request.setClaimMoney(new BigDecimal(1000));
		request.setClaimType(null);
		request.setClaimWay(null);
		request.setCreatorNo("999999");
		request.setCustNo("400037946");
		request.setDeptNo("GS00002");
		request.setPayBillLastTime(new Date());
		
		CurrentInfo currentInfo = SettlementUtil.getCRMCurrentInfo();
		
		try{
			
			crmPayableBillService.addCRMBillPayable(request, currentInfo);
		}catch(BusinessException e){
			this.logger.info("The Exp:"+e.getMessage());
		}
	}

}
