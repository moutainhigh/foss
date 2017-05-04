/*******************************************************************************
 * Copyright 2014 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOwnRQSVCService.java
 * 
 * FILE NAME        	: IOwnRQSVCService.java
 * 
 * AUTHOR			: FOSS综合开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2014  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnRQSVCException;

/**
 * 用来操作交互“公司车辆-骨架车”的数据库对应数据访问Service接口实现类：SUC-785 
 * @author 187862-dujunhui
 * @date 2014-6-10 下午4:00:14
 * @since
 * @version
 */
public interface IOwnRQSVCService extends IService, IOwnVehicleService {

	/**
	 * <p>
	 * 新增一个“公司骨架车”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-6-10 下午4:02:24
	 * @param ownTruck
	 *            “公司骨架车”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addOwnRQSVC(OwnTruckEntity ownTruck, String createUser, boolean ignoreNull)
			throws OwnRQSVCException;

	/**
	 * <p>
	 * 根据“公司骨架车”记录唯一标识作废（逻辑删除）一条“公司骨架车”记录
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-6-10 下午4:04:41
	 * @param ownTruck
	 *            “公司骨架车”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteOwnRQSVC(OwnTruckEntity ownTruck, String modifyUser)
			throws OwnRQSVCException;

	/**
	 * <p>
	 * 修改一个“公司骨架车”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-6-10 下午4:06:22
	 * @param ownTruck
	 *            “公司骨架车”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateOwnRQSVC(OwnTruckEntity ownTruck, String modifyUser)
			throws OwnRQSVCException;
}
