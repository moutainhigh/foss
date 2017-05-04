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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/OrgAdministrativeInfoEmployeeService.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoEmployeeService.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;


public class OrgAdministrativeInfoEmployeeService implements IOrgAdministrativeInfoEmployeeService{

    /**
     * 下面是dao对象的声明及get,set方法：
    */
    private IOrgAdministrativeInfoEmployeeDao orgAdministrativeInfoEmployeeDao;

    private IDataDictionaryValueService dataDictionaryValueService;
    
    
    /**
     * @param dataDictionaryValueService the dataDictionaryValueService to set
     */
    public void setDataDictionaryValueService(
    	IDataDictionaryValueService dataDictionaryValueService) {
        this.dataDictionaryValueService = dataDictionaryValueService;
    }

    /**
     * 
     * @date Mar 12, 2013 2:18:42 PM
     * @param orgAdministrativeInfoEmployeeDao
     * @see
     */
    public void setOrgAdministrativeInfoEmployeeDao(IOrgAdministrativeInfoEmployeeDao orgAdministrativeInfoEmployeeDao) {
	this.orgAdministrativeInfoEmployeeDao = orgAdministrativeInfoEmployeeDao;
    }

    /** 
     * 根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号，
     *    理货员编号，理货员编码 
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:48:54
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService#queryOrgAdministrativeInfoEmployeeDto(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity, com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     */
    @Override
    public List<OrgAdministrativeInfoEmployeeDto> getPorter(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity, EmployeeEntity employeeEntity, int start, int limit) {
	List<OrgAdministrativeInfoEmployeeDto> result = orgAdministrativeInfoEmployeeDao.getPorter(orgAdministrativeInfoEntity, employeeEntity, start, limit) ;
	// 如果结果是空则返直接返回
	if (CollectionUtils.isEmpty(result)) {
	    return new ArrayList<OrgAdministrativeInfoEmployeeDto>();
	}
	// 职位代码列表，用set是避免重复查询
	Set<String> titleCode = new HashSet<String> ();
	for (OrgAdministrativeInfoEmployeeDto dto : result) {
	    if (dto != null && StringUtils.isNotBlank(dto.getEmployeeTitle())) {
		titleCode.add(dto.getEmployeeTitle());
	    }
	}
	// 职位Map
	Map<String, String> titleMap = new HashMap<String, String> ();
	for (String code : titleCode) {
	    DataDictionaryValueEntity data = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.UUMS_POSITION_TERMSCODE, code);
	    if (data != null) {
		titleMap.put(code, data.getValueName());
	    }
	}
	// 把职位名称放到查询结果中
	for (OrgAdministrativeInfoEmployeeDto dto : result) {
	    if (dto != null && StringUtils.isNotBlank(dto.getEmployeeTitle())) {
		dto.setEmployeeTitleName(titleMap.get(dto.getEmployeeTitle()));
	    }
	}
	return result;
    }

    /** 
     * 查询总数
     * 根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号，
     *    理货员编号，理货员编码 
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:48:54
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoEmployeeService#getPorterCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity, com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     */
    @Override
    public long getPorterCount(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
	    EmployeeEntity employeeEntity) {
	return orgAdministrativeInfoEmployeeDao.getPorterCount(orgAdministrativeInfoEntity, employeeEntity) ;
    }
    

    
    
}
