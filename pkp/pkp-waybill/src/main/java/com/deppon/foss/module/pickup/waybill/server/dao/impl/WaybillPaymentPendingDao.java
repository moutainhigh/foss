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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillPaymentPendingDao.java
 * 
 * FILE NAME        	: WaybillPaymentPendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 待处理运单客户付费明细
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-30 下午7:41:39
 */
public class WaybillPaymentPendingDao extends iBatis3DaoImpl implements IWaybillPaymentPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPaymentPendingEntityMapper.";

	/**
	 * 更新待处理运单客户付费明细
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:42:21
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentPendingDao#updateByPrimaryKey(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity)
	 */
	@Override
	public int updateByPrimaryKey(WaybillPaymentPendingEntity record) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKey", record);
	}

	/**
	 * 保存待处理运单客户付费明细
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:42:11
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentPendingDao#insertWaybillPaymentPending(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity)
	 */
	@Override
	public int addWaybillPaymentPending(WaybillPaymentPendingEntity record) {
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}

	/**
	 * 批量新增运单客户付费明细
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param list
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentPendingDao#addWaybillPaymentPendingBatch(java.util.List)
	 */
	@Override
	public int addWaybillPaymentPendingBatch(List<WaybillPaymentPendingEntity> list) {
		for (WaybillPaymentPendingEntity entity : list) {
		    // 设置UUID
		    entity.setId(UUIDUtils.getUUID());
		}
		return this.getSqlSession().insert(NAMESPACE + "insertBatch",list);
	}
	
	/**
	 * 
	 * 删除
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-30 下午7:12:25
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}
	
	/**
	 * 根据运单号查询运单付费明细
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentPendingDao#queryPaymentPendingByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPaymentPendingEntity> queryPaymentPendingByNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		return  this.getSqlSession().selectList(NAMESPACE + "queryPaymentPendingByNo", parms);
	}
}