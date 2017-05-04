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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillPaymentDao.java
 * 
 * FILE NAME        	: WaybillPaymentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.dao.impl
 * FILE    NAME: WaybillPaymentDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 付款明细DAO的实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:52:53
 */
public class WaybillPaymentDao extends iBatis3DaoImpl implements
		IWaybillPaymentDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPaymentEntityMapper.";

	/**
	 * 批量新增付款明细数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午2:00:54
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao#addBatch(java.util.List)
	 */
	public int addBatch(List<WaybillPaymentEntity> list) {
		return this.getSqlSession().insert(NAMESPACE + "insertBatch", list);
	}

	/**
	 * 根据运单号查询折扣明细列表
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午5:38:57
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDisDtlDao#queryDisDtlEntityByNo(java.lang.String)
	 */
	@Override
	public List<WaybillPaymentEntity> queryPaymentEntityByNo(String waybillNo) {
		// 封装参数
		WaybillQueryArgsDto args = new WaybillQueryArgsDto();
		args.setWaybillNo(waybillNo);
		args.setActive(FossConstants.ACTIVE);

		@SuppressWarnings("unchecked")
		List<WaybillPaymentEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "selectEntityListByNo", args);
		return list;
	}

	/**
	 * 
	 * <p>
	 * 查询新版运单付款明细<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param waybillNo
	 * @return List<WaybillPaymentEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPaymentEntity> queryNewWaybillPaymentEntityByNo(
			LastWaybillRfcQueryDto qeuryDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + "queryNewWaybillPaymentEntityByNo", qeuryDto);
	}

	/**
	 * 更新运单付款明细
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param payment
	 * @@see 
	 *       com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao
	 *       #updateWaybillPaymentEntity(com.deppon.foss.module.pickup.waybill.
	 *       shared.domain.WaybillPaymentEntity)
	 */
	@Override
	public void updateWaybillPaymentEntity(WaybillPaymentEntity payment) {
		this.getSqlSession()
				.update(NAMESPACE + "updateWaybillPayment", payment);
	}

	/**
	 * 删除运单付款明细
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao#deleteWaybillPaymentEntityById(java.lang.String)
	 */
	@Override
	public int deleteWaybillPaymentEntityById(String id) {
		return this.getSqlSession().delete(
				NAMESPACE + "deleteWaybillPaymentEntityById", id);
	}
	@Override
	public int deleteWaybillPaymentEntityByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(
				NAMESPACE + "deleteWaybillPaymentEntityByWaybillNo", waybillNo);
	}
}