/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/CertificateBagAction.java
 *  
 *  FILE NAME          :CertificateBagAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**   
 * File Name：ConfigItemEntityDao.java   
 *   
 * Version:1.0
 * ：2013-4-2   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;

/**
 * Class Description：配置相关DAO实现
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-2 上午9:41:43
 */

@SuppressWarnings("unchecked")
public class ConfigItemEntityDao extends iBatis3DaoImpl implements IConfigItemEntityDao {

	// 定义命名空间
	private static final String NAMESPACE = "foss.management.configitementity.";

	// 定义命名空间
	private static final String NAMESPACE2 = "foss.management.configtypeentity.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryDistinctConfigTypes()
	 */
	@Override
	public List<ConfigItemEntity> queryDistinctConfigTypes() {
		return this.getSqlSession().selectList(NAMESPACE + "queryDistinctConfigTypes");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigItemEntitsByConfigType(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */
	@Override
	public List<ConfigItemEntity> queryConfigItemEntitsByConfigType(ConfigItemEntity configItemEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigItemEntitsByConfigType", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#addConfigTypeEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	public void addConfigTypeEntity(ConfigTypeEntity configTypeEntity) {
		this.getSqlSession().insert(NAMESPACE2 + "addConfigTypeEntity", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#abolishConfigTypeEntity(java.util.List)
	 */
	@Override
	public void abolishConfigTypeEntity(List<ConfigTypeEntity> newConfigTypeEntityList) {
		this.getSqlSession().update(NAMESPACE2 + "abolishConfigTypeEntity", newConfigTypeEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigTypeEntityCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	public Long queryConfigTypeEntityCount(ConfigTypeEntity configTypeEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE2 + "queryConfigTypeEntityCount", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigTypeEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */
	@Override
	public List<ConfigTypeEntity> queryConfigTypeEntityList(ConfigTypeEntity configTypeEntity, int start, int limit) {
		return this.getSqlSession().selectList(NAMESPACE2 + "queryConfigTypeEntityList", configTypeEntity, new RowBounds(start, limit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigTypeEntityListCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	public Long queryConfigTypeEntityListCount(ConfigTypeEntity configTypeEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE2 + "queryConfigTypeEntityListCount", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#addConfigItemEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	public void addConfigItemEntity(ConfigItemEntity configItemEntity) {
		this.getSqlSession().insert(NAMESPACE + "addConfigItemEntity", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigItemEntityCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */
	@Override
	public Long queryConfigItemEntityCount(ConfigItemEntity configItemEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryConfigItemEntityCount", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#abolishConfigItemEntity(java.util.List)
	 */
	@Override
	public void abolishConfigItemEntity(List<ConfigItemEntity> newConfigItemEntityList) {
		this.getSqlSession().update(NAMESPACE + "abolishConfigItemEntity", newConfigItemEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigItemEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */
	@Override
	public List<ConfigItemEntity> queryConfigItemEntityList(ConfigItemEntity configItemEntity, int start, int limit) {
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigItemEntityList", configItemEntity, new RowBounds(start, limit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigItemEntityListCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	public Long queryConfigItemEntityListCount(ConfigItemEntity configItemEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryConfigItemEntityListCount", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigTypeEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	public ConfigTypeEntity queryConfigTypeEntity(ConfigTypeEntity configTypeEntity) {
		return (ConfigTypeEntity) this.getSqlSession().selectOne(NAMESPACE2 + "queryConfigTypeEntity", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryConfigItemEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	public ConfigItemEntity queryConfigItemEntity(ConfigItemEntity configItemEntity) {
		return (ConfigItemEntity) this.getSqlSession().selectOne(NAMESPACE + "queryConfigItemEntity", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#queryAllConfigTypes()
	 */

	@Override
	public List<ConfigTypeEntity> queryAllConfigTypes() {
		return this.getSqlSession().selectList(NAMESPACE2 + "queryAllConfigTypes");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#abolishConfigItemEntityList(java.util.List)
	 */
	@Override
	public void abolishConfigItemEntityList(List<ConfigItemEntity> configItemEntityList) {
		this.getSqlSession().update(NAMESPACE + "abolishConfigItemEntityList", configItemEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#modifyConfigItemTypeRefItem(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */
	@Override
	public void modifyConfigItemTypeRefItem(ConfigTypeEntity configTypeEntity) {
		this.getSqlSession().update(NAMESPACE2 + "modifyConfigItemTypeRefItem", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#modifyConfigItemTypeRefOrg(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */
	@Override
	public void modifyConfigItemTypeRefOrg(ConfigTypeEntity configTypeEntity) {
		this.getSqlSession().update(NAMESPACE2 + "modifyConfigItemTypeRefOrg", configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#modifyConfigItemEntityRefOrg(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	public void modifyConfigItemEntityRefOrg(ConfigItemEntity configItemEntity) {
		this.getSqlSession().update(NAMESPACE + "modifyConfigItemEntityRefOrg", configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao#abolishConfigItemEntityRefOrg(java.util.List)
	 */

	@Override
	public void abolishConfigItemEntityRefOrg(List<ConfigItemEntity> newConfigItemEntityList) {
		this.getSqlSession().update(NAMESPACE + "abolishConfigItemEntityRefOrg", newConfigItemEntityList);
	}
}
