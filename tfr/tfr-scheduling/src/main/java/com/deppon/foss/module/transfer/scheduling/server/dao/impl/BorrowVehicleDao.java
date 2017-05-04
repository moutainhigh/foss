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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/BorrowVehicleDao.java
 * 
 *  FILE NAME     :BorrowVehicleDao.java
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
 * FILE    NAME: BorrowVehicleDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IBorrowVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.BorrowVehicleDto;
import com.deppon.foss.util.UUIDUtils;

 /**
 * 借车申请Dao
 * @author 104306-foss-wangLong
 * @date 2012-12-03 下午1:06:56
 */
public class BorrowVehicleDao extends iBatis3DaoImpl implements IBorrowVehicleDao {
	
	private static final String NAMESPACE = "foss.scheduling.BorrowVehicle.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param borrowVehicleEntity
	 * @return 受影响的行数
	 */
	public int addBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity) {
		String statement = NAMESPACE + "addBorrowVehicle";
		borrowVehicleEntity.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(statement, borrowVehicleEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param borrowVehicleEntity 
	 * @return 受影响的行数
	 */
	public int updateBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity) {
		String statement = NAMESPACE + "updateBorrowVehicle";
		return getSqlSession().update(statement, borrowVehicleEntity);
	}

	/**
	 * 查询单个对象（按主键查询）
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param id  主键id
	 * @return 存在 返回该对象 不存在返回null
	 */
	public BorrowVehicleEntity queryBorrowVehicle(String id) {
		String statement = NAMESPACE + "queryBorrowVehicle";
		return (BorrowVehicleEntity)getSqlSession().selectOne(statement, id);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param borrowVehicleDto
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<BorrowVehicleEntity> queryBorrowVehicleList(BorrowVehicleDto borrowVehicleDto) {
		String statement = NAMESPACE + "queryBorrowVehicleList";
		return getSqlSession().selectList(statement, borrowVehicleDto);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param borrowVehicleDto
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<BorrowVehicleDto> queryBorrowVehicleForPage(BorrowVehicleDto borrowVehicleDto, int start, int pageSize) {
		String statement = NAMESPACE + "queryBorrowVehicleForPage";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, borrowVehicleDto, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:56
	 * @param borrowVehicleDto
	 * @return 行数
	 */
	public Long queryCount(BorrowVehicleDto borrowVehicleDto) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, borrowVehicleDto);
	}
	
	/**
	 * 修改状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:23:07
	 * @param borrowNoList 借车编号
	 * @param status  状态
	 */
	public int updateBorrowVehicleApplyStatus(List<String> borrowNoList, String status) {
		String statement = NAMESPACE + "updateBorrowVehicleApplyStatus";
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("borrowNoList", borrowNoList);
		parameter.put("status", status);
		return getSqlSession().update(statement, parameter);
	}
}