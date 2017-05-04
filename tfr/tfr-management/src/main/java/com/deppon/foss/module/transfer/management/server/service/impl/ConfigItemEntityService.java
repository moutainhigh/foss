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
 * File Name：ConfigItemEntityService.java   
 *   
 * Version:1.0
 * ：2013-4-2   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.server.dao.IConfigItemEntityDao;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService;
import com.deppon.foss.module.transfer.management.api.shared.define.ConfigOrgRelationConstant;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * Class Description：配置相关服务类实现
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-2 上午9:40:43
 */

public class ConfigItemEntityService implements IConfigItemEntityService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigItemEntityService.class);

	private IConfigOrgRelationService configOrgRelationService;

	/**
	 * configOrgRelationService
	 * 
	 * @return the configOrgRelationService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IConfigOrgRelationService getConfigOrgRelationService() {
		return configOrgRelationService;
	}

	/**
	 * @param configOrgRelationService the configOrgRelationService to set Date:2013-4-18上午10:54:31
	 */

	public void setConfigOrgRelationService(IConfigOrgRelationService configOrgRelationService) {
		this.configOrgRelationService = configOrgRelationService;
	}

	/**
	 * 配置项DAO
	 */
	private IConfigItemEntityDao configItemEntityDao;

	/**
	 * configItemEntityDao
	 * 
	 * @return the configItemEntityDao
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IConfigItemEntityDao getConfigItemEntityDao() {
		return configItemEntityDao;
	}

	/**
	 * @param configItemEntityDao the configItemEntityDao to set Date:2013-4-2上午10:05:45
	 */

	public void setConfigItemEntityDao(IConfigItemEntityDao configItemEntityDao) {
		this.configItemEntityDao = configItemEntityDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryDistinctConfigTypes()
	 */
	@Override
	public List<ConfigItemEntity> queryDistinctConfigTypes() {
		// 查询唯一的配置项类型信息
		// return configItemEntityDao.queryDistinctConfigTypes(); modify by liangfuxiang 2013-04-17
		// 查询所有的配置型类型信息
		List<ConfigTypeEntity> configTypeEntityList = configItemEntityDao.queryAllConfigTypes();
		// 非空
		if (CollectionUtils.isNotEmpty(configTypeEntityList)) {
			List<ConfigItemEntity> configItemEntityList = new ArrayList<ConfigItemEntity>();
			ConfigItemEntity configItemEntity = null;
			// 遍历：将配置项类型信息转换为配置项信息
			for (ConfigTypeEntity configTypeEntity : configTypeEntityList) {
				configItemEntity = new ConfigItemEntity();
				configItemEntity.setConfType(configTypeEntity.getConfType());
				configItemEntity.setConfTypeName(configTypeEntity.getConfTypeName());
				configItemEntityList.add(configItemEntity);
			}
			return configItemEntityList;
		}
		else {
			// 为空，说明还未初始化，提示用户初始化。
			logger.info("ConfigItemEntityService[queryDistinctConfigTypes()]" + ConfigOrgRelationConstant.CONFIG_TYPE_NOT_INIT);
			throw new TfrBusinessException("ConfigItemEntityService[queryDistinctConfigTypes()]" + ConfigOrgRelationConstant.CONFIG_TYPE_NOT_INIT);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigItemEntitsByConfigType(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigItemEntity)
	 */

	@Override
	public List<ConfigItemEntity> queryConfigItemEntitsByConfigType(ConfigItemEntity configItemEntity) {
		if (null != configItemEntity) {
			// 根据配置项类型查询配置项信息
			return configItemEntityDao.queryConfigItemEntitsByConfigType(configItemEntity);
		}
		else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#addConfigTypeEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	@Transactional
	public void addConfigTypeEntity(ConfigTypeEntity configTypeEntity) throws TfrBusinessException {
		// 新增配置项类型信息
		addConfigTypeEntityMethod(configTypeEntity);
	}

	/**
	 * 
	 * @Title: addConfigTypeEntityMethod
	 * @Description: 新增配置项类型信息
	 * @param configTypeEntity 设定文件
	 * @return void 返回类型
	 * @see addConfigTypeEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:36:07
	 * @throws
	 */
	private void addConfigTypeEntityMethod(ConfigTypeEntity configTypeEntity) {
		if (null != configTypeEntity) {

			// 先判断新增的配置类型是否已经存在:不存在，新增
			if (!isExistConfigTypeEntity(configTypeEntity)) {
				// id
				configTypeEntity.setId(UUIDUtils.getUUID());
				// 创建人
				configTypeEntity.setCreateUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
				// 版本号
				configTypeEntity.setVersionNo(new Date().getTime());
				// 有效性
				configTypeEntity.setActive(ConfigOrgRelationConstant.CONFIG_TYPE_ACTIVE_YES);
				configItemEntityDao.addConfigTypeEntity(configTypeEntity);
			}
			else {
				logger.info("ConfigItemEntityService[addConfigTypeEntity()]:" + ConfigOrgRelationConstant.CONF_TYPE_EXSIT);
				throw new TfrBusinessException(ConfigOrgRelationConstant.CONF_TYPE_EXSIT);
			}
		}
	}

	/**
	 * @param configTypeEntity
	 * @Title: isExistConfigTypeEntity
	 * @Description: 断新增的配置类型是否已经存在
	 * @return 设定文件
	 * @return boolean 返回类型
	 * @see isExistConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:19:32
	 * @throws
	 */
	private boolean isExistConfigTypeEntity(ConfigTypeEntity configTypeEntity) {
		// 返回的数量大于0，则说明已经存在
		return (Long) configItemEntityDao.queryConfigTypeEntityCount(configTypeEntity) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#abolishConfigTypeEntity(java.util.List)
	 */
	@Override
	@Transactional
	public void abolishConfigTypeEntity(List<ConfigTypeEntity> configTypeEntityList) {
		// 作废配置类型信息
		abolishConfigTypeEntityMethod(configTypeEntityList);
	}

	/**
	 * 
	 * @Title: abolishConfigTypeEntityMethod
	 * @Description: 作废配置类型信息
	 * @param configTypeEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigTypeEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:35:23
	 * @throws
	 */
	private void abolishConfigTypeEntityMethod(List<ConfigTypeEntity> configTypeEntityList) {
		// 集合非空
		if (CollectionUtils.isNotEmpty(configTypeEntityList)) {
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			// 建立新配置项类型对象列表
			List<ConfigTypeEntity> newConfigTypeEntityList = new ArrayList<ConfigTypeEntity>();

			// 建立配置项对象列表
			List<ConfigItemEntity> configItemEntityList = new ArrayList<ConfigItemEntity>();
			ConfigItemEntity configItemEntity = null;
			// 建立对应配置项组织对象列表
			List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();
			ConfigOrgRelationEntity configOrgRelationEntity = null;
			// 遍历赋值
			for (ConfigTypeEntity configTypeEntity : configTypeEntityList) {
				// 配置项类型
				configTypeEntity.setModifyUser(modifyUserCode);
				// 无效
				configTypeEntity.setActive(ConfigOrgRelationConstant.CONFIG_TYPE_ACTIVE_NO);
				newConfigTypeEntityList.add(configTypeEntity);

				// 配置项
				configItemEntity = new ConfigItemEntity();
				configItemEntity.setId(configTypeEntity.getId());
				configItemEntity.setModifyUserCode(modifyUserCode);
				configItemEntity.setActive(ConfigOrgRelationConstant.CONFIG_TYPE_ACTIVE_NO);
				configItemEntityList.add(configItemEntity);

				// 配置项组织
				configOrgRelationEntity = new ConfigOrgRelationEntity();
				configOrgRelationEntity.setId(configTypeEntity.getId());
				configOrgRelationEntity.setActive(ConfigOrgRelationConstant.CONFIG_TYPE_ACTIVE_NO);
				configOrgRelationEntity.setModifyUserCode(modifyUserCode);
				configOrgRelationEntityList.add(configOrgRelationEntity);
			}

			// 作废配置类型信息
			configItemEntityDao.abolishConfigTypeEntity(newConfigTypeEntityList);
			// 作废配置项信息
			configItemEntityDao.abolishConfigItemEntityList(configItemEntityList);
			// 作废配置组织信息
			configOrgRelationService.abolishConfigOrgRelationEntityList(configOrgRelationEntityList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#modifyConfigTypeEtity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */
	@Override
	@Transactional
	public void modifyConfigTypeEntity(ConfigTypeEntity configTypeEntity) throws TfrBusinessException {
		// 对象非空
		if (null != configTypeEntity) {
			// 先判断修改后的对象是否已经存在:不存在,则修改
			if (!isExistConfigTypeEntity(configTypeEntity)) {
				// 获取旧的ID
				String oldId = configTypeEntity.getId();
				// 作废当前信息
				List<ConfigTypeEntity> configTypeEntityList = new ArrayList<ConfigTypeEntity>();
				configTypeEntityList.add(configTypeEntity);
				// 作废老的配置项类型信息
				abolishOldConfigTypeEntityMethod(configTypeEntityList);

				// 插入新的对象信息
				addConfigTypeEntityMethod(configTypeEntity);

				configTypeEntity.setId(oldId);
				// 配置项信息需要做相应修改。
				modifyConfigItemTypeRefItem(configTypeEntity);
				// 组织配置项需要做相应修改。
				modifyConfigItemTypeRefOrg(configTypeEntity);
			}
			else {
				logger.warn("ConfigItemEntityService[modifyConfigTypeEntity()]:" + ConfigOrgRelationConstant.CONF_TYPE_EXSIT);
				throw new TfrBusinessException(ConfigOrgRelationConstant.CONF_TYPE_EXSIT);
			}
		}
	}

	/**
	 * @Title: abolishOldConfigTypeEntityMethod
	 * @Description: 作废老的配置项类型信息
	 * @param configTypeEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishOldConfigTypeEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 下午3:26:28
	 * @throws
	 */
	private void abolishOldConfigTypeEntityMethod(List<ConfigTypeEntity> configTypeEntityList) {
		// 集合非空
		if (CollectionUtils.isNotEmpty(configTypeEntityList)) {
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			// 建立新配置项类型对象列表
			List<ConfigTypeEntity> newConfigTypeEntityList = new ArrayList<ConfigTypeEntity>();
			// 遍历赋值
			for (ConfigTypeEntity configTypeEntity : configTypeEntityList) {
				// 配置项类型
				configTypeEntity.setModifyUser(modifyUserCode);
				// 无效
				configTypeEntity.setActive(ConfigOrgRelationConstant.CONFIG_TYPE_ACTIVE_NO);
				newConfigTypeEntityList.add(configTypeEntity);
			}

			// 作废配置类型信息
			configItemEntityDao.abolishConfigTypeEntity(newConfigTypeEntityList);
		}

	}

	/**
	 * @Title: modifyConfigItemTypeRefOrg
	 * @Description: 组织配置项需要做相应修改。
	 * @param configTypeEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigItemTypeRefOrg
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 下午12:53:06
	 * @throws
	 */
	private void modifyConfigItemTypeRefOrg(ConfigTypeEntity configTypeEntity) {
		configTypeEntity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		configItemEntityDao.modifyConfigItemTypeRefOrg(configTypeEntity);
	}

	/**
	 * @Title: modifyConfigItemTypeRefItem
	 * @Description: 配置项信息需要做相应修改。
	 * @param configTypeEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigItemTypeRefItem
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 下午12:52:53
	 * @throws
	 */
	private void modifyConfigItemTypeRefItem(ConfigTypeEntity configTypeEntity) {
		configTypeEntity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		configItemEntityDao.modifyConfigItemTypeRefItem(configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigTypeEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */
	@Override
	public List<ConfigTypeEntity> queryConfigTypeEntityList(ConfigTypeEntity configTypeEntity, int start, int limit) {
		// configTypeEntity为空，查询出所有，否则查询指定类型
		return configItemEntityDao.queryConfigTypeEntityList(configTypeEntity, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#addConfigItemEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	@Transactional
	public void addConfigItemEntity(ConfigItemEntity configItemEntity) throws TfrBusinessException {
		// 新增配置项类型信息
		addConfigItemEntityMethod(configItemEntity);
	}

	/**
	 * 
	 * @Title: addConfigItemEntityMethod
	 * @Description: 新增配置项类型信息
	 * @param configItemEntity 设定文件
	 * @return void 返回类型
	 * @see addConfigItemEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:33:11
	 * @throws
	 */
	private void addConfigItemEntityMethod(ConfigItemEntity configItemEntity) {
		// 新增配置项类型信息
		if (null != configItemEntity) {

			// 先判断新增的配置项是否已经存在:不存在，新增
			if (!isExistConfigItemEntity(configItemEntity)) {
				// ID
				configItemEntity.setId(UUIDUtils.getUUID());
				// 创建人
				configItemEntity.setCreateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
				// 有效性
				configItemEntity.setActive(ConfigOrgRelationConstant.CONFIG_ITEM_ACTIVE_YES);
				// 版本号
				configItemEntity.setVersionNo(new Date().getTime());
				configItemEntityDao.addConfigItemEntity(configItemEntity);
			}
			else {
				logger.info("ConfigItemEntityService[addConfigItemEntity()]:" + ConfigOrgRelationConstant.CONF_ITEM_EXSIT);
				throw new TfrBusinessException(ConfigOrgRelationConstant.CONF_ITEM_EXSIT);
			}
		}
	}

	/**
	 * @Title: isExistConfigTypeEntity
	 * @Description: 判断新增的配置项是否已经存在
	 * @param configItemEntity
	 * @return 设定文件
	 * @return boolean 返回类型
	 * @see isExistConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:52:00
	 * @throws
	 */
	private boolean isExistConfigItemEntity(ConfigItemEntity configItemEntity) {
		return (Long) configItemEntityDao.queryConfigItemEntityCount(configItemEntity) > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#abolishConfigItemEntity(java.util.List)
	 */
	@Override
	@Transactional
	public void abolishConfigItemEntity(List<ConfigItemEntity> configItemEntityList) {
		// 作废配置项信息
		abolishConfigItemEntityMethod(configItemEntityList);
	}

	/**
	 * 
	 * @Title: abolishConfigItemEntityMethod
	 * @Description: 作废配置项信息
	 * @param configItemEntityList 设定文件
	 * @return void 作废配置项信息
	 * @see abolishConfigItemEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:34:32
	 * @throws
	 */
	private void abolishConfigItemEntityMethod(List<ConfigItemEntity> configItemEntityList) {
		// 集合非空
		if (CollectionUtils.isNotEmpty(configItemEntityList)) {
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			// 建立新新对象列表
			List<ConfigItemEntity> newConfigItemEntityList = new ArrayList<ConfigItemEntity>();
			// 遍历赋值
			for (ConfigItemEntity configItemEntity : configItemEntityList) {
				configItemEntity.setModifyUser(modifyUserCode);
				// 无效
				configItemEntity.setActive(ConfigOrgRelationConstant.CONFIG_ITEM_ACTIVE_NO);
				newConfigItemEntityList.add(configItemEntity);
			}

			// 作废配置
			configItemEntityDao.abolishConfigItemEntity(newConfigItemEntityList);

			// 作废配置项相关组织。
			configItemEntityDao.abolishConfigItemEntityRefOrg(newConfigItemEntityList);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#modifyConfigItemEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */
	@Override
	@Transactional
	public void modifyConfigItemEntity(ConfigItemEntity configItemEntity) {
		// 对象非空
		if (null != configItemEntity) {
			// 先判断修改后的对象是否已经存在:不存在,则修改
			if (!isExistConfigItemEntity(configItemEntity)) {
				// 获取旧的ID
				String oldId = configItemEntity.getId();
				// 作废当前信息
				List<ConfigItemEntity> configItemEntityList = new ArrayList<ConfigItemEntity>();
				configItemEntityList.add(configItemEntity);

				abolishOldConfigItemEntityMethod(configItemEntityList);

				// 插入新的对象信息
				addConfigItemEntityMethod(configItemEntity);

				configItemEntity.setId(oldId);
				// 同时修改组织配置项信息
				modifyConfigItemEntityRefOrg(configItemEntity);
			}
		}
	}

	/**
	 * @Title: abolishOldConfigItemEntityMethod
	 * @Description: 在修改配置项之前，先作废老的项目。
	 * @param configItemEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishOldConfigItemEntityMethod
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 下午3:05:13
	 * @throws
	 */
	private void abolishOldConfigItemEntityMethod(List<ConfigItemEntity> configItemEntityList) {
		// 集合非空
		if (CollectionUtils.isNotEmpty(configItemEntityList)) {
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();
			// 建立新新对象列表
			List<ConfigItemEntity> newConfigItemEntityList = new ArrayList<ConfigItemEntity>();
			// 遍历赋值
			for (ConfigItemEntity configItemEntity : configItemEntityList) {
				configItemEntity.setModifyUser(modifyUserCode);
				// 无效
				configItemEntity.setActive(ConfigOrgRelationConstant.CONFIG_ITEM_ACTIVE_NO);
				newConfigItemEntityList.add(configItemEntity);
			}
			// 作废配置
			configItemEntityDao.abolishConfigItemEntity(newConfigItemEntityList);
		}
	}

	/**
	 * @Title: modifyConfigItemEntityRefOrg
	 * @Description: 修改组织配置项信息
	 * @param configItemEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigItemEntityRefOrg
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 下午2:29:42
	 * @throws
	 */
	private void modifyConfigItemEntityRefOrg(ConfigItemEntity configItemEntity) {
		configItemEntityDao.modifyConfigItemEntityRefOrg(configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigItemEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */
	@Override
	public List<ConfigItemEntity> queryConfigItemEntityList(ConfigItemEntity configItemEntity, int start, int limit) {
		// configItemEntity为空，查询出所有，否则查询指定类型
		return configItemEntityDao.queryConfigItemEntityList(configItemEntity, start, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigTypeEntityListCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity
	 * )
	 */

	@Override
	public Long queryConfigTypeEntityListCount(ConfigTypeEntity configTypeEntity) {
		return configItemEntityDao.queryConfigTypeEntityListCount(configTypeEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigItemEntityListCount(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity
	 * )
	 */

	@Override
	public Long queryConfigItemEntityListCount(ConfigItemEntity configItemEntity) {
		return configItemEntityDao.queryConfigItemEntityListCount(configItemEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigTypeEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity)
	 */

	@Override
	public ConfigTypeEntity queryConfigTypeEntity(ConfigTypeEntity configTypeEntity) throws TfrBusinessException {
		// 查询条件丢失你，则返回异常
		if (null == configTypeEntity || StringUtils.isEmpty(configTypeEntity.getId())) {
			logger.error("ConfigItemEntityService[queryConfigTypeEntity()]:" + ConfigOrgRelationConstant.CONFIG_INFO_NULL);
			throw new TfrBusinessException("ConfigItemEntityService[queryConfigTypeEntity()]:" + ConfigOrgRelationConstant.CONFIG_INFO_NULL);
		}
		else {
			// 查询
			return configItemEntityDao.queryConfigTypeEntity(configTypeEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryConfigItemEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity)
	 */

	@Override
	public ConfigItemEntity queryConfigItemEntity(ConfigItemEntity configItemEntity) throws TfrBusinessException {
		// 查询条件丢失你，则返回异常
		if (null == configItemEntity || StringUtils.isEmpty(configItemEntity.getId())) {
			logger.error("ConfigItemEntityService[queryConfigItemEntity()]:" + ConfigOrgRelationConstant.CONFIG_INFO_NULL);
			throw new TfrBusinessException("ConfigItemEntityService[queryConfigItemEntity()]:" + ConfigOrgRelationConstant.CONFIG_INFO_NULL);
		}
		else {
			// 查询
			return configItemEntityDao.queryConfigItemEntity(configItemEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService#queryAllConfigTypes()
	 */

	@Override
	public List<ConfigTypeEntity> queryAllConfigTypes() throws TfrBusinessException {
		// 查询所有的配置型类型信息
		List<ConfigTypeEntity> configTypeEntityList = configItemEntityDao.queryAllConfigTypes();
		// 非空
		if (CollectionUtils.isNotEmpty(configTypeEntityList)) {
			return configTypeEntityList;
		}
		else {
			// 为空，说明还未初始化，提示用户初始化。
			logger.info("ConfigItemEntityService[queryAllConfigTypes()]" + ConfigOrgRelationConstant.CONFIG_TYPE_NOT_INIT);
			throw new TfrBusinessException("ConfigItemEntityService[queryAllConfigTypes()]" + ConfigOrgRelationConstant.CONFIG_TYPE_NOT_INIT);
		}
	}
}
