/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/DispatchOrderConditionDto.java
 * 
 * FILE NAME        	: DispatchOrderConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 处理订单查询条件Dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-11-14 上午9:06:12
 */
public class DispatchOrderConditionDto implements Serializable {

	private static final long serialVersionUID = -7495947552332506957L;
	/**
	 * 订单ID
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 行政区域list
	 */
	private List<String> districtList;
	/**
	 * 定人定区List
	 */
	private List<String> smallZoneList;
	/**
	 * 营业区List
	 */
	private List<String> businessAreaList;
	/**
	 * 车型
	 */
	private String vehicleType;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 营业部
	 */
	private List<String> salesDepartmentCodes;
	/**
	 * 接送货车队组
	 */
	private List<ServiceFleetDto> serviceFleetList;
	/**
	 * 签到状态
	 */
	private String status;
	/**
	 * 默认的订单状态
	 */
	private List<String> defaultOrderStatus;
	/**
	 * 默认的订单类型
	 */
	private List<String> defaultOrderType;
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 设备号
	 */
	private String deviceNo;

	/**
	 * 车队编码
	 */
	private String fleetCode;

	/**
	 * 顶级车队下的所有车队
	 */
	private List<String> fleetList;

	/**
	 * 是否PDA
	 */
	private String isPda;
	/**
	 * 是否营业部
	 */
	private String isSalesDept;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 订单状态集合（PDA接口使用）
	 */
	private List<String> orderStatusList;
	
	private String waybillNo;
	/**
	 * 操作时间
	 */
	private Date operateDate;
	
	/**
	 * 接货时间是否当日 
	 */
	private String isToday;
	
	/**
	 * 区域列表
	 */
	private String expressOrderAreas;
	/**
	 * 登录人所属快递大区下的所有行政区域（市一级单位）
	 */
	private List<String> expressOrderCityCodes;
	/**
	 * 登录人所属快递大区下的所有行政区域（区县一级单位）
	 */
	private List<String> expressOrderCountyCodes;
	/**
	 * 登录人查询录入的行政区域（区县一级单位，必须在所属对应的快递大区下）
	 */
	private List<String> expressOrderNewCountyCodes;
	
	/**
	 * 运输性质
	 */
	private String productCode;
	
	/**
	 * 运输性质集合
	 * @return
	 */
	private List<String> productCodes;
	
	/**
	 * 是否快递
	 * 
	 */
	private String isExpress;
	
	/**
	 * 是否快递
	 * 
	 */
	private String waybillType;
	
	
	public List<String> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * Gets the active.
	 * 
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 * 
	 * @param active the active to see
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the isSalesDept.
	 * 
	 * @return the isSalesDept
	 */
	public String getIsSalesDept() {
		return isSalesDept;
	}

	/**
	 * Sets the isSalesDept.
	 * 
	 * @param isSalesDept the isSalesDept to see
	 */
	public void setIsSalesDept(String isSalesDept) {
		this.isSalesDept = isSalesDept;
	}

	/**
	 * Gets the fleetList.
	 * 
	 * @return the fleetList
	 */
	public List<String> getFleetList() {
		return fleetList;
	}

	/**
	 * Sets the fleetList.
	 * 
	 * @param fleetList the fleetList to see
	 */
	public void setFleetList(List<String> fleetList) {
		this.fleetList = fleetList;
	}

	/**
	 * Gets the defaultOrderStatus.
	 * 
	 * @return the defaultOrderStatus
	 */
	public List<String> getDefaultOrderStatus() {
		return defaultOrderStatus;
	}

	/**
	 * Sets the defaultOrderStatus.
	 * 
	 * @param defaultOrderStatus the defaultOrderStatus to set
	 */
	public void setDefaultOrderStatus(List<String> defaultOrderStatus) {
		this.defaultOrderStatus = defaultOrderStatus;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the orderNo.
	 * 
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the orderNo.
	 * 
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the districtList.
	 * 
	 * @return the districtList
	 */
	public List<String> getDistrictList() {
		return districtList;
	}

	/**
	 * Sets the districtList.
	 * 
	 * @param districtList the districtList to set
	 */
	public void setDistrictList(List<String> districtList) {
		this.districtList = districtList;
	}

	/**
	 * Gets the smallZoneList.
	 * 
	 * @return the smallZoneList
	 */
	public List<String> getSmallZoneList() {
		return smallZoneList;
	}

	/**
	 * Sets the smallZoneList.
	 * 
	 * @param smallZoneList the smallZoneList to set
	 */
	public void setSmallZoneList(List<String> smallZoneList) {
		this.smallZoneList = smallZoneList;
	}

	/**
	 * Gets the businessAreaList.
	 * 
	 * @return the businessAreaList
	 */
	public List<String> getBusinessAreaList() {
		return businessAreaList;
	}

	/**
	 * Sets the businessAreaList.
	 * 
	 * @param businessAreaList the businessAreaList to set
	 */
	public void setBusinessAreaList(List<String> businessAreaList) {
		this.businessAreaList = businessAreaList;
	}

	/**
	 * Gets the vehicleType.
	 * 
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * Sets the vehicleType.
	 * 
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * Gets the orderStatus.
	 * 
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the orderStatus.
	 * 
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Gets the salesDepartmentCodes.
	 * 
	 * @return the salesDepartmentCodes
	 */
	public List<String> getSalesDepartmentCodes() {
		return salesDepartmentCodes;
	}

	/**
	 * Sets the salesDepartmentCodes.
	 * 
	 * @param salesDepartmentCodes the salesDepartmentCodes to set
	 */
	public void setSalesDepartmentCodes(List<String> salesDepartmentCodes) {
		this.salesDepartmentCodes = salesDepartmentCodes;
	}

	/**
	 * Gets the serviceFleetList.
	 * 
	 * @return the serviceFleetList
	 */
	public List<ServiceFleetDto> getServiceFleetList() {
		return serviceFleetList;
	}

	/**
	 * Sets the serviceFleetList.
	 * 
	 * @param serviceFleetList the serviceFleetList to set
	 */
	public void setServiceFleetList(List<ServiceFleetDto> serviceFleetList) {
		this.serviceFleetList = serviceFleetList;
	}

	/**
	 * Gets the driverCode.
	 * 
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the driverCode.
	 * 
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the vehicleNo.
	 * 
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the vehicleNo.
	 * 
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the deviceNo.
	 * 
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * Sets the deviceNo.
	 * 
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * Gets the fleetCode.
	 * 
	 * @return the fleetCode
	 */
	public String getFleetCode() {
		return fleetCode;
	}

	/**
	 * Sets the fleetCode.
	 * 
	 * @param fleetCode the fleetCode to set
	 */
	public void setFleetCode(String fleetCode) {
		this.fleetCode = fleetCode;
	}

	/**
	 * Gets the isPda.
	 * 
	 * @return the isPda
	 */
	public String getIsPda() {
		return isPda;
	}

	/**
	 * Sets the isPda.
	 * 
	 * @param isPda the isPda to set
	 */
	public void setIsPda(String isPda) {
		this.isPda = isPda;
	}

	/**
	 * To string |.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2013-1-17 下午8:01:48
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DispatchOrderConditionDto [orderNo=" + orderNo + ", districtList=" + districtList + ", smallZoneList=" + smallZoneList + ", businessAreaList=" + businessAreaList + ", vehicleType="
				+ vehicleType + ", orderStatus=" + orderStatus + ", salesDepartmentCodes=" + salesDepartmentCodes + ", serviceFleetList=" + serviceFleetList + ", status=" + status
				+ ", defaultOrderStatus=" + defaultOrderStatus + ", driverCode=" + driverCode + ", vehicleNo=" + vehicleNo + ", deviceNo=" + deviceNo + "]";
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id to see
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getExpressOrderAreas() {
		return expressOrderAreas;
	}

	public void setExpressOrderAreas(String expressOrderAreas) {
		this.expressOrderAreas = expressOrderAreas;
	}

	public List<String> getExpressOrderCityCodes() {
		return expressOrderCityCodes;
	}

	public void setExpressOrderCityCodes(List<String> expressOrderCityCodes) {
		this.expressOrderCityCodes = expressOrderCityCodes;
	}

	public List<String> getExpressOrderCountyCodes() {
		return expressOrderCountyCodes;
	}

	public void setExpressOrderCountyCodes(List<String> expressOrderCountyCodes) {
		this.expressOrderCountyCodes = expressOrderCountyCodes;
	}

	public List<String> getExpressOrderNewCountyCodes() {
		return expressOrderNewCountyCodes;
	}

	public void setExpressOrderNewCountyCodes(List<String> expressOrderNewCountyCodes) {
		this.expressOrderNewCountyCodes = expressOrderNewCountyCodes;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public List<String> getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}

	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getIsToday() {
		return isToday;
	}

	public void setIsToday(String isToday) {
		this.isToday = isToday;
	}

	public List<String> getDefaultOrderType() {
		return defaultOrderType;
	}

	public void setDefaultOrderType(List<String> defaultOrderType) {
		this.defaultOrderType = defaultOrderType;
	}

}