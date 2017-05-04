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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/service/IOrderVehicleService.java
 * 
 *  FILE NAME     :IOrderVehicleService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.server.service
 * FILE    NAME: IOrderVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;

/**
 * 约车申请Service
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:51:34
 */
public interface IOrderVehicleService extends IService {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 
	 */
	int addOrderVehicle(OrderVehicleEntity orderVehicleEntity);

	/**
	 * 修改单个对象
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 
	 */
	int updateOrderVehicle(OrderVehicleEntity orderVehicleEntity);

	/**
	 * 查询单个对象（按主键查询）
	 * <br>------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity
	 */
	OrderVehicleEntity queryOrderVehicle(String id);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 
	 */
	List<OrderVehicleEntity> queryOrderVehicleList(OrderVehicleDto orderVehicleDto);
	
	/**
	 * 根据约车编号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderNo  约车编号
	 * @return 
	 */
	OrderVehicleEntity queryOrderVehicleByOrderNo(String orderNo);
	
	/**
	 * 查询未审核,已受理状态的约车申请
	 * <p>1.从约车申请到约车审核  {@link orderIdList} 不为null   按orderIdList查询</br>
	 *    2.左边菜单直接打开  查询当前登录部门的所有约车申请 {@link orderIdList} 为null
	 * </p>
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderIdList 约车单申请主键id
	 * @param isLoadAll   是否加载全部数据
	 * @return 
	 */
	List<OrderVehicleEntity> queryOrderVehicleListByCanAcceptedStatus(List<String> orderIdList, boolean isLoadAll);
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @param start   
	 * @param pageSize
	 * @return 
	 */
	List<OrderVehicleEntity> queryOrderVehicleForPage(OrderVehicleDto orderVehicleDto, int start, int pageSize);

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return 
	 */
	Long queryCount(OrderVehicleDto orderVehicleDto);

	/**
	 * 撤销约车申请
	 * <p>参数,状态不对 ，会抛出异常,
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:48:34
	 * @param orderVehicleApplyIdList
	 * @throws OrderVehicleStatusErrorException
	 * @throws ParameterException
	 */
	void doUndoOrderVehicleApply(List<String> orderVehicleApplyIdList);

	/**
	 * 确认车辆到达，
	 * <p>参数错误,状态不对 ，会抛出异常,
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午6:48:34
	 * @param orderVehicleApplyIdList
	 * @throws OrderVehicleStatusErrorException
	 * @throws ParameterException
	 */
	void doConfirmTo(List<String> orderVehicleApplyIdList);
	
	/**
	 * 保存操作
	 * <p>根据主键id,来区分是新增操作 还是修改操作
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午5:16:15
	 * @param orderVehicleEntity
	 * @return 
	 * @throws ParameterException
	 */
	OrderVehicleEntity saveOrderVehicle(OrderVehicleEntity orderVehicleEntity);
	
	/**
	 * 更新到已退回状态
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:09:03
	 * @param orderId 约车申请主键id
	 */
	void updateOrderVehicleApplyForReturnStatus(String orderId);
	
	/**
	 * 更新到已拒绝状态
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:09:03
	 * @param orderId 约车申请主键id
	 */
	void updateOrderVehicleApplyForDismissStatus(String orderId);
	
	/**
	 * 更新到已受理(通过)状态
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:09:03
	 * @param orderId 约车申请主键id
	 */
	void updateOrderVehicleApplyForAcceptedStatus(String orderId);
	
	/**
	 * 更新约车申请状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-1 下午3:28:29
	 * @param orderNo 约车申请单号
	 * @param status  状态 接送那边的状态
	 * @param returnReason 退回原因
	 * @return 受影响的行数
	 */
	int updateOrderVehicleApplyStatusByOrderNo(String orderNo, String status, String returnReason);

	/**
	 * 非集中区域的转货约车，以及送货约车、偏线约车，需要处理车辆归还业务
	 * @param orderNoList: 页面传入的约车编号列表
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-4 下午5:10:29
	 */
	void doOrderVehicleGiveBack(List<String> orderNoList);

	/**
	 * 验证当前用户是否与所选顶级车队匹配
	 * @param motorcadeCode: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-4-8 下午3:56:42
	 */
	void checkTopFleet(String motorcadeCode);

	/**
	 *  尝试通过车牌号在短途排班表中获取司机信息
	 * @param vehicleNo: 
	 *
	 * @author foss-wuyingjie
	 * @return 
	 * @date 2013-4-8 下午5:04:02
	 */
	SimpleTruckScheduleDto queryDriverInfoInShortPlan(String vehicleNo);
}