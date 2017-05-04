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
 *  FILE PATH          :/AirWayBillDto.java
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

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirWayBillVO;
/**
 * 航空正单DTO
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:58:24
 */
public class AirWayBillDto implements Serializable {

	private static final long serialVersionUID = -1366083392958910871L;
	
	/**
	 * 航空正单实体
	 */
	private AirWaybillEntity optAirWaybillEntity;
	/**
	 * 航空正单ID
	 */
	private String airwaybillId;
	/**
	 * 制单时间开始时间
	 */
	private Date beginInTime;
	/**
	 * 制单结果时间
	 */
	private Date endInTime;
	/**
	 * 航空公司
	 */
	private String airlineTwoletter;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 目的站
	 */
	private String arrvRegionCode;
	/**
	 * 配载类型
	 */
	private String airAssembleType;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;
	/**
	 * 制单人名称
	 */
	private String createUserName;
	/**
	 * 付款状态
	 */
	private String paymentStatus;

	/**
	 * 制单部门
	 */
	private String createOrgName;
	/**
	 * 制单部门编码
	 */
	private String createOrgCode;
	
	/**
	 * 运输性质
	 * */
	private String transportType;
	
	private AirWayBillVO airWayBillVO = new AirWayBillVO();
	
	/**
	 * 获取 航空正单实体.
	 *
	 * @return the 航空正单实体
	 */
	public AirWaybillEntity getOptAirWaybillEntity() {
		return optAirWaybillEntity;
	}
	
	/**
	 * 设置 航空正单实体.
	 *
	 * @param optAirWaybillEntity the new 航空正单实体
	 */
	public void setOptAirWaybillEntity(AirWaybillEntity optAirWaybillEntity) {
		this.optAirWaybillEntity = optAirWaybillEntity;
	}
	
	/**
	 * 获取 航空正单ID.
	 *
	 * @return the 航空正单ID
	 */
	public String getAirwaybillId() {
		return airwaybillId;
	}
	
	/**
	 * 设置 航空正单ID.
	 *
	 * @param airwaybillId the new 航空正单ID
	 */
	public void setAirwaybillId(String airwaybillId) {
		this.airwaybillId = airwaybillId;
	}
	
	/**
	 * 获取 制单时间开始时间.
	 *
	 * @return the 制单时间开始时间
	 */
	public Date getBeginInTime() {
		return beginInTime;
	}
	
	/**
	 * 设置 制单时间开始时间.
	 *
	 * @param beginInTime the new 制单时间开始时间
	 */
	public void setBeginInTime(Date beginInTime) {
		this.beginInTime = beginInTime;
	}
	
	/**
	 * 获取 制单结果时间.
	 *
	 * @return the 制单结果时间
	 */
	public Date getEndInTime() {
		return endInTime;
	}
	
	/**
	 * 设置 制单结果时间.
	 *
	 * @param endInTime the new 制单结果时间
	 */
	public void setEndInTime(Date endInTime) {
		this.endInTime = endInTime;
	}
	
	/**
	 * 获取 航空公司.
	 *
	 * @return the 航空公司
	 */
	public String getAirlineTwoletter() {
		return airlineTwoletter;
	}
	
	/**
	 * 设置 航空公司.
	 *
	 * @param airlineTwoletter the new 航空公司
	 */
	public void setAirlineTwoletter(String airlineTwoletter) {
		this.airlineTwoletter = airlineTwoletter;
	}
	
	/**
	 * 获取 正单号.
	 *
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	
	/**
	 * 设置 正单号.
	 *
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	
	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionCode the new 目的站
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
	/**
	 * 获取 配载类型.
	 *
	 * @return the 配载类型
	 */
	public String getAirAssembleType() {
		return airAssembleType;
	}
	
	/**
	 * 设置 配载类型.
	 *
	 * @param airAssembleType the new 配载类型
	 */
	public void setAirAssembleType(String airAssembleType) {
		this.airAssembleType = airAssembleType;
	}

	/**
	 * 获取 目的站名称.
	 *
	 * @return the 目的站名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的站名称.
	 *
	 * @param arrvRegionName the new 目的站名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 制单人名称.
	 *
	 * @return the 制单人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 制单人名称.
	 *
	 * @param createUserName the new 制单人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 付款状态.
	 *
	 * @return the 付款状态
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置 付款状态.
	 *
	 * @param paymentStatus the new 付款状态
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public AirWayBillVO getAirWayBillVO() {
		return airWayBillVO;
	}

	/**
	 * 
	 *
	 * @param airWayBillVO 
	 */
	public void setAirWayBillVO(AirWayBillVO airWayBillVO) {
		this.airWayBillVO = airWayBillVO;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

}