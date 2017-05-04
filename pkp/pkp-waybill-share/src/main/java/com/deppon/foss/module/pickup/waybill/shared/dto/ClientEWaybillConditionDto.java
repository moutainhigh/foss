package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 客户端电子运单查询条件Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-7-16 16:41:05
 */
public class ClientEWaybillConditionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 *运单号
	 */
	private String waybillNo;
	/**
	 * 快递员工号
	 */
	private String driverCode;
	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 客户联系人编码
	 */
	private String custContactCode;
	/**
	 * 大客户编码
	 */
	private List<String> customerCodeList;
	
	/**
	 * 电子运单列表
	 */
	private List<String> eWaybillNoList;
	
	/**
	 * 开单部门集合
	 */
	private List<String> billOrgCodeList;
	
	/**
	 * 订单状态
	 */
	private List<String> orderStatusList;
	
	/**
	 * 订单类型
	 */
	private String waybillType;
	
	/**
	 * 退回原因
	 */
	private String returnReason;
	
	/**
	 * 退回备注
	 */
	private String remark;
	
	/**
	 * 发货单地址
	 */
	private String address;
	
	/**
	 * 发货联系人电话
	 */
	private String mobilePhone;
	/**
	 * 订单号List
	 */
	private List<String> orderNoList;
	
	/**
	 * 办公电话
	 */
	private String officePhone;
	
	/**
	 * 当前登录部门编码 
	 */
	private String currentDeptCode;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 开单开始时间
	 */
	private Date billStartTime;

	/**
	 * 开单结束时间
	 */
	private Date billEndTime;

	/**
	 * Foss提交开始时间
	 */
	private Date createStartTime;

	/**
	 * Foss提交结束时间
	 */
	private Date createEndTime;
	
	/**
	 * 开始 扫描时间
	 */
	private Date beginScanTime;
	
	/**
	 * 结束 扫描时间
	 */
	private Date endScanTime;
	
	/**
	 * 运单状态
	 */
	private List<String> waybillStatusList;
	
	/**
	 * 订单来源状态
	 */
	private List<String> orderChannelList;
	
	/**
	 * 是否已扫描
	 */
	private String isScan;
	
	/**
	 * 产品类型
	 */
	private List<String> productCodeList;
	
	/**
	 * 司机集合
	 */
	private List<String> driverCodeList;
	
	/**
	 * 司机在客户扫描类型
	 */
	private String scanType;
	
	/**
	 * 是否完成状态
	 */
	private String whetherComplete;
	
	/**
	 * 任务类型
	 */
	private String taskType;

	
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getCustContactCode() {
		return custContactCode;
	}

	public void setCustContactCode(String custContactCode) {
		this.custContactCode = custContactCode;
	}

	public List<String> getCustomerCodeList() {
		return customerCodeList;
	}

	public void setCustomerCodeList(List<String> customerCodeList) {
		this.customerCodeList = customerCodeList;
	}

	public List<String> geteWaybillNoList() {
		return eWaybillNoList;
	}

	public void seteWaybillNoList(List<String> eWaybillNoList) {
		this.eWaybillNoList = eWaybillNoList;
	}

	public List<String> getBillOrgCodeList() {
		return billOrgCodeList;
	}

	public void setBillOrgCodeList(List<String> billOrgCodeList) {
		this.billOrgCodeList = billOrgCodeList;
	}

	public List<String> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getBillStartTime() {
		return billStartTime;
	}

	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}

	public Date getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public Date getBeginScanTime() {
		return beginScanTime;
	}

	public void setBeginScanTime(Date beginScanTime) {
		this.beginScanTime = beginScanTime;
	}

	public Date getEndScanTime() {
		return endScanTime;
	}

	public void setEndScanTime(Date endScanTime) {
		this.endScanTime = endScanTime;
	}

	public List<String> getWaybillStatusList() {
		return waybillStatusList;
	}

	public void setWaybillStatusList(List<String> waybillStatusList) {
		this.waybillStatusList = waybillStatusList;
	}

	public List<String> getOrderChannelList() {
		return orderChannelList;
	}

	public void setOrderChannelList(List<String> orderChannelList) {
		this.orderChannelList = orderChannelList;
	}

	public String getIsScan() {
		return isScan;
	}

	public void setIsScan(String isScan) {
		this.isScan = isScan;
	}

	public List<String> getProductCodeList() {
		return productCodeList;
	}

	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	public List<String> getDriverCodeList() {
		return driverCodeList;
	}

	public void setDriverCodeList(List<String> driverCodeList) {
		this.driverCodeList = driverCodeList;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getWhetherComplete() {
		return whetherComplete;
	}

	public void setWhetherComplete(String whetherComplete) {
		this.whetherComplete = whetherComplete;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}