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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/CertificatebagReturnDto.java
 *  
 *  FILE NAME          :CertificatebagReturnDto.java
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
 * 证件包归还entity
 * 
 * @author 099197-foss-liming
 * @date 2012-11-07 下午4:49:47
 */
public class CertificatebagReturnDto implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6286815320136742494L;

	/**
	 * Id
	 */
	private String id;

	/**
	 * 证件包动作ID
	 */
	private String certificatebagActionId; 

	/**
	 * 证件类型	
	 */
	private String type; 
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 货柜号码
	 */
	private String containerNo;  
	
	/**
	 * 货柜Id
	 */
	private String containerId;  
	
	/**
	 * 业务类型
	 */
	private String businessType; 
	
	/**
	 * 归还人
	 */
	private String actualReturnUserName;
	
	/**
	 * 归还人
	 */
	private String actualReturnUserCode;
	
	/**
	 * 归还备注
	 */
	private String actualReturnNotes; 
	
	 
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



	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * 获取 证件包动作ID.
	 *
	 * @return the 证件包动作ID
	 */
	public String getCertificatebagActionId() {
		return certificatebagActionId;
	}



	/**
	 * 设置 证件包动作ID.
	 *
	 * @param certificatebagActionId the new 证件包动作ID
	 */
	public void setCertificatebagActionId(String certificatebagActionId) {
		this.certificatebagActionId = certificatebagActionId;
	}



	/**
	 * 获取 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getType() {
		return type;
	}



	/**
	 * 设置 证件类型.
	 *
	 * @param type the new 证件类型
	 */
	public void setType(String type) {
		this.type = type;
	}



	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}



	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}



	/**
	 * 获取 货柜号码.
	 *
	 * @return the 货柜号码
	 */
	public String getContainerNo() {
		return containerNo;
	}



	/**
	 * 设置 货柜号码.
	 *
	 * @param containerNo the new 货柜号码
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}



	/**
	 * 获取 货柜Id.
	 *
	 * @return the 货柜Id
	 */
	public String getContainerId() {
		return containerId;
	}



	/**
	 * 设置 货柜Id.
	 *
	 * @param containerId the new 货柜Id
	 */
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}



	/**
	 * 获取 业务类型.
	 *
	 * @return the 业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}



	/**
	 * 设置 业务类型.
	 *
	 * @param businessType the new 业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}



	/**
	 * 获取 归还人.
	 *
	 * @return the 归还人
	 */
	public String getActualReturnUserName() {
		return actualReturnUserName;
	}



	/**
	 * 设置 归还人.
	 *
	 * @param actualReturnUserName the new 归还人
	 */
	public void setActualReturnUserName(String actualReturnUserName) {
		this.actualReturnUserName = actualReturnUserName;
	}



	/**
	 * 获取 归还人.
	 *
	 * @return the 归还人
	 */
	public String getActualReturnUserCode() {
		return actualReturnUserCode;
	}



	/**
	 * 设置 归还人.
	 *
	 * @param actualReturnUserCode the new 归还人
	 */
	public void setActualReturnUserCode(String actualReturnUserCode) {
		this.actualReturnUserCode = actualReturnUserCode;
	}



	/**
	 * 获取 归还备注.
	 *
	 * @return the 归还备注
	 */
	public String getActualReturnNotes() {
		return actualReturnNotes;
	}



	/**
	 * 设置 归还备注.
	 *
	 * @param actualReturnNotes the new 归还备注
	 */
	public void setActualReturnNotes(String actualReturnNotes) {
		this.actualReturnNotes = actualReturnNotes;
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
	public void setVehicleheadOverlandTransportation(
			String vehicleheadOverlandTransportation) {
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
	 * 获取 车辆保险卡.
	 *
	 * @return the 车辆保险卡
	 */
	public String getVehicleInsuranceCard() {
		return vehicleInsuranceCard;
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