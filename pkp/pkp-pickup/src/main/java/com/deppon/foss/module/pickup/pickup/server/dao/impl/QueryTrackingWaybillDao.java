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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/dao/impl/QueryTrackingWaybillDao.java
 * 
 * FILE NAME        	: QueryTrackingWaybillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pickup.api.server.dao.IQueryTrackingWaybillDao;
import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 追踪运单Dao实现类
 * 
 * @author ibm-wangfei
 * @date Jan 4, 2013 3:03:39 PM
 */
public class QueryTrackingWaybillDao extends iBatis3DaoImpl implements IQueryTrackingWaybillDao {

	/**
	 * 运单追踪实体命名空间
	 */
	private static final String TRACK_NAMESPACE = "com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity.";
	
	/**
	 * 
	 * 查询符合条件的运单记录数-接上次追踪
	 * 
	 * @param condition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:46:25 PM
	 * @see com.deppon.foss.module.pickup.pickup.api.server.dao.IQueryTrackingWaybillDao#queryTrackingWaybillCount(com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto)
	 */
	@Override
	public Long queryTrackingWaybillCountForBefore(TrackingWaybillConditionDto condition) {
		return (Long)this.getSqlSession().selectOne(TRACK_NAMESPACE + "getTotalCountForBefore", condition);
	}

	/**
	 * 
	 * 查询符合条件的运单记录Dto列表
	 * 
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:46:06 PM
	 * @see com.deppon.foss.module.pickup.pickup.api.server.dao.IQueryTrackingWaybillDao#queryTrackingWaybillDtoList(com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingWaybillDto> queryTrackingWaybillDtoListForBefore(TrackingWaybillConditionDto condition, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(TRACK_NAMESPACE + "getTrackListForBefore", condition, rowBounds);
	}
	
	/**
	 * 
	 * 新增运单追踪记录
	 * 
	 * @param waybillTrackInfoEntity
	 * @return 插入记录数
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:02:19 PM
	 */
	@Override
	public int addTrackingWaybillEntity(WaybillTrackInfoEntity waybillTrackInfoEntity) {
		waybillTrackInfoEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(TRACK_NAMESPACE + "insert", waybillTrackInfoEntity);
	}

	/**
	 * 
	 * 根据运单号，查询运单追踪记录
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 4, 2013 3:02:40 PM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillTrackInfoEntity> queryByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(TRACK_NAMESPACE + "selectByWaybillNo", waybillNo);
	}

	/**
	 * 
	 * 查询符合条件的运单记录数-新追踪
	 * @param condition
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 11:09:35 AM
	 */
	@Override
	public Long queryTrackingWaybillCountForNew(TrackingWaybillConditionDto condition) {
		return (Long)this.getSqlSession().selectOne(TRACK_NAMESPACE + "getTotalCountForNew", condition);
	}

	/**
	 * 
	 * 查询符合条件的运单记录Dto列表-新追踪
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 6, 2013 11:10:01 AM
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingWaybillDto> queryTrackingWaybillDtoListForNew(TrackingWaybillConditionDto condition, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(TRACK_NAMESPACE + "getTrackListForNew", condition, rowBounds);
	}
	//RFOSS2015041738跟踪运单增加导出功能需求  065340 liutao
	/**
	 * 
	 * 查询符合条件的运单记录数-全部
	 * @param condition
	 * @return
	 * @author liutao 065340
	 */
	@Override
	public Long queryTrackingWaybillCountForAll(TrackingWaybillConditionDto condition) {
		return (Long)this.getSqlSession().selectOne(TRACK_NAMESPACE + "getTotalCountForAll", condition);
	}

	/**
	 * 
	 * 查询符合条件的运单记录Dto列表-全部
	 * @param condition
	 * @param start
	 * @param limit
	* @author liutao 065340
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingWaybillDto> queryTrackingWaybillDtoListForAll(TrackingWaybillConditionDto condition, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(TRACK_NAMESPACE + "getTrackListForAll", condition, rowBounds);
	}
}