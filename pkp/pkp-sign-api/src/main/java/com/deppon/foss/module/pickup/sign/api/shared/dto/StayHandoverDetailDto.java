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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/StayHandoverDetailDto.java
 * 
 * FILE NAME        	: StayHandoverDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;

/**
 * 交接明细dto
 * @author foss-meiying
 * @date 2012-11-28 上午11:41:39
 * @since
 * @version
 */
public class StayHandoverDetailDto implements Serializable{
	private static final long serialVersionUID = -9080817110604417646L;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  件数
	 */
	private Integer goodsQty;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * waybillPending的id 
	 */
	private String id;
	
	/**
	 * 包装备注（图片开单使用）
	 */
	private String packageRemark;
	
	public String getPackageRemark() {
		return packageRemark;
	}

	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * Gets the 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * Sets the 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * Gets the 是否有效.
	 *
	 * @return the 是否有效
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 * Sets the 是否有效.
	 *
	 * @param active the new 是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the waybillPending的id.
	 *
	 * @return the waybillPending的id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the waybillPending的id.
	 *
	 * @param id the new waybillPending的id
	 */
	public void setId(String id) {
		this.id = id;
	}

}