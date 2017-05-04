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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/UserDeptDataService.java
 * 
 * FILE NAME        	: UserDeptDataService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptDataDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 用户数据权限service实现
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-17 下午9:14:28
 */
public class UserDeptDataService implements IUserDeptDataService {

    // dao
    private IUserDeptDataDao userDeptDataDao;

    private IOutfieldService outfieldService;
    
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    
    /**
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    /**
     * @param outfieldService the outfieldService to set
     */
    public void setOutfieldService(IOutfieldService outfieldService) {
        this.outfieldService = outfieldService;
    }

    public IUserDeptDataDao getUserDeptDataDao() {
	return userDeptDataDao;
    }

    public void setUserDeptDataDao(IUserDeptDataDao userDeptDataDao) {
	this.userDeptDataDao = userDeptDataDao;
    }

	/**
	 * 
	 * <p>通过用户的编码得到用户的部门数据权限集合</p> 
	 * @author ztjie
	 * @date 2013-2-21 下午4:39:38
	 * @param userCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService#queryUserDeptDataByCode(java.lang.String)
	 */
	@Override
	public List<String> queryUserDeptDataByCode(String userCode) {
	    List<String> result = userDeptDataDao.getUserDeptDataByCode(userCode);
	    return result == null ? new ArrayList<String> () : result;
	}

	/**
	 * 
	 * <p>刷新用户部门数据权限</p> 
	 * @author ztjie
	 * @date 2013-2-21 下午4:39:40 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService#refreshUserDeptData()
	 */
	@Override
	public void refreshUserDeptData(String userCode) {
		userDeptDataDao.refreshUserDeptData(userCode);
	}

	/**
	 * 
	 * <p>同步组织的时候，进行用户部门数据的增加</p> 
	 * @author ztjie
	 * @date 2013-7-25 下午2:35:03
	 * @param orgCode 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService#addUserDeptBySyncOrg(java.lang.String)
	 */
	@Override
	public void addUserDeptBySyncOrg(String orgCode) {
		userDeptDataDao.addUserDeptBySyncOrg(orgCode);
	}
	
    /**
     * 
     * 通过用户的编码得到用户的外场部门数据权限集合
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 3:15:56 PM
     * @param userCode
     * @return
     * @see
     */
    public List<String> queryUserDeptDataInTransferCenter(String userCode, String deptCode) {
	// 查询该用户的上级外场（包括本部门）
	List<String> bizTypes = new ArrayList<String> ();
	bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
	OrgAdministrativeInfoEntity outfield = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(deptCode, bizTypes);
	
	// 查询数据权限
	List<String> orgCodeList = queryUserDeptDataByCode(userCode);
	List<String> result = new ArrayList<String>();
	if (CollectionUtils.isEmpty(orgCodeList) && outfield == null) {
	    return result;
	}
	// 把该用户的上级外场也加入到数据权限中
	if (outfield != null && StringUtils.isNotBlank(outfield.getCode()) && !orgCodeList.contains(outfield.getCode())) {
	    orgCodeList.add(outfield.getCode());
	}
	
	// 取出所有的外场组织编码
	List<String> transferCodeList = outfieldService.queryActiveOrgCodeList();
	// 取两个集合的交集
	for (String transferCode : transferCodeList) {
	    if (orgCodeList.contains(transferCode)) {
		result.add(transferCode);
	    }
	}
	return result;
    }

	
}
