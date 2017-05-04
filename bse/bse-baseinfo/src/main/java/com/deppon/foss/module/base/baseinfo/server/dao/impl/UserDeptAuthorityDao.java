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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/UserDeptAuthorityDao.java
 * 
 * FILE NAME        	: UserDeptAuthorityDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl
 * FILE    NAME: UserDeptAuthorityDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Collections;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity;

/**
 * 用户数据权限dao实现
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-17 下午9:12:01
 */
public class UserDeptAuthorityDao extends SqlSessionDaoSupport implements
	IUserDeptAuthorityDao {

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.userDeptAuthority.";

    /**
     * 增加用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:12:01
     * @param deptData
     *            :用户数据权限实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao#addUserDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDeptDataEntity)
     */
    @Override
    public int addUserDepts(UserDeptDataEntity deptData) {
	// UserEntity currentUser = FossUserContext.getCurrentUser();
    	// 何波 2013-2-22 注释不合理的代码
	// deptData.setCreateUser("000000");
	// deptData.setCreateDate(new Date());
	// deptData.setId(UUIDUtils.getUUID());
	return this.getSqlSession().insert(NAMESPACE + "insertUserDeptData",
		deptData);
    }

    /**
     * 根据用户编码查询用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:12:01
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao#searchUserDeptsByUserCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserDeptDataEntity> searchUserDeptsByUserCode(String userCode) {
	List<UserDeptDataEntity> entityResults = this.getSqlSession().selectList(
		NAMESPACE + "getuserAuthorityDataByUserCode", userCode);
	entityResults.removeAll(Collections.singleton(null));
	
	return entityResults;
    }

    /**
     * 根据用户数据权限集合删除用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:12:01
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao#deleteUserDepts(java.util.List)
     */
    @Override
    public int deleteUserDepts(List<String> userDeptIds) {
	return 0;
    }

    /**
     * 根据用户编码删除用户数据权限
     * 
     * @author 078823-foss-panGuangJun
     * @date 2012-12-17 下午9:12:01
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptAuthorityDao#deleteUserDepts(java.util.List)
     */
    @Override
    public int deleteUserDeptsByUserCode(String userCode) {
	return this.getSqlSession().delete(
		NAMESPACE + "deleteUserDeptByUserCode", userCode);
    }

}
