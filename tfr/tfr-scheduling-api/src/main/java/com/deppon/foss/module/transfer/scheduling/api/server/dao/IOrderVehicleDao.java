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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/server/dao/IOrderVehicleDao.java
 * 
 *  FILE NAME     :IOrderVehicleDao.java
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
package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;

/**
 * 约车数据访问
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:51:34
 */
public interface IOrderVehicleDao {

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 受影响的行数
	 */
	int addOrderVehicle(OrderVehicleEntity orderVehicleEntity);

	/**
	 * 修改单个对象
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 受影响的行数
	 */
	int updateOrderVehicle(OrderVehicleEntity orderVehicleEntity);

	/**
	 * 查询单个对象（按主键查询）
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity
	 */
	OrderVehicleEntity queryOrderVehicle(String id);
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return 
	 */
	List<OrderVehicleEntity> queryOrderVehicleList(OrderVehicleDto orderVehicleDto);
	
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
	 * @return 总行数
	 */
	Long queryCount(OrderVehicleDto orderVehicleDto);

	/**
	 * 更新约车申请状态
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleApplyIdList 要修改的List
	 * @param status				      状态
	 * @return 受影响的行数
	 */
	int updateOrderVehicleApplyStatus(List<String> orderVehicleApplyIdList, String status);
	/**
	 * 更新约车申请状态
	 * @author 332153-foss-zm
	 * @date 2016年11月22日14:19:42
	 * @param parameterMap
	 * @return 
	 */
	int updateOrderVehicleApplyStatus(Map<String, Object> parameterMap);

	/**
	 * 根据id集合查询
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午12:29:54
	 * @param orderVehicleApplyIdList 
	 * @return
	 */
	List<OrderVehicleEntity> queryOrderVehicleListByIds(List<String> orderVehicleApplyIdList);

	/**
	 * 查询审核受理页面的约车信息
	 * @param orderVehicleDto
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-4-8 上午10:35:44
	 */
	List<OrderVehicleEntity> queryAuditOrderVehicleList(OrderVehicleDto orderVehicleDto);
}