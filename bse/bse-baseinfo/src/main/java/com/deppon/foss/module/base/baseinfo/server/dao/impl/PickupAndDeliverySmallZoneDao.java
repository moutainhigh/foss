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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PickupAndDeliverySmallZoneDao.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定人定区集中集中接送货小区Dao实现类：对定人定区小区提供增删改查的操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午1:47:49 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午1:47:49
 * @since
 * @version
 */
public class PickupAndDeliverySmallZoneDao extends SqlSessionDaoSupport implements
	IPickupAndDeliverySmallZoneDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.smallzone.";
    
    /**
     * 新增集中接送货小区 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午1:47:49
     * @param entity
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int addPickupAndDeliverySmallZone(SmallZoneEntity entity) {
	
	entity.setCreateDate(new Date());
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废集中接送货小区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午1:47:49
     * @param codeStr 编码拼接字符串
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int deletePickupAndDeliverySmallZoneByCode(String[] codes,String modifyUser) {

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改集中接送货小区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午1:47:49
     * @param entity 集中接送货小区实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updatePickupAndDeliverySmallZone(SmallZoneEntity entity) {

	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有集中接送货小区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午1:47:49
     * @param entity 集中接送货小区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SmallZoneEntity> queryPickupAndDeliverySmallZones(
	    SmallZoneEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SmallZoneEntity> querySmallZonesByNameOrCode(
	    SmallZoneEntity entity) {
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByNameOrCode",
		entity);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午1:47:49
     * @param entity 集中接送货小区实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(SmallZoneEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件
     * 的集中接送货小区 
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 下午3:04:56
     * @param deptCode 管理部门Code
     * @param type 区域类型
     * @return 集中接送货小区列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#querySmallZonesByDeptCode(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
	    String areaTyp,String bigZoneVirtualCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("deptCode", deptCode);
	map.put("regionType",areaTyp);
	map.put("bigZoneVirtualCode", bigZoneVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByDeptCode", map);
    }
    
    /**
     * 根据区域编码查询集中接送货小区详细信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午2:27:08
     * @param regionCode 区域编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#querySmallZoneByCode(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByCode(String regionCode) {
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("regionCode", regionCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (SmallZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmallZoneByCode", map);
    }
    
    /**
     * <p>根据虚拟编码查询集中接送货小区详细信息 </p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-25 上午8:06:36
     * @param virtualCode 虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#querySmallZoneByVirtualCode(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByVirtualCode(String virtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("virtualCode", virtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (SmallZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmallZoneByVirtualCode", map);
    }
    
    /**
     * <p>验证小区名称是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-7 上午9:20:06
     * @param regionName 集中接送货小区名称
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#querySmallZoneByName(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByName(String regionName) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("regionName", regionName);
	map.put("active", FossConstants.ACTIVE);
	
	return (SmallZoneEntity)this.getSqlSession().selectOne(NAMESPACE + "querySmallZoneByName", map);
    }
    
    /**
     * <p>根据大区的区域编码模糊查询集中接送货小区</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-11 下午4:25:03
     * @param bigZoneRegionCode 大区的区域编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#querySmallZonesByBigZoneRegionCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SmallZoneEntity> querySmallZonesByBigZoneRegionCode(
	    String bigZoneRegionCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("bigZoneRegionCode", bigZoneRegionCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZonesByBigZoneRegionCode", map);
    }
    
    /**
     * <p>根据小区虚拟编码修改小区的区域编码、所属大区</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 上午8:12:26
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#updateSmallZoneByVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Override
    public int updateSmallZoneByVirtualCode(SmallZoneEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "updateSmallZoneByVirtualCode", entity);
    }
    
    /**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-18 上午9:31:36
     * @param bigVirtualCode
     *            所属大区编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao#updateSmallZoneByBigCode(java.lang.String)
     */
    @Override
    public int updateSmallZoneByBigCode(SmallZoneEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "updateSmallZoneByBigCode", entity);
    }
    /**
     * 通过GISid查询
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<SmallZoneEntity> querySmallZoneByGisId(String gisId) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("gisid", gisId);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "querySmallZoneByGisId", map);
	}
	
	 /**
     * 根据管理部门查询符合条件所有集中接送货小区信息 
     * @author 218400-foss-zhuweixing
     * @date 2015-01-06 下午1:47:49
     * @param entity 集中接送货小区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SmallZoneEntity> findSmallZonesByManagement(
    		SmallZoneEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "findSmallZonesByManagement", entity,rowBounds);
    }
    

    /**
     * 统计总记录数 
     * @author 218400-foss-zhuweixing
     * @date 2015-01-06 下午1:47:49
     * @param entity 集中接送货小区实体
     * @return
     * @see
     */
    @Override
    public Long findRecordCount(SmallZoneEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "findCount", entity);
    }


}
