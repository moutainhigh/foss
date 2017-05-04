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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/service/impl/DictDownloadService.java
 * 
 * FILE NAME        	: DictDownloadService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.dict.server.service.impl;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.server.service.IDictDownloadService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 数据字典 下载服务实现类
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-22 上午11:43:19
 * @since
 * @version
 */
public class DictDownloadService implements IDictDownloadService {
	 /**
	 * 
	 */
	private static final int BEFOREAMOUNT = 200;
    /**
	 * 
	 */
	private static final int THOUSAND = 1000;
    /**
     * 数据字典-词DAO
     */
    private IDataDictionaryDao dataDictionaryDao;

    /**
     * 数据字典-值DAO
     */
    private IDataDictionaryValueDao dataDictionaryValueDao;

    /**
     * 系统配置参数DAO
     */
    private IConfigurationParamsDao configurationParamsDao;

    /** 
     * 数据字典-词数据下载 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:7:48
     * @param clientInfo
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadDataDictionaryData(com.deppon.foss.base.util.ClientUpdateDataPack)
     */
    public DataBundle downloadDataDictionaryData(ClientUpdateDataPack clientInfo) {
	DataBundle db = new DataBundle();
	if (clientInfo == null){
	    return db;
	}
	DataDictionaryEntity entity = new DataDictionaryEntity();
	entity.setModifyDate(clientInfo.getLastUpdateTime());
	
	return db.setObject(dataDictionaryDao.queryDataDictionaryForDownload(entity));
    }

    /** 
     * 数据字典-值数据下载 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:22:25
     * @param clientInfo
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadDataDictionaryValueData(com.deppon.foss.base.util.ClientUpdateDataPack)
     */
    public DataBundle downloadDataDictionaryValueData(ClientUpdateDataPack clientInfo) {
	DataBundle db = new DataBundle();
	if (clientInfo == null){
	    return db;
	}
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	//entity.setModifyDate(clientInfo.getLastUpdateTime());
	if(clientInfo.getLastUpdateTime()!= null){
		entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	}
	
	if(FossConstants.YES.equals(clientInfo.getPagination())){
		//该表太大 需要分页
	
		return db.setObject(dataDictionaryValueDao.queryDataDictionaryValueForDownloadByPage(entity,
				(clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND));
		
	}else{
		return db.setObject(dataDictionaryValueDao.queryDataDictionaryValueForDownload(entity));
	}
	
    }
    

    /** 
     * 系统配置参数数据下载 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:30:36
     * @param clientInfo
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBaseInfoDownloadService#downloadConfigurationParamsData(com.deppon.foss.base.util.ClientUpdateDataPack)
     */
    public DataBundle downloadConfigurationParamsData(ClientUpdateDataPack clientInfo) {
	DataBundle db = new DataBundle();
	if (clientInfo == null){
	    return db;
	}
	ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
//	entity.setModifyDate(clientInfo.getLastUpdateTime());
//	entity.setOrgCode(clientInfo.getOrgCode());
	
	if(clientInfo.getLastUpdateTime()!=null){
		entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
	}
	
	return db.setObject(configurationParamsDao.queryConfigurationParamsForDownload(entity));
    }
    
    /**
     * 下面是一些get,set方法：
     */
    public void setConfigurationParamsDao(
    	IConfigurationParamsDao configurationParamsDao) {
        this.configurationParamsDao = configurationParamsDao;
    }

    public void setDataDictionaryValueDao(
    	IDataDictionaryValueDao dataDictionaryValueDao) {
        this.dataDictionaryValueDao = dataDictionaryValueDao;
    }
    public void setDataDictionaryDao(
    	IDataDictionaryDao dataDictionaryDao) {
        this.dataDictionaryDao = dataDictionaryDao;
    }

}
