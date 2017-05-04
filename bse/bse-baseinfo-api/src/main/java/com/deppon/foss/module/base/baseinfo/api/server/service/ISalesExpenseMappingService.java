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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IEmployeeService.java
 * 
 * FILE NAME        	: IEmployeeService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesExpenseMappingQueryDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 营业部与外请车费用承担部门映射信息service接口
 * @author 307196
 *
 */
public interface ISalesExpenseMappingService extends IService {
	/**
	 * 新增营业部与外请车费用承担部门映射信息service接口
	 * @author 307196
	 *
	 */
	void addSalesExpenseMapping(List<SalesExpenseMappingEntity> entitys,CurrentInfo currentInfo);
	
    /**
     * 修改营业部与外请车费用承担部门映射信息service接口
     * @author 307196
     *
     */
	void updateSalesExpenseMapping(SalesExpenseMappingQueryDto entity,CurrentInfo currentInfo);
	
    /**
     * 作废营业部与外请车费用承担部门映射信息service接口
     * @author 307196
     *
     */
	void deleteSalesExpenseMapping(SalesExpenseMappingEntity entity,CurrentInfo currentInfo);
	
    
    /**
     * 总条数营业部与外请车费用承担部门映射信息service接口
     * @author 307196
     *
     */
    long queryTotalByCondition(SalesExpenseMappingQueryDto entity);
	
    /**
     * 分页查询营业部与外请车费用承担部门映射信息service接口
     * @author 307196
     *
     */
    List<SalesExpenseMappingEntity> querySalesExpenseMappingByCondition(SalesExpenseMappingQueryDto entity,int start, int limit);
    /**
     * 结算的方法接口 
     * 根据营业部编码查询映射信息service接口
     * @author 307196
     *
     */
    List<SalesExpenseMappingEntity> queryByBusinessDepart(String businessDepartmentCode);
}
