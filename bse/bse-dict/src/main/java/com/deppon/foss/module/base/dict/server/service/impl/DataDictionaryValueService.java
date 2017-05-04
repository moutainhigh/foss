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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/service/impl/DataDictionaryValueService.java
 * 
 * FILE NAME        	: DataDictionaryValueService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.cache.RefreshableCache;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.module.base.dict.api.util.DataDictUtil;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

public class DataDictionaryValueService implements IDataDictionaryValueService {

	/**
	 * dao对象的声明
	 */
	@Inject
	private IDataDictionaryValueDao dataDictionaryValueDao;

	/**
	 * 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:26:0
	 * @see com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService#addDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
	 */
	@Override
	public DataDictionaryValueEntity addDataDictionaryValue(
			DataDictionaryValueEntity entity)
			throws DataDictionaryValueException {
		// 检查参数的合法性
		if (null == entity) {
			throw new DataDictionaryValueException("数据字典-值对象为空");
		}
		if (StringUtils.isEmpty(entity.getValueCode())) {
			throw new DataDictionaryValueException("数据字典-值代码为空");
		}
		DataDictionaryValueEntity entityCondition = new DataDictionaryValueEntity();
		entityCondition.setTermsCode(entity.getTermsCode());

		entityCondition.setValueCode(entity.getValueCode());
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueExactByEntity(entityCondition,
						NumberConstants.NUMERAL_ZORE, BigDecimal.ONE.intValue());
		if (!CollectionUtils.isEmpty(entitys)) {
			throw new DataDictionaryValueException("数据字典-值代码已存在");
		}

		entityCondition.setValueCode(null);
		entityCondition.setValueName(entity.getValueName());
		entitys = dataDictionaryValueDao.queryDataDictionaryValueExactByEntity(
				entityCondition, NumberConstants.NUMERAL_ZORE,
				BigDecimal.ONE.intValue());
		if (!CollectionUtils.isEmpty(entitys)) {
			throw new DataDictionaryValueException("数据字典-值名称已存在");
		}

		entityCondition.setValueName(null);
		entityCondition.setValueSeq(entity.getValueSeq());
		entitys = dataDictionaryValueDao.queryDataDictionaryValueExactByEntity(
				entityCondition, NumberConstants.NUMERAL_ZORE,
				BigDecimal.ONE.intValue());
		if (!CollectionUtils.isEmpty(entitys)) {
			throw new DataDictionaryValueException("数据字典-值序号已存在");
		}

		DataDictionaryValueEntity result = dataDictionaryValueDao
				.addDataDictionaryValue(entity);

		 //刷新数据字典缓存
		 this.flushDataDictionaryEntityCache(entity.getTermsCode());

		return result;
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:26:0
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValue(java.lang.String)
	 */
	@Override
	public DataDictionaryValueEntity deleteDataDictionaryValue(
			DataDictionaryValueEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			return null;
		}
		// 为了删除缓存需要先根据虚拟编码查询出实体信息
		DataDictionaryValueEntity dataDictEntity = dataDictionaryValueDao
				.queryDataDictionaryValueByVirtualCode(entity.getVirtualCode());

		DataDictionaryValueEntity result = dataDictionaryValueDao
				.deleteDataDictionaryValue(entity);

		// 刷新数据字典缓存
		this.flushDataDictionaryEntityCache(dataDictEntity.getTermsCode());
		// if (dataDictEntity != null) {
		// List<String> list = new ArrayList<String>();
		// list.add(dataDictEntity.getTermsCode());
		// list.add(dataDictEntity.getValueCode());
		// String code = StringUtils.join(list, SymbolConstants.EN_COMMA);
		// this.invalidateEntity(code);
		// }

		return result;
	}

	/**
	 * 通过code标识来批量删除(张继恒调查后发现该方法可以废弃，没有调用的地方，且逻辑有问题)
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:26:0
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValueMore(java.lang.String[])
	 */
	@Override
	public DataDictionaryValueEntity deleteDataDictionaryValueMore(
			DataDictionaryValueEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			return null;
		}
		DataDictionaryValueEntity result = dataDictionaryValueDao
				.deleteDataDictionaryValueMore(entity);

		// 刷新数据字典缓存
		this.flushDataDictionaryEntityCache(entity.getTermsCode());

		return result;
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:26:0
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#updateDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
	 */
	@Override
	@Transactional
	public DataDictionaryValueEntity updateDataDictionaryValue(
			DataDictionaryValueEntity entity)
			throws DataDictionaryValueException {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			return null;
		}
		// 请求合法性验证
		// 处理去重的问题
		DataDictionaryValueEntity entityCondition = new DataDictionaryValueEntity();
		entityCondition.setTermsCode(entity.getTermsCode());

		entityCondition.setValueCode(entity.getValueCode());
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueExactByEntity(entityCondition,
						NumberConstants.NUMERAL_ZORE, Integer.MAX_VALUE);
		if (!CollectionUtils.isEmpty(entitys)) {
			for (DataDictionaryValueEntity e : entitys) {
				if (!StringUtils.equals(entity.getVirtualCode(),
						e.getVirtualCode())) {
					throw new DataDictionaryValueException("数据字典-值代码已存在",
							"数据字典-值代码已存在");
				}
			}
		}

		entityCondition.setValueCode(null);
		entityCondition.setValueName(entity.getValueName());
		entitys = dataDictionaryValueDao.queryDataDictionaryValueExactByEntity(
				entityCondition, NumberConstants.NUMERAL_ZORE,
				Integer.MAX_VALUE);
		if (!CollectionUtils.isEmpty(entitys)) {
			for (DataDictionaryValueEntity e : entitys) {
				if (!StringUtils.equals(entity.getVirtualCode(),
						e.getVirtualCode())) {
					throw new DataDictionaryValueException("数据字典-值名称已存在",
							"数据字典-值代码已存在");
				}
			}
		}

		entityCondition.setValueName(null);
		entityCondition.setValueSeq(entity.getValueSeq());
		entitys = dataDictionaryValueDao.queryDataDictionaryValueExactByEntity(
				entityCondition, NumberConstants.NUMERAL_ZORE,
				Integer.MAX_VALUE);
		if (!CollectionUtils.isEmpty(entitys)) {
			for (DataDictionaryValueEntity e : entitys) {
				if (!StringUtils.equals(entity.getVirtualCode(),
						e.getVirtualCode())) {
					throw new DataDictionaryValueException("数据字典-值序号已存在",
							"数据字典-值代码已存在");
				}
			}
		}

		DataDictionaryValueEntity result = dataDictionaryValueDao
				.updateDataDictionaryValue(entity);

		// 刷新数据字典缓存
		this.flushDataDictionaryEntityCache(entity.getTermsCode());
		// 为了删除缓存需要先根据虚拟编码查询出实体信息
		// DataDictionaryValueEntity dataDictEntity = dataDictionaryValueDao
		// .queryDataDictionaryValueByVirtualCode(entity.getVirtualCode());
		// List<String> list = new ArrayList<String>();
		// list.add(dataDictEntity.getTermsCode());
		// list.add(dataDictEntity.getValueCode());
		// String code = StringUtils.join(list, SymbolConstants.EN_COMMA);
		// this.invalidateEntity(code);
		return result;
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 通过 VIRTUAL_CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 下午3:19:55
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByCode(java.lang.String)
	 */
	@Override
	public DataDictionaryValueEntity queryDataDictionaryValueByVirtualCode(
			String code) {
		if (null == code) {
			return null;
		}
		DataDictionaryValueEntity entity = dataDictionaryValueDao
				.queryDataDictionaryValueByVirtualCode(code);

		entity = attachTermsName(entity);
		return entity;
	}

	/**
	 * 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByCode(java.lang.String)
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueBatchByVirtualCode(
			String[] codes) {
		// 非空判断
		if (ArrayUtils.isEmpty(codes)) {
			// 返回为空
			return null;
		}
		// 执行查询
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueBatchByVirtualCode(codes);
		// 返回结果
		return attachTermsName(entitys);
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueExactByEntity(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity,
	 *      int, int)
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueExactByEntity(
			DataDictionaryValueEntity entity, int start, int limit) {
		// 非空验证，如果为空则声明新的对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 执行查询
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueExactByEntity(dataDictEntity, start,
						limit);
		// 返回查询结果
		return attachTermsName(entitys);
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueExactByEntityCount(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
	 */
	@Override
	public long queryDataDictionaryValueExactByEntityCount(
			DataDictionaryValueEntity entity) {
		// 非空验证，如果为空则声明新的对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 设置是否有效
		dataDictEntity.setActive(FossConstants.ACTIVE);
		// 返回查询结果
		return dataDictionaryValueDao
				.queryDataDictionaryValueExactByEntityCount(dataDictEntity);
	}

	/**
	 * 根据贵重物品词代码查询值得序号最大值
	 * 
	 * @author 132599-foss-shenweihua
	 * @param termsCode
	 *            贵重物品词代码
	 */
	@Override
	public long queryMaxValue(String termsCode) {
		return dataDictionaryValueDao.queryMaxValue(termsCode);
	}

	/**
	 * 动态的查询条件,模糊查询。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 下午3:19:55
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValueMore(java.lang.String[])
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueByEntity(
			DataDictionaryValueEntity entity, int start, int limit) {
		// 判断参数是否为空，如果为空则声明一个新的对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 执行查询
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueByEntity(dataDictEntity, start, limit);
		// 返回查询结果
		return attachTermsName(entitys);
	}

	/**
	 * 动态的查询条件-查询总条数。,模糊查询
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 下午3:19:55
	 * @see com.deppon.foss.module.district.server.dao.IDataDictionaryValueDao#queryDynimicCondition(com.deppon.foss.module.base.dict.api.shared.domain.district.shared.domain.DataDictionaryValueEntity)
	 */
	@Override
	public long queryDataDictionaryValueByEntityCount(
			DataDictionaryValueEntity entity) {
		// 判断参数是否为空，如果为空则声明一个新的对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 返回查询的结果
		return dataDictionaryValueDao
				.queryDataDictionaryValueByEntityCount(dataDictEntity);
	}

	/**
	 * 查询columnName列的columnValue值有多少个，可用于去重
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-17 下午3:19:55
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryCount(java.lang.String,
	 *      java.lang.String)
	 */
	/*@SuppressWarnings("unused")
	private long queryDataDictionaryValueCount(String columnName,
			String columnValue) {
		// 返回查询结果
		return dataDictionaryValueDao.queryDataDictionaryValueCount(columnName,
				columnValue);
	}*/

	/**
	 * 以下全为特殊查询：
	 */

	/**
	 * 动态的查询条件。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @param termsCode
	 *            上级词条 精确查询
	 * @param valueCode
	 *            值代码 模糊查询
	 * @param valueName
	 *            值名称 模糊查询
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueForView(
			DataDictionaryValueEntity entity, int start, int limit) {
		// 判断参数是否为空，为空则声明一个新对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 执行查询
		List<DataDictionaryValueEntity> entitys = dataDictionaryValueDao
				.queryDataDictionaryValueForView(dataDictEntity, start, limit);
		// 返回查询结果
		return attachTermsName(entitys);
	}

	/**
	 * 动态的查询条件-查询总条数。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @param termsCode
	 *            上级词条 精确查询
	 * @param valueCode
	 *            值代码 模糊查询
	 * @param valueName
	 *            值名称 模糊查询
	 */
	@Override
	public long queryDataDictionaryValueForViewCount(
			DataDictionaryValueEntity entity) {
		// 判断参数是否为空，为空则声明一个新对象
		DataDictionaryValueEntity dataDictEntity = entity == null ? new DataDictionaryValueEntity()
				: entity;
		// 执行查询并返回结果
		return dataDictionaryValueDao
				.queryDataDictionaryValueForViewCount(dataDictEntity);
	}

	/**
	 * 根据上级的 TERMS_CODE 查询下级所有的值对象：
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:11:20
	 */
	@Override
	public List<DataDictionaryValueEntity> queryDataDictionaryValueByTermsCode(
			String code) {
		// 声明list对象来存储查询结果
		List<DataDictionaryValueEntity> result = new ArrayList<DataDictionaryValueEntity>();
		// 对Code进行非空验证
		if (StringUtils.isBlank(code)) {
			// 返回查询结果
			return result;
		}
		// 先查询缓存中的数据
		if (SqlUtil.loadCache) {
			result = queryDataDictByTermsCodeByCache(code);
		} else {
			result = dataDictionaryValueDao
					.queryDataDictionaryValueByTermsCode(code);
		}
		// 返回查询结果
		return attachTermsName(result);
	}

	/**
	 * 根据 TERMS_CODE,VALUE_CODE 查询的值对象：
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:11:20
	 */
	@Override
	public DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCode(
			String termsCode, String valueCode) {
		// 非空校验
		if (StringUtils.isBlank(termsCode) || StringUtils.isBlank(valueCode)) {
			// 返回为空
			return null;
		}
		// 返回查询结果
		DataDictionaryValueEntity entity = null;
		// 先查询缓存
		if (SqlUtil.loadCache) {
			entity = queryDataDictByTermsCodeAndValueCodeByCache(termsCode,
					valueCode);
		} else {
			entity = dataDictionaryValueDao
					.queryDataDictionaryValueByTermsCodeValueCode(termsCode,
							valueCode);
		}
		return entity;
	}

	/**
	 * 根据 TERMS_CODE,VALUE_CODE 查询的值对象（不走缓存，供综合内部基础数据维护使用）：
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-18 下午4:11:20
	 */
	@Override
	public DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCodeNoCache(
			String termsCode, String valueCode) {
		// 非空校验
		if (StringUtils.isBlank(termsCode) || StringUtils.isBlank(valueCode)) {
			// 返回为空
			return null;
		}
		// 返回查询结果
		DataDictionaryValueEntity entity = null;

		entity = dataDictionaryValueDao
				.queryDataDictionaryValueByTermsCodeValueCode(termsCode,
						valueCode);

		return entity;
	}

	/**
	 * 
	 * <p>
	 * 批量查询数据字典
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 6, 2012 2:19:09 PM
	 * @param code
	 * @return
	 * @see
	 */
	@Override
	public Map<String, List<DataDictionaryValueEntity>> queryDataDictionaryValueBatchByTermsCode(
			List<String> codeList) {
		Map<String, List<DataDictionaryValueEntity>> result = new HashMap<String, List<DataDictionaryValueEntity>>();
		if (CollectionUtils.isEmpty(codeList)) {
			return result;
		}
		for (String code : codeList) {
			if (StringUtils.isEmpty(code)) {
				continue;
			}
			result.put(code, queryDataDictionaryValueByTermsCode(code));
		}
		return result;
	}

	/**
	 * 批量 给数据字典词条加上词条名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-27 下午7:16:14
	 */
	public List<DataDictionaryValueEntity> attachTermsName(
			List<DataDictionaryValueEntity> entitys) {
		for (DataDictionaryValueEntity entity : entitys) {
			entity = this.attachTermsName(entity);
		}
		return entitys;
	}

	/**
	 * 给数据字典词条加上词条名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-27 下午7:15:40
	 */
	public DataDictionaryValueEntity attachTermsName(
			DataDictionaryValueEntity entity) {
		if (entity == null) {
			return entity;
		}
		Map<String, DataDictionaryEntity> dataDictMap = DataDictUtil
				.getDataDictMap();
		DataDictionaryEntity dataDict = dataDictMap.get(entity.getTermsCode());
		if (dataDict == null) {
			return entity;
		}
		String termsName = dataDict.getTermsName();
		entity.setTermsName(termsName);
		return entity;
	}

	/**
     * 
     */
	public void flushDataDictionaryEntityCache(String key) {
		invalidateListEntity(key);
		invalidateEntity(key);
		@SuppressWarnings("rawtypes")
		RefreshableCache dataDictCache = (RefreshableCache) CacheManager
				.getInstance().getCache(DataDictionaryEntity.class.getName());
		dataDictCache.refresh();
	}

	/**
	 * 下面是特殊方法
	 */

	/**
	 * 批量作废数据字典-值
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-4 下午6:55:54
	 */
	@Override
	@Transactional
	public void deleteDataDictionaryValueMore(
			List<DataDictionaryValueEntity> entitys) {
		for (DataDictionaryValueEntity entity : entitys) {
			this.deleteDataDictionaryValue(entity);
		}
	}

	/**
	 * params dataDictionaryValueDao
	 */
	public void setDataDictionaryValueDao(
			IDataDictionaryValueDao dataDictionaryValueDao) {
		this.dataDictionaryValueDao = dataDictionaryValueDao;
	}

	/**
	 * 根据词条编码查询缓存,返回LIST集合
	 * 
	 * @author 088933-foss-张继恒
	 * @date 2012-12-4 下午6:55:54
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<DataDictionaryValueEntity> queryDataDictByTermsCodeByCache(
			String termsCode) {
		List<DataDictionaryValueEntity> dataDictList = new ArrayList<DataDictionaryValueEntity>();
		CacheManager cacheManger = CacheManager.getInstance();
		if (cacheManger == null) {
			return dataDictList;
		}
		ICache cache = cacheManger
				.getCache(FossTTLCache.DATE_DICT_LIST_ENTITY_CACHE_UUID);
		if (cache == null) {
			return dataDictList;
		}
		dataDictList = (List<DataDictionaryValueEntity>) cache.get(termsCode);
		return dataDictList;
	}

	/**
	 * 根据词条编码、值编码查询缓存,返回单条数据字典
	 * 
	 * @author 088933-foss-张继恒
	 * @date 2012-12-4 下午6:55:54
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DataDictionaryValueEntity queryDataDictByTermsCodeAndValueCodeByCache(
			String termsCode, String valueCode) {
		DataDictionaryValueEntity dataDictionaryValueEntity = new DataDictionaryValueEntity();
		CacheManager cacheManger = CacheManager.getInstance();
		if (cacheManger == null) {
			return dataDictionaryValueEntity;
		}
		ICache cache = cacheManger
				.getCache(FossTTLCache.DATE_DICT_ENTITY_CACHE_UUID);
		if (cache == null) {
			return dataDictionaryValueEntity;
		}
		List list = new ArrayList<String>();
		list.add(termsCode);
		list.add(valueCode);
		String code = StringUtils.join(list, SymbolConstants.EN_COMMA);
		dataDictionaryValueEntity = (DataDictionaryValueEntity) cache.get(code);
		return dataDictionaryValueEntity;
	}

	/**
	 * 清空指定的相关缓存
	 * 
	 * @author 088933-foss-张继恒
	 * @return
	 * @date 2012-12-4 下午6:55:54
	 */
	@SuppressWarnings("unchecked")
	private void invalidateEntity(String key) {
		CacheManager.getInstance()
				.getCache(FossTTLCache.DATE_DICT_ENTITY_CACHE_UUID)
				.invalid(key);
	}

	/**
	 * 清空指定的相关缓存列表
	 * 
	 * @author 088933-foss-张继恒
	 * @return
	 * @date 2012-12-4 下午6:55:54
	 */
	@SuppressWarnings("unchecked")
	private void invalidateListEntity(String key) {
		CacheManager.getInstance()
				.getCache(FossTTLCache.DATE_DICT_LIST_ENTITY_CACHE_UUID)
				.invalid(key);
	}

	/**
	 * 判断数据字典内容是否有更新
	 * 
	 * @author 107046-foss-zengxiantao
	 * @date 2013-05-06
	 */
	@Override
	public long getLastChangeVersionNo() {
		return dataDictionaryValueDao.getLastChangeVersionNo();
	}

}
