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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/commonselector/ICommonOwnDriverDao.java
 * 
 * FILE NAME        	: ICommonOwnDriverDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverDto;

/**
 * 公共查询选择器--公司司机DAO接口
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-10-29 下午4:11:14
 * @since
 * @version
 */
public interface ICommonOwnDriverDao {
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-10-29 下午4:24:23
	 * @param ownDriver
	 *            以“公司司机”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“公司司机”实体列表
	 */
	 List<DriverDto> queryDriverListBySelectiveCondition(
			OwnDriverEntity ownDriver, int offset, int limit);

	/**
	 * 根据条件查询符合条件的公司车辆总数
	 * 
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:01:03
	 * @retrun long
	 */
	 long queryDriverRecordByCondition(OwnDriverEntity ownDriver);
	
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-10-29 下午4:24:23
	 * @param ownDriver
	 *            以“司机”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“公司司机”实体列表
	 */
	 List<OwnDriverEntity> queryOwnDriverListBySelectiveCondition(
			OwnDriverEntity ownDriver, int offset, int limit);

	/**
	 * 根据条件查询符合条件的公司司机总数
	 * 
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:01:03
	 * @retrun long
	 */
	 long queryOwnDriverRecordByCondition(OwnDriverEntity ownDriver);
}
