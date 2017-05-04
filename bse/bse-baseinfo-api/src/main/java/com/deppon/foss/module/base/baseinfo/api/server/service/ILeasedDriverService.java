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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILeasedDriverService.java
 * 
 * FILE NAME        	: ILeasedDriverService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;

/**
 * 用来操作交互“外请车司机”的数据库对应数据访问Service接口：SUC-211
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午11:02:41
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午11:02:41
 * @since
 * @version
 */
public interface ILeasedDriverService extends IService {

	/**
	 * <p>
	 * 新增一个“外请车司机”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:43
	 * @param leasedDriver
	 *            “外请车司机”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @see
	 */
	int addLeasedDriver(LeasedDriverEntity leasedDriver, String createUser,
			boolean ignoreNull) throws LeasedDriverException;

	/**
	 * <p>
	 * 根据“外请车司机”记录标识集合删除（物理删除）多条“外请车司机”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:42
	 * @param ids
	 *            记录唯一标识集合
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @see
	 */
	int deleteLeasedDriver(List<String> ids, String modifyUser)
			throws LeasedDriverException;

	/**
	 * <p>
	 * 修改一个“外请车司机”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:48
	 * @param leasedDriver
	 *            “外请车司机”实体
	 * @param modifyUser
	 *            修改人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @see
	 */
	int updateLeasedDriver(LeasedDriverEntity leasedDriver, String modifyUser,
			boolean ignoreNull) throws LeasedDriverException;

	/**
	 * <p>
	 * 根据“外请车司机”记录唯一标识查询出一条“外请车司机”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:45
	 * @param id
	 *            记录唯一标识
	 * @return “外请车司机”实体
	 * @see
	 */
	LeasedDriverEntity queryLeasedDriverBySelective(String id)
			throws LeasedDriverException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件一个的“外请车司机”实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-14 上午11:23:53
	 * @param leasedDriver
	 *            以“外请车司机”实体承载的条件参数实体
	 * @return 符合条件的“外请车司机”实体
	 * @throws LeasedDriverException
	 * @see
	 */
	LeasedDriverEntity queryLeasedDriverBySelective(
			LeasedDriverEntity leasedDriver) throws LeasedDriverException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:46
	 * @param leasedDriver
	 *            以“外请车司机”实体承载的条件参数实体
	 * @return 分页的Action和Service通讯封装对象
	 * @see
	 */
	List<LeasedDriverEntity> queryLeasedDriverListBySelectiveCondition(
		LeasedDriverEntity leasedDriver) throws LeasedDriverException;
	
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:46
	 * @param leasedDriver
	 *            以“外请车司机”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @see
	 */
	PaginationDto queryLeasedDriverListBySelectiveCondition(
			LeasedDriverEntity leasedDriver, int offset, int limit)
			throws LeasedDriverException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-30 下午5:40:33
	 * @param vehicleNo
	 *            外请车车牌号
	 * @return DriverAssociationDto封装了的传送对象集合
	 * @throws LeasedDriverException
	 * @see
	 */
	List<DriverAssociationDto> queryLesasedDriverAssociationDtoByVehicleNo(
			String vehicleNo) throws LeasedDriverException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-5 下午7:04:42
	 * @param driverName
	 *            司机姓名
	 * @return DriverBaseDto封装的对象集合
	 * @throws OwnDriverException
	 * @see
	 */
	List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(
			String driverName) throws LeasedDriverException;

	/**
	 * 提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-8 下午3:00:25
	 * @param vehicleNo
	 *            外请车车牌号
	 * @return DriverAssociationDto封装了的传送对象集合
	 * @throws LeasedDriverException
	 * @see
	 */
	List<DriverAssociationDto> queryLesasedDriverAssociationDtoByTruckVehicleNo(
			String vehicleNo) throws LeasedDriverException;
}
