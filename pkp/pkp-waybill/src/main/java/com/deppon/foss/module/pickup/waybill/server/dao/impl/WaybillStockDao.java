/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillStockDao.java
 * 
 * FILE NAME        	: WaybillStockDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillStockEntity;
import com.deppon.foss.util.UUIDUtils;
/**
 * 运单库存DAO
 * @author 026113-foss-linwensheng
 * @date 2012-12-27 上午8:23:13
 */
public class WaybillStockDao extends iBatis3DaoImpl implements IWaybillStockDao {

	private static final String NAMESPACE = "foss.pkp.WaybillStockEntityMapper.";
	/**
	 * 添加异步入库
	 * @author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	@Override
	public int addWaybillStockEntity(WaybillStockEntity record) {
		// TODO Auto-generated method stub
		
		// 设置UUID
		record.setId(UUIDUtils.getUUID());
		return getSqlSession().insert(NAMESPACE+"insert", record);
	}
	/**
	 *更新JOBID
	 *@author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	@Override
	public int updateJobIDByID(WaybillStockEntity record) {
		
		return getSqlSession().update(NAMESPACE+"updateJobIDByID", record);
	}
	/**
	 * 更新操作类型
	 * @author 026113-foss-linwensheng
	 * @param record
	 * @return
	 */
	@Override
	public int updateOperateTypeByID(WaybillStockEntity record) {
		 	return getSqlSession().update(NAMESPACE+"updateOperateTypeByID", record);
	}
	/**
	 * 根据ID查询运单库存.
	 * @author 026113-foss-linwensheng
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillStockEntity> queryWaybillStockEntity() {
		
		return getSqlSession().selectList(NAMESPACE+"selectWaybillStock");
	}
	/**
	 * 根据jobId查询运单库存.
	 * @author 026113-foss-linwensheng
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillStockEntity> queryWaybillStockEntityByJobID(String jobId) {
		
		return getSqlSession().selectList(NAMESPACE+"selectWaybillStockEntityByJobID",jobId);
	}
	
	/**
	 * 通过Job 删除记录
	 * @author 026113-foss-linwensheng
	 * @param jobId
	 */
	@Override
	public void deleteByJobId(String jobId) {
		// TODO Auto-generated method stub
		
		 getSqlSession().delete(NAMESPACE+"deleteByJobId",jobId);
	}
	/**
	 * 通过ID  删除记录
	 * @author 026113-foss-linwensheng
	 * @param jobId
	 */
	@Override
	public void deleteById(String id) {
		
		
		 getSqlSession().delete(NAMESPACE+"deleteById",id);
		
	}
	
	/**
	 * 获取运单库存信息
	 * @author 087584-foss-WangQianJin
	 * @date 2013-3-21 下午4:48:39
	 */
	@Override
	public List<WaybillStockEntity> queryWaybillStockDtoByWaybillNo(String waybillNo){
	    return getSqlSession().selectList(NAMESPACE+"selectWaybillStockEntityByWaybillNo",waybillNo);
	}


}