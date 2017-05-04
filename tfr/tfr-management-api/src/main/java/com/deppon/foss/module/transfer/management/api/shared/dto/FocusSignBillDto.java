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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/FocusSignBillDto.java
 *  
 *  FILE NAME          :FocusSignBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity;

public class FocusSignBillDto extends FocusSignBillEntity {
	private static final long serialVersionUID = 2466367483527408391L;
	/**用车部门Code*/
	private String useTruckOrgCode;
	/**用车部门Name**/
	private String useTruckOrgName;
	/**体积*/
	private BigDecimal volume;
	/**重量*/
	private BigDecimal weight;
	/**接货票数*/
	private int waybillQtyTotal;
	/**上楼票数 */
    private int upstairsBillQty;
    /**单独接货票数*/
    private int singleReceiveBillQty;
    /**签单起始日期*/
    private Date signBillDateFrom;
    /**签单结束日期*/
    private Date signBillDateTo;
    /**
     * 当前部门的事业部下所有的子部门
     */
    private List<String> orgCodes;
    
    /**
	 *车型 名称
	 */
	private String vehicleTypeLengthName; 

	/**
	 * 获取 车型 名称.
	 *
	 * @return the 车型 名称
	 */
	public String getVehicleTypeLengthName() {
		return vehicleTypeLengthName;
	}

	/**
	 * 设置 车型 名称.
	 *
	 * @param vehicleTypeLengthName the new 车型 名称
	 */
	public void setVehicleTypeLengthName(String vehicleTypeLengthName) {
		this.vehicleTypeLengthName = vehicleTypeLengthName;
	}
    
	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public int getUpstairsBillQty() {
		return upstairsBillQty;
	}

	public void setUpstairsBillQty(int upstairsBillQty) {
		this.upstairsBillQty = upstairsBillQty;
	}

	public int getSingleReceiveBillQty() {
		return singleReceiveBillQty;
	}

	public void setSingleReceiveBillQty(int singleReceiveBillQty) {
		this.singleReceiveBillQty = singleReceiveBillQty;
	}

	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}

	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}

	public int getWaybillQtyTotal() {
		return waybillQtyTotal;
	}

	public void setWaybillQtyTotal(int waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}

	@DateFormat
	public Date getSignBillDateFrom() {
		return signBillDateFrom;
	}
	@DateFormat
	public void setSignBillDateFrom(Date signBillDateFrom) {
		this.signBillDateFrom = signBillDateFrom;
	}
	@DateFormat
	public Date getSignBillDateTo() {
		return signBillDateTo;
	}
	@DateFormat
	public void setSignBillDateTo(Date signBillDateTo) {
		this.signBillDateTo = signBillDateTo;
	}

	/**   
	 * useTruckOrgName   
	 *   
	 * @return  the useTruckOrgName   
	 */
	
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**   
	 * @param useTruckOrgName the useTruckOrgName to set
	 * Date:2013-3-21上午10:17:44
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}

	/**   
	 * orgCodes   
	 *   
	 * @return  the orgCodes   
	 */
	
	public List<String> getOrgCodes() {
		return orgCodes;
	}

	/**   
	 * @param orgCodes the orgCodes to set
	 * Date:2013-5-2下午2:31:02
	 */
	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
}