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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/dao/IDataDictionaryValueDao.java
 * 
 * FILE NAME        	: IDataDictionaryValueDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
/**
 *  DAO接口
 * @author 087584-foss-lijun
 * @date 2012-10-18 下午4:6:38
 */
public interface IDataDictionaryValueDao {
    /**
     * 插入
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDictionaryValueEntity addDataDictionaryValue(DataDictionaryValueEntity entity);
    /**
     * 根据CODE删除
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDictionaryValueEntity deleteDataDictionaryValue(DataDictionaryValueEntity entity);
    /**
     * 根据CODE批量删除
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDictionaryValueEntity deleteDataDictionaryValueMore(DataDictionaryValueEntity entity);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDictionaryValueEntity updateDataDictionaryValue(DataDictionaryValueEntity entity);
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据编码查询
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDictionaryValueEntity queryDataDictionaryValueByVirtualCode(String code);	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByCode(java.lang.String)
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午12:35:36
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueExactByEntity(
	    DataDictionaryValueEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午12:36:02
     */
    long queryDataDictionaryValueExactByEntityCount(DataDictionaryValueEntity entity);
    
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueByEntity(DataDictionaryValueEntity entity, int start, int limit);
	
    /**
     * 模糊查询
     * 查询queryDataDictionaryValueByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    long queryDataDictionaryValueByEntityCount(DataDictionaryValueEntity entity);
    /**
     * 根据贵重物品词代码查询值得序号最大值
     * @author 132599-foss-shenweihua
     * @param termsCode 贵重物品词代码
     * @return
     */
    long queryMaxValue(String termsCode);
		
	
    /** 
     * 精确查询
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    long queryDataDictionaryValueCount(String columnName,String columnValue);
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:21:56
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueForDownload(DataDictionaryValueEntity entity);
		
	
    
    
    /**
     * 以下为特殊查询 
     */
    

    
    /**
     * 动态的查询条件。 
     * 
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @param termsCode 上级词条 精确查询
     * @param valueCode 值代码 模糊查询
     * @param valueName 值名称 模糊查询
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueForView(
	    DataDictionaryValueEntity entity, int start, int limit);
    

    /**
     * 动态的查询条件-查询总条数。
     * 
     *  如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @param termsCode 上级词条 精确查询
     * @param valueCode 值代码 模糊查询
     * @param valueName 值名称 模糊查询
     */
    long queryDataDictionaryValueForViewCount(DataDictionaryValueEntity entity);
    
    /**
     * 精确查询
     * 根据上级的 TERMS_CODE 查询下级所有的值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:11:20
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueByTermsCode(String code); 
    
    
    /**
     * 精确查询
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:11:20
     */
    DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCode(String termsCode,String valueCode); 

    /**
     * 提供给张斌
     * 根据上级的 TERMS_CODE 批量查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-10 上午9:27:14
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueMoreByTermsCode(String[] codes);
	
    /***********************缓存方法定义************************************/
    /**
     * 通过词条代码，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
	//DataDictionaryEntity queryDataForCacheByTermsCode(String key);

	/**
     * 通过最后更新时间，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
	//List<DataDictionaryEntity> getByLastModifyDate(Date time);

	/**
	 * 通过一组词条代码，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
	List<DataDictionaryEntity> queryDataForCacheByTermsCodes(String[] keys);
    
    /**
     * 返回最后修改时间
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
	Date getLastModifyTime();

	/**
     * 加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
	List<DataDictionaryEntity> queryDataForCache();
	/***********************缓存方法定义************************************/
    
    /**
     * 判断数据字典内容是否有更新
     * 
     * @author 107046-foss-zengxiantao
     * @date 2013-05-06
     */    
    long getLastChangeVersionNo();
	/**
	 * 分页下载
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<DataDictionaryValueEntity> queryDataDictionaryValueForDownloadByPage(
			DataDictionaryValueEntity entity, int start, int limited);
    
}
