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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/DeliverLoadTaskDao.java
 *  
 *  FILE NAME          :DeliverLoadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: DeliverLoadTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto;

/**
 * DeliverLoadTaskDao
 * @author dp-duyi
 * @date 2012-10-30 上午11:11:10
 */
@SuppressWarnings("unchecked")
public class DeliverLoadTaskDao extends iBatis3DaoImpl implements IDeliverLoadTaskDao {
	private static final String NAMESPACE = "tfr-load.";
	/** 
	 * 确认、打回派送单:根据派送单号更新装车任务状态
	 * @author dp-duyi
	 * @date 2012-10-30 上午11:11:46
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#updateDeliverLoadTaskState(com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity)
	 */
	@Override
	public int updateDeliverLoadTaskStateByDeliverBillNo(LoadTaskEntity loadTask) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadTaskStateByDeliverBillNo", loadTask);
	}

	/** 
	 * 根据派送单号查询装车任务
	 * @author dp-duyi
	 * @date 2012-10-30 上午11:36:49
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#queryLoadTasks(com.deppon.foss.module.transfer.load.api.shared.domain.LoadTaskEntity)
	 */
	@Override
	public List<LoadTaskEntity> queryLoadTasksByDeliverBillNo(LoadTaskEntity task) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadTaskByDeliverBillNo", task);
	}

	/** 
	 * 根据派送单号、运单号，返回最新的装车任务流水号列表
	 * @author dp-duyi
	 * @date 2012-12-27 上午11:38:37
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#queryLastLoadSerialNos(java.util.Map)
	 */
	@Override
	public List<String> queryLastLoadSerialNos(Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLastLoadSerialNos", condition);
	}

	/**
	 * 根据派送单获取派送单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:31:00
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#queryDeliverBillByNo(java.lang.String)
	 */
	@Override
	public DeliverBillEntity queryDeliverBillByNo(String billNo) {
		List<DeliverBillEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryDeliverBillByNo", billNo);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据派送单获取派送单明细(运单)
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:31:14
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#queryDeliverBillDetailListByNo(com.deppon.foss.module.transfer.load.api.shared.dto.DeliverBillQueryConditionDto)
	 */
	@Override
	public List<LoadWaybillDetailEntity> queryDeliverBillDetailListByNo(
			DeliverBillQueryConditionDto queryDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverBillDetailListByNo", queryDto);
	}

}