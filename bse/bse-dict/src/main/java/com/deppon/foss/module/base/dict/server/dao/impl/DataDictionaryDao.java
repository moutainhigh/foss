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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/dao/impl/DataDictionaryDao.java
 * 
 * FILE NAME        	: DataDictionaryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class DataDictionaryDao extends SqlSessionDaoSupport implements
		IDataDictionaryDao {

	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_DICT_PREFIX
			+ ".dataDictionary.";

	/**
	 * 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#addDataDictionary(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity)
	 */
	@Override
	public DataDictionaryEntity addDataDictionary(DataDictionaryEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
			return null;
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		entity.setCreateDate(now);
		entity.setModifyDate(now);
		entity.setModifyUser(entity.getCreateUser());
		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addDataDictionary",
				entity);
		return result > 0 ? entity : null;
	}

	/**
	 * 通过 编码 标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionary(java.lang.String)
	 */
	@Override
	public DataDictionaryEntity deleteDataDictionary(DataDictionaryEntity entity) {
		// 请求参数合法性验证
		if (null == entity) {
			return null;
		}
		if (StringUtils.isBlank(entity.getTermsCode())) {
			return null;
		}

		// 处理删除时要更新的数据
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE + "deleteDataDictionary",
				map);
		return result > 0 ? entity : null;
	}

	/**
	 * 通过 编码 标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionaryMore(java.lang.String[])
	 */
	@Override
	public DataDictionaryEntity deleteDataDictionaryMore(
			DataDictionaryEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
			return null;
		}
		if (StringUtils.isBlank(entity.getTermsCode())) {
			return null;
		}

		// 组装参数：
		String code = entity.getTermsCode();
		String[] codes = code.split(",");

		// 处理删除时要更新的数据
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);

		int result = getSqlSession().update(
				NAMESPACE + "deleteDataDictionaryMore", map);
		return result > 0 ? entity : null;
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#updateDataDictionary(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity)
	 */
	@Override
	public DataDictionaryEntity updateDataDictionary(DataDictionaryEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
			return entity;
		}
		if (StringUtils.isBlank(entity.getTermsCode())) {
			return entity;
		}

		// 更新要先删除旧的数据：
		Date now = new Date();
		entity.setActive(FossConstants.INACTIVE);
		entity.setModifyDate(now);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE + "deleteDataDictionary",
				map);
		if (result <= 0) {
			return null;
		}

		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(entity.getModifyDate());
		entity.setCreateUser(entity.getModifyUser());
		entity.setActive(FossConstants.ACTIVE);
		result = getSqlSession()
				.insert(NAMESPACE + "addDataDictionary", entity);
		return result > 0 ? entity : null;
	}

	/**
	 * 以下全为查询：
	 */

	/**
	 * 通过 编码查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#queryDataDictionaryByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DataDictionaryEntity queryDataDictionaryByTermsCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		DataDictionaryEntity entity = new DataDictionaryEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setTermsCode(code);

		List<DataDictionaryEntity> entitys = this.getSqlSession().selectList(
				NAMESPACE + "queryDataDictionaryByTermsCode", entity);
		if (entitys == null || entitys.isEmpty()) {
			return null;
		} else {
			return entitys.get(0);
		}
	}

	/**
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionaryMore(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionaryEntity> queryDataDictionaryByEntity(
			DataDictionaryEntity entity, int start, int limit) {
		DataDictionaryEntity queryEntity;
		if (null == entity) {
			queryEntity = new DataDictionaryEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryDataDictionaryByEntity", queryEntity,
				rowBounds);
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.district.server.dao.IDataDictionaryDao#queryDynimicCondition(com.deppon.foss.module.base.dict.api.shared.domain.district.shared.domain.DataDictionaryEntity)
	 */
	@Override
	public long queryDataDictionaryByEntityCount(DataDictionaryEntity entity) {
		DataDictionaryEntity queryEntity;
		if (null == entity) {
			queryEntity = new DataDictionaryEntity();
		} else {
			queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryDataDictionaryByEntityCount", queryEntity);
	}

	/**
	 * 查询columnName列的columnValue值有多少个，可用于去重
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#queryCount(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public long queryDataDictionaryCount(String columnName, String columnValue) {
		if (null == columnName) {
			return 0;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("columnName", columnName);
		map.put("columnValue", columnValue);
		map.put("active", FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryDataDictionaryCount", map);
	}

	/**
	 * 根据entity精确查询,用于数据下载 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-7 下午9:7:1
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionaryEntity> queryDataDictionaryForDownload(
			DataDictionaryEntity entity) {
		DataDictionaryEntity queryEntity;
		if (null == entity) {
			queryEntity = new DataDictionaryEntity();
		} else {
			queryEntity = entity;
		}
		return (List<DataDictionaryEntity>) getSqlSession().selectList(
				NAMESPACE + "queryDataDictionaryForDownload", queryEntity);
	}

	/**
	 * 下面是特殊查询
	 */

	/**
	 * 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值： 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionaryMore(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionaryEntity> queryDataDictionaryByCond(
			String termsCode, String termsName, String valueCode,
			String valueName, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termsCode", termsCode);
		map.put("termsName", termsName);
		map.put("valueCode", valueCode);
		map.put("valueName", valueName);
		map.put("conditionActive", FossConstants.ACTIVE);

		RowBounds rowBounds = new RowBounds(start, limit);
		return (List<DataDictionaryEntity>) this.getSqlSession().selectList(
				NAMESPACE + "queryDataDictionaryByCond", map, rowBounds);
	}

	/**
	 * 查询总条数 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值： 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 上午11:27:53
	 * @see com.deppon.foss.module.district.server.dao.IDataDictionaryDao#queryDynimicCondition(com.deppon.foss.module.base.dict.api.shared.domain.district.shared.domain.DataDictionaryEntity)
	 */
	@Override
	public long queryDataDictionaryByCondCount(String termsCode,
			String termsName, String valueCode, String valueName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termsCode", termsCode);
		map.put("termsName", termsName);
		map.put("valueCode", valueCode);
		map.put("valueName", valueName);
		map.put("conditionActive", FossConstants.ACTIVE);

		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "queryDataDictionaryByCondCount", map);
	}
}
