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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/WaybillChangeMsgServiceTest.java
 * 
 * FILE NAME        	: WaybillChangeMsgServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IWaybillChangeMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillChangeMsgEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 财务变更消息 Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-4 下午7:48:06
 * @since
 * @version
 */
public class WaybillChangeMsgServiceTest extends BaseTestCase{
	
	/**
	 * 财务变更消息 Service
	 */
	@Autowired
	private IWaybillChangeMsgService waybillChangeMsgService;
	
	public void SetUp(){}
	
	
	private WaybillChangeMsgEntity getWaybillChangeMsgEntity(){
		WaybillChangeMsgEntity entity=new WaybillChangeMsgEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setWaybillNo(CommonTestUtil.getWaybillNO());
		entity.setMsgDate(new Date());
		
		//消息动作-新增
		entity.setMsgAction(SettlementDictionaryConstants.WAYBILL_CHANGE_MSG__MSG_ACTION__PROCESSING);
		entity.setSourceBillType("应收单");
		entity.setSourceBillNo(CommonTestUtil.getReceivableNo());//应收单号
		return entity;
	}
	
	/**
	 * 新增财务变更信息内容
	 *
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午7:50:02
	 */
	@Test
	@Rollback(true)
	public void testAddChangeMsg(){
		WaybillChangeMsgEntity entity=getWaybillChangeMsgEntity();
		this.waybillChangeMsgService.addChangeMsg(entity);
	}
	
	/**
	 * 删除财务变更信息内容
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午7:59:22
	 */
	@Test
	@Rollback(true)
	public void testDeleteChangeMsg(){
		WaybillChangeMsgEntity entity=getWaybillChangeMsgEntity();
		this.waybillChangeMsgService.addChangeMsg(entity);
		this.waybillChangeMsgService.deleteChangeMsgByBatch(Arrays.asList(entity));
	}
}
