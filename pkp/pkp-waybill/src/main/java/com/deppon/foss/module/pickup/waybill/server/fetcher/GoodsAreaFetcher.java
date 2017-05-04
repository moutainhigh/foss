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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/fetcher/GoodsAreaFetcher.java
 * 
 * FILE NAME        	: GoodsAreaFetcher.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;



/**
 * 库区获取
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午3:54:05
 */
@Service
@Transactional
public class GoodsAreaFetcher extends AbstractSyncDataFetcherWaybill<GoodsAreaEntity>{
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<GoodsAreaEntity> syncDataDao;
	/** 
     * 库区接口
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
	public ISyncDataDao<GoodsAreaEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncGoodsArea")
	public void setSyncDataDao(ISyncDataDao<GoodsAreaEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}
	
	
}