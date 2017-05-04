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

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.provider.ITTLCacheProvider;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;


/**
 * 数据字典缓存类
 * @author 088933-foss-zhangjiheng
 * @date 2013-4-16 下午4:16:05
 */
public class DataDictionaryValueCacheProvider implements ITTLCacheProvider<DataDictionaryValueEntity> {
	
	private IDataDictionaryValueDao dataDictionaryValueDao;
	
	@Override
	public DataDictionaryValueEntity get(String key) {
		//判断传入的参数是否为空
		if(StringUtil.isBlank(key)){
			return null;
		}
		//定义词条编码
		String termsCode=null;
		//定义值编码
		String valueCode=null;
		//用于存放分割后的字符
		String[] strList=key.split(SymbolConstants.EN_COMMA);
		if(strList.length>0){
			termsCode=strList[0];
			valueCode=strList[1];
		}
		return  (DataDictionaryValueEntity)dataDictionaryValueDao.
				queryDataDictionaryValueByTermsCodeValueCode(termsCode,valueCode);
	}

	public void setDataDictionaryValueDao(
			IDataDictionaryValueDao dataDictionaryValueDao) {
		this.dataDictionaryValueDao = dataDictionaryValueDao;
	}

	

}
