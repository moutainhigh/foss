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
 * File Name：ConfigOrgRelationService.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.server.dao.IConfigOrgRelationDao;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService;
import com.deppon.foss.module.transfer.management.api.shared.define.ConfigOrgRelationConstant;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.UUIDUtils;

/**
 * Class Description： 配置项与组织对应关系服务类
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午6:01:52
 */

public class ConfigOrgRelationService implements IConfigOrgRelationService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigOrgRelationService.class);

	/**
	 * 配置项DAO
	 */
	private IConfigOrgRelationDao configOrgRelationDao;

	/**
	 * 定义组织配置项信息字符串
	 */
	private static final String CONFIGORGRELATIONENTITY = "configOrgRelationEntity";

	/**
	 * 定义组织编码集合字符串
	 */
	private static final String ORGCODES = "orgCodes";

	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * orgAdministrativeInfoComplexService
	 * 
	 * @return the orgAdministrativeInfoComplexService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set Date:2013-3-29下午6:38:00
	 */

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * configOrgRelationDao
	 * 
	 * @return the configOrgRelationDao
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public IConfigOrgRelationDao getConfigOrgRelationDao() {
		return configOrgRelationDao;
	}

	/**
	 * @param configOrgRelationDao the configOrgRelationDao to set Date:2013-3-29上午10:55:04
	 */

	public void setConfigOrgRelationDao(IConfigOrgRelationDao configOrgRelationDao) {
		this.configOrgRelationDao = configOrgRelationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryConfigOrgRelationEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigOrgRelationEntity, int, int)
	 */

	/**
	 * configItemEntityService
	 * 
	 * @return the configItemEntityService
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */
	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(ConfigOrgRelationEntity configOrgRelationEntity, int start, int limit) {

		List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();

		// 构造配置项查询条件
		Map<String, Object> conditionMap = getConditionMap(configOrgRelationEntity);

		@SuppressWarnings("unchecked")
		List<String> orgCodes = (List<String>) conditionMap.get(ORGCODES);

		// 解决oracle列表最多只能1000条数据的问题 ORA-01795: 列表中的最大表达式数为 1000
		
		if(!CollectionUtils.isEmpty(orgCodes)){
			if (orgCodes.size() > ConstantsNumberSonar.SONAR_NUMBER_500) {
				// 列表拆分为多个列表，分别查询 以500为一个基点
				List<String> subOrgCodes = null;
				List<ConfigOrgRelationEntity> subConfigOrgRelationEntityList = null;
				int count = orgCodes.size() / ConstantsNumberSonar.SONAR_NUMBER_500;
				int mod = orgCodes.size() % ConstantsNumberSonar.SONAR_NUMBER_500;
				for (int i = 0; i < count; i++) {
					subOrgCodes = new ArrayList<String>(orgCodes.subList(i * ConstantsNumberSonar.SONAR_NUMBER_500, (i + 1) * ConstantsNumberSonar.SONAR_NUMBER_500));
					conditionMap.put(ORGCODES, subOrgCodes);
					// 查询
					subConfigOrgRelationEntityList = configOrgRelationDao.queryConfigOrgRelationEntityList(conditionMap, start, limit);
					// 合并结果子集至总结果集
					configOrgRelationEntityList = combineSubConfigOrgRelationEntityList(configOrgRelationEntityList, subConfigOrgRelationEntityList);
				}
				// 若存在余数
				if (mod > 0) {
					subOrgCodes = new ArrayList<String>(orgCodes.subList(count * ConstantsNumberSonar.SONAR_NUMBER_500, count * ConstantsNumberSonar.SONAR_NUMBER_500 + mod));
					conditionMap.put(ORGCODES, subOrgCodes);
					// 查询
					subConfigOrgRelationEntityList = configOrgRelationDao.queryConfigOrgRelationEntityList(conditionMap, start, limit);
					// 合并结果子集至总结果集
					configOrgRelationEntityList = combineSubConfigOrgRelationEntityList(configOrgRelationEntityList, subConfigOrgRelationEntityList);
				}
			}
			else{
				configOrgRelationEntityList = configOrgRelationDao.queryConfigOrgRelationEntityList(conditionMap, start, limit);
			}
		}
		else {
			configOrgRelationEntityList = configOrgRelationDao.queryConfigOrgRelationEntityList(conditionMap, start, limit);
		}

		// 返回组织配置信息列表
		return configOrgRelationEntityList;
	}

	/**
	 * @param configOrgRelationEntityList  
	* @Title: combineSubConfigOrgRelationEntityList 
	* @Description: 合并结果子集至总结果集
	* @param subConfigOrgRelationEntityList
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see combineSubConfigOrgRelationEntityList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午3:33:27   
	* @throws 
	*/ 
	private List<ConfigOrgRelationEntity> combineSubConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList, List<ConfigOrgRelationEntity> subConfigOrgRelationEntityList) {
		
		//若子集非空，直接返回
		if(!CollectionUtils.isEmpty(subConfigOrgRelationEntityList)){
			//遍历子集，增加至总总集
			for(ConfigOrgRelationEntity configOrgRelationEntity:subConfigOrgRelationEntityList){
				configOrgRelationEntityList.add(configOrgRelationEntity);
			}
		}
		return configOrgRelationEntityList;
	}

	/**
	 * @Title: getConditionMap
	 * @Description: 构造配置项查询条件
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @see getConditionMap
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午4:51:30
	 * @throws
	 */
	private Map<String, Object> getConditionMap(ConfigOrgRelationEntity configOrgRelationEntity) {

		// 获取组织编码
		String orgCode = configOrgRelationEntity.getOrgCode();
		
		// 组织编码集合
		List<String> orgCodeList = null;

		// 组织编码非空
		if (StringUtils.isNotEmpty(orgCode)) {
			logger.info("ConfigOrgRelationService[getConditionMap()]:orgCode=" + orgCode);
			// 获取当前组织编码及其子级别的组织编码
			orgCodeList = getOrgCodeAndSubOrgCode(orgCode);
		}
		
		// 查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>();

		// 添加组织配置项对象
		conditionMap.put(CONFIGORGRELATIONENTITY, configOrgRelationEntity);
		// 添加组织代码集合
		conditionMap.put(ORGCODES, orgCodeList);
		return conditionMap;
	}

	/**
	 * @Title: getOrgCodeAndSubOrgCode
	 * @Description: 根据当前组织编码及其下属组织编码
	 * @param orgCode
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see getOrgCodeAndSubOrgCode
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午4:36:32
	 * @throws
	 */
	private List<String> getOrgCodeAndSubOrgCode(String orgCode) {

		// 根据当前部门编码获取当前及其下属部门code
		List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgCode);
		
		// 返回组织编码集合
		return getOrgCodeList(orgAdministrativeInfoEntityList);
	}

	/**
	 * @Title: getOrgCodeList
	 * @Description: 返回组织编码集合
	 * @param orgAdministrativeInfoEntityList
	 * @return 设定文件
	 * @return List<String> 返回类型
	 * @see getOrgCodeList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午6:43:41
	 * @throws
	 */
	private List<String> getOrgCodeList(List<OrgAdministrativeInfoEntity> orgAdministrativeInfoEntityList) {

		// 部门信息非空
		if (!CollectionUtils.isEmpty(orgAdministrativeInfoEntityList)) {

			// 建立部门编码集合
			Set<String> orgCodeSet = new HashSet<String>();

			// 遍历部门信息列表
			for (OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgAdministrativeInfoEntityList) {
				orgCodeSet.add(orgAdministrativeInfoEntity.getCode());
			}

			// 返回组织编码集合
			return new ArrayList<String>(orgCodeSet);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryConfigOrgRelationEntityTotalCount(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigOrgRelationEntity)
	 */
	@Override
	public Long queryConfigOrgRelationEntityTotalCount(ConfigOrgRelationEntity configOrgRelationEntity) {
		// 构造配置项查询条件
		Map<String, Object> conditionMap = getConditionMap(configOrgRelationEntity);
		@SuppressWarnings("unchecked")
		List<String> orgCodes = (List<String>) conditionMap.get(ORGCODES);
		long globalCount = 0l;
		// 解决oracle列表最多只能1000条数据的问题 ORA-01795: 列表中的最大表达式数为 1000
		if (!CollectionUtils.isEmpty(orgCodes)) {
			if (orgCodes.size() > ConstantsNumberSonar.SONAR_NUMBER_500) {
				// 列表拆分为多个列表，分别查询 以500为一个基点
				List<String> subOrgCodes = null;
				int count = orgCodes.size() / ConstantsNumberSonar.SONAR_NUMBER_500;
				int mod = orgCodes.size() % ConstantsNumberSonar.SONAR_NUMBER_500;
				Long tempCount = 0l;
				for (int i = 0; i < count; i++) {
					subOrgCodes = new ArrayList<String>(orgCodes.subList(i * ConstantsNumberSonar.SONAR_NUMBER_500, (i + 1) * ConstantsNumberSonar.SONAR_NUMBER_500));
					conditionMap.put(ORGCODES, subOrgCodes);
					// 查询
					tempCount = configOrgRelationDao.queryConfigOrgRelationEntityTotalCount(conditionMap);
					// 合并总数量
					globalCount += tempCount;
				}
				// 若存在余数
				if (mod > 0) {
					subOrgCodes = new ArrayList<String>(orgCodes.subList(count * ConstantsNumberSonar.SONAR_NUMBER_500, count * ConstantsNumberSonar.SONAR_NUMBER_500 + mod));
					conditionMap.put(ORGCODES, subOrgCodes);
					// 查询
					tempCount = configOrgRelationDao.queryConfigOrgRelationEntityTotalCount(conditionMap);
					// 合并总数量
					globalCount += tempCount;
				}
			}
			else{
				globalCount = configOrgRelationDao.queryConfigOrgRelationEntityTotalCount(conditionMap);
			}
		}
		else {
			globalCount = configOrgRelationDao.queryConfigOrgRelationEntityTotalCount(conditionMap);
		}

		// 返回组织配置信息数量
		return globalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#abolishConfigOrgRelationById(java.util.List)
	 */

	@Transactional
	@Override
	public void abolishConfigOrgRelationById(List<String> idList) {

		// 配置参数ID集合非空
		if (!CollectionUtils.isEmpty(idList)) {
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();

			Map<String, Object> abolishMap = new HashMap<String, Object>();
			abolishMap.put("modifyUserCode", modifyUserCode);
			abolishMap.put("idList", idList);

			// 作废配置参数
			configOrgRelationDao.abolishConfigOrgRelation(abolishMap);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#modifyConfigOrgRealtion(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity
	 * )
	 */
	@Transactional
	@Override
	public void modifyConfigOrgRealtion(ConfigOrgRelationEntity configOrgRelationEntity) throws TfrBusinessException{

		if (null != configOrgRelationEntity) {

			// 判断修改后的信息是否已经存在
			List<ConfigOrgRelationEntity> configOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();
			configOrgRelationEntityList.add(configOrgRelationEntity);
			// 判断修改后的信息是否已经存在
			exsitConfigOrgRelationEntityList(configOrgRelationEntityList);

			// 不存在，则做如下修改操作。
			// 获取当前用户工号
			String modifyUserCode = FossUserContext.getCurrentUser().getEmployee().getEmpCode();

			// 将原纪录有效状态置为N
			Map<String, Object> abolishMap = new HashMap<String, Object>();
			abolishMap.put("modifyUserCode", modifyUserCode);
			List<String> idList=new ArrayList<String>();
			idList.add(configOrgRelationEntity.getId());
			abolishMap.put("idList",idList);
			configOrgRelationDao.abolishConfigOrgRelation(abolishMap);
			
			configOrgRelationEntity.setActive(ConfigOrgRelationConstant.CONFIGORGRELATION_ACTIVE_YES);
			
			// 插入新版本的信息
			configOrgRelationEntity.setVersionNo(new Date().getTime());
			// 修改人信息为空
			configOrgRelationEntity.setModifyUserCode(null);
			// 修改时间为空
			configOrgRelationEntity.setModifyTime(null);
			// 创建人为当前修改人
			configOrgRelationEntity.setCreateUserCode(modifyUserCode);
			// 创建ID
			configOrgRelationEntity.setId(UUIDUtils.getUUID());
			
			configOrgRelationDao.updateConfigOrgRealtion(configOrgRelationEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#addConfigOrgRelationList(java.util.List)
	 */
	@Transactional
	@Override
	public void addConfigOrgRelationList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) throws TfrBusinessException{
		// 判断配置参数属性的有效性
		isEffectiveConfigOrgRelationEntityList(configOrgRelationEntityList);
		// 首先判断新增的配置项信息是否已经存在
		exsitConfigOrgRelationEntityList(configOrgRelationEntityList);
		// 不存在，则 保存配置项信息
		addRealConfigOrgRelationList(configOrgRelationEntityList);
	}

	/**
	 * @Title: isEffectiveConfigOrgRelationDto
	 * @Description: 判断配置参数属性的有效性
	 * @param configOrgRelationDto 设定文件
	 * @return void 返回类型
	 * @see isEffectiveConfigOrgRelationDto
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-8 下午5:07:40
	 * @throws
	 */
	private void isEffectiveConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) throws TfrBusinessException {

		// 传入对象为空
		if (CollectionUtils.isEmpty(configOrgRelationEntityList)) {
			logger.error("ConfigOrgRelationService[isEffectiveConfigOrgRelationEntityList()]:" + ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_NULL);
			throw new TfrBusinessException(ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_NULL);
		}

		ConfigOrgRelationEntity configOrgRelationEntity = configOrgRelationEntityList.get(0);

		// 配置项编码为空
		if (StringUtil.isEmpty(configOrgRelationEntity.getConfCode())) {
			logger.error("ConfigOrgRelationService[isEffectiveConfigOrgRelationEntityList()]:" + ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFCODE_NULL);
			throw new TfrBusinessException(ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFCODE_NULL);
		}

		// 配置项类型为空
		if (StringUtils.isEmpty(configOrgRelationEntity.getConfType())) {
			logger.error("ConfigOrgRelationService[isEffectiveConfigOrgRelationEntityList()]:" + ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFTYPE_NULL);
			throw new TfrBusinessException(ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_CONFTYPE_NULL);
		}

		// 组织编码为空
		if (StringUtils.isEmpty(configOrgRelationEntity.getOrgCode())) {
			logger.error("ConfigOrgRelationService[isEffectiveConfigOrgRelationEntityList()]:" + ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_ORGCODE_NULL);
			throw new TfrBusinessException(ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_ORGCODE_NULL);
		}
	}

	/**
	 * @Title: exsitConfigOrgRelationEntityList
	 * @Description: 判断新增的配置项信息是否已经存在
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see exsitConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-7 下午8:22:36
	 * @throws
	 */
	private void exsitConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) throws TfrBusinessException {

		// 遍历查询，有一条存在，则抛出异常
		for (ConfigOrgRelationEntity configOrgRelationEntity : configOrgRelationEntityList) {
			// 查询到则抛出异常
			if (configOrgRelationDao.queryCountByConfigOrgRelationEntity(configOrgRelationEntity) > 0) {
				// 记录异常
				logger.error("ConfigOrgRelationService[exsitConfigOrgRelationEntityList()]:" + ConfigOrgRelationConstant.ORG_CONFIG_EXIST_EXCEPTION + configOrgRelationEntity.getOrgName()
						+ ConfigOrgRelationConstant.COLON_CONSTANT + configOrgRelationEntity.getConfName());
				// 抛出异常
				throw new TfrBusinessException(ConfigOrgRelationConstant.ORG_CONFIG_EXIST_EXCEPTION
						+ configOrgRelationEntity.getOrgName() + ConfigOrgRelationConstant.COLON_CONSTANT + configOrgRelationEntity.getConfName());
			}
		}
	}

	/**
	 * 
	 * @Title: addRealConfigOrgRelationList
	 * @Description: 保存配置项信息列表
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see addRealConfigOrgRelationList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-7 下午8:20:24
	 * @throws
	 */
	private void addRealConfigOrgRelationList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {

		List<ConfigOrgRelationEntity> newConfigOrgRelationEntityList = new ArrayList<ConfigOrgRelationEntity>();

		for (ConfigOrgRelationEntity configOrgRelationEntity : configOrgRelationEntityList) {
			// 设置ID
			configOrgRelationEntity.setId(UUIDUtils.getUUID());
			// 创建人
			configOrgRelationEntity.setCreateUserCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());

			// 有效
			configOrgRelationEntity.setActive(ConfigOrgRelationConstant.CONFIGORGRELATION_ACTIVE_YES);

			// 设置版本号
			configOrgRelationEntity.setVersionNo(new Date().getTime());

			newConfigOrgRelationEntityList.add(configOrgRelationEntity);
		}
		// 保存
		configOrgRelationDao.addConfigOrgRelationList(newConfigOrgRelationEntityList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryConfigOrgRelationEntity(com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity
	 * )
	 */

	@Override
	public ConfigOrgRelationEntity queryConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity) throws TfrBusinessException {

		// 若返回对象为空或者是返回的ID为空
		if (null == configOrgRelationEntity || StringUtils.isEmpty(configOrgRelationEntity.getId())) {
			// 记录异常
			logger.error("ConfigOrgRelationService[queryConfigOrgRelationEntity()]:" + ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_NULL);
			// 抛出异常
			throw new TfrBusinessException(ConfigOrgRelationConstant.CONFIGORGRELATIONENTITY_NULL);
		}

		// 查询配置参数信息
		return configOrgRelationDao.queryConfigOrgRelationEntity(configOrgRelationEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryConfigOrgRelationEntityList(com.deppon.foss.module.transfer.management.api.shared.domain.
	 * ConfigOrgRelationEntity)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityListNoPage(ConfigOrgRelationEntity configOrgRelationEntity) {

		List<String> orgCodeList = new ArrayList<String>();
		// 添加部门编码
		orgCodeList.add(configOrgRelationEntity.getOrgCode());
		// 查询条件
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		// 添加组织配置项对象
		conditionMap.put(CONFIGORGRELATIONENTITY, configOrgRelationEntity);
		// 添加组织代码集合
		conditionMap.put(ORGCODES, orgCodeList);

		// 返回组织配置信息列表
		return configOrgRelationDao.queryConfigOrgRelationEntityListNoPage(conditionMap);
	}

	  
	    /* (non-Javadoc)   
	     * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#abolishConfigOrgRelationEntityList(java.util.List)   
	     */   
	    
	@Override
	public void abolishConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {
		
		//列表非空
		if(!CollectionUtils.isEmpty(configOrgRelationEntityList)){
			configOrgRelationDao.abolishConfigOrgRelationEntityList(configOrgRelationEntityList);
		}
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryDipConfigOrgInfoByConfType(java.lang.String)
	 */
	@Override
	public List<ConfigOrgRelationEntity> queryDipConfigOrgInfoByConfType(String confType) {
		return configOrgRelationDao.queryDipConfigOrgInfoByConfType(confType);
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService#queryConfigOrgInfoByConfTypeAndOrgCode(java.lang.String, java.lang.String)
	 */

	@Override
	public List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfTypeAndOrgCode(String confType, String orgCode) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("confType", confType);
		queryMap.put("orgCode", orgCode);
		return configOrgRelationDao.queryConfigOrgInfoByConfTypeAndOrgCode(queryMap);
	}
}
