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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IFinancialOrganizationsDao.java
 * 
 * FILE NAME        	: IFinancialOrganizationsDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
/**
 *  DAO接口
 * @author 087584-foss-lijun
 * @date 2012-10-22 下午3:19:47
 */
public interface IFinancialOrganizationsDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    FinancialOrganizationsEntity addFinancialOrganizations(FinancialOrganizationsEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    FinancialOrganizationsEntity deleteFinancialOrganizations(FinancialOrganizationsEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    FinancialOrganizationsEntity deleteFinancialOrganizationsMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    FinancialOrganizationsEntity updateFinancialOrganizations(FinancialOrganizationsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    FinancialOrganizationsEntity queryFinancialOrganizationsByCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     * @see com.deppon.foss.module.base.dict.api.server.dao.IFinancialOrganizationsDao#queryFinancialOrganizationsByCode(java.lang.String)
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsExactByEntity(
	    FinancialOrganizationsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    long queryFinancialOrganizationsExactByEntityCount(FinancialOrganizationsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsByEntity(FinancialOrganizationsEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryFinancialOrganizationsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:24:35
     */
    long queryFinancialOrganizationsByEntityCount(FinancialOrganizationsEntity entity);
    

	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-29 上午10:3:10
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsForDownload(FinancialOrganizationsEntity entity);
    
    /**
     * 以下为特殊查询
     */
    
    /**
     * 模糊查询 根据name查询财务组织及财务组织的所有上级，上下级通过CODE,PARENT_ORG_CODE来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-22 下午5:09:07
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsUpByName(String name);

    /**
     * 获取最后跟新时间
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-29 上午10:01:39
     */
    Date getLastModifyTime();
	
}
