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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IPickupAndDeliveryBigZoneDao.java
 * 
 * FILE NAME        	: IPickupAndDeliveryBigZoneDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;

/**
 * 集中接送货大区DAO接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-11 上午9:53:07
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 上午10:17:23
 * @since
 * @version
 */
public interface IPickupAndDeliveryBigZoneDao {

    /**
     * 新增集中接送货大区
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午10:17:23
     * @param entity
     *            集中接送货大区实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addPickupAndDeliveryBigZone(BigZoneEntity entity);

    /**
     * 根据code作废集中接送货大区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午10:17:23
     * @param codes
     * @param modifyUser
     *            修改人
     * @return
     * @see
     */
    int deletePickupAndDeliveryBigZoneByCode(String[] codes, String modifyUser);

    /**
     * 修改集中接送货大区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 上午10:17:23
     * @param entity
     *            集中接送货大区实体
     * @return
     * @see
     */
    int updatePickupAndDeliveryBigZone(BigZoneEntity entity);

    /**
     * 根据传入对象查询符合条件所有集中接送货大区信息
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 下午1:55:57
     * @param entity
     *            集中接送货大区实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<BigZoneEntity> queryPickupAndDeliveryBigZones(BigZoneEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2012-10-11 下午1:58:06
     * @param entity
     *            集中接送货大区实体
     * @see
     */
    Long queryRecordCount(BigZoneEntity entity);

    /**
     * 根据区域编码查询集中接送货大区详细信息
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午1:39:44
     * @param regionCode
     *            区域编码
     * @return
     * @see
     */
    BigZoneEntity queryBigzoneByCode(String regionCode);

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
     * @see
     */
    BigZoneEntity queryBigzoneByVirtualCode(String virtualCode);

    /**
     * 根据条件查询接送货大区信息
     * 
     * @author panGuangJun
     * @date 2012-12-3 上午9:36:14
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @retrun List<BigZoneEntity>
     * @param
     */
    List<BigZoneEntity> queryBigZonesByCondition(BigZoneEntity entity,
	    int limit, int start);
    
    /**
     * <p>根据集中接送货大区生成的前五位编码模糊查询集中接送货大区</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-11 上午10:32:19
     * @param generateCode 接送货大区生成的前五位编码
     * @return
     * @see
     */
    List<BigZoneEntity> queryBigZonesByGenerateCode(String generateCode);
    /**
     * 根据条件查询接送货大区信息
     * 
     * @author 073586-LIXUEXING
     * @date 2012-12-3 上午9:36:14
     * @retrun List<BigZoneEntity>
     * @param
     */
    List<BigZoneEntity> queryBigZonesByNameOrCode(BigZoneEntity entity);
    
    /**
     * 统计总记录数
     * 
     * @author foss-zhuweixing
     * @date 2015-01-06 下午1:58:06
     * @param entity
     *            集中接送货大区实体
     * @see
     */
	Long searchRecordCount(BigZoneEntity entity);
    
	/**
     * 根据管理部门查询接送货大区信息
     * 
     * @author zhuweixing
     * @date 2015-01-06 上午9:36:14
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @retrun List<BigZoneEntity>
     * @param
     */
	List<BigZoneEntity> searchBigZonesByManagement(BigZoneEntity entity,
			int limit, int start);

}
