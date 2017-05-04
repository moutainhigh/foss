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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/CertificatebagTakeDto.java
 *  
 *  FILE NAME          :CertificatebagTakeDto.java
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

import java.io.Serializable;


/**
 * 证件包领取Dto
 * @author 099197-foss-liming
 * @date 2012-11-07 下午4:49:47
 */
public class CertificatebagTakeDto implements Serializable{
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2727836557875197704L;

	/**
	 * 证件类型
	 */
	private String type;// 
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;// 
	
	/**
	 * 货柜号码
	 */
	private String containerNo; // 
	
	/**
	 * 业务类型
	 */
	private String businessType;// 
	
	/**
	 * 领取人姓名
	 */
	private String actualTakeUserName;// 	
	
	/**
	 * 领取人编号
	 */
	private String actualTakeUserCode;// 

	/**
	 * 领取备注
	 */
	private String actualTakeNotes;// 	
	
	
	
	/**
	 * 车头行驶证
	 */
	private String vehicleheadPermisoDeCirculacion;
	 
	/**
	 * 车头运输证
	 */
	private String vehicleheadOverlandTransportation;
	 
	/**
	 * 车头车钥匙
	 */
	private String vehicleheadCarKey;		
	
	 
	/**
	 * 车头营业证
	 */
	private String vehicleheadBusinessRegistrationCertificate;
	 
	/**
	 * 车头车辆购置税证明
	 */
	private String vehicleheadPurchaseTax;

	 
	/**
	 * 车柜行驶证
	 */
	private String containerPermisoDeCirculacion;
	 
	/**
	 * 车柜运输证
	 */
	private String containerOverlandTransportation;
	 
	/**
	 * 车柜缴费单
	 */
	private String containerDebitNote;
	 
	/**
	 * 车柜营业证
	 */
	private String containerBusinessRegistrationCertificate;
	
	/**
	 *  车柜车辆购置税证明
	 */
	private String containerVehiclePurchaseTax;
	 
	/**
	 * 车柜保险卡
	 */
	private String containerInsuranceCard;

	
	/**
	 *  车辆行驶证
	 */
	private String vehiclePermisoDeCirculacion;
	 
	/**
	 * 车辆运输证
	 */
	private String vehicleOverlandTransportation;
	 
	/**
	 * 车辆车钥匙
	 */
	private String vehicleCarKey;
	 
	/**
	 * 车辆保险卡
	 */
	private String vehicleInsuranceCard;		
		
	
	
	/**
	 * 关联id
	 */
	private String refId; 
	
	
	
	/**
	 * 车头证件数组
	 */
	private String vehiclehead[];
	

	/**
	 * 车头证件数组
	 */
	private String container[];
	
	/**
	 * 车头证件数组
	 */
	private String vehicle[];
	
	/**
	 * 车牌业务交接情况
	 */
	private String vehicleHandOverStatus;
	
	/**
	 * 货柜业务交接情况
	 */
	private String containerHandOverStatus;
	

	public String[] getVehiclehead() {
		return vehiclehead;
	}

	public void setVehiclehead(String[] vehiclehead) {
		this.vehiclehead = vehiclehead;
	}

	public String[] getContainer() {
		return container;
	}

	public void setContainer(String[] container) {
		this.container = container;
	}

	public String[] getVehicle() {
		return vehicle;
	}

	public void setVehicle(String[] vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleHandOverStatus() {
		return vehicleHandOverStatus;
	}

	public void setVehicleHandOverStatus(String vehicleHandOverStatus) {
		this.vehicleHandOverStatus = vehicleHandOverStatus;
	}

	public String getContainerHandOverStatus() {
		return containerHandOverStatus;
	}

	public void setContainerHandOverStatus(String containerHandOverStatus) {
		this.containerHandOverStatus = containerHandOverStatus;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 *
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getActualTakeUserCode() {
		return actualTakeUserCode;
	}

	/**
	 * 
	 *
	 * @param actualTakeUserCode 
	 */
	public void setActualTakeUserCode(String actualTakeUserCode) {
		this.actualTakeUserCode = actualTakeUserCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 
	 *
	 * @param vehicleNo 
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 
	 *
	 * @param containerNo 
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 
	 *
	 * @param businessType 
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getActualTakeUserName() {
		return actualTakeUserName;
	}

	/**
	 * 
	 *
	 * @param actualTakeUserName 
	 */
	public void setActualTakeUserName(String actualTakeUserName) {
		this.actualTakeUserName = actualTakeUserName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getActualTakeNotes() {
		return actualTakeNotes;
	}

	/**
	 * 
	 *
	 * @param actualTakeNotes 
	 */
	public void setActualTakeNotes(String actualTakeNotes) {
		this.actualTakeNotes = actualTakeNotes;
	}

	/**
	 * 获取 车头行驶证.
	 *
	 * @return the 车头行驶证
	 */
	public String getVehicleheadPermisoDeCirculacion() {
		return vehicleheadPermisoDeCirculacion;
	}

	/**
	 * 设置 车头行驶证.
	 *
	 * @param vehicleheadPermisoDeCirculacion the new 车头行驶证
	 */
	public void setVehicleheadPermisoDeCirculacion(
			String vehicleheadPermisoDeCirculacion) {
		this.vehicleheadPermisoDeCirculacion = vehicleheadPermisoDeCirculacion;
	}

	/**
	 * 获取 车头运输证.
	 *
	 * @return the 车头运输证
	 */
	public String getVehicleheadOverlandTransportation() {
		return vehicleheadOverlandTransportation;
	}

	/**
	 * 设置 车头运输证.
	 *
	 * @param vehicleheadOverlandTransportation the new 车头运输证
	 */
	public void setVehicleheadOverlandTransportation(String vehicleheadOverlandTransportation) {
		this.vehicleheadOverlandTransportation = vehicleheadOverlandTransportation;
	}

	/**
	 * 获取 车头车钥匙.
	 *
	 * @return the 车头车钥匙
	 */
	public String getVehicleheadCarKey() {
		return vehicleheadCarKey;
	}

	/**
	 * 设置 车头车钥匙.
	 *
	 * @param vehicleheadCarKey the new 车头车钥匙
	 */
	public void setVehicleheadCarKey(String vehicleheadCarKey) {
		this.vehicleheadCarKey = vehicleheadCarKey;
	}

	
	/**
	 * 获取 车头营业证.
	 *
	 * @return the 车头营业证
	 */
	public String getVehicleheadBusinessRegistrationCertificate() {
		return vehicleheadBusinessRegistrationCertificate;
	}

	/**
	 * 设置 车头营业证.
	 *
	 * @param vehicleheadBusinessRegistrationCertificate the new 车头营业证
	 */
	public void setVehicleheadBusinessRegistrationCertificate(
			String vehicleheadBusinessRegistrationCertificate) {
		this.vehicleheadBusinessRegistrationCertificate = vehicleheadBusinessRegistrationCertificate;
	}

	

	/**
	 * 获取 车头车辆购置税证明.
	 *
	 * @return the 车头车辆购置税证明
	 */
	public String getVehicleheadPurchaseTax() {
		return vehicleheadPurchaseTax;
	}

	/**
	 * 设置 车头车辆购置税证明.
	 *
	 * @param vehicleheadPurchaseTax the new 车头车辆购置税证明
	 */
	public void setVehicleheadPurchaseTax(String vehicleheadPurchaseTax) {
		this.vehicleheadPurchaseTax = vehicleheadPurchaseTax;
	}

	/**
	 * 获取 车柜行驶证.
	 *
	 * @return the 车柜行驶证
	 */
	public String getContainerPermisoDeCirculacion() {
		return containerPermisoDeCirculacion;
	}

	/**
	 * 设置 车柜行驶证.
	 *
	 * @param containerPermisoDeCirculacion the new 车柜行驶证
	 */
	public void setContainerPermisoDeCirculacion(
			String containerPermisoDeCirculacion) {
		this.containerPermisoDeCirculacion = containerPermisoDeCirculacion;
	}

	/**
	 * 获取 车柜运输证.
	 *
	 * @return the 车柜运输证
	 */
	public String getContainerOverlandTransportation() {
		return containerOverlandTransportation;
	}

	/**
	 * 设置 车柜运输证.
	 *
	 * @param containerOverlandTransportation the new 车柜运输证
	 */
	public void setContainerOverlandTransportation(
			String containerOverlandTransportation) {
		this.containerOverlandTransportation = containerOverlandTransportation;
	}

	/**
	 * 获取 车柜缴费单.
	 *
	 * @return the 车柜缴费单
	 */
	public String getContainerDebitNote() {
		return containerDebitNote;
	}

	/**
	 * 设置 车柜缴费单.
	 *
	 * @param containerDebitNote the new 车柜缴费单
	 */
	public void setContainerDebitNote(String containerDebitNote) {
		this.containerDebitNote = containerDebitNote;
	}

	/**
	 * 获取 车柜营业证.
	 *
	 * @return the 车柜营业证
	 */
	public String getContainerBusinessRegistrationCertificate() {
		return containerBusinessRegistrationCertificate;
	}

	/**
	 * 设置 车柜营业证.
	 *
	 * @param containerBusinessRegistrationCertificate the new 车柜营业证
	 */
	public void setContainerBusinessRegistrationCertificate(
			String containerBusinessRegistrationCertificate) {
		this.containerBusinessRegistrationCertificate = containerBusinessRegistrationCertificate;
	}

	/**
	 * 获取 车柜车辆购置税证明.
	 *
	 * @return the 车柜车辆购置税证明
	 */
	public String getContainerVehiclePurchaseTax() {
		return containerVehiclePurchaseTax;
	}

	/**
	 * 设置 车柜车辆购置税证明.
	 *
	 * @param containerVehiclePurchaseTax the new 车柜车辆购置税证明
	 */
	public void setContainerVehiclePurchaseTax(String containerVehiclePurchaseTax) {
		this.containerVehiclePurchaseTax = containerVehiclePurchaseTax;
	}

	/**
	 * 获取 车柜保险卡.
	 *
	 * @return the 车柜保险卡
	 */
	public String getContainerInsuranceCard() {
		return containerInsuranceCard;
	}

	/**
	 * 设置 车柜保险卡.
	 *
	 * @param containerInsuranceCard the new 车柜保险卡
	 */
	public void setContainerInsuranceCard(String containerInsuranceCard) {
		this.containerInsuranceCard = containerInsuranceCard;
	}

	/**
	 * 获取 车辆行驶证.
	 *
	 * @return the 车辆行驶证
	 */
	public String getVehiclePermisoDeCirculacion() {
		return vehiclePermisoDeCirculacion;
	}

	/**
	 * 设置 车辆行驶证.
	 *
	 * @param vehiclePermisoDeCirculacion the new 车辆行驶证
	 */
	public void setVehiclePermisoDeCirculacion(String vehiclePermisoDeCirculacion) {
		this.vehiclePermisoDeCirculacion = vehiclePermisoDeCirculacion;
	}

	/**
	 * 获取 车辆运输证.
	 *
	 * @return the 车辆运输证
	 */
	public String getVehicleOverlandTransportation() {
		return vehicleOverlandTransportation;
	}

	/**
	 * 设置 车辆运输证.
	 *
	 * @param vehicleOverlandTransportation the new 车辆运输证
	 */
	public void setVehicleOverlandTransportation(
			String vehicleOverlandTransportation) {
		this.vehicleOverlandTransportation = vehicleOverlandTransportation;
	}	

	/**
	 * 获取 车辆保险卡.
	 *
	 * @return the 车辆保险卡
	 */
	public String getVehicleInsuranceCard() {
		return vehicleInsuranceCard;
	}

	/**
	 * 获取 车辆车钥匙.
	 *
	 * @return the 车辆车钥匙
	 */
	public String getVehicleCarKey() {
		return vehicleCarKey;
	}

	/**
	 * 设置 车辆车钥匙.
	 *
	 * @param vehicleCarKey the new 车辆车钥匙
	 */
	public void setVehicleCarKey(String vehicleCarKey) {
		this.vehicleCarKey = vehicleCarKey;
	}

	/**
	 * 设置 车辆保险卡.
	 *
	 * @param vehicleInsuranceCard the new 车辆保险卡
	 */
	public void setVehicleInsuranceCard(String vehicleInsuranceCard) {
		this.vehicleInsuranceCard = vehicleInsuranceCard;
	}

	/**
	 * 获取 关联id.
	 *
	 * @return the 关联id
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * 设置 关联id.
	 *
	 * @param refId the new 关联id
	 */
	public void setRefId(String refId) {
		this.refId = refId;
	}

	

}