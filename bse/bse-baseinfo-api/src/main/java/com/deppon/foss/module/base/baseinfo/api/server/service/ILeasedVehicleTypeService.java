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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILeasedVehicleTypeService.java
 * 
 * FILE NAME        	: ILeasedVehicleTypeService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;

/**
 * 用来操作交互“车辆车型”的数据库对应数据访问Service接口：SUC-109
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-18 上午8:46:22
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-18 上午8:46:22
 * @since
 * @version
 */
public interface ILeasedVehicleTypeService extends IService {

	/**
	 * <p>
	 * 新增一个“车辆车型”实体入库
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:24
	 * @param vehicleType
	 *            “车辆车型”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addLeasedVehicleType(VehicleTypeEntity vehicleType, String createUser,
			boolean ignoreNull) throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 根据“车辆车型”记录唯一标识作废（逻辑删除）一条“车辆车型”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:23
	 * @param id
	 *            记录唯一标识
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteLeasedVehicleType(String id, String modifyUser)
			throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 修改一个“车辆车型”实体入库
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:26
	 * @param vehicleType
	 *            “车辆车型”实体
	 * @param modifyUser
	 *            修改人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateLeasedVehicleType(VehicleTypeEntity vehicleType,
			String modifyUser, boolean ignoreNull)
			throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“车辆车型”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:29
	 * @param vehicleType
	 *            以“车辆车型”实体承载的条件参数实体
	 * @return 符合条件的“车辆车型”实体列表
	 * @throws LeasedVehicleTypeException
	 * @see
	 */
	VehicleTypeEntity queryLeasedVehicleType(VehicleTypeEntity vehicleType)
			throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 根据“车辆车型”记录唯一标识查询出一条“车辆车型”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:28
	 * @param id
	 *            记录唯一标识
	 * @return “车辆车型”实体
	 * @see
	 */
	VehicleTypeEntity queryLeasedVehicleType(String id)
			throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 根据“车辆车型”编码查询出一条“车辆车型”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:28
	 * @param vehicleLengthCode
	 *            车辆车长编码
	 * @return “车辆车型”实体
	 * @see
	 */
	VehicleTypeEntity queryLeasedVehicleTypeByVehicleLengthCode(
			String vehicleLengthCode) throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 获取当前系统中此对象最大的序列号
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-12-12 上午11:10:24
	 * @return 当前最大的序列号
	 * @see
	 */
	BigDecimal queryLeasedVehicleTypeMaxSeq() throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 根据条件（分页模糊）有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-18 上午8:46:29
	 * @param vehicleType
	 *            以“车辆车型”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“车辆车型”实体列表
	 * @see
	 */
	PaginationDto queryLeasedVehicleTypeListBySelectiveCondition(
			VehicleTypeEntity vehicleType, int offset, int limit)
			throws LeasedVehicleTypeException;

	/**
	 * <p>
	 * 查询车型编码和序号的对应关系
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Feb 27, 2013 4:17:39 PM
	 * @return
	 * @see
	 */
	Map<String, Integer> queryVehicleCodeSeqMapping()
			throws LeasedVehicleTypeException;

	/**
	 * 
	 * 查询车新信息，并对车型长度相同的信息做去重操作
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-6 下午3:19:55
	 */
	List<VehicleTypeEntity> queryDistinctLeasedVehicleTypeListBySelectiveCondition(
			VehicleTypeEntity vehicleType, int offset, int limit)
			throws LeasedVehicleTypeException;
}
