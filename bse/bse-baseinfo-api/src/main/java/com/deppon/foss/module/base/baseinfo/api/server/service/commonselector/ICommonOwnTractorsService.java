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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/commonselector/ICommonOwnTractorsService.java
 * 
 * FILE NAME        	: ICommonOwnTractorsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo-api
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.api.server.service
 * FILE    NAME: ICommonOwnTractorsService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.TruckDto;

/**
 * 公共选择器--公司车辆查询service
 * @author panGuangJun
 * @date 2012-12-3 上午8:23:09
 */
public interface ICommonOwnTractorsService {
	  
    /**
     * <p>根据条件有选择的统计出符合条件的“公司拖头”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author panGuangjun
     * @date 2012-12-03下午3:51:34
     * @param ownTruck 以“公司拖头”实体承载的条件参数实体
     * @return 符合条件的“公司拖头”实体记录条数
     * @see
     */
     long queryOwnTractorsRecordCountByCondition(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“公司拖头”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author panGuangjun
     * @date panGuangjun 下午2:32:27
     * @param ownTruck 以“公司拖头”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司拖头”实体列表
     * @see
     */
     List<OwnTruckEntity> queryOwnTractorsListByCondition(OwnTruckEntity ownTruck, int offset, int limit);

    /**
     * <p>根据条件有选择的查询外请车或者公司车总数</p> 
     * @author panGuangjun
     * @date 2012-12-03下午3:51:34
     * @param ownTruck 以“公司拖头”实体承载的条件参数实体
     * @return 符合条件的“公司拖头”实体记录条数
     * @see
     */
     long queryTractorsRecordCountByCondition(OwnTruckEntity ownTruck);
    
    /**
     * <p>根据条件有选择的查询外请车或者公司车</p> 
     * @author panGuangjun
     * @date panGuangjun 下午2:32:27
     * @param ownTruck 以“公司拖头”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司拖头”实体列表
     * @see
     */
     List<TruckDto> queryTractorsListByCondition(OwnTruckEntity ownTruck, int offset, int limit);
     
     /**
 	 * 设置结果集OrgName、IsTrailer
 	 * @Title: setTractorsOrgName 
 	 * @Description: TODO(这里用一句话描述这个方法的作用) 
 	 * @param @param ownTruckList
 	 * @param @return    设定文件 
 	 * @return List<TruckDto>    返回类型 
 	 * @throws 
 	 * @user 310854-liuzhenhua
 	 */
 	public List<TruckDto> setTractorsOrgName(List<TruckDto> ownTruckList);
 	
 	/**
	 * 设置参数：orgIds、ReginName
	 * @Title: setTractorsOrgIds 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruck
	 * @param @return    设定文件 
	 * @return OwnTruckEntity    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public OwnTruckEntity setTractorsOrgIds(OwnTruckEntity ownTruck);
	
	/**
	 * 设置参数ordIds
	 * @Title: getOwnTruckEntityOrgIds 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruck
	 * @param @return    设定文件 
	 * @return OwnTruckEntity    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public OwnTruckEntity setOwnTruckEntityOrgIds(OwnTruckEntity ownTruck);
	
	/**		
	 * 设置结果集OrgName
	 * @Title: setownTruckListOrgName 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param ownTruckList
	 * @param @return    设定文件 
	 * @return List<OwnTruckEntity>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public List<OwnTruckEntity> setownTruckListOrgName(List<OwnTruckEntity> ownTruckList);
}
