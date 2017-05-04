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
 *  FILE PATH          :/AirHandOverStateDto.java
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

/**
 * 接送货接口，根据运单号查询交接情况
 * @author foss-liuxue(for IBM)
 * @date 2013-1-14 上午9:27:16
 */
public class AirHandOverStateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -415686712203218430L;

	/**保存时间*/
	private Date createTime;
	/**出发时间*/
	private Date leaveTime;
	/**到达时间*/
	private Date arriveTime;
	/**交接编号*/
	private String airHandoverNo;
	/**交接部门编码*/
	private String orgCode;
	/**交接部门名称*/
	private String orgName;
	/**目的地编码*/
	private String arrvRegionCode;
	/**目的地名称*/
	private String arrvRegionName;
	/**是否预配*/
	private String isPreHandOverBill;
	/**交接类型*/
	private String handOverType;
	/**已配件数*/
	private String goodsQty;
	
	/**
	 * 获取 保存时间.
	 *
	 * @return the 保存时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 保存时间.
	 *
	 * @param createTime the new 保存时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 出发时间.
	 *
	 * @return the 出发时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * 设置 出发时间.
	 *
	 * @param leaveTime the new 出发时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	/**
	 * 获取 交接编号.
	 *
	 * @return the 交接编号
	 */
	public String getAirHandoverNo() {
		return airHandoverNo;
	}
	
	/**
	 * 设置 交接编号.
	 *
	 * @param airHandoverNo the new 交接编号
	 */
	public void setAirHandoverNo(String airHandoverNo) {
		this.airHandoverNo = airHandoverNo;
	}
	
	/**
	 * 获取 交接部门编码.
	 *
	 * @return the 交接部门编码
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 交接部门编码.
	 *
	 * @param orgCode the new 交接部门编码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 交接部门名称.
	 *
	 * @return the 交接部门名称
	 */
	public String getOrgName() {
		return orgName;
	}
	
	/**
	 * 设置 交接部门名称.
	 *
	 * @param orgName the new 交接部门名称
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	/**
	 * 获取 目的地编码.
	 *
	 * @return the 目的地编码
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的地编码.
	 *
	 * @param arrvRegionCode the new 目的地编码
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
	/**
	 * 获取 目的地名称.
	 *
	 * @return the 目的地名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的地名称.
	 *
	 * @param arrvRegionName the new 目的地名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 是否预配.
	 *
	 * @return the 是否预配
	 */
	public String getIsPreHandOverBill() {
		return isPreHandOverBill;
	}
	
	/**
	 * 设置 是否预配.
	 *
	 * @param isPreHandOverBill the new 是否预配
	 */
	public void setIsPreHandOverBill(String isPreHandOverBill) {
		this.isPreHandOverBill = isPreHandOverBill;
	}
	
	/**
	 * 获取 交接类型.
	 *
	 * @return the 交接类型
	 */
	public String getHandOverType() {
		return handOverType;
	}
	
	/**
	 * 设置 交接类型.
	 *
	 * @param handOverType the new 交接类型
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	
	/**
	 * 获取 已配件数.
	 *
	 * @return the 已配件数
	 */
	public String getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 设置 已配件数.
	 *
	 * @param goodsQty the new 已配件数
	 */
	public void setGoodsQty(String goodsQty) {
		this.goodsQty = goodsQty;
	}
	
}