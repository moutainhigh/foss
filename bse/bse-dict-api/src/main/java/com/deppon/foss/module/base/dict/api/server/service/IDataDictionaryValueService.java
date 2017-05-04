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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/service/IDataDictionaryValueService.java
 * 
 * FILE NAME        	: IDataDictionaryValueService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
/**
 *  Service接口
 * @author 087584-foss-lijun
 * @date 2012-10-18 下午4:17:33
 */
public interface IDataDictionaryValueService extends IService{
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDictionaryValueEntity addDataDictionaryValue(
	    DataDictionaryValueEntity entity)
	    throws DataDictionaryValueException;

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDictionaryValueEntity deleteDataDictionaryValue(DataDictionaryValueEntity entity);
   
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDictionaryValueEntity deleteDataDictionaryValueMore(DataDictionaryValueEntity entity);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDictionaryValueEntity updateDataDictionaryValue(
	    DataDictionaryValueEntity entity)
	    throws DataDictionaryValueException;	
    
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
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
     * @date 2012-10-18 下午4:17:33
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueByEntity(DataDictionaryValueEntity entity,int start, int limit);
	
    /**
     * 查询queryDataDictionaryValueByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
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
     * 下面为特殊查询
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
     * 根据上级的 TERMS_CODE 查询下级所有的值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:11:20
     */
    List<DataDictionaryValueEntity> queryDataDictionaryValueByTermsCode(String code); 
    
    
    /**
     * 
     * <p>批量查询数据字典</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 2:19:09 PM
     * @param code
     * @return
     * @see
     */
    Map<String, List<DataDictionaryValueEntity>> queryDataDictionaryValueBatchByTermsCode(List<String> codeList);
    
    /**
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:11:20
     */
    DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCode(String termsCode,String valueCode);
    
    /**
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象（不走缓存，供综合内部基础数据维护使用）：
     * 
     * @author 088933-foss-zhangjiheng
     * @date 2012-10-18 下午4:11:20
     */
    DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCodeNoCache(String termsCode,String valueCode);

    
    /**
     * 下面是特殊方法
     */
    
    
    /**
     * 批量作废数据字典-值
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-4 下午6:55:54
     */
    void deleteDataDictionaryValueMore(List<DataDictionaryValueEntity> entitys);
    
    /**
     * 判断数据字典内容是否有更新
     * 
     * @author 107046-foss-zengxiantao
     * @date 2013-05-06
     */    
    long getLastChangeVersionNo();
    
}
