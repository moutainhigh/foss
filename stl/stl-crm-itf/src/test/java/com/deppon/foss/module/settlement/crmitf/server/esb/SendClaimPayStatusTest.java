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
 * PROJECT NAME	: stl-crm-itf
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/crmitf/server/esb/SendClaimPayStatusTest.java
 * 
 * FILE NAME        	: SendClaimPayStatusTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.crmitf.server.esb;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.ReturnVoucherPaymentResultRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.api.util.ESBInitUtil;

/**
 * 发送理赔应付单付款状态测试
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-27 上午8:45:08
 */
public class SendClaimPayStatusTest {

	@Before
	public void init() throws Exception {
		ESBInitUtil util = new ESBInitUtil();
		util.process();
	}

	/**
	 * 
	 * 发送理赔应付单付款状态
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-27 上午8:46:48
	 */
	@Test
	@Ignore
	public void sendClaimPayStatus() throws Exception {

		ReturnVoucherPaymentResultRequest request = new ReturnVoucherPaymentResultRequest();
		String waybillNum = "TEST_WAYBILL_NUM";

		request.setDeptCode("TEST_DEPT_CODE");
		request.setAmount(new BigDecimal("500"));
		request.setCustCode("TEST_CUST_CODE");
		request.setPaymentType("TEST_PAYMENT_TYPE");
		request.setWaybillNum(waybillNum);
		request.setPayResult(true);
		request.setReason(null);

		AccessHeader header = buildHeader(waybillNum);

		ESBJMSAccessor.asynReqeust(header, request);
	}

	/**
	 * 
	 * 返回ESB请求头消息
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:41:17
	 */
	private AccessHeader buildHeader(String waybillNum) {

		AccessHeader header = new AccessHeader();
		header.setBusinessId(waybillNum);
		header.setEsbServiceCode("ESB_FOSS2ESB_NOTIFY_CLAIMS_STATE");
		header.setVersion("1.0");
		header.setBusinessDesc1("通知理赔支付状态");

		return header;

	}
}
