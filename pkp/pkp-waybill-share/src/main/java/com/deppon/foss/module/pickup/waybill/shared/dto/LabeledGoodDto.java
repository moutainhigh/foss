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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/LabeledGoodDto.java
 * 
 * FILE NAME        	: LabeledGoodDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 货签
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-7 下午12:05:41,content:TODO
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-7 下午12:05:41
 * @since
 * @version
 */
public class LabeledGoodDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1326697563394972158L;

	// 运单号
	private String waybillNo;

	// 流水号
	private String serialNo;

	// 关联原单号流水号
	private String oldSerialNo;

	// 件数变动事项
	private String numberChangItems;

	// 是否开单初始值
	private String initalVale;

	// 是否需要打木架
	private String isNeedWooden;
	
	// 开单时间
	private Date billTime;

	//历史变更冗余表id
	private String labledGoodChangeId;
	
	//包装类型
	private String packageType;
	
	
	/**
	 * @return the labledGoodChangeId
	 */
	public String getLabledGoodChangeId() {
		return labledGoodChangeId;
	}


	/**
	 * @param labledGoodChangeId the labledGoodChangeId to set
	 */
	public void setLabledGoodChangeId(String labledGoodChangeId) {
		this.labledGoodChangeId = labledGoodChangeId;
	}


	/**
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 *@param waybillNo the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	
	/**
	 * @return the serialNo .
	 */
	public String getSerialNo() {
		return serialNo;
	}

	
	/**
	 *@param serialNo the serialNo to set.
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	
	/**
	 * @return the oldSerialNo .
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}

	
	/**
	 *@param oldSerialNo the oldSerialNo to set.
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
	 *@param numberChangItems the numberChangItems to set.
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
	 *@param initalVale the initalVale to set.
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
	 *@param isNeedWooden the isNeedWooden to set.
	 */
	public void setIsNeedWooden(String isNeedWooden) {
		this.isNeedWooden = isNeedWooden;
	}

	
	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}

	
	/**
	 *@param billTime the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}


	public String getPackageType() {
		return packageType;
	}


	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	
}