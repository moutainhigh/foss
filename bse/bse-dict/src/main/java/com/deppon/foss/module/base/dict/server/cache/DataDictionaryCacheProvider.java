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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/cache/DataDictionaryCacheProvider.java
 * 
 * FILE NAME        	: DataDictionaryCacheProvider.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.cache.provider.IBatchCacheProvider;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:数据字典缓存数据提供对象</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 1 2012-11-13 钟庭杰 新增 </div>
 ******************************************** 
 */
public class DataDictionaryCacheProvider implements
		IBatchCacheProvider<String, DataDictionaryEntity> {
	
	private IDataDictionaryValueDao dataDictionaryValueDao;
	
	@Override
	public Map<String, DataDictionaryEntity> get() {
		Map<String, DataDictionaryEntity> map = new HashMap<String, DataDictionaryEntity>();
		List<DataDictionaryEntity> dataDictionarys = dataDictionaryValueDao.queryDataForCache();
		for (DataDictionaryEntity dataDictionary : dataDictionarys) {
			map.put(dataDictionary.getTermsCode(), dataDictionary);
		}
		return map;
	}

	/**
	 * 返回最后修改时间最小的值
	 */
	public Date getLastModifyTime() {
		Date dataValueLastModifyTime = dataDictionaryValueDao.getLastModifyTime();
		return dataValueLastModifyTime;
	}

	public IDataDictionaryValueDao getDataDictionaryValueDao() {
		return dataDictionaryValueDao;
	}

	public void setDataDictionaryValueDao(
			IDataDictionaryValueDao dataDictionaryValueDao) {
		this.dataDictionaryValueDao = dataDictionaryValueDao;
	}

}
