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
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/dto/LdpAgencySystemReportQueryDto.java
 * 
 *  FILE NAME     :LdpAgencySystemReportQueryDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/

package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.util.define.FossConstants;

/**
 * 落地配全盘报表查询条件Dto
 * 
 * @author ibm-liuzhaowei
 * @date 2013-07-29 上午9:32:36
 */
public class LdpAgencySystemReportQueryDto implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1113448913523528972L;
	
	
	/**
	 * 是否外发-是
	 */
	@SuppressWarnings("unused")
	private static final String REGISTER_FLAG_YES = FossConstants.YES;
	
	/**
	 * 是否外发-否
	 */
	@SuppressWarnings("unused")
	private static final String REGISTER_FLAG_NO = FossConstants.NO;
	
	/**
	 * 落地配代理类型
	 */
	private String agencyWayBillNo;

	/**
	 * 开始业务日期
	 */
	private Date startBusinessDate;

	/**
	 * 结束业务日期
	 */
	private Date endBusinessDate;

	/**
	 * 到达部门
	 */
	private String arriveOrgCode;

	/**
	 * 开始外发时间
	 */
	private Date handOverStartTime;

	/**
	 * 结束外发时间
	 */
	private Date handOverEndTime;

	/**
	 * 是否外发
	 */
	private String registerFlag;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 区分查询类型
	 */
	private String queryType;

	/**
	 * 最终配载部门
	 */
	private String lastLoadOrgCode;
	
	/**
	 * 货运类型--落地配  参见中转常量 PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER
	 */
	private String productType;
	
	
	/**
	 * 用户Code
	 */
	private String empCode;
	
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * @return agencyWayBillNo
	 */
	public String getAgencyWayBillNo() {
		return agencyWayBillNo;
	}

	/**
	 * @param  agencyWayBillNo  
	 */
	public void setAgencyWayBillNo(String agencyWayBillNo) {
		this.agencyWayBillNo = agencyWayBillNo;
	}

	/**
	 * @return startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	/**
	 * @param  startBusinessDate  
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	/**
	 * @return endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	/**
	 * @param  endBusinessDate  
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	/**
	 * @return arriveOrgCode
	 */
	public String getArriveOrgCode() {
		return arriveOrgCode;
	}

	/**
	 * @param  arriveOrgCode  
	 */
	public void setArriveOrgCode(String arriveOrgCode) {
		this.arriveOrgCode = arriveOrgCode;
	}

	/**
	 * @return handOverStartTime
	 */
	public Date getHandOverStartTime() {
		return handOverStartTime;
	}

	/**
	 * @param  handOverStartTime  
	 */
	public void setHandOverStartTime(Date handOverStartTime) {
		this.handOverStartTime = handOverStartTime;
	}

	/**
	 * @return handOverEndTime
	 */
	public Date getHandOverEndTime() {
		return handOverEndTime;
	}

	/**
	 * @param  handOverEndTime  
	 */
	public void setHandOverEndTime(Date handOverEndTime) {
		this.handOverEndTime = handOverEndTime;
	}

	/**
	 * @return registerFlag
	 */
	public String getRegisterFlag() {
		return registerFlag;
	}

	/**
	 * @param  registerFlag  
	 */
	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param  waybillNo  
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param  queryType  
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return lastLoadOrgCode
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * @param  lastLoadOrgCode  
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * @return productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param  productType  
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return  the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	
}
