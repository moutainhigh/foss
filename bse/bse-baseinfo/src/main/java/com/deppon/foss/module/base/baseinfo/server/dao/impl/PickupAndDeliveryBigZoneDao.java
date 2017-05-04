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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PickupAndDeliveryBigZoneDao.java
 * 
 * FILE NAME        	: PickupAndDeliveryBigZoneDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 集中接送货大区DAO实现类：对定人定区大区提供增删改查的操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-12
 * 下午2:15:24
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午2:15:24
 * @since
 * @version
 */
public class PickupAndDeliveryBigZoneDao extends SqlSessionDaoSupport implements
	IPickupAndDeliveryBigZoneDao {

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.bigzone.";

    /**
     * 新增集中接送货大区
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-24 下午3:40:41
     * @param entity
     *            集中接送货大区实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#addPickupAndDeliveryBigZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
     */
    @Override
    public int addPickupAndDeliveryBigZone(BigZoneEntity entity) {

	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /**
     * 根据code作废集中接送货大区信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:15:24
     * @param codes
     *            code字符串数组
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#deletePickupAndDeliveryBigZoneByCode(java.lang.String[])
     */
    @Override
    public int deletePickupAndDeliveryBigZoneByCode(String[] codes,
	    String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /**
     * 修改集中接送货大区信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:15:24
     * @param entity
     *            集中接送货大区实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updatePickupAndDeliveryBigZone(BigZoneEntity entity) {

	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /**
     * 根据传入对象查询符合条件所有集中接送货大区信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:15:24
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigZoneEntity> queryPickupAndDeliveryBigZones(
	    BigZoneEntity entity, int limit, int start) {

	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByCondition",
		entity, rowBounds);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BigZoneEntity> queryBigZonesByNameOrCode(BigZoneEntity entity) {
	return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByNameOrCode",
		entity);
    }
    /**
     * 统计总记录数
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:15:24
     * @param entity
     *            集中接送货大区实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(BigZoneEntity entity) {

	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }

    /**
     * 根据区域编码查询集中接送货大区详细信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午1:39:44
     * @param regionCode
     *            区域编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#queryBigzoneByCode(java.lang.String)
     */
    @Override
    public BigZoneEntity queryBigzoneByCode(String regionCode) {

	Map<String, String> map = new HashMap<String, String>();
	map.put("regionCode", regionCode);
	map.put("active", FossConstants.ACTIVE);
	return (BigZoneEntity) this.getSqlSession().selectOne(
		NAMESPACE + "queryBigzoneByCode", map);
    }

    /**
     * 根据条件查询接送货大区
     * 
     * @author panGuangJun
     * @date 2012-12-3 上午9:36:45
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#queryBigZonesByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigZoneEntity> queryBigZonesByCondition(BigZoneEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByCondition",
		entity, rowBounds);
    }

    /**
     * <p>
     * 根据大区虚拟编码查询集中接送货大区详细信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-11 下午7:34:29
     * @param virtualCode
     *            虚拟编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#queryBigzoneByVirtualCode(java.lang.String)
     */
    @Override
    public BigZoneEntity queryBigzoneByVirtualCode(String virtualCode) {
	//参数为空判断
	if(StringUtils.isBlank(virtualCode)){
	    return null;
	}
	Map<String, String> map = new HashMap<String, String>();
	map.put("virtualCode", virtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (BigZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "queryBigzoneByVirtualCode",map);
    }
    
    /**
     * <p>根据集中接送货大区生成的前五位编码模糊查询集中接送货大区</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-11 上午10:32:19
     * @param generateCode 接送货大区生成的前五位编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#queryBigZonesByGenerateCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigZoneEntity> queryBigZonesByGenerateCode(String generateCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("generateCode",generateCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryBigZonesByGenerateCode", map);
    }
    
    /**
     * 根据管理部门查询接送货大区
     * 
     * @author zhuweixing
     * @date 2015-01-06  上午9:36:45
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao#searchBigZonesByManagement(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity,
     *      int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BigZoneEntity> searchBigZonesByManagement(BigZoneEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return this.getSqlSession().selectList(NAMESPACE + "searchBigZonesByManagement",
		entity, rowBounds);
    }
    
    /**
     * 统计总记录数
     * 
     * @author 218400-foss-zhuweixing
     * @date 2015-01-06  下午2:15:24
     * @param entity
     *            集中接送货大区实体
     * @return
     * @see
     */
    @Override
    public Long searchRecordCount(BigZoneEntity entity) {

	return (Long) this.getSqlSession().selectOne(NAMESPACE + "searchCount",
		entity);
    }
}
