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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/dao/impl/ConfigurationParamsDao.java
 * 
 * FILE NAME        	: ConfigurationParamsDao.java
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ConfigurationParamsDao extends SqlSessionDaoSupport implements
	IConfigurationParamsDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_DICT_PREFIX
	    + ".configurationParams.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#addConfigurationParams(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
     */
    @Override
    public ConfigurationParamsEntity addConfigurationParams(ConfigurationParamsEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if(StringUtils.isBlank(entity.getVirtualCode())){
	    entity.setVirtualCode(entity.getId());
	}
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
		
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addConfigurationParams", entity);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#deleteConfigurationParams(java.lang.String)
     */
    @Override
    public ConfigurationParamsEntity deleteConfigurationParams(ConfigurationParamsEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
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
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteConfigurationParams", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#deleteConfigurationParamsMore(java.lang.String[], java.lang.String)
     */
    @Override
    public ConfigurationParamsEntity deleteConfigurationParamsMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	ConfigurationParamsEntity entity = new ConfigurationParamsEntity();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	entity.setModifyUser(deleteUser);
	entity.setActive(FossConstants.INACTIVE);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteConfigurationParamsMore", map);
	return result > 0 ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#updateConfigurationParams(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
     */
    @Override
    public ConfigurationParamsEntity updateConfigurationParams(ConfigurationParamsEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	ConfigurationParamsEntity result = this.deleteConfigurationParams(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}

	if(StringUtils.isBlank(entity.getVirtualCode())){
	    if(StringUtils.isNotBlank(entity.getId())){
		entity.setVirtualCode(entity.getId());
	    }else{
		entity.setVirtualCode(UUIDUtils.getUUID());
	    }
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
	int resultNum = getSqlSession().insert(NAMESPACE + "addConfigurationParams", entity);
	return resultNum > 0 ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationParamsEntity queryConfigurationParamsByVirtualCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	ConfigurationParamsEntity entity=new ConfigurationParamsEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setVirtualCode(code);
	
	List<ConfigurationParamsEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryConfigurationParamsByVirtualCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(0);
	}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsBatchByVirtualCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (codes==null||codes.length==0){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	return getSqlSession().selectList(
		NAMESPACE + "queryConfigurationParamsBatchByVirtualCode", map);
    }
    /**
     * 
     *<p>精确查询  根据多个编码进行批量查询</P>
     * @author 130566-foss-ZengJunfan
     * @date 2013-5-27下午6:12:32
     *@param codes
     *@return
     */
    @SuppressWarnings("unchecked")
	@Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(
			String[] codes) {
    	//请求参数是否合法，为空时返回空。
    	if(codes ==null || codes.length ==0){
    		return null;
    	}
    	//查询条件
    	Map<String,Object> map =new HashMap<String,Object>();
    	map.put("codes", codes);
    	map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigurationParamsBatchByCode",map);
	}
    
    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsExactByEntity(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
	    ConfigurationParamsEntity entity, int start, int limit) {
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryConfigurationParamsExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsExactByEntityCount(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
     */
    @Override
    public long queryConfigurationParamsExactByEntityCount(ConfigurationParamsEntity entity) {
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryConfigurationParamsExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(
	    ConfigurationParamsEntity entity, int start, int limit) {
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryConfigurationParamsByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     * @see com.deppon.foss.module.base.dict.server.dao.IConfigurationParamsDao#queryConfigurationParamsByEntityCount(com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity)
     */
    @Override
    public long queryConfigurationParamsByEntityCount(ConfigurationParamsEntity entity) {
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryConfigurationParamsByEntityCount", queryEntity);
    }
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:35:27
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConfigurationParamsEntity> queryConfigurationParamsForDownload(ConfigurationParamsEntity entity){
	ConfigurationParamsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new ConfigurationParamsEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<ConfigurationParamsEntity>) getSqlSession().selectList(NAMESPACE + "queryConfigurationParamsForDownload", queryEntity);
    }
    

    /**
     * 下面为特殊查询
     */
	
    /**
     * 精确查询
     * 通过 ConfigurationParams的CODE和OrgAdministrativeInfo的CODE查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-24 下午7:09:26
     */
    public ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String code,String orgCode){
	if (StringUtils.isBlank(code)||StringUtils.isBlank(orgCode)) {
	    return null;
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", code);
	map.put("orgCode", orgCode);
	map.put("active", FossConstants.ACTIVE);
	@SuppressWarnings("unchecked")
	List<ConfigurationParamsEntity> entitys = (List<ConfigurationParamsEntity>) this.getSqlSession().selectList(
		NAMESPACE + "queryConfigurationParamsByOrgCode", map);
	if(CollectionUtils.isEmpty(entitys)){
	    return null;
	}else{
	    return entitys.get(0);
	}
    }
    
    /**
      * 递归查询当前机构及其以上级别(父级)的 参数配置 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param params
	  * @return
	 */
    @Override
	public List<ConfigurationParamsEntity> queryConfigurationParamsByOrgCode(ConfigurationParamsEntity params) {
//		if (params == null || params.) {
//			return null;
//		}
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("code", code);
//		map.put("orgCode", orgCode);
//		map.put("active", FossConstants.ACTIVE);
		@SuppressWarnings("unchecked")
		List<ConfigurationParamsEntity> entitys = (List<ConfigurationParamsEntity>) this
				.getSqlSession().selectList(
						NAMESPACE + "queryConfigurationParamsByParams", params);
		if (CollectionUtils.isEmpty(entitys)) {
			return null;
		} else {
			return entitys;
		}
	}
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationParamsDao.class);

    /**
     * 提供给接送货根据code和时间来查询
     * @param Code
     * @param date
     * @return
     */
	@Override
	public ConfigurationParamsEntity queryConfigurationByCodeAndDate(
			String code, Date date) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(code)||date==null){
			return null;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("date", date);
		return (ConfigurationParamsEntity)getSqlSession().selectOne(NAMESPACE + "queryConfigurationByCodeAndDate", map);
	}

	@Override
	public String querySysConfig(ConfigurationParamsEntity params) {
		return (String) getSqlSession().selectOne(NAMESPACE + "querySysConfig", params);
	}

}
