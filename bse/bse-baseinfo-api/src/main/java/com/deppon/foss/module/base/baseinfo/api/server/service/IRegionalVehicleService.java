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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IRegionalVehicleService.java
 * 
 * FILE NAME        	: IRegionalVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;

/**
 * 定人定区service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-11 下午5:55:31
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 下午5:55:31
 * @since
 * @version
 */
public interface IRegionalVehicleService extends IService {

	/**
	 * 新增定人定区
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:55:31
	 * @param entity
	 *            车辆定区实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addRegionalVehicle(RegionVehicleInfoDto entity);

	/**
	 * 根据code作废定人定区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:55:31
	 * @param codeStr
	 *            虚拟编码拼接字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deleteRegionalVehicleByCode(String codeStr, String modifyUser);

	/**
	 * 修改定人定区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:55:31
	 * @param entity
	 *            车辆定区实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateRegionalVehicle(RegionVehicleInfoDto entity);

	/**
	 * 根据传入对象查询符合条件所有定人定区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:55:31
	 * @param entity
	 *            车辆定区实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<RegionVehicleInfoDto> queryRegionalVehicles(RegionVehicleEntity entity,
			int limit, int start);

	/**
	 * <p>
	 * 根据传入对象查询符合条件所有定人定区封装信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-17 上午9:23:13
	 * @param entity
	 *            车辆定区实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return
	 * @see
	 */
	List<RegionVehicleInfoDto> queryRegionalVehicleInfoDtos(
			RegionVehicleInfoDto infoDto, int limit, int start);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:55:31
	 * @param entity
	 *            车辆定区实体
	 * @return
	 * @see
	 */
	Long queryRecordCount(RegionVehicleInfoDto infoDto);

	/**
	 * <p>
	 * 验证车牌号与车辆职责类型的唯一性
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-23 下午2:31:22
	 * @param vehicleNo
	 *            车牌号
	 * @return
	 * @see
	 */
	RegionVehicleEntity queryRegionVehicleByVehicleAndVehicleType(
			String vehicleNo);

	/**
	 * <p>
	 * 验证车牌号与区域的唯一性
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-14 上午9:19:39
	 * @param vehicleNo
	 *            车牌号
	 * @param regionCode
	 *            区域虚拟编码
	 * @return
	 * @see
	 */
	RegionVehicleEntity queryRegionVehicleByVehicleAndRegionCode(
			String vehicleNo, String regionCode);

	/**
	 * <p>
	 * 根据区域的虚拟编码、区域性质查询定车定区详细信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-24 下午7:43:44
	 * @param regionVirtualCode
	 *            区域的虚拟编码
	 * @param regionNature
	 *            区域性质：大区、小区 大区：DictionaryValueConstants.REGION_NATURE_BQ
	 *            小区:DictionaryValueConstants.REGION_NATURE_SQ
	 * @return
	 * @see
	 */
	RegionVehicleDto queryInfoByCode(String regionVirtualCode,
			String regionNature);

	/**
	 * <p>
	 * 根据传入的参数查询车辆定区信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-7 下午2:08:20
	 * @param vehicleNoList
	 *            车牌号列表
	 * @param regionType
	 *            接送货类型：接货区:DictionaryValueConstants.REGION_TYPE_PK、
	 *            送货区:DictionaryValueConstants.REGION_TYPE_DE
	 * @param regionNature
	 *            区域类型:大区:ComnConst.REGION_NATURE_BQ、
	 *            小区:SQComnConst.REGION_NATURE_SQ
	 * @return null 或 车辆定区信息列表
	 * @see
	 */
	List<RegionVehicleEntity> queryRegionVehiclesByParam(
			List<String> vehicleNoList, String regionType, String regionNature);

	/**
	 * @author 092020-lipengfei
	 * <p>
	 * 根据gis的区域id匹配接货小区id及车辆车牌号(应接送货要求，原有的保留，新建一个)
	 * </p>
	 * @Time 2014-5-4
	 * @param gisId
	 * @return
	 */
	RegionVehicleInfoDto querySmallZoneInfoByGisIdForNew(String gisId);
	/**
	 * <p>
	 * 根据gis的区域id匹配接货小区id及车辆车牌号
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-15 下午6:16:29
	 * @param gisId
	 *            GIS系统小区范围ID
	 * @return
	 * @see
	 */
	RegionVehicleEntity querySmallZoneInfoByGisId(String gisId);

	/**
	 * <p>
	 * 通过传入一个车队或车队下调度组的code，查询出车队下的所有接货小区
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-11-16 上午11:58:23
	 * @param vehicleOrgCode
	 *            车队code或车队下调度组的code
	 * @return
	 * @see
	 */
	List<RegionVehicleInfoDto> queryRegionVehiclesByOrgCode(String vehicleOrgCode);
	
	/**
	 * 根据查询条件导出定人定区数据
	 * @param RegionVehicleEntity entity
	 * @return
	 */
	InputStream queryExportRegionVehicles(RegionVehicleInfoDto infoDto);
	
	/**
	 * 根据传入的大区或者小区的的虚拟编码作废定人定区记录
	 *
	 * auther:wangpeng_078816
	 * date:2014-6-9
	 *
	 */
	int deleteRegionalVehicleByRegionCode(String codeStr, String modifyUser);
	
	/**
     * @author dujunhui-187862
     * @Description 自动排单项目：根据小区编码查询对应车牌号
     * @Time 2015-5-8 下午5:04：24
     * @param codes
     * @return
     */
    String getRegionVehicleNoBySmallZoneCode(String regionCode);

}
