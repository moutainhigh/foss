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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PickupToDoMsgDto.java
 * 
 * FILE NAME        	: PickupToDoMsgDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceAirPlanDto;

/**
 * 
 * 空运价格区域下载
 * @author 026123-foss-lifengteng
 *
 */
@Service
@Transactional
public class PriceAirPlanDtoFetcher  extends AbstractSyncDataFetcherWaybill<PriceAirPlanDto> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<PriceAirPlanDto> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<PriceAirPlanDto> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncPriceAirPlanDto")
	public void setSyncDataDao(ISyncDataDao<PriceAirPlanDto> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}


