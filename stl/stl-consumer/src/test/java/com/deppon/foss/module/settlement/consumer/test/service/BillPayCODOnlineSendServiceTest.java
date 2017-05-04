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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/service/BillPayCODOnlineSendServiceTest.java
 * 
 * FILE NAME        	: BillPayCODOnlineSendServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineSendService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
import com.deppon.foss.module.settlement.consumer.test.util.ConsumerTestUtil;

/**
 * 代收货款数据发送给银企测试类
 * 
 * @author ibm-zhuwei
 * @date 2013-1-7 上午9:42:50
 */
public class BillPayCODOnlineSendServiceTest extends BaseTestCase {

	@Autowired
	private IBillPayCODOnlineSendService billPayCODOnlineSendService;
	
	@Autowired
	private ICODBatchService codBatchService;
	
	/**
	 * 代收货款服务
	 */
	@Autowired
	private ICodCommonService codCommonService;

	@Test
	@Ignore
	@Rollback(false)
	public void testSendCODMessage() throws Exception {

		
		
		List<CODPayableDto> codList = new ArrayList<CODPayableDto>();
		List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();// 获得BillPayableEntity集合
		CODPayableDto cod = new CODPayableDto();
		CODEntity entity = codCommonService.queryById("c616cf02-df2d-4511-9fc5-04d8fab517c8");
		//CODEntity entity1 = codCommonService.queryById("c616cf02-df2d-4511-9fc5-04d8fab517c8");
		String codBatchNo = codBatchService.generateCODBatchNo("R3,RA");
		codBatchService.createCODBatchEntity(ConsumerTestUtil.getCurrentInfo(), codBatchNo,SettlementDictionaryConstants.COD_BATCH__STATUS__SENDING);
		
		/*entity = new CODEntity();
		entity.setPayableOrgName("德邦物流");
		entity.setPayableOrgCode("GS00002");
		entity.setWaybillNo("111104154");
		entity.setPayeeName("张三");
		entity.setCodAmount(new BigDecimal("5200"));
		entity.setAccountNo("6222021001054324048");
		entity.setBankBranchCode("W01254");
		entity.setProvinceCode("1");
		entity.setCityCode("2");
		entity.setBankBranchCode("3");
		entity.setPayeePhone("13917791627");
		entity.setPublicPrivateFlag(SettlementDictionaryConstants.COD__PUBLIC_PRIVATE_FLAG__RESIDENT);
		entity.setCodType(codType);*/
		entity.setProvinceCode("107");
		entity.setCityCode("131000");
		entity.setBankHQCode("507");
		entity.setBankBranchCode("102585002075");
		
		BeanUtils.copyProperties(entity, cod);
		
		codList.add(cod);
		//codList.add(entity1);
		BillPayableEntity billPayable = codCommonService.getBillPayableEntity(entity);
		//BillPayableEntity billPayable1 = codCommonService.getBillPayableEntity(entity1);
		billPayableEntityList.add(billPayable); // 添加实体
		//billPayableEntityList.add(billPayable1); // 添加实体
		
		
		/*billPayCODOnlineSendService.billPayCODOnlineSend(codBatchNo, 
				codList, billPayableEntityList);*/
	}

}
