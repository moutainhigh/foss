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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/dao/impl/UserDefinedQueryDao.java
 * 
 * FILE NAME        	: UserDefinedQueryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDefinedQueryDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQueryConditionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserDefinedQuerySchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 自定义查询DAO接口实现类：提供对自定义查询的增删改查的基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:073586-FOSS-LIXUEXING,date:2013-01-21 20:33
 * </p>
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:33
 * @since
 * @version
 */
public class UserDefinedQueryDao extends SqlSessionDaoSupport implements
	IUserDefinedQueryDao {

    private static final String NAMESPACE_SCHEME = QueryingConstant.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".userDefinedQueryScheme.";

    private static final String NAMESPACE_CONDITION = QueryingConstant.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".userDefinedQueryCondition.";

    @Override
    public int addUserDefinedQueryScheme(UserDefinedQuerySchemeEntity entity) {
	entity.setId(UUIDUtils.getUUID());
	entity.setCode(entity.getId());
	entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
	return this.getSqlSession().insert(NAMESPACE_SCHEME + "insert", entity);
    }

    @Override
    public int deleteUserDefinedQuerySchemeByCode(String[] codes,
	    String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);

	return this.getSqlSession().update(NAMESPACE_SCHEME + "deleteByCode",
		map);
    }

    @Override
    public int updateUserDefinedQueryScheme(UserDefinedQuerySchemeEntity entity) {
	return this.getSqlSession().update(NAMESPACE_SCHEME + "update", entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDefinedQuerySchemeEntity> queryUserDefinedQuerySchemes(
	    UserDefinedQuerySchemeEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
	return this.getSqlSession().selectList(
		NAMESPACE_SCHEME + "getAllSchemeInfos", entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDefinedQueryDto> queryUserDefinedQueryDtos( UserDefinedQueryDto entity) {
	entity.setActive(FossConstants.ACTIVE);
	return this.getSqlSession().selectList(
		NAMESPACE_SCHEME + "getAllInfos", entity);
    }

    @Override
    public int addUserDefinedQueryCondition(
	    UserDefinedQueryConditionEntity entity) {
	entity.setId(UUIDUtils.getUUID());
	return this.getSqlSession().insert(NAMESPACE_CONDITION + "insert",
		entity);
    }

    @Override
    public int deleteUserDefinedQueryConditionByCode(String[] codes,
	    String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);

	return this.getSqlSession().update(
		NAMESPACE_CONDITION + "deleteByCode", map);
    }

    @Override
    public int deleteUserDefinedQueryConditionBySchemeCode(String[] codes,
	    String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);

	return this.getSqlSession().update(
		NAMESPACE_CONDITION + "deleteBySchemeCode", map);
    }

    @Override
    public int updateUserDefinedQueryCondition(
	    UserDefinedQueryConditionEntity entity) {
	return this.getSqlSession().update(NAMESPACE_CONDITION + "update",
		entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDefinedQueryConditionEntity> queryUserDefinedQueryConditions(
	    UserDefinedQueryConditionEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
	return this.getSqlSession().selectList(
		NAMESPACE_CONDITION + "getAllConditionInfos", entity);
    }

}
