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
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/OrderVehicleDao.java
 * 
 *  FILE NAME     :OrderVehicleDao.java
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
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IOrderVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrderVehicleDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 约车数据访问Dao
 * @author 104306-foss-wangLong
 * @date 2012-10-15 下午12:51:34
 */
public class OrderVehicleDao extends iBatis3DaoImpl implements IOrderVehicleDao {
	
	private final static String NAMESPACE = "foss.scheduling.OrderVehicle.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 受影响的行数
	 */
	public int addOrderVehicle(OrderVehicleEntity orderVehicleEntity) {
		String statement = NAMESPACE + "addOrderVehicle";
		orderVehicleEntity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(statement, orderVehicleEntity);
	}

	/**
	 * 修改单个对象
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleEntity
	 * @return 受影响的行数
	 */
	public int updateOrderVehicle(OrderVehicleEntity orderVehicleEntity) {
		String statement = NAMESPACE + "updateOrderVehicle";
		return getSqlSession().update(statement, orderVehicleEntity);
	}

	/**
	 * 查询单个对象（按主键查询）
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity
	 */
	public OrderVehicleEntity queryOrderVehicle(String id) {
		String statement = NAMESPACE + "queryOrderVehicle";
		return (OrderVehicleEntity) getSqlSession().selectOne(statement, id);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<OrderVehicleEntity> queryOrderVehicleList(OrderVehicleDto orderVehicleDto) {
		String statement = NAMESPACE + "queryOrderVehicleList";
		return getSqlSession().selectList(statement, orderVehicleDto);
	}
	
	/**
	 * 根据id集合查询
	 * @author 104306-foss-wangLong
	 * @date 2012-10-22 下午12:29:54
	 * @param  orderVehicleApplyIdList  
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderVehicleEntity> queryOrderVehicleListByIds(List<String> orderVehicleApplyIdList) {
		String statement = NAMESPACE + "queryOrderVehicleListByIds";
		return getSqlSession().selectList(statement, orderVehicleApplyIdList);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @param start
	 * @param pageSize
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<OrderVehicleEntity> queryOrderVehicleForPage(OrderVehicleDto orderVehicleDto, int start, int pageSize) {
		String statement = NAMESPACE + "queryOrderVehicleList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, orderVehicleDto, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleDto
	 * @return 总行数
	 */
	public Long queryCount(OrderVehicleDto orderVehicleDto) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, orderVehicleDto);
	}

	/**
	 * 更新约车申请状态
	 * @author 104306-foss-wangLong
	 * @date 2012-10-15 下午12:51:42
	 * @param orderVehicleApplyIdList
	 * @param status
	 * @return 
	 */
	public int updateOrderVehicleApplyStatus(List<String> orderVehicleApplyIdList, String status) {
		String statement = NAMESPACE + "updateOrderVehicleApplyStatus";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("orderVehicleApplyIdList", orderVehicleApplyIdList);
		parameterMap.put("status", status);
		if(OrderVehicleConstants.ORDERVEHICLE_STATUS_CONFIRMTO.equals(status)){
			parameterMap.put("arrivalTime", status);
		}else{
			parameterMap.put("arrivalTime", null);
		}
		
		//CONFIRMTO
		return getSqlSession().update(statement, parameterMap);
	}
	/**
	 * 更新约车申请状态
	 * @author 332153-foss-zm
	 * @date 2016年11月22日14:19:42
	 * @param parameterMap
	 * @return 
	 */
	public int updateOrderVehicleApplyStatus(Map<String, Object> parameterMap) {
		String statement = NAMESPACE + "updateOrderVehicleApplyStatusByOrderNo";
		return getSqlSession().update(statement, parameterMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVehicleEntity> queryAuditOrderVehicleList(OrderVehicleDto orderVehicleDto) {
		String statement = NAMESPACE + "queryAuditOrderVehicleList";
		
		return getSqlSession().selectList(statement, orderVehicleDto);
	}
}