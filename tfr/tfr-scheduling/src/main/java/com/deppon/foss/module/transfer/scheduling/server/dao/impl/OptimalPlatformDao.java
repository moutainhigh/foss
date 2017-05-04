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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/InTransitDao.java
 * 
 *  FILE NAME     :InTransitDao.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IOptimalPlatformDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OptimalPlatformEntity;


/**
 * 货量预测在途表dao实现
 * @author huyue
 * @date 2012-10-11 下午9:13:22
 */
public class OptimalPlatformDao extends iBatis3DaoImpl implements IOptimalPlatformDao{

	private static final String NAMESPACE = "Foss.optimalPlatform.";
	
	/**
	 * 插入
	 * @author huyue
	 * @date 2013-4-10 下午7:00:51
	 */
	public int addOptimalPlatform(OptimalPlatformEntity optimalPlatformEntity) {
		String statement = NAMESPACE + "insert";
		return getSqlSession().insert(statement, optimalPlatformEntity);
	}
	
	/**
	 * 插入 selective
	 * @author huyue
	 * @date 2013-4-10 下午7:01:04
	 */
	public int addOptimalPlatformSelective(OptimalPlatformEntity optimalPlatformEntity) {
		String statement = NAMESPACE + "insertSelective";
		return getSqlSession().insert(statement, optimalPlatformEntity);
	}
	
	/**
	 * 按照主键查询
	 * @author huyue
	 * @date 2013-4-10 下午7:01:19
	 */
	public OptimalPlatformEntity queryByPrimaryKey(String optimalPlatformId) {
		String statement = NAMESPACE + "selectByPrimaryKey";
		return (OptimalPlatformEntity) getSqlSession().selectOne(statement, optimalPlatformId);
	}
	
	/**
	 * 按照车辆明细ID查询批量
	 * @author huyue
	 * @date 2013-4-10 下午7:01:28
	 */
	@SuppressWarnings("unchecked")
	public List<OptimalPlatformEntity> queryListByTruckTaskDetailId(String truckTaskDetailId) {
		String statement = NAMESPACE + "selectByTruckTaskDetailId";
		return getSqlSession().selectList(statement, truckTaskDetailId);
	}
	
	/**
	 * 按照主键删除
	 * @author huyue
	 * @date 2013-4-10 下午7:04:19
	 */
	public int deleteByPrimaryKey(String optimalPlatformId) {
		String statement = NAMESPACE + "deleteByPrimaryKey";
		return getSqlSession().delete(statement, optimalPlatformId);
	}
	
	/**
	 * 按照车辆明细ID删除
	 * @author huyue
	 * @date 2013-4-10 下午7:04:31
	 */
	public int deleteByTruckTaskDetailId(String truckTaskDetailId) {
		String statement = NAMESPACE + "deleteByTruckTaskDetailId";
		return getSqlSession().delete(statement, truckTaskDetailId);
	}
}