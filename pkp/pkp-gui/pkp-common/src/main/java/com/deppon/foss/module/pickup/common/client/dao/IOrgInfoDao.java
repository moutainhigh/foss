/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IOrgInfoDao.java
 * 
 * FILE NAME        	: IOrgInfoDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 组织信息数据访问接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:36:44,
 * </p>
 * 
 * @author dp-yangtong
 * @date 2012-10-12 上午9:36:44
 * @since
 * @version
 */
public interface IOrgInfoDao {

    /**
     * 
     * 功能：按id查询
     * 
     * @param:
     * @return Department
     * @since:1.6
     */
    OrgAdministrativeInfoEntity queryById(String id);

    /**
     * <p>
     * 功能：按code查询
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-11-7 下午7:24:32
     * @param id
     * @return
     * @see
     */
    OrgAdministrativeInfoEntity queryByCode(String code);

    /**
     * 
     * 功能：插条记录
     * 
     * @param: orgInfo
     * @return void
     * @since:1.6
     */
    boolean addOrgInfo(OrgAdministrativeInfoEntity orgInfo);

    /**
     * 
     * 功能：更新条记录
     * 
     * @param:
     * @return void
     * @since:1.6
     */
    void updateOrgInfo(OrgAdministrativeInfoEntity orgInfo);

    /**
     * 功能：查询记录
     * 
     * @param:
     * @return List<Department>
     * @since:1.6
     */
    List<OrgAdministrativeInfoEntity> queryAll();

	/**
	 * 根据标杆编码查询组织信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 上午9:49:25
	 */
	OrgAdministrativeInfoEntity queryOrgByUnifiedCode(String unifiedCode);
	
	/**
	 * 
	 * 查询空运配载部门
	 * @author 025000-FOSS-helong
	 * @date 2013-1-21 下午07:29:51
	 * @return
	 */
	List<OrgAdministrativeInfoEntity> queryOrgAirDepartment();

	/**
	 * @param orgInfoEntity
	 */
	void delete(OrgAdministrativeInfoEntity orgInfoEntity);
	
	/**
	 * 
	 * 根据部门名称查询部门信息
	 * @author WangQianJin
	 * @date 2013-05-16
	 * @return
	 */
	List<OrgAdministrativeInfoEntity> queryOrgInfoByName(String name);

	/**
	 * 该方法用于根据服务器时间查询组织信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-20 下午6:26:49
	 */
	OrgAdministrativeInfoEntity queryByCodeAndServerTime(String code, Date time);
}