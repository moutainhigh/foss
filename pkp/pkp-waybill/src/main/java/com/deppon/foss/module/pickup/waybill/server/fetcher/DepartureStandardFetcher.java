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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/fetcher/DepartureStandardFetcher.java
 * 
 * FILE NAME        	: DepartureStandardFetcher.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;

/**
 * * 获取发出信息
 * @author 026123-foss-lifengteng
 *
 */
@Service
@Transactional
public class DepartureStandardFetcher  extends AbstractSyncDataFetcherWaybill<DepartureStandardEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<DepartureStandardEntity> syncDataDao;
	
	/**
	 * @功能：获取数据同步dao接口 
	 *  
	 */
	public ISyncDataDao<DepartureStandardEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncDepartureStandard")
	public void setSyncDataDao(ISyncDataDao<DepartureStandardEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}

}