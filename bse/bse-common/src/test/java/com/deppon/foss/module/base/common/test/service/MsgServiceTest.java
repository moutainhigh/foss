/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/common/test/service/MsgServiceTest.java
 * 
 * FILE NAME        	: MsgServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.test.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.base.common.api.server.service.IMessageService;


 
/**
 * 站内消息测试类
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-2 上午10:53:15
 */
public class MsgServiceTest  {
	
	
	@Autowired
	private IMessageService msgService;

	@Before
	public void setUp() {

	}
	
	@After
	public void tearDown() {
		
	}

	@Test
	@Rollback(false)
	public void testQueryBackLogMsgList() {
	}
	
	@Test
	public void testGetUserReceiveMsgCount() {
		/*Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("isSystem", "0");
		parameterMap.put("receiverId", "0102");
		int rt = -1;
		rt = msgService.getUserReceiveMsgCount(parameterMap);
		Assert.assertEquals(0, rt);*/
	}
	
	@Test
	public void testQueryMessageListByCondition() {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("startTime", new Date(System.currentTimeMillis()));
		parameterMap.put("endTime", new Date(System.currentTimeMillis()));
		parameterMap.put("isSystem", "0");
		/*List<MessageSearchDto> dtoList = msgService.queryMessageListByCondition(parameterMap);
		Assert.assertEquals(0, dtoList.size());*/
	}
	
	@Test
	public void testQueryBackLogList() {
		/*BackLogMsg logMsg = new BackLogMsg();
		//logMsg.setBusinessType("");
		List<BackLogMsg> msgList = msgService.queryBackLogList(logMsg);
		Assert.assertNotNull(msgList);*/
	}
	
//	@Test
//	@Rollback(true)
//	public void testUpdateBackLogById() {
//		msgService.updateBackLogById("3e786490-af67-4dcf-97d6-ebdaf8b66415");
//	}
}
