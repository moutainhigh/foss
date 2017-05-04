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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/IWaybillStockService.java
 * 
 * FILE NAME        	: IWaybillStockService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;



 
/**
 * 运单入库接口
 * @author 026113-foss-linwensheng
 * @date 2012-11-5 下午1:49:17
 */
public interface IWaybillStockService {
	
	    
  /**
   * 运单入库
   * @param waybillNo
   * @param serialNos
   */
	void saveWaybillStockService(ActualFreightEntity actualFreightEntity,WaybillEntity waybillEntry,
	    List<String> serialNos,UserEntity userEntity);
	
	
	
	
	 /**
	   * 获取最终库存部门和货区
	   * @param actualFreightEntity，waybillEntity
	   * @param serialNos
	   */
	ActualFreightEntity getEndStockCodeAndAreaCode(ActualFreightEntity actualFreightEntity,WaybillEntity waybillEntity);



	/**
	 * 批处理，入库操作
	 * @author 026113-foss-linwensheng
	 * @date 2013-02-18 上午9:17:13
	 */
	void batchjobs();



	




	/**
	 * 运单保存时，保存待处理表
	 * @author 026113-foss-linwensheng
	 * @date 2013-02-18 上午9:17:13
	 */
	void addWaybillStock(ActualFreightEntity actualFreightEntity,
			WaybillEntity waybillEntry,
			UserEntity userEntity);
	
	
	
	/**
	 * 获取异步信息
	 * @author 087584-foss-WangQianJin
	 * @date 2013-3-21 下午4:48:39
	 */
	List<WayBillNoLocusDTO> queryWaybillStockDtoByWaybillNo(String waybillNo);
	
	/**
	 * 获取最终库存部门
	 * @return
	 */
	String getEndStockCode(WaybillEntity waybillEntity);




	ActualFreightEntity getEndStockCodeAndAreaCodedhf(
			ActualFreightEntity actualFreightEntity,
			WaybillPendingEntity waybillEntity);
}