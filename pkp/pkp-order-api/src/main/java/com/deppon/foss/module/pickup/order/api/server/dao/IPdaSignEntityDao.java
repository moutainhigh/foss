/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/dao/IPdaSignEntityDao.java
 * 
 * FILE NAME        	: IPdaSignEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignOrSchedulingDto;

/**
 * PDA签到Dao
 * @author 038590-foss-wanghui
 * @date 2012-11-13 上午8:15:22
 */
public interface IPdaSignEntityDao {
    
	/**
	 * 插入PDA签到实体
	 * @param record
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午9:13:44
	 */
	int insertSelective(PdaSignEntity record);
	/**
	 * 
	 * 根据主键更新PDA签到实体
	 * @param record
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:54:42
	 */
	int updateByPrimaryKeySelective(PdaSignEntity record);
	
    /** 
	 * 根据绑定状态获得PDA签到信息
	 * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
	 * @author 038590-foss-wanghui
	 * @date 2012-10-29 下午6:14:23
	 * @see com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao#querySign(java.lang.String)
	 */
    List<OwnTruckDto> querySign(OwnTruckConditionDto ownTruckConditionDto);
    
    /**
     * 
     * 查询车辆和司机是否已绑定
     * @param record
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
     * @author 038590-foss-wanghui
     * @date 2012-11-13 下午2:59:04
     */
    List<PdaSignEntity> querySignedDV(PdaSignEntity pdaSignEntity);
    
    /**
     * 
     * 根据司机编码查询PDA签到信息
     * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
     * @author 038590-foss-wanghui
     * @date 2012-11-16 下午3:51:35
     */
    OwnTruckDto querySignByDriverCode(OwnTruckConditionDto ownTruckConditionDto);
    
    /**
     * 
     * 根据车牌号、车型和车辆组别查询车辆
     * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
     * @author 038590-foss-wanghui
     * @date 2012-11-16 下午4:13:48
     */
    List<OwnTruckSignOrSchedulingDto> queryOwnTruck(OwnTruckConditionDto ownTruckConditionDto, int start, int limit);
    /**
     * 
     * 根据车牌号、车型和车辆组别查询车辆数量
     * @param ownTruckConditionDto
     * 			vehicleNo
     * 				车牌号
     * 			vehicleType
     * 				车型
     * 			serviceFleetCode
     * 				接送货车队
     * 			driverCode
     * 				司机编码
     * 			regionType
     * 				区域类型（接货or送货）
     * 			regionNature
     * 				区域大小
     * 			active
     * 				是否激活
     * 			bundleStatus
     * 				绑定状态
     * 			schedulingType
     * 				排班类型
     * 			schedulingStatus
     * 				排班状态
     * 			schedulingPlanType
     * 				计划状态
     * 			departPlanType
     * 				物流班车类型
     * 			orgCode
     * 				组织code（营业部或车队）
     * 			departPlanStatus
     * 				发车计划状态
     * @author 038590-foss-wanghui
     * @date 2012-11-16 下午4:13:48
     */
    Long queryOwnTruckCount(OwnTruckConditionDto ownTruckConditionDto);
    /**
     * 
     * 根据车牌号查询签到表信息
     * @param vehicleNo
     * 			车牌号
     * @author 038590-foss-wanghui
     * @date 2012-11-17 下午5:28:15
     */
    PdaSignEntity querySignByVehicleNo(PdaSignEntity pdaSign);
    
    /**
     * 根据车牌号、设备号、司机工号查询是否签到
     * @param record
	 * 			deviceNo
	 * 				设备号
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机编码
	 * 			vehicleNo
	 * 				车牌号
	 * 			createTime
	 * 				创建时间
	 * 			unbundler
	 * 				解绑人
	 * 			unbundlerCode
	 * 				解绑人编码
	 * 			unbundleReason
	 * 				解绑原因
	 * 			unbundleTime
	 * 				解绑时间
	 * 			status
	 * 				状态
	 * 			userType
	 * 				用户类型
     * @author 097972-foss-dengtingting
     * @date 2012-11-27 上午9:56:22
     */
    Integer querySignCountByDV(PdaSignEntity entity);
    
    /**
     * 根据条件查询PDA签到信息
     * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
     * @author 097972-foss-dengtingting
     * @date 2012-11-19 下午3:05:00
     */
    List<PdaSignDto> querySignByDVByPage(PdaSignDto dto, int start, int limit);
    
    /**
     * 根据条件查询PDA签到信息
     * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
     * @author 097972-foss-dengtingting
     * @date 2012-11-19 下午3:05:00
     */
    List<PdaSignDto> querySignByDV(PdaSignDto dto);
    
    /**
     * 根据司机工号、车牌号更新签到状态
     * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
     * @author 097972-foss-dengtingting
     * @date 2012-11-27 下午7:28:12
     */
    Integer updateStatusByDv(PdaSignDto pdaSignDto);
    /**
     * 根据司机工号、车牌号查询签到信息
     * @date 2014-07-16 gcl AUTO-163
     */
	String querySignIdByDV(PdaSignDto pdaSignDto);
    /**
     * 
     * 自动更新解除司机签到
     * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
     * @author 038590-foss-wanghui
     * @date 2013-3-15 上午10:20:50
     */
    void updateUnbundle(PdaSignDto pdaSignDto);
    
    /**
     * 
     * 查询签到信息的总条数
     * 
     * @author 038590-foss-wanghui
     * @date 2013-4-2 下午3:20:53
     */
    Long querySignedInfoCount(PdaSignDto condition);
    /**
     * 查询可用快递员
     * @param ownTruckConditionDto
     * @return
     */
	List<OwnTruckDto> queryUsedUser(OwnTruckConditionDto ownTruckConditionDto);
	  /**
     * 查询可用快递员
     * @param ownTruckConditionDto
     * @return
     */
	List<OwnTruckDto> queryAllUser(OwnTruckConditionDto ownTruckConditionDto, int start, int limit);
	Long queryAllUserCount(OwnTruckConditionDto ownTruckConditionDto);
	/**
     * 根据条件查询PDA签到信息
     * @param dto
     * 			empPhone
     * 				司机手机
     * 			regionName
     * 				车辆区域
     * 			signBeginTime
     * 				签到开始时间
     * 			signEndTime
     * 				签到结束时间
     * 			originStatus
     * 				原状态
     * 			active
     * 				是否有效
     * @author 097972-foss-dengtingting
     * @date 2012-11-19 下午3:05:00
     */
    List<PdaSignDto> queryExpressSignByDVByPage(PdaSignDto dto, int start, int limit);
	/**
     * 
     * 查询快递员签到信息的总条数
     * 
     * @author 038590-foss-wanghui
     * @date 2013-4-2 下午3:20:53
     */
    Long queryExpressSignedInfoCount(PdaSignDto condition);
    
    /**
     * 
     * @Title: queryExpressDriverByDistirct 
     * @Description: 查询同区域下所有可用快递员
     * @param @param driverCode
     * @param @return    设定文件 
     * @return List<OwnTruckDto>    返回类型 
     * @throws
     */
    List<OwnTruckDto> queryExpressDriverByDistirct(String driverCode);
    /**
     * @Title: queryExpressDriverBySmallZone 
     * @Description: gcl 14.7.15 查询收派小区下所有可用快递员
     * @param @param smallZone
     * @param @return    设定文件 
     * @return List<OwnTruckDto>    返回类型 
     * @throws
     */
	List<OwnTruckDto> queryExpressDriverBySmallZone(String smallZone);
    
    /**
     * 
     * @Title: queryExpressVehicleNoByCourier 
     * @Description: 根据快递员工号查询快递员对应的车牌号
     * @param @param driverCode
     * @param @return    设定文件 
     * @return OwnTruckDto    返回类型 
     * @throws
     */
    OwnTruckDto queryExpressVehicleNoByCourier(String driverCode);
    /**
	 * 根据司机工号获得司机电话
	 * 14.8.13 gcl AUTO-224
	 */
    String queryPhoneByDriverCode(String driverCode);
    /**
     * 
     * @Title: queryExpressDriverBySignDto 
     * @Description: 查询出对应签到快递员集合信息
     * @param @param courierQueryDto
     * @param @return    设定文件 
     * @return List<CourierSignDto>    返回类型 
     * @throws
     */
    List<CourierSignDto> queryExpressDriverBySignDto(CourierQueryDto courierQueryDto);
    /**
     * 
     * @Title: selectByPrimaryKey 
     * @Description: 查询出PDA签到
     */
    public PdaSignEntity selectByPrimaryKey(String id);
    
    /**
     * 
     * @Title: queryDriverBySignDay 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OwnTruckSignDto>    返回类型 
     * @throws
     */
    public List<OwnTruckSignDto> queryDriverBySignDay(DriverQueryDto driverQueryDto ,int start, int limit);
    
    /**
     * 
     * @Title: queryDriverBySignDay 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return Long    返回类型 
     * @throws
     */
    public Long queryDriverBySignDayCount(DriverQueryDto driverQueryDto);
    
    /**
     * 根据条件查询签到数据
     * querySignByDV: <br/>
     * 
     * Date:2014-7-18下午12:30:18
     * @author 157229-zxy
     * @param queryParam
     * @return
     * @since JDK 1.6
     */
    public PdaSignEntity querySignEntityByDV(PdaSignEntity queryParam);
    
    /**
     * 根据条件查询签到数据
     * querySalesPartmentSignedInfoCount: <br/>
     * 
     * Date:2016-7-18下午12:30:18
     * @author 273279-liding
     * @param queryParam
     * @return
     * @since JDK 1.6
     */
    public Long querySalesPartmentSignedInfoCount(PdaSignDto dto);
    /**
     * 根据条件查询PDA签到信息
     * 
     * Date:2016-7-18下午12:30:18
     * @author 273279-liding
     * @param queryParam
     * @return
     * @since JDK 1.6
     */
	public List<PdaSignDto> querySalesPartmentSignByDVByPage(PdaSignDto dto,
			int start, int limit);
	
	/**
	 * 提供给结算的接口
	 * 根据工号，绑定状态，用户类型去取的车牌号(可能为空)
	 * 273279-liding
	 */
	public String queryVehicleNoByContidion(String driverCode);
	/**
	 * 自动解除POS机绑定
	 * 273279-liding add for NCI
	 */
	public void updatePosUnbundle(PdaSignDto pdaSignDto);
	
	/**
	 * 根据运单号查询实际承运表结清状态
	 * @param map
	 * @return
	 */
	String querySettleStatus(Map<String, String> map);
	/**
	 * add by 329757 07-09
	 * 查询外请车签到信息
	 * @param deviceNo
	 * @param vehicleNo
	 * @param bundle
	 * @return
	 */
	List<PdaSignEntity> querySignedByVehicleNo(PdaSignEntity queryParam);
	/**
	 * 查询出外请车签到信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-13 下午4:52:14
	* @param @param dto
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	Long querySignedInfoCountForLeased(PdaSignDto dto);
	/**
	 * 查询出外请车签到信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-13 下午5:24:06
	* @param @param dto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件 
	* @return List<PdaSignDto>    返回类型 
	* @throws
	 */
	List<PdaSignDto> querySignByDVByPageForLeased(PdaSignDto dto, int start,
			int limit);
}