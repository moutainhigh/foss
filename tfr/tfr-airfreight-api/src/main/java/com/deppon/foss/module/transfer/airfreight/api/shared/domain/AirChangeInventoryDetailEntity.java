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
 *  FILE PATH          :/AirChangeInventoryDetailEntity.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 清单变更 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-13 上午8:57:46
 */
public class AirChangeInventoryDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -1180504320467220053L;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 修改时间
	 */
	private Date createTime;
	/**
	 * 变更日期
	 */
	private Date operationTime;
	/**
	 * 工号
	 */
	private String operatorCode;
	/**
	 * 提货网点
	 */
	private String arrvRegionName;
	/**
	 * 送货费
	 */
	private BigDecimal deliverFee;
	/**
	 * 到服费
	 */
	private BigDecimal arrivalFee;
	/**
	 * 变更事项
	 */
	private String reviseContent;
	/**
 	 * 运单类型 AP:合大票  TP：中转
	 */ 
	private String billType;
	
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
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 修改时间.
	 *
	 * @param createTime the new 修改时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 变更日期.
	 *
	 * @return the 变更日期
	 */
	public Date getOperationTime() {
		return operationTime;
	}
	
	/**
	 * 设置 变更日期.
	 *
	 * @param operationTime the new 变更日期
	 */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	/**
	 * 获取 工号.
	 *
	 * @return the 工号
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	
	/**
	 * 设置 工号.
	 *
	 * @param operatorCode the new 工号
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	/**
	 * 获取 提货网点.
	 *
	 * @return the 提货网点
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 提货网点.
	 *
	 * @param arrvRegionName the new 提货网点
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 送货费.
	 *
	 * @return the 送货费
	 */
	public BigDecimal getDeliverFee() {
		return deliverFee;
	}
	
	/**
	 * 设置 送货费.
	 *
	 * @param deliverFee the new 送货费
	 */
	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}
	
	/**
	 * 获取 到服费.
	 *
	 * @return the 到服费
	 */
	public BigDecimal getArrivalFee() {
		return arrivalFee;
	}
	
	/**
	 * 设置 到服费.
	 *
	 * @param arrivalFee the new 到服费
	 */
	public void setArrivalFee(BigDecimal arrivalFee) {
		this.arrivalFee = arrivalFee;
	}
	
	/**
	 * 获取 变更事项.
	 *
	 * @return the 变更事项
	 */
	public String getReviseContent() {
		return reviseContent;
	}
	
	/**
	 * 设置 变更事项.
	 *
	 * @param reviseContent the new 变更事项
	 */
	public void setReviseContent(String reviseContent) {
		this.reviseContent = reviseContent;
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

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	
}