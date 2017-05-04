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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/DeliverbillDetailDao.java
 * 
 * FILE NAME        	: DeliverbillDetailDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 派送单明细DAO实现
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-24 上午10:07:01
 */
@SuppressWarnings("unchecked")
public class DeliverbillNewDetailDao extends iBatis3DaoImpl implements
		IDeliverbillNewDetailDao {
	// 派送单明细name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDetailDao";
	
	
	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillIdForPrint(Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillIdForPrint", map,rowBounds);
	}
	
	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto) {
		// 参数Map
		Map<String, String> map = new HashMap<String, String>();
		map.put("deliverbillId", deliverbillDetailDto.getDeliverbillId());
		map.put("preaudit", WaybillRfcConstants.PRE_AUDIT);
		map.put("preaccept", WaybillRfcConstants.PRE_ACCECPT);
		map.put("astatus", ArriveSheetConstants.STATUS_GENERATE);
		map.put("adestroyed", FossConstants.NO);
		map.put("active", FossConstants.YES);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectArrivesheetByDeliverbillId",
				map);
	}
	
	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillById(
			DeliverbillDetailDto deliverbillDetailDto) {
		// 参数Map
		Map<String, String> map = new HashMap<String, String>();
		map.put("deliverbillId", deliverbillDetailDto.getDeliverbillId());
		map.put("preaudit", WaybillRfcConstants.PRE_AUDIT);
		map.put("preaccept", WaybillRfcConstants.PRE_ACCECPT);
		map.put("astatus", ArriveSheetConstants.STATUS_DELIVER);
		map.put("adestroyed", FossConstants.NO);
		map.put("active", FossConstants.YES);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectArrivesheetByDeliverbillById",
				map);
	}
	
	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表大小
	 *  
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表大小
	 * 
	 * @return 派送单明细列表大小
	 * @author 
	 * @date 
	 */
	@Override
	public Long queryCountByDeliverbillId(String deliverbillId) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectCountByDeliverbillId", deliverbillId);
	}
	
	
	@Override
	public List<DeliverbillDetailEntity> queryByDeliverbillId(
			Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByDeliverbillId", map,rowBounds);
	}
	
	
	/**
	 * 根据派送单集合查询派送单明细
	 * @author
	 * @date 
	 * @param 
	 * @return
	 * @see
	 */
	@Override
	public List<DeliverbillDetailDto> queryByDeliverbillNos(DeliverbillNewDto deliverbillNewDto){
		return this.getSqlSession().selectList(NAMESPACE + ".selectByDeliverbillNos",deliverbillNewDto);
	}

	/**
	 * 更具派送单Id查询运单明细
	 */
	@Override
	public List<DeliverbillDetailEntity> queryDeliverbillDetailEntityById(String id) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectDeliverbillDetailByDeliverbillId",id);
	}

}