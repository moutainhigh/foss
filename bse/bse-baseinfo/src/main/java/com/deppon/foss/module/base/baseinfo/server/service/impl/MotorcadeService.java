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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/MotorcadeService.java
 * 
 * FILE NAME        	: MotorcadeService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 车队 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午2:33:12
 */
public class MotorcadeService implements IMotorcadeService {

	private static final Logger log = Logger.getLogger(MotorcadeService.class);

	private ISyncMotorcadeService syncMotorcadeService;

	public void setSyncMotorcadeService(
			ISyncMotorcadeService syncMotorcadeService) {
		this.syncMotorcadeService = syncMotorcadeService;
	}

	/**
	 * 车队 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService#addMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
	 */
	@Override
	public synchronized MotorcadeEntity addMotorcade(MotorcadeEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}

		// 新增时，把部门名字设置进来
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(entity.getCode());
		if (org != null) {
			entity.setName(org.getName());
		}

		// 新增时，把所服务的集中开单组的部门名称设置进来
		String serveBillTermName = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoNameByCode(entity.getServiceTeam());
		if (StringUtil.isEmpty(serveBillTermName)) {
			entity.setServeBillTermName(serveBillTermName);
		}

		MotorcadeEntity entityResult = motorcadeDao.addMotorcade(entity);
		if (entityResult == null) {
			String msg = "新增 车队信息失败";
			log.error(msg);
		} else {
			List<MotorcadeEntity> motorcadeList = new ArrayList<MotorcadeEntity>();
			motorcadeList.add(entityResult);
			// 同步车队信息给CRM
			syncMotorcadeService.syncMotorcadeToCrm(motorcadeList);

		}

		return entityResult;
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#deleteMotorcade(java.lang.String)
	 */
	@Override
	public MotorcadeEntity deleteMotorcade(MotorcadeEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		MotorcadeEntity result = motorcadeDao.deleteMotorcade(entity);
		if (result != null) {
			invalidEntity(entity.getCode());
		}
		return result;
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao#updateMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
	 */
	@Override
	@Transactional
	public synchronized MotorcadeEntity updateMotorcade(MotorcadeEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}

		MotorcadeEntity result = motorcadeDao.updateMotorcade(entity);
		if (result != null) {
			invalidEntity(entity.getCode());
			List<MotorcadeEntity> motorcadeList = new ArrayList<MotorcadeEntity>();
			motorcadeList.add(result);
			// 同步车队信息给CRM
			syncMotorcadeService.syncMotorcadeToCrm(motorcadeList);

		}
		return result;
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryMotorcadeByCode(java.lang.String)
	 */
	@Override
	public MotorcadeEntity queryMotorcadeByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		MotorcadeEntity entity = queryMotorcadeByCodeClean(code);
		if (entity != null) {
			this.attachOrgName(entity);
		}
		return entity;
	}

	@Override
	public MotorcadeEntity queryMotorcadeByCodeClean(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		MotorcadeEntity entity = queryEntityCache(code);
		if (entity == null) {
			entity = motorcadeDao.queryMotorcadeByCode(code);
		}
		return entity;
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryMotorcadeByCode(java.lang.String)
	 */
	@Override
	public List<MotorcadeEntity> queryMotorcadeBatchByCode(String[] codes) {
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}

		List<MotorcadeEntity> entityResults = motorcadeDao
				.queryMotorcadeBatchByCode(codes);

		this.attachOrgName(entityResults);

		return entityResults;

	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryMotorcadeExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity,
	 *      int, int)
	 */
	@Override
	public List<MotorcadeEntity> queryMotorcadeExactByEntity(
			MotorcadeEntity entity, int start, int limit) {
		List<MotorcadeEntity> entityResults = querySimpleMotorcadeExactByEntity(
				entity, start, limit);
		this.attachOrgName(entityResults);
		return entityResults;
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryMotorcadeExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity,
	 *      int, int)
	 */
	@Override
	public List<MotorcadeEntity> querySimpleMotorcadeExactByEntity(
			MotorcadeEntity entity, int start, int limit) {
		return motorcadeDao.queryMotorcadeExactByEntity(entity, start, limit);
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryMotorcadeExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeExactByEntityCount(MotorcadeEntity entity) {
		return motorcadeDao.queryMotorcadeExactByEntityCount(entity);
	}

	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#deleteMotorcadeMore(java.lang.String[])
	 */
	@Override
	public List<MotorcadeEntity> queryMotorcadeByEntity(MotorcadeEntity entity,
			int start, int limit) {
		List<MotorcadeEntity> entityResults = motorcadeDao
				.queryMotorcadeByEntity(entity, start, limit);

		this.attachOrgName(entityResults);

		return entityResults;
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午2:33:12
	 * @see com.deppon.foss.module.baseinfo.server.service.IMotorcadeService#queryMotorcadeCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.MotorcadeEntity)
	 */
	@Override
	public long queryMotorcadeByEntityCount(MotorcadeEntity entity) {
		return motorcadeDao.queryMotorcadeByEntityCount(entity);
	}

	/**
	 * 下面是工具方法
	 */

	/**
	 * 给部门加上“财务组织的子公司名称”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	public MotorcadeEntity attachOrgName(MotorcadeEntity entity) {
		if (entity == null) {
			return entity;
		}

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(entity.getCode());

		// 添加“所服务的集中开单组”的名称 update by zhangxiaohui
		// 更新查询所服务所集中开单组的名称逻辑 原因：判断逻辑错误，应该判断code不为空Name为空才执行查询，所服务的外场部分的代码也有相同问题
		// 修改了Line 245 Line253
		// @author foss-zhangxiaohui @date Feb 1, 2013 3:21:13 PM
		if (StringUtils.isNotEmpty(entity.getServeBillTerm())
				&& StringUtils.isEmpty(entity.getServeBillTermName())) {
			OrgAdministrativeInfoEntity entityResult = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(entity.getServeBillTerm());
			if (entityResult != null) {
				entity.setServeBillTermName(entityResult.getName());
			}
		}
		// 添加“所服务外场”的名称
		if (StringUtils.isNotEmpty(entity.getTransferCenter())
				&& StringUtils.isEmpty(entity.getTransferCenterName())) {
			OrgAdministrativeInfoEntity entityResult = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(entity
							.getTransferCenter());
			if (entityResult != null) {
				entity.setTransferCenterName(entityResult.getName());
			}
		}
		// 添加“所属车队”的名称
		if (!StringUtils.equals(org.getTransDepartment(), FossConstants.YES)
				&& StringUtils.isEmpty(entity.getParentOrgCodeName())) {
			// 如果不是车队，且上级车队的名称为空
			OrgAdministrativeInfoEntity entityResult = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(entity.getParentOrgCode());
			if (entityResult != null) {
				entity.setParentOrgCodeName(entityResult.getName());
			}
		}
		return entity;
	}

	/**
	 * 给部门加上“名称”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	public List<MotorcadeEntity> attachOrgName(List<MotorcadeEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}

		for (MotorcadeEntity entity : entitys) {
			this.attachOrgName(entity);
		}

		return entitys;
	}

	/**
	 * 
	 * <p>
	 * 查找所有顶级车队
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Apr 11, 2013 3:20:05 PM
	 * @return
	 * @see
	 */
	@Override
	public List<MotorcadeEntity> queryTopFleetList(String key) {

		MotorcadeEntity c = new MotorcadeEntity();
		c.setActive(FossConstants.ACTIVE);
		c.setIsTopFleet(FossConstants.YES);
		if (StringUtils.isNotBlank(key)) {
			c.setKey(key);
		}
		List<MotorcadeEntity> list = querySimpleMotorcadeExactByEntity(c, 0,
				Integer.MAX_VALUE);
		List<MotorcadeEntity> result = new ArrayList<MotorcadeEntity>();
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		// 过滤掉basOrg里没有车队属性的
		for (MotorcadeEntity entity : list) {
			if (entity == null) {
				continue;
			}
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeClean(entity.getCode());
			if (org != null && org.checkTransDepartment()) {
				result.add(entity);
			}
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * 根据外场编号查询顶级车队信息
	 * </p>
	 * @author foss-wusuhua
	 * @date 2015 6-17
	 * @return
	 * @see
	 */
	public MapDto queryTopFleetByTransferCenterCode(String code){
		//MotorcadeEntity entity = new MotorcadeEntity();
		if(StringUtils.isEmpty(code)){
			return null;
		}
		MotorcadeEntity entity=motorcadeDao.queryTopFleetByTransferCenterCode(code);
		if(entity==null){
			return null;
		}
		MapDto map=new MapDto();
		map.setName(entity.getName());
		map.setCode(entity.getCode());
		return map;
	} 
	
	/**
	 * 下面是dao对象的声明及get,set方法：
	 */
	private IMotorcadeDao motorcadeDao;

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setMotorcadeDao(IMotorcadeDao motorcadeDao) {
		this.motorcadeDao = motorcadeDao;
	}

	@SuppressWarnings("unchecked")
	private void invalidEntity(String key) {
		((ICache<String, MotorcadeEntity>) CacheManager.getInstance().getCache(
				FossTTLCache.MOTORCADE_ENTITY_CACHE_UUID)).invalid(key);
	}

	// 取缓存中的数据
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MotorcadeEntity queryEntityCache(String key) {
		MotorcadeEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.MOTORCADE_ENTITY_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (MotorcadeEntity) cache.get(key);
		} catch (Exception t) {
			log.error("cache找不到", t);
		}
		return result;
	}

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 332219-foss
	 * @date 2017-03-25 下午2:33:12
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeService#queryTransMotorcadeByCode(java.lang.String)
	 */
	@Override
	public MotorcadeEntity queryTransMotorcadeByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		MotorcadeEntity entity = motorcadeDao.queryMotorcadeByCode(code);
		return entity;
	}

}
