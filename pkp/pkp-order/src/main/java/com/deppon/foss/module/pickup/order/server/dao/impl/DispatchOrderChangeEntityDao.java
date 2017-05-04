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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/dao/impl/DispatchOrderChangeEntityDao.java
 * 
 * FILE NAME        	: DispatchOrderChangeEntityDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.QueryOrderChangeDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;

/**
 * 调度订单变更表DAO，当新增调度订单时，同时向调度订单变更表插入记录
 * @author 038590-foss-wanghui
 * @date 2012-11-2 下午3:46:11
 */
@SuppressWarnings("unchecked")
public class DispatchOrderChangeEntityDao extends iBatis3DaoImpl implements
		IDispatchOrderChangeEntityDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity.";
	
	/**
	 * 
	 * 查询变更表中记录
	 * @param 
	 * @author 038590-foss-wanghui
	 * @date 2012-11-2 下午4:43:36
	 */
	@Override
	public List<DispatchOrderChangeEntity> queryChange() {
		QueryOrderChangeDto queryDto = new QueryOrderChangeDto();
		queryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryChange", queryDto);
	}

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
	@Override
	public int deleteChange(List<DispatchOrderChangeEntity> changeList) {
		return getSqlSession().delete(NAMESPACE + "deleteChange", changeList);
	}
	
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
	@Override
	public List<DispatchOrderEntity> queryOrder(List<DispatchOrderChangeEntity> changeList) {
		return getSqlSession().selectList(NAMESPACE + "queryOrder", changeList);
	}

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
	@Override
	public int insertChange(DispatchOrderChangeEntity dispatchOrderChangeEntity) {
		return getSqlSession().insert(NAMESPACE + "insertChange", dispatchOrderChangeEntity);
	}

	/**
	 * 
	 * 查询变更表中记录
	 * @param 
	 * @author YANGBIN
	 * @date 2014-5-4 下午4:43:36
	 */
	@Override
	public List<DispatchOrderChangeEntity> queryChangeByExpressDto(ExpressOrderDto dto) {
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryChangeByExpressDto",dto);
	}
	
	/**
	 * 
	 * @Title: deleteChangeByOrderID 
	 * @Description: 根据订单ID删除预处理订单
	 * @param @param orderId
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	@Override
	public int deleteChangeByOrderID(String orderId) {
		return getSqlSession().delete(NAMESPACE + "deleteChangeByOrderID", orderId);
	}
	
	/**
	 * 
	 * @Title: batchUpdateChange 
	 * @Description: 批量更新预处理订单状态
	 * @param @param list
	 * @param @return    设定文件 
	 * @return int    返回类型 
	 * @throws
	 */
	@Override
	public int batchUpdateChange(List<DispatchOrderChangeEntity> list) {
		return getSqlSession().update(NAMESPACE + "batchUpdateChange", list);
	}
	/**
	 * 根据条件查询（产品类型为不等与的条件）
	 * @param queryOrderChangeDto
	 * @return
	 */
	@Override
	public List<DispatchOrderChangeEntity> queryByQueryOrderChangeDto(
			QueryOrderChangeDto queryOrderChangeDto) {
		queryOrderChangeDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryByQueryOrderChangeDto",queryOrderChangeDto);
	}
	/**
	 * 将job_id更新为'B'表示该订单处于开关关闭状态的车队
	 * @param changeList
	 */
	@Override
	public int updateChangebyOrderId(DispatchOrderChangeEntity dispatchOrderChangeEntity) {
		return getSqlSession().update(NAMESPACE + "updateDispatchOrderStatusById", dispatchOrderChangeEntity);
	}
	/**
	 * 更具预处理订单状态jobId获取预处理订单
	 * @param queryOrderChangeDto
	 * @return
	 */
	@Override
	public List<DispatchOrderChangeEntity> queryChangebyJobId(QueryOrderChangeDto queryOrderChangeDto){
		queryOrderChangeDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryChangebyJobId",queryOrderChangeDto);
	}
	
	/**
	 * 
	* @Title: queryChanageIdByOrder 
	* @Description: 根据订单id查看是否存在预处理单
	* @param @param orderId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@Override
	public String queryChanageIdByOrder(String orderId){
		return (String) getSqlSession().selectOne(NAMESPACE + "queryChanageIdByOrder", orderId);
	}

	/**
	 * 
	* @Title: queryExpressChange 
	* @Description: 预处理-查询快递订单
	* @return List<DispatchOrderChangeEntity>    返回类型 
	* @throws
	 */
	@Override
	public List<DispatchOrderChangeEntity> queryExpressChange() {
		QueryOrderChangeDto queryDto = new QueryOrderChangeDto();
		queryDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE + "queryExpressChange");
	}
	
	@Override
	public int updateOrderChangeForJob(QueryOrderChangeDto queryOrderChangeDto){
		queryOrderChangeDto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().update(NAMESPACE + "updateOrderChangeForJob",queryOrderChangeDto);
		
	}
	
	@Override
	public int updateOrderChangeForExpressJob(ExpressOrderDto dto){
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().update(NAMESPACE + "updateOrderChangeForExpressJob",dto);
		
	}
	

}