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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/DispatchVehicleRecordEntityDao.java
 * 
 * FILE NAME        	: DispatchVehicleRecordEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchVehicleRecordEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 派车记录DAO
 * @author 038590-foss-wanghui
 * @date 2012-12-18 下午8:38:16
 */
public class DispatchVehicleRecordEntityDao extends iBatis3DaoImpl implements IDispatchVehicleRecordEntityDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.DispatchVehicleRecordEntity.";
	
	/**
	 * 
	 * 新增派车记录
	 * @param record
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:39:27
	 */
	@Override
	public int addDispatchVehicleRecord(DispatchVehicleRecordEntity record) {
		return getSqlSession().insert(NAMESPACE + "addDispatchVehicleRecord", record);
	}

	/**
	 * 根据查询处理订单任务DTO查询调度订单派车记录
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 上午10:17:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchOrderVehicleDto> queryVehicleRecordBy(
			DispatchOrderVehicleDto dto,int start,int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		dto.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "queryVehicleRecordBy",dto,rowBounds);
	}

	/**
	 * 获得派车记录总数
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:39:43
	 */
	@Override
	public Long getVehicleRecordByCount(DispatchOrderVehicleDto dto) {
		return (Long) getSqlSession().selectOne(NAMESPACE + "getVehicleRecordByCount",dto);
	}

	/**
	 * 根据派车记录ID将派车记录ispad 改为异常
	 * @param entity
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-9 下午3:25:20
	 */
	@Override
	public Integer updateIspdaByVehicleId(DispatchVehicleRecordEntity entity) {
		return getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective",entity);
	}

	/**
	 * 批量添加派车记录 
	 * @param entity
	 * 			tSrvDispatchOrderId
	 * 				调度订单id
	 * 			driverName
	 * 				司机姓名
	 * 			driverCode
	 * 				司机code
	 * 			vehicleNo
	 * 				车牌号
	 * 			deliverTime
	 * 				派车时间
	 * 			pickupRegionCode
	 * 				定人定区code
	 * 			pdaStatus
	 * 				是否使用PDA
	 * 			pickupRegionName
	 * 				定人定区名字
	 * 			orderStatus
	 * 				订单状态
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:40:49
	 */
	@Override
	public int batchAddDispatchVehicleRecord(
			List<DispatchVehicleRecordEntity> vehicleRecordEntities) {
		return getSqlSession().insert(NAMESPACE + "batchAddDispatchVehicleRecord", vehicleRecordEntities);
	}

	/**
	 * 根据订单id更新派车记录
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 038590-foss-wanghui
	 * @date 2013-2-3 上午10:56:12
	 */
	@Override
	public Integer updateVehicleRecordByOrderId(DispatchOrderVehicleDto dispatchOrderVehicleDto) {
		return getSqlSession().update(NAMESPACE + "updateVehicleRecordByOrderId", dispatchOrderVehicleDto);
	}

	/**
	 * 
	 * 不分页查询派车记录（导出用）
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-4 上午11:55:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchOrderVehicleDto> queryVehicleRecord(DispatchOrderVehicleDto dto) {
		dto.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "queryVehicleRecordBy",dto);
	}
	
	/**
	 * 根据查询小件处理订单任务DTO查询调度订单派车记录
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-7 上午10:17:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchOrderVehicleDto> queryExpressVehicleRecordBy(
			DispatchOrderVehicleDto dto,int start,int limit) {
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		RowBounds rowBounds = new RowBounds(start, limit);
		dto.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(NAMESPACE + "queryExpressVehicleRecordBy",dto,rowBounds);
	}

	/**
	 * 获得小件派车记录总数
	 * @param dto
	 * 			id
	 * 				派车记录Id
	 * 			orderId
	 * 				订单ID
	 * 			orderVehicleOrgName
	 * 				约车部门名称
	 * 			orderNo
	 * 				订单号
	 * 			deliverBeginTime
	 * 				派车时间 ==处理订单时间开始时间
	 * 			deliverEndTime
	 * 				派车时间 ==处理订单时间结束时间
	 * 			earliestPickupTime
	 * 				接货最早时间 == 用车时间
	 * 			latestPickupTime
	 * 				接货最晚时间
	 * 			orderVehicleTime
	 * 				约车时间
	 * 			driverName
	 * 				司机姓名
	 * 			vehicleNo
	 * 				车牌号
	 * 			orderNotes
	 * 				备注 ==订单备注
	 * 			pickupRegionCode
	 * 				定人定区
	 * 			orderSendStatus
	 * 				订单任务发送状态
	 * 			orderStatus
	 * 				订单任务完成状态 == 订单状态
	 * 			orderType
	 * 				订单类型
	 * 			usecarTime
	 * 				用车时间
	 * 			pdaStatus
	 * 				PDA使用状态
	 * 			deliverTime
	 * 				派车时间
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:39:43
	 */
	@Override
	public Long getExpressVehicleRecordByCount(DispatchOrderVehicleDto dto) {
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return (Long) getSqlSession().selectOne(NAMESPACE + "getExpressVehicleRecordByCount",dto);
	}
}