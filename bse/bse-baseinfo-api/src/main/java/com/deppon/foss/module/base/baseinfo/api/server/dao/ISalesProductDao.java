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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISalesProductDao.java
 * 
 * FILE NAME        	: ISalesProductDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
/**
 * 营业部适用产品 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-14 上午12:45:51
 */
public interface ISalesProductDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    SalesProductEntity addSalesProduct(SalesProductEntity entity);
    
    /**
     * 批量插入
     * 
     * @author 313353-foss-qiupeng
     * @date 2016-11-2 上午12:45:51
     */
    List<SalesProductEntity> addSalesProductBatch(List<SalesProductEntity> entitys);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    SalesProductEntity deleteSalesProduct(SalesProductEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    SalesProductEntity deleteSalesProductMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    SalesProductEntity updateSalesProduct(SalesProductEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    SalesProductEntity querySalesProductByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     * @see com.deppon.foss.module.base.dict.api.server.dao.ISalesProductDao#querySalesProductByCode(java.lang.String)
     */
    List<SalesProductEntity> querySalesProductBatchByVirtualCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    List<SalesProductEntity> querySalesProductExactByEntity(
	    SalesProductEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    long querySalesProductExactByEntityCount(SalesProductEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    List<SalesProductEntity> querySalesProductByEntity(SalesProductEntity entity,
	    int start, int limit);
	
    /**
     * 查询querySalesProductByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    long querySalesProductByEntityCount(SalesProductEntity entity);
		
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:45:51
     */
    List<SalesProductEntity> querySalesProductForDownload(SalesProductEntity entity);
	
    
    
    
    /**
     * 下面是特殊方法
     */

    /**
     * 通过SalesDeptCode营业部编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    SalesProductEntity deleteSalesProductBySalesDeptCode(SalesProductEntity entity);

	/**
	 * 
	 * 分页查询下载 数据量太大了 防止out of memory
	 * @param entity
	 * @return
	 */
	List<SalesProductEntity> querySalesProductForDownloadByPage(SalesProductEntity entity, int start, int limit);
    
}
