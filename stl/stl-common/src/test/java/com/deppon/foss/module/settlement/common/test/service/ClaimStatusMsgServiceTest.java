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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/ClaimStatusMsgServiceTest.java
 * 
 * FILE NAME        	: ClaimStatusMsgServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IClaimStatusMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.ClaimStatusMsgEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 理赔支付状态消息 Service测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-4 下午6:47:43
 * @since
 * @version
 */
public class ClaimStatusMsgServiceTest extends BaseTestCase{
	@Autowired
	private IClaimStatusMsgService claimStatusMsgService;
	
	public void setUp(){
		
	}
	
	/**
	 * 理赔支付状态消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午5:17:57
	 * @return
	 */
	private ClaimStatusMsgEntity getClaimStatusMsgEntity(){
		ClaimStatusMsgEntity entity=new ClaimStatusMsgEntity();
		entity.setId(UUIDUtils.getUUID());//ID
		entity.setWaybillNo(CommonTestUtil.getWaybillNO());//运单号
		entity.setMsgDate(new Date());
		
		//消息动作--核销成功
		entity.setMsgAction(SettlementDictionaryConstants.
						CLAIM_STATUS_MSG__MSG_ACTION__PASS);
		
		entity.setMsgContent("核销成功生成消息");
		
		//消息状态---新增
		entity.setMsgStatus(SettlementDictionaryConstants.
						CLAIM_STATUS_MSG__MSG_STATUS__NEW);
		return entity;
	}
	
	/**
	 * 新增理赔支付状态消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午5:18:17
	 */
	@Test
	@Rollback(true)
	public void testAddClaimStatusMsg(){
		this.claimStatusMsgService.addClaimStatusMsg(getClaimStatusMsgEntity());
	}
	
	/**
	 * 删除新增理赔支付状态消息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午5:27:59
	 */
	@Test
	@Rollback(false)
	public void testDeleteClaimStatusMsg(){
		ClaimStatusMsgEntity entity=getClaimStatusMsgEntity();
		this.claimStatusMsgService.addClaimStatusMsg(entity);
		this.claimStatusMsgService.deleteClaimStatusMsg(entity.getId());
	}
}
