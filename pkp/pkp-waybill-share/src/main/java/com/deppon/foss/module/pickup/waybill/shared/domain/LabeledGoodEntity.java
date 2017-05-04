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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/LabeledGoodEntity.java
 * 
 * FILE NAME        	: LabeledGoodEntity.java
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

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * "货签信息"实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:李凤腾,date:2012-10-11 上午9:27:43
 * </p>
 * 
 * @author 李凤腾
 * @date 2012-10-11 上午9:27:43
 * @since
 * @version
 */
public class LabeledGoodEntity extends BaseEntity {

	/**
	 * 类的序列货ID
	 */
	private static final long serialVersionUID = -6540348441761837981L;

	/**
	 * 打印流水号
	 */
	private String serialNo;

	/**
	 * 状态
	 */
	private String active;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 开单时间
	 */
	private Date billTime;

	/**
	 * 关联原单号流水号
	 */
	private String oldSerialNo;

	/**
	 * 件数变动事项
	 */
	private String numberChangItems;

	/**
	 * 是否开单初始值
	 */
	private String initalVale;

	/**
	 * 是否需要打木架
	 */
	private String isNeedWooden;
	
	/**
	 * 包装类型：托=SALVER
	 */
	private String packageType;

	/**
	 * @return the serialNo .
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set.
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime
	 *            the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the oldSerialNo .
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}

	/**
	 * @param oldSerialNo
	 *            the oldSerialNo to set.
	 */
	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
	}

	/**
	 * @return the numberChangItems .
	 */
	public String getNumberChangItems() {
		return numberChangItems;
	}

	/**
	 * @param numberChangItems
	 *            the numberChangItems to set.
	 */
	public void setNumberChangItems(String numberChangItems) {
		this.numberChangItems = numberChangItems;
	}

	/**
	 * @return the initalVale .
	 */
	public String getInitalVale() {
		return initalVale;
	}

	/**
	 * @param initalVale
	 *            the initalVale to set.
	 */
	public void setInitalVale(String initalVale) {
		this.initalVale = initalVale;
	}

	/**
	 * @return the isNeedWooden .
	 */
	public String getIsNeedWooden() {
		return isNeedWooden;
	}

	/**
	 * @param isNeedWooden
	 *            the isNeedWooden to set.
	 */
	public void setIsNeedWooden(String isNeedWooden) {
		this.isNeedWooden = isNeedWooden;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

}