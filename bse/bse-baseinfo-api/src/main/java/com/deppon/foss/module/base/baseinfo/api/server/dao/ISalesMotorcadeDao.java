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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISalesMotorcadeDao.java
 * 
 * FILE NAME        	: ISalesMotorcadeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
/**
 * 营业部车队关系 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-14 上午11:55:28
 */
public interface ISalesMotorcadeDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    SalesMotorcadeEntity addSalesMotorcade(SalesMotorcadeEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    SalesMotorcadeEntity deleteSalesMotorcade(SalesMotorcadeEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    SalesMotorcadeEntity deleteSalesMotorcadeMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    SalesMotorcadeEntity updateSalesMotorcade(SalesMotorcadeEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    SalesMotorcadeEntity querySalesMotorcadeByVirtualCode(String code);	
	
    /**
     * 
     *<p>通过营业部编码和车队编码 精确查询</p>
     *@author 130566-zengJunfan
     *@date   2013-8-17下午3:10:07
     * @param code
     * @return
     */
    SalesMotorcadeEntity querySalesMotorcadeBySalesCodeAndMotorCode(String salesdeptCode,String motorcadeCode);	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     * @see com.deppon.foss.module.base.dict.api.server.dao.ISalesMotorcadeDao#querySalesMotorcadeByCode(java.lang.String)
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeExactByEntity(
	    SalesMotorcadeEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    long querySalesMotorcadeExactByEntityCount(SalesMotorcadeEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeByEntity(SalesMotorcadeEntity entity,
	    int start, int limit);
	
    /**
     * 查询querySalesMotorcadeByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    long querySalesMotorcadeByEntityCount(SalesMotorcadeEntity entity);
		
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:55:28
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeForDownload(SalesMotorcadeEntity entity);
		
	
    
    
    
    /**
     * 下面是特殊方法
     */

    /**
     * 通过salesdeptCode营业部编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    SalesMotorcadeEntity deleteSalesMotorcadeBySalesdeptCode(SalesMotorcadeEntity entity);
    /**
     * 
     *<p>通过删除motorcadeCode 车队编码来进行删除</p>
     *@author 130566-zengJunfan
     *@date   2013-7-26上午8:32:46
     * @param entity
     * @return
     */
    SalesMotorcadeEntity deleteSalesMotorcadeEntityByMotorcadeCode(SalesMotorcadeEntity entity);
    
}
