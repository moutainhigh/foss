package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WaybillBatchCreateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8775329181044735062L;
	/**
	 * 序号
	 */
	private String serialNumber;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 发货人手机号
	 */
	private String deliveryCustomerMobilephone;
	/**
	 * 发货人电话
	 */
	private String deliveryCustomerPhone;
	/**
	 * 发货人名称
	 */
	private String deliveryCustomerName;
	/**
	 * 发货部门CODE
	 */
	private String receiveOrgCode;
	/**
	 * 发货部门名称(出发网点)
	 */
	private String receiveOrgName;
	/**
	 * 发货人详细地址
	 */
	private String deliveryCustomerAddress;
	/**
	 * 收货人手机号
	 */
	private String receiveCustomerMobilephone;
	/**
	 * 收货人电话
	 */
	private String receiveCustomerPhone;
	/**
	 * 收货部门CODE
	 */
	private String customerPickupOrgCode;
	/**
	 * 收货部门name(提货网点)
	 */
	private String customerPickupOrgName;
	/**
	 * 收货详细地址
	 */
	private String receiveCustomerAddress;
	/**
	 * 运输性质编码(?)
	 */
	private String productCode;
	/**
	 * 运输性质名称
	 */
	private String transportProperiesName;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	/**
	 * 货物名称
	 */
	private String goodsName;
	/**
	 * 总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 总重量
	 */
	private BigDecimal goodsWeightTotal;
	/**
	 * 总体积
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 对内备注
	 */
	private String innerNotes;
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	/**
	 * 付款方式名称(?)
	 */
	private String paidMethodName;
	/**
	 * 付款方式编码(?)
	 */
	private String paidMethod;
	/**
	 * 货物包装
	 */
	private String goodsPackage;
	/**
	 * 货物类型(?)
	 */
	private String goodsTypeCode;
	 /**
	  * 开单开始时间
	  */
    private Date billStartTime;
    
    /**
     * 开单结束时间
     */
    private Date billEndTime;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}

	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}

	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTransportProperiesName() {
		return transportProperiesName;
	}

	public void setTransportProperiesName(String transportProperiesName) {
		this.transportProperiesName = transportProperiesName;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	public String getInnerNotes() {
		return innerNotes;
	}

	public void setInnerNotes(String innerNotes) {
		this.innerNotes = innerNotes;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getCodAmount() {
		return codAmount;
	}

	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	public String getPaidMethodName() {
		return paidMethodName;
	}

	public void setPaidMethodName(String paidMethodName) {
		this.paidMethodName = paidMethodName;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}

	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
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
}
