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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/OperatingLogServiceTest.java
 * 
 * FILE NAME        	: OperatingLogServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.module.settlement.common.test.util.CommonTestUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 操作日志测试类
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-8 上午11:15:39
 */
public class OperatingLogServiceTest extends BaseTestCase {

	@Autowired
	private IOperatingLogService operatingLogService;

	
	/**
	 * 测试新加操作日志
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午11:20:24
	 */
	@Test
	public void testAddOperatingLog(){
		OperatingLogEntity logEntity = new OperatingLogEntity();
		
		logEntity.setId(UUIDUtils.getUUID());

		//设置默认值
		logEntity.setOperateOrgCode("operateOrgCode");
		logEntity.setOperateBillNo("foss00001");
		logEntity.setOperateBillType("billType");
		
		logEntity.setOperatorCode("operatorCOde");
		logEntity.setOperateType("testAddLog");
		logEntity.setOperateTime(new Date());
		
		
		//检查是否插入成功
		this.operatingLogService.addOperatingLog(logEntity,CommonTestUtil.getCurrentInfo());
		Assert.assertEquals(1, 1);
		
		//通过查询验证是否插入成功
		List<OperatingLogEntity> queryResult = this.operatingLogService.queryByOperateBillNo("foss00001");
		Assert.assertEquals(1, queryResult.size());
	}

	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}
	
	private OperatingLogEntity getOperatingLogEntity(){
		OperatingLogEntity logEntity = new OperatingLogEntity();
		logEntity.setId(UUIDUtils.getUUID());
		
		//设置默认值
		logEntity.setOperateOrgCode("operateOrgCode");
		logEntity.setOperateBillNo("foss00001");
		logEntity.setOperateBillType("billType");
		
		logEntity.setOperatorCode("operatorCOde");
		logEntity.setOperateType("testAddLog");
		logEntity.setOperateTime(new Date());
		return logEntity;
	}
	
	/**
	 * 新增多条操作日志信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-25 下午2:37:32
	 */
	@Test
	@Rollback(false)
	public void testAddBatchOperatingLog(){
		List<OperatingLogEntity> list=new ArrayList<OperatingLogEntity>();
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		list.add(this.getOperatingLogEntity());
		
		this.operatingLogService.addBatchOperatingLog(list);
	}
	
}
