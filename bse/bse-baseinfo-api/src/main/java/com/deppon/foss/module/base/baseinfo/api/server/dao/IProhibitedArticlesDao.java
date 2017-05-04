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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IProhibitedArticlesDao.java
 * 
 * FILE NAME        	: IProhibitedArticlesDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
/**
 * 禁运物品 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午9:19:0
 */
public interface IProhibitedArticlesDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    ProhibitedArticlesEntity addProhibitedArticles(ProhibitedArticlesEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    ProhibitedArticlesEntity deleteProhibitedArticles(ProhibitedArticlesEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    ProhibitedArticlesEntity deleteProhibitedArticlesMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    ProhibitedArticlesEntity updateProhibitedArticles(ProhibitedArticlesEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    ProhibitedArticlesEntity queryProhibitedArticlesByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     * @see com.deppon.foss.module.base.dict.api.server.dao.IProhibitedArticlesDao#queryProhibitedArticlesByCode(java.lang.String)
     */
    List<ProhibitedArticlesEntity> queryProhibitedArticlesBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    List<ProhibitedArticlesEntity> queryProhibitedArticlesExactByEntity(
	    ProhibitedArticlesEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    long queryProhibitedArticlesExactByEntityCount(ProhibitedArticlesEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    List<ProhibitedArticlesEntity> queryProhibitedArticlesByEntity(ProhibitedArticlesEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryProhibitedArticlesByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:19:0
     */
    long queryProhibitedArticlesByEntityCount(ProhibitedArticlesEntity entity);
	
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午7:27:28
     */
    List<ProhibitedArticlesEntity> queryProhibitedArticlesForDownload(ProhibitedArticlesEntity entity);
		
	
}
