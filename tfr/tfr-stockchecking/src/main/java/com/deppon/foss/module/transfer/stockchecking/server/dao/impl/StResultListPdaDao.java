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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StResultListPdaDao.java
 *  
 *  FILE NAME          :StResultListPdaDao.java
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
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 处理pda清仓结果相关的crud操作
 * @author foss-wuyingjie
 * @date 2012-12-25 上午10:44:34
 */
public class StResultListPdaDao extends iBatis3DaoImpl implements IStResultListPdaDao {

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:40
	 * @function 新增pda任务
	 * @param stResultListPdaEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao#addStResultListPdaEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity)
	 */
	@Override
	public void addStResultListPdaEntity(StResultListPdaEntity stResultListPdaEntity) {
		stResultListPdaEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert("foss.tfr.StResultListPdaDao.addStResultListPdaEntity", stResultListPdaEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:44
	 * @function 根据任务号、运单号、流水号查询最后一次的扫描记录
	 * @param stResultListPdaEntity
	 * @return StResultListPdaEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao#queryLastScanEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StResultListPdaEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StResultListPdaEntity queryLastScanEntity(StResultListPdaEntity stResultListPdaEntity) {
		List<StResultListPdaEntity> list = this.getSqlSession().selectList("foss.tfr.StResultListPdaDao.queryLastScanEntity", stResultListPdaEntity);
		if(CollectionUtils.isEmpty(list)){
			return new StResultListPdaEntity();
		}else{
			return list.get(0);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:44:49
	 * @function 根据任务号查询pda扫描列表
	 * @param stTaskNo
	 * @return List<StResultListPdaEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStResultListPdaDao#queryStResultListPda(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StResultListPdaEntity> queryStResultListPda(String stTaskNo) {

		return this.getSqlSession().selectList("foss.tfr.StResultListPdaDao.queryStResultListPda", stTaskNo);
	}
	
	/**
	 * @author niuly
	 * @date 2014-3-17下午5:00:18
	 * @function 根据pda信息查询pda扫描记录
	 * @param stResultListPdaEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StResultListPdaEntity> queryStPdaResultList(String taskNo) {
		return this.getSqlSession().selectList("foss.tfr.StResultListPdaDao.queryStPdaResultList", taskNo);
	}
}