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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISalesMotorcadeService.java
 * 
 * FILE NAME        	: ISalesMotorcadeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
/**
 * 营业部车队关系 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-14 上午11:56:23
 */
public interface ISalesMotorcadeService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    SalesMotorcadeEntity addSalesMotorcade(SalesMotorcadeEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    SalesMotorcadeEntity deleteSalesMotorcade(SalesMotorcadeEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    SalesMotorcadeEntity deleteSalesMotorcadeMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    SalesMotorcadeEntity updateSalesMotorcade(SalesMotorcadeEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    SalesMotorcadeEntity querySalesMotorcadeByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeDao#querySalesMotorcadeByVirtualCode(java.lang.String)
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeExactByEntity(
	    SalesMotorcadeEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    long querySalesMotorcadeExactByEntityCount(SalesMotorcadeEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    List<SalesMotorcadeEntity> querySalesMotorcadeByEntity(
	    SalesMotorcadeEntity entity,int start, int limit);
	
    /**
     * 查询querySalesMotorcadeByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午11:56:23
     */
    long querySalesMotorcadeByEntityCount(SalesMotorcadeEntity entity);
		
	
    
    
    
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
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    List<SalesMotorcadeEntity> addSalesMotorcadeMore(List<SalesMotorcadeEntity> entitys);

    /**
     * 
     * <p>通过营业部查询关联的车队部门编码</p> 
     * @author foss-zhujunyong
     * @date Apr 10, 2013 3:31:31 PM
     * @param salesCode
     * @return
     * @see
     */
    List<String> querySalesMotorcadeListBySales(String salesCode);
    /**
     * 
     *<p>根据motorcadeCode车队编码来废除</p>
     *@author 130566-zengJunfan
     *@date   2013-7-26上午9:03:13
     * @param entity
     * @return
     */
    SalesMotorcadeEntity deleteSalesMotorcadeEntityByMotorcadeCode(SalesMotorcadeEntity entity);
}
