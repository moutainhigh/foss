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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/service/CreditMsgServiceTest.java
 * 
 * FILE NAME        	: CreditMsgServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.common.api.server.service.ICreditMsgService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditMsgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditMsgDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;

/**
 * 财务收支平衡消息 Service 测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-18 下午1:43:07
 * @since
 * @version
 */
public class CreditMsgServiceTest extends BaseTestCase{
	
	@Autowired
	private ICreditMsgService creditMsgService;
	
	public void setUp(){
		
	}
	
	/**
	 * 获取一个财务收支平衡消息实体
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:40:52
	 * @return
	 */
	private CreditMsgEntity getCreditMsgEntity(){
		CreditMsgEntity entity=new CreditMsgEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setType(SettlementDictionaryConstants.CREDIT_MSG_TYPE__WRITEOFF);//核销
		entity.setSourceBillNo("YS123456");
		entity.setCode("W04061620");
		entity.setName("测试");
		entity.setCreditType(SettlementDictionaryConstants.CREDIT_MSG_CREDIT_TYPE__ORG);//部门临欠
		entity.setAmount(new BigDecimal("1000"));
		entity.setCreateTime(new Date());
		entity.setStatus(SettlementDictionaryConstants.CREDIT_MSG_STATUS_NOT_EXECUTE);//状态未执行
		return entity;
	}
	
	/**
	 * 新增财务收支平衡消息
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:29:52
	 */
	@Test
	@Rollback(true)
	public void testAdd(){
		try{
			CreditMsgEntity entity=this.getCreditMsgEntity();
			this.creditMsgService.add(entity);
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 查询时间段内（结束时间默认为系统时间）所有未执行的财务收支平衡消息表
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:31:27
	 */
	@Test
	@Rollback(true)
	public void testQueryCreditMsgEntity(){
		try{
			CreditMsgEntity entity=this.getCreditMsgEntity();
			this.creditMsgService.add(entity);
			List<CreditMsgEntity> list=this.creditMsgService.queryCreditMsgEntity(new Date(),"");
			Assert.assertTrue(CollectionUtils.isNotEmpty(list));
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 *  批量修改财务收支平衡消息的状态为已执行
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:31:48
	 */
	@Test
	@Rollback(true)
	public void testBatchUpdateCreditMsgForStatus(){
		try{
			CreditMsgEntity entity=this.getCreditMsgEntity();
			this.creditMsgService.add(entity);
			List<CreditMsgEntity> list=this.creditMsgService.queryCreditMsgEntity(new Date(),"");
			CreditMsgDto dto=new CreditMsgDto();
			dto.setList(list);
			this.creditMsgService.batchUpdateCreditMsgForStatus(dto);
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 批量执行财务收支平衡消息表，来还原或增加客户的信用额度/部门的可用额度
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-21 上午8:34:33
	 */
	@Test
	@Rollback(true)
	public void testBatchUpdateCreditMsg(){
		try{
			CreditMsgEntity entity=this.getCreditMsgEntity();
			this.creditMsgService.add(entity);
			
			this.creditMsgService.batchUpdateCreditMsg(new Date());
		}catch(SettlementException e){
			
		}
	}

}
