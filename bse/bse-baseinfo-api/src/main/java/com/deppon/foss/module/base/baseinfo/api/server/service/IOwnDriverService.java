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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOwnDriverService.java
 * 
 * FILE NAME        	: IOwnDriverService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;

/**
 * 用来操作交互“公司司机”的数据库对应数据访问Service接口：无SUC
 * <p>
 * 需要版本控制
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午5:41:42
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午5:41:42
 * @since
 * @version
 */
public interface IOwnDriverService extends IService {

	/**
	 * <p>
	 * 新增一个“公司司机”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-29 下午4:13:32
	 * @param ownDriver
	 *            “公司司机”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addOwnDriver(OwnDriverEntity ownDriver, String createUser,
			boolean ignoreNull) throws OwnDriverException;

	/**
	 * <p>
	 * 根据“公司司机”记录唯一标识删除（物理删除）一条“公司司机”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-29 下午4:13:37
	 * @param ownDriver
	 *            “公司司机”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteOwnDriver(OwnDriverEntity ownDriver, String modifyUser)
			throws OwnDriverException;

	/**
	 * <p>
	 * 修改一个“公司司机”实体入库 （只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-29 下午4:17:50
	 * @param ownDriver
	 *            “公司司机”实体
	 * @param modifyUser
	 *            修改人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateOwnDriver(OwnDriverEntity ownDriver, String modifyUser,
			boolean ignoreNull) throws OwnDriverException;

	/**
	 * <p>
	 * 根据“公司司机”记录唯一标识查询出一条“公司司机”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-29 下午4:18:17
	 * @param id
	 *            记录唯一标识
	 * @return “公司司机”实体
	 * @see
	 */
	OwnDriverEntity queryOwnDriver(String id) throws OwnDriverException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-29 下午4:24:23
	 * @param ownDriver
	 *            以“公司司机”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“公司司机”实体列表
	 * @see
	 */
	List<OwnDriverEntity> queryOwnDriverListBySelectiveCondition(
			OwnDriverEntity ownDriver, int offset, int limit)
			throws OwnDriverException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"公司员工工号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-30 下午5:20:05
	 * @param driverCode
	 *            公司员工工号
	 * @return DriverAssociationDto封装了的传送对象
	 * @see
	 */
	DriverAssociationDto queryOwnDriverAssociationDtoByDriverCode(
			String driverCode) throws OwnDriverException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-5 下午7:04:42
	 * @param driverName
	 *            司机姓名集合
	 * @return DriverBaseDto封装的对象集合
	 * @throws OwnDriverException
	 * @see
	 */
	List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(
			String driverName) throws OwnDriverException;
}
