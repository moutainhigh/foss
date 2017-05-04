/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/PdaSignEntityDao.java
 * 
 * FILE NAME        	: PdaSignEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DriverQueryDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckSignOrSchedulingDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDA签到Dao
 * @author 038590-foss-wanghui
 * @date 2012-11-13 上午8:15:22
 */
@SuppressWarnings("unchecked")
public class PdaSignEntityDao extends iBatis3DaoImpl implements IPdaSignEntityDao {

	public static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity.";

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
	@Override
	public int insertSelective(PdaSignEntity record) {
		return getSqlSession().insert(NAMESPACE +"insertSelective",record);
	}
	
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
	@Override
	public int updateByPrimaryKeySelective(PdaSignEntity record) {
		return getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective",record);
	}
	
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
	@Override
	public List<OwnTruckDto> querySign(OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "querySign", ownTruckConditionDto);
	}

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
	@Override
	public List<PdaSignEntity> querySignedDV(PdaSignEntity pdaSignEntity) {
		return  getSqlSession().selectList(NAMESPACE + "querySignedDV", pdaSignEntity);
	}

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
	@Override
	public OwnTruckDto querySignByDriverCode(OwnTruckConditionDto ownTruckConditionDto) {
		return (OwnTruckDto) getSqlSession().selectOne(NAMESPACE + "querySignByDriverCode", ownTruckConditionDto);
	}

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
	@Override
	public List<OwnTruckSignOrSchedulingDto> queryOwnTruck(
			OwnTruckConditionDto ownTruckConditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryOwnTruck", ownTruckConditionDto, rowBounds);
	}
	
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
    public Long queryOwnTruckCount(OwnTruckConditionDto ownTruckConditionDto) {
    	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryOwnTruckCount", ownTruckConditionDto);
    }

	/**
     * 
     * 根据车牌号查询签到表信息
     * @param vehicleNo
     * 			车牌号
     * @author 038590-foss-wanghui
     * @date 2012-11-17 下午5:28:15
     */
	@Override
	public PdaSignEntity querySignByVehicleNo(PdaSignEntity pdaSign) {
		// 查询已签到记录
		List<PdaSignEntity> pdaSignList = getSqlSession().selectList(NAMESPACE + "querySignByVehicleNo", pdaSign);
		// 返回记录中第一个
		if(CollectionUtils.isNotEmpty(pdaSignList)){
			return pdaSignList.get(0);
		}
		return new PdaSignEntity();
	}

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
	@Override
	public List<PdaSignDto> querySignByDVByPage(PdaSignDto dto, int start, int limit) {
		dto.setActive(FossConstants.YES);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "querySignByDV", dto, rowBounds);
	}
	
	/**
	 * 根据条件查询外请车签到信息
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-13 下午5:25:51
	* @param @param dto
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	 */
	@Override
	public List<PdaSignDto> querySignByDVByPageForLeased(PdaSignDto dto,int start, int limit) {
		dto.setActive(FossConstants.YES);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "querySignByDVForLeased", dto, rowBounds);
	}


	
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
	@Override
	public Integer querySignCountByDV(PdaSignEntity entity) {
		return (Integer)getSqlSession().selectOne(NAMESPACE + "querySignCountByDV",entity);
	}

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
	@Override
	public Integer updateStatusByDv(PdaSignDto pdaSignDto) {
		return getSqlSession().update(NAMESPACE + "updateStatusByDv", pdaSignDto);
	}
	/**
     * 根据司机工号、车牌号查询签到信息
     * @date 2014-07-16 gcl AUTO-163
     */
	@Override
	public String querySignIdByDV(PdaSignDto pdaSignDto) {
		return (String)getSqlSession().selectOne(NAMESPACE + "querySignIdByDV", pdaSignDto);
	}

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
	@Override
	public List<PdaSignDto> querySignByDV(PdaSignDto dto) {
		dto.setActive(FossConstants.YES);
		return getSqlSession().selectList(NAMESPACE + "querySignByDV", dto);
	}

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
	@Override
	public void updateUnbundle(PdaSignDto pdaSignDto) {
		getSqlSession().update(NAMESPACE + "updateUnbundle", pdaSignDto);
	}
	
	/**
     * 
     * 查询签到信息的总条数
     * 
     * @author 038590-foss-wanghui
     * @date 2013-4-2 下午3:20:53
     */
	@Override
	public Long querySignedInfoCount(PdaSignDto condition) {
		condition.setActive(FossConstants.YES);
		return (Long) getSqlSession().selectOne(NAMESPACE + "querySignedInfoCount", condition);
	}
	/**
	 * 查询外请车签到信息总条数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-13 下午4:53:26
	* @param @param dto
	* @param @return    设定文件
	 */
	@Override
	public Long querySignedInfoCountForLeased(PdaSignDto dto) {
		dto.setActive(FossConstants.YES);
		return (Long) getSqlSession().selectOne(NAMESPACE + "querySignedInfoCountForLeased", dto);
	}

	
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
	@Override
	public List<OwnTruckDto> queryUsedUser(OwnTruckConditionDto ownTruckConditionDto) {
		return getSqlSession().selectList(NAMESPACE + "queryUsedUser", ownTruckConditionDto);
	}
	
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
	@Override
	public List<OwnTruckDto> queryAllUser(OwnTruckConditionDto ownTruckConditionDto, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryAllUsedUser", ownTruckConditionDto, rowBounds);
	}
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
	@Override
	public Long queryAllUserCount(OwnTruckConditionDto ownTruckConditionDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryAllUserCount", ownTruckConditionDto);
	}
	
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
	@Override
	public List<PdaSignDto> queryExpressSignByDVByPage(PdaSignDto dto, int start, int limit) {
		dto.setActive(FossConstants.YES);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "queryExpressSignByDVByPage", dto, rowBounds);
	}
	
	/**
     * 
     * 查询快递员签到信息的总条数
     * 
     * @author 038590-foss-wanghui
     * @date 2013-4-2 下午3:20:53
     */
	@Override
	public Long queryExpressSignedInfoCount(PdaSignDto condition) {
		condition.setActive(FossConstants.YES);
		return (Long) getSqlSession().selectOne(NAMESPACE + "queryExpressSignedInfoCount", condition);
	}
	
	
	/**
     * 
     * @Title: queryExpressDriverByDistirct 
     * @Description: 查询同区域下所有可用快递员
     * @param @param dirverCode
     * @param @return    设定文件 
     * @return List<OwnTruckDto>    返回类型 
     * @throws
     */
	@Override
	public List<OwnTruckDto> queryExpressDriverByDistirct(String driverCode) {
		return getSqlSession().selectList(NAMESPACE+"queryExpressDriverByDistirct",driverCode);
	}
	/**
     * @Title: queryExpressDriverBySmallZone 
     * @Description: gcl 14.7.15 查询收派小区下所有可用快递员
     * @param @param smallZone
     * @param @return    设定文件 
     * @return List<OwnTruckDto>    返回类型 
     * @throws
     */
	@Override
	public List<OwnTruckDto> queryExpressDriverBySmallZone(String smallZone) {
		return getSqlSession().selectList(NAMESPACE+"queryExpressDriverBySmallZone",smallZone);
	}
	 /**
     * 
     * @Title: queryExpressVehicleNoByCourier 
     * @Description: 根据快递员工号查询快递员对应的车牌号
     * @param @param driverCode
     * @param @return    设定文件 
     * @return OwnTruckDto    返回类型 
     * @throws
     */
	@Override
	public OwnTruckDto queryExpressVehicleNoByCourier(String driverCode) {
		List<OwnTruckDto> list = getSqlSession().selectList(NAMESPACE+"queryExpressVehicleNoByCourier",driverCode);
		if(!list.isEmpty()
				&& list.size() > 0){
			OwnTruckDto ownTruckDto = list.get(0);
			return ownTruckDto;
		}else {
			return null;
		}
	}
	/**
	 * 根据司机工号获得司机电话
	 * 14.8.13 gcl AUTO-224
	 */
	@Override
	public String queryPhoneByDriverCode(String driverCode) {
		Object obj = getSqlSession().selectOne(NAMESPACE+"queryPhoneByDriverCode",driverCode);
		if(obj!=null){
			return obj+"";
		}else {
			return null;
		}
	}
	/**
     * 
     * @Title: queryExpressDriverBySignDto 
     * @Description: 查询出对应签到快递员集合信息
     * @param @param courierQueryDto
     * @param @return    设定文件 
     * @return CourierSignDto    返回类型 
     * @throws
     */
	@Override
	public List<CourierSignDto> queryExpressDriverBySignDto(
			CourierQueryDto courierQueryDto) {
		return getSqlSession().selectList(NAMESPACE+"queryExpressDriverBySignDto",courierQueryDto);
	}
	
	/**
     * 
     * @Title: queryDriverBySignDay 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return List<OwnTruckSignDto>    返回类型 
     * @throws
     */
	@Override
	public List<OwnTruckSignDto> queryDriverBySignDay(
			DriverQueryDto driverQueryDto,int start, int limit) {
		driverQueryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryDriverBySignDay",driverQueryDto,rowBounds);
	}
	/**
     * 
     * @Title: selectByPrimaryKey 
     * @Description: 查询出PDA签到
     */
	@Override
	public PdaSignEntity selectByPrimaryKey(String id) {
		List<PdaSignEntity> l=getSqlSession().selectList(NAMESPACE+"selectByPrimaryKey",id);
		if(l!=null&&l.size()>0){
			return l.get(0);
		}
		return null;
	}
	
	/**
     * 
     * @Title: queryDriverBySignDay 
     * @Description: 查询出对应签到司机统计信息
     * @param @param driverQueryDto
     * @param @return    设定文件 
     * @return Long    返回类型 
     * @throws
     */
	public Long queryDriverBySignDayCount(DriverQueryDto driverQueryDto){
		driverQueryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryDriverBySignDayCount",driverQueryDto);
	}
	
	/**
	 * 根据条件查询签到数据
	 * TODO 简单描述该方法的实现功能（可选）.
	 * @see com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao#querySignByDV(com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity)
	 */
	@Override
	public PdaSignEntity querySignEntityByDV(PdaSignEntity queryParam){
		return (PdaSignEntity) getSqlSession().selectOne(NAMESPACE+"querySignEntityByDV",queryParam);
	}

	/**
	 * 根据条件查询营业部签到数据
	 * 273279-liding
	 */
	@Override
	public Long querySalesPartmentSignedInfoCount(PdaSignDto dto) {
		dto.setActive(FossConstants.YES);
		return (Long) getSqlSession().selectOne(NAMESPACE + "querySalesPartmentSignedInfoCount", dto);
	}
	/**
	 * 根据条件查询营业部签到数据
	 * 273279-liding
	 */
	@Override
	public List<PdaSignDto> querySalesPartmentSignByDVByPage(PdaSignDto dto,
			int start, int limit){
		dto.setActive(FossConstants.YES);
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE + "querySalesPartmentSignByDVByPage", dto, rowBounds);
	}
	
	/**
	 * 提供给结算的接口
	 * 根据工号，绑定状态，用户类型去取的车牌号(可能为空)
	 * 273279-liding
	 */
	@Override
	public String queryVehicleNoByContidion(String driverCode){
		return (String) getSqlSession().selectOne(NAMESPACE + "queryVehicleNoByContidion",driverCode);
	}
	
	/**
	 * 自动解除POS机绑定
	 * 273279-liding add for NCI
	 */
	@Override
	public void updatePosUnbundle(PdaSignDto pdaSignDto){
		getSqlSession().update(NAMESPACE + "updatePosUnbundle", pdaSignDto);
	}

	@Override
	public String querySettleStatus(Map<String, String> map) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "querySettleStatus", map);
		
	}

	/**
	 * 根据车号查询外请车信息  add by 329757
	 */
	@Override
	public List<PdaSignEntity> querySignedByVehicleNo(PdaSignEntity queryParam) {
		return  getSqlSession().selectList(NAMESPACE + "querySignedByVehicle", queryParam);
	}

	
}