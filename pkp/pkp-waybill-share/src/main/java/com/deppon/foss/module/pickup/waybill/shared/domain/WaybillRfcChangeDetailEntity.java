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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcChangeDetailEntity.java
 * 
 * FILE NAME        	: WaybillRfcChangeDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 运单变更明细
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:45:31
 */
public class WaybillRfcChangeDetailEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -489527508621179849L;

	/**
	 * 运单变更ID
	 */
	private String waybillRfcId;

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
	 * 是否允许显示
	 */
	private String visiable;

	/**
	 * 变更项名称
	 */
	private String rfcItemsName;

	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	/**
	 * @param waybillRfcId
	 *            the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
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
	 * @return the visiable
	 */
	public String getVisiable() {
		return visiable;
	}

	/**
	 * @param visiable
	 *            the visiable to set
	 */
	public void setVisiable(String visiable) {
		this.visiable = visiable;
	}

	/**
	 * @return the rfcItemsName
	 */
	public String getRfcItemsName() {
		return rfcItemsName;
	}

	/**
	 * @param rfcItemsName
	 *            the rfcItemsName to set
	 */
	public void setRfcItemsName(String rfcItemsName) {
		this.rfcItemsName = rfcItemsName;
	}

}