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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/dao/IDataDictionaryDao.java
 * 
 * FILE NAME        	: IDataDictionaryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;

/**
 * DAO接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-17 上午9:57:2
 */
public interface IDataDictionaryDao {

    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    DataDictionaryEntity addDataDictionary(DataDictionaryEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    DataDictionaryEntity deleteDataDictionary(DataDictionaryEntity entity);

    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    DataDictionaryEntity deleteDataDictionaryMore(DataDictionaryEntity entity);

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    DataDictionaryEntity updateDataDictionary(DataDictionaryEntity entity);

    
    
    /**
     * 以下全为查询
     */

    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    List<DataDictionaryEntity> queryDataDictionaryByEntity(
	    DataDictionaryEntity entity, int limit, int start);

    /**
     * 查询queryDataDictionaryByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    long queryDataDictionaryByEntityCount(DataDictionaryEntity entity);

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    long queryDataDictionaryCount(String columnName,String columnValue);

    /**
     * 根据teamsCode查询（根据“词代码”查询“值”对象）
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-17 上午9:57:2
     */
    DataDictionaryEntity queryDataDictionaryByTermsCode(String teamsCode);
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:6:45
     */
    List<DataDictionaryEntity> queryDataDictionaryForDownload(DataDictionaryEntity entity);
    
    
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
