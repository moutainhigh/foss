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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/HandOverBillSerialNoDetailEntity.java
 *  
 *  FILE NAME          :HandOverBillSerialNoDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 交接单流水号明细entity
 * @author dp-shiwei
 * @date 2012-10-16 下午2:53:48
 */
public class HandOverBillSerialNoDetailEntity extends BaseEntity {

	private static final long serialVersionUID = -874150965300350278L;
	//上级记录ID（HandOverBillDetailEntity）
	private String superId;
	//运单号
	private String waybillNo;
	//流水号
    private String serialNo;
    //出发部门编号
    private String origOrgCode;
    //到达部门code
    private String destOrgCode;
    //交接时间
    private Date handOverTime;
    //交接单号
    private String handOverBillNo;
    //预配状态
    private String preHandOverStatus;
    //是否在库
    private int isInStorage;//0、不在库；1、在库
    //是否合车
    private String isJoinCar;
 
	private BigDecimal weight;//重量
	
	private BigDecimal volumn;//体积
	
	private String beScan;//是否扫描（过计泡机）
	
	private String productCode;
	
	public String getIsJoinCar() {
		return isJoinCar;
	}
	public void setIsJoinCar(String isJoinCar) {
		this.isJoinCar = isJoinCar;
	}
	public String getDestOrgCode() {
		return destOrgCode;
	}
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	public int getIsInStorage() {
		return isInStorage;
	}
	public void setIsInStorage(int isInStorage) {
		this.isInStorage = isInStorage;
	}
	public String getPreHandOverStatus() {
		return preHandOverStatus;
	}
	public void setPreHandOverStatus(String preHandOverStatus) {
		this.preHandOverStatus = preHandOverStatus;
	}
	public String getSuperId() {
		return superId;
	}
	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	@DateFormat
	public Date getHandOverTime() {
		return handOverTime;
	}
	@DateFormat
	public void setHandOverTime(Date handOverTime) {
		this.handOverTime = handOverTime;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public String getBeScan() {
		return beScan;
	}
	public void setBeScan(String beScan) {
		this.beScan = beScan;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
    
}