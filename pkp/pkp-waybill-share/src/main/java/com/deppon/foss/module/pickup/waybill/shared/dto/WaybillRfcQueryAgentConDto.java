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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcQueryAgentConDto.java
 * 
 * FILE NAME        	: WaybillRfcQueryAgentConDto.java
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
 * 审核代理查询条件Dto
 * @title WaybillRfcQueryAgentConDto.java
 * @package com.deppon.foss.module.pickup.waybill.shared.dto 
 * @author suyujun
 * @version 0.1 2012-12-24
 */
public class WaybillRfcQueryAgentConDto implements Serializable {
	private static final long serialVersionUID = -5671671452712634322L;
	/**
	 * 查询开始时间
	 */
	private Date beginDate;
	/**
	 * 查询结束时间
	 */
	private Date endDate;
	/**
	 * 代理人
	 */
	private String agentCode;
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 状态
	 */
	private String active;
	
	/**
	 * 当前用户Code
	 */
	private String currentEmpCode;
	/**
	 * 当前时间
	 */
	private Date currentDate;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return currentDate : set the property currentDate.
	 */
	public Date getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate : return the property currentDate.
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	/**
	 * @return beginDate : return the property beginDate.
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate : set the property beginDate.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return endDate : return the property endDate.
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate : set the property endDate.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return agentCode : return the property agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode : set the property agentCode.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return status : return the property status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status : set the property status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return currentEmpCode : return the property currentEmpCode.
	 */
	public String getCurrentEmpCode() {
		return currentEmpCode;
	}
	/**
	 * @param currentEmpCode : set the property currentEmpCode.
	 */
	public void setCurrentEmpCode(String currentEmpCode) {
		this.currentEmpCode = currentEmpCode;
	}
	
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

}