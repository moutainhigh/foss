package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.util.DateUtils;

public class LineLossWaybillInfDto{
	/**开单部门*/
	private String createOrgName;
	/**运输类型*/
	private String transportType;
	/**托运人*/
	private String deliveryCustomerName;
	/**运输性质*/
	private String productName;
	/**收货人*/
	private String receiveCustomerName;
	/**储运事项*/
	private String transportationRemark;
	/**收货人电话*/
	private String receiveCustomerPhone;
	/**提货方式*/
	private String receiveMethod;
	/**重量*/
	private String goodsWeightTotal;
	/**体积*/
	private String goodsVolumeTotal;
	/**件数*/
	private String goodsQtyTotal;
	/**货物名称*/
	private String goodsName;
	/**发货时间*/
	private Date billTime;
	/**发货时间*/
	private String billTimeStr;
	/**到达部门*/
	private String customerPickupOrgName;
	/**收货部门*/
	private String receiveOrgName;
	/**付款方式*/
	private String paidMethod;
	/**保险金额*/
	private BigDecimal insuranceFee;
	/**包装*/
	private String goodsPackage;
	/**运费总额*/
	private BigDecimal totalFee;
	/**返货类型*/
	private String returnBillType;
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public String getTransportationRemark() {
		return transportationRemark;
	}
	public void setTransportationRemark(String transportationRemark) {
		this.transportationRemark = transportationRemark;
	}
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}
	public String getReceiveMethod() {
		return receiveMethod;
	}
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}
	public String getGoodsWeightTotal() {
		return goodsWeightTotal;
	}
	public void setGoodsWeightTotal(String goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}
	public String getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}
	public void setGoodsVolumeTotal(String goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}
	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getPaidMethod() {
		return paidMethod;
	}
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public String getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public String getReturnBillType() {
		return returnBillType;
	}
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}
	public String getBillTimeStr() {
		return billTimeStr;
	}
	public void setBillTimeStr() {
		this.billTimeStr = DateUtils.convert(this.billTime, DateUtils.DATE_TIME_FORMAT);
	}
	
}
