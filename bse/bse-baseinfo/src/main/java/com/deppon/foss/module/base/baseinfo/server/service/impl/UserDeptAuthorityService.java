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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/UserDeptAuthorityService.java
 * 
 * FILE NAME        	: UserDeptAuthorityService.java
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
 * FILE    NAME: UserDeptAuthorityService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用户数据权限service实现
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-17 下午9:14:28
 */
public class UserDeptAuthorityService implements IUserDeptAuthorityService {

	// dao
	private IUserDeptAuthorityDao userDeptAuthorityDao;
	
	private IUserDeptDataService userDeptDataService;
	
	private ISyncUserDeptDataService syncUserDeptDataService;
	
	private IOrgAdministrativeInfoService  orgAdministrativeInfoService; 

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 数据授权同步数据接口
	 * @author 310854
	 * @date 2016-4-7
	 */
	public void setSyncUserDeptDataService(
			ISyncUserDeptDataService syncUserDeptDataService) {
		this.syncUserDeptDataService = syncUserDeptDataService;
	}

	/**
	 * 数据授权同步数据
	 * @author 310854
	 * @date 2016-4-7
	 */
	private void syncUserDeptDataToOther(List<UserDeptDataEntity> deptDatas, EmployeeEntity userEntity, int operateType){
		syncUserDeptDataService.syncUserDeptData(deptDatas, userEntity, operateType);
	}
	
	/**
	 * 增加用户数据权限u
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-17 下午9:14:28
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService#saveUserDepts(java.util.List)
	 */
	@Transactional
	@Override
	public int saveUserDepts(List<UserDeptDataEntity> deptDatas, String empCode) {
		int result = 0;
		EmployeeEntity userEntity = new EmployeeEntity();
		userEntity.setEmpCode(empCode);
		//  默认
		int operateType = NumberConstants.NUMBER_1;
		if (null != deptDatas) {
			// 何波 2013-2-22 将dao代码提取到service中
			UserEntity currentUser = FossUserContext.getCurrentUser();
			result = userDeptAuthorityDao.deleteUserDeptsByUserCode(empCode);
			
			if(0 < result){
				//  当数据为空的时候，就是新增，含有删除数据的时候就是修改
				operateType = NumberConstants.NUMBER_2;
			}
			List<UserDeptDataEntity> entitys = new ArrayList<UserDeptDataEntity>();
			
			if(CollectionUtils.isEmpty(deptDatas)){
				//   当数据授权对象为空的时候，删除全部权限
				operateType = NumberConstants.NUMBER_3;
			}else{
				for(UserDeptDataEntity deptData : deptDatas){
					//	UserDeptDataEntity deptData = deptDatas.get(i);
					// 何波 2013-2-22设置基本属性
					deptData.setCreateUser(currentUser.getEmpCode());
					deptData.setCreateDate(new Date());
					deptData.setModifyUser(deptData.getCreateUser());
					deptData.setModifyDate(deptData.getCreateDate());
					deptData.setId(UUIDUtils.getUUID());
					// 将前台传入的boolean值转换成对应的true - Y false - N
					if (deptData.isSubOrg()) {
						deptData.setSubOrgFlag(FossConstants.ACTIVE);
					} else {
						deptData.setSubOrgFlag(FossConstants.INACTIVE);
					}
					result = userDeptAuthorityDao.addUserDepts(deptData);
					entitys.add(deptData);
				}
			}
			
			if(0 < result){
				//   同步数据授权信息去其他系统
				syncUserDeptDataToOther(entitys, userEntity, operateType);	
			}
		}
	    userDeptDataService.refreshUserDeptData(empCode);
		return 0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
	}

    /**
     * 根据用户编码查询用户用户拥有的数据权限--组织信息
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:14:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService#searchUserDeptsByUserCode(java.lang.String)
     */
    @Override
    public List<OrgAdministrativeInfoEntity> searchUserDeptsByUserCode(String userCode) {
	List<OrgAdministrativeInfoEntity> result = new ArrayList<OrgAdministrativeInfoEntity>();
	if (StringUtils.isBlank(userCode)) {
	    return result;
	}
	List<UserDeptDataEntity> userDataList = userDeptAuthorityDao.searchUserDeptsByUserCode(userCode);
	if (CollectionUtils.isEmpty(userDataList)) {
	    return result;
	} 
	for (UserDeptDataEntity userDept : userDataList) {
	    if (userDept == null) {
		continue;
	    }
	    OrgAdministrativeInfoEntity simpleOrg = userDept.getDept();
	    if (simpleOrg != null && StringUtils.isNotBlank(simpleOrg.getCode())) {
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(simpleOrg.getCode());
		if (org == null) {
		    continue;
		}
		// 暂时将组织描述字段作为是否选中子部门标记
		if (StringUtils.equals(userDept.getSubOrgFlag(), FossConstants.ACTIVE)) {
		    org.setDeptDesc(FossConstants.ACTIVE);
		}
		result.add(org);
	    }

	}
	return result;
    }

    /**
     * 根据用户数据权限信息，删除用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:14:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService#deleteUserDepts(java.util.List)
     */
    @Override
    public int deleteUserDepts(List<UserDeptDataEntity> userDepts) {
	int result = FossConstants.FAILURE;
	if (CollectionUtils.isEmpty(userDepts)) {
	    return result;
	}
	
	List<String> ids = new ArrayList<String>();
	for (UserDeptDataEntity entity : userDepts) {
	    if (entity != null && StringUtils.isNotBlank(entity.getId())) {
		ids.add(entity.getId());
	    }
	}
	if (CollectionUtils.isEmpty(ids)) {
	    return result;
	}
	result = userDeptAuthorityDao.deleteUserDepts(ids);
	
	return 0 < result ? FossConstants.SUCCESS : FossConstants.FAILURE;
    }

    /**
     * 
     * @date Mar 11, 2013 3:13:32 PM
     * @param userDeptAuthorityDao
     * @see
     */
    public void setUserDeptAuthorityDao(
	    IUserDeptAuthorityDao userDeptAuthorityDao) {
	this.userDeptAuthorityDao = userDeptAuthorityDao;
    }

	/**
	 * 
	 * @date Mar 11, 2013 3:13:37 PM
	 * @param userDeptDataService
	 * @see
	 */
	public void setUserDeptDataService(IUserDeptDataService userDeptDataService) {
		this.userDeptDataService = userDeptDataService;
	}

	/**
	 * <p>
	 * 根据用户编码查询用户所拥有的数据权限,包含当前部门
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Jan 22, 2013 5:05:23 PM
	 * @param userCode
	 * @param currentDeptCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptAuthorityService#searchUserDeptsByUserCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<String> searchUserDeptsByUserCode(String userCode,
			String currentDeptCode) {
		List<String> result = new ArrayList<String>();
		if (StringUtils.isBlank(userCode)) {
			return result;
		}

		List<OrgAdministrativeInfoEntity> orgList = searchUserDeptsByUserCode(userCode);
		// 如果数据权限表中未配置，则只加上当前部门返回
		if (CollectionUtils.isEmpty(orgList)) {
			if (StringUtils.isNotBlank(currentDeptCode)) {
				result.add(currentDeptCode);
			}
			return result;
		}
		// 把数据权限表中的数据加上
		for (OrgAdministrativeInfoEntity org : orgList) {
			if (org == null || StringUtils.isBlank(org.getCode())) {
				continue;
			}
			result.add(org.getCode());
		}
		// 如果数据权限中未配当前部门，则加上当前部门返回
		if (StringUtils.isNotBlank(currentDeptCode)
				&& BooleanUtils.isFalse(result.contains(currentDeptCode))) {
			result.add(currentDeptCode);
		}
		return result;
	}

    /**
     * 
     * <p>
     * 根据用户编码查询用户所拥有的数据权限表信息
     * </p>
     * 
     * @author 何波
     * @date 2013-2-22 上午11:27:26
     * @return
     * @see
     */
    @Override
    public List<UserDeptDataEntity> queryUserDeptsByUserCode(String userCode) {

	if (StringUtils.isBlank(userCode)) {
	    return null;
	}
	List<UserDeptDataEntity> userDataEntities = userDeptAuthorityDao
		.searchUserDeptsByUserCode(userCode);

	return userDataEntities;
    }

}
