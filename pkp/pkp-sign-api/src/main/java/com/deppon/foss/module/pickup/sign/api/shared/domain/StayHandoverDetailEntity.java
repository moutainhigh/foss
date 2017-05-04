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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/StayHandoverDetailEntity.java
 * 
 * FILE NAME        	: StayHandoverDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 交接明细
 * @author foss-meiying
 * @date 2012-10-30 上午10:05:26
 * @since
 * @version
 */
public class StayHandoverDetailEntity extends BaseEntity {
	private static final long serialVersionUID = -1296636090338031180L;
	/**
	 *  交接_id
	 */
	private String tSrvStayHandoverId;
	/**
	 *  运单号
	 */
	private String waybillNo;
	/**
	 *  件数
	 */
	private Integer goodsQty;
	/**
	 * 流水号
	 */
    private String serialNo;
    /**
     * 派送单编号
     */
    private String deliverbillNo;

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
	 * @return the tSrvStayHandoverId
	 */
	public String gettSrvStayHandoverId() {
		return tSrvStayHandoverId;
	}

	/**
	 * @param tSrvStayHandoverId the tSrvStayHandoverId to see
	 */
	public void settSrvStayHandoverId(String tSrvStayHandoverId) {
		this.tSrvStayHandoverId = tSrvStayHandoverId;
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
	 * Gets the 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the 派送单编号.
	 *
	 * @return the 派送单编号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单编号.
	 *
	 * @param deliverbillNo the new 派送单编号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

}