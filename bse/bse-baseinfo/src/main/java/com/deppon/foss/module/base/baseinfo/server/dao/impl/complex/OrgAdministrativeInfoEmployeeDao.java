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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/complex/OrgAdministrativeInfoEmployeeDao.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoEmployeeDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.complex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAdministrativeInfoEmployeeDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 部门人员联合查询dao
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午10:54:51
 */
public class OrgAdministrativeInfoEmployeeDao extends SqlSessionDaoSupport implements
	IOrgAdministrativeInfoEmployeeDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".orgAdministrativeInfoEmployee.";

    
    /**
     * 根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号，
     *    理货员编号，理货员编码 
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:44:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoEmployeeDao#getPorter(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity, com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    public List<OrgAdministrativeInfoEmployeeDto> getPorter(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
	    EmployeeEntity employeeEntity, int start, int limit){
	OrgAdministrativeInfoEntity orgAdministrativeInfo;
	if (null == orgAdministrativeInfoEntity) {
	    orgAdministrativeInfo = new OrgAdministrativeInfoEntity();
	} else {
	    orgAdministrativeInfo = orgAdministrativeInfoEntity;
	}
	
	EmployeeEntity employee;
	if (null == employeeEntity) {
	    employee = new EmployeeEntity();
	} else {
	    employee = employeeEntity;
	}
	

	orgAdministrativeInfo.setActive(FossConstants.ACTIVE);
	employee.setActive(FossConstants.ACTIVE);
	
	
	Map<String, Object> map = new HashMap<String,Object>();
	map.put("orgAdministrativeInfo", orgAdministrativeInfo);
	map.put("employee", employee);
	map.put("yes", FossConstants.YES);
	map.put("conditionActive", FossConstants.ACTIVE);
	
	RowBounds rowBounds = new RowBounds(start, limit);
	return  (List<OrgAdministrativeInfoEmployeeDto>)getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoEmployeeExactByEntity",
		map,
		rowBounds);
    }
    
    
    /**
     * 查询总条数
     * 
     * 根据 理货部门所服务的外场，理货部门类型（装车/卸车/派送装车），部门的编号，
     *    理货员编号，理货员编码 
     * 动态条件，精确查询 理货员信息
     * 支持分页
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-3 下午4:44:21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.complex.IOrgAdministrativeInfoEmployeeDao#getPorter(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity, com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     */
    public long getPorterCount(
	    OrgAdministrativeInfoEntity orgAdministrativeInfoEntity,
	    EmployeeEntity employeeEntity){
	OrgAdministrativeInfoEntity orgAdministrativeInfo;
	if (null == orgAdministrativeInfoEntity) {
	    orgAdministrativeInfo = new OrgAdministrativeInfoEntity();
	} else {
	    orgAdministrativeInfo = orgAdministrativeInfoEntity;
	}
	
	EmployeeEntity employee;
	if (null == employeeEntity) {
	    employee = new EmployeeEntity();
	} else {
	    employee = employeeEntity;
	}
	

	orgAdministrativeInfo.setActive(FossConstants.ACTIVE);
	employee.setActive(FossConstants.ACTIVE);
	
	
	Map<String, Object> map = new HashMap<String,Object>();
	map.put("orgAdministrativeInfo", orgAdministrativeInfo);
	map.put("employee", employee);
	map.put("yes", FossConstants.YES);
	map.put("conditionActive", FossConstants.ACTIVE);
	
	return  (Long)getSqlSession().selectOne(
		NAMESPACE + "queryOrgAdministrativeInfoEmployeeExactByEntityCount",
		map);
    }
}
