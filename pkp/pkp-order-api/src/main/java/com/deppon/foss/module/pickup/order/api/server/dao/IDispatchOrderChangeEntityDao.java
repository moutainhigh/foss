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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/server/dao/IDispatchOrderChangeEntityDao.java
 * 
 * FILE NAME        	: IDispatchOrderChangeEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.QueryOrderChangeDto;


/**
 * 调度订单变更表DAO，当新增调度订单时，同时向调度订单变更表插入记录
 * @author 038590-foss-wanghui
 * @date 2012-11-2 下午3:46:11
 */
public interface IDispatchOrderChangeEntityDao {
	
	/**
	 * 
	 * 查询变更表中零担记录
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午4:43:36
	 */
	List<DispatchOrderChangeEntity> queryChange();
	
	/**
	 * 
	 * 删除变更表中记录
	 * @param DispatchOrderChangeEntity
	 * 			changeId
	 * 				变更Id
	 * 			changeTime
	 * 				变更时间
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午4:43:52
	 */
	int deleteChange(List<DispatchOrderChangeEntity> changeList);
	
	/**
	 * 
	 * 查询待处理的订单
	 * @param DispatchOrderChangeEntity
	 * 			changeId
	 * 				变更Id
	 * 			changeTime
	 * 				变更时间
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午4:44:33
	 */
	List<DispatchOrderEntity> queryOrder(List<DispatchOrderChangeEntity> changeList);
	
	/**
	 * 
	 * 插入变更信息
	 * @param DispatchOrderChangeEntity
	 * 			changeId
	 * 				变更Id
	 * 			changeTime
	 * 				变更时间
	 * @author 038590-foss-wanghui
	 * @date 2012-11-22 下午6:16:55
	 */
	int insertChange(DispatchOrderChangeEntity dispatchOrderChangeEntity);
	
	/**
	 * 
	 * @Title: queryChangeByRownum 
	 * @Description: 查询变更表中记录
	 * @param @param dto
	 * @param @return    设定文件 
	 * @return List<DispatchOrderChangeEntity>    返回类型 
	 * @throws
	 */
	List<DispatchOrderChangeEntity> queryChangeByExpressDto(ExpressOrderDto dto);
	
	/**
	 * 
	 * @Title: deleteChangeByOrderID 
	 * @Description: 根据订单ID删除预处理订单
	 * @param @param orderId
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int deleteChangeByOrderID(String orderId);
	
	/**
	 * 
	 * @Title: batchUpdateChange 
	 * @Description: 批量更新预处理订单状态
	 * @param @param list
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	int batchUpdateChange(List<DispatchOrderChangeEntity> list);
	/**
	 * 根据条件查询（产品类型为不等与的条件）
	 * @param queryOrderChangeDto
	 * @return
	 */
	List<DispatchOrderChangeEntity> queryByQueryOrderChangeDto(
			QueryOrderChangeDto queryOrderChangeDto);
	/**
	 * 将job_id更新为'B'表示该订单处于开关关闭状态的车队
	 * @param changeList
	 */
	int updateChangebyOrderId(DispatchOrderChangeEntity dispatchOrderChangeEntity);
	
	/**
	 * 更具预处理订单状态jobId获取预处理订单
	 * @param queryOrderChangeDto
	 * @return
	 */
	List<DispatchOrderChangeEntity> queryChangebyJobId(QueryOrderChangeDto queryOrderChangeDto);
	
	/**
	 * 
	* @Title: queryChanageIdByOrder 
	* @Description: 根据订单id查看是否存在预处理单
	* @param @param orderId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String queryChanageIdByOrder(String orderId);
	
	/**
	 * 
	 * 查询变更表中快递记录
	 * @param 
	 * @author FOSS-YANGBIN
	 * @date 2012-11-2 下午4:43:36
	 */
	List<DispatchOrderChangeEntity>  queryExpressChange();
	
	/**
	 * 更新job锁
	 * updateRepaymentForJob: <br/>
	 * 
	 * Date:2014-7-9下午6:59:44
	 * @author 157229-zxy
	 * @param queryOrderChangeDto
	 * @return
	 * @since JDK 1.6
	 */
	public int updateOrderChangeForJob(QueryOrderChangeDto queryOrderChangeDto);
	
	/**
	 * 更新快递job锁
	 * updateOrderChangeForExpressJob: <br/>
	 * 
	 * Date:2014-7-9下午6:59:44
	 * @author 157229-zxy
	 * @param queryOrderChangeDto
	 * @return
	 * @since JDK 1.6
	 */
	public int updateOrderChangeForExpressJob(ExpressOrderDto dto);
}