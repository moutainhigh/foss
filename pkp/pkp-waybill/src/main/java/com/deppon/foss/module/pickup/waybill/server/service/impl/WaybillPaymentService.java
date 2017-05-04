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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillPaymentService.java
 * 
 * FILE NAME        	: WaybillPaymentService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.service.impl
 * FILE    NAME: WaybillPaymentService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPaymentDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运单付款服务实现类
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:49:17
 */
public class WaybillPaymentService implements IWaybillPaymentService {

	// 运单付款信息DAO
	private IWaybillPaymentDao waybillPaymentDao;

	/**
	 * 
	 * 注入运单付款信息DAO
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:23:44
	 */
	public void setWaybillPaymentDao(IWaybillPaymentDao waybillPaymentDao) {
		this.waybillPaymentDao = waybillPaymentDao;
	}

	/**
	 * 批量新增付款明细实体
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午1:49:17
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPaymentService#addWaybillPaymentEntityBatch(java.util.List)
	 */
	@Override
	public int addWaybillPaymentEntityBatch(List<WaybillPaymentEntity> waybillPaymentList, WaybillSystemDto systemDto) {
		for (WaybillPaymentEntity entity : waybillPaymentList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.ACTIVE);
		}
		return waybillPaymentDao.addBatch(waybillPaymentList);
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
	public List<WaybillPaymentEntity> queryNewWaybillPaymentEntityByNo(LastWaybillRfcQueryDto queryDto) {
		return waybillPaymentDao.queryNewWaybillPaymentEntityByNo(queryDto);
	}

	/**
	 * 
	 * <p>
	 * 更新付款对象<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param payment
	 *            void
	 */
	@Override
	public void updateWaybillPaymentEntity(WaybillPaymentEntity payment) {
		waybillPaymentDao.updateWaybillPaymentEntity(payment);
	}

	/**
	 * 
	 * <p>
	 * 删除付款明细
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 *            void
	 */
	@Override
	public int deleteWaybillPaymentEntityById(String id) {
		return waybillPaymentDao.deleteWaybillPaymentEntityById(id);
	}
	
	@Override
	public int deleteWaybillPaymentEntityByWaybillNo(String waybillNo) {
		return waybillPaymentDao.deleteWaybillPaymentEntityByWaybillNo(waybillNo);
	}

	/**
	 * 
	 * 更改单起草后追加付款明细实体
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-3 下午4:08:02
	 */
	@Override
	public int appendWaybillPaymentEntityBatchAfterChange(List<WaybillPaymentEntity> waybillPaymentList, WaybillSystemDto systemDto) {
		for (WaybillPaymentEntity entity : waybillPaymentList) {
			entity.setId(UUIDUtils.getUUID());
			// 设置创建人、创建时间、更新人、更新时间
			entity.setCreateTime(systemDto.getCreateTime());
			entity.setModifyTime(systemDto.getModifyTime());
			entity.setBillTime(systemDto.getBillTime());
			// 设置是否有效
			entity.setActive(FossConstants.INACTIVE);
		}
		return waybillPaymentDao.addBatch(waybillPaymentList);
	}

	/**
	 * 查询付款信息
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param waybillNo
	 * @return
	 * @return List<WaybillPaymentEntity>
	 * @see
	 */
	@Override
	public List<WaybillPaymentEntity> queryWaybillPayment(String waybillNo) {
		return waybillPaymentDao.queryPaymentEntityByNo(waybillNo);
	}

}