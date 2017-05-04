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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirCargovolumeSerialnoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 航空货量流水号明细
 * @author 099197-foss-zhoudejun
 * @date 2012-10-22 下午2:17:01
 */
public class AirCargovolumeSerialnoEntity extends BaseEntity{

	private static final long serialVersionUID = 4914296991342688614L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 是否和票
	 */
	private String beAssemble;
	/**
	 * 操作状态
	 */
	private String operatingStatus;
	/**
	 * 空运货量明细ID
	 */
	private String airCargovolumeId;
	/**
	 * 开单时间
	 */
	private Date createTime;
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 是否和票.
	 *
	 * @return the 是否和票
	 */
	public String getBeAssemble() {
		return beAssemble;
	}
	
	/**
	 * 设置 是否和票.
	 *
	 * @param beAssemble the new 是否和票
	 */
	public void setBeAssemble(String beAssemble) {
		this.beAssemble = beAssemble;
	}
	
	/**
	 * 获取 操作状态.
	 *
	 * @return the 操作状态
	 */
	public String getOperatingStatus() {
		return operatingStatus;
	}
	
	/**
	 * 设置 操作状态.
	 *
	 * @param operatingStatus the new 操作状态
	 */
	public void setOperatingStatus(String operatingStatus) {
		this.operatingStatus = operatingStatus;
	}
	
	/**
	 * 获取 空运货量明细ID.
	 *
	 * @return the 空运货量明细ID
	 */
	public String getAirCargovolumeId() {
		return airCargovolumeId;
	}
	
	/**
	 * 设置 空运货量明细ID.
	 *
	 * @param airCargovolumeId the new 空运货量明细ID
	 */
	public void setAirCargovolumeId(String airCargovolumeId) {
		this.airCargovolumeId = airCargovolumeId;
	}
	
	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 开单时间.
	 *
	 * @param createTime the new 开单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}