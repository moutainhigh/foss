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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/FinancialOrganizationsService.java
 * 
 * FILE NAME        	: FinancialOrganizationsService.java
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
/**
 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 * 1.5.3	界面描述-主界面
 1.	功能按钮区域
 1)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
 2.	树形结构区域
 1)	点击树形结构中的一个财务组织，在树型结构的右边显示这个财务组织的详情。参见【图二：财务组织查看界面】。
 2)	点击树形结构的“+”，在组织树中展开该财务组织下一级的所有财务组织。点击树形结构的“-”，则收起该财务组织下一级的所有财务组织。
 3.	字段输入区域
 1)	参见数据元素【财务组织查询条件】。

 1.5.5	界面描述-查看界面
 1.	字段显示区域
 1)	参见数据元素【财务组织信息】。

 1	进入财务组织主界面		
 2	输入查询条件，点击查询按钮。	【财务组织查询条件】	系统返回查询结果，在财务组织树型结构中被查询的财务组织突出显示。
 3	点击树型结构中的目标财务组织。		在财务组织树的右边展示财务组织详情。

 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.google.inject.Inject;

/**
 * 
 * 财务组织
 * 
 * @date Mar 13, 2013 9:59:15 AM
 * @version 1.0
 */
public class FinancialOrganizationsService implements
		IFinancialOrganizationsService {

	/**
	 * 日志类
	 */
	private static final Logger log = Logger
			.getLogger(FinancialOrganizationsService.class);

	/**
	 * 财务组织 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService#addFinancialOrganizations(com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity)
	 */
	@Override
	public FinancialOrganizationsEntity addFinancialOrganizations(
			FinancialOrganizationsEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}

		return financialOrganizationsDao.addFinancialOrganizations(entity);
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao#deleteFinancialOrganizations(java.lang.String)
	 */
	@Override
	public FinancialOrganizationsEntity deleteFinancialOrganizations(
			FinancialOrganizationsEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// 清空缓存
		invalidOrgCode(entity.getCode());
		return financialOrganizationsDao.deleteFinancialOrganizations(entity);
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsDao#updateFinancialOrganizations(com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity)
	 */
	@Override
	@Transactional
	public FinancialOrganizationsEntity updateFinancialOrganizations(
			FinancialOrganizationsEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		// 清空缓存
		invalidOrgCode(entity.getCode());
		return financialOrganizationsDao.updateFinancialOrganizations(entity);
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsService#queryFinancialOrganizationsByCode(java.lang.String)
	 */
	@Override
	public FinancialOrganizationsEntity queryFinancialOrganizationsByCode(
			String code) {
		// 检查参数
		if (StringUtils.isBlank(code)) {
			return null;
		}
		// 找缓存
		FinancialOrganizationsEntity entity = null;
		if (SqlUtil.loadCache) {// 客户端不读缓存
			entity = queryOrgCodeCache(code);
		} else {
			entity = financialOrganizationsDao
					.queryFinancialOrganizationsByCode(code);
		}
		return entity;
	}

	/**
	 * 
	 * 通过CODE查询，不走缓存，供综合内部维护基础数据使用
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-5-29 下午5:09:46
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService#queryFinancialOrganizationsByCode(java.lang.String)
	 */
	@Override
	public FinancialOrganizationsEntity queryFinancialOrganizationsByCodeNoCache(
			String code) {
		// 检查参数
		if (StringUtils.isBlank(code)) {
			return null;
		}
		// 找缓存
		FinancialOrganizationsEntity entity = null;
		entity = financialOrganizationsDao
				.queryFinancialOrganizationsByCode(code);
		return entity;
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsService#queryFinancialOrganizationsByCode(java.lang.String)
	 */
	@Override
	public List<FinancialOrganizationsEntity> queryFinancialOrganizationsBatchByCode(
			String[] codes) {
		// 检查参数
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		return financialOrganizationsDao
				.queryFinancialOrganizationsBatchByCode(codes);
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsService#queryFinancialOrganizationsExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity,
	 *      int, int)
	 */
	@Override
	public List<FinancialOrganizationsEntity> queryFinancialOrganizationsExactByEntity(
			FinancialOrganizationsEntity entity, int start, int limit) {
		List<FinancialOrganizationsEntity> financialOrganizationsEntityList = financialOrganizationsDao
				.queryFinancialOrganizationsExactByEntity(entity, start, limit);
		if (CollectionUtils.isNotEmpty(financialOrganizationsEntityList)) {// 去查询其父组织的名称，因为父组织都一样，所以查一次就够了
			FinancialOrganizationsEntity financialOrganizationsEntity = this
					.queryFinancialOrganizationsByCode(financialOrganizationsEntityList
							.get(0).getParentOrgCode());
			for (FinancialOrganizationsEntity financialOrganizations : financialOrganizationsEntityList) {
				financialOrganizations
						.setParentOrgName(financialOrganizationsEntity
								.getName());
			}
		}
		return financialOrganizationsEntityList;
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsService#queryFinancialOrganizationsExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity)
	 */
	@Override
	public long queryFinancialOrganizationsExactByEntityCount(
			FinancialOrganizationsEntity entity) {
		return financialOrganizationsDao
				.queryFinancialOrganizationsExactByEntityCount(entity);
	}

	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFinancialOrganizationsService#deleteFinancialOrganizationsMore(java.lang.String[])
	 */
	@Override
	public List<FinancialOrganizationsEntity> queryFinancialOrganizationsByEntity(
			FinancialOrganizationsEntity entity, int start, int limit) {
		return financialOrganizationsDao.queryFinancialOrganizationsByEntity(
				entity, start, limit);
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:32:30
	 * @see com.deppon.foss.module.baseinfo.server.service.IFinancialOrganizationsService#queryFinancialOrganizationsCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.FinancialOrganizationsEntity)
	 */
	@Override
	public long queryFinancialOrganizationsByEntityCount(
			FinancialOrganizationsEntity entity) {
		return financialOrganizationsDao
				.queryFinancialOrganizationsByEntityCount(entity);
	}

	/**
	 * 以下为特殊查询
	 */

	/**
	 * 模糊查询 根据name查询财务组织及财务组织的所有上级，上下级通过CODE,PARENT_ORG_CODE来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 下午5:19:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService#queryFinancialOrganizationsUpByName(java.lang.String)
	 */
	@Override
	public List<FinancialOrganizationsEntity> queryFinancialOrganizationsUpByName(
			String name) {
		// 检查参数
		if (StringUtils.isBlank(name)) {
			return null;
		}

		return financialOrganizationsDao
				.queryFinancialOrganizationsUpByName(name);
	}

	/**
	 * 下面是dao对象的声明及get,set方法：
	 * 
	 * "@Inject"是给接送货组使用，用于重用综合组代码
	 */
	@Inject
	private IFinancialOrganizationsDao financialOrganizationsDao;

	/**
	 * 
	 * @date Mar 13, 2013 10:18:30 AM
	 * @param financialOrganizationsDao
	 * @see
	 */
	public void setFinancialOrganizationsDao(
			IFinancialOrganizationsDao financialOrganizationsDao) {
		this.financialOrganizationsDao = financialOrganizationsDao;
	}

	/**
	 * 清空缓存
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 13, 2013 10:18:35 AM
	 * @param key
	 * @see
	 */
	@SuppressWarnings("unchecked")
	private void invalidOrgCode(String key) {
		((ICache<String, FinancialOrganizationsEntity>) CacheManager
				.getInstance()
				.getCache(FossTTLCache.FINANCE_ORGCODE_CACHE_UUID))
				.invalid(key);
	}

	/**
	 * 
	 * <p>
	 * 取缓存中的数据
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Mar 13, 2013 10:19:00 AM
	 * @param key
	 * @return
	 * @see
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private FinancialOrganizationsEntity queryOrgCodeCache(String key) {
		FinancialOrganizationsEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.FINANCE_ORGCODE_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (FinancialOrganizationsEntity) cache.get(key);
		} catch (Exception t) {
			log.error("cache找不到", t);
		}
		return result;
	}

}
