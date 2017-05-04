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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SignRfcChangeDetailEntity.java
 * 
 * FILE NAME        	: SignRfcChangeDetailEntity.java
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
 * 变更内容明细
 * 
 * @author ibm-lizhiguo
 * @date 2012-11-7 下午2:58:05
 */
public class SignRfcChangeDetailEntity extends BaseEntity{
	/** 
	 * @fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 变更ID
	 */
	private String tSrvSignRfcId;

	/**
	 * 变更项NAME
	 */
	private String rfcItems;

	/**
	 * 变更项CODE
	 */
	private String rfcItemsCode;

	/**
	 * 变更前
	 */
	private String beforeRfcinfo;

	/**
	 * 变更后
	 */
	private String afterRfcinfo;

	/**
	 * 变更类型(0:付款,1:到达联,2:运单签收结果)
	 */
	private String rfcType;
	/**
	 * @return tSrvSignRfcId : return the property tSrvSignRfcId.
	 */
	public String gettSrvSignRfcId() {
		return tSrvSignRfcId;
	}

	/**
	 * @param tSrvSignRfcId : set the property tSrvSignRfcId.
	 */
	public void settSrvSignRfcId(String tSrvSignRfcId) {
		this.tSrvSignRfcId = tSrvSignRfcId;
	}

	/**
	 * @return rfcItems : return the property rfcItems.
	 */
	public String getRfcItems() {
		return rfcItems;
	}

	/**
	 * @param rfcItems : set the property rfcItems.
	 */
	public void setRfcItems(String rfcItems) {
		this.rfcItems = rfcItems;
	}

	/**
	 * @return rfcItemsCode : return the property rfcItemsCode.
	 */
	public String getRfcItemsCode() {
		return rfcItemsCode;
	}

	/**
	 * @param rfcItemsCode : set the property rfcItemsCode.
	 */
	public void setRfcItemsCode(String rfcItemsCode) {
		this.rfcItemsCode = rfcItemsCode;
	}

	/**
	 * @return beforeRfcinfo : return the property beforeRfcinfo.
	 */
	public String getBeforeRfcinfo() {
		return beforeRfcinfo;
	}

	/**
	 * @param beforeRfcinfo : set the property beforeRfcinfo.
	 */
	public void setBeforeRfcinfo(String beforeRfcinfo) {
		this.beforeRfcinfo = beforeRfcinfo;
	}

	/**
	 * @return afterRfcinfo : return the property afterRfcinfo.
	 */
	public String getAfterRfcinfo() {
		return afterRfcinfo;
	}

	/**
	 * @param afterRfcinfo : set the property afterRfcinfo.
	 */
	public void setAfterRfcinfo(String afterRfcinfo) {
		this.afterRfcinfo = afterRfcinfo;
	}

	/**
	 * @return rfcType : return the property rfcType.
	 */
	public String getRfcType() {
		return rfcType;
	}

	/**
	 * @param rfcType : set the property rfcType.
	 */
	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}
}