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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/dao/IPublishPriceDao.java
 * 
 * FILE NAME        	: IPublishPriceDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceExpressDto;

public interface IPublishPriceExpressDao {
	/**
	 * 查询公布价价格区域 
	 * @author sz
	 * @date 2012-11-29 下午3:45:49
	 * @param startDeptNo
	 * @param destinationCode
	 * @param receiveDate
	 * @param type
	 * @return
	 * @see
	 */
	 List<PublishPriceExpressDto> queryPublishPriceValuationByCalculaCondition(PublishPriceExpressDto dto);
	
	/**
	 * 
	 * @Description: 根据出发和到达区域ID集合查询公布价价格区域
	 * @author FOSSDP-sz
	 * @date 2013-1-25 下午4:20:20
	 * @param deptRegionIds
	 * @param arrvieRegionIds
	 * @param type
	 * @param active
	 * @param billDate
	 * @return
	 * @version V1.0
	 */
	 List<PublishPriceExpressDto> queryPublishPriceByRegionIds(List<String> deptRegionIds, List<String> arrvieRegionIds,
			String type, String active, Date billDate);
}