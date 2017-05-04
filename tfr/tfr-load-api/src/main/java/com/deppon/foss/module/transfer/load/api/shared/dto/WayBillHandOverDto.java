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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/WayBillHandOverDto.java
 *  
 *  FILE NAME          :WayBillHandOverDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: WayBillHandOverDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 接送货接口，根据运单号查询交接情况
 * @author dp-duyi
 * @date 2013-1-5 上午10:37:51
 */
public class WayBillHandOverDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4954824361756704762L;
	/**保存时间*/
	private Date createTime;
	/**出发时间*/
	private Date leaveTime;
	/**到达时间*/
	private Date arriveTime;
	/**交接编号*/
	private String handOverNo;
	/**出发部门编码*/
	private String origOrgCode;
	/**出发部门名称*/
	private String origOrgName;
	/**到达部门编码*/
	private String destOrgCode;
	/**到达部门名称*/
	private String destOrgName;
	/**是否预配*/
	private String isPreHandOverBill;
	/**交接类型*/
	private String handOverType;
	/**已配件数*/
	private String goodsQty;
	
	/**
	 * Gets the 保存时间.
	 *
	 * @return the 保存时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the 保存时间.
	 *
	 * @param createTime the new 保存时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the 出发时间.
	 *
	 * @return the 出发时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * Sets the 出发时间.
	 *
	 * @param leaveTime the new 出发时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	/**
	 * Gets the 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * Sets the 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * Gets the 交接编号.
	 *
	 * @return the 交接编号
	 */
	public String getHandOverNo() {
		return handOverNo;
	}
	
	/**
	 * Sets the 交接编号.
	 *
	 * @param handOverNo the new 交接编号
	 */
	public void setHandOverNo(String handOverNo) {
		this.handOverNo = handOverNo;
	}
	
	/**
	 * Gets the 出发部门编码.
	 *
	 * @return the 出发部门编码
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * Sets the 出发部门编码.
	 *
	 * @param origOrgCode the new 出发部门编码
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * Gets the 出发部门名称.
	 *
	 * @return the 出发部门名称
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * Sets the 出发部门名称.
	 *
	 * @param origOrgName the new 出发部门名称
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * Gets the 到达部门编码.
	 *
	 * @return the 到达部门编码
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * Sets the 到达部门编码.
	 *
	 * @param destOrgCode the new 到达部门编码
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * Gets the 到达部门名称.
	 *
	 * @return the 到达部门名称
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * Sets the 到达部门名称.
	 *
	 * @param destOrgName the new 到达部门名称
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * Gets the 是否预配.
	 *
	 * @return the 是否预配
	 */
	public String getIsPreHandOverBill() {
		return isPreHandOverBill;
	}
	
	/**
	 * Sets the 是否预配.
	 *
	 * @param isPreHandOverBill the new 是否预配
	 */
	public void setIsPreHandOverBill(String isPreHandOverBill) {
		this.isPreHandOverBill = isPreHandOverBill;
	}
	
	/**
	 * Gets the 交接类型.
	 *
	 * @return the 交接类型
	 */
	public String getHandOverType() {
		return handOverType;
	}
	
	/**
	 * Sets the 交接类型.
	 *
	 * @param handOverType the new 交接类型
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	
	/**
	 * Gets the 已配件数.
	 *
	 * @return the 已配件数
	 */
	public String getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * Sets the 已配件数.
	 *
	 * @param goodsQty the new 已配件数
	 */
	public void setGoodsQty(String goodsQty) {
		this.goodsQty = goodsQty;
	}
	
}