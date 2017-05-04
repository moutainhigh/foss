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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillPaymentDao.java
 * 
 * FILE NAME        	: IWaybillPaymentDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.dao
 * FILE    NAME: IWaybillPaymentDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;


/**
 * 运单付款明细DAO接口
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 下午1:51:27
 */
public interface IWaybillPaymentDao extends IService {
	/**
	 * 批量新增付款明细数据
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午2:00:28
	 */
	int addBatch(List<WaybillPaymentEntity> waybillPaymentList);

	/**
	 * 根据运单号查询折扣明细列表
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 下午5:42:47
	 */
	List<WaybillPaymentEntity> queryPaymentEntityByNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 查询新版运单付款明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-1
	 * @param queryDto
	 * @return
	 * List<WaybillPaymentEntity>
	 */
	List<WaybillPaymentEntity> queryNewWaybillPaymentEntityByNo(
			LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 更新付款对象<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param payment
	 * void
	 */
	void updateWaybillPaymentEntity(WaybillPaymentEntity payment);

	/**
	 * 
	 * <p>
	 * 删除付款对象<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * @return
	 * int
	 */
	int deleteWaybillPaymentEntityById(String id);

	int deleteWaybillPaymentEntityByWaybillNo(String waybillNo);
}