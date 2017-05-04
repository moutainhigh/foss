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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/dao/IFuelConsumptionDao.java
 *  
 *  FILE NAME          :IFuelConsumptionDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

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
public interface IFuelConsumptionDao {
	
	/**
	 * 新增油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-24 下午8:59:27
	 */
	int addFuelConsumption(FuelConsumptionEntity fuelConsumptionEntity);
	
	/**
	 * 修改油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-24 下午8:59:27
	 */
	int updateFuelConsumption(FuelConsumptionEntity fuelConsumptionEntity);
	
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
	 * 添加油耗明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午4:12:15
	 */
	int addFuelDetail(List<FuelDetailEntity> fuelDetailList);
	
	/**
	 * @author niuly
	 * @date 2013-11-20下午2:07:54
	 * @function 添加路桥费信息
	 * @param fuelRoadTollList
	 * @return
	 */
	int addFuelRoadToll(List<FuelRoadTollEntity> fuelRoadTollList);
	
	/**
	 * @author niuly
	 * @date 2013-11-20下午2:08:37
	 * @function 添加发车信息
	 * @param fuelDepartureList
	 * @return
	 */
	int addDeparture(List<FuelDepartureEntity> fuelDepartureList);
	
	/**
	 * @author niuly
	 * @date 2013-11-20下午2:09:15
	 * @function 添加车辆信息
	 * @param fuelVehicleEntity
	 * @return
	 */
	int addVehicle(FuelVehicleEntity fuelVehicleEntity);
	
	/**
	 * 根据ID查询油耗信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午6:13:02
	 */
	List<FuelConsumptionEntity> queryFuelConsumptionById(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 根据ID查询油耗明细信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午6:13:02
	 */
	List<FuelDetailEntity> queryFuelDetailById(FuelConsumptionDto fuelConsumptionDto);
	
	/**
	 * 删除加油明细
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-25 下午8:19:59
	 */
	int deleteFuelDetail(String id);
	
	
	/**
	 * 查询需要导出的车辆油耗信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-27 下午3:24:43
	 * @param fuelConsumptionDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<FuelConsonptionForExportDto>	queryFuelConsumptionForExport(FuelConsumptionDto fuelConsumptionDto, int start, int limit);

	/**
	 * @author niuly
	 * @date 2013-11-21下午1:54:26
	 * @function 根据发车记录id查询加油信息
	 * @param id
	 * @return
	 */
	List<FuelDetailEntity> queryFuelDetailById(String id);

	/**
	 * @author niuly
	 * @date 2013-11-21下午3:27:55
	 * @function 根据发车记录id查询路桥费信息
	 * @param id
	 * @return
	 */
	List<FuelRoadTollEntity> queryFuelRoadTollById(String id);

	/**
	 * @author niuly
	 * @date 2013-11-22下午7:40:23
	 * @function 删除路桥费信息
	 * @param id
	 */
	int deleteFuelRoadToll(String id);

	/**
	 * @author niuly
	 * @date 2013-11-22下午7:40:33
	 * @function 查询车辆对应的发车条数
	 * @param vehicleId
	 * @return
	 */
	Long queryDepartureCount(String vehicleId);

	/**
	 * @author niuly
	 * @date 2013-11-22下午7:40:40
	 * @function 删除车辆信息
	 * @param vehicleId
	 */
	int deleteVehicle(String vehicleId);

	/**
	 * @author niuly
	 * @date 2013-11-22下午7:40:46
	 * @function 删除发车信息
	 * @param id
	 * @return
	 */
	int deleteDeparture(String id);

	/**
	 * @author niuly
	 * @date 2013-11-23上午11:36:29
	 * @function 更新加油信息
	 * @param fuelDetailList
	 */
	int updateFuelDetail(List<FuelDetailEntity> fuelDetailList);

	/**
	 * @author niuly
	 * @date 2013-11-23上午11:36:33
	 * @function 更新路桥费信息
	 * @param fuelRoadTollList
	 */
	int updateRoadToll(List<FuelRoadTollEntity> fuelRoadTollList);

	/**
	 * @author niuly
	 * @date 2013-11-23上午11:36:41
	 * @function 更新发车信息
	 * @param fuelConsumptionDto
	 */
	int updateDeparture(FuelConsumptionDto fuelConsumptionDto);

	/**
	 * @author niuly
	 * @date 2013-11-23上午11:36:46
	 * @function 更新车辆信息
	 * @param fuelVehicleEntity
	 */
	int updateVehicle(FuelVehicleEntity fuelVehicleEntity);

	/**
	 * @author niuly
	 * @date 2013-11-23下午2:20:21
	 * @function 根据id删除加油明细
	 * @param detailDeleteIdList
	 */
	int deleteFuelDetailByIds(List<String> detailDeleteIdList);

	/**
	 * @author niuly
	 * @date 2013-11-23下午2:20:41
	 * @function 根据id删除路桥明细
	 * @param roadTollDeleteIdList
	 */
	int deleteRoadTollByIds(List<String> roadTollDeleteIdList);

	/**
	 * @author niuly
	 * @date 2013-11-26下午8:42:11
	 * @function 根据查询条件查询信息总计
	 * @param fuelConsumptionDto
	 * @return
	 */
	FuelConsumptionTotalDto queryTotalInfo(FuelConsumptionDto fuelConsumptionDto);

	/**
	 * @author niuly
	 * @date 2013-11-28上午9:04:05
	 * @function 批量导入油耗标准
	 * @param excelDtos
	 * @param days 每个月的天数
	 * @return int
	 */
	int batchImportFuleList(List<FuelStandardExcelDto> excelDtos,int days);

	/**
	 * @author niuly
	 * @date 2013-11-28下午3:13:51
	 * @function 查询油耗标准
	 * @param fuelStandardExcelDto
	 * @param start
	 * @param limit
	 * @return
	 */
	List<FuelStandardExcelDto> queryFuelStandard(FuelStandardExcelDto fuelStandardExcelDto, int start, int limit);

	/**
	 * @author niuly
	 * @date 2013-11-28下午3:13:57
	 * @function 查询油耗标准总条数，用于分页
	 * @param fuelStandardExcelDto
	 * @return
	 */
	Long getFuelStandardCount(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:35:26
	 * @function 删除油耗标准
	 * @param id
	 * @return
	 */
	int deleteFuelStandard(String id);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:35:34
	 * @function 更新油耗标准
	 * @param fuelStandardExcelDto
	 * @return
	 */
	int updateFuelStandard(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-28下午5:52:24
	 * @function 新增油耗标准
	 * @param fuelStandardExcelDto
	 * @return
	 */
	int addFuelStandard(FuelStandardExcelDto fuelStandardExcelDto);

	/**
	 * @author niuly
	 * @date 2013-11-30下午12:23:01
	 * @function 删除油耗标准
	 * @param dto
	 */
	int delFuelStdByDateAndVehiNo(FuelStandardExcelDto dto);

	/**
	 * @author niuly
	 * @date 2013-11-30下午12:58:15
	 * @function 查询该月车牌号记录条数
	 * @param dto
	 * @return
	 */
	Long queryFuelStandardCount(FuelStandardExcelDto dto);

	/**
	 * @author niuly
	 * @date 2013-12-4下午7:10:00
	 * @function 根据车牌号和发车日期查询油耗标准值
	 * @param fuelConsumptionDto
	 * @return
	 */
	List<BigDecimal> queryFuelStandardValue(FuelConsumptionDto fuelConsumptionDto);
	
}