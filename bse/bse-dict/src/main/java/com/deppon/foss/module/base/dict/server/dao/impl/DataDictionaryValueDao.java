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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/dao/impl/DataDictionaryValueDao.java
 * 
 * FILE NAME        	: DataDictionaryValueDao.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class DataDictionaryValueDao extends SqlSessionDaoSupport implements
	IDataDictionaryValueDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataDictionaryValueDao.class);
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_DICT_PREFIX
	    + ".dataDictionaryValue.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:24:4
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#addDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
     */
    @Override
    public DataDictionaryValueEntity addDataDictionaryValue(
	    DataDictionaryValueEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return null;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    entity.setVirtualCode(entity.getId());
	}
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());

	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(
		NAMESPACE + "addDataDictionaryValue", entity);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:24:4
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValue(java.lang.String)
     */
    @Override
    public DataDictionaryValueEntity deleteDataDictionaryValue(
	    DataDictionaryValueEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}

	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(
		NAMESPACE + "deleteDataDictionaryValue", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:24:4
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValueMore(java.lang.String[])
     */
    @Override
    public DataDictionaryValueEntity deleteDataDictionaryValueMore(
	    DataDictionaryValueEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}

	// 组装参数：
	String code = entity.getVirtualCode();
	String[] codes = code.split(",");

	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);

	int result = getSqlSession().update(
		NAMESPACE + "deleteDataDictionaryValueMore", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:24:4
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#updateDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
     */
    @Override
    public DataDictionaryValueEntity updateDataDictionaryValue(
	    DataDictionaryValueEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return entity;
	}

	// 更新要先删除旧的数据：
	DataDictionaryValueEntity result = this
		.deleteDataDictionaryValue(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	entity.setVersionNo(System.currentTimeMillis());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(new Date());
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());

	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(
		NAMESPACE + "addDataDictionaryValue", entity);
	return resultNum > 0 ? entity : null;
    }

    /**
     * 以下全为查询：
     */

    /**
     * 通过 编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataDictionaryValueEntity queryDataDictionaryValueByVirtualCode(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setVirtualCode(code);

	List<DataDictionaryValueEntity> entitys = this.getSqlSession()
		.selectList(
			NAMESPACE + "queryDataDictionaryValueByVirtualCode",
			entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(0);
	}
    }

    /**
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueBatchByVirtualCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueBatchByVirtualCode", map);
    }

    /**
     * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueExactByEntity(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueExactByEntity(
	    DataDictionaryValueEntity entity, int start, int limit) {
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueExactByEntity",
		queryEntity, rowBounds);
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
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long) this.getSqlSession().selectOne(
		NAMESPACE + "queryDataDictionaryValueExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询条件 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValueMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueByEntity(
	    DataDictionaryValueEntity entity, int start, int limit) {
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueByEntity", entity,
		rowBounds);
    }

    /**
     * 模糊查询条件 动态的查询条件-查询总条数。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.server.dao.IDataDictionaryValueDao#queryDynimicCondition(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity)
     */
    @Override
    public long queryDataDictionaryValueByEntityCount(
	    DataDictionaryValueEntity entity) {
	if (null == entity) {
	    entity = new DataDictionaryValueEntity();
	}
	entity.setActive(FossConstants.ACTIVE);
	return (Long) this.getSqlSession().selectOne(
		NAMESPACE + "queryDataDictionaryValueByEntityCount", entity);
    }
    
    /**
     * 根据贵重物品词代码查询值得序号最大值
     * @author 132599-foss-shenweihua
     * @param termsCode 贵重物品词代码
     */
    @Override
	public long queryMaxValue(String termsCode) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryMaxValue", termsCode);
	}
    

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryCount(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public long queryDataDictionaryValueCount(String columnName,
	    String columnValue) {
	if (null == columnName) {
	    return 0;
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("columnName", columnName);
	map.put("columnValue", columnValue);
	map.put("active", FossConstants.ACTIVE);
	return (Long) this.getSqlSession().selectOne(
		NAMESPACE + "queryDataDictionaryValueCount", map);
    }

    /**
     * 根据entity精确查询,用于数据下载 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:20:53
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueForDownload(
	    DataDictionaryValueEntity entity) {
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	return (List<DataDictionaryValueEntity>) getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueForDownload", queryEntity);
    }
    
    /**
     * 根据entity精确查询,用于数据下载 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午9:20:53
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueForDownloadByPage(
	    DataDictionaryValueEntity entity, int start , int limited) {
		RowBounds rowBounds = new RowBounds(start, limited);
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	return (List<DataDictionaryValueEntity>) getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueForDownload", queryEntity,rowBounds);
    }

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
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueForView(
	    DataDictionaryValueEntity entity, int start, int limit) {
	DataDictionaryValueEntity queryEntity;
	if (null == entity) {
	    queryEntity = new DataDictionaryValueEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueForView", entity,
		rowBounds);
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
	if (null == entity) {
	    entity = new DataDictionaryValueEntity();
	}
	entity.setActive(FossConstants.ACTIVE);
	return (Long) this.getSqlSession().selectOne(
		NAMESPACE + "queryDataDictionaryValueForViewCount", entity);
    }

    /**
     * 根据上级的 TERMS_CODE 查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:12:25
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByTermsCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueByTermsCode(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setTermsCode(code);

	return this.getSqlSession().selectList(
		NAMESPACE + "queryDataDictionaryValueByTermsCode", entity);
    }
    
    /**
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:12:25
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByTermsCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public DataDictionaryValueEntity queryDataDictionaryValueByTermsCodeValueCode(
	    String termsCode, String valueCode) {
	if (StringUtils.isBlank(termsCode) || StringUtils.isBlank(valueCode)) {
	    return null;
	}

	// 构造查询条件：
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setTermsCode(termsCode);
	entity.setValueCode(valueCode);

	List<DataDictionaryValueEntity> entitys = this
		.getSqlSession()
		.selectList(
			NAMESPACE
				+ "queryDataDictionaryValueByTermsCodeValueCode",
			entity);
	if (CollectionUtils.isNotEmpty(entitys)) {
	    return entitys.get(0);
	}
	return null;
    }

    /**
     * 
     * 根据上级的 TERMS_CODE 批量查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:12:25
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#queryDataDictionaryValueByTermsCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryValueEntity> queryDataDictionaryValueMoreByTermsCode(
	    String[] codes) {
	if (codes == null || codes.length == 0) {
	    return null;
	}

	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	// 只查询active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);

	return (List<DataDictionaryValueEntity>) this.getSqlSession()
		.selectList(
			NAMESPACE + "queryDataDictionaryValueMoreByTermsCode",
			map);
    }

    /*********************** 缓存方法定义 ************************************/
    /**
     * 通过词条代码，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
    /************
     * @Override public DataDictionaryEntity queryDataForCacheByTermsCode(String
     *           termsCode) { Map<String, Object> map = new HashMap<String,
     *           Object>(); map.put("termsCode", termsCode); map.put("active",
     *           FossConstants.ACTIVE); return (DataDictionaryEntity)
     *           getSqlSession().selectOne(
     *           "foss.bse.bse-dict.dataDictionaryCache.queryDataForCacheByTermsCode"
     *           , map); }
     **************/
    /**
     * 通过最后更新时间，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
    /************
     * @SuppressWarnings("unchecked")
     * @Override public List<DataDictionaryEntity> getByLastModifyDate(Date
     *           modifyDate) { Map<String, Object> map = new HashMap<String,
     *           Object>(); map.put("modifyDate", modifyDate); map.put("active",
     *           FossConstants.ACTIVE); return (List<DataDictionaryEntity>)
     *           getSqlSession().selectList(
     *           "foss.bse.bse-dict.dataDictionaryCache.getByLastModifyDate",
     *           map); }
     **************/
    /**
     * 通过一组词条代码，加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryEntity> queryDataForCacheByTermsCodes(
	    String[] termsCodes) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("termsCodes", termsCodes);
	map.put("active", FossConstants.ACTIVE);
	return (List<DataDictionaryEntity>) getSqlSession()
		.selectList(
			"foss.bse.bse-dict.dataDictionaryCache.queryDataForCacheByTermsCodes",
			map);
    }

    /**
     * 返回最后修改时间
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
    @Override
    public Date getLastModifyTime() {
	// return (Date)
	// getSqlSession().selectOne("foss.bse.bse-dict.dataDictionaryCache.getLastModifyTime",
	// FossConstants.ACTIVE);
	long versionNo = (Long) getSqlSession().selectOne(
		"foss.bse.bse-dict.dataDictionaryCache.getLastModifyTime",
		FossConstants.ACTIVE);

	Date lastModyfyTime = new Date(versionNo);
	return lastModyfyTime;
    }

    /**
     * 加载数据字典缓存的数据
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-13 下午2:6:1
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DataDictionaryEntity> queryDataForCache() {
	return (List<DataDictionaryEntity>) getSqlSession().selectList(
		"foss.bse.bse-dict.dataDictionaryCache.queryDataForCache",
		FossConstants.ACTIVE);
    }
    /*********************** 缓存方法定义 ************************************/
	
    /**
     * 判断数据字典内容是否有更新
     * 
     * @author 107046-foss-zengxiantao
     * @date 2013-05-06
     */  
	@Override
	public long getLastChangeVersionNo() {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "getLastChangeVersionNo");
	}

}
