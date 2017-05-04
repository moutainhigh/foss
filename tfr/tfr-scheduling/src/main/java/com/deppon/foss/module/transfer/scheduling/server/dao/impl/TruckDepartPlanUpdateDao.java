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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/TruckDepartPlanUpdateDao.java
 * 
 *  FILE NAME     :TruckDepartPlanUpdateDao.java
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
 * FILE    NAME: TruckDepartPlanUpdateDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TruckDepartPlanUpdateEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;

/**
 * 日志dao实现
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-21 下午7:03:23
 */
public class TruckDepartPlanUpdateDao extends iBatis3DaoImpl implements ITruckDepartPlanUpdateDao {

	/**
	 * 前缀
	 */
	private static String prefix = "Foss.scheduling.TruckDepartPlanUpdate.";

	/**
	 * 新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:03:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao#addTruckDepartPlanUpdate(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public void addTruckDepartPlanUpdate(TruckDepartPlanUpdateEntity truckDepartPlanUpdateEntity) {
		this.getSqlSession().insert(prefix + "addTruckDepartPlanUpdate", truckDepartPlanUpdateEntity);

	}

	/**
	 * 查询日志列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-21 下午7:03:23
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao#queryTruckDepartPlanUpdates(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckDepartPlanUpdateEntity> queryTruckDepartPlanUpdates(TruckDepartPlanDetailDto detailDto, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(prefix + "queryTruckDepartPlanUpdates", detailDto, rowBounds);
	}

	/**
	 * 查询总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-11-23 上午8:41:09
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao#queryTotalCount(com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto)
	 */
	@Override
	public Long queryTotalCount(TruckDepartPlanDetailDto detailDto) {
		return (Long) this.getSqlSession().selectOne(prefix + "queryTotalCount", detailDto);
	}

	/**
	 * 批量新增日志
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 上午10:08:50
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.ITruckDepartPlanUpdateDao#addTruckDepartPlanUpdates(java.util.List)
	 */
	@Override
	public void addTruckDepartPlanUpdates(List<TruckDepartPlanUpdateEntity> list) {
		//由原来的addTruckDepartPlanUpdates换位addTruckDepartPlanUpdatesOne
		if(CollectionUtils.isNotEmpty(list)){
			for(TruckDepartPlanUpdateEntity entity:list){
				this.getSqlSession().insert(prefix + "addTruckDepartPlanUpdatesOne", entity);
			}
		}
		

	}

}