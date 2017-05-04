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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillPaymentDao.java
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
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPaymentDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 付款明细DAO的实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:52:53
 */
public class WaybillPaymentDao implements IWaybillPaymentDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPaymentEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 批量新增付款明细数据
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午2:00:54
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao#addBatch(java.util.List)
	 */
	public void addBatch(List<WaybillPaymentEntity> list) {
		for (WaybillPaymentEntity entity : list) {
			// 设置UUID
			entity.setId(UUIDUtils.getUUID());
			this.sqlSession.insert(NAMESPACE + "insertSelective", entity);
		}
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

		List<WaybillPaymentEntity> list = this.sqlSession.selectList(NAMESPACE + "selectEntityListByNo", args);
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
	@Override
	public List<WaybillPaymentEntity> queryNewWaybillPaymentEntityByNo(LastWaybillRfcQueryDto qeuryDto) {
		return this.sqlSession.selectList(NAMESPACE + "queryNewWaybillPaymentEntityByNo", qeuryDto);
	}

	@Override
	public void updateWaybillPaymentEntity(WaybillPaymentEntity payment) {
		this.sqlSession.update(NAMESPACE + "updateWaybillPayment", payment);
	}

	@Override
	public int deleteWaybillPaymentEntityById(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteWaybillPaymentEntityById", id);
	}
}