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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/PassOrderApplyDao.java
 * 
 *  FILE NAME     :PassOrderApplyDao.java
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
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: PassOrderApplyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassOrderApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.QueryDispatchOrderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 受理约车Dao
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午6:02:27
 */
public class PassOrderApplyDao extends iBatis3DaoImpl implements IPassOrderApplyDao {

	private static final String NAMESPACE = "foss.scheduling.PassOrderApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 上午5:07:32
	 * @param passOrderApplyEntity
	 * @return 受影响的行数
	 */
	public int addPassOrderApply(PassOrderApplyEntity passOrderApplyEntity) {
		passOrderApplyEntity.setId(UUIDUtils.getUUID());
		String statement = NAMESPACE + "addPassOrderApply";
		return getSqlSession().insert(statement, passOrderApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 上午5:08:43
	 * @param passOrderApplyEntity 
	 * @return 
	 */
	public int updatePassOrderApply(PassOrderApplyEntity passOrderApplyEntity) {
		String statement = NAMESPACE + "updatePassOrderApply";
		return getSqlSession().update(statement, passOrderApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 上午5:10:09
	 * @param passOrderApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassOrderApplyEntity> queryPassOrderApplyList(PassOrderApplyEntity passOrderApplyEntity) {
		String statement = NAMESPACE + "queryPassOrderApplyList";
		return getSqlSession().selectList(statement, passOrderApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 上午5:10:43
	 * @param passOrderApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassOrderApplyEntity> queryPassOrderApplyForPage(PassOrderApplyEntity passOrderApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryPassOrderApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, passOrderApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 上午5:12:19
	 * @param passOrderApplyEntity
	 * @return 行数
	 */
	public Long queryCount(PassOrderApplyEntity  passOrderApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, passOrderApplyEntity);
	}
	
	/**
	 * 根据约车单号查询约车审核信息&& 放行任务
	 * @author 104306-foss-wangLong
	 * @date 2012-11-27 下午4:11:38
	 * @param orderNo 约车单号
	 * @return PassOrderApplyDto
	 */
	public PassOrderApplyDto queryPassOrderApplyListAndDepartTask(String orderNo) {
		String statement = NAMESPACE + "queryPassOrderApplyListAndDepartTask";
		return (PassOrderApplyDto) getSqlSession().selectOne(statement, orderNo);
	}
	
	/**
	 * 根据约车单号查询 集中转货处理结果
	 * @author 104306-foss-wangLong
	 * @date 2012-12-1 下午4:39:16
	 * @param orderNo 约车单号
	 */
	@SuppressWarnings("unchecked")
	public List<QueryDispatchOrderDto> queryPassOrderApplyByDispatchOrder(String orderNo) {
		String statement = NAMESPACE + "queryPassOrderApplyByDispatchOrder";
		return getSqlSession().selectList(statement, orderNo);
	}
	
	/**
	 * 根据车牌号查询约车对应的司机信息
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午5:09:41
	 * @param vehicleNoList
	 * @param orderVehicleStatusList
	 * @param useVehicleOrgCode
	 * @return {@link java.util.Map}
	 */
	@SuppressWarnings("unchecked")
	public Map<String, VehicleDriverWithDto> queryDriverInfoByVehicleNos(List<String> vehicleNoList, List<String> orderVehicleStatusList, String useVehicleOrgCode) {
		String statement = NAMESPACE + "queryDriverInfoByVehicleNos";
		Map<String, Object>  parameter = new HashMap<String, Object>();
		parameter.put("vehicleNoList", vehicleNoList);
		parameter.put("orderVehicleStatusList", orderVehicleStatusList);
		parameter.put("useVehicleOrgCode", useVehicleOrgCode);
		return getSqlSession().selectMap(statement, parameter, "vehicleNo");
	}
}