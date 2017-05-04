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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/UserDeptDataDao.java
 * 
 * FILE NAME        	: UserDeptDataDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptDataDao;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDeptDataDto;

/**
 * 
 * 用户部门数据操作类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:ztjie,date:2013-2-21 下午3:29:09</p>
 * @author ztjie
 * @date 2013-2-21 下午3:29:09
 * @since
 * @version
 */
public class UserDeptDataDao extends SqlSessionDaoSupport implements
	IUserDeptDataDao {

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.userDeptData.";

    /**
     * 
     * <p>通过用户的编码得到用户的部门数据权限集合</p> 
     * @author ztjie
     * @date 2013-2-21 下午3:36:09
     * @param userCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptDataDao#getUserDeptDataByCode(java.lang.String)
     */
	@Override
	public List<String> getUserDeptDataByCode(String userCode) {
		UserDeptDataDto userDeptData = (UserDeptDataDto) this.getSqlSession().selectOne(NAMESPACE + "getUserDeptDataByCode", userCode);
		if(userDeptData!=null){
			return userDeptData.getOrgCodes();
		}else{
			return null;			
		}
	}

	/**
	 * 
	 * <p>刷新用户部门数据权限</p> 
	 * @author ztjie
	 * @date 2013-2-21 下午3:36:12 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptDataDao#reflashUserDeptData()
	 */
	@Override
	public void refreshUserDeptData(String userCode) {
		this.getSqlSession().selectOne(NAMESPACE + "refreshUserDeptData",userCode);
	}
	
	/**
	 * 
	 * <p>同步组织的时候，进行用户部门数据的增加</p> 
	 * @author ztjie
	 * @date 2013-7-25 下午1:47:23
	 * @param orgCode 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDeptDataDao#addUserDeptBySyncOrg(java.lang.String)
	 */
	@Override
	public void addUserDeptBySyncOrg(String orgCode) {
		this.getSqlSession().selectOne(NAMESPACE + "addUserDeptBySyncOrg",orgCode);
	}
	
}
