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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOwnVanService.java
 * 
 * FILE NAME        	: IOwnVanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVanException;

/**
 * 用来操作交互“公司车辆-厢式车”的数据库对应数据访问Service接口实现类：SUC-785
 * <p>
 * 需要版本控制
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-19 上午10:59:13
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-19 上午10:59:13
 * @since
 * @version
 */
public interface IOwnVanService extends IService, IOwnVehicleService {

	/**
	 * <p>
	 * 新增一个“公司厢式车”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:37
	 * @param ownTruck
	 *            “公司厢式车”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addOwnVan(OwnTruckEntity ownTruck, String createUser, boolean ignoreNull)
			throws OwnVanException;

	/**
	 * <p>
	 * 根据“公司厢式车”记录唯一标识作废（逻辑删除）一条“公司厢式车”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:40
	 * @param ownTruck
	 *            “公司厢式车”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteOwnVan(OwnTruckEntity ownTruck, String modifyUser)
			throws OwnVanException;

	/**
	 * <p>
	 * 修改一个“公司厢式车”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:43
	 * @param ownTruck
	 *            “公司厢式车”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateOwnVan(OwnTruckEntity ownTruck, String modifyUser)
			throws OwnVanException;
}
