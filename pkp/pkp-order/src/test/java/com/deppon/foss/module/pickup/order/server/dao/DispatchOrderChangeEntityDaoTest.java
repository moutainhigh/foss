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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/order/server/dao/DispatchOrderChangeEntityDaoTest.java
 * 
 * FILE NAME        	: DispatchOrderChangeEntityDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;

public class DispatchOrderChangeEntityDaoTest extends TestCase {
	
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	
	private static ApplicationContext ctx = null;
	
	String[] xmls = new String[] {
			"com/deppon/foss/module/pickup/order/test/META-INF/spring.xml",
			"com/deppon/foss/module/pickup/order/server/META-INF/spring.xml" };
	
	@Override
	protected void setUp() throws Exception {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(xmls);
		}
		dispatchOrderChangeEntityDao = (IDispatchOrderChangeEntityDao) ctx.getBean("dispatchOrderChangeEntityDao");
		
	}
	
	@Transactional
	public void testAddTempChange(){
		List<DispatchOrderChangeEntity> orderChangeList = dispatchOrderChangeEntityDao.queryChange();
		System.out.println(orderChangeList.size());
	}
	
	@Transactional
	public void testDeleteTempChange(){
		List<DispatchOrderChangeEntity> orderChangeList = dispatchOrderChangeEntityDao.queryChange();
		dispatchOrderChangeEntityDao.deleteChange(orderChangeList);
	}
	
	@Transactional
	public void testQueryOrder(){
		List<DispatchOrderChangeEntity> orderChangeList = dispatchOrderChangeEntityDao.queryChange();
		List<DispatchOrderEntity> list =dispatchOrderChangeEntityDao.queryOrder(orderChangeList);
		System.out.println(list.size());
	}
}