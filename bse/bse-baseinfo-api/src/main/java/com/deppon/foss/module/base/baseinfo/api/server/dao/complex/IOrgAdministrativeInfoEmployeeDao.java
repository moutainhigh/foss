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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/complex/IOrgAdministrativeInfoEmployeeDao.java
 * 
 * FILE NAME        	: IOrgAdministrativeInfoEmployeeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.complex;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;


/**
 * 部门人员联合查询 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午11:24:48
 */
public interface IOrgAdministrativeInfoEmployeeDao {
    /**
     * 根据 理货部门所服务的外场ARRANGE_OUTFIELD，理货部门类型（装车/卸车/派送装车）ARRANGE_BIZ_TYPE，部门的编号UNIFIED_CODE，
     *    理货员职位TITLE，理货员编号EMP_NAME
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * queryOrgAdministrativeInfoEmployeeDto
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:44:21
     */
    List<OrgAdministrativeInfoEmployeeDto> getPorter(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
	    EmployeeEntity employeeEntity, int start, int limit);
    
    /**
     * 查询总条数
     * 
     * 根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号，
     *    理货员编号，理货员编码 
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * queryOrgAdministrativeInfoEmployeeDto
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:44:21
     */
    long getPorterCount(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
	    EmployeeEntity employeeEntity);
}
