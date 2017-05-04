/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IMaketingEventService.java
 * 
 * FILE NAME        	: IMaketingEventService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.MarketingEventDto;


public interface IMaketingEventService extends IService {
	
	/**
	 * 
	 * <p>
	 * Description: 根据主键查找价格折扣方案<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * MarketingEventEntity
	 */
	 MarketingEventEntity selectByPrimaryKey(String id);
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询折扣方案列表（目前条件为：方案名称、方案状态、起始结束时间）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-23
	 * @param marketintEvent
	 * @param start
	 * @param limit
	 * @return
	 * List<MarketingEventDto>
	 */
	 List<MarketingEventDto> selectMarketingEventList(MarketingEventEntity marketintEvent, int start, int limit);
	
	/**
	 * 
	 * <p>
	 * Description:新增价格折扣方案（只有字段值不为空的才被存入）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param marketingEventEntity
	 * @return
	 * int
	 */
	 int addMaketingEventSelective(MarketingEventEntity marketingEventEntity);
	
	
	/**
	 * 
	 * <p>
	 * Description:根据主键更新价格折扣方案（只有字段值不为空的才会被更新）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param marketingEventEntity
	 * @return
	 * int
	 */
	 int updateByPrimaryKeySelective(MarketingEventEntity marketingEventEntity);
	
	/**
	 * 
	 * <p>
	 * Description:根据主键ID删除状态为“新建”的价格折扣方案<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-18
	 * @param id
	 * @return
	 * int
	 */
	 int deleteByPrimaryKey(MarketingEventEntity marketingEventEntity);
	
	/**
	 * 启用折扣方案（只能启用状态为“新建的”记录）
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-23
	 * @param marketingEventEntity
	 * @return
	 * int
	 */
	 int activeMarketEvents(MarketingEventEntity marketingEventEntity);
	
	/**
	 * 
	 * <p>
	 * Description:结束折扣方案（只能结束状态为“启用”的记录）<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-23
	 * @param marketingEventEntity
	 * @return
	 * int
	 */
	 int disableMarketEvents(MarketingEventEntity marketingEventEntity);
	
	/**
	 * 
	 * <p>
	 * Description:复制一个旧的方案，并且版本号在原方案基础上加1，原有方案不变<br />
	 * </p>
	 * @author admin
	 * @version 0.1 2012-10-23
	 * @param id
	 * @return
	 * int
	 */
	 int copyOldtoNewVersion(String id);
	/**
	 * 
	 * @Description: 删除明细
	 * @author FOSSDP-Administrator
	 * @date 2013-3-17 下午4:41:15
	 * @return
	 * @version V1.0
	 */
	 int saveMarketingEventDetail();
	
}