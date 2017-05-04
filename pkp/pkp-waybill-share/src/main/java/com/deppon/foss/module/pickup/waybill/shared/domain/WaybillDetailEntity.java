package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @ClassName: WaybillDetailEntity 
* @Description: 开单明细-图片开单推送app
* @author dpyuanjb@deppon.com/092039
* @date 2014年10月28日 下午3:16:13 
*
 */
public class WaybillDetailEntity implements Serializable{
	
	private static final long serialVersionUID = -1185297549344493002L;
	// 运单号
	private String waybillCode;
	// 收入部门名字
	private String departDeptName;
	// 目的地网点 / 自提网点(网点名字)
	private String destinationDeptName;
	// 发货人姓名
	private String shipperName;
	// 发货人手机号
	private String shipperPhone;
	// 发货人地址
	private String shipperAddress;
	// 收货人姓名
	private String consigneeName;
	// 收货人地址
	private String consigneeAddress;
	// 收货人手机号
	private String consigneePhone;
	
	// 增值服务费
	private List<AppreciationEntity> appreciationCost;
	// 标签信息
	private LabelPrintEntity labelDetail;
	
	private String createTime;
	/**
	 * 开单员姓名
	 */
	private String billUserName;
	/**
	 * 开单员手机
	 */
	private String biilUserPhone;
	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**提货网点 - 20161212-FOSS348757增  需求编号：DN201612050014*/
	private String customerPickupOrgName;
	
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getDepartDeptName() {
		return departDeptName;
	}
	public void setDepartDeptName(String departDeptName) {
		this.departDeptName = departDeptName;
	}
	public String getDestinationDeptName() {
		return destinationDeptName;
	}
	public void setDestinationDeptName(String destinationDeptName) {
		this.destinationDeptName = destinationDeptName;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getShipperPhone() {
		return shipperPhone;
	}
	public void setShipperPhone(String shipperPhone) {
		this.shipperPhone = shipperPhone;
	}
	public String getShipperAddress() {
		return shipperAddress;
	}
	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public List<AppreciationEntity> getAppreciationCost() {
		return appreciationCost;
	}
	public void setAppreciationCost(List<AppreciationEntity> appreciationCost) {
		this.appreciationCost = appreciationCost;
	}
	public LabelPrintEntity getLabelDetail() {
		return labelDetail;
	}
	public void setLabelDetail(LabelPrintEntity labelDetail) {
		this.labelDetail = labelDetail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBillUserName() {
		return billUserName;
	}
	public void setBillUserName(String billUserName) {
		this.billUserName = billUserName;
	}
	public String getBiilUserPhone() {
		return biilUserPhone;
	}
	public void setBiilUserPhone(String biilUserPhone) {
		this.biilUserPhone = biilUserPhone;
	}
	
}
