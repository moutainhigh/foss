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
 *  FILE PATH          :/AirWaybillSerialNoEntity.java
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

import java.io.Serializable;
import java.util.Date;
/**
 * 航空正单流水明细
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午3:48:13
 */
public class OppNeedAirWaybillSerialNoEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2282731757656457196L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 航空正单明细ID
	 */
	private String airWaybillDetailId;
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 生成流水号时间
	 */
	private Date createTime;
	
	/**
	 * 运单号
	 */
	private String waybillNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirWaybillDetailId() {
		return airWaybillDetailId;
	}

	public void setAirWaybillDetailId(String airWaybillDetailId) {
		this.airWaybillDetailId = airWaybillDetailId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
}