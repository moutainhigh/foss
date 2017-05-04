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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillPaymentService.java
 * 
 * FILE NAME        	: IWaybillPaymentService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: IWaybillPaymentService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;


/**
 * 付款明细服务接口
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 上午11:53:54
 */
public interface IWaybillPaymentService extends IService {
    /**
     * 批量新增付款明细实体
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    int addWaybillPaymentEntityBatch(List<WaybillPaymentEntity> waybillPaymentList,WaybillSystemDto systemDto );
   
    /**
     * 
     * 更改单起草后追加付款明细实体
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午4:08:02
     */
    int appendWaybillPaymentEntityBatchAfterChange(List<WaybillPaymentEntity> waybillPaymentList,WaybillSystemDto systemDto );

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
	List<WaybillPaymentEntity> queryNewWaybillPaymentEntityByNo(LastWaybillRfcQueryDto queryDto);

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
	 * 删除付款明细
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * void
	 */
	int deleteWaybillPaymentEntityById(String id);

	/**
	 * 查询付款信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param waybillNo
	 * @return
	 * @return List<WaybillPaymentEntity>
	 * @see
	 */
	List<WaybillPaymentEntity> queryWaybillPayment(String waybillNo);

	int deleteWaybillPaymentEntityByWaybillNo(String waybillNo);
}