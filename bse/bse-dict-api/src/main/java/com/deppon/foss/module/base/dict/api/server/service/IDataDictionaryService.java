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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/service/IDataDictionaryService.java
 * 
 * FILE NAME        	: IDataDictionaryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
/**
 *  Service接口
 * @author 087584-dp-lijun
 * @date 2012-10-13 下午7:26:39
 */
public interface IDataDictionaryService extends IService {
    /**
     * 插入
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    DataDictionaryEntity addDataDictionary(DataDictionaryEntity entity);
    /**
     * 根据CODE删除
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    DataDictionaryEntity deleteDataDictionary(DataDictionaryEntity entity);
    /**
     * 根据CODE批量删除
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    DataDictionaryEntity deleteDataDictionaryMore(DataDictionaryEntity entity);
	
    /**
     * 更新 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    DataDictionaryEntity updateDataDictionary(DataDictionaryEntity entity);
	
	
    /**
     * 以下全为查询 
     */
    
    /**
     * 查询所有数据字典对象：
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-14 上午10:53:20
     */
	List<DataDictionaryEntity> queryDataDictionarys(); 

    /**
     * 根据 词条查询
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    DataDictionaryEntity queryDataDictionaryByTermsCode(String code);
    
    /**
     * 根据词条集合查询数据字典对象：
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-14 上午10:53:20
     */
    List<DataDictionaryEntity> queryDataDictionaryByTermsCodes(
			List<String> termsCodes);
	
    /**
     * 根据entity模糊查询，
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    List<DataDictionaryEntity> queryDataDictionaryByEntity(DataDictionaryEntity entity,int limit,int start);
	
    /**
     * 查询queryDataDictionaryByEntity返回的记录总数,用于分页
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午7:26:39
     */
    long queryDataDictionaryByEntityCount(DataDictionaryEntity entity);

    /**
     * 查询 columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-15 上午13:3:43
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IDataDictionaryDao#queryCount(java.lang.String,
     *      java.lang.String)
     */
    long queryDataDictionaryCountByTermsCode(String columnValue);
    
    
    
    
    /**
     * 下面是特殊查询
     */
    
    
    
    /**
     * 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值：
     * 动态的查询条件。 如果传入的为空，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-9 下午11:18:56
     */
    List<DataDictionaryEntity> queryDataDictionaryByCond(
	    String termsCode,String termsName,String valueCode,String valueName, 
	    int start, int limit);
    
    /**
     * 查询总条数      
     * 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值：
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-9 下午11:19:52
     */    
    long queryDataDictionaryByCondCount(String termsCode,String termsName,String valueCode,String valueName);
	
}
