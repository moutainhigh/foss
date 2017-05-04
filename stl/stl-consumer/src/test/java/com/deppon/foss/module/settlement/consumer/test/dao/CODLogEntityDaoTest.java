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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/consumer/test/dao/CODLogEntityDaoTest.java
 * 
 * FILE NAME        	: CODLogEntityDaoTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.test.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.jgroups.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODLogEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.test.BaseTestCase;
/**
 * 
 * 代收货款记录日志测试
 * @author dp-huangxb
 * @date 2012-10-13 下午5:10:53
 */
public class CODLogEntityDaoTest extends BaseTestCase {
	
	
	@Autowired
	private ICODLogEntityDao codLogEntityDao;

	/**
	 * 
	 * 测试新加CodLog
	 * @author dp-huangxb
	 * @date 2012-10-13 下午5:18:07
	 */
	@Test
	public void testAddCodLog(){
		int addResult = addCodLog();
		Assert.assertEquals(1, addResult);
	}

	/**
	 * 新加操作日志
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午10:40:27
	 */
	private int addCodLog() {
		CODLogEntity entity = new CODLogEntity();
		
		entity.setId(UUID.randomUUID().toString()); //Id
		entity.setCodId("codId");
		entity.setWaybillNo("w1231231");
		entity.setBusinessDate(new Date());
		entity.setOperateTime(new Date());
		entity.setOperateBillType("operateBillType");
		entity.setOperateActiontype("operateActiontype");
		entity.setActive("1");
		// 加入
		int addResult = codLogEntityDao.addCODLog(entity);
		return addResult;
	}
	
	/**
	 * 测试查询方法
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 上午10:33:06
	 */
	@Test
	public void testQueryByWaybillNumber(){
		
		//先插入
		int addResult = addCodLog();
		Assert.assertEquals(1, addResult);
		
		//然后验证插入结果
		List<CODLogEntity> codLogEntitySet = this.getCodLogEntityDao().queryByWaybill("w1231231");
		Assert.assertEquals(1, codLogEntitySet.size());
		
	}
	
	

	public ICODLogEntityDao getCodLogEntityDao() {
		return codLogEntityDao;
	}

	public void setCodLogEntityDao(ICODLogEntityDao codLogEntityDao) {
		this.codLogEntityDao = codLogEntityDao;
	}
	
}
