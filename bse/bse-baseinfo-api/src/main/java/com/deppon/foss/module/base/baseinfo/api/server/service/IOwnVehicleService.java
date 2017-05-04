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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IOwnVehicleService.java
 * 
 * FILE NAME        	: IOwnVehicleService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleRegionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;

/**
 * 用来操作交互“公司车辆（厢式车、挂车、拖头）”的数据库对应数据访问Service接口：SUC-785
 * <p>
 * 需要版本控制
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-10-16 下午3:30:32
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-16 下午3:30:32
 * @since
 * @version
 */
public interface IOwnVehicleService extends IService {

	/**
	 * <p>
	 * 从LMS同步一个“公司车辆和停车计划信息”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-16 下午2:33:15
	 * @param ownTruck
	 *            “公司车辆”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int synchronousOwnVehiclePlanByLMS(OwnTruckEntity ownTruck,
			String modifyUser) throws OwnVehicleException;

	/**
	 * <p>
	 * 新增一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:37
	 * @param ownTruck
	 *            “公司车辆（厢式车、挂车、拖头）”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @param vehicleType
	 *            厢式车/挂车/拖头/null
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addOwnVehicle(OwnTruckEntity ownTruck, String createUser,
			boolean ignoreNull, String vehicleType) throws OwnVehicleException;

	/**
	 * <p>
	 * 根据“公司车辆（厢式车、挂车、拖头）”记录唯一标识作废（逻辑删除）一条“公司车辆（厢式车、挂车、拖头）”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:40
	 * @param ownTruck
	 *            “公司车辆（厢式车、挂车、拖头）”实体
	 * @param modifyUser
	 *            修改人
	 * @param vehicleType
	 *            厢式车/挂车/拖头/null
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteOwnVehicle(OwnTruckEntity ownTruck, String modifyUser,
			String vehicleType) throws OwnVehicleException;

	/**
	 * <p>
	 * 修改一个“公司车辆（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:43
	 * @param ownTruck
	 *            “公司车辆（厢式车、挂车、拖头）”实体
	 * @param modifyUser
	 *            修改人
	 * @param vehicleType
	 *            厢式车/挂车/拖头/null
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateOwnVehicle(OwnTruckEntity ownTruck, String modifyUser,
			String vehicleType) throws OwnVehicleException;

	/**
	 * <p>
	 * 根据“公司车辆（厢式车、挂车、拖头）”记录唯一标识查询出一条“公司车辆（厢式车、挂车、拖头）”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-19 下午2:32:47
	 * @param id
	 *            记录唯一标识
	 * @return “公司车辆（厢式车、挂车、拖头）”实体
	 * @see
	 */
	OwnTruckEntity queryOwnVehicleBySelective(String id)
			throws OwnVehicleException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头）”单个实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-22 上午9:28:20
	 * @param ownTruck
	 *            以“公司车辆（厢式车、挂车、拖头）”实体承载的条件参数实体
	 * @param vehicleType
	 *            车辆类型数据字典对应值代码（厢式车、挂车、拖头）
	 * @return 符合条件的“公司车辆（厢式车、挂车、拖头）”实体
	 * @throws OwnVehicleException
	 * @see
	 */
	OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity ownTruck,
			String vehicleType) throws OwnVehicleException;

	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:46
	 * @param ownTruck
	 *            以“公司车辆（厢式车、挂车、拖头）”实体承载的条件参数实体
	 * @param vehicleType
	 *            车辆类型数据字典对应值代码（厢式车、挂车、拖头）
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 分页的Action和Service通讯封装对象
	 * @see
	 */
	PaginationDto queryOwnVehicleListBySelectiveCondition(
			OwnTruckEntity ownTruck, String vehicleType, int offset, int limit)
			throws OwnVehicleException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合
	 * </p>
	 * <p>
	 * 包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-30 下午4:28:11
	 * @param vehicleNos
	 *            车牌号集合
	 * @return VehicleAssociationDto封装了传送对象集合
	 * @throws OwnVehicleException
	 * @see
	 */
	List<VehicleAssociationDto> queryOwnVehicleAssociationDtosByVehicleNos(
			String[] vehicleNos) throws OwnVehicleException;

	/**
	 * <p>
	 * 提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆、所属定人定区的相关信息DTO集合
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 上午11:34:30
	 * @param vehicleNo
	 *            车牌号
	 * @param vehicleTypeCode
	 *            车型编码
	 * @param orgCode
	 *            组织编码
	 * @param regionType
	 *            大区（ComnConst.REGION_NATURE_BQ）/小区（ComnConst.REGION_NATURE_SQ）
	 * @param goodsType
	 *            接货（DictionaryValueConstants.REGION_TYPE_PK）/送货（
	 *            DictionaryValueConstants.REGION_TYPE_DE）
	 * @return OwnVehicleRegionDto封装了传送对象集合
	 * @throws OwnVehicleException
	 * @see
	 */
	List<OwnVehicleRegionDto> queryOwnVehicleRegionDtosByCondition(
			String vehicleNo, String vehicleTypeCode, String orgCode,
			String regionType, String goodsType) throws OwnVehicleException;

	/**
	 * <p>
	 * 提供给"中转"模块使用，根据"车牌号、部门编号、车型编号、车辆状态"来获取车辆相关信息DTO集合（分页模糊）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-12-17 下午3:13:22
	 * @param orgId
	 *            部门编号
	 * @param vehicleNos
	 *            车牌号集合
	 * @param vehicleTypeCode
	 *            车型编号
	 * @param status
	 *            车辆状态（Y：可用；N：不可用）
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @param excludeVehicleType
	 *            排除挂车类型数据（数据字典常量值）
	 * @return 封装了数据集合和记录数的对象
	 * @throws OwnVehicleException
	 * @see
	 */
	PaginationDto queryVehicleAssociationDtoListPaginationByCondition(
			String orgId, List<String> vehicleNos, String vehicleTypeCode,
			String status, int offset, int limit, String excludeVehicleType)
			throws OwnVehicleException;

	/**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合(包括拖头)</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 313353-foss-qiupeng
     * @date 2016-07-07 上午09:28:11
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleAssociationDtosByVehicleNos(java.lang.String[])
     */
	List<VehicleAssociationDto> queryOwnVehicleByVehicleNosIncludeTractors(
			String[] vehicleNos) throws OwnVehicleException;

	/**
     * <p>提供给"中转"模块使用，根据"车牌号、部门编号、车型编号、车辆状态"来获取车辆相关信息DTO集合（分页模糊）(包含拖头)</p> 
     * @author 313353-foss-qiupeng
     * @date 2016-07-07 上午08:13:22
     * @param orgId 部门编号
     * @param vehicleNos 车牌号集合
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态（Y：可用；N：不可用）
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @param excludeVehicleType 排除挂车类型数据（数据字典常量值）
     * @return 封装了数据集合和记录数的对象
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryVehicleAssociationDtoListPaginationByCondition(java.lang.String, java.util.List, java.lang.String, java.lang.String, int, int, boolean)
     */
	PaginationDto queryVehicleByConditionIncludeTractors(String orgId,
			List<String> vehicleNos, String vehicleTypeCode, String status,
			int offset, int limit, String excludeVehicleType)
			throws OwnVehicleException;
}
