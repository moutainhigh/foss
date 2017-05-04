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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcChangeDetailDto.java
 * 
 * FILE NAME        	: WaybillRfcChangeDetailDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.*;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeChargeEntity;

/**
 * 
 * 变更明细DTO
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午8:57:17
 */
public class WaybillRfcChangeDetailDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -5496563907808152466L;

	/**
	 * WaybillInfoVo变更属性
	 */
	private String propertyName;

	/**
	 * 是否金额变更
	 */
	private String chargeChange;

	/**
	 * 是否显示
	 */
	private String visible;

	/**
	 * 运单变更ID
	 */
	private String tSrvWaybillRfcId;

	/**
	 * 变更项
	 */
	private String rfcItems;

	/**
	 * 变更前信息
	 */
	private String beforeRfcInfo;

	/**
	 * 变更后信息
	 */
	private String afterRfcInfo;

	/**
	 * 含金额变更明细
	 */
	private List<WaybillRfcChangeChargeEntity> rfcChangeChargeEntities;

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName
	 *            the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the chargeChange
	 */
	public String getChargeChange() {
		return chargeChange;
	}

	/**
	 * @param chargeChange
	 *            the chargeChange to set
	 */
	public void setChargeChange(String chargeChange) {
		this.chargeChange = chargeChange;
	}

	/**
	 * @return the visible
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/**
	 * @return the tSrvWaybillRfcId
	 */
	public String gettSrvWaybillRfcId() {
		return tSrvWaybillRfcId;
	}

	/**
	 * @param tSrvWaybillRfcId
	 *            the tSrvWaybillRfcId to set
	 */
	public void settSrvWaybillRfcId(String tSrvWaybillRfcId) {
		this.tSrvWaybillRfcId = tSrvWaybillRfcId;
	}

	/**
	 * @return the rfcItems
	 */
	public String getRfcItems() {
		return rfcItems;
	}

	/**
	 * @param rfcItems
	 *            the rfcItems to set
	 */
	public void setRfcItems(String rfcItems) {
		this.rfcItems = rfcItems;
	}

	/**
	 * @return the beforeRfcInfo
	 */
	public String getBeforeRfcInfo() {
		return beforeRfcInfo;
	}

	/**
	 * @param beforeRfcInfo
	 *            the beforeRfcInfo to set
	 */
	public void setBeforeRfcInfo(String beforeRfcInfo) {
		this.beforeRfcInfo = beforeRfcInfo;
	}

	/**
	 * @return the afterRfcInfo
	 */
	public String getAfterRfcInfo() {
		return afterRfcInfo;
	}

	/**
	 * @param afterRfcInfo
	 *            the afterRfcInfo to set
	 */
	public void setAfterRfcInfo(String afterRfcInfo) {
		this.afterRfcInfo = afterRfcInfo;
	}

	/**
	 * @return the rfcChangeChargeEntities
	 */
	public List<WaybillRfcChangeChargeEntity> getRfcChangeChargeEntities() {
		return rfcChangeChargeEntities;
	}

	/**
	 * @param rfcChangeChargeEntities
	 *            the rfcChangeChargeEntities to set
	 */
	public void setRfcChangeChargeEntities(
			List<WaybillRfcChangeChargeEntity> rfcChangeChargeEntities) {
		this.rfcChangeChargeEntities = rfcChangeChargeEntities;
	}

}