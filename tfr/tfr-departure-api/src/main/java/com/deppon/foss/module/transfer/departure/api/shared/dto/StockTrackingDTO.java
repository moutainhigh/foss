/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/StockTrackingDTO.java
 *  
 *  FILE NAME          :StockTrackingDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * 库存轨迹.
 */
public class StockTrackingDTO extends BaseEntity implements  Comparator{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ***********运单号**************. */
	private String waybillNo;        
	
	/** ***********库存部门**************. */
	private String orgCode;     
	
	/** ***********库存部门名称**************. */
	private String orgName;     
	
	/** ***********库存件数**************. */
	private long serialCount;      
	
	/** ***********库存状态**************. */
	private String stockStatus;

	/** ***********操作时间**************. */
	private Date operateTime;
	
	/** ***********交接单号**************. */
	private String billNo;
	
	/** ***********部门的集合**************. */
	private List<String> orgCodes;

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybill no
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the new waybill no
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the serial count.
	 *
	 * @return the serial count
	 */
	public long getSerialCount() {
		return serialCount;
	}

	/**
	 * Sets the serial count.
	 *
	 * @param serialCount the new serial count
	 */
	public void setSerialCount(long serialCount) {
		this.serialCount = serialCount;
	}

	/**
	 * Gets the stock status.
	 *
	 * @return the stock status
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * Sets the stock status.
	 *
	 * @param stockStatus the new stock status
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * Gets the operate time.
	 *
	 * @return the operate time
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * Sets the operate time.
	 *
	 * @param operateTime the new operate time
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * Gets the bill no.
	 *
	 * @return the bill no
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * Sets the bill no.
	 *
	 * @param billNo the new bill no
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * Gets the org name.
	 *
	 * @return the org name
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * Sets the org name.
	 *
	 * @param orgName the new org name
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Gets the org codes.
	 *
	 * @return the org codes
	 */
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**
	 * Sets the org codes.
	 *
	 * @param orgCodes the new org codes
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}



	/**
	 * 用操作时间来排序（倒叙）.
	 *
	 * @param o1 the o1
	 * @param o2 the o2
	 * @return the int
	 */
	@Override
	public int compare(Object o1, Object o2) {
		StockTrackingDTO d1 = (StockTrackingDTO)o1;
		StockTrackingDTO d2 = (StockTrackingDTO)o2;
		if (d1.getOperateTime().after(d2.getOperateTime()))
			return -1;
		if (d2.getOperateTime().after(d1.getOperateTime()))
			return 1;
		return 0;
	}
	
}