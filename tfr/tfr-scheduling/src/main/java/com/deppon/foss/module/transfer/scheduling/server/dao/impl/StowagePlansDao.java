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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto;

/**
 * 调整走货路径Dao实现
 * 
 * @author huyue
 * @date 2012-10-12 下午6:53:38
 */
public class StowagePlansDao extends iBatis3DaoImpl implements IStowagePlansDao {

	private static final String NAMESPACE = "Foss.scheduling.stowagePlans.";

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#selectStowagePlansList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StowagePlansEntity> selectStowagePlansList(
			StowagePlansDto stowagePlansDto,int start,int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", stowagePlansDto.getId());
		map.put("origOrgCode", stowagePlansDto.getOrigOrgCode());
		map.put("destOrgCode", stowagePlansDto.getDestOrgCode());

		return super.getSqlSession().selectList(
				NAMESPACE + "selectStowagePlansList", map);
	}
	
	/**
	 * @author 134019-foss-yuyongxiang
	 */
	@Override
	public Long selectStowagePlansListCount(
			StowagePlansDto stowagePlansDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", stowagePlansDto.getId());
		map.put("origOrgCode", stowagePlansDto.getOrigOrgCode());
		map.put("destOrgCode", stowagePlansDto.getDestOrgCode());
		
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "selectStowagePlansListCount", map);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#selectStowagePlansDetailList(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StowagePlansDetailEntity> selectStowagePlansDetailList(
			StowagePlansDto stowagePlansDto) {

		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("id", stowagePlansDto.getId());
		map.put("stowagePlansId", stowagePlansDto.getStowagePlansId());

		return super.getSqlSession().selectList(
				NAMESPACE + "selectStowagePlansDetailList", map);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#insertStowagePlans(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity)
	 */
	@Override
	public void insertStowagePlans(StowagePlansEntity stowagePlansEntity) {
		super.getSqlSession().insert(NAMESPACE + "insertStowagePlans",
				stowagePlansEntity);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#insertStowagePlansDetail(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansDetailEntity)
	 */
	@Override
	public void insertStowagePlansDetail(
			StowagePlansDetailEntity stowagePlansDetailEntity) {
		super.getSqlSession().insert(NAMESPACE + "insertStowagePlansDetail",
				stowagePlansDetailEntity);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#updateStowagePlans(com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity)
	 */
	@Override
	public void updateStowagePlans(StowagePlansEntity stowagePlansEntity) {
		super.getSqlSession().update(NAMESPACE + "updateStowagePlans",
				stowagePlansEntity);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-30 下午3:08:56 (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#deleteStowagePlansDetailWithStowagePlansId(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@Override
	public void deleteStowagePlansDetailWithStowagePlansId(
			StowagePlansDto stowagePlansDto) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("stowagePlansId", stowagePlansDto.getStowagePlansId());
		super.getSqlSession().update(
				NAMESPACE + "deleteStowagePlansDetailWithStowagePlansId", map);
	}

	/**
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午7:02:11 
	 *(non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IStowagePlansDao#deleteStowagePlansWithId(com.deppon.foss.module.transfer.scheduling.api.shared.dto.StowagePlansDto)
	 */
	@Override
	public void deleteStowagePlansWithId(StowagePlansDto stowagePlansDto) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("id", stowagePlansDto.getId());
		super.getSqlSession().update(
				NAMESPACE + "deleteStowagePlansWithId", map);
		
	}

}