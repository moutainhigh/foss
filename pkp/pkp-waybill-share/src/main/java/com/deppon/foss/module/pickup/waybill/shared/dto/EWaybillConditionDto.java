package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 电子运单查询条件Dto
 * @author Foss-105888-Zhangxingwang
 * @date 2014-7-16 16:41:05
 */
public class EWaybillConditionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 快递员工号
	 */
	private String driverCode;
	
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
	 * 重量
	 */
	private BigDecimal weight;
	
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
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

	public String getWaybillType() {
		return waybillType;
	}
	
	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}
	
	public List<String> getOrderStatusList() {
		return orderStatusList;
	}
	
	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}
	
	public void setBillOrgCodeList(List<String> billOrgCodeList) {
		this.billOrgCodeList = billOrgCodeList;
	}
	
	public List<String> getBillOrgCodeList() {
		return billOrgCodeList;
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

	public String getCustContactCode() {
		return custContactCode;
	}

	public void setCustContactCode(String custContactCode) {
		this.custContactCode = custContactCode;
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

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
}
