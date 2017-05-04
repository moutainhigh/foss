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
 * File Name：ConfigOrgRelationDao.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;

/**
 * Class Description： 配置项与组织对应关系DAO
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午6:04:16
 */
@SuppressWarnings("unchecked")
public class ConfigOrgRelationDao extends iBatis3DaoImpl implements IConfigOrgRelationDao {

	// 定义命名空间
	private static final String NAMESPACE = "foss.management.configorgrelation.";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity
	 * , int, int)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(ConfigOrgRelationEntity configOrgRelationEntity, int start, int limit) {
		// 查询组织配置信息列表
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigOrgRelationEntityList", configOrgRelationEntity, new RowBounds(start, limit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntityTotalCount(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigOrgRelationEntity)
	 */

	@Override
	public Long queryConfigOrgRelationEntityTotalCount(ConfigOrgRelationEntity configOrgRelationEntity) {
		// 查询组织配置信息数量
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryConfigOrgRelationEntityTotalCount", configOrgRelationEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntityList(java.util.Map, int, int)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(Map<String, Object> conditionMap, int start, int limit) {
		// 查询组织配置信息列表
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigOrgRelationEntityListByMap", conditionMap, new RowBounds(start, limit));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntityTotalCount(java.util.Map)
	 */

	@Override
	public Long queryConfigOrgRelationEntityTotalCount(Map<String, Object> conditionMap) {
		// 查询组织配置信息数量
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryConfigOrgRelationEntityTotalCountByMap", conditionMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#abolishConfigOrgRelation(java.util.Map)
	 */
	@Override
	public void abolishConfigOrgRelation(Map<String, Object> abolishMap) {
		this.getSqlSession().selectOne(NAMESPACE + "abolishConfigOrgRelation", abolishMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#modifyConfigOrgRealtion(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity)
	 */
	@Override
	public void updateConfigOrgRealtion(ConfigOrgRelationEntity configOrgRelationEntity) {
		this.getSqlSession().selectOne(NAMESPACE + "insertConfigOrgRealtion", configOrgRelationEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#addConfigOrgRelationList(java.util.List)
	 */
	@Override
	public void addConfigOrgRelationList(List<ConfigOrgRelationEntity> newConfigOrgRelationEntityList) {
		this.getSqlSession().insert(NAMESPACE + "addConfigOrgRelationList", newConfigOrgRelationEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryCountByConfigOrgRelationEntity(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigOrgRelationEntity)
	 */
	@Override
	public Long queryCountByConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCountByConfigOrgRelationEntity", configOrgRelationEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity
	 * )
	 */
	@Override
	public ConfigOrgRelationEntity queryConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity) {
		return (ConfigOrgRelationEntity) this.getSqlSession().selectOne(NAMESPACE + "queryConfigOrgRelationEntity", configOrgRelationEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgRelationEntityListNoPage(java.util.Map)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityListNoPage(Map<String, Object> conditionMap) {
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigOrgRelationEntityListNoPageByMap", conditionMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#abolishConfigOrgRelationEntityList(java.util.List)
	 */

	@Override
	public void abolishConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {
		this.getSqlSession().update(NAMESPACE + "abolishConfigOrgRelationEntityList", configOrgRelationEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#saveConfigOrgRelationEntityList(java.util.List)
	 */

	@Override
	public void saveConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#updateConfigOrgRelationEntityList(java.util.List)
	 */

	@Override
	public void updateConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#updateConfigOrgRelationEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity
	 * )
	 */

	@Override
	public void updateConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity) {

	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryDipConfigOrgInfoByConfType(java.lang.String)
	 */
	@Override
	public List<ConfigOrgRelationEntity> queryDipConfigOrgInfoByConfType(String confType) {
		// 查询顶级组织（DIP）配置信息列表
		return this.getSqlSession().selectList(NAMESPACE + "queryDipConfigOrgInfoByConfType", confType);
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao#queryConfigOrgInfoByConfTypeAndOrgCode(java.util.Map)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfTypeAndOrgCode(Map<String, Object> queryMap) {
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigOrgInfoByConfTypeAndOrgCode", queryMap);
	}
	
}
