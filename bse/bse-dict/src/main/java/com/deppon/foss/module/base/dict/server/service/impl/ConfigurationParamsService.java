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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/service/impl/ConfigurationParamsService.java
 * 
 * FILE NAME        	: ConfigurationParamsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressInsuredLimitService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.ConfigurationParamsException;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.module.base.dict.server.cache.ConfigurationParamsCacheDeal;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.opensymphony.xwork2.inject.Inject;

public class ConfigurationParamsService implements IConfigurationParamsService {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigurationParamsService.class);

	@Inject
	private IDataDictionaryValueService dataDictionaryValueService;
	@Inject
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	@Inject
	private ISyncExpressInsuredLimitService syncExpressInsuredLimitService;
	@Inject
	private ConfigurationParamsCacheDeal configurationParamsCacheDeal;


	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	public IDataDictionaryValueService getDataDictionaryValueService() {
		return dataDictionaryValueService;
	}

	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setConfigurationParamsCacheDeal(
			ConfigurationParamsCacheDeal configurationParamsCacheDeal) {
		this.configurationParamsCacheDeal = configurationParamsCacheDeal;
	}

	public void setSyncExpressInsuredLimitService(
			ISyncExpressInsuredLimitService syncExpressInsuredLimitService) {
		this.syncExpressInsuredLimitService = syncExpressInsuredLimitService;
	}

	/**
	 * 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:33:35
	 * @see com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService#addConfigurationParams(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	public ConfigurationParamsEntity addConfigurationParams(
			ConfigurationParamsEntity entity)
			throws ConfigurationParamsException {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		// 去重性判断
		ConfigurationParamsEntity entityCondition = new ConfigurationParamsEntity();
		entityCondition.setOrgCode(entity.getOrgCode());
		entityCondition.setCode(entity.getCode());
		List<ConfigurationParamsEntity> entitys = configurationParamsDao
				.queryConfigurationParamsExactByEntity(entityCondition,
						NumberConstants.NUMERAL_ZORE,
						NumberConstants.NUMERAL_ONE);
		if (!CollectionUtils.isEmpty(entitys)) {
			throw new ConfigurationParamsException("系统配置参数已存在相同的部门和相同的配置项代码",
					"系统配置参数已存在相同的部门和相同的配置项代码");
		}
		ConfigurationParamsEntity config=configurationParamsDao.addConfigurationParams(entity);
		if(config!=null){
			//如果新增的是“快递保价申明价值上限”或“快递外发保价申明价值上限”
			if("1104".equals(config.getCode())||"1105".equals(config.getCode())){
				//将本工程的entity转换成baseinfo-api下的entity
				com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity conf = new com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity();
				conf.setConfName(config.getConfName());
				conf.setConfValue(config.getConfValue());
				//同步新数据给下游系统（同步名称和值）
				syncExpressInsuredLimitService.synInfoToCRMCCGW(conf);
			}
		}
		return config;
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:33:35
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#deleteConfigurationParams(java.lang.String)
	 */
	@Override
	public ConfigurationParamsEntity deleteConfigurationParams(
			ConfigurationParamsEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		return configurationParamsDao.deleteConfigurationParams(entity);
	}

	/**
	 * 通过VIRTUAL_CODE标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:33:35
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#deleteConfigurationParamsMore(java.lang.String[])
	 */
	@Override
	public ConfigurationParamsEntity deleteConfigurationParamsMore(
			String[] codes, String deleteUser) {
		// 请求合法性判断：
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		ConfigurationParamsEntity config = configurationParamsDao.deleteConfigurationParamsMore(codes,deleteUser);
		flushCache(config);
		return config;
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:33:35
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#updateConfigurationParams(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	@Transactional
	public ConfigurationParamsEntity updateConfigurationParams(
			ConfigurationParamsEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		ConfigurationParamsEntity config = configurationParamsDao
				.updateConfigurationParams(entity);
		if (null == config) {
			return null;
		}
		flushCache(config);
		// 修改后的实体不为空，即修改成功

		// 如果修改的是“快递保价申明价值上限”或“快递外发保价申明价值上限”
		if ("1104".equals(config.getCode()) || "1105".equals(config.getCode())) {
			// 将本工程的entity转换成baseinfo-api下的entity
			com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity conf = new com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity();
			conf.setConfName(config.getConfName());
			conf.setConfValue(config.getConfValue());
			// 同步新数据给下游系统（同步名称和值）
			syncExpressInsuredLimitService.synInfoToCRMCCGW(conf);
		}

		// 返回结果
		return config;
	}

	
	/**
	 * 缓存刷新
	 * @param config
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void flushCache(ConfigurationParamsEntity config) {
		ICache cache = CacheManager.getInstance().getCache(FossTTLCache.CONFIGURATION_PARAMS_CACHE_UUID);
		String key = config.getConfType() + "#" + config.getCode() + "#" + config.getOrgCode();
		cache.invalid(key);
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 下午4:42:19
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsService#queryConfigurationParamsByCode(java.lang.String)
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByVirtualCode(
			String code) {
		// 非空判断
		if (null == code) {
			// 如果code为空则返回空
			return null;
		}
		// 执行查询
		ConfigurationParamsEntity entityResult = configurationParamsDao
				.queryConfigurationParamsByVirtualCode(code);
		// 返回查询结果
		return this.attachOrg(entityResult);
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsService#queryConfigurationParamsByCode(java.lang.String)
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsBatchByVirtualCode(
			String[] codes) {
		// 非空判断
		if (ArrayUtils.isEmpty(codes)) {
			// 如果参数为空则返回空
			return null;
		}
		// 执行查询
		List<ConfigurationParamsEntity> entityResults = configurationParamsDao
				.queryConfigurationParamsBatchByVirtualCode(codes);
		// 返回查询结果
		return this.attachOrg(entityResults);
	}
	
	/**
	 * 
	 *<p>精确查询 根据多个编码（code）批量查询</P>
	 * @author 130566-foss-ZengJunfan
	 * @date 2013-5-27下午6:03:26
	 *@param codes
	 *@return
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(
			String[] codes) {
		//若传进来的参数为空
		if(ArrayUtils.isEmpty(codes)){
			return null;
		}
		//执行查询
		List<ConfigurationParamsEntity> entities =configurationParamsDao.queryConfigurationParamsBatchByCode(codes);
		return entities;
	}
	
	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsService#queryConfigurationParamsExactByEntity(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity,
	 *      int, int)
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
			ConfigurationParamsEntity entity, int start, int limit) {
		// 保证entity对象不为空
		ConfigurationParamsEntity entityCondition = entity == null ? new ConfigurationParamsEntity()
				: entity;
		// 执行查询
		List<ConfigurationParamsEntity> entityResults = configurationParamsDao
				.queryConfigurationParamsExactByEntity(entityCondition, start,
						limit);
		// 返回结果
		return this.attachOrg(entityResults);

	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsService#queryConfigurationParamsExactByEntityCount(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	public long queryConfigurationParamsExactByEntityCount(
			ConfigurationParamsEntity entity) {
		// 保证entity对象不为空
		ConfigurationParamsEntity entityCondition = entity == null ? new ConfigurationParamsEntity()
				: entity;
		// 返回查询结果
		return configurationParamsDao
				.queryConfigurationParamsExactByEntityCount(entityCondition);
	}

	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 下午4:42:19
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsService#deleteConfigurationParamsMore(java.lang.String[])
	 */
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(
			ConfigurationParamsEntity entity, int start, int limit) {
		// 保证entity对象不为空
		ConfigurationParamsEntity entityCondition = entity == null ? new ConfigurationParamsEntity()
				: entity;
		// 执行查询
		List<ConfigurationParamsEntity> entityResults = configurationParamsDao
				.queryConfigurationParamsByEntity(entityCondition, start, limit);
		// 返回查询结果
		return this.attachOrg(entityResults);
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 下午4:42:19
	 * @see com.deppon.foss.module.dict.server.service.IConfigurationParamsService#queryConfigurationParamsCountByEntity(com.deppon.foss.module.base.dict.api.shared.domain.district.shared.domain.ConfigurationParamsEntity)
	 */
	@Override
	public long queryConfigurationParamsByEntityCount(
			ConfigurationParamsEntity entity) {
		// 保证entity对象不为空
		ConfigurationParamsEntity entityCondition = entity == null ? new ConfigurationParamsEntity()
				: entity;
		// 返回查询结果
		return configurationParamsDao
				.queryConfigurationParamsByEntityCount(entityCondition);
	}

	/**
	 * 下面是特殊方法
	 */

	/**
	 * 批量新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:33:35
	 */
	@Override
	public void addConfigurationParamsMore(
			List<ConfigurationParamsEntity> entitys) {
		for (ConfigurationParamsEntity entity : entitys) {
			this.addConfigurationParams(entity);
		}
	}

	/**
	 * 下面是特殊查询
	 */

	/**
	 * 精确查询
	 * 
	 * 通过 参数所属模块parmMouudle ，参数编码 parmCode，和组织机构orgCode ，查询组织参数。
	 * 
	 * parmMouudle（所属模块） 传入参数请参考 DictionaryConstants 中
	 * 常量：SYSTEM_CONFIG_PARM__BAS SYSTEM_CONFIG_PARM__PKP
	 * SYSTEM_CONFIG_PARM__TFR SYSTEM_CONFIG_PARM__STL parmCode（parmCode）
	 * ，传入参数详情参考 ConfigurationParamsConstants 中的定义，如果没有，自己补全 orgCode 组织机构
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-24 下午7:09:26
	 * @midify 088933-foss-zhangjiheng
	 * @midifyDate 2014-4-24 下午7:09:26
	 * @mark 方法性能低下且存在BUG，进行重构
	 * 
	 */
	public ConfigurationParamsEntity queryConfigurationParamsByOrgCode(
			String parmMouudle, String parmCode, String orgCodeParm) {
		String orgCode = orgCodeParm;
		// 校验输入的查询参数是否为空
		if (StringUtils.isBlank(parmMouudle) || StringUtils.isBlank(parmCode)
				|| StringUtils.isBlank(orgCode)) {
			return null;
		}
		// 直接查询配置参数信息
		ConfigurationParamsEntity findEntity = getConfigurationParamsByParm(
				parmMouudle, parmCode, orgCode);
		if (findEntity != null) {
			return findEntity;
		}

		// 如果该部门不存在配置参数信息，则根据词条编码和值编码获取数据字典信息
		DataDictionaryValueEntity data = dataDictionaryValueService
				.queryDataDictionaryValueByTermsCodeValueCode(parmMouudle,
						parmCode);
		//313353 sonar
		this.sonarSplitOne(data, parmMouudle, parmCode);
		
		String name = data.getValueName();
		// 如果部门已经为顶级部门编码，则不在向下执行
		if (FossConstants.ROOT_ORG_CODE.equalsIgnoreCase(orgCode)) {
			throw new ConfigurationParamsException("没有配置系统参数:" + name + "["
					+ parmCode + "]", "没有配置系统参数:" + name + "[" + parmCode + "]");
		}
		// 根据获取的数据字典判断该项配置参数能否允许递归使用上级组织配置参数
		// 如果数据字典的扩展字段（ExtAttribute1）为空或Y，则允许使用，否则不允许使用

		if (data.getExtAttribute1() == null
				|| StringUtils.equalsIgnoreCase(
						ConfigurationParamsConstants.NEED_SEARCH_PARENT_ORG,
						data.getExtAttribute1())) {
			// 获取本部门及所有的上级部门列表
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
			if(CollectionUtils.isEmpty(orgList)){
				return null;
			    }
			ConfigurationParamsEntity entity=null;
			for(int i = 0; i < orgList.size();i++){
				orgCode = getParentOrgCode(orgList, orgCode);
				 entity= getConfigurationParamsByParm(
						parmMouudle, parmCode, orgCode);
				if(entity!=null){
					break;
				}
			}
			if (entity == null) {
				throw new ConfigurationParamsException("没有配置系统参数:" + name + "["
						+ parmCode + "]", "没有配置系统参数:" + name + "[" + parmCode
						+ "]");
			}
			return entity;
		} else {
			
		    return null;
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(DataDictionaryValueEntity data, String parmMouudle, String parmCode) {
		if (null == data) {
			throw new DataDictionaryValueException("在数据字典中查不到对应的值，词代码为："
					+ parmMouudle + "，值代码为：" + parmCode, "在数据字典中查不到对应的值，词代码为："
					+ parmMouudle + "，值代码为：" + parmCode);
		}
	}
	
	/**
	 * （不走缓存查询）
	 * 通过 参数所属模块parmMouudle ，参数编码 parmCode，和组织机构orgCode ，查询组织参数。
	 * 
	 * parmMouudle（所属模块） 传入参数请参考 DictionaryConstants 中
	 * 常量：SYSTEM_CONFIG_PARM__BAS SYSTEM_CONFIG_PARM__PKP
	 * SYSTEM_CONFIG_PARM__TFR SYSTEM_CONFIG_PARM__STL parmCode（parmCode）
	 * ，传入参数详情参考 ConfigurationParamsConstants 中的定义，如果没有，自己补全 orgCode 组织机构
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 *  @author 130566-foss-zengJunFan
	 * @date 2014-4-24 下午3:57:43
	 * @see com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService#queryConfigurationParamsByOrgCodeNoCache(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsByOrgCodeNoCache(String parmMouudle,String parmCode,String orgCodeParm){
		String orgCode = orgCodeParm;
		// 校验输入的查询参数是否为空
		if (StringUtils.isBlank(parmMouudle) || StringUtils.isBlank(parmCode)
				|| StringUtils.isBlank(orgCode)) {
			return null;
		}
		// 直接查询配置参数信息
		ConfigurationParamsEntity findEntity = getConfigurationParamsByParm(
				parmMouudle, parmCode, orgCode);
		if (findEntity != null) {
			return findEntity;
		}

		// 如果该部门不存在配置参数信息，则根据词条编码和值编码获取数据字典信息
		DataDictionaryValueEntity data = dataDictionaryValueService
				.queryDataDictionaryValueByTermsCodeValueCodeNoCache(parmMouudle,
						parmCode);
		//313353 sonar
		this.sonarSplitTwo(data, parmMouudle, parmCode);
		
		String name = data.getValueName();
		// 如果部门已经为顶级部门编码，则不在向下执行
		if (FossConstants.ROOT_ORG_CODE.equalsIgnoreCase(orgCode)) {
			throw new ConfigurationParamsException("没有配置系统参数:" + name + "["
					+ parmCode + "]", "没有配置系统参数:" + name + "[" + parmCode + "]");
		}
		// 根据获取的数据字典判断该项配置参数能否允许递归使用上级组织配置参数
		// 如果数据字典的扩展字段（ExtAttribute1）为空或Y，则允许使用，否则不允许使用

		if (data.getExtAttribute1() == null
				|| StringUtils.equalsIgnoreCase(
						ConfigurationParamsConstants.NEED_SEARCH_PARENT_ORG,
						data.getExtAttribute1())) {
			// 获取本部门及所有的上级部门列表
			List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoEntityAllUpNOCache(orgCode);
			if(CollectionUtils.isEmpty(orgList)){
				return null;
			    }
			ConfigurationParamsEntity entity=null;
			for(int i = 0; i < orgList.size();i++){
				orgCode = getParentOrgCode(orgList, orgCode);
				 entity= getConfigurationParamsByParm(
						parmMouudle, parmCode, orgCode);
				if(entity!=null){
					break;
				}
			}
			if (entity == null) {
				throw new ConfigurationParamsException("没有配置系统参数:" + name + "["
						+ parmCode + "]", "没有配置系统参数:" + name + "[" + parmCode
						+ "]");
			}
			return entity;
		} else {
			
		    return null;
		}
	
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(DataDictionaryValueEntity data, String parmMouudle, String parmCode) {
		if (null == data) {
			throw new DataDictionaryValueException("在数据字典中查不到对应的值，词代码为："
					+ parmMouudle + "，值代码为：" + parmCode, "在数据字典中查不到对应的值，词代码为："
					+ parmMouudle + "，值代码为：" + parmCode);
		}
	}
	
	/**
	 * 根据配置类型ConfType，parmCode配置项编码，配置项组织，查询系统配置对象
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 上午9:50:28
	 * 
	 * @param parmMouudle
	 *            ,取自数据字典，对应ConfType
	 * @param parmCode
	 *            对应Code
	 * @param orgCode
	 *            组织编码（非标杆编码）
	 */
	private ConfigurationParamsEntity getConfigurationParamsByParm(
			String parmMouudle, String parmCode, String orgCode) {
		String keys = "";
		if (StringUtils.isNotBlank(parmMouudle)) {
			keys = parmMouudle;
		} else {
			keys = "key";
		}

		if (StringUtils.isNotBlank(parmCode)) {
			keys = keys + "#" + parmCode;
		} else {
			keys = keys + "#key";
		}

		if (StringUtils.isNotBlank(orgCode)) {
			keys = keys + "#" + orgCode;
		} else {
			keys = keys + "#key";
		}

		ConfigurationParamsEntity configurationParamsEntity = null;
		try {
			configurationParamsEntity = configurationParamsCacheDeal
					.getConfigurationParamsEntityByCache(keys);

		} catch (Exception e) {
			// 打印日志
			LOGGER.info("getConfigurationParamsByParm 查询系统配置对象出现异常");
		}
		if (configurationParamsEntity != null) {
			return configurationParamsEntity;
		}
		ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setConfType(parmMouudle);
		entity.setCode(parmCode);
		entity.setOrgCode(orgCode);
		List<ConfigurationParamsEntity> list = configurationParamsDao
				.queryConfigurationParamsExactByEntity(entity, 0, 1);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			ConfigurationParamsEntity entityResult = list
					.get(NumberConstants.ZERO);
			entityResult = this.attachOrg(entityResult);
			return entityResult;
		}
	}

	/**
	 * 精确查询 根据“系统配置编码”查询第一个系统参数配置，适用于无部门编码的系统配置
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 上午11:11:15
	 * @param code
	 *            系统配置的编码，对应表中的CODE
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationParamsOneByCode(
			String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		ConfigurationParamsEntity entityCondition = new ConfigurationParamsEntity();
		entityCondition.setCode(code);
		List<ConfigurationParamsEntity> entityResults = this
				.queryConfigurationParamsExactByEntity(entityCondition,
						NumberConstants.ZERO, Integer.MAX_VALUE);
		if (CollectionUtils.isNotEmpty(entityResults)) {
			// 如果返回的有多个，只取第一个
			ConfigurationParamsEntity entityResult = entityResults
					.get(NumberConstants.ZERO);
			entityResult = this.attachOrg(entityResult);
			return entityResult;
		}
		return null;

	}

	/**
	 * 精确查询
	 * 
	 * 根据“系统配置编码”查询第一个系统参数配置的“配置项值”，适用于无部门编码的系统配置
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 上午11:11:15
	 * @param code
	 *            系统配置的编码，对应表中的CODE
	 */
	@Override
	public String queryConfValueByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		ConfigurationParamsEntity entityCondition = this
				.queryConfigurationParamsOneByCode(code);
		if (entityCondition != null) {
			return entityCondition.getConfValue();
		}
		return null;

	}

	/**
	 * 将部门名称添加进去，
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午7:43:53
	 */
	@Override
	public ConfigurationParamsEntity attachOrg(ConfigurationParamsEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getCode())) {
			return entity;
		}

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(entity.getOrgCode());
		if (org != null) {
			entity.setOrgName(org.getName());
		}

		return entity;
	}

	/**
	 * 将部门名称添加进去，
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午7:43:53
	 */
	@Override
	public List<ConfigurationParamsEntity> attachOrg(
			List<ConfigurationParamsEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}

		for (ConfigurationParamsEntity entity : entitys) {
			this.attachOrg(entity);
		}

		return entitys;
	}

	/**
	 * 
	 * @Description: 刷新缓存
	 * @author FOSSDP-sz
	 * @date 2013-2-25 上午10:43:11
	 * @param parmMouudle
	 * @param parmCode
	 * @param orgCode
	 * @version V1.0
	 */
	@Override
	public void refreshConfigurationParamsCache(String parmMouudle,
			String parmCode, String orgCode) {
		String keys = "";
		if (StringUtils.isNotBlank(parmMouudle)) {
			keys = parmMouudle;
		} else {
			keys = "key";
		}

		if (StringUtils.isNotBlank(parmCode)) {
			keys = keys + "#" + parmCode;
		} else {
			keys = keys + "#key";
		}

		if (StringUtils.isNotBlank(orgCode)) {
			keys = keys + "#" + parmCode;
		} else {
			keys = keys + "#key";
		}

		try {
			configurationParamsCacheDeal.getConfigurationParamsCache().invalid(
					keys);
		} catch (Exception e) {
			// 打印日志
			LOGGER.info("refreshConfigurationParamsCache 刷新缓存失败");
		}
	}

	
	 /**
     * <p>根据传入的部门编码查询上级部门的部门编码</p>.
     *
     * @param list 
     * @param orgCode 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午8:11:55
     * @see
     */
    private String getParentOrgCode(List<OrgAdministrativeInfoEntity> list,String orgCode){
	
	Map<String, OrgAdministrativeInfoEntity> codeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	Map<String, OrgAdministrativeInfoEntity> unicodeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	for(OrgAdministrativeInfoEntity entity : list){
	    //组织编码作为key把集合中的组织实体放在map中
	    codeMap.put(entity.getCode(), entity);
	    //组织的标杆编码作为key把集合中的组织实体放在map中
	    unicodeMap.put(entity.getUnifiedCode(), entity);
	}
	LOGGER.debug("orgCode: "+ orgCode);
	//通过部门编码查询该部门的实体
	OrgAdministrativeInfoEntity orgEntity = codeMap.get(orgCode);
	
	if(orgEntity != null){
	    //如果上级标杆编码为空
	    if(StringUtil.isNotBlank(orgEntity.getParentOrgUnifiedCode())){
		
		OrgAdministrativeInfoEntity parentOrg = unicodeMap.get(orgEntity.getParentOrgUnifiedCode());
		if(null != parentOrg){
		  //通过部门的上级部门标杆编码查询上级部门编码
		    return parentOrg.getCode();
		}else {
		    return orgCode;
		}
	    }
	    return orgCode;
	}
	//没有上级，返回本部门的部门编码
	return orgCode;
    }
	/**
	 * 下面是dao对象的声明及get,set方法：
	 */
	@Inject
	private IConfigurationParamsDao configurationParamsDao;

	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setConfigurationParamsDao(
			IConfigurationParamsDao configurationParamsDao) {
		this.configurationParamsDao = configurationParamsDao;
	}

	/**
     * 递归查询当前机构及其以上级别(父级)的 参数配置 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param params
	  * @return
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsByOrgCode(ConfigurationParamsEntity params) {
		return this.configurationParamsDao.queryConfigurationParamsByOrgCode(params);
	}
	/**
	 * 提供给接送货根据code和时间来查询
	 */
	@Override
	public ConfigurationParamsEntity queryConfigurationByCodeAndDate(String code,Date date) {
		return this.configurationParamsDao.queryConfigurationByCodeAndDate(code,date);
	}
	
	/**
	 * @desc 查询配置参数
	 * @param confType
	 * @param code
	 * @param orgCode
	 * @return
	 * @date 2016年7月18日 下午5:56:42
	 */
	@Override
	public String querySysConfig(String confType, String code, String orgCode) {

		LOGGER.info("查询配置参数confType=" + confType + ", code=" + code + ", orgCode=" + orgCode);

		if (StringUtils.isEmpty(confType) || StringUtils.isEmpty(code) || StringUtils.isEmpty(orgCode)) {
			return null;
		}

		StringBuilder sb = new StringBuilder(confType);
		sb.append("#");
		sb.append(code);
		sb.append("#");
		sb.append(orgCode);
		String keys = sb.toString();

		ConfigurationParamsEntity configurationParamsEntity = null;
		try {
			configurationParamsEntity = configurationParamsCacheDeal.getConfigurationParamsEntityByCache(keys);
		} catch (Exception e) {
			LOGGER.info("getConfigurationParamsByParm 查询系统配置对象出现异常");
		}
		if (configurationParamsEntity != null) {
			String confValue = configurationParamsEntity.getConfValue();
			LOGGER.info("配置参数keys=" + keys + "，从缓存取值为" + confValue);
			return confValue;
		}

		LOGGER.info("查询配置参数keys=" + keys + "缓存查询不到数据，走DB查询");

		ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
		entity.setConfType(confType);
		entity.setCode(code);
		entity.setOrgCode(FossConstants.ROOT_ORG_CODE);
		entity.setActive(FossConstants.YES);
		String value = configurationParamsDao.querySysConfig(entity);
		LOGGER.info("配置参数keys=" + keys + "，从DB取值为" + value);
		return value;
	}
	
	private static final String BSE_FOSS_GRAY_RELEASED = "BSE_FOSS_GRAY_RELEASED";
	private static final String TFR_FOSS_GRAY_RELEASED = "TFR_FOSS_GRAY_RELEASED";
	private static final String STL_FOSS_GRAY_RELEASED = "STL_FOSS_GRAY_RELEASED";
	private static final String PKP_FOSS_GRAY_RELEASED = "PKP_FOSS_GRAY_RELEASED";
	private static final String SYNC_EXPRESS_DELIVERY_ADDRESS = "SYNC_EXPRESS_DELIVERY_ADDRESS";
	private static final String SYNC_SEARCH_VEHICLE_SOLR = "SEARCH_VEHICLE_SOLR";
	
	@Override
	public boolean queryBseSwitch4Ecs() {
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS, BSE_FOSS_GRAY_RELEASED,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
	
	@Override
	public boolean queryTfrSwitch4Ecs() {
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, TFR_FOSS_GRAY_RELEASED,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
	
	@Override
	public boolean queryStlSwitch4Ecs() {
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__STL, STL_FOSS_GRAY_RELEASED,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
	
	@Override
	public boolean queryPkpSwitch4Ecs() {
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP, PKP_FOSS_GRAY_RELEASED,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}

	/**
	 * 快递派送地址库ESB开关
	 * @author 313353
	 * @date 2016年8月15日 下午14:56:42
	 */
	public boolean queryExpressDeliveryAddress(){
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS, SYNC_EXPRESS_DELIVERY_ADDRESS,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
	
	/**
	 * 使用solr文本搜索车牌号
	 * @Title: queryVehicleBSolr 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	@Override
	public boolean queryVehicleBSolr() {
		String value = querySysConfig(DictionaryConstants.SYSTEM_CONFIG_PARM__BAS, SYNC_SEARCH_VEHICLE_SOLR,
				FossConstants.ROOT_ORG_CODE);
		return FossConstants.YES.equals(value) ? true : false;
	}
}
