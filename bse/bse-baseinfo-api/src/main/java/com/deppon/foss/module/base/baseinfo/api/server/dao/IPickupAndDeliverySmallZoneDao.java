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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPickupAndDeliverySmallZoneDao.java
 * 
 * FILE NAME        	: IPickupAndDeliverySmallZoneDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;

/**
 * 集中接送货小区DAO接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-11 上午9:53:07
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 上午9:53:07
 * @since
 * @version
 */
public interface IPickupAndDeliverySmallZoneDao {

    /**
     * 新增集中接送货小区
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午9:54:10
     * @param entity
     *            集中接送货小区实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addPickupAndDeliverySmallZone(SmallZoneEntity entity);

    /**
     * 根据code作废集中接送货小区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午9:54:20
     * @param codeStr
     *            编码拼接字符串
     * @return 1：成功；-1：失败
     * @see
     */
    int deletePickupAndDeliverySmallZoneByCode(String[] codes, String modifyUser);

    /**
     * 修改集中接送货小区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午9:54:29
     * @param entity
     *            集中接送货小区实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updatePickupAndDeliverySmallZone(SmallZoneEntity entity);

    /**
     * <p>
     * 根据小区虚拟编码修改小区的区域编码、所属大区
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-12 上午8:12:26
     * @param entity
     * @return
     * @see
     */
    int updateSmallZoneByVirtualCode(SmallZoneEntity entity);

    /**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-18 上午9:31:36
     * @param entity
     *            小区信息
     * @return
     * @see
     */
    int updateSmallZoneByBigCode(SmallZoneEntity entity);

    /**
     * 根据传入对象查询符合条件所有集中接送货小区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 下午1:55:57
     * @param entity
     *            集中接送货小区实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<SmallZoneEntity> queryPickupAndDeliverySmallZones(
	    SmallZoneEntity entity, int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 下午1:58:06
     * @param entity
     *            集中接送货小区实体
     * @return
     * @see
     */
    Long queryRecordCount(SmallZoneEntity entity);

    /**
     * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件 的集中接送货小区
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-25 下午3:04:56
     * @param deptCode
     *            管理部门Code
     * @param type
     *            区域类型
     * @return 集中接送货小区列表
     * @see
     */
    List<SmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
	    String type, String bigZoneVirtualCode);

    /**
     * 根据区域编码查询集中接送货小区详细信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午2:27:08
     * @param regionCode
     *            区域编码
     * @return
     * @see
     */
    SmallZoneEntity querySmallZoneByCode(String regionCode);

    /**
     * <p>
     * 验证小区名称是否唯一
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-7 上午9:20:06
     * @param regionName
     *            集中接送货小区名称
     * @return
     * @see
     */
    SmallZoneEntity querySmallZoneByName(String regionName);

    /**
     * <p>
     * 根据虚拟编码查询集中接送货小区详细信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-25 上午8:06:36
     * @param virtualCode
     *            虚拟编码
     * @return
     * @see
     */
    SmallZoneEntity querySmallZoneByVirtualCode(String virtualCode);

    /**
     * <p>
     * 根据大区的区域编码模糊查询集中接送货小区
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-11 下午4:25:03
     * @param bigZoneRegionCode
     *            大区的区域编码
     * @return
     * @see
     */
    List<SmallZoneEntity> querySmallZonesByBigZoneRegionCode(
	    String bigZoneRegionCode);

    /**
     * 根据条件查询接送货小区信息
     * 
     * @author 073586-LIXUEXING
     * @date 2012-12-3 上午9:36:14
     * @RETRUN List<SmallZoneEntity>
     * @param
     */
    List<SmallZoneEntity> querySmallZonesByNameOrCode(SmallZoneEntity entity);
    
    public List<SmallZoneEntity> querySmallZoneByGisId(String gisId);
    
    /**
     * 根据管理部门接送货小区信息
     * 
     * @author 218400-zhuweixing
     * @date 2014-12-26 上午9:36:14
     * @RETRUN List<SmallZoneEntity>
     * @param
     */
    List<SmallZoneEntity> findSmallZonesByManagement(SmallZoneEntity entity,
			int limit, int start);
    
	/**
     * 统计总记录数
     * 
     * @author foss-zhuweixing
     * @date 2014-12-26 下午1:58:06
     * @param entity
     *            集中接送货小区实体
     * @return
     * @see
     */
	Long findRecordCount(SmallZoneEntity entity);

    
    
}
