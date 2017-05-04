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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonEmpService.java
 * 
 * FILE NAME        	: CommonEmpService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl
 * FILE    NAME: CommonEmpService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonEmpService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 公共组件--人员信息查询.
 *
 * @author panGuangJun
 * @date 2012-11-30 上午11:48:03
 */
public class CommonEmpService implements ICommonEmpService {
	
	/** The employee dao. */
	private IEmployeeDao employeeDao;
	//
	/** The org administrative info service. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 根据条件查询人员信息.
	 *
	 * @param vo :查询条件
	 * @param start the start
	 * @param limit the limit
	 * @return 人员集合
	 * @author panGuangJun
	 * @date 2012-11-30 上午11:48:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonEmpService#queryEmpByCondition(com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo)
	 */
	@Override
	public List<EmployeeEntity> queryEmpByCondition(EmployeeVo vo, int start,
			int limit) {
		if (null == vo || null == vo.getEmployeeDetail()) {
			return null;
		}
		// 如果查询条件为空则返回空
		EmployeeEntity entity=vo.getEmployeeDetail();
		
		if(StringUtil.isNotBlank(entity.getParentOrgCode())){
			// 获取接收方组织及所有下级组织
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(entity.getParentOrgCode());
			List<String> subOrgCodeList=new ArrayList<String>();
			for(OrgAdministrativeInfoEntity orgEntity : orgList){
				subOrgCodeList.add(orgEntity.getCode());
			}
			entity.setSubOrgCodeList(subOrgCodeList);
		}
		// 根据名称查询
		List<EmployeeEntity> emps = employeeDao.queryEmployeeExactByEntity4Selector(entity,start, limit);
		if (CollectionUtils.isNotEmpty(emps)) {
			OrgAdministrativeInfoEntity dept = null;
			for (int i = 0; i < emps.size(); i++) {
				dept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(emps.get(i).getUnifieldCode());
				if(dept!=null){
					emps.get(i).setDepartment(dept);
					if(StringUtil.isNotBlank(dept.getName())){
						emps.get(i).setOrgName(dept.getName());
					}
					if(StringUtil.isNotBlank(dept.getCode())){
						emps.get(i).setOrgCode(dept.getCode());
					}					
				}
				if(StringUtil.isNotBlank(emps.get(i).getEntryDate()+"")){
					emps.get(i).setEnterDate(emps.get(i).getEntryDate()+"");
				}
			}
		}
		return emps;
	}

	/**
	 * 查询的总条数.
	 *
	 * @param vo the vo
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-11-30 上午11:53:14
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonEmpService#countEmpByCondition(com.deppon.foss.module.base.baseinfo.api.shared.vo.EmployeeVo)
	 */
	@Override
	public long countEmpByCondition(EmployeeVo vo) {
		// 如果查询条件为空则返回空
		if (null == vo || null == vo.getEmployeeDetail()) {
			return 0;
		}
		EmployeeEntity entity=vo.getEmployeeDetail();
		
		if(StringUtil.isNotBlank(entity.getParentOrgCode())){
			// 获取接收方组织及所有下级组织
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(entity.getParentOrgCode());
			List<String> subOrgCodeList=new ArrayList<String>();
			for(OrgAdministrativeInfoEntity orgEntity : orgList){
				subOrgCodeList.add(orgEntity.getCode());
			}
			entity.setSubOrgCodeList(subOrgCodeList);
		}
		return employeeDao.countEmployeeExactByEntity4Selector(entity);
	}

	/**
	 * Gets the employee dao.
	 *
	 * @return the employee dao
	 */
	public IEmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	/**
	 * Sets the employee dao.
	 *
	 * @param employeeDao the new employee dao
	 */
	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * setter.
	 *
	 * @param orgAdministrativeInfoService the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
}
