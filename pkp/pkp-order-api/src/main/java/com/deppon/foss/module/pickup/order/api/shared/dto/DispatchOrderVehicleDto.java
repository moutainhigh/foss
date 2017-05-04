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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/DispatchOrderVehicleDto.java
 * 
 * FILE NAME        	: DispatchOrderVehicleDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

/**
 * 查询处理订单任务DTO
 * 
 * @author 097972-foss-dengtingting
 */
public class DispatchOrderVehicleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 派车记录Id
	 */
	private String id;

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 约车部门名称
	 */
	private String orderVehicleOrgName;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 接货地址
	 */
	private String pickupAddress;

	/**
	 * 派车时间 ==处理订单时间开始时间
	 */
	private Date deliverBeginTime;

	/**
	 * 派车时间 ==处理订单时间结束时间
	 */
	private Date deliverEndTime;

	/**
	 * 接货最早时间 == 用车时间
	 */
	private Date earliestPickupTime;

	/**
	 * 接货最晚时间
	 */
	private Date latestPickupTime;
	
	/**
	 * 所属接货小区名称
	 */
	private String pickupRegionName;
	
	/**
	 * 实际接货小区名称
	 */
	private String actualRegionName;

	/**
	 * 约车时间
	 */
	private Date orderVehicleTime;

	/**
	 * 司机姓名
	 */
	private String driverName;

	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * 备注 ==订单备注
	 */
	private String orderNotes;

	/**
	 * 定人定区
	 */
	private String pickupRegionCode;

	/**
	 * 订单任务发送状态
	 */
	private String orderSendStatus;

	/**
	 * 订单任务完成状态 == 订单状态
	 */
	private String orderStatus;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * PDA使用状态
	 */
	private String pdaStatus;

	/**
	 * 派车时间
	 */
	private Date deliverTime;
	
	/**
	 * FOSS开单时间
	 * */
	private Date fossBillTime;
	
	/**
	 * PDA标签打印时间
	 * */
	private Date pdaPrintTime;
	
	/**
	 * PDA开单时间(PDA端取打印标签时间/PDA传回FOSS端，则取FOSS开单时间)
	 * */
	private Date billTime;
	
	/**
	 * 开单类型（移动端PDA开单，或者PC端FOSS开单）
	 * */
	private String pendingType;
	
	/**
	 * 操作时间
	 */
	private Date operateTime;
	
	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * 组织code
	 */
	private String orgCode;
	
	/**
	 * 车队组织下车队
	 */
	private List<OrgAdministrativeInfoEntity> fleetList;
	
	/**
	 * 行政区域list
	 */
	private List<String> districtList;
	
	/**
	 * 快递员手机号
	 */
	private String driverMobile;
	
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 营业部
	 */
	private List<String> salesDepartmentCodes;
	/**
	 * 客户名称
	 */
	private String customerName;
	/**
	 * 客户手机号
	 */
	private String mobile;
	/**
	 * 客户电话
	 */
	private String tel;
	/**
	 * 操作备注
	 */
	private String operateNotes;
	/**
	 * 处理状态
	 */
	private String processStatus;
	
	/**
	 * 司机code
	 */
	private String driverCode;
	/**
	* 创建订单时间开始时间
	*/
	private Date orderVehicleBeginTime;

	/**
	* 创建订单时间结束时间
	*/
	private Date orderVehicleEndTime;

	/**
	 * 省
	 */
	private String proCode;
	/**
	 * 市
	 */
	private String cityCode;
	/**
	 * 区
	 */
	private String countyCode;
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
	 * 运输性质codes
	 * 
	 */
	private List<String> productCodes;
	/**
	 * 重量和体积
	 */
	private String weightAndVolume;
	/**
	 * 用车时间
	 */
	private String usecarTime;
	 /**
	  * 体积
	  */
	private BigDecimal volume;
	
	/**
	 * 重量
	 */
	private BigDecimal weight;
	private String acceptStatus; //14.9.4 

	
	

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
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

	/**
	 * Gets the processStatus.
	 * 
	 * @return the processStatus
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * Sets the processStatus.
	 * 
	 * @param processStatus the processStatus to see
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * Gets the operateNotes.
	 * 
	 * @return the operateNotes
	 */
	public String getOperateNotes() {
		return operateNotes;
	}

	/**
	 * Sets the operateNotes.
	 * 
	 * @param operateNotes the operateNotes to see
	 */
	public void setOperateNotes(String operateNotes) {
		this.operateNotes = operateNotes;
	}

	/**
	 * Gets the customerName.
	 * 
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customerName.
	 * 
	 * @param customerName the customerName to see
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the mobile.
	 * 
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 * 
	 * @param mobile the mobile to see
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the tel.
	 * 
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * Sets the tel.
	 * 
	 * @param tel the tel to see
	 */
	public void setTel(String tel) {
		this.tel = tel;
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
	 * @param salesDepartmentCodes the salesDepartmentCodes to see
	 */
	public void setSalesDepartmentCodes(List<String> salesDepartmentCodes) {
		this.salesDepartmentCodes = salesDepartmentCodes;
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
	 * Gets the driverMobile.
	 * 
	 * @return the driverMobile
	 */
	public String getDriverMobile() {
		return driverMobile;
	}

	/**
	 * Sets the driverMobile.
	 * 
	 * @param driverMobile the driverMobile to see
	 */
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
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
	 * @param districtList the districtList to see
	 */
	public void setDistrictList(List<String> districtList) {
		this.districtList = districtList;
	}

	/**
	 * Gets the fleetList.
	 * 
	 * @return the fleetList
	 */
	public List<OrgAdministrativeInfoEntity> getFleetList() {
		return fleetList;
	}

	/**
	 * Sets the fleetList.
	 * 
	 * @param fleetList the fleetList to see
	 */
	public void setFleetList(List<OrgAdministrativeInfoEntity> fleetList) {
		this.fleetList = fleetList;
	}

	/**
	 * Gets the orgCode.
	 * 
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the orgCode.
	 * 
	 * @param orgCode the orgCode to see
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the pickupAddress.
	 * 
	 * @return the pickupAddress
	 */
	public String getPickupAddress() {
		return pickupAddress;
	}

	/**
	 * Sets the pickupAddress.
	 * 
	 * @param pickupAddress the pickupAddress to see
	 */
	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	/**
	 * Gets the orderType.
	 * 
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * Sets the orderType.
	 * 
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * Gets the orderVehicleOrgName.
	 * 
	 * @return the orderVehicleOrgName
	 */
	public String getOrderVehicleOrgName() {
		return orderVehicleOrgName;
	}

	/**
	 * Sets the orderVehicleOrgName.
	 * 
	 * @param orderVehicleOrgName the orderVehicleOrgName to set
	 */
	public void setOrderVehicleOrgName(String orderVehicleOrgName) {
		this.orderVehicleOrgName = orderVehicleOrgName;
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
	 * Gets the driverName.
	 * 
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Sets the driverName.
	 * 
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
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
	 * Gets the orderNotes.
	 * 
	 * @return the orderNotes
	 */
	public String getOrderNotes() {
		return orderNotes;
	}

	/**
	 * Sets the orderNotes.
	 * 
	 * @param orderNotes the orderNotes to set
	 */
	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the pickupRegionCode.
	 * 
	 * @return the pickupRegionCode
	 */
	public String getPickupRegionCode() {
		return pickupRegionCode;
	}

	/**
	 * Sets the pickupRegionCode.
	 * 
	 * @param pickupRegionCode the pickupRegionCode to set
	 */
	public void setPickupRegionCode(String pickupRegionCode) {
		this.pickupRegionCode = pickupRegionCode;
	}

	/**
	 * Gets the orderSendStatus.
	 * 
	 * @return the orderSendStatus
	 */
	public String getOrderSendStatus() {
		return orderSendStatus;
	}

	/**
	 * Sets the orderSendStatus.
	 * 
	 * @param orderSendStatus the orderSendStatus to set
	 */
	public void setOrderSendStatus(String orderSendStatus) {
		this.orderSendStatus = orderSendStatus;
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
	 * Gets the pdaStatus.
	 * 
	 * @return the pdaStatus
	 */
	public String getPdaStatus() {
		return pdaStatus;
	}

	/**
	 * Sets the pdaStatus.
	 * 
	 * @param pdaStatus the pdaStatus to set
	 */
	public void setPdaStatus(String pdaStatus) {
		this.pdaStatus = pdaStatus;
	}

	/**
	 * Gets the deliverBeginTime.
	 * 
	 * @return the deliverBeginTime
	 */
	public Date getDeliverBeginTime() {
		return deliverBeginTime;
	}

	/**
	 * Sets the deliverBeginTime.
	 * 
	 * @param deliverBeginTime the deliverBeginTime to set
	 */
	@DateFormat(formate = "yyyy-MM-dd HH:mm:ss")
	public void setDeliverBeginTime(Date deliverBeginTime) {
		this.deliverBeginTime = deliverBeginTime;
	}

	/**
	 * Gets the deliverEndTime.
	 * 
	 * @return the deliverEndTime
	 */
	public Date getDeliverEndTime() {
		return deliverEndTime;
	}

	/**
	 * Sets the deliverEndTime.
	 * 
	 * @param deliverEndTime the deliverEndTime to set
	 */
	@DateFormat
	public void setDeliverEndTime(Date deliverEndTime) {
		this.deliverEndTime = deliverEndTime;
	}

	/**
	 * Gets the deliverTime.
	 * 
	 * @return the deliverTime
	 */
	public Date getDeliverTime() {
		return deliverTime;
	}

	/**
	 * Sets the deliverTime.
	 * 
	 * @param deliverTime the deliverTime to set
	 */
	@DateFormat
	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	/**
	 * Gets the orderId.
	 * 
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * Sets the orderId.
	 * 
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * Gets the earliestPickupTime.
	 * 
	 * @return the earliestPickupTime
	 */
	public Date getEarliestPickupTime() {
		return earliestPickupTime;
	}

	/**
	 * Sets the earliestPickupTime.
	 * 
	 * @param earliestPickupTime the earliestPickupTime to set
	 */
	@DateFormat
	public void setEarliestPickupTime(Date earliestPickupTime) {
		this.earliestPickupTime = earliestPickupTime;
	}

	/**
	 * Gets the latestPickupTime.
	 * 
	 * @return the latestPickupTime
	 */
	public Date getLatestPickupTime() {
		return latestPickupTime;
	}

	/**
	 * Sets the latestPickupTime.
	 * 
	 * @param latestPickupTime the latestPickupTime to set
	 */
	@DateFormat
	public void setLatestPickupTime(Date latestPickupTime) {
		this.latestPickupTime = latestPickupTime;
	}

	/**
	 * Gets the orderVehicleTime.
	 * 
	 * @return the orderVehicleTime
	 */
	public Date getOrderVehicleTime() {
		return orderVehicleTime;
	}

	/**
	 * Sets the orderVehicleTime.
	 * 
	 * @param orderVehicleTime the orderVehicleTime to set
	 */
	@DateFormat
	public void setOrderVehicleTime(Date orderVehicleTime) {
		this.orderVehicleTime = orderVehicleTime;
	}

	/**
	 * 所属接货小区名称
	 */
	public String getPickupRegionName() {
		return pickupRegionName;
	}

	/**
	 * 所属接货小区名称
	 */
	public void setPickupRegionName(String pickupRegionName) {
		this.pickupRegionName = pickupRegionName;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public Date getOrderVehicleBeginTime() {
		return orderVehicleBeginTime;
	}

	public void setOrderVehicleBeginTime(Date orderVehicleBeginTime) {
		this.orderVehicleBeginTime = orderVehicleBeginTime;
	}

	public Date getOrderVehicleEndTime() {
		return orderVehicleEndTime;
	}

	public void setOrderVehicleEndTime(Date orderVehicleEndTime) {
		this.orderVehicleEndTime = orderVehicleEndTime;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
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

	public List<String> getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	public String getActualRegionName() {
		return actualRegionName;
	}

	public void setActualRegionName(String actualRegionName) {
		this.actualRegionName = actualRegionName;
	}

	public String getWeightAndVolume() {
		return weightAndVolume;
	}

	public void setWeightAndVolume(String weightAndVolume) {
		this.weightAndVolume = weightAndVolume;
	}

	public String getUsecarTime() {
		return usecarTime;
	}

	public void setUsecarTime(String usecarTime) {
		this.usecarTime = usecarTime;
	}

	public Date getFossBillTime() {
		return fossBillTime;
	}

	public void setFossBillTime(Date fossBillTime) {
		this.fossBillTime = fossBillTime;
	}

	public Date getPdaPrintTime() {
		return pdaPrintTime;
	}

	public void setPdaPrintTime(Date pdaPrintTime) {
		this.pdaPrintTime = pdaPrintTime;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	
}