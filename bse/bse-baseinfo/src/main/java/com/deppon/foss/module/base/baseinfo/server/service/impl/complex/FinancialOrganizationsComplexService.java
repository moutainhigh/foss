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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/FinancialOrganizationsComplexService.java
 * 
 * FILE NAME        	: FinancialOrganizationsComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl.complex;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IFinancialOrganizationsComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;


/**
 * 财务组织复杂查询 service层
 * @author 087584-foss-lijun
 * @date 2012-12-18 下午5:07:42
 */
public class FinancialOrganizationsComplexService implements
	IFinancialOrganizationsComplexService {

    private IEmployeeService employeeService;
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    private IFinancialOrganizationsService financialOrganizationsService;
    
    /**
     * 
     * @date Mar 12, 2013 2:11:37 PM
     * @param employeeService
     * @see
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:11:42 PM
     * @param orgAdministrativeInfoService
     * @see
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    
    /**
     * 
     * @date Mar 12, 2013 2:11:47 PM
     * @param financialOrganizationsService
     * @see
     */
    public void setFinancialOrganizationsService(
    	IFinancialOrganizationsService financialOrganizationsService) {
        this.financialOrganizationsService = financialOrganizationsService;
    }
    

    /** 
     * 根据人员编码，获得组织信息，再通过组织信息中的子公司编码，获得子公司对象
     * 
     * 提供给彭臻
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-18 下午5:09:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IFinancialOrganizationsComplexService#queryFinancialOrganizationsByEmpCode(java.lang.String)
     */
    @Override
    public FinancialOrganizationsEntity queryFinancialOrganizationsByEmpCode(
	    String empCode) {
	EmployeeEntity employee =  employeeService.queryEmployeeByEmpCode(empCode);
	if(employee == null || StringUtils.isBlank(employee.getUnifieldCode())){
	    return null;
	}
	OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeClean(employee.getUnifieldCode());
	if(orgAdministrativeInfo == null || StringUtils.isBlank(orgAdministrativeInfo.getSubsidiaryCode())){
	    return null;
	}
	
	return financialOrganizationsService
		.queryFinancialOrganizationsByCode(orgAdministrativeInfo
			.getSubsidiaryCode());
    }

    
}
