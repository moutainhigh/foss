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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/AirAgencyDto.java
 * 
 * FILE NAME        	: AirAgencyDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 
 * 批量签收
 * @author foss-meiying
 * @date 2014-11-25 上午10:09:57
 * @since
 * @version
 */
public class BatchSignDto implements Serializable  {
	//序列
	private static final long serialVersionUID = -6522770559854851487L;
	/**
	 *  运单签收结果Entity
	 */
	private WaybillSignResultEntity waybillSignResultEntity;
	/**
	 * 并发控制  --
	 * 		到达未出库件数
	 */
	private Integer oldArriveNotoutGoodsQty;
	public WaybillSignResultEntity getWaybillSignResultEntity() {
		return waybillSignResultEntity;
	}
	public void setWaybillSignResultEntity(
			WaybillSignResultEntity waybillSignResultEntity) {
		this.waybillSignResultEntity = waybillSignResultEntity;
	}
	public Integer getOldArriveNotoutGoodsQty() {
		return oldArriveNotoutGoodsQty;
	}
	public void setOldArriveNotoutGoodsQty(Integer oldArriveNotoutGoodsQty) {
		this.oldArriveNotoutGoodsQty = oldArriveNotoutGoodsQty;
	}
	
}