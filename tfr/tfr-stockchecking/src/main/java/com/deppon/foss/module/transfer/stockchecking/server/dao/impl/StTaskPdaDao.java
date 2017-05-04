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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/server/dao/impl/StTaskPdaDao.java
 *  
 *  FILE NAME          :StTaskPdaDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskPdaEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 处理PDA清仓任务实体相关的Crud
 * @author foss-wuyingjie
 * @date 2012-10-16 下午4:56:09
 */
public class StTaskPdaDao extends iBatis3DaoImpl implements IStTaskPdaDao {

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:08
	 * @function 查询PDA任务分支
	 * @param stTaskNo
	 * @return List<StTaskPdaEntity>
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao#queryBranchPdaTask(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StTaskPdaEntity> queryBranchPdaTask(String stTaskNo) {
		
		return this.getSqlSession().selectList("foss.tfr.StTaskPdaDao.queryBranchPdaTask", stTaskNo);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:12
	 * @fucntion 根据任务号PDA号查询任务分支
	 * @param stTaskNo
	 * @param pdaNo
	 * @return StTaskPdaEntity
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao#queryBranchPdaTask(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StTaskPdaEntity queryBranchPdaTask(String stTaskNo, String pdaNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("stTaskNo", stTaskNo);
		params.put("pdaNo", pdaNo);
		
		List<StTaskPdaEntity> list = this.getSqlSession().selectList("foss.tfr.StTaskPdaDao.queryOneBranchPdaTask", params);
		if(null == list){
			return new StTaskPdaEntity();
		}else{
			return list.get(0);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:17
	 * @function 新增PDA任务
	 * @param stTaskPdaEntity
	 * @return
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao#addStTaskPdaEntity(com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StTaskPdaEntity)
	 */
	@Override
	public void addStTaskPdaEntity(StTaskPdaEntity stTaskPdaEntity) {
		stTaskPdaEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.tfr.StTaskPdaDao.addStTaskPdaEntity", stTaskPdaEntity);
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:21
	 * @fucntion 批量新增PDA任务
	 * @param lastScanTaskPdaList
	 * @return 
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao#addStTaskPdaEntityBatch(java.util.List)
	 */
	@Override
	public void addStTaskPdaEntityBatch(List<StTaskPdaEntity> lastScanTaskPdaList) {
		if(CollectionUtils.isNotEmpty(lastScanTaskPdaList)){
			this.getSqlSession().insert("foss.tfr.StTaskPdaDao.addStTaskPdaEntityBatch", lastScanTaskPdaList);
		}
	}

	/** 
	 * @author foss-wuyingjie
	 * @date 2012-12-25 上午10:47:21
	 * @fucntion 是否有更改
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return int
	 * @see com.deppon.foss.module.transfer.stockchecking.api.server.dao.IStTaskPdaDao#addStTaskPdaEntityBatch(java.util.List)
	 */
	@Override
	public int queryIsChanged(String waybillNo, String serialNo, String deptCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		params.put("deptCode", deptCode);
		
		return (Integer) this.getSqlSession().selectOne("foss.tfr.StTaskPdaDao.queryIsChanged", params);
	}
}