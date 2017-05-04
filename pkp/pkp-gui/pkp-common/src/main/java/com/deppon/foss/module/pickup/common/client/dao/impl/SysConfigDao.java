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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/SysConfigDao.java
 * 
 * FILE NAME        	: SysConfigDao.java
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
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.common.client.dto.SysConfigDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 系统配置数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-23 下午4:06:37, </p>
 * @author foss-sunrui
 * @date 2012-10-23 下午4:06:37
 * @since
 * @version
 */
public class SysConfigDao implements ISysConfigDao {
	/**
	 * 名称空间
	 */
    private static final String NAMESPACE = "pkp.sysConfigEntityMapper.";
    /**
	 * 数据库连接
	 */
    private SqlSession sqlSession;
    /**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */
    @Inject
    public void setSqlSession(SqlSession sqlSession) {
    	this.sqlSession = sqlSession;
    }

    /**
     * 
     * <p>通过主键查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:10:22
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao#selectByPrimaryKey(java.lang.String)
     */
    public ConfigurationParamsEntity queryByPrimaryKey(String id) {
    	return sqlSession.selectOne(NAMESPACE + "selectSysConfigByPrimaryKey", id);
    }

    /**
     * 
     * <p>通过配置项标示查询系统配置,根据confCode和orgCode一起查询</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:10:25
     * @param confCode
     * @param orgCode
     * @return 
     */
    public ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCode(confCode);
		if(orgCode!=null&&!orgCode.equals("")){
			sysConfig.setOrgCode(orgCode);
		}else{
			sysConfig.setOrgCode(FossConstants.ROOT_ORG_CODE);
		}
		sysConfig.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		sysConfig.setActive(FossConstants.ACTIVE);
		return sqlSession.selectOne(NAMESPACE + "selectSysConfigByConfCode", sysConfig);
    }
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,只根据confCode来查询</p> 
     * @author niujian
     * @date 2012-10-23 下午4:10:25
     * @param confCode
     * @return 
     */
    public ConfigurationParamsEntity queryByConfCode(String confCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCode(confCode);
		sysConfig.setActive(FossConstants.ACTIVE);
		return sqlSession.selectOne(NAMESPACE + "selectSysConfigByConfCode", sysConfig);
    }
    
    /**
     * 
     * <p>通过配置项集合查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-24 下午2:36:42
     * @param confCodes
     * @param orgCode
     * @return 
     * @see com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao#queryByConfCodeArray(java.lang.String[])
     */
	public List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes, String orgCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCodes(confCodes);
		sysConfig.setOrgCode(orgCode);
		sysConfig.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		sysConfig.setActive(FossConstants.ACTIVE);
		return sqlSession.selectList(NAMESPACE + "selectSysConfigByConfCodeArray", sysConfig);
	}
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addConfig(ConfigurationParamsEntity config) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", config.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertSysConfig", config);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateConfig(ConfigurationParamsEntity config) {
		sqlSession.update(NAMESPACE + "updateSysConfigByPrimaryKey", config);
	}


	/**
	 * 删除
	 * @param config
	 */
	public void delete(ConfigurationParamsEntity config) {
		sqlSession.delete(NAMESPACE + "delete", config);
	}

}