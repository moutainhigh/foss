/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-ontheway
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/server/dao/impl/OnthewayDao.java
 *  
 *  FILE NAME          :OnthewayDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.ontheway.api.server.dao.IOnthewayDao;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ManualEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.OnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.QueryOnthewayEntity;

/**
 * 
 * 车辆在途的底层实现
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午7:07:36
 */
public class OnthewayDao extends iBatis3DaoImpl implements IOnthewayDao
{

	private static final String NAMESPACE = "tfr-ontheway.";

	/**
	 * 查询临时任务,分页
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-29 下午5:22:40
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OnthewayEntity> queryOnthewayGrid(
			QueryOnthewayEntity queryEntity, int limit, int start)
	{
		RowBounds rowBounds = new RowBounds(start, limit);
		List<OnthewayEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryOnthewayGrid", queryEntity, rowBounds);
		return list;
	}

	/**
	 * 
	 * 待跟踪长途车辆
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public Long getCount(QueryOnthewayEntity queryEntity)
	{
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + "getOnthewayCount", queryEntity);
	}

	/**
	 * 
	 * 增加一条在途信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	@Override
	public void addManual(ManualEntity manualEntity)
	{
		this.getSqlSession().insert(NAMESPACE + "addManual", manualEntity);

	}

	/**
	 * 
	 * 更行一些状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	@Override
	public void updateManualStatus(ManualEntity manualEntity)
	{
		this.getSqlSession().update(NAMESPACE + "updateManualStatus",
				manualEntity);
	}

	/**
	 * 
	 * 更行一些状态
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	@Override
	public List<OnthewayEntity> queryOnthewayGridById(String truckTaskDetailId)
	{
		List<OnthewayEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryOnthewayGridById", truckTaskDetailId);
		return list;
	}

}