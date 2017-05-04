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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/fetcher/DataDictionaryValueFetcher.java
 * 
 * FILE NAME        	: DataDictionaryValueFetcher.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.fetcher;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.entity.SyncDataRequest;
import com.deppon.foss.framework.server.components.sync.ISyncDataDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;


/**
 * 数据字典-值数据获取类
 * @author 105089-foss-yangtong
 * @date 2012-11-7 下午4:02:15
 */
@Service
@Transactional
public class DataDictionaryValueFetcher extends AbstractSyncDataFetcherWaybill<DataDictionaryValueEntity> {
	/**
	 * 数据抓取Dao接口
	 */
	private ISyncDataDao<DataDictionaryValueEntity> syncDataDao;
	/** 
     * 银行接口
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
	public ISyncDataDao<DataDictionaryValueEntity> getSyncDataDao() {
		return syncDataDao;
	}
	/**
	 * 设置数据抓取Dao接口
	 * @param syncDataDao
	 */
	@Resource(name="syncDataDictionaryValue")
	public void setSyncDataDao(ISyncDataDao<DataDictionaryValueEntity> syncDataDao) {
		this.syncDataDao = syncDataDao;
	}
	 
	public List<DataDictionaryValueEntity> getSyncData(SyncDataRequest request) {
		
	
		if(request.getList()==null || request.getList().isEmpty()){
			return getSyncDataDao().getSyncData(request.getFromDate(),
					request.getUserID(), 
					request.getRegionID(), 
					request.getPagination(), 
					request.getSyncPage());
		}else{
			return getSyncDataDao().getSyncData(request);
		}
		
		
	}
}