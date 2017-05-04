package com.deppon.foss.module.pickup.creating.client.vo;

import java.util.Date;

public class BatchWaybillComVo {
	
	// 配载线路
    private String loadLineCode;

    // 配载部门
    private String loadOrgCode;

    // 最终配载部门
    private String lastLoadOrgCode;

    // 预计出发时间
    private Date preDepartureTime;

    // 预计派送/提货时间
    private Date preCustomerPickupTime;
    // 是否可以打木架
 	private String doPacking;
   // 代打木架部门
 	private String packageOrgCode;
 	//最终外场
 	private String lastOutLoadOrgCode;
 	
 	//产品编码
 	private String productCode;
 	//产品名称
	private String productName;
	//产品ID
	private String productId;
	 // 发货客户编码
    private String deliveryCustomerCode;
    // 发货客户名称
    private String deliveryCustomerName;
    // 发货客户联系人
    private String deliveryCustomerContact;
    // 发货具体地址
    private String deliveryCustomerAddress;
    // 收货客户编码
    private String receiveCustomerCode;
    // 收货客户名称
    private String receiveCustomerName;
    // 收货客户联系人
    private String receiveCustomerContact;
    // 收货具体地址
    private String receiveCustomerAddress;
    //AB货
    private String goodsTypeAB;
    
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}
	public String getDeliveryCustomerAddress() {
		return deliveryCustomerAddress;
	}
	public void setDeliveryCustomerAddress(String deliveryCustomerAddress) {
		this.deliveryCustomerAddress = deliveryCustomerAddress;
	}
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}
	public String getLoadLineCode() {
		return loadLineCode;
	}
	public void setLoadLineCode(String loadLineCode) {
		this.loadLineCode = loadLineCode;
	}
	public String getLoadOrgCode() {
		return loadOrgCode;
	}
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public Date getPreDepartureTime() {
		return preDepartureTime;
	}
	public void setPreDepartureTime(Date preDepartureTime) {
		this.preDepartureTime = preDepartureTime;
	}
	public Date getPreCustomerPickupTime() {
		return preCustomerPickupTime;
	}
	public void setPreCustomerPickupTime(Date preCustomerPickupTime) {
		this.preCustomerPickupTime = preCustomerPickupTime;
	}
	public String getDoPacking() {
		return doPacking;
	}
	public void setDoPacking(String doPacking) {
		this.doPacking = doPacking;
	}
	public String getPackageOrgCode() {
		return packageOrgCode;
	}
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}
	public String getLastOutLoadOrgCode() {
		return lastOutLoadOrgCode;
	}
	public void setLastOutLoadOrgCode(String lastOutLoadOrgCode) {
		this.lastOutLoadOrgCode = lastOutLoadOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getGoodsTypeAB() {
		return goodsTypeAB;
	}
	public void setGoodsTypeAB(String goodsTypeAB) {
		this.goodsTypeAB = goodsTypeAB;
	}
	
	
 	

}
