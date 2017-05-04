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
 *  PROJECT NAME  : tfr-stockchecking
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StOperatorDao.java
 *  
 *  FILE NAME          :StOperatorDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 清仓操作人的crud处理
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:43:07
 */
public class StOperatorDao extends iBatis3DaoImpl implements IStOperatorDao {

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:15
	 * @function 新增任务操作人
	 * @param entity
	 * @return void
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao#addStOperatorEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StOperatorEntity)
	 */
	@Override
	public void addStOperatorEntity(StOperatorEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.tfr.StOperatorDao.addStOperatorEntity", entity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:21
	 * @function 根据任务号查询操作人
	 * @param stTaskId
	 * @return List<StOperatorEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao#queryOperatorsByStTaskId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StOperatorEntity> queryOperatorsByStTaskId(String stTaskId) {
		
		return this.getSqlSession().selectList("foss.tfr.StOperatorDao.queryOperatorsByStTaskId", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:24
	 * @function 删除清仓人
	 * @param stTaskId
	 * @return void
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao#deleteOperatorByStTaskId(java.lang.String)
	 */
	@Override
	public void deleteOperatorByStTaskId(String stTaskId) {
		this.getSqlSession().delete("foss.tfr.StOperatorDao.deleteOperatorByStTaskId", stTaskId);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:43:29
	 * @function 批量新增清仓人
	 * @param stOperatorList
	 * @return 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStOperatorDao#addStOperatorEntityBatch(java.util.List)
	 */
	@Override
	public void addStOperatorEntityBatch(List<StOperatorEntity> stOperatorList) {
		if(CollectionUtils.isNotEmpty(stOperatorList)){
			this.getSqlSession().insert("foss.tfr.StOperatorDao.addStOperatorEntityBatch", stOperatorList);
		}
	}

}