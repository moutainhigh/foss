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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/PrintCarriageContractDataDto.java
 *  
 *  FILE NAME          :PrintCarriageContractDataDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打印运输合同Dto
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:39:33
 */
public class PrintCarriageContractDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2955879531515485723L;
	
	/**车主名称**/
	private String contact;				

	/**车牌号**/
	private String vehicleNo;			

	/**驾驶员**/
	private String driverName;			

	/**驾驶员**/
	private String driverCode;			

	/**身份证**/
	private String idCard;				

	/**驾驶证**/
	private String driverLicense;		

	/**发动机号**/
	private String engineNo;			

	/**车架号**/
	private String vehicleFrameNo;		

	/**营运证号**/
	private String operatingLic;		

	/**行驶证**/
	private String drivingLicense;		

	/**始发地**/
	private String origOrgName;	
	
	/**始发地 code*/
	private String origOrgCode;

	/**目的地**/
	private String destOrgName;			

	/**家 庭 住 址**/
	private String address;				

	/**司机电话**/
	private String driverPhone;			

	/**发车时间**/
	private Date departTime;			

	/**预到时间**/
	private Date eta;					

	/**负责人/制单人**/
	private String createUserName;		

	/**总件数**/
	private Integer totQty;				

	/**本车总运费**/
	private BigDecimal feeTotal;		

	/**始发已付运费**/
	private BigDecimal prePaidFeeTotal;	

	/**币种**/
	private String currencyCode;		

	/**目的站联系人**/
	private String destStationContact;	

	/**是否押回单**/
	private String beReturnReceipt;		

	/**备注**/
	private String note;				
	
	/**是否时效条款*/
	private String beTimeLiness;
	
	public String getBeTimeLiness() {
		return beTimeLiness;
	}

	public void setBeTimeLiness(String beTimeLiness) {
		this.beTimeLiness = beTimeLiness;
	}

	/**
	 * 获取 车主名称*.
	 *
	 * @return the 车主名称*
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 * 设置 车主名称*.
	 *
	 * @param contact the new 车主名称*
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	/**
	 * 获取 车牌号*.
	 *
	 * @return the 车牌号*
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号*.
	 *
	 * @param vehicleNo the new 车牌号*
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 驾驶员*.
	 *
	 * @return the 驾驶员*
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * 设置 驾驶员*.
	 *
	 * @param driverName the new 驾驶员*
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * 获取 身份证*.
	 *
	 * @return the 身份证*
	 */
	public String getIdCard() {
		return idCard;
	}
	
	/**
	 * 设置 身份证*.
	 *
	 * @param idCard the new 身份证*
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	/**
	 * 获取 驾驶证*.
	 *
	 * @return the 驾驶证*
	 */
	public String getDriverLicense() {
		return driverLicense;
	}
	
	/**
	 * 设置 驾驶证*.
	 *
	 * @param driverLicense the new 驾驶证*
	 */
	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}
	
	/**
	 * 获取 发动机号*.
	 *
	 * @return the 发动机号*
	 */
	public String getEngineNo() {
		return engineNo;
	}
	
	/**
	 * 设置 发动机号*.
	 *
	 * @param engineNo the new 发动机号*
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	
	/**
	 * 获取 车架号*.
	 *
	 * @return the 车架号*
	 */
	public String getVehicleFrameNo() {
		return vehicleFrameNo;
	}
	
	/**
	 * 设置 车架号*.
	 *
	 * @param vehicleFrameNo the new 车架号*
	 */
	public void setVehicleFrameNo(String vehicleFrameNo) {
		this.vehicleFrameNo = vehicleFrameNo;
	}
	
	/**
	 * 获取 营运证号*.
	 *
	 * @return the 营运证号*
	 */
	public String getOperatingLic() {
		return operatingLic;
	}
	
	/**
	 * 设置 营运证号*.
	 *
	 * @param operatingLic the new 营运证号*
	 */
	public void setOperatingLic(String operatingLic) {
		this.operatingLic = operatingLic;
	}
	
	/**
	 * 获取 行驶证*.
	 *
	 * @return the 行驶证*
	 */
	public String getDrivingLicense() {
		return drivingLicense;
	}
	
	/**
	 * 设置 行驶证*.
	 *
	 * @param drivingLicense the new 行驶证*
	 */
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	
	/**
	 * 获取 始发地*.
	 *
	 * @return the 始发地*
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	
	/**
	 * 设置 始发地*.
	 *
	 * @param origOrgName the new 始发地*
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	
	/**
	 * 获取 目的地*.
	 *
	 * @return the 目的地*
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * 设置 目的地*.
	 *
	 * @param destOrgName the new 目的地*
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取 家 庭 住 址*.
	 *
	 * @return the 家 庭 住 址*
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 设置 家 庭 住 址*.
	 *
	 * @param address the new 家 庭 住 址*
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 获取 司机电话*.
	 *
	 * @return the 司机电话*
	 */
	public String getDriverPhone() {
		return driverPhone;
	}
	
	/**
	 * 设置 司机电话*.
	 *
	 * @param driverPhone the new 司机电话*
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
	/**
	 * 获取 发车时间*.
	 *
	 * @return the 发车时间*
	 */
	public Date getDepartTime() {
		return departTime;
	}
	
	/**
	 * 设置 发车时间*.
	 *
	 * @param departTime the new 发车时间*
	 */
	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}
	
	/**
	 * 获取 预到时间*.
	 *
	 * @return the 预到时间*
	 */
	public Date getEta() {
		return eta;
	}
	
	/**
	 * 设置 预到时间*.
	 *
	 * @param eta the new 预到时间*
	 */
	public void setEta(Date eta) {
		this.eta = eta;
	}
	
	/**
	 * 获取 负责人/制单人*.
	 *
	 * @return the 负责人/制单人*
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 负责人/制单人*.
	 *
	 * @param createUserName the new 负责人/制单人*
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 总件数*.
	 *
	 * @return the 总件数*
	 */
	public Integer getTotQty() {
		return totQty;
	}
	
	/**
	 * 设置 总件数*.
	 *
	 * @param totQty the new 总件数*
	 */
	public void setTotQty(Integer totQty) {
		this.totQty = totQty;
	}
	
	/**
	 * 获取 本车总运费*.
	 *
	 * @return the 本车总运费*
	 */
	public BigDecimal getFeeTotal() {
		return feeTotal;
	}
	
	/**
	 * 设置 本车总运费*.
	 *
	 * @param feeTotal the new 本车总运费*
	 */
	public void setFeeTotal(BigDecimal feeTotal) {
		this.feeTotal = feeTotal;
	}
	
	/**
	 * 获取 始发已付运费*.
	 *
	 * @return the 始发已付运费*
	 */
	public BigDecimal getPrePaidFeeTotal() {
		return prePaidFeeTotal;
	}
	
	/**
	 * 设置 始发已付运费*.
	 *
	 * @param prePaidFeeTotal the new 始发已付运费*
	 */
	public void setPrePaidFeeTotal(BigDecimal prePaidFeeTotal) {
		this.prePaidFeeTotal = prePaidFeeTotal;
	}
	
	/**
	 * 获取 币种*.
	 *
	 * @return the 币种*
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * 设置 币种*.
	 *
	 * @param currencyCode the new 币种*
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	/**
	 * 获取 目的站联系人*.
	 *
	 * @return the 目的站联系人*
	 */
	public String getDestStationContact() {
		return destStationContact;
	}
	
	/**
	 * 设置 目的站联系人*.
	 *
	 * @param destStationContact the new 目的站联系人*
	 */
	public void setDestStationContact(String destStationContact) {
		this.destStationContact = destStationContact;
	}
	
	/**
	 * 获取 是否押回单*.
	 *
	 * @return the 是否押回单*
	 */
	public String getBeReturnReceipt() {
		return beReturnReceipt;
	}
	
	/**
	 * 设置 是否押回单*.
	 *
	 * @param beReturnReceipt the new 是否押回单*
	 */
	public void setBeReturnReceipt(String beReturnReceipt) {
		this.beReturnReceipt = beReturnReceipt;
	}
	
	/**
	 * 获取 备注*.
	 *
	 * @return the 备注*
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 设置 备注*.
	 *
	 * @param note the new 备注*
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**   
	 * driverCode   
	 *   
	 * @return  the driverCode   
	 */
	
	public String getDriverCode() {
		return driverCode;
	}

	/**   
	 * @param driverCode the driverCode to set
	 * Date:2013-5-3下午4:41:19
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
}