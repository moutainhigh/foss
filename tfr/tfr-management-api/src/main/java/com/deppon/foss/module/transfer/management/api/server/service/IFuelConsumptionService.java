/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IFuelConsumptionService.java
 *  
 *  FILE NAME          :IFuelConsumptionService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelConsumptionEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDepartureEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDetailEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelRoadTollEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelVehicleEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelConsonptionForExportDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelConsumptionDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelConsumptionTotalDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelStandardExcelDto;

/**
 * 车辆油耗相关操作
 * @author foss-liuxue(for IBM)
 * @date 2012-12-20 下午3:01:09
 */
public interface IFuelConsumptionService extends IService {

	/**
	 * 根据车牌号获取车辆信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-21 下午4:12:26
	 */
	FuelVehicleEntity queryVehicleInfo(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 新增或修改油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-24 下午8:59:27
	 */
	int saveOrUpdateFuelConsumption(FuelVehicleEntity fuelVehicleEntity,List<FuelDepartureEntity> fuelDepartureList);
	
	/**
	 * 查询油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-25 下午8:02:32
	 */
	List<FuelConsumptionDto> queryFuelConsumption(FuelConsumptionDto fuelConsumptionDto, int start, int limit);
	
	/**
	 * 查询油耗信息总条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-25 下午8:02:32
	 */
	Long getCount(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 删除油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-25 下午8:19:59
	 */
	int deleteFuelConsumption(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 点击编辑/查看时load油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午6:15:59
	 */
	FuelConsumptionEntity loadFuelConsumption(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 导出车辆油耗信息
	 * @author 099995-foss-wujiangtao
	 * @param exportExcelMaxCounts 
	 * @param i 
	 * @date 2012-12-27 下午3:42:15
	 * @return
	 */
	List<FuelConsonptionForExportDto> queryFuelConsumptionForExport(FuelConsumptionDto dto, int i, int exportExcelMaxCounts);

	/**
	 * @author niuly
	 * @date 2013-11-21下午1:50:36
	 * @function 根据发车记录id查询加油信息
	 * @param id
	 * @return
	 */
	List<FuelDetailEntity> queryFuelDetailById(String id);

	/**
	 * @author niuly
	 * @date 2013-11-21下午3:26:52
	 * @function 根据发车记录id查询路桥费信息
	 * @param id
	 * @return
	 */
	List<FuelRoadTollEntity> queryFuelRoadTollById(String id);

	/**
	 * @author niuly
	 * @date 2013-11-23上午11:23:02
	 * @function 更新记录
	 * @param fuelVehicleEntity
	 * @param fuelConsumptionDto
	 * @param fuelDetailList
	 * @param fuelRoadTollList
	 */
	void updateFuelConsumption(FuelVehicleEntity fuelVehicleEntity,	FuelConsumptionDto fuelConsumptionDto,List<FuelDetailEntity> fuelDetailList,List<FuelRoadTollEntity> fuelRoadTollList);

	/**
	 * @author niuly
	 * @date 2013-11-26下午7:45:27
	 * @function 根据查询条件查询信息总计
	 * @param fuelConsumptionDto
	 * @return
	 */
	FuelConsumptionTotalDto queryTotalInfo(FuelConsumptionDto fuelConsumptionDto);

	/**
	 * @author niuly
	 * @date 2013-11-27上午9:36:14
	 * @function 根据配载车次号查询配载信息
	 * @param assemblyNo
	 * @return
	 */
	FuelConsumptionDto queryAssemblyList(String assemblyNo);

	/**
	 * @author niuly
	 * @date 2013-11-28上午8:40:07
	 * @function  批量导入油耗标准
	 * @param excelDtos
	 */
	int batchImportFuleList(List<FuelStandardExcelDto> excelDtos);

	/**
	 * @author niuly
	 * @date 2013-11-28下午3:06:55
	 * @function 分页查询油耗标准
	 * @param fuelStandardEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<FuelStandardExcelDto> queryFuelStandard(FuelStandardExcelDto fuelStandardExcelDto, int start, int limit);

	/**
	 * @author niuly
	 * @date 2013-11-28下午3:07:12
	 * @function 查询油耗标准总条数，用于分页
	 * @param fuelStandardEntity
	 * @return
	 */
	Long getFuelStandardCount(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:32:36
	 * @function 删除油耗标准
	 * @param id
	 */
	int deleteFuelStandard(String id);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:33:13
	 * @function  更新油耗标准
	 * @param fuelStandardExcelDto
	 */
	int updateFuelStandard(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:49:35
	 * @function 新增油耗标准
	 * @param fuelStandardExcelDto
	 */
	//int addFuelStandard(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-30下午12:56:52
	 * @function 查询该月车牌号记录条数
	 * @param fuelStandardExcelDto
	 * @return
	 */
	Long queryFuelStandardCount(FuelStandardExcelDto dto);

	/**
	 * @author niuly
	 * @date 2013-12-4下午7:03:53
	 * @function 根据车牌号和发车日期查询油耗标准值
	 * @param fuelConsumptionDto
	 * @return
	 */
	BigDecimal queryFuelStandardValue(FuelConsumptionDto fuelConsumptionDto);

	/**
	 * @author niuly
	 * @date 2013-12-5下午2:21:22
	 * @function 是否车队组织
	 * @return
	 */
	String isTransDepartment();

}