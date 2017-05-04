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
 * PROJECT NAME	: stl-pay
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/pay/test/ClaimNumServiceTest.java
 * 
 * FILE NAME        	: ClaimNumServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.test;

import java.math.BigDecimal;

import javax.xml.ws.Holder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.esb.header.ESBHeader;
import com.deppon.fin.module.claimpay.service.IFinClaimNumService;
import com.deppon.fin.module.claimpay.shared.domain.ObtainClaimNumRequest;
import com.deppon.fin.module.claimpay.shared.domain.ObtainClaimNumResponse;
import com.deppon.fin.module.claimpay.shared.domain.QueryTransFerRequest;
import com.deppon.fin.module.claimpay.shared.domain.QueryTransFerResponse;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumRequest;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumResponse;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RemitTransferQueryResultDto;
import com.deppon.foss.util.UUIDUtils;

public class ClaimNumServiceTest extends BaseTestCase {

	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private IFinClaimNumService claimNumService;

//	@Before
//	public void before() {
//		claimNumService = (IFinClaimNumService) this.applicationContext
//				.getBean("claimNumServiceClient");
//	}

	/**
	 * 占用
	 * @author foss-qiaolifeng
	 * @date 2012-12-19 下午2:07:40
	 */
	@Test
	@Ignore
	public void testClaimNumObtainService() throws Exception {
		ObtainClaimNumRequest request = new ObtainClaimNumRequest();
		request.setClaimAmount(new BigDecimal("2300"));
		request.setClaimBillNum("E448001");
		request.setClaimDeptCode("1");
		
		// 实例化ESBHeader，并设置到Holder对象中
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_OBTAIN_NUMBER");
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

		ObtainClaimNumResponse response = claimNumService
				.obtainClaimNum(holder, request);
		
		logger.info(response);
		if (response != null) {
			logger.info(response.isResult());
			logger.info(response.getReason());
		}
	}
	
	
	
	/**
	 * 释放
	 * @author foss-qiaolifeng
	 * @date 2012-12-19 下午2:09:36
	 */
	@Test
	@Ignore
	public void testClaimNumService() throws Exception {
		ReleaseClaimNumRequest request = new ReleaseClaimNumRequest();
		request.setClaimAmount(new BigDecimal("2300"));
		request.setClaimBillNum("E448001");
		request.setClaimDeptCode("1");
		
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_RELEASE_NUMBER");
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		ReleaseClaimNumResponse response = claimNumService
				.releaseClaimNum(holder,request);

		logger.info(response);
		if (response != null) {
			logger.info(response.isResult());
			logger.info(response.getReason());
		}
	}
	
	/**查询汇款信息
	 * @author foss-qiaolifeng
	 * @date 2012-12-19 下午2:09:36
	 */
	@Test
	public void testqueryTransferClaimNumService() throws Exception {
		
		
		String remitTransNum = "D1000930";
		QueryTransFerRequest request = new QueryTransFerRequest();
		request.setRemitTransNum(remitTransNum);

		// 实例化ESBHeader，并设置到Holder对象中
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_QUERY_TRANSFER);
		//与业务相关的字段
		header.setBusinessId(remitTransNum);
		header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
		header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
		//消息格式
		header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
		header.setRequestId(UUIDUtils.getUUID());
	    //请求系统
		header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

		QueryTransFerResponse response = claimNumService
				.queryTransFer(holder, request);

	
		RemitTransferQueryResultDto resultDto = new RemitTransferQueryResultDto();
		resultDto.setRemitName(response.getRemitName());
		resultDto.setRemitDate(response.getRemitDate());
		resultDto.setClaimState(response.getClaimState());
		resultDto.setClaimDeptName(response.getClaimDeptName());
		resultDto.setClaimDeptNo(response.getClaimDeptNo());
		resultDto.setNoCancelAmount(response.getNoCancelAmount());

	}

}
