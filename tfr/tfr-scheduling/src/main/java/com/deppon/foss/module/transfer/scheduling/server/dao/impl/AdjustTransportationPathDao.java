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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/AdjustTransportationPathDao.java
 * 
 *  FILE NAME     :AdjustTransportationPathDao.java
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
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao;


/**
 * 调整走货路径Dao实现
 * @author huyue
 * @date 2012-10-12 下午6:53:38
 */
public class AdjustTransportationPathDao extends iBatis3DaoImpl implements IAdjustTransportationPathDao{


	private static final String NAMESPACE = "Foss.scheduling.";

	/** 
	 * 查询调整走货路径LIST, 带边界值, 取库区,count运单
	 * @author huyue
	 * @date 2012-10-12 下午6:53:28
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#queryLevel1(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustEntity> queryLevel1(AdjustEntity adjustEntity, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		List<AdjustEntity> adjustList = this.getSqlSession().selectList(NAMESPACE + "queryLevel1", adjustEntity, rowBounds);
		
		return adjustList;
	}
	
	/** 
	 * 根据部门&货区编号查询运单信息
	 * @author huyue
	 * @date 2012-10-12 下午9:33:57
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#queryLevel2(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity)
	 */
	@SuppressWarnings("unchecked")
	public List<AdjustEntity> queryWaybillList(AdjustEntity adjustEntity) {
		
		List<AdjustEntity> waybillList = this.getSqlSession().selectList(NAMESPACE + "waybillList", adjustEntity);
		
		return waybillList;
	}
	
	/**
	 * 根据部门&修改后的货区编号查询运单信息
	 * @author huyue
	 * @date 2012-11-5 下午4:48:50
	 */
	@SuppressWarnings("unchecked")
	public List<AdjustEntity> queryChangedWaybillList(Map<String, String> map) {
		
		List<AdjustEntity> waybillList = this.getSqlSession().selectList(NAMESPACE + "changedWaybillList", map);
		
		return waybillList;
	}

	/**
	 * 根据运单号,部门号,原库区编号查询改变后的库区list
	 * @author huyue
	 * @date 2012-11-8 下午6:07:41
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryNewGoodsAreaCodeList(Map<String, String> map){
		
		List<String> newGoodsAreaCodeList = this.getSqlSession().selectList(NAMESPACE + "newGoodsAreaCodeList", map);
		
		return newGoodsAreaCodeList;
	}
	
	/**
	 * 查询调整走货路径2级list, 根据waybill
	 * @author huyue
	 * @date 2012-10-12 下午8:08:34
	 */
	@SuppressWarnings("unchecked")
	public List<AdjustEntity> queryLevel2(AdjustEntity adjustEntity) {
		
		List<AdjustEntity> adjustList = this.getSqlSession().selectList(NAMESPACE + "queryLevel2", adjustEntity);
		
		return adjustList;
	}
	
	/** 
	 * 查询走货路径count
	 * @author huyue
	 * @date 2012-10-12 下午6:53:30
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#getCount(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity)
	 */
	public Long getCount(AdjustEntity adjustEntity) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", adjustEntity);
		
	}

	/** 
	 * 修改走货路径第三层查询,根据运单匹配在库货件号 
	 * @author huyue
	 * @date 2012-10-12 下午8:01:41
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#queryLevel3(com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity)
	 */
	@SuppressWarnings("unchecked")
	public List<AdjustEntity> queryLevel3(AdjustEntity adjustEntity) {
		
		List<AdjustEntity> waybillList = this.getSqlSession().selectList(NAMESPACE + "queryLevel3", adjustEntity);
		
		return waybillList;
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#queryLevel1ByOrgCodesList(java.util.List, int, int)
	 */

	@Override
	public List<AdjustEntity> queryLevel1ByParamMap(Map<String, Object> paramMap, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		@SuppressWarnings("unchecked")
		List<AdjustEntity> adjustList = this.getSqlSession().selectList(NAMESPACE + "queryLevel1ByParamMap", paramMap, rowBounds);
		return adjustList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustTransportationPathDao#queryWaybillListByOrgCodesList(java.util.List)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustEntity> queryWaybillListByParamMap(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillListByParamMap", paramMap);
	}
	
}