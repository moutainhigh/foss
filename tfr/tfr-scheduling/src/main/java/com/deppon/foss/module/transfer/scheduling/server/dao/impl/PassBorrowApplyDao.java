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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/PassBorrowApplyDao.java
 * 
 *  FILE NAME     :PassBorrowApplyDao.java
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

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassBorrowApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.util.UUIDUtils;

 /**
 * 借车受理Dao
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:15
 */
public class PassBorrowApplyDao extends iBatis3DaoImpl implements IPassBorrowApplyDao {
	
	private static final String NAMESPACE = "foss.scheduling.PassBorrowApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 受影响的行数
	 */
	public int addPassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity) {
		passBorrowApplyEntity.setId(UUIDUtils.getUUID());
		String statement = NAMESPACE + "addPassBorrowApply";
		return getSqlSession().insert(statement, passBorrowApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity 
	 * @return 受影响的行数
	 */
	public int updatePassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity) {
		String statement = NAMESPACE + "updatePassBorrowApply";
		return getSqlSession().update(statement, passBorrowApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassBorrowApplyEntity> queryPassBorrowApplyList(PassBorrowApplyEntity passBorrowApplyEntity) {
		String statement = NAMESPACE + "queryPassBorrowApplyList";
		return getSqlSession().selectList(statement, passBorrowApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassBorrowApplyEntity> queryPassBorrowApplyForPage(PassBorrowApplyEntity passBorrowApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryPassBorrowApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, passBorrowApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 行数
	 */
	public Long queryCount(PassBorrowApplyEntity  passBorrowApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, passBorrowApplyEntity);
	}
	
	/**
	 *  借来的车辆, 约车使用
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午12:38:30
	 * @param vehicleDriverWithDto
	 */
	@SuppressWarnings("unchecked")
	public Map<String, VehicleDriverWithDto> queryBorrowVehicle(VehicleDriverWithDto vehicleDriverWithDto) {
		String statement = NAMESPACE + "queryBorrowVehicle";
		return getSqlSession().selectMap(statement, vehicleDriverWithDto, "vehicleNo");
	}
}