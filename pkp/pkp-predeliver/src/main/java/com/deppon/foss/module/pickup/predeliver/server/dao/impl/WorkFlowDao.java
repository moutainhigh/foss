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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/AbandonGoodsApplicationDao.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IWorkFlowDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;

/**
 * @ClassName: WorkFlowDao
 * @Description: 工作流dao
 * @author 136892
 * @date 2014-2-14 上午10:11:12
 * 
 */
public class WorkFlowDao extends iBatis3DaoImpl implements IWorkFlowDao {
	// 弃货功能模块命名空间
	private static final String WORKFLOWNAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity.";
	private final static String INSERT = "insert";
	private final static String SEARCH = "search";
	private final static String GET_COUNT = "getCount";
	private final static String UPDATE = "update";
	private final static String GET_ID_BY_WAYBILL_NO = "getIdByWaybillNo";
	private final static String UPDATE_APP_STATUS = "updateAppStatus";
	private final static String QUERY_ORG_CODE_BY_UNIFIED_CODE="queryOrgCodeByUnifiedCode";

	/**
	 * 插入工作流
	 */
	@Override
	public void insertWorkFlow(WorkFlowEntity entity) {

		this.getSqlSession().insert(WORKFLOWNAMESPACE + INSERT, entity);

	}

	/**
	 * 查询
	 */
	@Override
	public WorkFlowEntity workFlowQuery(WorkFlowEntity entity) {
		List<WorkFlowEntity> list=this.getSqlSession().selectList(
				WORKFLOWNAMESPACE + SEARCH, entity);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			entity=list.get(0);
		}
		
		return entity;
	}



	@Override
	public void updateWorkFlow(long processId, String bizCode,String waybillNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("flowCode", Long.toString(processId));
		map.put("bizCode", bizCode);
		map.put("waybillNo", waybillNo);
		this.getSqlSession().update(WORKFLOWNAMESPACE + UPDATE, map);

	}

	@Override
	public String getIdByWaybillNo(String waybillNo,String serialNumber) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("serialNumber", serialNumber);
		return (String) this.getSqlSession().selectOne(
				WORKFLOWNAMESPACE + GET_ID_BY_WAYBILL_NO, map);
	}

	@Override
	public void updateStatus(String waybillNo, String status) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("status", status);
		this.getSqlSession().update(WORKFLOWNAMESPACE + UPDATE_APP_STATUS, map);

	}

	@Override
	public String queryOrgCodeByUnifiedCode(String unifiedCode) {
		return (String) this.getSqlSession().selectOne(WORKFLOWNAMESPACE + QUERY_ORG_CODE_BY_UNIFIED_CODE, unifiedCode);
	}



}