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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IRegionalVehicleDao.java
 * 
 * FILE NAME        	: IRegionalVehicleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;

/**
 * 定人定区Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-10-11 下午2:29:03</p>
 * @author dp-xieyantao
 * @date 2012-10-11 下午2:29:03
 * @since
 * @version
 */
public interface IRegionalVehicleDao {
    /**
     * 新增定人定区 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:29:03
     * @param entity 车辆定区实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addRegionalVehicle(RegionVehicleEntity entity) ;
    
    /**
     * 根据code作废定人定区信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:29:03
     * @param codes
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    int deleteRegionalVehicleByCode(List<String> codes,String modifyUser);
    
    /**
     * 修改定人定区信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:29:03
     * @param entity 车辆定区实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateRegionalVehicle(RegionVehicleEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有定人定区信息 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:29:03
     * @param entity 车辆定区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<RegionVehicleInfoDto> queryRegionalVehicles(RegionVehicleEntity entity,int limit,int start);
    /**
     * @author 092020-lipengfei
     * @Description 根据虚拟编码查询定人定区，用于导出
     * @Time 2014-4-22
     * @param codes
     * @return
     */
    List<RegionVehicleEntity> getRegionVehicleEntityByBigZoonCode(String regionCode);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-11 下午2:29:03
     * @param entity 车辆定区实体
     * @return
     * @see
     */
    Long queryRecordCount(RegionVehicleEntity entity);
    
    /**
     * 根据区域编码查询定车定区绑定的车辆信息
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 上午11:10:08
     * @param regionCode 区域编码
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehiclesByCode(String regionCode);
    
    /**
     * <p>根据传入的参数查询车辆定区信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-7 下午2:08:20
     * @param vehicleNoList 车牌号列表
     * @param regionType 接送货类型：接货区、送货区
     * @param regionNature 区域类型:大区、小区
     * @return null 或 车辆定区信息列表
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehiclesByParam(List<String> vehicleNoList,String regionType,String regionNature);
    
    /**
     * <p>根据gis的区域id匹配接货小区id及车辆车牌号</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-15 下午6:16:29
     * @param gisId GIS系统小区范围ID
     * @return
     * @see
     */
    List<RegionVehicleEntity> querySmallZoneInfoByGisId(String gisId);
    
    /**
     * <p>通过传入一个车队或车队下调度组的code，查询出车队下的所有接货小区</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 上午11:58:23
     * @param vehicleOrgCode 车队code或车队下调度组的code
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehiclesByOrgCode(List<String> vehicleOrgCodes);
    
    /**
     * <p>验证车牌号与区域的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-14 上午9:19:39
     * @param vehicleNo 车牌号
     * @param regionCode 区域虚拟编码
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehicleByVehicleAndRegionCode(String vehicleNo,String regionCode);
    
    /**
     * <p>根据车牌号、接送货类型、车辆职责类型验证定车定区是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-15 下午5:02:17
     * @param vehicleNo 车牌号
     * @param regionType 接送货类型：接货区、送货区
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryInfoByVehicleNoAndRegionType(String vehicleNo, String regionType,String vehicleType);
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-23 下午2:31:22
     * @param vehicleNo 车牌号
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehicleByVehicleAndVehicleType(String vehicleNo);
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午3:30:31
     * @param vehicleNo 车牌号
     * @param vehicleType 车辆职责类型
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryInfosVehicleAndVehicleType(String vehicleNo,String vehicleType);
    
    /**
     * <p>根据多个区域的虚拟编码查询定车定区信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午8:30:34
     * @param regionCodes 区域的虚拟编码List
     * @return
     * @see
     */
    List<RegionVehicleEntity> queryRegionVehiclesByRegionCodes(List<String> regionCodes);
    /**
     * @author 092020-lipengfei
     * @Description 根据区域编码和车辆职责类型查询定车定区数量
     * @Time 2014-4-18
     * @param regionCode
     * @param vehicleType
     * @return
     */
    int queryNumByRegionCodeAndVehicleType(String regionCode,String vehicleType);
    
    /**
     * 根据大区或者小区的虚拟编码去作废定人定区的数据
     * code对应定人定区表里region_code
     * auther:wangpeng_078816
     * date:2014-6-9
     *
     */
    int deleteRegionalVehicleByRegionCode(List<String> codes,String modifyUser);
    /**
     * 
     *<p>Title: queryInfosByRegionCodeAndVihicleTypeAndRegionType</p>
     *<p>根据区域名称，区域类型，和车辆职责类型查询唯一定区车记录</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-9上午9:53:48
     * @param regionCode
     * @param regionType
     * @param vehicleType
     * @return
     */
    RegionVehicleEntity queryInfosByRegionCodeAndVihicleTypeAndRegionType(String regionCode,String regionType,String vehicleType);
    
    /**
     * @author dujunhui-187862
     * @Description 自动排单项目：根据小区编码查询对应车牌号
     * @Time 2015-5-8 下午5:00：00
     * @param codes
     * @return
     */
    List<RegionVehicleEntity> getRegionVehicleNoBySmallZoneCode(String regionCode);
    
}
