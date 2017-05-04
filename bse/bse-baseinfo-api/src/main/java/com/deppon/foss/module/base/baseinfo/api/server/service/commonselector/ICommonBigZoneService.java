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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonBigZoneService.java
 * 
 * FILE NAME        	: ICommonBigZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
/**
 * 集中接送货大区service接口
 * <p style="display:none">modifyRecord</p>
 * @author panGuangjun
 * @date 2012-12-03 下午5:51:34
 * @since
 * @version
 */
public interface ICommonBigZoneService extends IService {
    /**
     * 根据传入对象查询符合条件所有集中接送货大区以及大区所包含小区信息
     * @author panGuangjun
     * @date 2012-12-03 下午5:51:34
     * @param entity 集中接送货大区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
     List<BigZoneEntity> queryBigZones(BigZoneEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author panGuangjun
     * @date 2012-12-03 下午5:51:34
     * @param entity 集中接送货大区实体
     * @return
     * @see
     */
     Long queryRecordCount(BigZoneEntity entity);
     
     /**
      * 统计总记录数 
      * @author zhuweixing
      * @date 2015-01-06 下午5:51:34
      * @param entity 集中接送货大区实体
      * @return
      * @see
      */
	 Long searchRecordCount(BigZoneEntity entity);
     
	 /**
	     * 根据管理部门查询接送货大区消息
	     * @author zhuweixing
	     * @date 2015-01-06 下午5:51:34
	     * @param entity 集中接送货大区实体
	     * @param limit 每页最大显示记录数
	     * @param start 开始记录数
	     * @return 符合条件的实体列表
	     * @see
	     */
	 List<BigZoneEntity> searchBigZones(BigZoneEntity entity, int limit,
			int start);
     
	 /**
      * 统计总记录数 
      * @author zhuweixing
      * @date 2015-01-06 下午5:51:34
      * @param entity 集中接送货小区实体
      * @return
      * @see
      */
	 Long findRecordCount(SmallZoneEntity entity);
     
	 /**
	     * 根据管理部门查询接送货小区消息
	     * @author zhuweixing
	     * @date 2015-01-06 下午5:51:34
	     * @param limit 每页最大显示记录数
	     * @param start 开始记录数
	     * @return 符合条件的实体列表
	     * @see
	     */
	 List<SmallZoneEntity> findSmallZones(SmallZoneEntity entity, int limit,
			int start);
	 
     /**
      * 统计总记录数 
      * @author  wusuhua
      * @date 2015-06-11 下午5:51:34
      * @param entity 集中送货大区实体
      * @return
      * @see
      */
	 Long searchDERecordCount(BigZoneEntity entity);
     
	 /**
	     * 根据管理部门查询接送货大区消息
	     * @author wusuhua
	     * @date 2015-06-11 下午5:51:34
	     * @param entity 集中送货大区实体
	     * @param limit 每页最大显示记录数
	     * @param start 开始记录数
	     * @return 符合条件的实体列表
	     * @see
	     */
	 List<BigZoneEntity> searchBigZonesDE(BigZoneEntity entity, int limit,
			int start);
     
}
