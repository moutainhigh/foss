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
 *  FILE PATH          :/SaleBookingSpaceDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SaleBookingSpaceEntity;
/**
 * 营业部订舱dto
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:15:03
 */
public class SaleBookingSpaceDto extends SaleBookingSpaceEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9144529398309545630L;
	/**申请开始时间*/
	private Date createTimeFrom;
	/**申请结束时间*/
	private Date createTimeTo;
	/**受理列表id*/
	List<String> acceptIds;
	/**当前操作部门Code*/
	private String currentDetpCode;
	/**
	 * 航空公司
	 */
	private String airlines;
	
	/**
	 * 获取 航空公司.
	 *
	 * @return the 航空公司
	 */
	public String getAirlines() {
		return airlines;
	}

	/**
	 * 设置 航空公司.
	 *
	 * @param airlines the new 航空公司
	 */
	public void setAirlines(String airlines) {
		this.airlines = airlines;
	}

	/**
	 * 获取 申请开始时间.
	 *
	 * @return the 申请开始时间
	 */
	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}
	
	/**
	 * 设置 申请开始时间.
	 *
	 * @param createTimeFrom the new 申请开始时间
	 */
	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	
	/**
	 * 获取 申请结束时间.
	 *
	 * @return the 申请结束时间
	 */
	public Date getCreateTimeTo() {
		return createTimeTo;
	}
	
	/**
	 * 设置 申请结束时间.
	 *
	 * @param createTimeTo the new 申请结束时间
	 */
	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	
	/**
	 * 获取 受理列表id.
	 *
	 * @return the 受理列表id
	 */
	public List<String> getAcceptIds() {
		return acceptIds;
	}
	
	/**
	 * 设置 受理列表id.
	 *
	 * @param acceptIds the new 受理列表id
	 */
	public void setAcceptIds(List<String> acceptIds) {
		this.acceptIds = acceptIds;
	}
	
	/**
	 * 获取 当前操作部门Code.
	 *
	 * @return the 当前操作部门Code
	 */
	public String getCurrentDetpCode() {
		return currentDetpCode;
	}
	
	/**
	 * 设置 当前操作部门Code.
	 *
	 * @param currentDetpCode the new 当前操作部门Code
	 */
	public void setCurrentDetpCode(String currentDetpCode) {
		this.currentDetpCode = currentDetpCode;
	}
	
}