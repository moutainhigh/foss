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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/TrackStockDto.java
 * 
 * FILE NAME        	: TrackStockDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 运单库存信息Dto
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 5:07:55 PM
 */
public class TrackStockDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -7924156065349533246L;

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
	 * 获取 ***********运单号**************.
	 *
	 * @return the ***********运单号**************
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 ***********运单号**************.
	 *
	 * @param waybillNo the new ***********运单号**************
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 ***********库存部门**************.
	 *
	 * @return the ***********库存部门**************
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 ***********库存部门**************.
	 *
	 * @param orgCode the new ***********库存部门**************
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 ***********库存部门名称**************.
	 *
	 * @return the ***********库存部门名称**************
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置 ***********库存部门名称**************.
	 *
	 * @param orgName the new ***********库存部门名称**************
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取 ***********库存件数**************.
	 *
	 * @return the ***********库存件数**************
	 */
	public long getSerialCount() {
		return serialCount;
	}

	/**
	 * 设置 ***********库存件数**************.
	 *
	 * @param serialCount the new ***********库存件数**************
	 */
	public void setSerialCount(long serialCount) {
		this.serialCount = serialCount;
	}

	/**
	 * 获取 ***********库存状态**************.
	 *
	 * @return the ***********库存状态**************
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置 ***********库存状态**************.
	 *
	 * @param stockStatus the new ***********库存状态**************
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * 获取 ***********操作时间**************.
	 *
	 * @return the ***********操作时间**************
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * 设置 ***********操作时间**************.
	 *
	 * @param operateTime the new ***********操作时间**************
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 获取 ***********交接单号**************.
	 *
	 * @return the ***********交接单号**************
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置 ***********交接单号**************.
	 *
	 * @param billNo the new ***********交接单号**************
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 获取 ***********部门的集合**************.
	 *
	 * @return the ***********部门的集合**************
	 */
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**
	 * 设置 ***********部门的集合**************.
	 *
	 * @param orgCodes the new ***********部门的集合**************
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}