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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IFinancialOrganizationsService.java
 * 
 * FILE NAME        	: IFinancialOrganizationsService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
/**
 *  Service接口
 * @author 087584-foss-lijun
 * @date 2012-10-22 下午3:21:26
 */
public interface IFinancialOrganizationsService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    FinancialOrganizationsEntity addFinancialOrganizations(FinancialOrganizationsEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    FinancialOrganizationsEntity deleteFinancialOrganizations(FinancialOrganizationsEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
//    FinancialOrganizationsEntity deleteFinancialOrganizationsMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    FinancialOrganizationsEntity updateFinancialOrganizations(FinancialOrganizationsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    FinancialOrganizationsEntity queryFinancialOrganizationsByCode(String code);
    /**
     * 
     * 根据编码查询,不走缓存，共综合内部维护基础数据使用
     * @author 088933-foss-zhangjiheng
     * @date 2013-5-29 下午5:10:46
     */
    FinancialOrganizationsEntity queryFinancialOrganizationsByCodeNoCache(String code);
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao#queryFinancialOrganizationsByCode(java.lang.String)
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsExactByEntity(
	    FinancialOrganizationsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    long queryFinancialOrganizationsExactByEntityCount(FinancialOrganizationsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    List<FinancialOrganizationsEntity> queryFinancialOrganizationsByEntity(
	    FinancialOrganizationsEntity entity, int start, int limit);

    /**
     * 查询queryFinancialOrganizationsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:32:3
     */
    long queryFinancialOrganizationsByEntityCount(FinancialOrganizationsEntity entity);
	
    
    
    /**
     * 以下为特殊查询
     */
    
    /**
     * 模糊查询 根据name查询财务组织及财务组织的所有上级，上下级通过CODE,PARENT_ORG_CODE来关联
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-22 下午5:09:07
     */
    public List<FinancialOrganizationsEntity> queryFinancialOrganizationsUpByName(String name);


	
}
