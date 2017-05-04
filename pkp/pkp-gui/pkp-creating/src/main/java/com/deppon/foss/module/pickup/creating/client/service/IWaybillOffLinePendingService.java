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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/IWaybillOffLinePendingService.java
 * 
 * FILE NAME        	: IWaybillOffLinePendingService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;


/**
 * 
 * 运单 offline submit service
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public interface IWaybillOffLinePendingService {
	

	/**
	 * 
	 * 运单提交
	 * @author 025000-FOSS-helong
	 * @date 2012-12-26 下午07:59:50
	 * @param waybillDto
	 */
	void tempSave(WaybillPendingDto waybillDto);
	
	/**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:46:24
	 */
	WaybillPendingEntity queryPendingByNo(String waybillNo);
	
	/**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:46:24
	 */
	WaybillPendingEntity queryPendingByWaybillNoAndOrderNo(String waybillNo, String orderNo);

	/**
	 * 通过运单ID查询待处理运单付款信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午10:33:17
	 */
	List<WaybillPaymentPendingEntity> queryPaymentPendingByNo(String waybillNo);

	/**
	 * 通过运单ID查询待处理运单折扣信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:07:40
	 */
	List<WaybillDisDtlPendingEntity> queryDisDtlPendingByNo(String waybillNo);

	/**
	 * 通过运单ID查询待处理费用信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:53:24
	 */
	List<WaybillCHDtlPendingEntity> queryCHDtlPendingByNo(String id);

	/**
	 * 通过运单号查询待处理运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午2:57:48
	 */
	WaybillPendingDto queryPendingWaybillByNo(String waybillNo);



	/**
	 * 根据运单号查询打木架信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 上午9:26:55
	 */
	WoodenRequirePendingEntity queryWoodenRequireByNo(String waybillNo);

	
	/**
	 * 
	 * 判断单号是否存在
	 * @author 025000-FOSS-helong
	 * @date 2013-1-11 下午03:24:47
	 * @param mixNo
	 * @return
	 */
	public boolean isWayBillExsits(String mixNo);

	
	/**
	 * <p>
	 * (查询运单待处理表)
	 * </p>
	 * 
	 * @author 105089-025000-foss-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);
	
	/**
	 * <p>
	 * (查询运单待处理表)
	 * </p>
	 * 
	 * @author 105089-025000-foss-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	List<WaybillPendingEntity> queryPendingExpress(WaybillPendingDto waybillPendingDto);
	
	
	
	/**
	  离线运单保存
	 * 
	 * @author 105089-025000-foss-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	int insert(WaybillPendingEntity waybillPending);
	
	/**
	  离线运单删除
	 * 
	 * @author 105089-025000-foss-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	 void deleteByPrimaryKey(String id);

	/**
	 * 与queryPending调用的DAO是一样的，只是为了返回不一样的实体类型
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 上午10:52:27
	 * @see com.deppon.foss.module.pickup.creating.client.service.IWaybillOffLinePendingService#queryPending(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto)
	 */
	List<WaybillOfflineEntity> queryOfflinePending(WaybillPendingDto waybillPendingDto);

}